package dtls.ssl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.security.KeyStore;

public class SslEngineManager {

    private static final Logger logger = LoggerFactory.getLogger(SslEngineManager.class);

    /*
     * Enables the JSSE system debugging system property:
     *
     *     -Djavax.net.debug=all
     *
     * This gives a lot of low-level information about operations underway,
     * including specific handshake messages, and might be best examined
     * after gaining some familiarity with this application.
     */
    private static boolean debug = false;

    private SSLContext sslc;

    private ClientSsl clientSsl;
    private ServerSsl serverSsl;

    private ByteBuffer cTOs;            // "reliable" transport client->server
    private ByteBuffer sTOc;            // "reliable" transport server->client

    private static String keyStoreFile = "testkeys";
    private static String trustStoreFile = "testkeys";
    private static String passwd = "passphrase";


    public SslEngineManager(SSLContext sslc, ClientSsl clientSsl, ServerSsl serverSsl, ByteBuffer cTOs, ByteBuffer sTOc) {
        this.sslc = sslc;
        this.clientSsl = clientSsl;
        this.serverSsl = serverSsl;
        this.cTOs = cTOs;
        this.sTOc = sTOc;
    }

    public SslEngineManager() {
        if (debug) {
            System.setProperty("javax.net.debug", "all");
        }
    }

    public void init() {
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore ts = KeyStore.getInstance("JKS");

            char[] passphrase = "passphrase".toCharArray();

            ks.load(new FileInputStream(keyStoreFile), passphrase);
            ts.load(new FileInputStream(trustStoreFile), passphrase);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, passphrase);

            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ts);

            SSLContext sslCtx = SSLContext.getInstance("TLS");
            sslCtx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            sslc = sslCtx;
        } catch (Exception e) {
            logger.warn("SslEngineManager.init.Exception", e);
        }
    }

    private void initEngine() {
        if (serverSsl == null) {
            SSLEngine serverEngine = sslc.createSSLEngine();
            serverEngine.setUseClientMode(false);
            serverEngine.setNeedClientAuth(true);
            serverSsl = new ServerSsl(serverEngine);
        }

        if (clientSsl == null) {
            SSLEngine clientEngine = sslc.createSSLEngine("client", 80);
            clientEngine.setUseClientMode(true);
            clientSsl = new ClientSsl(clientEngine);
        }
    }

    public void initBuffer() {
        if (serverSsl == null || clientSsl == null) {
            return;
        }

        /*
         * We'll assume the buffer sizes are the same
         * between client and server.
         */
        SSLEngine clientEngine = clientSsl.getClientEngine();
        if (clientEngine == null) {
            return;
        }

        SSLSession session = clientEngine.getSession();
        int appBufferMax = session.getApplicationBufferSize();
        int netBufferMax = session.getPacketBufferSize();

        /*
         * We'll make the input buffers a bit bigger than the max needed
         * size, so that unwrap()s following a successful data transfer
         * won't generate BUFFER_OVERFLOWS.
         *
         * We'll use a mix of direct and indirect ByteBuffers for
         * tutorial purposes only.  In reality, only use direct
         * ByteBuffers when they give a clear performance enhancement.
         */
        ByteBuffer clientIn = ByteBuffer.allocate(appBufferMax + 50);
        clientSsl.setClientIn(clientIn);
        ByteBuffer serverIn = ByteBuffer.allocate(appBufferMax + 50);
        serverSsl.setServerIn(serverIn);

        cTOs = ByteBuffer.allocateDirect(netBufferMax);
        sTOc = ByteBuffer.allocateDirect(netBufferMax);

        ByteBuffer clientOut = ByteBuffer.wrap("Hi Server, I'm Client".getBytes());
        clientSsl.setClientOut(clientOut);
        ByteBuffer serverOut = ByteBuffer.wrap("Hello Client, I'm Server".getBytes());
        serverSsl.setServerOut(serverOut);
    }

}
