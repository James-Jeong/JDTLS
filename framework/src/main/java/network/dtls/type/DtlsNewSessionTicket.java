package network.dtls.type;

import network.dtls.ticket.TlsSessionTicket;
import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsNewSessionTicket extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH + TlsSessionTicket.MIN_LENGTH;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody; // 12 bytes
    private TlsSessionTicket tlsSessionTicket; // 166 bytes

    public DtlsNewSessionTicket(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, TlsSessionTicket tlsSessionTicket) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.tlsSessionTicket = tlsSessionTicket;
    }

    public DtlsNewSessionTicket() {}

    public DtlsNewSessionTicket(byte[] data) {
        if (data.length == LENGTH) {
            int index = 0;

            byte[] commonBodyData = new byte[DtlsHandshakeCommonBody.LENGTH];
            System.arraycopy(data, index, commonBodyData, 0, DtlsHandshakeCommonBody.LENGTH);
            dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(commonBodyData);
            index += commonBodyData.length;

            byte[] tlsSessionTicketData = new byte[TlsSessionTicket.MIN_LENGTH];
            System.arraycopy(data, index, tlsSessionTicketData, 0, TlsSessionTicket.MIN_LENGTH);
            tlsSessionTicket = new TlsSessionTicket(tlsSessionTicketData);
        }
    }

    @Override
    public byte[] getData() {
        if (dtlsHandshakeCommonBody == null || tlsSessionTicket == null) { return null; }

        int index = 0;
        byte[] data = new byte[LENGTH];

        byte[] commonBodyData = dtlsHandshakeCommonBody.getData();
        System.arraycopy(commonBodyData, 0, data, index, DtlsHandshakeCommonBody.LENGTH);
        index += DtlsHandshakeCommonBody.LENGTH;

        byte[] tlsSessionTicketData = tlsSessionTicket.getData();
        System.arraycopy(tlsSessionTicketData, 0, data, index, TlsSessionTicket.MIN_LENGTH);

        return data;
    }

    public DtlsHandshakeCommonBody getDtlsHandshakeCommonBody() {
        return dtlsHandshakeCommonBody;
    }

    public void setDtlsHandshakeCommonBody(DtlsHandshakeCommonBody dtlsHandshakeCommonBody) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
    }

    public TlsSessionTicket getTlsSessionTicket() {
        return tlsSessionTicket;
    }

    public void setTlsSessionTicket(TlsSessionTicket tlsSessionTicket) {
        this.tlsSessionTicket = tlsSessionTicket;
    }

}
