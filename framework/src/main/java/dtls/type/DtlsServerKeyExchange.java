package dtls.type;

import dtls.type.base.DtlsFormat;

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

    ////////////////////////////////////////////////////////////
    public static final int MIN_LENGTH = 0;

    transient private byte[] encryptedPreMasterSecretData = null;
    private int encryptedPreMasterSecretDataLength = 0;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsServerKeyExchange() {}

    public DtlsServerKeyExchange(byte[] data) {
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
