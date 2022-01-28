package dtls.type;

import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;

/**
 * - This message carries the keys exchange algorithm parameters
 *      that the client needs from the server in order to get the symmetric encryption working thereafter.
 * - It is optional, since not all key exchanges require the server explicitly sending this message.
 * - Actually, in most cases, the Certificate message is enough for the client
 *      to securely communicate a premaster key with the server.
 * - The format of those parameters depends exclusively on the selected CipherSuite,
 *      which has been previously set by the server via the ServerHello message.
 * - Same with DtlsClientKeyExchange
 */
public class DtlsServerKeyExchange extends DtlsFormat {

    public static final int MIN_LENGTH = DtlsHandshakeCommonBody.LENGTH;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody;
    private byte[] encryptedPreMasterSecretData;

    public DtlsServerKeyExchange(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, byte[] encryptedPreMasterSecretData) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.encryptedPreMasterSecretData = encryptedPreMasterSecretData;
    }

    public DtlsServerKeyExchange() {}

    public DtlsServerKeyExchange(byte[] data) {
        if (data.length >= MIN_LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

            int remainLength = (int) dtlsHandshakeCommonBody.getFragmentLength();
            if (remainLength > 0) {
                encryptedPreMasterSecretData = new byte[remainLength];
                System.arraycopy(data, index, encryptedPreMasterSecretData, 0, remainLength);
            }
        }
    }

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
