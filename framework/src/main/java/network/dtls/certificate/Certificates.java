package network.dtls.certificate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Certificates {

    private long length; // 3 bytes
    private byte[] certificate; // length bytes

    public Certificates(long length, byte[] certificate) {
        this.length = length;
        this.certificate = certificate;
    }

    public Certificates() {}

    public Certificates(byte[] data) {
        // TODO
    }

    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
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
