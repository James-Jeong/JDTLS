package dtls.type;

import dtls.packet.base.DtlsProtocolVersion;
import dtls.type.base.DtlsFormat;
import util.module.ByteUtil;

public class DtlsHelloVerifyRequest extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int MIN_LENGTH = 3;

    private DtlsProtocolVersion protocolVersion = null; // 2 bytes
    private short cookieLength = 0; // 1 byte
    transient private byte[] cookie = null; // cookieLength bytes
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsHelloVerifyRequest(DtlsProtocolVersion protocolVersion, short cookieLength, byte[] cookie) {
        this.protocolVersion = protocolVersion;
        this.cookieLength = cookieLength;
        this.cookie = cookie;
    }

    public DtlsHelloVerifyRequest() {}

    public DtlsHelloVerifyRequest(byte[] data) {
        if (data != null && data.length >= MIN_LENGTH) {
            int index = 0;

            byte[] protocolVersionData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, protocolVersionData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            protocolVersion = new DtlsProtocolVersion(protocolVersionData);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] cookieLengthData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, cookieLengthData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] cookieLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(cookieLengthData, 0, cookieLengthData2, ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            cookieLength = ByteUtil.bytesToShort(cookieLengthData2, true);
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            if (cookieLength > 0) {
                cookie = new byte[cookieLength];
                System.arraycopy(data, index, cookie, 0, cookieLength);
            }
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (protocolVersion == null) { return null; }

        int index = 0;
        byte[] data = new byte[MIN_LENGTH + cookieLength];

        System.arraycopy(protocolVersion.getVersion(), 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        byte[] cookieLengthData = ByteUtil.shortToBytes(cookieLength, true);
        byte[] cookieLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(cookieLengthData, ByteUtil.NUM_BYTES_IN_BYTE, cookieLengthData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(cookieLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        index += ByteUtil.NUM_BYTES_IN_BYTE;

        if (cookieLength > 0 && cookie != null) {
            System.arraycopy(cookie, 0, data, index, cookieLength);
        }

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(DtlsProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public short getCookieLength() {
        return cookieLength;
    }

    public void setCookieLength(short cookieLength) {
        this.cookieLength = cookieLength;
    }

    public byte[] getCookie() {
        return cookie;
    }

    public void setCookie(byte[] cookie) {
        this.cookie = cookie;
    }
    ////////////////////////////////////////////////////////////

}
