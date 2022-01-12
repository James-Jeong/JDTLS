package network.dtls.type;

import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsFinished extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody;

    public DtlsFinished() {}

    public DtlsFinished(byte[] data) {
        if (data.length == LENGTH) {
            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, 0, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
        }
    }

    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null) { return null; }

        byte[] data = new byte[LENGTH];

        byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
        System.arraycopy(commonBodyData, 0, data, 0, DtlsHandshakeCommonBody.LENGTH);

        return data;
    }

    public DtlsHandshakeCommonBody getDtlsHandshakeCommonBody() {
        return dtlsHandshakeCommonBody;
    }

    public void setDtlsHandshakeCommonBody(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

}
