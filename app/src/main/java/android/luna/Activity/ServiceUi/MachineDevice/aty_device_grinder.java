package android.luna.Activity.ServiceUi.MachineDevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Data.module.DeviceLayout.DeviceCalibrationItem;
import android.luna.Data.module.MachineDevice.Dev_Grinder;
import android.luna.Data.module.MachineDevice.Dev_Hopper;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.ViewUi.CalibrationView.ShowCalibrationDialog;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.widget.MaintenceItemView;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.luna.rs232.Cmd.CmdDeviceDbGet;
import android.luna.rs232.Cmd.CmdHardwareTest;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.Map;
import java.util.TreeMap;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/19.
 */

public class aty_device_grinder extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;
    private SettingItemTextView2 grinder_id,hopper1_id,hopper2_id;
    private SettingItemDropDown hopper1_cp ,hopper1_powder,hopper2_cp ,hopper2_powder;
    private MaintenceItemView m_grinder,m_hopper1,m_hopper2;

    private SettingItemTextView2 hopper1_dosage ,hopper2_dosage;
    private SettingItemCheckBox t_grinder ,t_hopper1,t_hopper2;
    private Map<String, String> hopper_cp_data = new TreeMap<>();
    private Map<String, String> hopper_bean_data = new TreeMap<>();

    private Device current_device =null;
    private CmdHardwareTest cmdHardwareTest;
    private MaterialDialog progressDialog;
    private int dosagevalue =50;
    private ShowCalibrationDialog showCalibrationDialog;
    private void showCalibrationwindow()
    {
        progressDialog = new MaterialDialog.Builder(aty_device_grinder.this)
                .title("Calibration")
                .canceledOnTouchOutside(false)
                .content("calibration...")
                .progress(true, 0)
                .show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_ACK_CALIBRATION_STATE);
        filter.addAction(Constant.ACTION_CALIBRATION_FINISH);
        filter.addAction(Constant.ACTION_DB_GET_GRINDER_CALIBRATION_VALUE);
        filter.addAction(Constant.ACTION_DB_SET_ACK);

        registerReceiver(receiver, filter);
       // getApp().addCmdQueue(new CmdDeviceDbGet().buildDeviceParam(getApp().getMain_device().GetDeviceId(),1));
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Constant.ACTION_ACK_CALIBRATION_STATE.equals(action))
            {
                if(intent.getIntExtra("ACK",2)==1)
                    showCalibrationwindow();
                else
                    showTestToast("Calibration failed!");
            }
            else if(Constant.ACTION_CALIBRATION_FINISH.equals(action))
            {
                if(progressDialog!=null) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
            else if(Constant.ACTION_DB_GET_GRINDER_CALIBRATION_VALUE.equals(action))
            {
                dosagevalue = intent.getIntExtra("DOSAGE",50);
            }
            else if(Constant.ACTION_DB_SET_ACK.equals(action))
            {
                if(showCalibrationDialog!=null)
                    showCalibrationDialog.dismiss();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void InitView() {
        setContentView(R.layout.aty_device_grinder);
        super.InitView();
        btn_back = findViewById(R.id.btn_back);
        device_rg = findViewById(R.id.device_rg);
        sv_pty = findViewById(R.id.sv_pty);
        sv_tc = findViewById(R.id.sv_tc);
        sv_mt = findViewById(R.id.sv_mt);
        grinder_id = findViewById(R.id.grinder_id);
        hopper1_id = findViewById(R.id.hopper1_id);
        hopper2_id = findViewById(R.id.hopper2_id);
        hopper1_cp = findViewById(R.id.hopper1_cp);
        hopper2_cp = findViewById(R.id.hopper2_cp);
        hopper1_powder = findViewById(R.id.hopper1_powder);
        hopper2_powder = findViewById(R.id.hopper2_powder);

        t_grinder = findViewById(R.id.t_grinder);
        t_hopper1 = findViewById(R.id.t_hopper1);
        t_hopper2 = findViewById(R.id.t_hopper2);

        hopper1_dosage = findViewById(R.id.hopper1_dosage);
        hopper2_dosage = findViewById(R.id.hopper2_dosage);

        m_grinder = findViewById(R.id.m_grinder);
        m_hopper1 = findViewById(R.id.m_hopper1);
        m_hopper2 = findViewById(R.id.m_hopper2);

        ((RadioButton)findViewById(R.id.rb_1)).setChecked(true);
        grinder_id.setTextValue(String.format("%08X",getApp().getMain_device().GetDeviceId()));
        findViewById(R.id.lyt_hopper1).setVisibility(View.GONE);
        findViewById(R.id.lyt_hopper2).setVisibility(View.GONE);
        t_hopper1.setVisibility(View.GONE);
        t_hopper2.setVisibility(View.GONE);
        hopper1_dosage.setVisibility(View.GONE);
        hopper2_dosage.setVisibility(View.GONE);
        m_hopper1.setVisibility(View.GONE);
        m_hopper2.setVisibility(View.GONE);

        if(getApp().getAttach_device().size() >= 1)
        {
            findViewById(R.id.lyt_hopper1).setVisibility(View.VISIBLE);
            t_hopper1.setVisibility(View.VISIBLE);
            hopper1_dosage.setVisibility(View.VISIBLE);
            m_hopper1.setVisibility(View.VISIBLE);
            //Capability
            hopper1_id.setTextValue(String.format("%08X",getApp().getAttach_device().get(0).GetDeviceId()));
            hopper1_cp.setItemAndValues(hopper_cp_data);
            hopper1_cp.refreshData(0);
            String key =(((Dev_Hopper)getApp().getAttach_device().get(0)).getMax_capability()+"");
            hopper1_cp.setSelItem(key,hopper1_cp.getItemAndValues().get(key));

            //Type
            hopper1_powder.setItemAndValues(hopper_bean_data);
            hopper1_powder.refreshData(0);
            key =(((Dev_Hopper)getApp().getAttach_device().get(0)).getPowder_type()+"");
            hopper1_powder.setSelItem(key,hopper1_powder.getItemAndValues().get(key));
        }
        if(getApp().getAttach_device().size() >= 2)
        {
            findViewById(R.id.lyt_hopper2).setVisibility(View.VISIBLE);
            t_hopper2.setVisibility(View.VISIBLE);
            hopper2_dosage.setVisibility(View.VISIBLE);
            m_hopper2.setVisibility(View.VISIBLE);


            hopper2_id.setTextValue(String.format("%08X",getApp().getAttach_device().get(1).GetDeviceId()));
            hopper2_cp.setItemAndValues(hopper_cp_data);
            hopper2_cp.refreshData(0);
            String key =(((Dev_Hopper)getApp().getAttach_device().get(1)).getMax_capability()+"");
            hopper2_cp.setSelItem(key,hopper2_cp.getItemAndValues().get(key));

            hopper2_powder.setItemAndValues(hopper_bean_data);
            hopper2_powder.refreshData(0);

            key =(((Dev_Hopper)getApp().getAttach_device().get(1)).getPowder_type()+"");
            hopper2_powder.setSelItem(key,hopper2_powder.getItemAndValues().get(key));
        }


    }

    @Override
    public void InitData() {
        super.InitData();
        String[] hopper_value = getResources().getStringArray(R.array.hopper_cap_value);
        int[] hopper_key = getResources().getIntArray(R.array.hopper_cap_key);
        if(hopper_value.length <= hopper_key.length)
        {
            for (int i=0;i<hopper_key.length;i++)
            {
                hopper_cp_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }

        String[] bean_value = getResources().getStringArray(R.array.hopper_bean_value);
        int[] bean_key = getResources().getIntArray(R.array.hopper_bean_key);
        if(bean_value.length <= bean_key.length)
        {
            for (int i=0;i<bean_key.length;i++)
            {
                hopper_bean_data.put(bean_key[i]+"",bean_value[i]);
            }
        }
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        hopper1_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = hopper1_cp.getItemAndValues().get(key);
                hopper1_cp.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = getApp().getAttach_device().get(0);
                ((Dev_Hopper)current_device).setMax_capability(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        hopper2_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = hopper2_cp.getItemAndValues().get(key);
                hopper2_cp.setSelItem(key, name);
                //// TODO: 2018/4/20 tongbu device shu ju
                current_device = getApp().getAttach_device().get(1);
                ((Dev_Hopper)current_device).setMax_capability(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        hopper2_powder.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = hopper2_powder.getItemAndValues().get(key);
                hopper2_powder.setSelItem(key, name);
                //// TODO: 2018/4/20 tongbu device shu ju
                current_device = getApp().getAttach_device().get(1);
                ((Dev_Hopper)current_device).setPowder_type(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        hopper1_powder.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = hopper1_powder.getItemAndValues().get(key);
                hopper1_powder.setSelItem(key, name);
                //// TODO: 2018/4/20 tongbu device shu ju
                current_device = getApp().getAttach_device().get(0);
                ((Dev_Hopper)current_device).setPowder_type(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });


        hopper1_dosage.setOnClickListener(this);
        hopper2_dosage.setOnClickListener(this);

        btn_back.setOnClickListener(this);
        device_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                sv_pty.setVisibility(View.INVISIBLE);
                sv_tc.setVisibility(View.INVISIBLE);
                sv_mt.setVisibility(View.INVISIBLE);
                switch (checkedId)
                {
                    case R.id.rb_1:
                        sv_pty.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_2:
                        sv_tc.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rb_3:
                        sv_mt.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        t_grinder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,grinder_id.getTextValue(),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,grinder_id.getTextValue(),"64"));

                }
            }
        });
        t_hopper1.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,hopper1_id.getTextValue(),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,hopper1_id.getTextValue(),"64"));

                }
            }
        });

        t_hopper2.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,hopper2_id.getTextValue(),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,hopper2_id.getTextValue(),"64"));

                }
            }
        });
    }

    @Override
    public void setDeviceData(Device a) {
        DeviceXmlFactory.UpdateDeviceXml(a);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_device_grinder.this);
                break;
            case R.id.hopper1_dosage:
                showCalibrationDialog =new  ShowCalibrationDialog(this,new DeviceCalibrationItem(getApp().getMain_device().GetDeviceId(),1,
                        250,50,10,"g",dosagevalue,true));
                showCalibrationDialog.show();
                break;
            case R.id.hopper2_dosage:
                break;
        }
    }
}
