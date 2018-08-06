package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IScheduleDetail;
import android.luna.Data.Interface.IScheduleEvent;
import android.luna.Data.Interface.IScheduleFactory;
import android.luna.Data.Interface.ISmart;
import android.luna.Data.Interface.ISmartDetail;
import android.luna.Data.Interface.IVendScheduleDetail;
import android.luna.Data.module.Scheduler;
import android.luna.Data.module.SchedulerDetail;
import android.luna.Data.module.VendSchedulerDetail;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class ScheduleDaoFactory extends BaseDaobak implements IScheduleFactory {


    private IScheduleEvent scheduleEventdao   = null;
    private IScheduleDetail scheduleDetaildao = null;
    private IVendScheduleDetail vendscheduleDetaildao = null;
    private ISmart smartdao =null;
    private ISmartDetail smartDetaildao =null;
    public ScheduleDaoFactory(Context context, CremApp app) {
        super(context, app);
    }



    public void Loadfordefault()
    {
        try {
            getHelper().get_SchedulerDao().delete(getHelper().get_SchedulerDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getHelper().get_SchedulerDetailDao().delete(getHelper().get_SchedulerDetailDao().queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Scheduler scheduler;
        for (int i =1 ;i<=5;i++)
        {
            scheduler = new Scheduler(Scheduler.TYPE_DAILY_CLEAN,"08:00",0,i,0);
            getScheduleEventDao().createOrUpdateScheduler(scheduler);
            getScheduleDetailDao().updateSchedulerDetails(scheduler);
            if(i==5)
            {
                scheduler = new Scheduler(Scheduler.TYPE_WEEKLY_CLEAN,"15:30",0,i,0);
                getScheduleEventDao().createOrUpdateScheduler(scheduler);
                getScheduleDetailDao().updateSchedulerDetails(scheduler);
                scheduler = new Scheduler(Scheduler.TYPE_ENERGY_SAVING,"19:00",60,i,0);
                getScheduleEventDao().createOrUpdateScheduler(scheduler);
                getScheduleDetailDao().updateSchedulerDetails(scheduler);
            }
            else
            {
                scheduler = new Scheduler(Scheduler.TYPE_ENERGY_SAVING,"19:00",12,i,0);
                getScheduleEventDao().createOrUpdateScheduler(scheduler);
                getScheduleDetailDao().updateSchedulerDetails(scheduler);
            }

        }

    }

    @Override
    public IScheduleEvent getScheduleEventDao() {
        if(scheduleEventdao == null)
            scheduleEventdao = new IScheduleEvent() {
                @Override
                public void delete(Scheduler scheduler) {
                    try {
                        getHelper().get_SchedulerDao().delete(scheduler);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public List<Scheduler> queryBytype(int type) {
                    try {
                        QueryBuilder<Scheduler, Integer> queryBuilder = getHelper().get_SchedulerDao().queryBuilder();
                        queryBuilder.where().eq("eventType", type);
                        return queryBuilder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public Scheduler findSchedulerById(int schedulerId) {
                    try {
                        return getHelper().get_SchedulerDao().queryForId(schedulerId);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int deleteById(int id) {
                    try {
                        return getHelper().get_SchedulerDao().deleteById(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public void createOrUpdateScheduler(Scheduler scheduler) {
                    try {
                        getHelper().get_SchedulerDao().createOrUpdate(scheduler);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };
        return scheduleEventdao;
    }

    @Override
    public IScheduleDetail getScheduleDetailDao() {
        if(scheduleDetaildao == null)
        {
            scheduleDetaildao =new IScheduleDetail() {

                @Override
                public void delete(SchedulerDetail schedulerDetail) {

                }

                @Override
                public SchedulerDetail findSchedulerDetailByWeekAndHour(int week, int hour) {
                    try {
                        QueryBuilder<SchedulerDetail, Integer> queryBuilder = getHelper().get_SchedulerDetailDao().queryBuilder();
                        queryBuilder.where().eq("week", week).and().eq("hour", hour);
                        return queryBuilder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                @Override
                public int deleteBySchedulerId(int schedulerId) {
                    DeleteBuilder<SchedulerDetail, Integer> builder = getHelper().get_SchedulerDetailDao().deleteBuilder();
                    try {
                        builder.where().eq("schedulerId", schedulerId);
                        return builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public List<SchedulerDetail> findSchedulerDetailsByHour(int hour) {
                    QueryBuilder<SchedulerDetail, Integer> queryBuilder = getHelper().get_SchedulerDetailDao().queryBuilder();
                    try {
                        queryBuilder.orderBy("week", true);
                        queryBuilder.where().eq("hour", hour);
                        return queryBuilder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public Map<Integer, List<SchedulerDetail>> findSchedulerDetails() {
                    Map<Integer, List<SchedulerDetail>> mapDetails = new HashMap<>();
                        // 24小时
                        for (int i = 0; i < 24; i++) {
                            List<SchedulerDetail> list = findSchedulerDetailsByHour(i);
                            if (list != null && list.size() > 0) {
                                mapDetails.put(i, list);
                            }
                        }
                    return mapDetails;
                }
                private void createOrUpdate(SchedulerDetail schedulerDetail) {
                    SchedulerDetail detail2 = findSchedulerDetailByWeekAndHour(schedulerDetail.getWeek(), schedulerDetail.getHour());
                    if (detail2 != null) {
                        schedulerDetail.setId(detail2.getId());
                    }
                    try {
                        getHelper().get_SchedulerDetailDao().createOrUpdate(schedulerDetail);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                private String getNameByEventType(int type) {
                    switch (type) {
                        case 1:
                            return "Daily Clean";
                        case 2:
                            return "Weekly Clean";
                        case 3:
                            return "Energy Saving";
                        default:
                            return "";
                    }
                }
                private void deleteRt(int hour, int week) {

                    SchedulerDetail detail = findSchedulerDetailByWeekAndHour(week, hour);
                    if (detail != null) {int schedulerId = detail.getSchedulerId();
                        deleteBySchedulerId(schedulerId);
                        try {
                            getHelper().get_SchedulerDao().deleteById(schedulerId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void updateSchedulerDetails(Scheduler scheduler) {
                    int hour = Integer.valueOf(scheduler.getStartTime().substring(0, 2));
                    int min = Integer.valueOf(scheduler.getStartTime().substring(3, 5));
                    int week = scheduler.getWeek();
                    int persistTime = scheduler.getPersistTime();
                    int schedulerId = scheduler.getId();
                    String eventName = getNameByEventType(scheduler.getEventType());
                    if (persistTime >=1) {
                        int startHour = hour - 1;
                        int count = min>0?persistTime+1:persistTime+1;
                        for (int i = 0; i < count; i++) {
                            startHour += 1;
                            if (startHour > 23) {
                                startHour = 0;
                                week += 1;
                                if (week > 6) {
                                    week = 0;
                                }
                            }

                            int persistTimeFlag = 0;
                            if (i == 0) {
                                persistTimeFlag = 1;
                            } else if (i == persistTime) {
                                persistTimeFlag = 2;
                            }

                            SchedulerDetail detail = new SchedulerDetail();
                            detail.setPersistFlag(persistTimeFlag);
                            detail.setSchedulerId(schedulerId);
                            detail.setHour(startHour);
                            detail.setWeek(week);
                            detail.setName(eventName);
                            deleteRt(startHour, week);
                            createOrUpdate(detail);
                        }
                    } else {
                        SchedulerDetail detail = new SchedulerDetail();
                        detail.setSchedulerId(schedulerId);
                        detail.setPersistFlag(1);
                        detail.setHour(hour);
                        detail.setWeek(week);
                        detail.setName(eventName);
                        createOrUpdate(detail);
                    }
                }
            };
        }
        return scheduleDetaildao;
    }

    @Override
    public IVendScheduleDetail getVendScheduleDetailDao() {
        if(vendscheduleDetaildao == null)
        {
            vendscheduleDetaildao =new IVendScheduleDetail() {
                @Override
                public VendSchedulerDetail findSchedulerDetailByWeekAndHour(int week, int hour) {
                    try {
                        QueryBuilder<VendSchedulerDetail, Integer> queryBuilder = getHelper().get_VendSchedulerDetailDao().queryBuilder();
                        queryBuilder.where().eq("week", week).and().eq("hour", hour);
                        return queryBuilder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int deleteBySchedulerId(int schedulerId) {
                    DeleteBuilder<VendSchedulerDetail, Integer> builder = getHelper().get_VendSchedulerDetailDao().deleteBuilder();
                    try {
                        builder.where().eq("schedulerId", schedulerId);
                        return builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                private void deleteRt(int hour, int week) {
                    VendSchedulerDetail detail = findSchedulerDetailByWeekAndHour(week, hour);
                    if (detail != null) {int schedulerId = detail.getSchedulerId();
                        deleteBySchedulerId(schedulerId);
                        try {
                            getHelper().get_SchedulerDao().deleteById(schedulerId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                private void createOrUpdate(VendSchedulerDetail schedulerDetail) {
                    VendSchedulerDetail detail2 = findSchedulerDetailByWeekAndHour(schedulerDetail.getWeek(), schedulerDetail.getHour());
                    if (detail2 != null) {
                        schedulerDetail.setId(detail2.getId());
                    }
                    try {
                        getHelper().get_VendSchedulerDetailDao().createOrUpdate(schedulerDetail);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public List<VendSchedulerDetail> findSchedulerDetailsByHour(int hour) {
                    QueryBuilder<VendSchedulerDetail, Integer> queryBuilder = getHelper().get_VendSchedulerDetailDao().queryBuilder();
                    try {
                        queryBuilder.orderBy("week", true);
                        queryBuilder.where().eq("hour", hour);
                        return queryBuilder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public Map<Integer, List<VendSchedulerDetail>> findSchedulerDetails() {
                    Map<Integer, List<VendSchedulerDetail>> mapDetails = new HashMap<>();
                    // 24小时
                    for (int i = 0; i < 24; i++) {
                        List<VendSchedulerDetail> list = findSchedulerDetailsByHour(i);
                        if (list != null && list.size() > 0) {
                            mapDetails.put(i, list);
                        }
                    }
                    return mapDetails;
                }

                @Override
                public void updateSchedulerDetails(Scheduler scheduler) {
                    int hour = Integer.valueOf(scheduler.getStartTime().substring(0, 2));
                    int min = Integer.valueOf(scheduler.getStartTime().substring(3, 5));
                    int week = scheduler.getWeek();
                    int persistTime = scheduler.getPersistTime();
                    int schedulerId = scheduler.getId();
                    String eventName = "Happy hours";
                    if (persistTime >=1) {
                        int startHour = hour - 1;
                        int count = min>0?persistTime+1:persistTime+1;
                        for (int i = 0; i < count; i++) {
                            startHour += 1;
                            if (startHour > 23) {
                                startHour = 0;
                                week += 1;
                                if (week > 6) {
                                    week = 0;
                                }
                            }

                            int persistTimeFlag = 0;
                            if (i == 0) {
                                persistTimeFlag = 1;
                            } else if (i == persistTime) {
                                persistTimeFlag = 2;
                            }

                            VendSchedulerDetail detail = new VendSchedulerDetail();
                            detail.setPersistFlag(persistTimeFlag);
                            detail.setSchedulerId(schedulerId);
                            detail.setHour(startHour);
                            detail.setWeek(week);
                            detail.setName(eventName);
                            deleteRt(startHour, week);
                            createOrUpdate(detail);
                        }
                    } else {
                        VendSchedulerDetail detail = new VendSchedulerDetail();
                        detail.setSchedulerId(schedulerId);
                        detail.setPersistFlag(1);
                        detail.setHour(hour);
                        detail.setWeek(week);
                        detail.setName(eventName);
                        createOrUpdate(detail);
                    }
                }

                @Override
                public void delete(VendSchedulerDetail vendSchedulerDetail) {

                }
            };
        }
        return vendscheduleDetaildao;
    }

    @Override
    public ISmart getSmartDao() {
        return null;
    }

    @Override
    public ISmartDetail getSmartDetailDao() {
        return null;
    }
}
