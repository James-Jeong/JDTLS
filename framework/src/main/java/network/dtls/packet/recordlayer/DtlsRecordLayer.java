package network.dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.handshake.DtlsHandShake;

public class DtlsRecordLayer {

    private DtlsRecordHeader recordHeader;
    private DtlsHandShake dtlsHandShake;

    public DtlsRecordLayer(DtlsRecordHeader recordHeader, DtlsHandShake dtlsHandShake) {
        this.recordHeader = recordHeader;
        this.dtlsHandShake = dtlsHandShake;
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

    public DtlsHandShake getDtlsHandShake() {
        return dtlsHandShake;
    }

    public void setDtlsHandShake(DtlsHandShake dtlsHandShake) {
        this.dtlsHandShake = dtlsHandShake;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
