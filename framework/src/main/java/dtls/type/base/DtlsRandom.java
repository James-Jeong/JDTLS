package dtls.type.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.module.ByteUtil;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DtlsRandom {

    private static final Logger logger = LoggerFactory.getLogger(DtlsRandom.class);

    public static final int TIME_LENGTH = 4;
    public static final int RANDOM_BYTES_LENGTH = 28;
    public static final int LENGTH = TIME_LENGTH + RANDOM_BYTES_LENGTH;

    public static byte[] getRandom() {
        byte[] totalData = new byte[LENGTH];

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+00"));
        Date date = calendar.getTime();
        byte[] dateData = ByteUtil.intToBytes((int) date.getTime(), true);
        System.arraycopy(dateData, 0, totalData, 0, TIME_LENGTH);

        try {
            byte[] randomBytes = new byte[RANDOM_BYTES_LENGTH];
            SecureRandom.getInstanceStrong().nextBytes(randomBytes);
            System.arraycopy(randomBytes, 0, totalData, TIME_LENGTH, RANDOM_BYTES_LENGTH);
        } catch (Exception e) {
            logger.warn("DtlsRandom.Exception", e);
            return totalData;
        }

        return totalData;
    }

}
