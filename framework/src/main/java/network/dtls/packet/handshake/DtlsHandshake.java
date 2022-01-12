package network.dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.type.*;
import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtlsHandshake implements DtlsHandshakeFactory {

    private static final Logger logger = LoggerFactory.getLogger(DtlsHandshake.class);

    private DtlsFormat dtlsFormat;

    public DtlsHandshake(DtlsFormat dtlsFormat) {
        this.dtlsFormat = dtlsFormat;
    }

    public DtlsHandshake() {}

    public DtlsHandshake(DtlsHandshakeType dtlsHandshakeType, byte[] data) {
        if (data.length > 0) {
            switch (dtlsHandshakeType.getType()) {
                case DtlsHandshakeType.TLS_TYPE_HELLO_REQUEST:
                    dtlsFormat = new DtlsHelloRequest(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_CLIENT_HELLO:
                    dtlsFormat = new DtlsClientHello(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_SERVER_HELLO:
                    dtlsFormat = new DtlsServerHello(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_HELLO_VERIFY_REQUEST:
                    dtlsFormat = new DtlsHelloVerifyRequest(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_NEW_SESSION_TICKET:
                    // Not implemented yet
                    break;
                case DtlsHandshakeType.TLS_TYPE_END_OF_EARLY_DATA:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_END_OF_EARLY_DATA)");
                    break;
                case DtlsHandshakeType.TLS_TYPE_HELLO_RETRY_REQUEST:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_HELLO_RETRY_REQUEST)");
                    break;
                case DtlsHandshakeType.TLS_TYPE_ENCRYPTED_EXTENSIONS:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_ENCRYPTED_EXTENSIONS)");
                    break;
                case DtlsHandshakeType.TLS_TYPE_CERTIFICATE:
                    dtlsFormat = new DtlsCertificate(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_SERVER_KEY_EXCHANGE:
                    dtlsFormat = new DtlsServerKeyExchange(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_CERTIFICATE_REQUEST:
                    dtlsFormat = new DtlsCertificateRequest(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_SERVER_HELLO_DONE:
                    dtlsFormat = new DtlsServerHelloDone(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_CERTIFICATE_VERIFY:
                    dtlsFormat = new DtlsCertificateVerify(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_CLIENT_KEY_EXCHANGE:
                    dtlsFormat = new DtlsClientKeyExchange(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_FINISHED:
                    dtlsFormat = new DtlsFinished(data);
                    break;
                case DtlsHandshakeType.TLS_TYPE_CERTIFICATE_URL:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_CERTIFICATE_URL)");
                    break;
                case DtlsHandshakeType.TLS_TYPE_CERTIFICATE_STATUS:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_CERTIFICATE_STATUS)");
                    break;
                case DtlsHandshakeType.TLS_TYPE_SUPPLEMENTAL_DATA:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_SUPPLEMENTAL_DATA)");
                    break;
                case DtlsHandshakeType.TLS_TYPE_KEY_UPDATE:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_KEY_UPDATE)");
                    break;
                case DtlsHandshakeType.TLS_TYPE_MESSAGE_HASH:
                    // Not implemented yet
                    logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_MESSAGE_HASH)");
                    break;
                default:
                    logger.warn("DtlsHandshakeBody.UnknownHandshakeType ({})", dtlsHandshakeType.getType());
            }
        }
    }

    public byte[] getData() {
        if (dtlsFormat == null) { return null; }
        return dtlsFormat.getData();
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
