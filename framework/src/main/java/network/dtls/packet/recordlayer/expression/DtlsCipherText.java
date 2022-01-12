package network.dtls.packet.recordlayer.expression;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.base.DtlsCipherType;
import network.dtls.packet.recordlayer.DtlsRecordHeader;

public class DtlsCipherText {

    private DtlsRecordHeader recordHeader;
    private final DtlsCipherType cipherType = DtlsCipherType.GENERIC_BLOCK_CIPHER;

    public DtlsCipherText(DtlsRecordHeader recordHeader) {
        this.recordHeader = recordHeader;
    }

    public DtlsCipherText() {}

    public DtlsCipherText(byte[] data) {
        // TODO
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }

    public DtlsRecordHeader getRecordHeader() {
        return recordHeader;
    }

    public void setRecordHeader(DtlsRecordHeader recordHeader) {
        this.recordHeader = recordHeader;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
