package network.dtls.cipher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DtlsCipherSuite {

    private final byte[] cipherSuite;

    public DtlsCipherSuite(byte[] cipherSuite) {
        this.cipherSuite = cipherSuite;
    }

    public byte[] getCipherSuite() {
        return cipherSuite;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
