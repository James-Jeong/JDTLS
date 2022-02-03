package dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.base.DtlsRecordFactory;

public class DtlsEncryptedHandShake implements DtlsRecordFactory {

    ////////////////////////////////////////////////////////////
    transient private byte[] encryptedMessage = null;
    private int encryptedMessageLength = 0;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsEncryptedHandShake() {}

    public DtlsEncryptedHandShake(byte[] data) {
        if (data.length > 0) {
            encryptedMessage = new byte[data.length];
            System.arraycopy(data, 0, encryptedMessage, 0, data.length);
            encryptedMessageLength = encryptedMessage.length;
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
    public byte[] getEncryptedMessage() {
        return encryptedMessage;
    }

    public void setEncryptedMessage(byte[] encryptedMessage) {
        this.encryptedMessage = encryptedMessage;
    }

    public int getEncryptedMessageLength() {
        return encryptedMessageLength;
    }

    public void setEncryptedMessageLength(int encryptedMessageLength) {
        this.encryptedMessageLength = encryptedMessageLength;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    ////////////////////////////////////////////////////////////

}
