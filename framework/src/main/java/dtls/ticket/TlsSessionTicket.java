package dtls.ticket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.module.ByteUtil;

public class TlsSessionTicket {

    public static final int MIN_LENGTH = 6;

    private long lifeTimeHint = 0; // 4 bytes, sec
    private int length = 0; // 2 bytes
    private byte[] ticket = null; // length bytes

    public TlsSessionTicket(long lifeTimeHint, int length, byte[] ticket) {
        this.lifeTimeHint = lifeTimeHint;
        this.length = length;
        this.ticket = ticket;
    }

    public TlsSessionTicket() {}

    public TlsSessionTicket(byte[] data) {
        if (data.length >= MIN_LENGTH) {
            int index = 0;

            byte[] lifeTimeHintData = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(data, index, lifeTimeHintData, 0, ByteUtil.NUM_BYTES_IN_INT);
            byte[] lifeTimeHintData2 = new byte[ByteUtil.NUM_BYTES_IN_LONG];
            System.arraycopy(lifeTimeHintData, 0, lifeTimeHintData2, ByteUtil.NUM_BYTES_IN_INT, ByteUtil.NUM_BYTES_IN_INT);
            lifeTimeHint = ByteUtil.bytesToLong(lifeTimeHintData2, true);
            index += ByteUtil.NUM_BYTES_IN_INT;

            byte[] lengthData = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
            System.arraycopy(data, index, lengthData, 0, ByteUtil.NUM_BYTES_IN_SHORT);
            byte[] lengthData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
            System.arraycopy(lengthData, 0, lengthData2, ByteUtil.NUM_BYTES_IN_SHORT, ByteUtil.NUM_BYTES_IN_SHORT);
            length = ByteUtil.bytesToInt(lengthData2, true);
            index += ByteUtil.NUM_BYTES_IN_SHORT;

            if (length > 0) {
                ticket = new byte[length];
                System.arraycopy(data, index, ticket, 0, length);
            }
        }
    }

    public byte[] getData() {
        int index = 0;
        byte[] data = new byte[MIN_LENGTH];

        byte[] lifeTimeHintData = ByteUtil.longToBytes(lifeTimeHint, true);
        byte[] lifeTimeHintData2 = new byte[ByteUtil.NUM_BYTES_IN_INT];
        System.arraycopy(lifeTimeHintData, ByteUtil.NUM_BYTES_IN_INT, lifeTimeHintData2, 0, ByteUtil.NUM_BYTES_IN_INT);
        System.arraycopy(lifeTimeHintData2, 0, data, index, ByteUtil.NUM_BYTES_IN_INT);
        index += ByteUtil.NUM_BYTES_IN_INT;

        byte[] lengthData = ByteUtil.intToBytes(length, true);
        byte[] lengthData2 = new byte[ByteUtil.NUM_BYTES_IN_SHORT];
        System.arraycopy(lengthData, ByteUtil.NUM_BYTES_IN_SHORT, lengthData2, 0, ByteUtil.NUM_BYTES_IN_SHORT);
        System.arraycopy(lengthData2, 0, data, index, ByteUtil.NUM_BYTES_IN_SHORT);
        index += ByteUtil.NUM_BYTES_IN_SHORT;

        if (length > 0 && ticket != null && ticket.length > 0) {
            byte[] newData = new byte[MIN_LENGTH + length];
            System.arraycopy(data, 0, newData, 0, MIN_LENGTH);
            data = newData;
            System.arraycopy(ticket, 0, data, index, length);
        }

        return data;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
