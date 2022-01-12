package network.dtls.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.handshake.DtlsHandshakeHeader;

public class DtlsApplicationData {

    private DtlsHandshakeHeader dtlsHandshakeHeader;
    private byte[] encryptedAppliationData;

    public DtlsApplicationData(DtlsHandshakeHeader dtlsHandshakeHeader, byte[] encryptedAppliationData) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
        this.encryptedAppliationData = encryptedAppliationData;
    }

    public DtlsApplicationData() {}

    public DtlsApplicationData(byte[] data) {
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

    public byte[] getEncryptedAppliationData() {
        return encryptedAppliationData;
    }

    public void setEncryptedAppliationData(byte[] encryptedAppliationData) {
        this.encryptedAppliationData = encryptedAppliationData;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
