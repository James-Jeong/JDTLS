package dtls.handler;

import dtls.packet.DtlsPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtlsPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Logger logger = LoggerFactory.getLogger(DtlsPacketHandler.class);

    private final String id;

    ////////////////////////////////////////////////////////////////////////////////

    public DtlsPacketHandler(String id) {
        this.id = id;
        logger.debug("[DtlsPacketHandler<{}>] is created.", id);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
        ByteBuf buf = msg.content();
        if (buf == null) {
            return;
        }

        int dataLength = 0;
        try {
            int readableBytes = buf.readableBytes();
            if (readableBytes > 0) {
                byte[] data = new byte[readableBytes];
                buf.readBytes(data);
                dataLength = data.length;

                DtlsPacket dtlsPacket = new DtlsPacket(data);
                logger.debug("[DtlsPacketHandler<{}>] [RECV] DtlsPacket: \n{}.", id, dtlsPacket);
            }
        } catch (Exception e) {
            logger.warn("[DtlsPacketHandler<{}>] Fail to handle UDP Packet. (length={})", id, dataLength, e);
        }
    }

    @Override
    public void exceptionCaught (ChannelHandlerContext ctx, Throwable cause) {
        //logger.warn("DtlsPacketHandler.exceptionCaught", cause);
        //ctx.close();
    }

}
