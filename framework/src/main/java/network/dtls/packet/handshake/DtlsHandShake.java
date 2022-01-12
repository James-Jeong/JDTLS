package network.dtls.packet.handshake;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import network.dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsHandShake {

    private DtlsHandshakeHeader dtlsHandshakeHeader;
    private DtlsHandshakeBody dtlsHandshakeBody;

    public DtlsHandShake(DtlsHandshakeHeader dtlsHandshakeHeader, DtlsHandshakeBody dtlsHandshakeBody) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
        this.dtlsHandshakeBody = dtlsHandshakeBody;
    }

    public DtlsHandShake() {}

    public DtlsHandShake(byte[] data) {
        if (data.length >= DtlsHandshakeHeader.LENGTH) {
            int index = 0;

            byte[] headerData = new byte[DtlsHandshakeHeader.LENGTH];
            System.arraycopy(data, index, headerData, 0, DtlsHandshakeHeader.LENGTH);
            dtlsHandshakeHeader = new DtlsHandshakeHeader(headerData);
            index += DtlsHandshakeHeader.LENGTH;

            int remainLength = data.length -  DtlsHandshakeHeader.LENGTH;
            if (remainLength >= DtlsHandshakeCommonBody.LENGTH) {
                byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
                System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
                DtlsHandshakeCommonBody dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
                index += DtlsHandshakeCommonBody.LENGTH;

                remainLength = data.length - DtlsHandshakeHeader.LENGTH - DtlsHandshakeCommonBody.LENGTH;
                if (remainLength > 0) {
                    byte[] bodyData = new byte[remainLength];
                    System.arraycopy(data, index, bodyData, 0, remainLength);
                    dtlsHandshakeBody = new DtlsHandshakeBody(dtlsHandshakeCommonBody.getHandshakeType(), bodyData);
                }
            }
        }
    }

    public byte[] getData() {
        if (dtlsHandshakeHeader == null || dtlsHandshakeBody == null) { return null; }

        byte[] headerData = dtlsHandshakeHeader.getData();
        byte[] bodyData = dtlsHandshakeBody.getData();

        byte[] data = new byte[headerData.length + bodyData.length];
        System.arraycopy(headerData, 0, data, 0, headerData.length);
        System.arraycopy(bodyData, 0, data, headerData.length, bodyData.length);
        return data;
    }

    public DtlsHandshakeHeader getDtlsHandshakeHeader() {
        return dtlsHandshakeHeader;
    }

    public void setDtlsHandshakeHeader(DtlsHandshakeHeader dtlsHandshakeHeader) {
        this.dtlsHandshakeHeader = dtlsHandshakeHeader;
    }

    public DtlsHandshakeBody getDtlsHandshakeBody() {
        return dtlsHandshakeBody;
    }

    public void setDtlsHandshakeBody(DtlsHandshakeBody dtlsHandshakeBody) {
        this.dtlsHandshakeBody = dtlsHandshakeBody;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
