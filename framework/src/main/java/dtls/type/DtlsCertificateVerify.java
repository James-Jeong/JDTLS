package dtls.type;

import dtls.type.base.DtlsFormat;

public class DtlsCertificateVerify extends DtlsFormat {

    ////////////////////////////////////////////////////////////
    public static final int LENGTH = 0;

    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    public DtlsCertificateVerify() {}

    public DtlsCertificateVerify(byte[] data) {
        // TODO
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    @Override
    public byte[] getData() {
        return new byte[0];
    }
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////

}
