package network.dtls.type;

import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;

/**
 * - It provides the server with the necessary data
 *      to generate the keys for the symmetric encryption.
 * - Same with DtlsServerKeyExchange
 */
public class DtlsClientKeyExchange extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH + 128;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody;
    private byte[] encryptedPreMasterSecretData;

    public DtlsClientKeyExchange(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, byte[] encryptedPreMasterSecretData) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.encryptedPreMasterSecretData = encryptedPreMasterSecretData;
    }

    public DtlsClientKeyExchange() {}

    public DtlsClientKeyExchange(byte[] data) {
        if (data.length == LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

            encryptedPreMasterSecretData = new byte[128];
            System.arraycopy(data, index, encryptedPreMasterSecretData, 0, 128);
        }
    }

    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null || encryptedPreMasterSecretData == null) { return null; }

        int index = 0;
        byte[] data = new byte[LENGTH];

        byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
        System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
        index += DtlsHandshakeCommonBody.LENGTH;

        System.arraycopy(encryptedPreMasterSecretData, 0, data, index, encryptedPreMasterSecretData.length);

        return data;
    }

    public DtlsHandshakeCommonBody getDtlsHandshakeCommonBody() {
        return dtlsHandshakeCommonBody;
    }

    public void setDtlsHandshakeCommonBody(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    public byte[] getEncryptedPreMasterSecretData() {
        return encryptedPreMasterSecretData;
    }

    public void setEncryptedPreMasterSecretData(byte[] encryptedPreMasterSecretData) {
        this.encryptedPreMasterSecretData = encryptedPreMasterSecretData;
    }

}
