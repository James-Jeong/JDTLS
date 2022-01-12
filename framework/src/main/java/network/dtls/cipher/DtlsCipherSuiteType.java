package network.dtls.cipher;

public class DtlsCipherSuiteType {

    public static final int LENGTH = 2;

    public static final byte[] TLS_RSA_EXPORT_WITH_RC4_40_MD5 = { 0x00, 0x03 };
    public static final byte[] TLS_RSA_WITH_RC4_128_MD5 = { 0x00, 0x04 };
    public static final byte[] TLS_RSA_WITH_RC4_128_SHA = { 0x00, 0x05 };
    public static final byte[] TLS_RSA_EXPORT_WITH_RC2_CBC_40_MD5 = { 0x00, 0x06 };
    public static final byte[] TLS_RSA_WITH_IDEA_CBC_SHA = { 0x00, 0x07 };
    public static final byte[] TLS_RSA_EXPORT_WITH_DES40_CBC_SHA = { 0x00, 0x08 };
    public static final byte[] TLS_RSA_WITH_DES_CBC_SHA = { 0x00, 0x09 };
    public static final byte[] TLS_RSA_WITH_3DES_EDE_CBC_SHA = { 0x00, 0x0a };
    public static final byte[] TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA = { 0x00, 0x11 };
    public static final byte[] TLS_DHE_DSS_WITH_DES_CBC_SHA = { 0x00, 0x12 };
    public static final byte[] TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = { 0x00, 0x13 };
    public static final byte[] TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA = { 0x00, 0x14 };
    public static final byte[] TLS_DHE_RSA_WITH_DES_CBC_SHA = { 0x00, 0x15 };
    public static final byte[] TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = { 0x00, 0x16 };
    public static final byte[] TLS_DH_anon_EXPORT_WITH_RC4_40_MD5 = { 0x00, 0x17 };
    public static final byte[] TLS_DH_anon_WITH_RC4_128_MD5 = { 0x00, 0x18 };
    public static final byte[] TLS_KRB5_WITH_RC4_128_SHA = { 0x00, 0x20 };
    public static final byte[] TLS_KRB5_WITH_RC4_128_MD5 = { 0x00, 0x24 };
    public static final byte[] TLS_KRB5_EXPORT_WITH_RC4_40_SHA = { 0x00, 0x28 };
    public static final byte[] TLS_KRB5_EXPORT_WITH_RC4_40_MD5 = { 0x00, 0x2B };
    public static final byte[] TLS_RSA_WITH_AES_128_CBC_SHA = { 0x00, 0x2f };
    public static final byte[] TLS_DHE_DSS_WITH_AES_128_CBC_SHA = { 0x00, 0x32 };
    public static final byte[] TLS_DHE_RSA_WITH_AES_128_CBC_SHA = { 0x00, 0x33 };
    public static final byte[] TLS_DHE_DSS_WITH_AES_256_CBC_SHA = { 0x00, 0x35 };
    public static final byte[] TLS_RSA_WITH_AES_256_CBC_SHA = { 0x00, 0x38 };
    public static final byte[] TLS_DHE_RSA_WITH_AES_256_CBC_SHA = { 0x00, 0x39 };
    public static final byte[] TLS_RSA_EXPORT1024_WITH_RC4_56_MD5 = { 0x00, 0x60 };
    public static final byte[] TLS_RSA_EXPORT1024_WITH_RC2_CBC_56_MD5 = { 0x00, 0x61 };
    public static final byte[] TLS_RSA_EXPORT1024_WITH_DES_CBC_SHA = { 0x00, 0x62 };
    public static final byte[] TLS_DHE_DSS_EXPORT1024_WITH_DES_CBC_SHA = { 0x00, 0x63 };
    public static final byte[] TLS_RSA_EXPORT1024_WITH_RC4_56_SHA = { 0x00, 0x64 };
    public static final byte[] TLS_DHE_DSS_EXPORT1024_WITH_RC4_56_SHA = { 0x00, 0x65 };
    public static final byte[] TLS_DHE_DSS_WITH_RC4_128_SHA = { 0x00, 0x66 };
    public static final byte[] TLS_PSK_WITH_RC4_128_SHA = { 0x00, (byte) 0x8A};
    public static final byte[] TLS_DHE_PSK_WITH_RC4_128_SHA = { 0x00, (byte) 0x8E};
    public static final byte[] TLS_RSA_PSK_WITH_RC4_128_SHA = { 0x00, (byte) 0x92};
    public static final byte[] TLS_ECDH_ECDSA_WITH_RC4_128_SHA = {(byte) 0xC0, 0x02 };
    public static final byte[] TLS_ECDHE_ECDSA_WITH_RC4_128_SHA = {(byte) 0xC0, 0x07 };
    public static final byte[] TLS_ECDH_RSA_WITH_RC4_128_SHA = {(byte) 0xC0, 0x0C };
    public static final byte[] TLS_ECDHE_RSA_WITH_RC4_128_SHA = {(byte) 0xC0, 0x11 };
    public static final byte[] TLS_ECDH_anon_WITH_RC4_128_SHA = {(byte) 0xC0, 0x16 };
    public static final byte[] TLS_ECDHE_PSK_WITH_RC4_128_SHA = {(byte) 0xC0, 0x33 };


}
