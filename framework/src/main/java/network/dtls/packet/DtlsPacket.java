package network.dtls.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.recordlayer.DtlsRecordLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Single or Compound 가능
 */
public class DtlsPacket {

    private List<DtlsRecordLayer> dtlsRecordLayerList = new ArrayList<>();

    public DtlsPacket(List<DtlsRecordLayer> dtlsHandShakeList) {
        this.dtlsRecordLayerList = dtlsHandShakeList;
    }

    public DtlsPacket() {}

    public DtlsPacket(byte[] data) {
        // TODO
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }

    public List<DtlsRecordLayer> getDtlsRecordLayerList() {
        return dtlsRecordLayerList;
    }

    public void setDtlsRecordLayerList(List<DtlsRecordLayer> dtlsRecordLayerList) {
        this.dtlsRecordLayerList = dtlsRecordLayerList;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
