package android.luna.Activity.ServiceUi.Setting.Payment;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.BrewerCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.DailyCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.WeeklyCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Payment.fragment.PayNormalFragment;
import android.luna.Activity.ServiceUi.Setting.Payment.fragment.PayOnlineFragment;
import android.luna.Activity.ServiceUi.Setting.Payment.fragment.SchedulerOverviewFragment;
import android.luna.Data.DAO.ScheduleDaoFactory;
import android.luna.Data.module.Scheduler;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class aty_payment_main extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private RadioGroup myTabRg;
    private MaterialDialog progressDialog;
    private TextView btn_back;
    private PayNormalFragment tb_normal;
    private PayOnlineFragment tb_online;
    private SchedulerOverviewFragment  tb_overview;
    private ScheduleDaoFactory scheduleDaoFactory =null;
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
        setContentView(R.layout.aty_payment_editor);
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
                    if(progressDialog!=null)
                        progressDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId)
        {
            case R.id.rb_normal:
                if(tb_normal==null)
                {
                    tb_normal = new PayNormalFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_pay,tb_normal).commit();
                break;
            case R.id.rb_online:
                if(tb_online==null)
                {
                    tb_online = new PayOnlineFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_pay,tb_online).commit();
                break;
            case R.id.rb_happy:
                if(tb_overview==null)
                {
                    tb_overview = new SchedulerOverviewFragment();
                    tb_overview.setdatas(scheduleDaoFactory, Scheduler.TYPE_FREE_DRINK);
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_pay,tb_overview).commit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_payment_main.this);
                break;
        }
    }
}
