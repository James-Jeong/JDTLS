package network.dtls.algorithm;

public class DtlsSignatureAlgorithm {

    public static final int TLS_SIGN_ALGO_ANONYMOUS                          = 0;
    public static final int TLS_SIGN_ALGO_RSA                                = 1;
    public static final int TLS_SIGN_ALGO_DSA                                = 2;
    public static final int TLS_SIGN_ALGO_ECDSA                              = 3;
    public static final int TLS_SIGN_ALGO_RSA_PSS_RSAE_SHA256                = 4;
    public static final int TLS_SIGN_ALGO_RSA_PSS_RSAE_SHA384                = 5;
    public static final int TLS_SIGN_ALGO_RSA_PSS_RSAE_SHA512                = 6;
    public static final int TLS_SIGN_ALGO_ED25519                            = 7;
    public static final int TLS_SIGN_ALGO_ED448                              = 8;
    public static final int TLS_SIGN_ALGO_RSA_PSS_PSS_SHA256                 = 9;
    public static final int TLS_SIGN_ALGO_RSA_PSS_PSS_SHA384                 = 10;
    public static final int TLS_SIGN_ALGO_RSA_PSS_PSS_SHA512                 = 11;
    public static final int TLS_SIGN_ALGO_ECDSA_BRAINPOOLP256R1_TLS13_SHA256 = 26;
    public static final int TLS_SIGN_ALGO_ECDSA_BRAINPOOLP384R1_TLS13_SHA384 = 27;
    public static final int TLS_SIGN_ALGO_ECDSA_BRAINPOOLP512R1_TLS13_SHA512 = 28;
    public static final int TLS_SIGN_ALGO_GOSTR34102012_256                  = 64; //RFC draft
    public static final int TLS_SIGN_ALGO_GOSTR34102012_512                  = 65;  //RFC draft

}
