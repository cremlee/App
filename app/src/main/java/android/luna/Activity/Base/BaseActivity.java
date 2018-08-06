package android.luna.Activity.Base;

import android.app.Activity;
import android.content.IntentFilter;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.BuildConfig;
import android.luna.Utils.Device.MachineConfig;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Toast;

/**
 * Created by Lee.li on 2017/12/28.
 */

public class BaseActivity extends Activity implements IUiCreater {
    private  static String Tag = "BaseActivity";
    private static final int ACTIVITY_RESUME = 0;
    private static final int ACTIVITY_STOP = 1;
    private static final int ACTIVITY_PAUSE = 2;
    private static final int ACTIVITY_DESTROY = 3;
    public int activityState;

    private Toast toast;
    public void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
    public void showTestToast(String msg) {
        if(!BuildConfig.isDebug)
            return;
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public void showToastLong(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public void showToast(int msg) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitActivity();
        AppManager.getAppManager().addActivity(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUME;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ACTIVITY_STOP;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSE;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROY;
        System.gc();
    }

    //<code>初始化用户界面</code>
   public   void  InitActivity()
   {
       InitData();
       InitView();
       InitEvent();
   }

    @Override
    public void InitView() {

    }

    @Override
    public void InitData() {

    }

    @Override
    public void InitEvent() {

    }

    @Override
    public void setDeviceData(Device a) {

    }


    public CremApp getApp() {
        return (CremApp) getApplication();
    }
}
