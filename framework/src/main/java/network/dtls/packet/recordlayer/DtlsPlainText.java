package network.dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DtlsPlainText {

    private DtlsRecordHeader recordHeader;
    private byte[] fragment = null;

    public DtlsPlainText(DtlsRecordHeader recordHeader, byte[] fragment) {
        this.recordHeader = recordHeader;
        this.fragment = fragment;
    }

    public DtlsPlainText(DtlsRecordHeader recordHeader) {
        this.recordHeader = recordHeader;
    }

    public DtlsPlainText() {}

    public DtlsPlainText(byte[] data) {
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

    public byte[] getFragment() {
        return fragment;
    }

    public void setFragment(byte[] fragment) {
        this.fragment = fragment;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
