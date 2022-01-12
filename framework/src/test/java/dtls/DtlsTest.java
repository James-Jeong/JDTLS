package dtls;

import network.dtls.cipher.DtlsCipherSuite;
import network.dtls.cipher.DtlsCipherSuiteList;
import network.dtls.cipher.DtlsCipherSuiteType;
import network.dtls.compression.DtlsCompressionMethod;
import network.dtls.packet.DtlsPacket;
import network.dtls.packet.base.DtlsProtocolVersion;
import network.dtls.type.DtlsClientHello;
import network.dtls.type.base.DtlsHandshakeCommonBody;
import network.dtls.type.base.DtlsHandshakeType;
import network.dtls.type.base.DtlsRandom;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.module.ByteUtil;

import java.util.ArrayList;
import java.util.List;

public class DtlsTest {

    private static final Logger logger = LoggerFactory.getLogger(DtlsTest.class);

    @Test
    public void test() {
        Assert.assertNotNull(createDtlsCipherSuiteListTest());
        Assert.assertNotNull(createDtlsClientHelloTest());
        //createDtlsPacketTest();
    }

    /*public static DtlsPacket createDtlsPacketTest() {
        return null;
    }*/

    public static DtlsCipherSuiteList createDtlsCipherSuiteListTest() {
        List<DtlsCipherSuite> dtlsCipherSuiteLists = new ArrayList<>();
        dtlsCipherSuiteLists.add(new DtlsCipherSuite(DtlsCipherSuiteType.TLS_RSA_EXPORT_WITH_RC4_40_MD5));
        dtlsCipherSuiteLists.add(new DtlsCipherSuite(DtlsCipherSuiteType.TLS_RSA_WITH_RC4_128_MD5));
        dtlsCipherSuiteLists.add(new DtlsCipherSuite(DtlsCipherSuiteType.TLS_KRB5_EXPORT_WITH_RC4_40_SHA));
        dtlsCipherSuiteLists.add(new DtlsCipherSuite(DtlsCipherSuiteType.TLS_RSA_EXPORT1024_WITH_RC4_56_SHA));
        dtlsCipherSuiteLists.add(new DtlsCipherSuite(DtlsCipherSuiteType.TLS_PSK_WITH_RC4_128_SHA));
        dtlsCipherSuiteLists.add(new DtlsCipherSuite(DtlsCipherSuiteType.TLS_ECDH_RSA_WITH_RC4_128_SHA));
        dtlsCipherSuiteLists.add(new DtlsCipherSuite(DtlsCipherSuiteType.TLS_ECDHE_PSK_WITH_RC4_128_SHA));

        DtlsCipherSuiteList dtlsCipherSuiteList = new DtlsCipherSuiteList(dtlsCipherSuiteLists);
        //logger.debug("[DtlsTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList: {}", dtlsCipherSuiteList);

        // ENCODING
        byte[] dtlsCipherSuiteListData = dtlsCipherSuiteList.getData();
        Assert.assertNotNull(dtlsCipherSuiteListData);
        //logger.debug("[DtlsTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList byte data: {}", dtlsCipherSuiteListData);
        //logger.debug("[DtlsTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList byte data: {}", ByteUtil.byteArrayToHex(dtlsCipherSuiteListData));

        // DECODING
        dtlsCipherSuiteList = new DtlsCipherSuiteList(dtlsCipherSuiteListData);
        //logger.debug("[DtlsTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList: {}", dtlsCipherSuiteList);

        return dtlsCipherSuiteList;
    }

    public static DtlsClientHello createDtlsClientHelloTest() {
        DtlsCipherSuiteList dtlsCipherSuiteList = createDtlsCipherSuiteListTest();
        DtlsHandshakeCommonBody dtlsHandshakeCommonBody = new DtlsHandshakeCommonBody(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_CLIENT_HELLO),
                DtlsClientHello.MIN_LENGTH + dtlsCipherSuiteList.getTotalLength(), 0,
                0, DtlsClientHello.MIN_LENGTH + dtlsCipherSuiteList.getTotalLength()
        );

        DtlsClientHello dtlsClientHello = new DtlsClientHello(
                dtlsHandshakeCommonBody,
                new DtlsProtocolVersion(DtlsProtocolVersion.DTLS_1_0), DtlsRandom.getRandom(),
                (short) 8, (short) 0,
                dtlsCipherSuiteList.getTotalLength(), dtlsCipherSuiteList,
                (short) 1, new DtlsCompressionMethod(DtlsCompressionMethod.TLS_COMPRESSION_METHOD_NULL)
        );
        logger.debug("[DtlsTest][createDtlsClientHelloTest] DtlsClientHello: {}", dtlsClientHello);

        // ENCODING
        byte[] dtlsClientHelloData = dtlsClientHello.getData();
        logger.debug("[DtlsTest][createDtlsClientHelloTest] DtlsClientHello byte data: {}", dtlsClientHelloData);
        logger.debug("[DtlsTest][createDtlsClientHelloTest] DtlsClientHello byte data: {}", ByteUtil.byteArrayToHex(dtlsClientHelloData));

        // DECODING
        dtlsClientHello = new DtlsClientHello(dtlsClientHelloData);
        logger.debug("[DtlsTest][createDtlsClientHelloTest] DtlsClientHello: {}", dtlsClientHello);

        return dtlsClientHello;
    }


}
