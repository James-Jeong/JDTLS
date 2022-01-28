package dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.handshake.DtlsEncryptedHandShake;
import dtls.packet.handshake.DtlsHandshake;
import dtls.packet.handshake.DtlsHandshakeFactory;
import dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsRecordLayer {

    ////////////////////////////////////////////////////////////
    private DtlsRecordHeader dtlsRecordHeader = null;
    private DtlsHandshakeFactory dtlsHandshakeFactory = null;

    private int length = 0;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsRecordLayer(DtlsRecordHeader recordHeader, DtlsHandshakeFactory dtlsHandshakeFactory) {
        this.dtlsRecordHeader = recordHeader;
        this.dtlsHandshakeFactory = dtlsHandshakeFactory;

        int dtlsHandshakeLength = 0;
        if (this.dtlsHandshakeFactory.getData() != null) {
            dtlsHandshakeLength = this.dtlsHandshakeFactory.getData().length;
        }

        this.length = DtlsRecordHeader.LENGTH + dtlsHandshakeLength;
    }

    public DtlsRecordLayer() {}

    public DtlsRecordLayer(byte[] data) {
        if (data.length >= DtlsRecordHeader.LENGTH) {
            int index = 0;

            byte[] dtlsRecordHeaderData = new byte[DtlsRecordHeader.LENGTH];
            System.arraycopy(data, index, dtlsRecordHeaderData, 0, DtlsRecordHeader.LENGTH);
            dtlsRecordHeader = new DtlsRecordHeader(dtlsRecordHeaderData);
            index += DtlsRecordHeader.LENGTH;

            int remainDataLength = data.length - DtlsRecordHeader.LENGTH;
            if (remainDataLength > 0) {
                int length = dtlsRecordHeader.getLength();
                if (length > 0) {
                    int epoch = dtlsRecordHeader.getEpoch();
                    if (epoch != 0) { // Encrypted
                        byte[] encryptedHandShakeData = new byte[length];
                        System.arraycopy(data, index, encryptedHandShakeData, 0, length);
                        index += length;
                        dtlsHandshakeFactory = new DtlsEncryptedHandShake(encryptedHandShakeData);
                    } else { // Not encrypted
                        byte[] dtlsHandShakeCommonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
                        System.arraycopy(data, index, dtlsHandShakeCommonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
                        index += DtlsHandshakeCommonBody.LENGTH;
                        DtlsHandshakeCommonBody dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(dtlsHandShakeCommonBodyData);

                        int handshakeDataLength = (int) dtlsHandshakeCommonBody.getLength();
                        if (handshakeDataLength > 0) {
                            byte[] dtlsHandShakeData = new byte[handshakeDataLength];
                            System.arraycopy(data, index, dtlsHandShakeData, 0, handshakeDataLength);
                            index += handshakeDataLength;
                            dtlsHandshakeFactory = new DtlsHandshake(dtlsHandshakeCommonBody.getHandshakeType(), dtlsHandShakeData);
                        }
                    }
                }
            }
            length = index;
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public byte[] getData() {
        if (dtlsRecordHeader == null || dtlsHandshakeFactory == null || length == 0) { return null; }

        int index = 0;
        byte[] data = new byte[length];

        byte[] dtlsRecordHeaderData = dtlsRecordHeader.getData();
        System.arraycopy(dtlsRecordHeaderData, 0, data, index, DtlsRecordHeader.LENGTH);
        index += DtlsRecordHeader.LENGTH;

        byte[] dtlsHandshakeFactoryData = dtlsHandshakeFactory.getData();
        if (dtlsHandshakeFactoryData.length > 0) {
            System.arraycopy(dtlsHandshakeFactoryData, 0, data, index, dtlsHandshakeFactoryData.length);
        }

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsRecordHeader getDtlsRecordHeader() {
        return dtlsRecordHeader;
    }

    public void setDtlsRecordHeader(DtlsRecordHeader dtlsRecordHeader) {
        this.dtlsRecordHeader = dtlsRecordHeader;
    }

    public DtlsHandshakeFactory getDtlsHandshakeFactory() {
        return dtlsHandshakeFactory;
    }

    public void setDtlsHandshakeFactory(DtlsHandshakeFactory dtlsHandshakeFactory) {
        this.dtlsHandshakeFactory = dtlsHandshakeFactory;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
    ////////////////////////////////////////////////////////////

}
