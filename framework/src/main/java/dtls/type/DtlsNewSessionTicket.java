package dtls.type;

import dtls.ticket.TlsSessionTicket;
import dtls.type.base.DtlsFormat;

public class DtlsNewSessionTicket extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int LENGTH = TlsSessionTicket.MIN_LENGTH;

    private TlsSessionTicket tlsSessionTicket; // 166 bytes
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsNewSessionTicket(TlsSessionTicket tlsSessionTicket) {
        this.tlsSessionTicket = tlsSessionTicket;
    }

    public DtlsNewSessionTicket() {}

    public DtlsNewSessionTicket(byte[] data) {
        if (data != null && data.length == LENGTH) {
            int index = 0;

            byte[] tlsSessionTicketData = new byte[TlsSessionTicket.MIN_LENGTH];
            System.arraycopy(data, index, tlsSessionTicketData, 0, TlsSessionTicket.MIN_LENGTH);
            tlsSessionTicket = new TlsSessionTicket(tlsSessionTicketData);
        }
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        if (tlsSessionTicket == null) { return null; }

        int index = 0;
        byte[] data = new byte[LENGTH];

        byte[] tlsSessionTicketData = tlsSessionTicket.getData();
        System.arraycopy(tlsSessionTicketData, 0, data, index, TlsSessionTicket.MIN_LENGTH);

        return data;
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public TlsSessionTicket getTlsSessionTicket() {
        return tlsSessionTicket;
    }

    public void setTlsSessionTicket(TlsSessionTicket tlsSessionTicket) {
        this.tlsSessionTicket = tlsSessionTicket;
    }
    ////////////////////////////////////////////////////////////

}
