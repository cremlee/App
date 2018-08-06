package android.luna.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.CremApp;
import android.luna.Utils.BuildConfig;
import android.luna.Utils.Logger.EvoTrace;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lee.li on 2018/8/6.
 */

public class ScheduleService extends Service {

    private CremApp app;
    private Timer scheduletimer;
    private int currentTime;




    private void ScheduleProcess()
    {
        currentTime = Calendar.getInstance().get(Calendar.MINUTE);
        if(currentTime%15 == 0)
        {
            // TODO: 2018/8/6 timer = 15min
            Cleanprocess();
            EcoProcess();
            SmartProcess();
            DaylightProcess();
        }
        TempEventProcess();
        HappyhourProcess();

    }

    private void TempEventProcess()
    {
        showTestToast("TempEventProcess");
    }
    private void Cleanprocess()
    {
        showTestToast("Cleanprocess");
    }
    private void EcoProcess()
    {
        showTestToast("EcoProcess");
    }
    private void SmartProcess()
    {
        showTestToast("SmartProcess");
    }
    private void HappyhourProcess()
    {
        showTestToast("HappyhourProcess");
    }
    private void DaylightProcess()
    {
        showTestToast("DaylightProcess");
    }

    private void StartScheduleTimer()
    {
        if(scheduletimer == null)
            scheduletimer = new Timer();
        scheduletimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ScheduleProcess();
            }
        },0,60*1000);
    }

    private void StopScheduleTimer()
    {
        if (scheduletimer != null) {
            scheduletimer.cancel();
            scheduletimer.purge();
            scheduletimer = null;
        }
    }

    public class MyBinder extends Binder {

        public ScheduleService getService() {
            return ScheduleService.this;
        }
    }
    private MyBinder myBinder = new ScheduleService.MyBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = (CremApp)getApplication();
        StartScheduleTimer();
        IntentFilter filter = new IntentFilter();
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
    @Override
    public void onDestroy() {
        super.onDestroy();
        StopScheduleTimer();
        unregisterReceiver(receiver);
    }
    private Toast toast;
    public void showTestToast(String msg) {
        if(BuildConfig.isDebug)
            EvoTrace.e("schedule",msg);

    }

}
