package network.dtls.certificate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import util.module.ByteUtil;

public class Certificates {

    private long length; // 3 bytes
    private byte[] certificate; // length bytes

    public Certificates(long length, byte[] certificate) {
        this.length = length;
        this.certificate = certificate;
    }

    public Certificates() {}

    public Certificates(byte[] data) {
        if (data.length > 0) {
            int index = 0;

            byte[] lengthData = new byte[3];
            System.arraycopy(data, index, lengthData, 0, 3);
            byte[] lengthData2 = new byte[ByteUtil.NUM_BYTES_IN_LONG];
            System.arraycopy(lengthData, 0, lengthData2,ByteUtil.NUM_BYTES_IN_LONG - 3, 3);
            length = ByteUtil.bytesToLong(lengthData2, true);
            index += 3;

            if (length > 0) {
                certificate = new byte[(int) length];
                System.arraycopy(data, index, certificate, 0, (int) length);
            }
        }
    }

    public byte[] getData() {
        int index = 0;
        byte[] data;

        if (length > 0) {
            data = new byte[3 + certificate.length];

            byte[] lengthData = ByteUtil.longToBytes(length, true);
            byte[] lengthData2 = new byte[3];
            System.arraycopy(lengthData, ByteUtil.NUM_BYTES_IN_LONG - 3, lengthData2, 0, 3);
            System.arraycopy(lengthData2, 0, data, index, 3);
            index += 3;

            System.arraycopy(certificate, 0, data, index, certificate.length);
        } else {
            data = new byte[3];

            byte[] lengthData = ByteUtil.longToBytes(length, true);
            byte[] lengthData2 = new byte[3];
            System.arraycopy(lengthData, ByteUtil.NUM_BYTES_IN_LONG - 3, lengthData2, 0, 3);
            System.arraycopy(lengthData2, 0, data, index, 3);
        }

        return data;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
