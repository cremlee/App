package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/5/30.
 */

public interface IScheduleFactory {
    IScheduleEvent getScheduleEventDao();
    IScheduleDetail getScheduleDetailDao();
    IVendScheduleDetail getVendScheduleDetailDao();
    ISmart getSmartDao();
    ISmartDetail getSmartDetailDao();
}
