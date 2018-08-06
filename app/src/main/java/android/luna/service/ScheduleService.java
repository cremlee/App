package android.luna.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Lee.li on 2018/8/6.
 */

public class ScheduleService extends Service {
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    
}
