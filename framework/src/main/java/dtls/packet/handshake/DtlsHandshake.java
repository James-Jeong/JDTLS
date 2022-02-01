package dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.type.*;
import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;
import dtls.type.base.DtlsHandshakeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtlsHandshake implements DtlsHandshakeFactory {

    ////////////////////////////////////////////////////////////
    private static final Logger logger = LoggerFactory.getLogger(DtlsHandshake.class);

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody = null;
    private DtlsFormat dtlsFormat = null;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsHandshake(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, DtlsFormat dtlsFormat) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.dtlsFormat = dtlsFormat;
    }

    public DtlsHandshake() {}

    public DtlsHandshake(byte[] data) {
        if (data.length >= DtlsHandshakeCommonBody.LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

            int remainDataLength = data.length - index;
            if (remainDataLength > 0) {
                byte[] dtlsFormatData = new byte[remainDataLength];
                System.arraycopy(data, index, dtlsFormatData, 0, remainDataLength);

                DtlsHandshakeType dtlsHandshakeType = dtlsHandshakeCommonBody.getHandshakeType();
                switch (dtlsHandshakeType.getType()) {
                    //////////////////////////////////////////////////////////////////////////
                    case DtlsHandshakeType.TLS_TYPE_HELLO_REQUEST:
                        // TODO: Not implemented yet
                        dtlsFormat = new DtlsHelloRequest(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_CLIENT_HELLO:
                        dtlsFormat = new DtlsClientHello(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_SERVER_HELLO:
                        dtlsFormat = new DtlsServerHello(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_HELLO_VERIFY_REQUEST:
                        dtlsFormat = new DtlsHelloVerifyRequest(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_CERTIFICATE:
                        dtlsFormat = new DtlsCertificate(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_SERVER_KEY_EXCHANGE:
                        dtlsFormat = new DtlsServerKeyExchange(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_CERTIFICATE_REQUEST:
                        // TODO: Not implemented yet
                        dtlsFormat = new DtlsCertificateRequest(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_SERVER_HELLO_DONE:
                        dtlsFormat = new DtlsServerHelloDone();
                        break;
                    case DtlsHandshakeType.TLS_TYPE_CERTIFICATE_VERIFY:
                        // TODO: Not implemented yet
                        dtlsFormat = new DtlsCertificateVerify(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_CLIENT_KEY_EXCHANGE:
                        dtlsFormat = new DtlsClientKeyExchange(dtlsFormatData);
                        break;
                    case DtlsHandshakeType.TLS_TYPE_FINISHED:
                        dtlsFormat = new DtlsFinished();
                        break;
                    //////////////////////////////////////////////////////////////////////////

                    //////////////////////////////////////////////////////////////////////////
                    case DtlsHandshakeType.TLS_TYPE_NEW_SESSION_TICKET:
                        // Not implemented yet
                        logger.warn("DtlsHandshakeBody.NotImplemented (DtlsHandshakeType.TLS_TYPE_NEW_SESSION_TICKET)");
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
                    //////////////////////////////////////////////////////////////////////////

                    default:
                        logger.warn("DtlsHandshakeBody.UnknownHandshakeType ({})", dtlsHandshakeType.getType());
                }
            }
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null || dtlsFormat == null) { return null; }

        int formatLength = 0;
        byte[] formatData = dtlsFormat.getData();
        if (formatData != null) {
            formatLength = formatData.length;
        }

        int index = 0;
        byte[] data = new byte[DtlsHandshakeCommonBody.LENGTH + formatLength];

        byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
        System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
        index += DtlsHandshakeCommonBody.LENGTH;

        if (formatData != null && formatLength > 0) {
            System.arraycopy(formatData, 0, data, index, formatLength);
        }

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////

}
