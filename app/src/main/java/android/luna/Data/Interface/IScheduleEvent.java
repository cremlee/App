package android.luna.Data.Interface;

import android.luna.Data.module.Scheduler;

import java.util.List;

/**
 * Created by Lee.li on 2018/5/30.
 */

public interface IScheduleEvent extends ISchedule<Scheduler> {
       List<Scheduler> queryBytype(int type);
       Scheduler findSchedulerById(int schedulerId);
        int deleteById(int id);
        void createOrUpdateScheduler(Scheduler scheduler);
}
