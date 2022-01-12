package network.dtls.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.cipher.DtlsCipherSuite;
import network.dtls.compression.DtlsCompressionMethod;
import network.dtls.packet.base.DtlsProtocolVersion;
import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeType;
import network.dtls.type.base.DtlsRandom;

public class DtlsServerHello implements DtlsFormat {

    public static final int LENGTH = 82;

    private DtlsHandshakeType handshakeType; // 1 byte
    private long length; // 3 bytes
    private int messageSequence; // 2 bytes
    private long fragmentOffset; // 3 bytes
    private long fragmentLength; // 3 bytes
    private DtlsProtocolVersion protocolVersion; // 2 bytes
    private final byte[] randomBytes = DtlsRandom.getRandom(); // 32 bytes
    private short sessionIdLength; // 1 byte
    private final byte[] sessionIdBytes = new byte[32]; // 32 bytes
    private DtlsCipherSuite cipherSuite; // 2 bytes
    private DtlsCompressionMethod dtlsCompressionMethod; // 1 byte

    public DtlsServerHello(DtlsHandshakeType handshakeType, long length, int messageSequence, long fragmentOffset, long fragmentLength, DtlsProtocolVersion protocolVersion, short sessionIdLength, DtlsCipherSuite cipherSuite, DtlsCompressionMethod dtlsCompressionMethod) {
        this.handshakeType = handshakeType;
        this.length = length;
        this.messageSequence = messageSequence;
        this.fragmentOffset = fragmentOffset;
        this.fragmentLength = fragmentLength;
        this.protocolVersion = protocolVersion;
        this.sessionIdLength = sessionIdLength;
        this.cipherSuite = cipherSuite;
        this.dtlsCompressionMethod = dtlsCompressionMethod;
    }

    public DtlsServerHello() {}

    public DtlsServerHello(byte[] data) {
        if (data.length == LENGTH) {
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

    public byte[] getSessionIdBytes() {
        return sessionIdBytes;
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

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
