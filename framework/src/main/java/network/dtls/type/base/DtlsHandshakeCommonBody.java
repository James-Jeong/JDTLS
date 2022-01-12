package network.dtls.type.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.module.ByteUtil;

public class DtlsHandshakeCommonBody {

    public static final int LENGTH = 12;

    private DtlsHandshakeType handshakeType; // 1 byte
    private long length; // 3 bytes
    private int messageSequence; // 2 bytes
    private long fragmentOffset; // 3 bytes
    private long fragmentLength; // 3 bytes

    public DtlsHandshakeCommonBody(DtlsHandshakeType handshakeType,
                                   long length, int messageSequence,
                                   long fragmentOffset, long fragmentLength) {
        this.handshakeType = handshakeType;
        this.length = length;
        this.messageSequence = messageSequence;
        this.fragmentOffset = fragmentOffset;
        this.fragmentLength = fragmentLength;
    }

    public DtlsHandshakeCommonBody() {}

    public DtlsHandshakeCommonBody(byte[] data) {
        if (data.length == LENGTH) {
            int index = 0;

            byte[] handshakeTypeData = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
            System.arraycopy(data, index, handshakeTypeData, 0, ByteUtil.NUM_BYTES_IN_BYTE);
            byte[] handshakeTypeData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(handshakeTypeData, 0, handshakeTypeData2, ByteUtil.NUM_BYTES_IN_INT - ByteUtil.NUM_BYTES_IN_BYTE, ByteUtil.NUM_BYTES_IN_BYTE);
            handshakeType = new DtlsHandshakeType(ByteUtil.bytesToInt(handshakeTypeData2, true));
            index += ByteUtil.NUM_BYTES_IN_BYTE;

            byte[] lengthData = new byte[3];
            System.arraycopy(data, index, lengthData, 0, 3);
            byte[] lengthData2 = new byte[ByteUtil.NUM_BYTES_IN_LONG];
            System.arraycopy(lengthData, 0, lengthData2,ByteUtil.NUM_BYTES_IN_LONG - 3, 3);
            length = ByteUtil.bytesToLong(lengthData2, true);
            index += 3;

            byte[] messageSequenceData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, messageSequenceData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            byte[] messageSequenceData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(messageSequenceData, 0, messageSequenceData2, ByteUtil.NUM_BYTES_IN_SHORT, ByteUtil.NUM_BYTES_IN_SHORT);
            messageSequence = ByteUtil.bytesToInt(messageSequenceData2, true);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            byte[] fragmentOffsetData = new byte[3];
            System.arraycopy(data, index, fragmentOffsetData, 0, 3);
            byte[] fragmentOffsetData2 = new byte[ByteUtil.NUM_BYTES_IN_LONG];
            System.arraycopy(fragmentOffsetData, 0, fragmentOffsetData2,ByteUtil.NUM_BYTES_IN_LONG - 3, 3);
            fragmentOffset = ByteUtil.bytesToLong(fragmentOffsetData2, true);
            index += 3;

            byte[] fragmentLengthData = new byte[3];
            System.arraycopy(data, index, fragmentLengthData, 0, 3);
            byte[] fragmentLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_LONG];
            System.arraycopy(fragmentLengthData, 0, fragmentLengthData2,ByteUtil.NUM_BYTES_IN_LONG - 3, 3);
            fragmentLength = ByteUtil.bytesToLong(fragmentLengthData2, true);
        }
    }

    public byte[] getData() {
        int index = 0;
        byte[] data = new byte[LENGTH];

        byte[] handshakeTypeData = ByteUtil.intToBytes(handshakeType.getType(), true);
        byte[] handshakeTypeData2 = new byte[ByteUtil.NUM_BYTES_IN_BYTE];
        System.arraycopy(handshakeTypeData, ByteUtil.NUM_BYTES_IN_INT - ByteUtil.NUM_BYTES_IN_BYTE, handshakeTypeData2, 0, ByteUtil.NUM_BYTES_IN_BYTE);
        System.arraycopy(handshakeTypeData2, 0, data, index, ByteUtil.NUM_BYTES_IN_BYTE);
        index += ByteUtil.NUM_BYTES_IN_BYTE;

        byte[] lengthData = ByteUtil.longToBytes(length, true);
        byte[] lengthData2 = new byte[3];
        System.arraycopy(lengthData, ByteUtil.NUM_BYTES_IN_LONG - 3, lengthData2, 0, 3);
        System.arraycopy(lengthData2, 0, data, index, 3);
        index += 3;

        byte[] messageSequenceData = ByteUtil.intToBytes(messageSequence, true);
        byte[] messageSequenceData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
        System.arraycopy(messageSequenceData, ByteUtil.NUM_BYTES_IN_SHORT, messageSequenceData2, 0, ByteUtil.NUM_BYTES_IN_SHORT);
        System.arraycopy(messageSequenceData2, 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        byte[] fragmentOffsetData = ByteUtil.longToBytes(fragmentOffset, true);
        byte[] fragmentOffsetData2 = new byte[3];
        System.arraycopy(fragmentOffsetData, ByteUtil.NUM_BYTES_IN_LONG - 3, fragmentOffsetData2, 0, 3);
        System.arraycopy(fragmentOffsetData2, 0, data, index, 3);
        index += 3;

        byte[] fragmentLengthData = ByteUtil.longToBytes(fragmentLength, true);
        byte[] fragmentLengthData2 = new byte[3];
        System.arraycopy(fragmentLengthData, ByteUtil.NUM_BYTES_IN_LONG - 3, fragmentLengthData2, 0, 3);
        System.arraycopy(fragmentLengthData2, 0, data, index, 3);

        return data;
    }

    public DtlsHandshakeType getHandshakeType() {
        return handshakeType;
    }

    public void setHandshakeType(DtlsHandshakeType handshakeType) {
        this.handshakeType = handshakeType;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getMessageSequence() {
        return messageSequence;
    }

    public void setMessageSequence(int messageSequence) {
        this.messageSequence = messageSequence;
    }

    public long getFragmentOffset() {
        return fragmentOffset;
    }

    public void setFragmentOffset(long fragmentOffset) {
        this.fragmentOffset = fragmentOffset;
    }

    public long getFragmentLength() {
        return fragmentLength;
    }

    public void setFragmentLength(long fragmentLength) {
        this.fragmentLength = fragmentLength;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
