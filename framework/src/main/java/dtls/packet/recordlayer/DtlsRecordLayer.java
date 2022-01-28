package dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.handshake.DtlsHandshakeFactory;

public class DtlsRecordLayer {

    private DtlsRecordHeader recordHeader;
    private DtlsHandshakeFactory dtlsHandshakeFactory;

    public DtlsRecordLayer(DtlsRecordHeader recordHeader, DtlsHandshakeFactory dtlsHandshakeFactory) {
        this.recordHeader = recordHeader;
        this.dtlsHandshakeFactory = dtlsHandshakeFactory;
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

    public DtlsHandshakeFactory getDtlsHandshakeFactory() {
        return dtlsHandshakeFactory;
    }

    public void setDtlsHandshakeFactory(DtlsHandshakeFactory dtlsHandshakeFactory) {
        this.dtlsHandshakeFactory = dtlsHandshakeFactory;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
