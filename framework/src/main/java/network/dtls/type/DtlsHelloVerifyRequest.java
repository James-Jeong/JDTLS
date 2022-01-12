package network.dtls.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.base.DtlsProtocolVersion;
import network.dtls.type.base.DtlsHandshakeType;

public class DtlsHelloVerifyRequest {

    public static final int LENGTH = 15;

    private DtlsHandshakeType handshakeType;        // 1 byte
    private long length;                            // 3 bytes
    private int messageSequence;                    // 2 bytes
    private long fragmentOffset;                    // 3 bytes
    private long fragmentLength;                    // 3 bytes
    private DtlsProtocolVersion protocolVersion;    // 2 bytes
    private short cookieLength;                     // 1 byte

    public DtlsHelloVerifyRequest(DtlsHandshakeType handshakeType, long length, int messageSequence, long fragmentOffset, long fragmentLength, DtlsProtocolVersion protocolVersion, short cookieLength) {
        this.handshakeType = handshakeType;
        this.length = length;
        this.messageSequence = messageSequence;
        this.fragmentOffset = fragmentOffset;
        this.fragmentLength = fragmentLength;
        this.protocolVersion = protocolVersion;
        this.cookieLength = cookieLength;
    }

    public DtlsHelloVerifyRequest() {}

    public DtlsHelloVerifyRequest(byte[] data) {
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

    public short getCookieLength() {
        return cookieLength;
    }

    public void setCookieLength(short cookieLength) {
        this.cookieLength = cookieLength;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
