package dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DtlsEncryptedHandShake implements DtlsHandshakeFactory {

    ////////////////////////////////////////////////////////////
    private byte[] encryptedMessage;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsEncryptedHandShake() {}

    public DtlsEncryptedHandShake(byte[] data) {
        if (data.length > 0) {
            encryptedMessage = new byte[data.length];
            System.arraycopy(data, 0, encryptedMessage, 0, data.length);
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (encryptedMessage == null) { return null; }

        byte[] data = new byte[encryptedMessage.length];
        System.arraycopy(encryptedMessage, 0, data, 0, encryptedMessage.length);

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    ////////////////////////////////////////////////////////////

}
