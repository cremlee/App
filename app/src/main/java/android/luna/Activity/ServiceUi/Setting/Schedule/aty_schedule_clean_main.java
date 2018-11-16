package android.luna.Activity.ServiceUi.Setting.Schedule;

import android.content.DialogInterface;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.Schedule.fragment.DailyCleanSchedulerFragment;
import android.luna.Activity.ServiceUi.Setting.Schedule.fragment.ScheduleECOFragment;
import android.luna.Activity.ServiceUi.Setting.Schedule.fragment.SchedulerOverviewFragment;
import android.luna.Data.DAO.ScheduleDaoFactory;
import android.luna.Data.module.Scheduler;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class aty_schedule_clean_main extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private ScheduleDaoFactory scheduleDaoFactory =null;
    private DailyCleanSchedulerFragment tb_daily;
    private DailyCleanSchedulerFragment tb_weekly;
    private SchedulerOverviewFragment tb_overview;
    private ScheduleECOFragment tb_eco;
    private RadioGroup myTabRg;
    private MaterialDialog progressDialog;
    private Button btn_back;
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_schedule_editor);
        myTabRg = findViewById(R.id.myTabRg);
        myTabRg.setOnCheckedChangeListener(this);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void InitData() {
        super.InitData();
        scheduleDaoFactory = new ScheduleDaoFactory(this,getApp());

    }

    @Override
    public void InitEvent() {
        super.InitEvent();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(tb_overview==null)
                    {
                        tb_overview = new SchedulerOverviewFragment();
                        tb_overview.setdatas(scheduleDaoFactory, Scheduler.TYPE_ENERGY_SAVING);
                    }
                    if(tb_overview.isAdded())
                    {
                        tb_overview.refreshoverview();
                    }else {
                        getFragmentManager().beginTransaction().replace(R.id.fly_schedule, tb_overview).commit();
                    }
                    if(progressDialog!=null)
                        progressDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    private void loaddefault()
    {
        scheduleDaoFactory.Loadfordefault();
        mHandler.sendEmptyMessage(0);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId)
        {
            case R.id.rb_overview:
                if(tb_overview==null)
                {
                    tb_overview = new SchedulerOverviewFragment();
                    tb_overview.setdatas(scheduleDaoFactory, Scheduler.TYPE_ENERGY_SAVING);
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_schedule,tb_overview).commit();
                break;
            case R.id.rb_daily:
                if(tb_daily==null)
                {
                    tb_daily = new DailyCleanSchedulerFragment();
                    tb_daily.setdatas(scheduleDaoFactory, Scheduler.TYPE_DAILY_CLEAN);
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_schedule,tb_daily).commit();
                break;
            case R.id.rb_weekly:
                if(tb_weekly==null)
                {
                    tb_weekly = new DailyCleanSchedulerFragment();
                    tb_weekly.setdatas(scheduleDaoFactory, Scheduler.TYPE_WEEKLY_CLEAN);
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_schedule,tb_weekly).commit();
                break;
            case R.id.rb_eco:
                if(tb_eco==null)
                {
                    tb_eco = new ScheduleECOFragment();
                    tb_eco.setdatas(scheduleDaoFactory, Scheduler.TYPE_ENERGY_SAVING);
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_schedule,tb_eco).commit();
                break;
            case R.id.rb_reset:
                //// TODO: 2018/5/31 reset the scheduler for default  
                //// TODO: 2018/5/31 geichu ti shi
                // TODO: 2018/5/31 do the reset
                progressDialog = new MaterialDialog.Builder(aty_schedule_clean_main.this)
                        .title("Load default")
                        .content("your scheduler will be reseted ,do you want to do it?")
                        .positiveText("OK")
                        .positiveColor(getResources().getColor(R.color.green_grass))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                progressDialog.dismiss();
                                progressDialog = new MaterialDialog.Builder(aty_schedule_clean_main.this)
                                        .title("Loading")
                                        .canceledOnTouchOutside(false)
                                        .content("waiting...")
                                        .progress(true, 0)
                                        .show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loaddefault();
                                    }
                                }).start();
                            }
                        })
                        .negativeText("NO")
                        .negativeColor(getResources().getColor(R.color.red_wine))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                progressDialog.dismiss();
                            }
                        })
                        .canceledOnTouchOutside(false)
                        .show();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_schedule_clean_main.this);
                break;
        }
    }
}
