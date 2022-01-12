package network.dtls.packet.base;

public class DtlsContentType {

    public static final int TLS_TYPE_NONE               = 0;
    public static final int TLS_TYPE_CHANGE_CIPHER_SPEC = 20;
    public static final int TLS_TYPE_ALERT              = 21;
    public static final int TLS_TYPE_HANDSHAKE          = 22;
    public static final int TLS_TYPE_APPLICATION_DATA   = 23;
    public static final int TLS_TYPE_HEARTBEAT          = 24;
    public static final int TLS_TYPE_ACK                = 25; // RFC draft

    private final int type;

    public DtlsContentType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

