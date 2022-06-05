package dtls.type;

import dtls.type.base.DtlsFormat;

/**
 * - It provides the server with the necessary data
 *      to generate the keys for the symmetric encryption.
 * - Same with DtlsServerKeyExchange
 */
public class DtlsClientKeyExchange extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int MIN_LENGTH = 0;

    transient private byte[] encryptedPreMasterSecretData = null;
    private int encryptedPreMasterSecretDataLength = 0;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsClientKeyExchange() {}

    public DtlsClientKeyExchange(byte[] data) {
        if (data != null) {
            int index = 0;

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
        if (encryptedPreMasterSecretData == null) { return null; }

        int index = 0;
        byte[] data = null;

        if (encryptedPreMasterSecretData.length > 0) {
            data = new byte[encryptedPreMasterSecretData.length];
            System.arraycopy(encryptedPreMasterSecretData, 0, data, index, encryptedPreMasterSecretData.length);
        }

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
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
