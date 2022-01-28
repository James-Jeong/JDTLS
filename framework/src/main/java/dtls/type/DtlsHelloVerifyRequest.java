package dtls.type;

import dtls.packet.base.DtlsProtocolVersion;
import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;
import util.module.ByteUtil;

public class DtlsHelloVerifyRequest extends DtlsFormat {

    public static final int MIN_LENGTH = DtlsHandshakeCommonBody.LENGTH + 3;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody; // 12 bytes
    private DtlsProtocolVersion protocolVersion; // 2 bytes
    private short cookieLength; // 1 byte
    private byte[] cookie; // cookieLength bytes

    public DtlsHelloVerifyRequest(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, DtlsProtocolVersion protocolVersion, short cookieLength, byte[] cookie) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.protocolVersion = protocolVersion;
        this.cookieLength = cookieLength;
        this.cookie = cookie;
    }

    public DtlsHelloVerifyRequest() {}

    public DtlsHelloVerifyRequest(byte[] data) {
        if (data.length >= MIN_LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

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

    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null || protocolVersion == null) { return null; }

        int index = 0;
        byte[] data;

        if (cookieLength > 0 && cookie != null) {
            data = new byte[MIN_LENGTH + cookieLength];

            byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
            System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
            index += DtlsHandshakeCommonBody.LENGTH;

            System.arraycopy(protocolVersion.getVersion(), 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] cookieLengthData = ByteUtil.shortToBytes(cookieLength, true);
            byte[] cookieLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(cookieLengthData, ByteUtil.NUM_BYTES_IN_BYTE, cookieLengthData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            System.arraycopy(cookieLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            System.arraycopy(cookie, 0, data, index, cookieLength);
        } else {
            data = new byte[MIN_LENGTH];

            byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
            System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
            index += DtlsHandshakeCommonBody.LENGTH;

            System.arraycopy(protocolVersion.getVersion(), 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] cookieLengthData = ByteUtil.shortToBytes(cookieLength, true);
            byte[] cookieLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(cookieLengthData, ByteUtil.NUM_BYTES_IN_BYTE, cookieLengthData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            System.arraycopy(cookieLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        }

        return data;
    }

    public DtlsHandshakeCommonBody getDtlsHandshakeCommonBody() {
        return dtlsHandshakeCommonBody;
    }

    public void setDtlsHandshakeCommonBody(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

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

}
