package dtls.fsm.definition.callback;

import JFSM.JFSM.src.main.java.com.fsm.StateManager;
import JFSM.JFSM.src.main.java.com.fsm.event.base.CallBack;
import JFSM.JFSM.src.main.java.com.fsm.unit.StateUnit;
import dtls.retransmit.DtlsRetransmit;
import dtls.unit.DtlsUnit;

import java.util.concurrent.TimeUnit;

public class DtlsRetransmitTimerMaker extends CallBack {

    private final DtlsUnit dtlsUnit;
    private final long timeoutMs;

    public DtlsRetransmitTimerMaker(StateManager stateManager, String name, DtlsUnit dtlsUnit, long timeoutMs) {
        super(stateManager, name);

        this.dtlsUnit = dtlsUnit;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public Object callBackFunc(StateUnit stateUnit) {
        dtlsUnit.getDtlsScheduleManager().startJob(
                dtlsUnit.getDtlsScheduleKey(),
                new DtlsRetransmit(
                        dtlsUnit.getDtlsScheduleManager(),
                        DtlsRetransmit.class + "_" + dtlsUnit.getId(),
                        0, 0, TimeUnit.MILLISECONDS,
                        1, 0, false,
                        dtlsUnit, timeoutMs
                )
        );
        return null;
    }

}
