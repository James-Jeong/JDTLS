package dtls.retransmit;

import JFSM.JFSM.src.main.java.com.fsm.StateManager;
import JFSM.JFSM.src.main.java.com.fsm.module.StateHandler;
import dtls.fsm.definition.DtlsEvent;
import dtls.fsm.definition.DtlsState;
import dtls.unit.DtlsUnit;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.scheduler.job.Job;
import service.scheduler.schedule.ScheduleManager;

import java.util.concurrent.TimeUnit;

public class DtlsRetransmit extends Job { // Scheduled by DtlsUnit

    private static final Logger logger = LoggerFactory.getLogger(DtlsRetransmit.class);

    private final StopWatch stopWatch = new StopWatch();

    private final DtlsUnit dtlsUnit;
    private final long timeoutMs;

    public DtlsRetransmit(ScheduleManager scheduleManager, String name,
                          int initialDelay, int interval, TimeUnit timeUnit,
                          int priority, int totalRunCount, boolean isLasted,
                          DtlsUnit dtlsUnit, long initialTimeoutMs) {
        super(scheduleManager, name, initialDelay, interval, timeUnit, priority, totalRunCount, isLasted);

        this.dtlsUnit = dtlsUnit;
        this.timeoutMs = initialTimeoutMs;
    }

    @Override
    public void run() {
        stopWatch.start();

        long curTimeMs;
        long startTimeMs = stopWatch.getStartTime();
        logger.debug("({}) DTLS RETRANSMIT IS STARTED. (startTimeMs={}, timeoutMs={})",
                getName(), startTimeMs, timeoutMs
        );

        ////////////////////////////
        while (true) {
            curTimeMs = stopWatch.getTime(TimeUnit.MILLISECONDS);
            if (curTimeMs - startTimeMs >= timeoutMs) {
                logger.warn("({}) DTLS RETRANSMIT TIMEOUT EXPIRED! (timeoutMs={})",
                        getName(), timeoutMs
                );

                ////////////////////////////
                // FIRE [TIMER_EXPIRES]
                StateManager stateManager = dtlsUnit.getDtlsFsmManager().getStateManager();
                StateHandler stateHandler = stateManager.getStateHandler(DtlsState.NAME);
                if (stateHandler != null) {
                    stateHandler.fire(
                            DtlsEvent.TIMER_EXPIRES.name(),
                            stateManager.getStateUnit(dtlsUnit.getDtlsStateUnitName())
                    );
                }
                ////////////////////////////

                break;
            }
        }
        ////////////////////////////

        stopWatch.stop();
        curTimeMs = stopWatch.getTime(TimeUnit.MILLISECONDS);
        logger.debug("({}) DTLS RETRANSMIT IS FINISHED. (startTimeMs={}, curTimeMs={}, timeoutMs={})",
                getName(), startTimeMs, curTimeMs, timeoutMs
        );
    }

}
