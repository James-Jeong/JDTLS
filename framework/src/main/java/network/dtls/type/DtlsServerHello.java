package network.dtls.type;

import network.dtls.cipher.DtlsCipherSuite;
import network.dtls.compression.DtlsCompressionMethod;
import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;
import network.dtls.type.base.DtlsRandom;
import util.module.ByteUtil;

public class DtlsServerHello extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH + 70;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody; // 12 bytes
    private byte[] randomBytes; // 32 bytes (DtlsRandom.getRandom())
    private short sessionIdLength; // 1 byte
    private byte[] sessionIdBytes; // 32 bytes
    private DtlsCipherSuite cipherSuite; // 2 bytes
    private DtlsCompressionMethod dtlsCompressionMethod; // 1 byte

    public DtlsServerHello(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, byte[] randomBytes, short sessionIdLength, byte[] sessionIdBytes, DtlsCipherSuite cipherSuite, DtlsCompressionMethod dtlsCompressionMethod) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.randomBytes = randomBytes;
        this.sessionIdLength = sessionIdLength;
        this.sessionIdBytes = sessionIdBytes;
        this.cipherSuite = cipherSuite;
        this.dtlsCompressionMethod = dtlsCompressionMethod;
    }

    public DtlsServerHello() {}

    public DtlsServerHello(byte[] data) {
        if (data.length == LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

            randomBytes = new byte[DtlsRandom.LENGTH];
            System.arraycopy(data, index, randomBytes, 0, DtlsRandom.LENGTH);
            index += DtlsRandom.LENGTH;

            byte[] sessionIdLengthData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, sessionIdLengthData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] sessionIdLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(sessionIdLengthData, 0, sessionIdLengthData2, ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            sessionIdLength = ByteUtil.bytesToShort(sessionIdLengthData2, true);
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            sessionIdBytes = new byte[32];
            System.arraycopy(data, index, sessionIdBytes, 0, 32);
            index += 32;

            byte[] cipherSuiteData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, cipherSuiteData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            cipherSuite = new DtlsCipherSuite(cipherSuiteData);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] dtlsCompressionMethodData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, dtlsCompressionMethodData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            dtlsCompressionMethod = new DtlsCompressionMethod(dtlsCompressionMethodData[0]);
        }
    }

    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null || randomBytes == null || sessionIdBytes == null
                || cipherSuite == null || dtlsCompressionMethod == null) { return null; }

        int index = 0;
        byte[] data = new byte[LENGTH];

        byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
        System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
        index += DtlsHandshakeCommonBody.LENGTH;

        System.arraycopy(randomBytes, 0, data, index, randomBytes.length);
        index += randomBytes.length;

        byte[] sessionIdLengthData = ByteUtil.shortToBytes(sessionIdLength, true);
        byte[] sessionIdLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(sessionIdLengthData, ByteUtil.NUM_BYTES_IN_BYTE, sessionIdLengthData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(sessionIdLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        index += ByteUtil.NUM_BYTES_IN_BYTE;

        System.arraycopy(sessionIdBytes, 0, data, index, sessionIdBytes.length);
        index += sessionIdBytes.length;

        System.arraycopy(cipherSuite.getCipherSuite(), 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        byte[] dtlsCompressionMethodData = ByteUtil.intToBytes(dtlsCompressionMethod.getMethod(), true);
        byte[] dtlsCompressionMethodData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(dtlsCompressionMethodData, ByteUtil.NUM_BYTES_IN_SHORT - ByteUtil.NUM_BYTES_IN_BYTE, dtlsCompressionMethodData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(dtlsCompressionMethodData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);

        return data;
    }

    public DtlsHandshakeCommonBody getDtlsHandshakeCommonBody() {
        return dtlsHandshakeCommonBody;
    }

    public void setDtlsHandshakeCommonBody(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    public byte[] getRandomBytes() {
        return randomBytes;
    }

    public void setRandomBytes(byte[] randomBytes) {
        this.randomBytes = randomBytes;
    }

    public short getSessionIdLength() {
        return sessionIdLength;
    }

    public void setSessionIdLength(short sessionIdLength) {
        this.sessionIdLength = sessionIdLength;
    }

    public byte[] getSessionIdBytes() {
        return sessionIdBytes;
    }

    public void setSessionIdBytes(byte[] sessionIdBytes) {
        this.sessionIdBytes = sessionIdBytes;
    }

    public DtlsCipherSuite getCipherSuite() {
        return cipherSuite;
    }

    public void setCipherSuite(DtlsCipherSuite cipherSuite) {
        this.cipherSuite = cipherSuite;
    }

    public DtlsCompressionMethod getDtlsCompressionMethod() {
        return dtlsCompressionMethod;
    }

    public void setDtlsCompressionMethod(DtlsCompressionMethod dtlsCompressionMethod) {
        this.dtlsCompressionMethod = dtlsCompressionMethod;
    }
}
