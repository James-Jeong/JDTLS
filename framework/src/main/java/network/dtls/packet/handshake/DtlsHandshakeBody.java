package network.dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.type.base.DtlsFormat;

public class DtlsHandshakeBody {

    private DtlsFormat dtlsFormat;

    public DtlsHandshakeBody(DtlsFormat dtlsFormat) {
        this.dtlsFormat = dtlsFormat;
    }

    public DtlsHandshakeBody() {}

    public DtlsHandshakeBody(byte[] data) {

    }

    public DtlsFormat getDtlsFormat() {
        return dtlsFormat;
    }

    public void setDtlsFormat(DtlsFormat dtlsFormat) {
        this.dtlsFormat = dtlsFormat;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
