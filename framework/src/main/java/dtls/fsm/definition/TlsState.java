package dtls.fsm.definition;

public class TlsState {

    public static final String NAME = "TlsState";

    public static final int TLS_STATE_INIT                        = 0;
    public static final int TLS_STATE_CLIENT_HELLO                = 1;
    public static final int TLS_STATE_CLIENT_HELLO_2              = 2;
    public static final int TLS_STATE_EARLY_DATA                  = 3;
    public static final int TLS_STATE_HELLO_VERIFY_REQUEST        = 4;
    public static final int TLS_STATE_HELLO_RETRY_REQUEST         = 5;
    public static final int TLS_STATE_SERVER_HELLO                = 6;
    public static final int TLS_STATE_SERVER_HELLO_2              = 7;
    public static final int TLS_STATE_SERVER_HELLO_3              = 8;
    public static final int TLS_STATE_HANDSHAKE_TRAFFIC_KEYS      = 9;
    public static final int TLS_STATE_ENCRYPTED_EXTENSIONS        = 10;
    public static final int TLS_STATE_SERVER_CERTIFICATE          = 11;
    public static final int TLS_STATE_SERVER_KEY_EXCHANGE         = 12;
    public static final int TLS_STATE_SERVER_CERTIFICATE_VERIFY   = 13;
    public static final int TLS_STATE_CERTIFICATE_REQUEST         = 14;
    public static final int TLS_STATE_SERVER_HELLO_DONE           = 15;
    public static final int TLS_STATE_CLIENT_CERTIFICATE          = 16;
    public static final int TLS_STATE_CLIENT_KEY_EXCHANGE         = 17;
    public static final int TLS_STATE_CLIENT_CERTIFICATE_VERIFY   = 18;
    public static final int TLS_STATE_CLIENT_CHANGE_CIPHER_SPEC   = 19;
    public static final int TLS_STATE_CLIENT_CHANGE_CIPHER_SPEC_2 = 20;
    public static final int TLS_STATE_CLIENT_FINISHED             = 21;
    public static final int TLS_STATE_CLIENT_APP_TRAFFIC_KEYS     = 22;
    public static final int TLS_STATE_SERVER_CHANGE_CIPHER_SPEC   = 23;
    public static final int TLS_STATE_SERVER_CHANGE_CIPHER_SPEC_2 = 24;
    public static final int TLS_STATE_SERVER_FINISHED             = 25;
    public static final int TLS_STATE_END_OF_EARLY_DATA           = 26;
    public static final int TLS_STATE_SERVER_APP_TRAFFIC_KEYS     = 27;
    public static final int TLS_STATE_NEW_SESSION_TICKET          = 28;
    public static final int TLS_STATE_KEY_UPDATE                  = 29;
    public static final int TLS_STATE_APPLICATION_DATA            = 30;
    public static final int TLS_STATE_CLOSING                     = 31;
    public static final int TLS_STATE_CLOSED                      = 32;

}
