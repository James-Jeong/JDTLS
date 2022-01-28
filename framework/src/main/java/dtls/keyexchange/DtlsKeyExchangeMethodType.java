package dtls.keyexchange;

public class DtlsKeyExchangeMethodType {

    public static final int TLS_KEY_EXCH_NONE        = 0;
    public static final int TLS_KEY_EXCH_RSA         = 1;
    public static final int TLS_KEY_EXCH_DH_RSA      = 2;
    public static final int TLS_KEY_EXCH_DHE_RSA     = 3;
    public static final int TLS_KEY_EXCH_DH_DSS      = 4;
    public static final int TLS_KEY_EXCH_DHE_DSS     = 5;
    public static final int TLS_KEY_EXCH_DH_ANON     = 6;
    public static final int TLS_KEY_EXCH_ECDH_RSA    = 7;
    public static final int TLS_KEY_EXCH_ECDHE_RSA   = 8;
    public static final int TLS_KEY_EXCH_ECDH_ECDSA  = 9;
    public static final int TLS_KEY_EXCH_ECDHE_ECDSA = 10;
    public static final int TLS_KEY_EXCH_ECDH_ANON   = 11;
    public static final int TLS_KEY_EXCH_PSK         = 12;
    public static final int TLS_KEY_EXCH_RSA_PSK     = 13;
    public static final int TLS_KEY_EXCH_DHE_PSK     = 14;
    public static final int TLS_KEY_EXCH_ECDHE_PSK   = 15;
    public static final int TLS_KEY_EXCH_SRP_SHA     = 16;
    public static final int TLS_KEY_EXCH_SRP_SHA_RSA = 17;
    public static final int TLS_KEY_EXCH_SRP_SHA_DSS = 18;
    public static final int TLS13_KEY_EXCH_DHE       = 19;
    public static final int TLS13_KEY_EXCH_ECDHE     = 20;
    public static final int TLS13_KEY_EXCH_PSK       = 21;
    public static final int TLS13_KEY_EXCH_PSK_DHE   = 22;
    public static final int TLS13_KEY_EXCH_PSK_ECDHE = 23;

}
