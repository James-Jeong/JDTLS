package dtls.alert;

public class DtlsAlertDescription {

    public static final int TLS_ALERT_CLOSE_NOTIFY                    = 0;
    public static final int TLS_ALERT_UNEXPECTED_MESSAGE              = 10;
    public static final int TLS_ALERT_BAD_RECORD_MAC                  = 20;
    public static final int TLS_ALERT_DECRYPTION_FAILED               = 21;
    public static final int TLS_ALERT_RECORD_OVERFLOW                 = 22;
    public static final int TLS_ALERT_DECOMPRESSION_FAILURE           = 30;
    public static final int TLS_ALERT_HANDSHAKE_FAILURE               = 40;
    public static final int TLS_ALERT_NO_CERTIFICATE                  = 41;
    public static final int TLS_ALERT_BAD_CERTIFICATE                 = 42;
    public static final int TLS_ALERT_UNSUPPORTED_CERTIFICATE         = 43;
    public static final int TLS_ALERT_CERTIFICATE_REVOKED             = 44;
    public static final int TLS_ALERT_CERTIFICATE_EXPIRED             = 45;
    public static final int TLS_ALERT_CERTIFICATE_UNKNOWN             = 46;
    public static final int TLS_ALERT_ILLEGAL_PARAMETER               = 47;
    public static final int TLS_ALERT_UNKNOWN_CA                      = 48;
    public static final int TLS_ALERT_ACCESS_DENIED                   = 49;
    public static final int TLS_ALERT_DECODE_ERROR                    = 50;
    public static final int TLS_ALERT_DECRYPT_ERROR                   = 51;
    public static final int TLS_ALERT_EXPORT_RESTRICTION              = 60;
    public static final int TLS_ALERT_PROTOCOL_VERSION                = 70;
    public static final int TLS_ALERT_INSUFFICIENT_SECURITY           = 71;
    public static final int TLS_ALERT_INTERNAL_ERROR                  = 80;
    public static final int TLS_ALERT_INAPPROPRIATE_FALLBACK          = 86;
    public static final int TLS_ALERT_USER_CANCELED                   = 90;
    public static final int TLS_ALERT_NO_RENEGOTIATION                = 100;
    public static final int TLS_ALERT_MISSING_EXTENSION               = 109;
    public static final int TLS_ALERT_UNSUPPORTED_EXTENSION           = 110;
    public static final int TLS_ALERT_CERTIFICATE_UNOBTAINABLE        = 111;
    public static final int TLS_ALERT_UNRECOGNIZED_NAME               = 112;
    public static final int TLS_ALERT_BAD_CERTIFICATE_STATUS_RESPONSE = 113;
    public static final int TLS_ALERT_BAD_CERTIFICATE_HASH_VALUE      = 114;
    public static final int TLS_ALERT_UNKNOWN_PSK_IDENTITY            = 115;
    public static final int TLS_ALERT_CERTIFICATE_REQUIRED            = 116;
    public static final int TLS_ALERT_NO_APPLICATION_PROTOCOL         = 120;

}
