package network.dtls.packet.recordlayer.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.handshake.DtlsHandshakeHeader;
import network.dtls.packet.recordlayer.DtlsRecordHeader;

public class DtlsApplicationData {

    private DtlsRecordHeader dtlsRecordHeader;
    private byte[] encryptedApplicationData;

    public DtlsApplicationData(DtlsRecordHeader dtlsRecordHeader, byte[] encryptedAppliationData) {
        this.dtlsRecordHeader = dtlsRecordHeader;
        this.encryptedApplicationData = encryptedAppliationData;
    }

    public DtlsApplicationData() {}

    public DtlsApplicationData(byte[] data) {
        if (data.length >= DtlsHandshakeHeader.LENGTH) {
            int index = 0;

            byte[] dtlsHandshakeHeaderData = new byte[DtlsHandshakeHeader.LENGTH];
            System.arraycopy(data, index, dtlsHandshakeHeaderData, 0, DtlsHandshakeHeader.LENGTH);
            dtlsRecordHeader = new DtlsRecordHeader(dtlsHandshakeHeaderData);
            index += DtlsHandshakeHeader.LENGTH;

            int remainLength = data.length - DtlsHandshakeHeader.LENGTH;
            encryptedApplicationData = new byte[remainLength];
            System.arraycopy(data, index, encryptedApplicationData, 0, remainLength);
        }
    }

    public byte[] getData() {
        byte[] data;
        byte[] dtlsHandshakeHeaderData = dtlsRecordHeader.getData();

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

    public DtlsRecordHeader getDtlsHandshakeHeader() {
        return dtlsRecordHeader;
    }

    public void setDtlsHandshakeHeader(DtlsRecordHeader dtlsRecordHeader) {
        this.dtlsRecordHeader = dtlsRecordHeader;
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
