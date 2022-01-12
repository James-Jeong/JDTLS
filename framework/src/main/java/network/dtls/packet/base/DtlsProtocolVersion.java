package network.dtls.packet.base;

public class DtlsProtocolVersion {

    public static final byte[] DTLS_1_0 = { 0x01, 0x00 };
    public static final byte[] DTLS_1_2 = { 0x01, 0x02 };
    public static final byte[] DTLS_1_3 = { 0x01, 0x03 };

    private final byte[] version;

    public DtlsProtocolVersion(byte[] version) {
        this.version = version;
    }

    public byte[] getVersion() {
        return version;
    }
}
