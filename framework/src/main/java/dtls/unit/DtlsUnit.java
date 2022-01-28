package dtls.unit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtls.fsm.DtlsFsmManager;
import service.scheduler.schedule.ScheduleManager;

/**
 * DTLS 제어 객체
 *      DtlsHandShakeManager 에 의해서 관리됨
 */
public class DtlsUnit {

    /////////////////////////////////////////////////////
    private final String id;
    private final String dtlsStateUnitName;

    private final ScheduleManager dtlsScheduleManager = new ScheduleManager();

    private final DtlsFsmManager dtlsFsmManager = new DtlsFsmManager();
    private final String dtlsScheduleKey = "DTLS";
    /////////////////////////////////////////////////////

    /////////////////////////////////////////////////////
    public DtlsUnit(String id) {
        this.id = id;
        this.dtlsStateUnitName = dtlsScheduleKey + "_" + id;

        dtlsFsmManager.init(this);
        dtlsScheduleManager.initJob(
                dtlsScheduleKey, 10, 10
        );
    }
    /////////////////////////////////////////////////////

    /////////////////////////////////////////////////////
    public String getId() {
        return id;
    }

    public String getDtlsStateUnitName() {
        return dtlsStateUnitName;
    }

    public ScheduleManager getDtlsScheduleManager() {
        return dtlsScheduleManager;
    }

    public DtlsFsmManager getDtlsFsmManager() {
        return dtlsFsmManager;
    }

    public String getDtlsScheduleKey() {
        return dtlsScheduleKey;
    }
    /////////////////////////////////////////////////////

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
