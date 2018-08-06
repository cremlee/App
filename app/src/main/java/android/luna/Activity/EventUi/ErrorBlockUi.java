package android.luna.Activity.EventUi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Data.Warning.MachineWarn;
import android.luna.Utils.Logger.EvoTrace;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/25.
 */

public class ErrorBlockUi extends BaseActivity implements OnClickListener{
    private ViewFlipper errorlsv;
    private TextView pageindex;
    private TextView ntc,water;
    private Button action;
    private TextView call;
    private List<MachineWarn> machineWarns;
    private View errinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EvoTrace.e("abc","onCreate");
    }

    @Override
    protected void onResume() {
        EvoTrace.e("abc","onResume");
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_WARNING_NOTIFICATION);
        filter.addAction(Constant.ACTION_ERROR_CHECK_RELEASE_UI);
        registerReceiver(receiver, filter);
        if(!mHandler.hasMessages(1000))
            mHandler.sendEmptyMessageDelayed(1000,1000);
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Constant.ACTION_WARNING_NOTIFICATION.equals(action))
            {
                mHandler.sendEmptyMessage(2000);
            }else if(Constant.ACTION_ERROR_CHECK_RELEASE_UI.equals(action))
            {
                AppManager.getAppManager().finishActivity(ErrorBlockUi.this);
            }
        }
    };
    private int currentIndex=0;
    private int totalcount;

    private void updateErrorData()
    {
        Predicate notmachineWarnList = new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                MachineWarn c = (MachineWarn)o;
                if(c.getWarninglevel() == 2)
                    return true;
                return false;
            }
        };
        machineWarns = (List<MachineWarn>)CollectionUtils.select(  getApp().getallMachineWarnList(),notmachineWarnList);
    }

    private void updateErrorInfo()
    {
        updateErrorIndex();
        if(machineWarns!=null && machineWarns.size()>0) {
            error_title.setText(machineWarns.get(currentIndex).getErrorDeviceName(this));
            error_content.setText(machineWarns.get(currentIndex).getErrorContent());
            error_help.setText("quick action for error!");
        }
    }

    private void updateErrorIndex()
    {
        if(machineWarns!=null && machineWarns.size()>0)
        {
            totalcount = machineWarns.size();
            if(currentIndex>=totalcount)
                currentIndex=0;
            pageindex.setText(String.format("%d/%d",currentIndex+1,totalcount));
        }

    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    ntc.setText("T: "+getApp().getAckQueryInstance().getNtcHighTemperature()+" ℃");
                    water.setText("W: "+getApp().getAckQueryInstance().getWaterHighState());
                    mHandler.sendEmptyMessageDelayed(1000,1000);
                    break;
                case 2000:
                    updateErrorData();
                    updateErrorInfo();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onPause() {
        EvoTrace.e("abc","onPause");
        super.onPause();
        mHandler.removeMessages(1000);
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private TextView error_title,error_content,error_help;
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_error_tip);
        errorlsv =findViewById(R.id.errorlsv);
        pageindex =findViewById(R.id.pageindex);
        ntc =findViewById(R.id.ntc);
        water =findViewById(R.id.water);
        action =findViewById(R.id.action);
        call =findViewById(R.id.call);
        errinfo = getLayoutInflater().inflate(R.layout.tip_error_info, null);
        error_title = errinfo.findViewById(R.id.error_title);
        error_content = errinfo.findViewById(R.id.error_content);
        error_help = errinfo.findViewById(R.id.error_help);
        errorlsv.addView(errinfo);
        updateErrorInfo();
    }

    @Override
    public void InitData() {
        super.InitData();
        updateErrorData();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        errorlsv.setOnClickListener(this);
        action.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.errorlsv:
                currentIndex++;
                updateErrorInfo();
                break;
            case R.id.action:
                AppManager.getAppManager().finishActivity(ErrorBlockUi.this);
                break;
        }
    }
}
