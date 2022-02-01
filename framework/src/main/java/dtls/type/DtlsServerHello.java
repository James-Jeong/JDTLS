package dtls.type;

import dtls.cipher.DtlsCipherSuite;
import dtls.compression.DtlsCompressionMethodType;
import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsRandom;
import util.module.ByteUtil;

public class DtlsServerHello extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int LENGTH = 70;

    transient private byte[] randomBytes = null; // 32 bytes (DtlsRandom.getRandom())
    private short sessionIdLength = 0; // 1 byte
    transient private byte[] sessionIdBytes = null; // 32 bytes
    private DtlsCipherSuite cipherSuite = null; // 2 bytes
    private DtlsCompressionMethodType dtlsCompressionMethod = null; // 1 byte
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsServerHello(byte[] randomBytes,
                           short sessionIdLength, byte[] sessionIdBytes,
                           DtlsCipherSuite cipherSuite,
                           DtlsCompressionMethodType dtlsCompressionMethod) {
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
            dtlsCompressionMethod = new DtlsCompressionMethodType(dtlsCompressionMethodData[0]);
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (randomBytes == null || sessionIdBytes == null
                || cipherSuite == null || dtlsCompressionMethod == null) { return null; }

        int index = 0;
        byte[] data = new byte[LENGTH];

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
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
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

    public DtlsCompressionMethodType getDtlsCompressionMethod() {
        return dtlsCompressionMethod;
    }

    public void setDtlsCompressionMethod(DtlsCompressionMethodType dtlsCompressionMethod) {
        this.dtlsCompressionMethod = dtlsCompressionMethod;
    }
    ////////////////////////////////////////////////////////////

}
