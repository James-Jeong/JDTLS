package dtls.type;

import dtls.cipher.DtlsCipherSuiteList;
import dtls.compression.DtlsCompressionMethodType;
import dtls.packet.base.DtlsProtocolVersion;
import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsRandom;
import util.module.ByteUtil;

public class DtlsClientHello extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int MIN_LENGTH = 40;

    private DtlsProtocolVersion protocolVersion = null; // 2 bytes
    transient private byte[] randomBytes = null; // 32 bytes > DtlsRandom.getRandom()
    private short sessionIdLength = 0; // 1 byte
    transient private byte[] sessionId = null; // sessionIdLength bytes
    private short cookieLength = 0; // 1 byte
    transient private byte[] cookie = null; // cookieLength bytes
    private int cipherSuitesLength = 0; // 2 bytes
    private DtlsCipherSuiteList dtlsCipherSuiteList = null; // cipherSuitesLength bytes
    private short compressionMethodsLength = 0; // 1 byte
    private DtlsCompressionMethodType dtlsCompressionMethod = null; // 1 byte
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsClientHello(DtlsProtocolVersion protocolVersion, byte[] randomBytes,
                           short sessionIdLength, byte[] sessionId, short cookieLength, byte[] cookie,
                           int cipherSuitesLength, DtlsCipherSuiteList dtlsCipherSuiteList,
                           short compressionMethodsLength, DtlsCompressionMethodType compressionMethod) {
        this.protocolVersion = protocolVersion;
        this.randomBytes = randomBytes;
        this.sessionIdLength = sessionIdLength;
        this.sessionId = sessionId;
        this.cookieLength = cookieLength;
        this.cookie = cookie;
        this.cipherSuitesLength = cipherSuitesLength;
        this.dtlsCipherSuiteList = dtlsCipherSuiteList;
        this.compressionMethodsLength = compressionMethodsLength;
        this.dtlsCompressionMethod = compressionMethod;
    }

    public DtlsClientHello() {}

    public DtlsClientHello(byte[] data) {
        if (data != null && data.length >= MIN_LENGTH) {
            int index = 0;

            byte[] protocolVersionData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, protocolVersionData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            protocolVersion = new DtlsProtocolVersion(protocolVersionData);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            randomBytes = new byte[DtlsRandom.LENGTH];
            System.arraycopy(data, index, randomBytes, 0, DtlsRandom.LENGTH);
            index += DtlsRandom.LENGTH;

            byte[] sessionIdLengthData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, sessionIdLengthData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] sessionIdLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(sessionIdLengthData, 0, sessionIdLengthData2, ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            sessionIdLength = ByteUtil.bytesToShort(sessionIdLengthData2, true);
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            if (sessionIdLength > 0) {
                sessionId = new byte[sessionIdLength];
                System.arraycopy(data, index, sessionId, 0, sessionIdLength);
                index += sessionIdLength;
            }

            byte[] cookieLengthData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, cookieLengthData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] cookieLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(cookieLengthData, 0, cookieLengthData2, ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            cookieLength = ByteUtil.bytesToShort(cookieLengthData2, true);
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            if (cookieLength > 0) {
                cookie = new byte[cookieLength];
                System.arraycopy(data, index, cookie, 0, cookieLength);
                index += cookieLength;
            }

            byte[] cipherSuitesLengthData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, cipherSuitesLengthData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            byte[] cipherSuitesLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(cipherSuitesLengthData, 0, cipherSuitesLengthData2, ByteUtil.NUM_BYTES_IN_SHORT, ByteUtil.NUM_BYTES_IN_SHORT);
            cipherSuitesLength = ByteUtil.bytesToInt(cipherSuitesLengthData2, true);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            if (cipherSuitesLength > 0) {
                byte[] cipherSuitesData = new byte[cipherSuitesLength];
                System.arraycopy(data, index, cipherSuitesData, 0, cipherSuitesLength);
                dtlsCipherSuiteList = new DtlsCipherSuiteList(cipherSuitesData);
                index += cipherSuitesLength;
            }

            byte[] compressionMethodsLengthData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, compressionMethodsLengthData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] compressionMethodsLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(compressionMethodsLengthData, 0, compressionMethodsLengthData2, ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            compressionMethodsLength = ByteUtil.bytesToShort(compressionMethodsLengthData2, true);
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            byte[] dtlsCompressionMethodData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, dtlsCompressionMethodData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            dtlsCompressionMethod = new DtlsCompressionMethodType(dtlsCompressionMethodData[0]);
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (protocolVersion == null || randomBytes == null || dtlsCompressionMethod == null) { return null; }

        int index = 0;
        byte[] data = new byte[MIN_LENGTH + sessionIdLength + cookieLength + cipherSuitesLength];

        System.arraycopy(protocolVersion.getVersion(), 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        System.arraycopy(randomBytes, 0, data, index, DtlsRandom.LENGTH);
        index += DtlsRandom.LENGTH;

        byte[] sessionIdLengthData = ByteUtil.shortToBytes(sessionIdLength, true);
        byte[] sessionIdLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(sessionIdLengthData, ByteUtil.NUM_BYTES_IN_BYTE, sessionIdLengthData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(sessionIdLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        index += ByteUtil.NUM_BYTES_IN_BYTE;

        if (sessionIdLength > 0 && sessionId != null) {
            System.arraycopy(sessionId, 0, data, index, sessionIdLength);
            index += sessionIdLength;
        }

        byte[] cookieLengthData = ByteUtil.shortToBytes(cookieLength, true);
        byte[] cookieLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(cookieLengthData, ByteUtil.NUM_BYTES_IN_BYTE, cookieLengthData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(cookieLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        index += ByteUtil.NUM_BYTES_IN_BYTE;

        if (cookieLength > 0 && cookie != null) {
            System.arraycopy(cookie, 0, data, index, cookieLength);
            index += cookieLength;
        }

        byte[] cipherSuitesLengthData = ByteUtil.intToBytes(cipherSuitesLength, true);
        byte[] cipherSuitesLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
        System.arraycopy(cipherSuitesLengthData, ByteUtil.NUM_BYTES_IN_SHORT, cipherSuitesLengthData2, 0, ByteUtil.NUM_BYTES_IN_SHORT);
        System.arraycopy(cipherSuitesLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        if (cipherSuitesLength > 0 && dtlsCipherSuiteList != null) {
            byte[] dtlsCipherSuiteListData = dtlsCipherSuiteList.getData();
            if (dtlsCipherSuiteListData.length > 0) {
                System.arraycopy(dtlsCipherSuiteListData, 0, data, index, dtlsCipherSuiteListData.length);
                index += dtlsCipherSuiteListData.length;
            }
        }

        byte[] compressionMethodsLengthData = ByteUtil.shortToBytes(compressionMethodsLength, true);
        byte[] compressionMethodsLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(compressionMethodsLengthData, ByteUtil.NUM_BYTES_IN_BYTE, compressionMethodsLengthData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(compressionMethodsLengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        index += ByteUtil.NUM_BYTES_IN_BYTE;

        byte[] dtlsCompressionMethodData = ByteUtil.intToBytes(dtlsCompressionMethod.getMethod(), true);
        byte[] dtlsCompressionMethodData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(dtlsCompressionMethodData, ByteUtil.NUM_BYTES_IN_SHORT - ByteUtil.NUM_BYTES_IN_BYTE, dtlsCompressionMethodData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(dtlsCompressionMethodData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);

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

    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
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

    public int getCipherSuitesLength() {
        return cipherSuitesLength;
    }

    public void setCipherSuitesLength(int cipherSuitesLength) {
        this.cipherSuitesLength = cipherSuitesLength;
    }

    public DtlsCipherSuiteList getDtlsCipherSuiteList() {
        return dtlsCipherSuiteList;
    }

    public void setDtlsCipherSuiteList(DtlsCipherSuiteList dtlsCipherSuiteList) {
        this.dtlsCipherSuiteList = dtlsCipherSuiteList;
    }

    public short getCompressionMethodsLength() {
        return compressionMethodsLength;
    }

    public void setCompressionMethodsLength(short compressionMethodsLength) {
        this.compressionMethodsLength = compressionMethodsLength;
    }

    public DtlsCompressionMethodType getDtlsCompressionMethod() {
        return dtlsCompressionMethod;
    }

    public void setDtlsCompressionMethod(DtlsCompressionMethodType dtlsCompressionMethod) {
        this.dtlsCompressionMethod = dtlsCompressionMethod;
    }
    ////////////////////////////////////////////////////////////

}
