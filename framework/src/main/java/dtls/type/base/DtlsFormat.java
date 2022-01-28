package dtls.type.base;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class DtlsFormat {

    ////////////////////////////////////////////////////////////
    public byte[] getData() {
        return null;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    ////////////////////////////////////////////////////////////

}
