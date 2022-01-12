package network.dtls.certificate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Certificate {

    private SignedCertificate signedCertificate; // unknown bytes


    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
    }



    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
