package network.dtls.type;

import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsCertificateRequest extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody;

    public DtlsCertificateRequest(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    public DtlsCertificateRequest() {}

    public DtlsCertificateRequest(byte[] data) {
        // TODO
    }

    public DtlsHandshakeCommonBody getDtlsHandshakeCommonBody() {
        return dtlsHandshakeCommonBody;
    }

    public void setDtlsHandshakeCommonBody(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    @Override
    public byte[] getData() {
        return new byte[0];
    }

}
