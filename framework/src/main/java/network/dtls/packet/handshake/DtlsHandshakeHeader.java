package network.dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.packet.base.DtlsContentType;
import network.dtls.packet.base.DtlsProtocolVersion;
import util.module.ByteUtil;

public class DtlsHandshakeHeader {

    public static final int LENGTH = 13;

    private DtlsContentType contentType; // 1 byte
    private DtlsProtocolVersion protocolVersion; // 2 bytes
    private int epoch; // 2 bytes
    private long sequenceNumber; // 6 bytes
    private int length; // 2 bytes

    public DtlsHandshakeHeader(DtlsContentType contentType, DtlsProtocolVersion protocolVersion, int epoch, long sequenceNumber, int length) {
        this.contentType = contentType;
        this.protocolVersion = protocolVersion;
        this.epoch = epoch;
        this.sequenceNumber = sequenceNumber;
        this.length = length;
    }

    public DtlsHandshakeHeader() {}

    public DtlsHandshakeHeader(byte[] data) {
        if (data.length == LENGTH) {
            int index = 0;

            byte[] contentTypeData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, contentTypeData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] contentTypeData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(contentTypeData, 0, contentTypeData2, ByteUtil.NUM_BYTES_IN_INT - ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            contentType = new DtlsContentType(ByteUtil.bytesToInt(contentTypeData2, true));
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            byte[] protocolVersionData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, protocolVersionData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            protocolVersion = new DtlsProtocolVersion(protocolVersionData);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] epochData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, epochData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            byte[] epochData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(epochData, 0, epochData2, ByteUtil.NUM_BYTES_IN_SHORT, ByteUtil.NUM_BYTES_IN_SHORT);
            epoch = ByteUtil.bytesToInt(epochData2, true);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] sequenceNumberData = new byte[6];
            System.arraycopy(data, index, sequenceNumberData, 0, 6);
            byte[] sequenceNumberData2 = new byte[ByteUtil.NUM_BYTES_IN_LONG];
            System.arraycopy(sequenceNumberData, 0, sequenceNumberData2, ByteUtil.NUM_BYTES_IN_INT - 2, 6);
            sequenceNumber = ByteUtil.bytesToLong(sequenceNumberData2, true);
            index += 6;

            byte[] lengthData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, lengthData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            byte[] lengthData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(lengthData, 0, lengthData2, ByteUtil.NUM_BYTES_IN_SHORT, ByteUtil.NUM_BYTES_IN_SHORT);
            length = ByteUtil.bytesToInt(lengthData2, true);
        }
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