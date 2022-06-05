package dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.base.DtlsContentType;
import dtls.packet.base.DtlsProtocolVersion;
import util.module.ByteUtil;

public class DtlsRecordHeader {

    ////////////////////////////////////////////////////////////
    public static final int LENGTH = 13;

    // DTLS Record type (message type)
    private DtlsContentType contentType = null; // 1 byte

    // DTLS Protocol version
    private DtlsProtocolVersion protocolVersion = null; // 2 byte

    // Record layer 암호화 여부 (0: not, 1>: encrypted)
    // - The epoch number is initially zero and is incremented each time keying material changes and a sender aims to rekey.
    private int epoch = 0; // 2 bytes

    // Packet 시퀀스 번호
    private long sequenceNumber = 0; // 6 bytes

    // Record layer 총 바이트 수
    private int length = 0; // 2 bytes
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsRecordHeader(DtlsContentType contentType, DtlsProtocolVersion protocolVersion, int epoch, long sequenceNumber, int length) {
        this.contentType = contentType;
        this.protocolVersion = protocolVersion;
        this.epoch = epoch;
        this.sequenceNumber = sequenceNumber;
        this.length = length;
    }

    public DtlsRecordHeader() {}

    public DtlsRecordHeader(byte[] data) {
        if (data != null && data.length == LENGTH) {
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
            System.arraycopy(sequenceNumberData, 0, sequenceNumberData2, ByteUtil.NUM_BYTES_IN_LONG - 6, 6);
            sequenceNumber = ByteUtil.bytesToLong(sequenceNumberData2, true);
            index += 6;

            byte[] lengthData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, lengthData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            byte[] lengthData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(lengthData, 0, lengthData2, ByteUtil.NUM_BYTES_IN_SHORT, ByteUtil.NUM_BYTES_IN_SHORT);
            length = ByteUtil.bytesToInt(lengthData2, true);
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public byte[] getData() {
        int index = 0;
        byte[] data = new byte[LENGTH];

        byte[] contentTypeData = ByteUtil.intToBytes(contentType.getType(), true);
        byte[] contentTypeData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(contentTypeData, ByteUtil.NUM_BYTES_IN_INT - ByteUtil.NUM_BYTES_IN_BYTE, contentTypeData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(contentTypeData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        index += ByteUtil.NUM_BYTES_IN_BYTE;

        byte[] protocolVersionData = protocolVersion.getVersion();
        System.arraycopy(protocolVersionData, 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        byte[] epochData = ByteUtil.intToBytes(epoch, true);
        byte[] epochData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
        System.arraycopy(epochData, ByteUtil.NUM_BYTES_IN_SHORT, epochData2, 0, ByteUtil.NUM_BYTES_IN_SHORT);
        System.arraycopy(epochData2, 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        byte[] sequenceNumberData = ByteUtil.longToBytes(sequenceNumber, true);
        byte[] sequenceNumberData2 = new byte[6];
        System.arraycopy(sequenceNumberData, ByteUtil.NUM_BYTES_IN_LONG - 6, sequenceNumberData2, 0, 6);
        System.arraycopy(sequenceNumberData2, 0, data, index, 6);
        index += 6;

        byte[] lengthData = ByteUtil.intToBytes(length, true);
        byte[] lengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
        System.arraycopy(lengthData, ByteUtil.NUM_BYTES_IN_SHORT, lengthData2, 0, ByteUtil.NUM_BYTES_IN_SHORT);
        System.arraycopy(lengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////

}
