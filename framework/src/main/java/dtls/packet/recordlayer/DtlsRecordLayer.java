package dtls.packet.recordlayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.handshake.DtlsEncryptedHandShake;
import dtls.packet.handshake.DtlsHandshake;
import dtls.packet.base.DtlsRecordFactory;

public class DtlsRecordLayer {

    ////////////////////////////////////////////////////////////
    private DtlsRecordHeader dtlsRecordHeader = null;
    private DtlsRecordFactory dtlsHandshakeFactory = null;

    private int length = 0;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsRecordLayer(DtlsRecordHeader recordHeader, DtlsRecordFactory dtlsHandshakeFactory) {
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
        if (data != null && data.length >= DtlsRecordHeader.LENGTH) {
            int index = 0;

            byte[] dtlsRecordHeaderData = new byte[DtlsRecordHeader.LENGTH];
            System.arraycopy(data, index, dtlsRecordHeaderData, 0, DtlsRecordHeader.LENGTH);
            dtlsRecordHeader = new DtlsRecordHeader(dtlsRecordHeaderData);
            index += DtlsRecordHeader.LENGTH;

            int remainDataLength = data.length - DtlsRecordHeader.LENGTH;
            if (remainDataLength > 0) {
                int length = dtlsRecordHeader.getLength();
                if (length > 0) {
                    byte[] handShakeData = new byte[length];
                    System.arraycopy(data, index, handShakeData, 0, length);

                    int epoch = dtlsRecordHeader.getEpoch();
                    if (epoch != 0) { // Encrypted
                        dtlsHandshakeFactory = new DtlsEncryptedHandShake(handShakeData);
                    } else { // Not encrypted
                        dtlsHandshakeFactory = new DtlsHandshake(handShakeData);
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
        if (dtlsHandshakeFactoryData != null && dtlsHandshakeFactoryData.length > 0) {
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

    public DtlsRecordFactory getDtlsHandshakeFactory() {
        return dtlsHandshakeFactory;
    }

    public void setDtlsHandshakeFactory(DtlsRecordFactory dtlsHandshakeFactory) {
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
