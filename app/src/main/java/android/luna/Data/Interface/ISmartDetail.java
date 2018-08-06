package android.luna.Data.Interface;
import android.luna.Data.module.Scheduler;
import android.luna.Data.module.SchedulerDetail;
import android.luna.Data.module.Smart;
import android.luna.Data.module.SmartDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by Lee.li on 2018/5/30.
 */

public interface ISmartDetail extends ISchedule<SmartDetail> {
    SmartDetail findSchedulerDetailByWeekAndHour(int week, int hour);
    int deleteBySchedulerId(int smartId);
    List<SmartDetail> findSchedulerDetailsByHour(int hour);
    Map<Integer, List<SmartDetail>> findSchedulerDetails();
    void updateSchedulerDetails(Smart smart);
}
