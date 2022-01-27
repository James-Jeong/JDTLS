package network.dtls.ssl;

import javax.net.ssl.SSLEngine;
import java.nio.ByteBuffer;

public class ClientSsl {

    private SSLEngine clientEngine;     // client Engine
    private ByteBuffer clientOut;       // write side of clientEngine
    private ByteBuffer clientIn;        // read side of clientEngine

    public ClientSsl(SSLEngine clientEngine, ByteBuffer clientOut, ByteBuffer clientIn) {
        this.clientEngine = clientEngine;
        this.clientOut = clientOut;
        this.clientIn = clientIn;
    }

    public ClientSsl(SSLEngine clientEngine) {
        this.clientEngine = clientEngine;
    }

    public SSLEngine getClientEngine() {
        return clientEngine;
    }

    public void setClientEngine(SSLEngine clientEngine) {
        this.clientEngine = clientEngine;
    }

    public ByteBuffer getClientOut() {
        return clientOut;
    }

    public void setClientOut(ByteBuffer clientOut) {
        this.clientOut = clientOut;
    }

    public ByteBuffer getClientIn() {
        return clientIn;
    }

    public void setClientIn(ByteBuffer clientIn) {
        this.clientIn = clientIn;
    }
}
