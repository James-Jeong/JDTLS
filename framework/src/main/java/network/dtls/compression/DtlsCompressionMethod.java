package network.dtls.compression;

public class DtlsCompressionMethod {

    public static final int TLS_COMPRESSION_METHOD_NULL    = 0;
    public static final int TLS_COMPRESSION_METHOD_DEFLATE = 1;

    private final int method;

    public DtlsCompressionMethod(int method) {
        this.method = method;
    }

    public int getMethod() {
        return method;
    }
}
