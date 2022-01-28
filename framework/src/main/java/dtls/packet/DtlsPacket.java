package dtls.packet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.packet.handshake.DtlsEncryptedHandShake;
import dtls.packet.handshake.DtlsHandshake;
import dtls.packet.recordlayer.DtlsRecordHeader;
import dtls.packet.recordlayer.DtlsRecordLayer;
import dtls.type.base.DtlsHandshakeCommonBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Single or Compound 가능
 */
public class DtlsPacket {

    ////////////////////////////////////////////////////////////
    private List<DtlsRecordLayer> dtlsRecordLayerList = null;

    private int length = 0;
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsPacket(List<DtlsRecordLayer> dtlsHandShakeList) {
        this.dtlsRecordLayerList = dtlsHandShakeList;
    }

    public DtlsPacket() {}

    public DtlsPacket(byte[] data) {
        if (data.length > DtlsRecordHeader.LENGTH) {
            int index = 0;
            dtlsRecordLayerList = new ArrayList<>();

            // Packet Parsing
            while(index < data.length) {
                byte[] dtlsRecordHeaderData = new byte[DtlsRecordHeader.LENGTH];
                System.arraycopy(data, index, dtlsRecordHeaderData, 0, DtlsRecordHeader.LENGTH);
                index += DtlsRecordHeader.LENGTH;

                DtlsRecordHeader dtlsRecordHeader = new DtlsRecordHeader(dtlsRecordHeaderData);
                int length = dtlsRecordHeader.getLength();
                if (length > 0) {
                    DtlsRecordLayer dtlsRecordLayer;
                    int epoch = dtlsRecordHeader.getEpoch();
                    if (epoch != 0) { // Encrypted
                        byte[] encryptedHandShakeData = new byte[length];
                        System.arraycopy(data, index, encryptedHandShakeData, 0, length);
                        index += length;

                        DtlsEncryptedHandShake dtlsEncryptedHandShake = new DtlsEncryptedHandShake(encryptedHandShakeData);
                        dtlsRecordLayer = new DtlsRecordLayer(dtlsRecordHeader, dtlsEncryptedHandShake);
                    } else { // Not encrypted
                        byte[] dtlsHandShakeCommonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
                        System.arraycopy(data, index, dtlsHandShakeCommonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
                        index += DtlsHandshakeCommonBody.LENGTH;
                        DtlsHandshakeCommonBody dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(dtlsHandShakeCommonBodyData);

                        int handshakeDataLength = (int) dtlsHandshakeCommonBody.getLength();
                        if (handshakeDataLength == 0) { continue; }

                        byte[] dtlsHandShakeData = new byte[handshakeDataLength];
                        System.arraycopy(data, index, dtlsHandShakeData, 0, handshakeDataLength);
                        index += handshakeDataLength;

                        DtlsHandshake dtlsHandshake = new DtlsHandshake(dtlsHandshakeCommonBody.getHandshakeType(), dtlsHandShakeData);
                        dtlsRecordLayer = new DtlsRecordLayer(dtlsRecordHeader, dtlsHandshake);
                    }

                    dtlsRecordLayerList.add(dtlsRecordLayer);
                }
            }
            length = index;
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public byte[] getData() {
        if (dtlsRecordLayerList == null || length == 0) { return null; }

        int index = 0;
        byte[] data = new byte[length];

        for (DtlsRecordLayer dtlsRecordLayer : dtlsRecordLayerList) {
            if (dtlsRecordLayer == null) { continue; }

            byte[] dtlsRecordLayerData = dtlsRecordLayer.getData();
            System.arraycopy(dtlsRecordLayerData, 0, data, index, dtlsRecordLayerData.length);
            index += dtlsRecordLayerData.length;
        }

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public List<DtlsRecordLayer> getDtlsRecordLayerList() {
        return dtlsRecordLayerList;
    }

    public void setDtlsRecordLayerList(List<DtlsRecordLayer> dtlsRecordLayerList) {
        this.dtlsRecordLayerList = dtlsRecordLayerList;
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
