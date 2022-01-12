package network.dtls.packet.recordlayer.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.recordlayer.DtlsRecordHeader;

public class DtlsChangeCipherSpecMessage {

    private DtlsRecordHeader dtlsRecordHeader;
    private static final byte CHANGE_CIPHER_SPEC_MESSAGE = (byte) 0x01;

    public DtlsChangeCipherSpecMessage(DtlsRecordHeader dtlsRecordHeader) {
        this.dtlsRecordHeader = dtlsRecordHeader;
    }

    public DtlsChangeCipherSpecMessage() {}

    public DtlsChangeCipherSpecMessage(byte[] data) {
        // TODO
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }

    public DtlsRecordHeader getDtlsRecordHeader() {
        return dtlsRecordHeader;
    }

    public void setDtlsRecordHeader(DtlsRecordHeader dtlsRecordHeader) {
        this.dtlsRecordHeader = dtlsRecordHeader;
    }

    public byte getChangeCipherSpecMessage() {
        return CHANGE_CIPHER_SPEC_MESSAGE;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
