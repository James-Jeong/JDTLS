package network.dtls.type.base;

public class DtlsHandshakeType {

    public static final int TLS_TYPE_HELLO_REQUEST        = 0;
    public static final int TLS_TYPE_CLIENT_HELLO         = 1;
    public static final int TLS_TYPE_SERVER_HELLO         = 2;
    public static final int TLS_TYPE_HELLO_VERIFY_REQUEST = 3;
    public static final int TLS_TYPE_NEW_SESSION_TICKET   = 4;
    public static final int TLS_TYPE_END_OF_EARLY_DATA    = 5;
    public static final int TLS_TYPE_HELLO_RETRY_REQUEST  = 6;
    public static final int TLS_TYPE_ENCRYPTED_EXTENSIONS = 8;
    public static final int TLS_TYPE_CERTIFICATE          = 11;
    public static final int TLS_TYPE_SERVER_KEY_EXCHANGE  = 12;
    public static final int TLS_TYPE_CERTIFICATE_REQUEST  = 13;
    public static final int TLS_TYPE_SERVER_HELLO_DONE    = 14;
    public static final int TLS_TYPE_CERTIFICATE_VERIFY   = 15;
    public static final int TLS_TYPE_CLIENT_KEY_EXCHANGE  = 16;
    public static final int TLS_TYPE_FINISHED             = 20;
    public static final int TLS_TYPE_CERTIFICATE_URL      = 21;
    public static final int TLS_TYPE_CERTIFICATE_STATUS   = 22;
    public static final int TLS_TYPE_SUPPLEMENTAL_DATA    = 23;
    public static final int TLS_TYPE_KEY_UPDATE           = 24;
    public static final int TLS_TYPE_MESSAGE_HASH         = 254;

    private final int type;

    public DtlsHandshakeType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
