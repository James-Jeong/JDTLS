package network.dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DtlsHandShake {

    private DtlsHandshakeHeader dtlsHandshakeHeader;
    private DtlsHandshakeBody dtlsHandshakeBody;

    public DtlsHandShake(DtlsHandshakeHeader dtlsHandshakeHeader, DtlsHandshakeBody dtlsHandshakeBody) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
        this.dtlsHandshakeBody = dtlsHandshakeBody;
    }

    public DtlsHandShake() {}

    public DtlsHandShake(byte[] data) {

    }

    public DtlsHandshakeHeader getDtlsHandshakeHeader() {
        return dtlsHandshakeHeader;
    }

    public void setDtlsHandshakeHeader(DtlsHandshakeHeader dtlsHandshakeHeader) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
    }

    public DtlsHandshakeBody getDtlsHandshakeBody() {
        return dtlsHandshakeBody;
    }

    public void setDtlsHandshakeBody(DtlsHandshakeBody dtlsHandshakeBody) {
        this.dtlsHandshakeBody = dtlsHandshakeBody;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
