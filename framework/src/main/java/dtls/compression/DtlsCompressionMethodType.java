package dtls.compression;

public class DtlsCompressionMethodType {

    public static final int TLS_COMPRESSION_METHOD_NULL    = 0;
    public static final int TLS_COMPRESSION_METHOD_DEFLATE = 1;

    private final int method;

    public DtlsCompressionMethodType(int method) {
        this.method = method;
    }

    public int getMethod() {
        return method;
    }
}
