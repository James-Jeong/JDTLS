package network.dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DtlsRecordLayer {

    private DtlsRecordHeader recordHeader;
    private byte[] handshakeProtocol;

    public DtlsRecordLayer(DtlsRecordHeader recordHeader, byte[] handshakeProtocol) {
        this.recordHeader = recordHeader;
        this.handshakeProtocol = handshakeProtocol;
    }

    public DtlsRecordLayer() {}

    public DtlsRecordLayer(byte[] data) {
        // TODO
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }

    public DtlsRecordHeader getRecordHeader() {
        return recordHeader;
    }

    public void setRecordHeader(DtlsRecordHeader recordHeader) {
        this.recordHeader = recordHeader;
    }

    public byte[] getHandshakeProtocol() {
        return handshakeProtocol;
    }

    public void setHandshakeProtocol(byte[] handshakeProtocol) {
        this.handshakeProtocol = handshakeProtocol;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
