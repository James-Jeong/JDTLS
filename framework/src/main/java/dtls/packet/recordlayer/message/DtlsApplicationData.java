package dtls.packet.recordlayer.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.base.DtlsRecordFactory;

public class DtlsApplicationData implements DtlsRecordFactory {

    ////////////////////////////////////////////////////////////
    private byte[] encryptedApplicationData;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsApplicationData() {}

    public DtlsApplicationData(byte[] data) {
        if (data != null && data.length > 0) {
            encryptedApplicationData = new byte[data.length];
            System.arraycopy(data, 0, encryptedApplicationData, 0, data.length);
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (encryptedApplicationData == null) { return null; }

        byte[] data = new byte[encryptedApplicationData.length];
        System.arraycopy(encryptedApplicationData, 0, data, 0, encryptedApplicationData.length);

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////

}
