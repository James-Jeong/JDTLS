package network.dtls.certificate;

public class DtlsCertificateType {

    public static final int TLS_CERT_NONE             = 0;
    public static final int TLS_CERT_RSA_SIGN         = 1;
    public static final int TLS_CERT_DSS_SIGN         = 2;
    public static final int TLS_CERT_RSA_FIXED_DH     = 3;
    public static final int TLS_CERT_DSS_FIXED_DH     = 4;
    public static final int TLS_CERT_RSA_EPHEMERAL_DH = 5;
    public static final int TLS_CERT_DSS_EPHEMERAL_DH = 6;
    public static final int TLS_CERT_FORTEZZA_DMS     = 20;
    public static final int TLS_CERT_ECDSA_SIGN       = 64;
    public static final int TLS_CERT_RSA_FIXED_ECDH   = 65;
    public static final int TLS_CERT_ECDSA_FIXED_ECDH = 66;
    public static final int TLS_CERT_RSA_PSS_SIGN     = 256; // For internal use only
    public static final int TLS_CERT_ED25519_SIGN     = 257; // For internal use only
    public static final int TLS_CERT_ED448_SIGN       = 258;  // For internal use only

}
