package dtls.type;

import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;

/**
 * - It provides the server with the necessary data
 *      to generate the keys for the symmetric encryption.
 * - Same with DtlsServerKeyExchange
 */
public class DtlsClientKeyExchange extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int MIN_LENGTH = DtlsHandshakeCommonBody.LENGTH;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody = null;
    transient private byte[] encryptedPreMasterSecretData = null;
    private int encryptedPreMasterSecretDataLength = 0;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsClientKeyExchange(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, byte[] encryptedPreMasterSecretData) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.encryptedPreMasterSecretData = encryptedPreMasterSecretData;
        this.encryptedPreMasterSecretDataLength = encryptedPreMasterSecretData.length;
    }

    public DtlsClientKeyExchange() {}

    public DtlsClientKeyExchange(byte[] data) {
        if (data.length >= MIN_LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

            int remainLength = data.length - index;
            if (remainLength > 0) {
                encryptedPreMasterSecretData = new byte[remainLength];
                System.arraycopy(data, index, encryptedPreMasterSecretData, 0, remainLength);
                encryptedPreMasterSecretDataLength = encryptedPreMasterSecretData.length;
            }
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null || encryptedPreMasterSecretData == null) { return null; }

        int index = 0;
        byte[] data;

        if (encryptedPreMasterSecretData.length > 0) {
            data = new byte[MIN_LENGTH + encryptedPreMasterSecretData.length];
            byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
            System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
            index += DtlsHandshakeCommonBody.LENGTH;
            System.arraycopy(encryptedPreMasterSecretData, 0, data, index, encryptedPreMasterSecretData.length);
        } else {
            data = new byte[MIN_LENGTH];
            byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
            System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
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

    public byte[] getEncryptedPreMasterSecretData() {
        return encryptedPreMasterSecretData;
    }

    public void setEncryptedPreMasterSecretData(byte[] encryptedPreMasterSecretData) {
        this.encryptedPreMasterSecretData = encryptedPreMasterSecretData;
    }

    public int getEncryptedPreMasterSecretDataLength() {
        return encryptedPreMasterSecretDataLength;
    }

    public void setEncryptedPreMasterSecretDataLength(int encryptedPreMasterSecretDataLength) {
        this.encryptedPreMasterSecretDataLength = encryptedPreMasterSecretDataLength;
    }

    ////////////////////////////////////////////////////////////

}
