package network.dtls.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.handshake.DtlsHandshakeHeader;

public class DtlsApplicationData {

    private DtlsHandshakeHeader dtlsHandshakeHeader;
    private byte[] encryptedApplicationData;

    public DtlsApplicationData(DtlsHandshakeHeader dtlsHandshakeHeader, byte[] encryptedAppliationData) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
        this.encryptedApplicationData = encryptedAppliationData;
    }

    public DtlsApplicationData() {}

    public DtlsApplicationData(byte[] data) {
        if (data.length >= DtlsHandshakeHeader.LENGTH) {
            int index = 0;

            byte[] dtlsHandshakeHeaderData = new byte[DtlsHandshakeHeader.LENGTH];
            System.arraycopy(data, index, dtlsHandshakeHeaderData, 0, DtlsHandshakeHeader.LENGTH);
            dtlsHandshakeHeader = new DtlsHandshakeHeader(dtlsHandshakeHeaderData);
            index += DtlsHandshakeHeader.LENGTH;

            int remainLength = data.length - DtlsHandshakeHeader.LENGTH;
            encryptedApplicationData = new byte[remainLength];
            System.arraycopy(data, index, encryptedApplicationData, 0, remainLength);
        }
    }

    public byte[] getData() {
        byte[] data;
        byte[] dtlsHandshakeHeaderData = dtlsHandshakeHeader.getData();

        if (encryptedApplicationData != null && encryptedApplicationData.length > 0) {
            int index = 0;
            data = new byte[dtlsHandshakeHeaderData.length + encryptedApplicationData.length];
            System.arraycopy(dtlsHandshakeHeaderData, 0, data, index, dtlsHandshakeHeaderData.length);
            index += dtlsHandshakeHeaderData.length;
            System.arraycopy(encryptedApplicationData, 0, data, index, encryptedApplicationData.length);
        } else {
            data = new byte[dtlsHandshakeHeaderData.length];
            System.arraycopy(dtlsHandshakeHeaderData, 0, data, 0, dtlsHandshakeHeaderData.length);
        }

        return data;
    }

    public DtlsHandshakeHeader getDtlsHandshakeHeader() {
        return dtlsHandshakeHeader;
    }

    public void setDtlsHandshakeHeader(DtlsHandshakeHeader dtlsHandshakeHeader) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
    }

    public byte[] getEncryptedApplicationData() {
        return encryptedApplicationData;
    }

    public void setEncryptedApplicationData(byte[] encryptedApplicationData) {
        this.encryptedApplicationData = encryptedApplicationData;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
