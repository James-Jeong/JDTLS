package dtls.type;

import dtls.certificate.Certificates;
import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;
import util.module.ByteUtil;

public class DtlsCertificate extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int MIN_LENGTH = DtlsHandshakeCommonBody.LENGTH + 3;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody; // 12 bytes
    private long certificatesLength; // 3 bytes
    private Certificates certificates; // certificatesLength bytes
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsCertificate(DtlsHandshakeCommonBody dtlsHandshakeCommonBody,
                           long certificatesLength, Certificates certificates) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.certificatesLength = certificatesLength;
        this.certificates = certificates;
    }

    public DtlsCertificate() {}

    public DtlsCertificate(byte[] data) {
        if (data.length >= MIN_LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

            byte[] certificatesLengthData = new byte[3];
            System.arraycopy(data, index, certificatesLengthData, 0, 3);
            byte[] certificatesLengthData2 = new byte[ByteUtil.NUM_BYTES_IN_LONG];
            System.arraycopy(certificatesLengthData, 0, certificatesLengthData2,ByteUtil.NUM_BYTES_IN_LONG - 3, 3);
            certificatesLength = ByteUtil.bytesToLong(certificatesLengthData2, true);
            index += 3;

            if (certificatesLength > 0) {
                byte[] certificatesData = new byte[(int) certificatesLength];
                System.arraycopy(data, index, certificatesData, 0, (int) certificatesLength);
                certificates = new Certificates(certificatesData);
            }
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null || certificates == null) { return null; }

        int index = 0;

        byte[] data;
        byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
        if (certificatesLength > 0) {
            data = new byte[MIN_LENGTH + (int) certificatesLength];
            System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
            index += DtlsHandshakeCommonBody.LENGTH;

            byte[] certificatesLengthData = ByteUtil.longToBytes(certificatesLength, true);
            byte[] certificatesLengthData2 = new byte[3];
            System.arraycopy(certificatesLengthData, ByteUtil.NUM_BYTES_IN_LONG - 3, certificatesLengthData2, 0, 3);
            System.arraycopy(certificatesLengthData2, 0, data, index, 3);
            index += 3;

            byte[] certificatesData = certificates.getData();
            System.arraycopy(certificatesData, 0, data, index, certificatesData.length);
        } else {
            data = new byte[MIN_LENGTH];
            System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
            index += DtlsHandshakeCommonBody.LENGTH;

            byte[] certificatesLengthData = ByteUtil.longToBytes(certificatesLength, true);
            byte[] certificatesLengthData2 = new byte[3];
            System.arraycopy(certificatesLengthData, ByteUtil.NUM_BYTES_IN_LONG - 3, certificatesLengthData2, 0, 3);
            System.arraycopy(certificatesLengthData2, 0, data, index, 3);
        }

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsHandshakeCommonBody getDtlsHandshakeCommonBody() {
        return dtlsHandshakeCommonBody;
    }

    public void setDtlsHandshakeCommonBody(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    public long getCertificatesLength() {
        return certificatesLength;
    }

    public void setCertificatesLength(long certificatesLength) {
        this.certificatesLength = certificatesLength;
    }

    public Certificates getCertificates() {
        return certificates;
    }

    public void setCertificates(Certificates certificates) {
        this.certificates = certificates;
    }
    ////////////////////////////////////////////////////////////

}
