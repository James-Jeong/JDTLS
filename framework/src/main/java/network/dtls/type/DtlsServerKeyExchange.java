package network.dtls.type;

import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsServerKeyExchange extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody;

    public DtlsServerKeyExchange(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    public DtlsServerKeyExchange() {}

    public DtlsServerKeyExchange(byte[] data) {
        if (data.length >= LENGTH) {

        }
    }

    @Override
    public byte[] getData() {
        return new byte[0];
    }

}
