package network.dtls.ssl;

import javax.net.ssl.SSLEngine;
import java.nio.ByteBuffer;

public class ServerSsl {

    private SSLEngine serverEngine;     // server Engine
    private ByteBuffer serverOut;       // write side of serverEngine
    private ByteBuffer serverIn;        // read side of serverEngine

    public ServerSsl(SSLEngine serverEngine, ByteBuffer serverOut, ByteBuffer serverIn) {
        this.serverEngine = serverEngine;
        this.serverOut = serverOut;
        this.serverIn = serverIn;
    }

    public ServerSsl(SSLEngine serverEngine) {
        this.serverEngine = serverEngine;
    }

    public SSLEngine getServerEngine() {
        return serverEngine;
    }

    public void setServerEngine(SSLEngine serverEngine) {
        this.serverEngine = serverEngine;
    }

    public ByteBuffer getServerOut() {
        return serverOut;
    }

    public void setServerOut(ByteBuffer serverOut) {
        this.serverOut = serverOut;
    }

    public ByteBuffer getServerIn() {
        return serverIn;
    }

    public void setServerIn(ByteBuffer serverIn) {
        this.serverIn = serverIn;
    }
}
