package dtls;

import JFSM.JFSM.src.main.java.com.fsm.StateManager;
import JFSM.JFSM.src.main.java.com.fsm.module.StateHandler;
import JFSM.JFSM.src.main.java.com.fsm.unit.StateUnit;
import dtls.fsm.definition.DtlsEvent;
import dtls.fsm.definition.DtlsState;
import dtls.handler.DtlsPacketHandler;
import dtls.packet.DtlsPacket;
import dtls.packet.base.DtlsRecordFactory;
import dtls.packet.handshake.DtlsHandshake;
import dtls.packet.recordlayer.DtlsRecordHeader;
import dtls.packet.recordlayer.DtlsRecordLayer;
import dtls.packet.recordlayer.message.DtlsChangeCipherSpec;
import dtls.type.*;
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
                false, false,
                10, 500000, 500000
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
        // DTLS UNIT 생성 (Client: 1, Server: 2)

        // Client dtls unit
        String dtls1Key = "DTLS_1";
        DtlsHandshakeManager.getInstance().addDtlsUnit(dtls1Key);
        DtlsUnit dtlsUnit1 = DtlsHandshakeManager.getInstance().getDtlsUnit(dtls1Key);
        Assert.assertNotNull(dtlsUnit1);

        StateManager stateManager1 = dtlsUnit1.getDtlsFsmManager().getStateManager();
        StateHandler stateHandler1 = stateManager1.getStateHandler(DtlsState.NAME);
        StateUnit stateUnit1 = stateManager1.getStateUnit(dtlsUnit1.getDtlsStateUnitName());

        // Server dtls unit
        String dtls2Key = "DTLS_2";
        DtlsHandshakeManager.getInstance().addDtlsUnit(dtls2Key);
        DtlsUnit dtlsUnit2 = DtlsHandshakeManager.getInstance().getDtlsUnit(dtls2Key);
        Assert.assertNotNull(dtlsUnit2);

        StateManager stateManager2 = dtlsUnit2.getDtlsFsmManager().getStateManager();
        StateHandler stateHandler2 = stateManager2.getStateHandler(DtlsState.NAME);
        StateUnit stateUnit2 = stateManager2.getStateUnit(dtlsUnit2.getDtlsStateUnitName());
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
        DtlsHandshake dtlsHandshake = makeDtlsHandshake(dtlsClientHelloHandshakeCommonBody, dtlsClientHello);
        DtlsPacket dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        byte[] dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel1.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_1]
         * DTLS_UNIT_1: PREPARING > SENDING > WAITING
         * >> Waiting for HelloVerifyRequest from server
         */
        stateHandler1.fire(DtlsEvent.BUFFER_NEXT_FLIGHT.name(), stateUnit1);
        stateHandler1.fire(DtlsEvent.SEND_FLIGHT_2.name(), stateUnit1);
        ///////////////////////////////

        ///////////////////////////////
        // 2) SEND HELLO VERIFY REQUEST (Server > Client)
        DtlsHelloVerifyRequest dtlsHelloVerifyRequest = DtlsMessageTest.createDtlsHelloVerifyRequestTest();
        DtlsHandshakeCommonBody dtlsHelloVerifyRequestHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_HELLO_VERIFY_REQUEST),
                dtlsHelloVerifyRequest.getData().length
        );
        dtlsHandshake = makeDtlsHandshake(dtlsHelloVerifyRequestHandshakeCommonBody, dtlsHelloVerifyRequest);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_2]
         * DTLS_UNIT_2: PREPARING > SENDING > WAITING
         * >> Waiting for ClientHello from client
         * DTLS_UNIT_1: If timer is not expired, WAITING > PREPARING
         *              If timer is expired, WAITING > SENDING
         */
        stateHandler2.fire(DtlsEvent.BUFFER_NEXT_FLIGHT.name(), stateUnit2);
        stateHandler2.fire(DtlsEvent.SEND_FLIGHT_2.name(), stateUnit2);

        // MessageHandler 에서 직접 상태 천이 필요 > Timer 삭제 여부 반영 > 지금은 테스트 중이니까 여기서 천이
        stateHandler1.fire(DtlsEvent.RECEIVE_NEXT_FLIGHT.name(), stateUnit1);
        //stateHandler1.fire(DtlsEvent.TIMER_EXPIRES.name(), stateUnit1);
        ///////////////////////////////

        ///////////////////////////////
        // 3) SEND CLIENT HELLO (Client > Server)
        dtlsClientHello = DtlsMessageTest.createDtlsClientHelloTest();
        dtlsClientHelloHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_CLIENT_HELLO),
                dtlsClientHello.getData().length
        );
        dtlsHandshake = makeDtlsHandshake(dtlsClientHelloHandshakeCommonBody, dtlsClientHello);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel1.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_3]
         * DTLS_UNIT_1: PREPARING > SENDING > WAITING
         * >> Waiting for DTLS_FLIGHT_4
         * DTLS_UNIT_2: WAITING > PREPARING
         */
        stateHandler1.fire(DtlsEvent.BUFFER_NEXT_FLIGHT.name(), stateUnit1);
        stateHandler1.fire(DtlsEvent.SEND_FLIGHT_2.name(), stateUnit1);

        stateHandler2.fire(DtlsEvent.RECEIVE_NEXT_FLIGHT.name(), stateUnit2);
        ///////////////////////////////

        ///////////////////////////////
        // 4) SEND SERVER HELLO (Server > Client)
        DtlsServerHello dtlsServerHello = DtlsMessageTest.createDtlsServerHelloTest();
        DtlsHandshakeCommonBody dtlsServerHelloHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_SERVER_HELLO),
                dtlsServerHello.getData().length
        );
        dtlsHandshake = makeDtlsHandshake(dtlsServerHelloHandshakeCommonBody, dtlsServerHello);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_4]
         * DTLS_UNIT_2: PREPARING > SENDING > WAITING
         * >> Waiting for DTLS_FLIGHT_5
         * DTLS_UNIT_1: WAITING > PREPARING
         */
        stateHandler2.fire(DtlsEvent.BUFFER_NEXT_FLIGHT.name(), stateUnit2);
        stateHandler2.fire(DtlsEvent.SEND_FLIGHT_2.name(), stateUnit2);

        stateHandler1.fire(DtlsEvent.RECEIVE_NEXT_FLIGHT.name(), stateUnit1);
        ///////////////////////////////

        ///////////////////////////////
        // 5) SEND CERTIFICATE (optional) (Server > Client)
        DtlsCertificate dtlsCertificate = DtlsMessageTest.createDtlsCertificateTest();
        int certificateDataLength = 0;
        if (dtlsCertificate != null) {
            certificateDataLength = dtlsCertificate.getData().length;
        }
        DtlsHandshakeCommonBody dtlsCertificateHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_CERTIFICATE),
                certificateDataLength
        );
        dtlsHandshake = makeDtlsHandshake(dtlsCertificateHandshakeCommonBody, dtlsCertificate);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_4]
         */
        ///////////////////////////////

        ///////////////////////////////
        // 6) SEND SERVER KEY EXCHANGE (optional) (Server > Client)
        DtlsServerKeyExchange dtlsServerKeyExchange = DtlsMessageTest.createDtlsServerKeyExchangeTest();
        DtlsHandshakeCommonBody dtlsServerKeyExchangeHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_SERVER_KEY_EXCHANGE),
                dtlsServerKeyExchange.getData().length
        );
        dtlsHandshake = makeDtlsHandshake(dtlsServerKeyExchangeHandshakeCommonBody, dtlsServerKeyExchange);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_4]
         */
        ///////////////////////////////

        ///////////////////////////////
        // 7) SEND CERTIFICATE REQUEST (optional) (Server > Client)
        // SKIP

        /**
         * [DTLS_FLIGHT_4]
         */
        ///////////////////////////////

        ///////////////////////////////
        // 8) SEND SERVER HELLO DONE (Server > Client)
        DtlsServerHelloDone dtlsServerHelloDone = DtlsMessageTest.createDtlsServerHelloDoneTest();
        DtlsHandshakeCommonBody dtlsServerHelloDoneExchangeHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_SERVER_HELLO_DONE),
                0
        );
        dtlsHandshake = makeDtlsHandshake(dtlsServerHelloDoneExchangeHandshakeCommonBody, dtlsServerHelloDone);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_4]
         */
        ///////////////////////////////

        ///////////////////////////////
        // 9) SEND CERTIFICATE (optional) (Client > Server)
        dtlsCertificate = DtlsMessageTest.createDtlsCertificateTest();
        certificateDataLength = 0;
        if (dtlsCertificate != null) {
            certificateDataLength = dtlsCertificate.getData().length;
        }
        dtlsCertificateHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_CERTIFICATE),
                certificateDataLength
        );
        dtlsHandshake = makeDtlsHandshake(dtlsCertificateHandshakeCommonBody, dtlsCertificate);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel1.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_5]
         * DTLS_UNIT_1: PREPARING > SENDING > WAITING
         * >> Waiting for DTLS_FLIGHT_6
         * DTLS_UNIT_2: WAITING > PREPARING
         */
        stateHandler1.fire(DtlsEvent.BUFFER_NEXT_FLIGHT.name(), stateUnit1);
        stateHandler1.fire(DtlsEvent.SEND_FLIGHT_2.name(), stateUnit1);

        stateHandler2.fire(DtlsEvent.RECEIVE_NEXT_FLIGHT.name(), stateUnit2);
        ///////////////////////////////

        ///////////////////////////////
        // 10) SEND CLIENT KEY EXCHANGE (Client > Server)
        DtlsClientKeyExchange dtlsClientKeyExchange = DtlsMessageTest.createDtlsClientKeyExchangeTest();
        DtlsHandshakeCommonBody dtlsClientKeyExchangeHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_CLIENT_KEY_EXCHANGE),
                dtlsClientKeyExchange.getData().length
        );
        dtlsHandshake = makeDtlsHandshake(dtlsClientKeyExchangeHandshakeCommonBody, dtlsClientKeyExchange);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel1.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_5]
         */
        ///////////////////////////////

        ///////////////////////////////
        // 11) SEND CLIENT KEY EXCHANGE (optional) (Client > Server)
        // SKIP

        /**
         * [DTLS_FLIGHT_5]
         */
        ///////////////////////////////

        ///////////////////////////////
        // 12) SEND CHANGE CIPHER SPEC (Client > Server)
        DtlsChangeCipherSpec dtlsChangeCipherSpecMessage = DtlsMessageTest.createDtlsChangeCipherSpecTest();
        dtlsPacket = makeSingleDtlsPacket(dtlsChangeCipherSpecMessage);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel1.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_5]
         */
        ///////////////////////////////

        ///////////////////////////////
        // 13) SEND FINISHED (Client > Server)
        DtlsFinished dtlsFinished = DtlsMessageTest.createDtlsFinishedTest();
        DtlsHandshakeCommonBody dtlsFinishedHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_FINISHED),
                0
        );
        dtlsHandshake = makeDtlsHandshake(dtlsFinishedHandshakeCommonBody, dtlsFinished);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel1.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_5]
         * DTLS_UNIT_1: WAITING > FINISHED
         */
        stateHandler1.fire(DtlsEvent.RECEIVE_LAST_FLIGHT.name(), stateUnit1);
        ///////////////////////////////

        ///////////////////////////////
        // 14) SEND CHANGE CIPHER SPEC (Server > Client)
        dtlsChangeCipherSpecMessage = DtlsMessageTest.createDtlsChangeCipherSpecTest();
        dtlsPacket = makeSingleDtlsPacket(dtlsChangeCipherSpecMessage);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_6]
         * DTLS_UNIT_2: PREPARING > SENDING
         */
        stateHandler2.fire(DtlsEvent.BUFFER_NEXT_FLIGHT.name(), stateUnit2);
        ///////////////////////////////

        ///////////////////////////////
        // 14) SEND FINISHED (Server > Client)
        dtlsFinished = DtlsMessageTest.createDtlsFinishedTest();
        dtlsFinishedHandshakeCommonBody = DtlsMessageTest.createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_FINISHED),
                0
        );
        dtlsHandshake = makeDtlsHandshake(dtlsFinishedHandshakeCommonBody, dtlsFinished);
        dtlsPacket = makeSingleDtlsPacket(dtlsHandshake);
        dtlsPacketData = dtlsPacket.getData();
        Assert.assertNotNull(dtlsPacketData);
        nettyChannel2.sendData(dtlsPacketData, dtlsPacketData.length);

        /**
         * [DTLS_FLIGHT_6]
         * DTLS_UNIT_2: SENDING > FINISHED
         */
        stateHandler2.fire(DtlsEvent.SEND_FLIGHT_1.name(), stateUnit2);
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

    public static DtlsHandshake makeDtlsHandshake(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, DtlsFormat dtlsFormat) {
        return DtlsMessageTest.createDtlsHandshakeByObjectTest(dtlsHandshakeCommonBody, dtlsFormat);
    }

    public static DtlsPacket makeMultiDtlsPacket(List<DtlsRecordFactory> dtlsRecordFactoryList) {
        if (dtlsRecordFactoryList == null || dtlsRecordFactoryList.isEmpty()) { return null; }

        List<DtlsRecordLayer> dtlsRecordLayerList = new ArrayList<>();
        for (DtlsRecordFactory dtlsRecordFactory : dtlsRecordFactoryList) {
            if (dtlsRecordFactory == null) { continue; }

            byte[] dtlsRecordFactoryData = dtlsRecordFactory.getData();
            if (dtlsRecordFactoryData == null) { continue; }

            DtlsRecordHeader dtlsRecordHeader = DtlsMessageTest.createDtlsRecordHeaderTest(dtlsRecordFactoryData.length);
            DtlsRecordLayer dtlsRecordLayer = new DtlsRecordLayer(dtlsRecordHeader, dtlsRecordFactory);
            dtlsRecordLayerList.add(dtlsRecordLayer);
        }
        return DtlsMessageTest.createDtlsPacketTest(dtlsRecordLayerList);
    }

    public static DtlsPacket makeSingleDtlsPacket(DtlsRecordFactory dtlsRecordFactory) {
        if (dtlsRecordFactory == null) { return null; }

        byte[] dtlsHandshakeData = dtlsRecordFactory.getData();

        DtlsRecordHeader dtlsRecordHeader = DtlsMessageTest.createDtlsRecordHeaderTest(dtlsHandshakeData.length);
        List<DtlsRecordLayer> dtlsRecordLayerList = new ArrayList<>();
        DtlsRecordLayer dtlsRecordLayer = new DtlsRecordLayer(dtlsRecordHeader, dtlsRecordFactory);
        dtlsRecordLayerList.add(dtlsRecordLayer);
        return DtlsMessageTest.createDtlsPacketTest(dtlsRecordLayerList);
    }

}
