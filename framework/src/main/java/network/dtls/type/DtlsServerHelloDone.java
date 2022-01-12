package network.dtls.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.type.base.DtlsHandshakeType;

public class DtlsServerHelloDone {

    public static final int LENGTH = 12;

    private DtlsHandshakeType handshakeType; // 1 byte
    private long length; // 3 bytes
    private int messageSequence; // 2 bytes
    private long fragmentOffset; // 3 bytes
    private long fragmentLength; // 3 bytes

    public DtlsServerHelloDone(DtlsHandshakeType handshakeType, long length, int messageSequence, long fragmentOffset, long fragmentLength) {
        this.handshakeType = handshakeType;
        this.length = length;
        this.messageSequence = messageSequence;
        this.fragmentOffset = fragmentOffset;
        this.fragmentLength = fragmentLength;
    }

    public DtlsServerHelloDone() {}

    public DtlsServerHelloDone(byte[] data) {
        // TODO
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

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
