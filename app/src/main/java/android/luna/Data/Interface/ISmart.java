package android.luna.Data.Interface;

import android.luna.Data.module.Scheduler;
import android.luna.Data.module.Smart;

import java.util.List;

/**
 * Created by Lee.li on 2018/5/30.
 */

public interface ISmart extends ISchedule<Smart> {
        List<Smart> queryBytype(int type);
        Smart findSchedulerById(int SmartId);
        int deleteById(int id);
        void createOrUpdateScheduler(Smart smart);
}
