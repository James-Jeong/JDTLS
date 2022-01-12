package network.dtls.type;

import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;

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
        // TODO
    }

    @Override
    public byte[] getData() {
        // TODO
        int index = 0;
        return null;
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
