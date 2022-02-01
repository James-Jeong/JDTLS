package dtls;

import JFSM.JFSM.src.main.java.com.fsm.StateManager;
import JFSM.JFSM.src.main.java.com.fsm.module.StateHandler;
import JFSM.JFSM.src.main.java.com.fsm.unit.StateUnit;
import dtls.fsm.definition.DtlsEvent;
import dtls.fsm.definition.DtlsState;
import dtls.handler.DtlsPacketHandler;
import dtls.packet.DtlsPacket;
import dtls.packet.handshake.DtlsHandshake;
import dtls.packet.recordlayer.DtlsRecordHeader;
import dtls.packet.recordlayer.DtlsRecordLayer;
import dtls.type.DtlsClientHello;
import dtls.type.DtlsHelloVerifyRequest;
import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;
import dtls.type.base.DtlsHandshakeType;
import dtls.unit.DtlsUnit;
import instance.BaseEnvironment;
import instance.DebugLevel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioDatagramChannel;
import network.ChannelHandlerMaker;
import network.definition.DestinationRecord;
import network.definition.NetAddress;
import network.socket.GroupSocket;
import network.socket.SocketManager;
import network.socket.SocketProtocol;
import network.socket.netty.NettyChannel;
import org.junit.Assert;
import org.junit.Test;
import service.ResourceManager;
import service.scheduler.schedule.ScheduleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DtlsNetworkTest {

    @Test
    public void test() {
        /////////////////////////////////////////////////////////
        // dtlsNormalFlow
        dtlsNormalFlow();
        /////////////////////////////////////////////////////////
    }

    public void dtlsNormalFlow() {
        ////////////////////////////////////////////////////////////
        // 인스턴스 생성
        BaseEnvironment baseEnvironment = new BaseEnvironment(
                new ScheduleManager(),
                new ResourceManager(5000, 7000),
                DebugLevel.DEBUG
        );
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // SocketManager 생성
        SocketManager socketManager = new SocketManager(
                baseEnvironment,
                false,
                10,
                500000,
                500000
        );
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 소켓 생성 (GroupSocket1)
        NetAddress netAddress1 = new NetAddress("127.0.0.1", 5000,true, SocketProtocol.UDP);
        ChannelInitializer<NioDatagramChannel> serverChannelInitializer1 = ChannelHandlerMaker.get(
                new DtlsPacketHandler("SENDER/" + netAddress1.getAddressString())
        );
        Assert.assertTrue(socketManager.addSocket(netAddress1, serverChannelInitializer1));
        GroupSocket groupSocket1 = socketManager.getSocket(netAddress1);
        Assert.assertNotNull(groupSocket1);
        // Listen channel open
        Assert.assertTrue(groupSocket1.getListenSocket().openListenChannel());
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 소켓 생성 (GroupSocket2)
        NetAddress netAddress2 = new NetAddress("127.0.0.1", 6000,true, SocketProtocol.UDP);
        ChannelInitializer<NioDatagramChannel> serverChannelInitializer2 = ChannelHandlerMaker.get(
                new DtlsPacketHandler("RECEIVER/" + netAddress2.getAddressString())
        );
        Assert.assertTrue(socketManager.addSocket(netAddress2, serverChannelInitializer2));
        GroupSocket groupSocket2 = socketManager.getSocket(netAddress2);
        Assert.assertNotNull(groupSocket2);
        // Listen channel open
        Assert.assertTrue(groupSocket2.getListenSocket().openListenChannel());
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // Destination 추가
        long sessionId = 1234;
        ChannelInitializer<NioDatagramChannel> clientChannelInitializer1 = ChannelHandlerMaker.get(new DtlsClientHandler());
        Assert.assertTrue(groupSocket1.addDestination(netAddress2, null, sessionId, clientChannelInitializer1));

        ChannelInitializer<NioDatagramChannel> clientChannelInitializer2 = ChannelHandlerMaker.get(new DtlsClientHandler());
        Assert.assertTrue(groupSocket2.addDestination(netAddress1, null, sessionId, clientChannelInitializer2));

        baseEnvironment.printMsg("[DtlsNetworkTest][dtlsNormalFlow] GROUP-SOCKET1: {%s}", groupSocket1.toString());
        baseEnvironment.printMsg("[DtlsNetworkTest][dtlsNormalFlow] GROUP-SOCKET2: {%s}", groupSocket2.toString());
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // DTLS 통신
        String dtlsKey = "1234";
        DtlsHandshakeManager.getInstance().addDtlsUnit(dtlsKey);
        DtlsUnit dtlsUnit = DtlsHandshakeManager.getInstance().getDtlsUnit(dtlsKey);
        Assert.assertNotNull(dtlsUnit);

        StateManager stateManager = dtlsUnit.getDtlsFsmManager().getStateManager();
        StateHandler stateHandler = stateManager.getStateHandler(DtlsState.NAME);
        StateUnit stateUnit = stateManager.getStateUnit(dtlsUnit.getDtlsStateUnitName());
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // DTLS 통신
        // netAddress1 : Client
        // netAddress2 : Server

        DestinationRecord destinationRecord1 = groupSocket1.getDestination(sessionId);
        Assert.assertNotNull(destinationRecord1);
        NettyChannel nettyChannel1 = destinationRecord1.getNettyChannel();
        Assert.assertNotNull(nettyChannel1);
        nettyChannel1.openConnectChannel(netAddress2.getAddressString(), netAddress2.getPort());

        DestinationRecord destinationRecord2 = groupSocket2.getDestination(sessionId);
        Assert.assertNotNull(destinationRecord2);
        NettyChannel nettyChannel2 = destinationRecord2.getNettyChannel();
        Assert.assertNotNull(nettyChannel2);
        nettyChannel2.openConnectChannel(netAddress1.getAddressString(), netAddress1.getPort());

        ///////////////////////////////
        // 1) SEND CLIENT HELLO (Client > Server)
        DtlsClientHello dtlsClientHello = DtlsMessageTest.createDtlsClientHelloTest();
        DtlsHandshakeCommonBody dtlsClientHelloHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_CLIENT_HELLO),
                dtlsClientHello.getData().length
        );
        DtlsPacket dtlsPacket = makeDtlsPacket(dtlsClientHelloHandshakeCommonBody, dtlsClientHello);

        byte[] dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel1.sendData(dtlsPacketData, dtlsPacketData.length);

        stateHandler.fire(DtlsEvent.BUFFER_NEXT_FLIGHT.name(), stateUnit);
        ///////////////////////////////

        ///////////////////////////////
        // 2) SEND HELLO VERIFY REQUEST (Server > Client)
        DtlsHelloVerifyRequest dtlsHelloVerifyRequest = DtlsMessageTest.createDtlsHelloVerifyRequestTest();
        DtlsHandshakeCommonBody dtlsHelloVerifyRequestHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_HELLO_VERIFY_REQUEST),
                dtlsHelloVerifyRequest.getData().length
        );
        dtlsPacket = makeDtlsPacket(dtlsHelloVerifyRequestHandshakeCommonBody, dtlsHelloVerifyRequest);

        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);
        ///////////////////////////////

        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 소켓 삭제
        TimeUnit timeUnit = TimeUnit.SECONDS;
        try {
            timeUnit.sleep(1);
        } catch (InterruptedException e) {
            baseEnvironment.printMsg(DebugLevel.WARN, "[DtlsNetworkTest][dtlsNormalFlow] SLEEP FAIL");
        }

        Assert.assertTrue(socketManager.removeSocket(netAddress1));
        Assert.assertTrue(socketManager.removeSocket(netAddress2));
        ////////////////////////////////////////////////////////////

        ////////////////////////////////////////////////////////////
        // 인스턴스 삭제
        baseEnvironment.stop();
        ////////////////////////////////////////////////////////////
    }

    public static DtlsPacket makeDtlsPacket(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, DtlsFormat dtlsFormat) {
        DtlsHandshake dtlsHandshake = DtlsMessageTest.createDtlsHandshakeByObjectTest(dtlsHandshakeCommonBody, dtlsFormat);
        byte[] dtlsHandshakeData = dtlsHandshake.getData();
        DtlsRecordHeader dtlsRecordHeader = DtlsMessageTest.createDtlsRecordHeaderTest(dtlsHandshakeData.length);
        List<DtlsRecordLayer> dtlsRecordLayerList = new ArrayList<>();
        DtlsRecordLayer dtlsRecordLayer = new DtlsRecordLayer(dtlsRecordHeader, dtlsHandshake);
        dtlsRecordLayerList.add(dtlsRecordLayer);
        return DtlsMessageTest.createDtlsPacketTest(dtlsRecordLayerList);
    }

}
