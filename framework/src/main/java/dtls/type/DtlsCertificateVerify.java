package dtls.type;

import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsCertificateVerify extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody;

    public DtlsCertificateVerify(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    public DtlsCertificateVerify() {}

    public DtlsCertificateVerify(byte[] data) {
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