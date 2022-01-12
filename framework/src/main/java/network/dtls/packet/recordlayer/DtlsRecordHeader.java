package network.dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.base.DtlsContentType;
import network.dtls.packet.base.DtlsProtocolVersion;
import util.module.ByteUtil;

public class DtlsRecordHeader {

    public static final int LENGTH = 12;

    private DtlsContentType contentType; // 1 byte
    private DtlsProtocolVersion protocolVersion; // 1 byte
    private int epoch; // 2 bytes
    private long sequenceNumber; // 6 bytes
    private int length; // 2 bytes

    public DtlsRecordHeader(DtlsContentType contentType, DtlsProtocolVersion protocolVersion, int epoch, long sequenceNumber, int length) {
        this.contentType = contentType;
        this.protocolVersion = protocolVersion;
        this.epoch = epoch;
        this.sequenceNumber = sequenceNumber;
        this.length = length;
    }

    public DtlsRecordHeader() {}

    public DtlsRecordHeader(byte[] data) {
        if (data.length == LENGTH) {
            int index = 0;

            byte[] contentTypeData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, contentTypeData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] contentTypeData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(contentTypeData, 0, contentTypeData2, ByteUtil.NUM_BYTES_IN_INT - ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            contentType = new DtlsContentType(ByteUtil.bytesToInt(contentTypeData2, true));
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            byte[] protocolVersionData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            byte[] epochData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] sequenceNumberData = new byte[ByteUtil.NUM_BYTES_IN_INT];
            index += ByteUtil.NUM_BYTES_IN_INT;

            byte[] lengthData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];

        }
    }

    public byte[] getData() {
        if (contentType == null || protocolVersion == null) {
            return null;
        }

        int index = 0;
        byte[] data = new byte[LENGTH];

        byte[] contentTypeData = ByteUtil.intToBytes(contentType.getType(), true);

        byte[] protocolVersionData = protocolVersion.getVersion();

        byte[] epochData = ByteUtil.intToBytes(epoch, true);

        byte[] sequenceNumberData = ByteUtil.longToBytes(sequenceNumber, true);

        byte[] lengthData = ByteUtil.intToBytes(length, true);

        return data;
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
