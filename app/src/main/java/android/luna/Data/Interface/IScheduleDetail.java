package android.luna.Data.Interface;
import android.luna.Data.module.Scheduler;
import android.luna.Data.module.SchedulerDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by Lee.li on 2018/5/30.
 */

public interface IScheduleDetail extends ISchedule<SchedulerDetail> {
    SchedulerDetail findSchedulerDetailByWeekAndHour(int week, int hour);
    int deleteBySchedulerId(int schedulerId);
    List<SchedulerDetail> findSchedulerDetailsByHour(int hour);
    Map<Integer, List<SchedulerDetail>> findSchedulerDetails();
    void updateSchedulerDetails(Scheduler scheduler);
}
