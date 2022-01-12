package network.dtls.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.handshake.DtlsHandShake;

import java.util.ArrayList;
import java.util.List;

public class DtlsPacket {

    private List<DtlsHandShake> dtlsHandShakeList = new ArrayList<>();
    private DtlsChangeCipherSpecMessage dtlsChangeCipherSpecMessage;

    public DtlsPacket(List<DtlsHandShake> dtlsHandShakeList, DtlsChangeCipherSpecMessage dtlsChangeCipherSpecMessage) {
        this.dtlsHandShakeList = dtlsHandShakeList;
        this.dtlsChangeCipherSpecMessage = dtlsChangeCipherSpecMessage;
    }

    public DtlsPacket(List<DtlsHandShake> dtlsHandShakeList) {
        this.dtlsHandShakeList = dtlsHandShakeList;
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

    public List<DtlsHandShake> getDtlsHandShakeList() {
        return dtlsHandShakeList;
    }

    public void setDtlsHandShakeList(List<DtlsHandShake> dtlsHandShakeList) {
        this.dtlsHandShakeList = dtlsHandShakeList;
    }

    public DtlsChangeCipherSpecMessage getDtlsChangeCipherSpecMessage() {
        return dtlsChangeCipherSpecMessage;
    }

    public void setDtlsChangeCipherSpecMessage(DtlsChangeCipherSpecMessage dtlsChangeCipherSpecMessage) {
        this.dtlsChangeCipherSpecMessage = dtlsChangeCipherSpecMessage;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
