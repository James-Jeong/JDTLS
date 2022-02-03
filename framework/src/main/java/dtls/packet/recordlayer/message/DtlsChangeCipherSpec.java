package dtls.packet.recordlayer.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.base.DtlsRecordFactory;
import dtls.packet.recordlayer.DtlsRecordHeader;

public class DtlsChangeCipherSpec implements DtlsRecordFactory {

    ////////////////////////////////////////////////////////////
    public static final int LENGTH = DtlsRecordHeader.LENGTH + 1;

    private static final byte CHANGE_CIPHER_SPEC_MESSAGE = (byte) 0x01;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsChangeCipherSpec() {}
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        return new byte[] { CHANGE_CIPHER_SPEC_MESSAGE };
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public byte getChangeCipherSpecMessage() {
        return CHANGE_CIPHER_SPEC_MESSAGE;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    ////////////////////////////////////////////////////////////

}
