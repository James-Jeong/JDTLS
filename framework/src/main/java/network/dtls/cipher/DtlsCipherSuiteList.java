package network.dtls.cipher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class DtlsCipherSuiteList {

    private List<DtlsCipherSuite> dtlsCipherSuiteList = new ArrayList<>();

    public DtlsCipherSuiteList(List<DtlsCipherSuite> dtlsCipherSuiteList) {
        this.dtlsCipherSuiteList = dtlsCipherSuiteList;
    }

    public DtlsCipherSuiteList() {}

    public DtlsCipherSuiteList(byte[] data) {
        // 2 바이트씩 끊어서 읽으면 된다.
        if ((data.length >= DtlsCipherSuiteType.LENGTH) &&
                (data.length % DtlsCipherSuiteType.LENGTH == 0)) {
            int index = 0;
            while (index < data.length) {
                byte[] curCipherSuiteData = new byte[DtlsCipherSuiteType.LENGTH];
                System.arraycopy(data, index, curCipherSuiteData, 0, DtlsCipherSuiteType.LENGTH);
                DtlsCipherSuite dtlsCipherSuite = new DtlsCipherSuite(curCipherSuiteData);
                dtlsCipherSuiteList.add(dtlsCipherSuite);

                index += DtlsCipherSuiteType.LENGTH;
            }
        }
    }

    public int getTotalLength() {
        int totalLength = 0;

        for(DtlsCipherSuite dtlsCipherSuite : dtlsCipherSuiteList) {
            if (dtlsCipherSuite == null) { continue; }

            totalLength += dtlsCipherSuite.getCipherSuite().length;
        }

        return totalLength;
    }

    public byte[] getData() {
        int totalDataLength = getTotalLength();
        if (totalDataLength > 0) {
            int index = 0;
            byte[] data = new byte[totalDataLength];

            for(DtlsCipherSuite dtlsCipherSuite : dtlsCipherSuiteList) {
                if (dtlsCipherSuite == null) { continue; }

                byte[] cipherSuiteData = dtlsCipherSuite.getCipherSuite();
                if (cipherSuiteData == null || cipherSuiteData.length == 0) { continue; }

                System.arraycopy(cipherSuiteData, 0, data, index, cipherSuiteData.length);
                index += cipherSuiteData.length;
            }

            return data;
        }

        return null;
    }

    public List<DtlsCipherSuite> getDtlsCipherSuiteList() {
        return dtlsCipherSuiteList;
    }

    public void setDtlsCipherSuiteList(List<DtlsCipherSuite> dtlsCipherSuiteList) {
        this.dtlsCipherSuiteList = dtlsCipherSuiteList;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
