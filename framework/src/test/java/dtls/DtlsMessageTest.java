package dtls;

import dtls.certificate.Certificates;
import dtls.cipher.DtlsCipherSuite;
import dtls.cipher.DtlsCipherSuiteList;
import dtls.cipher.DtlsCipherSuiteType;
import dtls.compression.DtlsCompressionMethodType;
import dtls.packet.DtlsPacket;
import dtls.packet.base.DtlsContentType;
import dtls.packet.base.DtlsProtocolVersion;
import dtls.packet.handshake.DtlsEncryptedHandShake;
import dtls.packet.handshake.DtlsHandshake;
import dtls.packet.recordlayer.DtlsRecordHeader;
import dtls.packet.recordlayer.DtlsRecordLayer;
import dtls.packet.recordlayer.message.DtlsApplicationData;
import dtls.packet.recordlayer.message.DtlsChangeCipherSpec;
import dtls.type.*;
import dtls.type.base.DtlsFormat;
import dtls.type.base.DtlsHandshakeCommonBody;
import dtls.type.base.DtlsHandshakeType;
import dtls.type.base.DtlsRandom;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.module.ByteUtil;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

public class DtlsMessageTest {

    private static final Logger logger = LoggerFactory.getLogger(DtlsMessageTest.class);

    @Test
    public void test() {
        /////////////////////////////////////////////////////////
        // DtlsCipherSuiteList
        DtlsCipherSuiteList dtlsCipherSuiteList = createDtlsCipherSuiteListTest();
        Assert.assertNotNull(dtlsCipherSuiteList);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsClientHello
        DtlsClientHello dtlsClientHello = createDtlsClientHelloTest();
        Assert.assertNotNull(dtlsClientHello);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsHelloVerifyRequest
        DtlsHelloVerifyRequest dtlsHelloVerifyRequest = createDtlsHelloVerifyRequestTest();
        Assert.assertNotNull(dtlsHelloVerifyRequest);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsServerHello
        DtlsServerHello dtlsServerHello = createDtlsServerHelloTest();
        Assert.assertNotNull(dtlsServerHello);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsCertificate
        DtlsCertificate dtlsCertificate = createDtlsCertificateTest();
        Assert.assertNotNull(dtlsCertificate);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsServerKeyExchange
        DtlsServerKeyExchange dtlsServerKeyExchange = createDtlsServerKeyExchangeTest();
        Assert.assertNotNull(dtlsServerKeyExchange);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsServerHelloDone
        DtlsServerHelloDone dtlsServerHelloDone = createDtlsServerHelloDoneTest();
        Assert.assertNotNull(dtlsServerHelloDone);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsChangeCipherSpecMessage
        DtlsChangeCipherSpec dtlsChangeCipherSpecMessage = createDtlsChangeCipherSpecTest();
        Assert.assertNotNull(dtlsChangeCipherSpecMessage);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsApplicationData
        DtlsApplicationData dtlsApplicationData = createDtlsApplicationData();
        Assert.assertNotNull(dtlsApplicationData);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsFinished
        DtlsFinished dtlsFinished = createDtlsFinishedTest();
        Assert.assertNotNull(dtlsFinished);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsHandshake with DtlsFinished
        // 1) OBJECT
        DtlsHandshakeCommonBody dtlsFinishedHandshakeCommonBody = createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_FINISHED),
                0
        );
        DtlsHandshake dtlsHandshake = createDtlsHandshakeByObjectTest(dtlsFinishedHandshakeCommonBody, dtlsFinished);
        Assert.assertNotNull(dtlsHandshake);

        // 2) BYTE DATA
        dtlsHandshake = createDtlsHandshakeByByteDataTest(dtlsFinished.getData());
        Assert.assertNotNull(dtlsHandshake);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // EncryptedDtlsHandshake
        DtlsEncryptedHandShake dtlsEncryptedHandShake = createDtlsEncryptedHandshakeTest();
        Assert.assertNotNull(dtlsEncryptedHandShake);
        /////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////
        // DtlsPacket with DtlsClientHello
        DtlsHandshakeCommonBody dtlsClientHelloHandshakeCommonBody = createDtlsCommonBodyTest(
                new DtlsHandshakeType(DtlsHandshakeType.TLS_TYPE_CLIENT_HELLO),
                dtlsClientHello.getData().length
        );
        dtlsHandshake = createDtlsHandshakeByObjectTest(dtlsClientHelloHandshakeCommonBody, dtlsClientHello);
        Assert.assertNotNull(dtlsHandshake);

        // 1) DtlsRecordHeader
        byte[] dtlsHandshakeData = dtlsHandshake.getData();
        DtlsRecordHeader dtlsRecordHeader = createDtlsRecordHeaderTest(DtlsContentType.TLS_TYPE_HANDSHAKE, dtlsHandshakeData.length);

        // 2) DtlsHandshakeFactory
        // Already exists

        // 3) DtlsRecordLayer List
        List<DtlsRecordLayer> dtlsRecordLayerList = new ArrayList<>();
        DtlsRecordLayer dtlsRecordLayer = new DtlsRecordLayer(dtlsRecordHeader, dtlsHandshake);
        dtlsRecordLayerList.add(dtlsRecordLayer);

        // 4) DtlsPacket
        DtlsPacket dtlsPacket = createDtlsPacketTest(dtlsRecordLayerList);
        Assert.assertNotNull(dtlsPacket);
        /////////////////////////////////////////////////////////
    }

    public static DtlsRecordHeader createDtlsRecordHeaderTest(int dtlsContentType, int recordLength) {
        DtlsRecordHeader dtlsRecordHeader = new DtlsRecordHeader(
                new DtlsContentType(dtlsContentType),
                new DtlsProtocolVersion(DtlsProtocolVersion.DTLS_1_0),
                0,
                0,
                recordLength
        );
        logger.debug("[DtlsMessageTest][createDtlsRecordHeaderTest] DtlsRecordHeader: {}", dtlsRecordHeader);

        return dtlsRecordHeader;
    }

    public static DtlsPacket createDtlsPacketTest(List<DtlsRecordLayer> dtlsRecordLayerList) {
        DtlsPacket dtlsPacket = new DtlsPacket(dtlsRecordLayerList);
        logger.debug("[DtlsMessageTest][createDtlsPacketTest] DtlsPacket: {}", dtlsPacket);

        return dtlsPacket;
    }

    public static DtlsHandshake createDtlsHandshakeByObjectTest(DtlsHandshakeCommonBody dtlsHandshakeCommonBody, DtlsFormat dtlsFormat) {
        DtlsHandshake dtlsHandshake = new DtlsHandshake(
                dtlsHandshakeCommonBody,
                dtlsFormat
        );
        logger.debug("[DtlsMessageTest][createDtlsHandshakeByObjectTest] DtlsHandshake: {}", dtlsHandshake);

        return dtlsHandshake;
    }

    public static DtlsHandshake createDtlsHandshakeByByteDataTest(byte[] data) {
        DtlsHandshake dtlsHandshake = new DtlsHandshake(data);
        logger.debug("[DtlsMessageTest][createDtlsHandshakeByByteDataTest] DtlsHandshake: {}", dtlsHandshake);

        return dtlsHandshake;
    }

    public static DtlsEncryptedHandShake createDtlsEncryptedHandshakeTest() {
        byte[] encryptedMessage = new byte[] { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08 };

        DtlsEncryptedHandShake dtlsEncryptedHandShake = new DtlsEncryptedHandShake(encryptedMessage);
        logger.debug("[DtlsMessageTest][createDtlsEncryptedHandshakeTest] DtlsEncryptedHandShake: {}", dtlsEncryptedHandShake);

        return dtlsEncryptedHandShake;
    }

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
        //logger.debug("[DtlsMessageTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList: {}", dtlsCipherSuiteList);

        // ENCODING
        byte[] dtlsCipherSuiteListData = dtlsCipherSuiteList.getData();
        Assert.assertNotNull(dtlsCipherSuiteListData);
        //logger.debug("[DtlsMessageTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList byte data: {}", dtlsCipherSuiteListData);
        //logger.debug("[DtlsMessageTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList byte data: {}", ByteUtil.byteArrayToHex(dtlsCipherSuiteListData));

        // DECODING
        dtlsCipherSuiteList = new DtlsCipherSuiteList(dtlsCipherSuiteListData);
        //logger.debug("[DtlsMessageTest][createDtlsCipherSuiteListTest] DtlsCipherSuiteList: {}", dtlsCipherSuiteList);

        return dtlsCipherSuiteList;
    }

    public static DtlsHandshakeCommonBody createDtlsCommonBodyTest(DtlsHandshakeType dtlsHandshakeType, int length) {
        return new DtlsHandshakeCommonBody(
                new DtlsHandshakeType(dtlsHandshakeType.getType()),
                length, 0,
                0, length
        );
    }

    public static DtlsClientHello createDtlsClientHelloTest() {
        DtlsCipherSuiteList dtlsCipherSuiteList = createDtlsCipherSuiteListTest();
        DtlsClientHello dtlsClientHello = new DtlsClientHello(
                new DtlsProtocolVersion(DtlsProtocolVersion.DTLS_1_0),
                DtlsRandom.getRandom(),
                (short) 8, "12345678".getBytes(StandardCharsets.UTF_8),
                (short) 0, null,
                dtlsCipherSuiteList.getTotalLength(), dtlsCipherSuiteList,
                (short) 1, new DtlsCompressionMethodType(DtlsCompressionMethodType.TLS_COMPRESSION_METHOD_NULL)
        );
        logger.debug("[DtlsMessageTest][createDtlsClientHelloTest] DtlsClientHello: {}", dtlsClientHello);

        // ENCODING
        byte[] dtlsClientHelloData = dtlsClientHello.getData();
        logger.debug("[DtlsMessageTest][createDtlsClientHelloTest] DtlsClientHello byte data: {}", dtlsClientHelloData);
        logger.debug("[DtlsMessageTest][createDtlsClientHelloTest] DtlsClientHello byte data: {}", ByteUtil.byteArrayToHex(dtlsClientHelloData));

        // DECODING
        dtlsClientHello = new DtlsClientHello(dtlsClientHelloData);
        logger.debug("[DtlsMessageTest][createDtlsClientHelloTest] DtlsClientHello: {}", dtlsClientHello);

        return dtlsClientHello;
    }

    public static DtlsHelloVerifyRequest createDtlsHelloVerifyRequestTest() {
        DtlsHelloVerifyRequest dtlsHelloVerifyRequest = new DtlsHelloVerifyRequest(
                new DtlsProtocolVersion(DtlsProtocolVersion.DTLS_1_0),
                (short) 0, null
        );
        logger.debug("[DtlsMessageTest][createDtlsHelloVerifyRequest] DtlsHelloVerifyRequest: {}", dtlsHelloVerifyRequest);

        // ENCODING
        byte[] dtlsHelloVerifyRequestData = dtlsHelloVerifyRequest.getData();
        logger.debug("[DtlsMessageTest][createDtlsHelloVerifyRequest] DtlsHelloVerifyRequest byte data: {}", dtlsHelloVerifyRequestData);
        logger.debug("[DtlsMessageTest][createDtlsHelloVerifyRequest] DtlsHelloVerifyRequest byte data: {}", ByteUtil.byteArrayToHex(dtlsHelloVerifyRequestData));

        // DECODING
        dtlsHelloVerifyRequest = new DtlsHelloVerifyRequest(dtlsHelloVerifyRequestData);
        logger.debug("[DtlsMessageTest][createDtlsHelloVerifyRequest] DtlsHelloVerifyRequest: {}", dtlsHelloVerifyRequestData);

        return dtlsHelloVerifyRequest;
    }

    public static DtlsServerHello createDtlsServerHelloTest() {
        DtlsServerHello dtlsServerHello = new DtlsServerHello(
                new DtlsProtocolVersion(DtlsProtocolVersion.DTLS_1_0),
                DtlsRandom.getRandom(),
                (short) 8, new byte[] { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08 },
                new DtlsCipherSuite(DtlsCipherSuiteType.TLS_RSA_WITH_RC4_128_MD5),
                new DtlsCompressionMethodType(DtlsCompressionMethodType.TLS_COMPRESSION_METHOD_NULL)
        );

        logger.debug("[DtlsMessageTest][createDtlsServerHelloTest] DtlsServerHello: {}", dtlsServerHello);

        // ENCODING
        byte[] dtlsHelloVedtlsServerHelloData = dtlsServerHello.getData();
        logger.debug("[DtlsMessageTest][createDtlsServerHelloTest] DtlsServerHello byte data: {}", dtlsHelloVedtlsServerHelloData);
        logger.debug("[DtlsMessageTest][createDtlsServerHelloTest] DtlsServerHello byte data: {}", ByteUtil.byteArrayToHex(dtlsHelloVedtlsServerHelloData));

        // DECODING
        dtlsServerHello = new DtlsServerHello(dtlsHelloVedtlsServerHelloData);
        logger.debug("[DtlsMessageTest][createDtlsServerHelloTest] DtlsServerHello: {}", dtlsHelloVedtlsServerHelloData);

        return dtlsServerHello;
    }

    public static DtlsCertificate createDtlsCertificateTest() {
        byte[] certificateData;
        try {
            String certificateFileName = "/Users/jamesj/GIT_PROJECTS/JDTLS/framework/src/test/resources/dtls/certificate/x509v3_md5_rsa_certificate";
            FileInputStream fileInputStream = new FileInputStream(certificateFileName);

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            Certificate certificate = certificateFactory.generateCertificate(fileInputStream);
            certificateData = certificate.getEncoded();

            PublicKey publicKey = certificate.getPublicKey();
            logger.debug("[DtlsMessageTest][createDtlsCertificateTest] Public key: [{}], \ncertificate.getEncoded(): {}", publicKey, certificateData);
        } catch (Exception e) {
            logger.debug("[DtlsMessageTest][createDtlsCertificateTest] Certificate Exception", e);
            return null;
        }

        Certificates certificates = new Certificates(
                certificateData.length,
                certificateData
        );
        byte[] certificatesData = certificates.getData();

        DtlsCertificate dtlsCertificate = new DtlsCertificate(
                certificatesData.length,
                certificates
        );

        logger.debug("[DtlsMessageTest][createDtlsCertificateTest] DtlsCertificate: {}", dtlsCertificate);

        // ENCODING
        byte[] dtlsCertificateData = dtlsCertificate.getData();
        logger.debug("[DtlsMessageTest][createDtlsCertificateTest] DtlsCertificate byte data: {}", dtlsCertificateData);
        logger.debug("[DtlsMessageTest][createDtlsCertificateTest] DtlsCertificate byte data: {}", ByteUtil.byteArrayToHex(dtlsCertificateData));

        // DECODING
        dtlsCertificate = new DtlsCertificate(dtlsCertificateData);
        logger.debug("[DtlsMessageTest][createDtlsCertificateTest] DtlsCertificate: {}", dtlsCertificate);

        return dtlsCertificate;
    }

    public static DtlsServerKeyExchange createDtlsServerKeyExchangeTest() {
        byte[] encryptedPreMasterSecretData = new byte[] { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08 };

        DtlsServerKeyExchange dtlsServerKeyExchange = new DtlsServerKeyExchange(encryptedPreMasterSecretData);
        logger.debug("[DtlsMessageTest][createDtlsServerKeyExchangeTest] DtlsServerKeyExchange: {}", dtlsServerKeyExchange);

        // ENCODING
        byte[] dtlsServerKeyExchangeData = dtlsServerKeyExchange.getData();
        logger.debug("[DtlsMessageTest][createDtlsServerKeyExchangeTest] DtlsServerKeyExchange byte data: {}", dtlsServerKeyExchangeData);
        logger.debug("[DtlsMessageTest][createDtlsServerKeyExchangeTest] DtlsServerKeyExchange byte data: {}", ByteUtil.byteArrayToHex(dtlsServerKeyExchangeData));

        // DECODING
        dtlsServerKeyExchange = new DtlsServerKeyExchange(dtlsServerKeyExchangeData);
        logger.debug("[DtlsMessageTest][createDtlsServerKeyExchangeTest] DtlsServerKeyExchange: {}", dtlsServerKeyExchange);

        return dtlsServerKeyExchange;
    }

    public static DtlsServerHelloDone createDtlsServerHelloDoneTest() {
        DtlsServerHelloDone dtlsServerHelloDone = new DtlsServerHelloDone();
        logger.debug("[DtlsMessageTest][createDtlsServerHelloDoneTest] DtlsServerHelloDone: {}", dtlsServerHelloDone);
        return dtlsServerHelloDone;
    }

    public static DtlsClientKeyExchange createDtlsClientKeyExchangeTest() {
        byte[] encryptedPreMasterSecretData = new byte[] { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07, (byte) 0x08 };

        DtlsClientKeyExchange dtlsClientKeyExchange = new DtlsClientKeyExchange(encryptedPreMasterSecretData);
        logger.debug("[DtlsMessageTest][createDtlsClientKeyExchangeTest] DtlsClientKeyExchange: {}", dtlsClientKeyExchange);

        // ENCODING
        byte[] dtlsClientKeyExchangeData = dtlsClientKeyExchange.getData();
        logger.debug("[DtlsMessageTest][createDtlsClientKeyExchangeTest] DtlsClientKeyExchange byte data: {}", dtlsClientKeyExchangeData);
        logger.debug("[DtlsMessageTest][createDtlsClientKeyExchangeTest] DtlsClientKeyExchange byte data: {}", ByteUtil.byteArrayToHex(dtlsClientKeyExchangeData));

        // DECODING
        dtlsClientKeyExchange = new DtlsClientKeyExchange(dtlsClientKeyExchangeData);
        logger.debug("[DtlsMessageTest][createDtlsClientKeyExchangeTest] DtlsClientKeyExchange: {}", dtlsClientKeyExchange);

        return dtlsClientKeyExchange;
    }

    public static DtlsChangeCipherSpec createDtlsChangeCipherSpecTest() {
        DtlsChangeCipherSpec dtlsChangeCipherSpec = new DtlsChangeCipherSpec();
        logger.debug("[DtlsMessageTest][createDtlsChangeCipherSpecMessageTest] DtlsChangeCipherSpecMessage: {}", dtlsChangeCipherSpec);
        return dtlsChangeCipherSpec;
    }

    public static DtlsApplicationData createDtlsApplicationData() {
        byte[] applicationData = {
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x08, (byte) 0x00, (byte) 0x45, (byte) 0x00,
                (byte) 0x00, (byte) 0x59, (byte) 0xb8, (byte) 0xfc,
                (byte) 0x40, (byte) 0x00, (byte) 0x40, (byte) 0x11,
                (byte) 0x83, (byte) 0x95, (byte) 0x7f, (byte) 0x00,
                (byte) 0x00, (byte) 0x01, (byte) 0x7f, (byte) 0x00,
                (byte) 0x00, (byte) 0x01, (byte) 0x81, (byte) 0xa8,
                (byte) 0x11, (byte) 0x51, (byte) 0x00, (byte) 0x45,
                (byte) 0xfe, (byte) 0x58, (byte) 0x17, (byte) 0x01,
                (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x01, (byte) 0x00, (byte) 0x30, (byte) 0xbc,
                (byte) 0x8b, (byte) 0x22, (byte) 0x8b, (byte) 0x92,
                (byte) 0x37, (byte) 0xdf, (byte) 0x40, (byte) 0xe4,
                (byte) 0xe2, (byte) 0xce, (byte) 0x8d, (byte) 0xce,
                (byte) 0x8f, (byte) 0x33, (byte) 0x4b, (byte) 0x90,
                (byte) 0xea, (byte) 0x4b, (byte) 0x33, (byte) 0xc5,
                (byte) 0xcb, (byte) 0x9a, (byte) 0x1f, (byte) 0x84,
                (byte) 0x59, (byte) 0x6f, (byte) 0x9b, (byte) 0x06,
                (byte) 0x79, (byte) 0x9e, (byte) 0x34, (byte) 0x86,
                (byte) 0x07, (byte) 0xdb, (byte) 0x81, (byte) 0x9e,
                (byte) 0xd7, (byte) 0xc8, (byte) 0x7e, (byte) 0x15,
                (byte) 0x4d, (byte) 0x34, (byte) 0x24, (byte) 0xc7,
                (byte) 0x89, (byte) 0xc1, (byte) 0x66};
        DtlsApplicationData dtlsApplicationData = new DtlsApplicationData(applicationData);
        logger.debug("[DtlsMessageTest][createDtlsApplicationData] DtlsApplicationData: {}", dtlsApplicationData);
        return dtlsApplicationData;
    }

    public static DtlsFinished createDtlsFinishedTest() {
        DtlsFinished dtlsFinished = new DtlsFinished();
        logger.debug("[DtlsMessageTest][createDtlsFinishedTest] DtlsFinished: {}", dtlsFinished);
        return dtlsFinished;
    }

}
