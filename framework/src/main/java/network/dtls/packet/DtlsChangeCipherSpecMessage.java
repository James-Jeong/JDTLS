package network.dtls.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.handshake.DtlsHandshakeHeader;
import network.dtls.packet.recordlayer.DtlsRecordLayer;

public class DtlsChangeCipherSpecMessage {

    private DtlsHandshakeHeader dtlsHandshakeHeader;
    private static final byte CHANGE_CIPHER_SPEC_MESSAGE = (byte) 0x01;
    private DtlsRecordLayer dtlsRecordLayer;

    public DtlsChangeCipherSpecMessage(DtlsHandshakeHeader dtlsHandshakeHeader, DtlsRecordLayer dtlsRecordLayer) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
        this.dtlsRecordLayer = dtlsRecordLayer;
    }

    public DtlsChangeCipherSpecMessage() {}

    public DtlsChangeCipherSpecMessage(byte[] data) {
        // TODO
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }

    public DtlsHandshakeHeader getDtlsHandshakeHeader() {
        return dtlsHandshakeHeader;
    }

    public void setDtlsHandshakeHeader(DtlsHandshakeHeader dtlsHandshakeHeader) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
    }

    public byte getChangeCipherSpecMessage() {
        return CHANGE_CIPHER_SPEC_MESSAGE;
    }

    public DtlsRecordLayer getDtlsRecordLayer() {
        return dtlsRecordLayer;
    }

    public void setDtlsRecordLayer(DtlsRecordLayer dtlsRecordLayer) {
        this.dtlsRecordLayer = dtlsRecordLayer;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
