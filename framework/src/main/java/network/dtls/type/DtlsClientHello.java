package network.dtls.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.cipher.DtlsCipherSuiteList;
import network.dtls.compression.DtlsCompressionMethod;
import network.dtls.packet.base.DtlsProtocolVersion;
import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeType;
import network.dtls.type.base.DtlsRandom;

public class DtlsClientHello implements DtlsFormat {

    public static final int MIN_LENGTH = 52;

    private DtlsHandshakeType handshakeType; // 1 byte
    private long length; // 3 bytes
    private int messageSequence; // 2 bytes
    private long fragmentOffset; // 3 bytes
    private long fragmentLength; // 3 bytes
    private DtlsProtocolVersion protocolVersion; // 2 bytes
    private final byte[] randomBytes = DtlsRandom.getRandom(); // 32 bytes
    private short sessionIdLength; // 1 byte
    private short cookieLength; // 1 byte
    private int cipherSuitesLength; // 2 bytes
    private DtlsCipherSuiteList dtlsCipherSuiteList; // cipherSuitesLength bytes
    private short compressionMethodsLength; // 1 byte
    private DtlsCompressionMethod compressionMethod; // 1 byte

    public DtlsClientHello(DtlsHandshakeType handshakeType, long length, int messageSequence, long fragmentOffset, long fragmentLength, DtlsProtocolVersion protocolVersion, short sessionIdLength, short cookieLength, int cipherSuitesLength, DtlsCipherSuiteList dtlsCipherSuiteList, short compressionMethodsLength, DtlsCompressionMethod compressionMethod) {
        this.handshakeType = handshakeType;
        this.length = length;
        this.messageSequence = messageSequence;
        this.fragmentOffset = fragmentOffset;
        this.fragmentLength = fragmentLength;
        this.protocolVersion = protocolVersion;
        this.sessionIdLength = sessionIdLength;
        this.cookieLength = cookieLength;
        this.cipherSuitesLength = cipherSuitesLength;
        this.dtlsCipherSuiteList = dtlsCipherSuiteList;
        this.compressionMethodsLength = compressionMethodsLength;
        this.compressionMethod = compressionMethod;
    }

    public DtlsClientHello() {}

    public DtlsClientHello(byte[] data) {
        if (data.length >= MIN_LENGTH) {
            // TODO
        }
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }

    public DtlsHandshakeType getHandshakeType() {
        return handshakeType;
    }

    public void setHandshakeType(DtlsHandshakeType handshakeType) {
        this.handshakeType = handshakeType;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getMessageSequence() {
        return messageSequence;
    }

    public void setMessageSequence(int messageSequence) {
        this.messageSequence = messageSequence;
    }

    public long getFragmentOffset() {
        return fragmentOffset;
    }

    public void setFragmentOffset(long fragmentOffset) {
        this.fragmentOffset = fragmentOffset;
    }

    public long getFragmentLength() {
        return fragmentLength;
    }

    public void setFragmentLength(long fragmentLength) {
        this.fragmentLength = fragmentLength;
    }

    public DtlsProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(DtlsProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public byte[] getRandomBytes() {
        return randomBytes;
    }

    public short getSessionIdLength() {
        return sessionIdLength;
    }

    public void setSessionIdLength(short sessionIdLength) {
        this.sessionIdLength = sessionIdLength;
    }

    public short getCookieLength() {
        return cookieLength;
    }

    public void setCookieLength(short cookieLength) {
        this.cookieLength = cookieLength;
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

    public DtlsCompressionMethod getCompressionMethod() {
        return compressionMethod;
    }

    public void setCompressionMethod(DtlsCompressionMethod compressionMethod) {
        this.compressionMethod = compressionMethod;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
