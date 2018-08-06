package android.luna.Activity.ProductLineUi.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_1;
import android.luna.Activity.ProductLineUi.adapter.adapter_production_test;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Data.module.Production.AutoTestItem;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.rs232.Cmd.CmdHardwareTest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class Fgt_production_test extends productionFragment implements View.OnClickListener{
    private List<AutoTestItem> _data=new ArrayList<>();
    private adapter_production_test  adapter_test;
    private ListView test_item;
    private View lvheader;
    private CremApp app;
    private BaseActivity activity;
    private List<AutoTestItem> autoTestItems =new ArrayList<>();
    private Button start,next,stop;
    private IntentFilter filter;
    private PopupWindow popvalve;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_production_test, container, false);
        lvheader = inflater.inflate(R.layout.lv_device_header,null);
        InitData();
        InitView(view);
        activity = (BaseActivity)getActivity();
        app = activity.getApp();
        filter = new IntentFilter();
        filter.addAction(Constant.ACTION_TEST_ACK);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);

        return view;
    }
    private boolean isstop =false;
    public static int STATE_READY = 0;
    public static int STATE_TESTING= 1;
    private int state_test = 0;
    private AutoTestItem currenttestitem;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constant.ACTION_TEST_ACK))
            {
                int ackresult = intent.getIntExtra("ACK",2);
                int ackop = intent.getIntExtra("OP",1);
                int deviceid = intent.getIntExtra("ID",1);
                if(ackop == 1 || ackop ==2  )  //zhu bujian
                {
                    if((deviceid &0xffff0000)!=0x000a0000)
                    {
                        if (ackresult == 1) {
                            // TODO: 2018/7/5 show finish
                            if ((deviceid & 0xffff0000) != 0x00010000)
                                mHandler.sendEmptyMessageDelayed(1001, 2000);
                            else
                                mHandler.sendEmptyMessageDelayed(1001, 30000);
                        } else {
                            // TODO: 2018/7/5 show failed
                            if ((deviceid & 0xffff0000) != 0x00010000)
                                mHandler.sendEmptyMessageDelayed(1002, 2000);
                            else
                                mHandler.sendEmptyMessageDelayed(1002, 10000);
                        }
                    }
                }
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        activity.registerReceiver(receiver,filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        activity.unregisterReceiver(receiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isstop =true;
        mHandler.removeCallbacksAndMessages(null);

    }
    private CmdHardwareTest cmdHardwareTest;
    private void TestProcess()
    {
        while (!isstop)
        {
            if(autoTestItems.size()<=0)
            {
                isstop = true;
                mHandler.sendEmptyMessage(1003);
                break;
            }
            if(state_test!=STATE_READY)
            {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            currenttestitem = autoTestItems.get(0);
            currenttestitem.setResult(AutoTestItem.RESULT_TESTING);
            state_test = STATE_TESTING;
            switch (currenttestitem.getOptype())
            {
                case  AutoTestItem.OP_AUTO:
                    // TODO: 2018/7/5 send test  cmd to io
                    cmdHardwareTest =new CmdHardwareTest();
                    if(!currenttestitem.getTestID().startsWith("0001"))
                        app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,currenttestitem.getTestID(),"64") );
                    else
                        app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ONCE,currenttestitem.getTestID(),"64") );
                    mHandler.sendEmptyMessage(1000);
                    break;
                case AutoTestItem.OP_SENSOR:
                    // TODO: 2018/7/5 show win for sensor test
                    mHandler.sendEmptyMessageDelayed(3001,2000);
                    mHandler.sendEmptyMessage(1000);
                    break;
                case  AutoTestItem.OP_VALVE:
                    // TODO: 2018/7/5 show win for valve test
                    mHandler.sendEmptyMessage(2000);
                    mHandler.sendEmptyMessage(1000);
                    break;
            }
        }
    }
    private class TestRunnable implements Runnable
    {

        @Override
        public void run() {
            TestProcess();
        }
    };
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    adapter_test.notifyDataSetChanged();
                    test_item.smoothScrollToPosition(_data.size()-autoTestItems.size());
                    break;
                case 1001:
                    cmdHardwareTest =new CmdHardwareTest();
                    if(!currenttestitem.getTestID().startsWith("0001"))
                        app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,currenttestitem.getTestID(),"64") );
                    autoTestItems.remove(0);
                    currenttestitem.setResult(AutoTestItem.RESULT_OK);
                    adapter_test.notifyDataSetChanged();
                    state_test = STATE_READY;
                    break;
                case 1002:
                    cmdHardwareTest =new CmdHardwareTest();
                    if(!currenttestitem.getTestID().startsWith("0001"))
                        app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,currenttestitem.getTestID(),"64") );
                    autoTestItems.remove(0);
                    currenttestitem.setResult(AutoTestItem.RESULT_FAILED);
                    adapter_test.notifyDataSetChanged();
                    state_test = STATE_READY;
                    break;
                case 1003:
                    start.setEnabled(true);
                    next.setVisibility(View.VISIBLE);
                    app.setTestItemsReport(_data);
                    break;
                case 2000:
                    View contentView = LayoutInflater.from(activity).inflate(R.layout.pop_win_test_valve, null);
                    contentView.findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!currenttestitem.getTestID().startsWith("000A")) {
                                app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_OPEN_HOT, currenttestitem.getTestID(), "64"));
                                app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_CLOSE_HOT, currenttestitem.getTestID(), "64"));
                            }
                            else
                            {
                                app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS, currenttestitem.getTestID(), "64"));
                                app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF, currenttestitem.getTestID(), "64"));
                            }
                        }
                    });
                    contentView.findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!currenttestitem.getTestID().startsWith("000A"))
                                app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_CLOSE_HOT,currenttestitem.getTestID(),"64") );
                           else
                                app.addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF, currenttestitem.getTestID(), "64"));
                            mHandler.sendEmptyMessage(2001);
                            popvalve.dismiss();
                        }
                    });
                    ((TextView)contentView.findViewById(R.id.name)).setText(currenttestitem.getName(getActivity()));
                    popvalve = new PopupWindow(contentView);
                    popvalve.setWidth(600);
                    popvalve.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                    popvalve.showAtLocation(test_item, Gravity.CENTER, 0, 0);
                    break;
                case 2001:
                    autoTestItems.remove(0);
                    currenttestitem.setResult(AutoTestItem.RESULT_OK);
                    adapter_test.notifyDataSetChanged();
                    state_test = STATE_READY;
                    break;
                case 3001:
                    autoTestItems.remove(0);
                    currenttestitem.setResult(AutoTestItem.RESULT_OK);
                    adapter_test.notifyDataSetChanged();
                    state_test = STATE_READY;
                    break;
                default:
                    break;
            }
        }
    };
    private void InitData()
    {
        _data.clear();
      List<Device> devices = DeviceXmlFactory.getXmlDevice(FileHelper.PATH_CONFIG+"config.xmld");
        if(devices!=null && devices.size()>0)
        {
            for (Device device:devices)
            {
                if(device.getGroup_id() == 0x0001 || device.getGroup_id() == 0x0002||
                    device.getGroup_id() == 0x0003 || device.getGroup_id() == 0x0004||
                    device.getGroup_id() == 0x0015  )
                    _data.add(new AutoTestItem(device.GetDeviceId(),AutoTestItem.RESULT_IDEL,AutoTestItem.OP_AUTO));
                if(device.getGroup_id() == 0x0001 || device.getGroup_id() == 0x0004 || device.getGroup_id() == 0x000a)
                    _data.add(new AutoTestItem(device.GetDeviceId(),AutoTestItem.RESULT_IDEL,AutoTestItem.OP_VALVE));
                else if(device.getGroup_id() == 0x0006 || device.getGroup_id() == 0x0007 || device.getGroup_id() == 0x0008
                        ||device.getGroup_id() == 0x0009 || device.getGroup_id() == 0x00018|| device.getGroup_id() == 0x001a)
                    _data.add(new AutoTestItem(device.GetDeviceId(),AutoTestItem.RESULT_IDEL,AutoTestItem.OP_SENSOR));
            }
            Collections.sort(_data);
            autoTestItems.clear();
            for (int i=0;i<_data.size();i++)
            {
                    autoTestItems.add(_data.get(i));
            }
        }
        adapter_test = new adapter_production_test(getActivity(),_data);
    }
    @Override
    public void InitView(View view) {
        test_item = view.findViewById(R.id.test_item);
        test_item.addHeaderView(lvheader);
        test_item.setAdapter(adapter_test);
        start = view.findViewById(R.id.start);
        start.setOnClickListener(this);
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);
        next.setVisibility(View.GONE);
        stop = view.findViewById(R.id.stop);
        stop.setOnClickListener(this);
    }
    public  interface nextListerner
    {
        void Clicked(int a);
    }
    private nextListerner _nextListerner;
    public void setOnnextListerner(nextListerner a)
    {
        _nextListerner =a;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start:
                isstop =false;
                start.setEnabled(false);
                new Thread(new TestRunnable()).start();
                break;
            case R.id.stop:
                autoTestItems.clear();
                start.setEnabled(true);
                app.setTestItemsReport(_data);
                next.setVisibility(View.VISIBLE);
                break;
            case R.id.next:
                if(_nextListerner!=null)
                    _nextListerner.Clicked(2);
                break;
        }
    }
}
