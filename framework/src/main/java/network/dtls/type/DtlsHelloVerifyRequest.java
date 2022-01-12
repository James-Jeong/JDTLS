package network.dtls.type;

import network.dtls.packet.base.DtlsProtocolVersion;
import network.dtls.type.base.DtlsFormat;
import network.dtls.type.base.DtlsHandshakeCommonBody;

public class DtlsHelloVerifyRequest extends DtlsFormat {

    public static final int LENGTH = DtlsHandshakeCommonBody.LENGTH + 3;

    private DtlsHandshakeCommonBody dtlsHandshakeCommonBody; // 12 bytes
    private DtlsProtocolVersion protocolVersion; // 2 bytes
    private short cookieLength; // 1 byte

    public DtlsHelloVerifyRequest(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, DtlsProtocolVersion protocolVersion, short cookieLength) {
        this.dtlsHandshakeCommonBody = dtlsHandshakeCommonBody;
        this.protocolVersion = protocolVersion;
        this.cookieLength = cookieLength;
    }

    public DtlsHelloVerifyRequest() {}

    public DtlsHelloVerifyRequest(byte[] data) {
        if (data.length == LENGTH) {
            // TODO
        }
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

    public DtlsProtocolVersion getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(DtlsProtocolVersion protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public short getCookieLength() {
        return cookieLength;
    }

    public void setCookieLength(short cookieLength) {
        this.cookieLength = cookieLength;
    }

}
