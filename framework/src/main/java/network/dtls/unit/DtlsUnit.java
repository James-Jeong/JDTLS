package network.dtls.unit;

import network.dtls.timer.DtlsTimer;

/**
 * DTLS 제어 객체
 *      DtlsHandShakeManager 에 의해서 관리됨
 */
public class DtlsUnit {

    private DtlsTimer dtlsTimer;

    public DtlsUnit() {}

    public DtlsTimer getDtlsTimer() {
        return dtlsTimer;
    }

    public void setDtlsTimer(DtlsTimer dtlsTimer) {
        this.dtlsTimer = dtlsTimer;
    }


}
