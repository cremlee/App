package android.luna.Data.Interface;
import android.luna.Data.module.Scheduler;
import android.luna.Data.module.VendSchedulerDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by Lee.li on 2018/5/30.
 */

public interface IVendScheduleDetail extends ISchedule<VendSchedulerDetail> {
    VendSchedulerDetail findSchedulerDetailByWeekAndHour(int week, int hour);
    int deleteBySchedulerId(int schedulerId);
    List<VendSchedulerDetail> findSchedulerDetailsByHour(int hour);
    Map<Integer, List<VendSchedulerDetail>> findSchedulerDetails();
    void updateSchedulerDetails(Scheduler scheduler);
}
