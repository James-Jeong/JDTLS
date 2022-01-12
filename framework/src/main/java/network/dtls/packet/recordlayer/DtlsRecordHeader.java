package network.dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.base.DtlsContentType;
import network.dtls.packet.base.DtlsProtocolVersion;

public class DtlsRecordHeader {

    private DtlsContentType contentType;
    private DtlsProtocolVersion protocolVersion;
    private int epoch;
    private long sequenceNumber;
    private int length;

    public DtlsRecordHeader(DtlsContentType contentType, DtlsProtocolVersion protocolVersion, int epoch, long sequenceNumber, int length) {
        this.contentType = contentType;
        this.protocolVersion = protocolVersion;
        this.epoch = epoch;
        this.sequenceNumber = sequenceNumber;
        this.length = length;
    }

    public DtlsRecordHeader() {}

    public DtlsRecordHeader(byte[] data) {
        // TODO
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }

    public DtlsContentType getContentType() {
        return contentType;
    }

    public void setContentType(DtlsContentType contentType) {
        this.contentType = contentType;
    }

    public DtlsProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(DtlsProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
