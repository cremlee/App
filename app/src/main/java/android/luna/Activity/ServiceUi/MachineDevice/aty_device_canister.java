package android.luna.Activity.ServiceUi.MachineDevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Data.module.DeviceLayout.DeviceCalibrationItem;
import android.luna.Data.module.MachineDevice.Dev_Canister;
import android.luna.Data.module.MachineDevice.Dev_Hopper;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.ViewUi.CalibrationView.ShowCalibrationDialog;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.widget.MaintenceItemView;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.luna.rs232.Cmd.CmdHardwareTest;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import java.util.Map;
import java.util.TreeMap;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/19.
 */

public class aty_device_canister extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;

    private SettingItemTextView2 canister_id,canister_type,canister_dosage;
    private SettingItemDropDown canister_cp,canister_powder;
    private MaintenceItemView m_canister;
    private SettingItemCheckBox t_canistor;

    private Map<String, String> canister_cp_data = new TreeMap<>();
    private Map<String, String> canister_powder_data = new TreeMap<>();
    private Device current_device =null;
    private MaterialDialog progressDialog;
    private ShowCalibrationDialog showCalibrationDialog;
    private int dosagevalue =50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_ACK_CALIBRATION_STATE);
        filter.addAction(Constant.ACTION_CALIBRATION_FINISH);
        filter.addAction(Constant.ACTION_DB_GET_CANISTER_CALIBRATION_VALUE);
        filter.addAction(Constant.ACTION_DB_SET_ACK);
        registerReceiver(receiver, filter);
    }
    private void showCalibrationwindow()
    {
        progressDialog = new MaterialDialog.Builder(aty_device_canister.this)
                .title("Calibration")
                .canceledOnTouchOutside(false)
                .content("calibration...")
                .progress(true, 0)
                .show();
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
            else if(Constant.ACTION_DB_GET_CANISTER_CALIBRATION_VALUE.equals(action))
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
        setContentView(R.layout.aty_device_canister);
        super.InitView();
        canister_id = findViewById(R.id.canister_id);
        canister_type = findViewById(R.id.canister_type);
        canister_dosage = findViewById(R.id.canister_dosage);
        canister_cp = findViewById(R.id.canister_cp);
        canister_powder = findViewById(R.id.canister_powder);
        m_canister = findViewById(R.id.m_canister);
        t_canistor = findViewById(R.id.t_canistor);

        canister_id.setTextValue(String.format("%08X",getApp().getMain_device().GetDeviceId()));
        canister_type.setTextValue(getApp().getMain_device().getCompent_type()+"");

        canister_cp.setItemAndValues(canister_cp_data);
        canister_cp.refreshData(0);
        String key =(((Dev_Canister)getApp().getMain_device()).getMax_capability()+"");
        canister_cp.setSelItem(key,canister_cp.getItemAndValues().get(key));

        canister_powder.setItemAndValues(canister_powder_data);
        canister_powder.refreshData(0);
         key =(((Dev_Canister)getApp().getMain_device()).getPowder_type()+"");
        canister_powder.setSelItem(key,canister_powder.getItemAndValues().get(key));


        btn_back = findViewById(R.id.btn_back);
        device_rg = findViewById(R.id.device_rg);
        sv_pty = findViewById(R.id.sv_pty);
        sv_tc = findViewById(R.id.sv_tc);
        sv_mt = findViewById(R.id.sv_mt);
        ((RadioButton)findViewById(R.id.rb_1)).setChecked(true);
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
                canister_cp_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }

         hopper_value = getResources().getStringArray(R.array.power_value);
         hopper_key = getResources().getIntArray(R.array.powder_key);
        if(hopper_value.length <= hopper_key.length)
        {
            for (int i=0;i<hopper_key.length;i++)
            {
                canister_powder_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }
    }

    @Override
    public void InitEvent() {
        super.InitEvent();

        canister_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = canister_cp.getItemAndValues().get(key);
                canister_cp.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = getApp().getMain_device();
                ((Dev_Canister)current_device).setMax_capability(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        canister_powder.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = canister_powder.getItemAndValues().get(key);
                canister_powder.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = getApp().getMain_device();
                ((Dev_Canister)current_device).setPowder_type(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

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

        t_canistor.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,canister_id.getTextValue(),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,canister_id.getTextValue(),"64"));

                }
            }
        });
        canister_dosage.setOnClickListener(this);
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
            case R.id.canister_dosage:
                showCalibrationDialog =new  ShowCalibrationDialog(this,new DeviceCalibrationItem(getApp().getMain_device().GetDeviceId(),2,
                        250,50,10,"g",50,true));
                showCalibrationDialog.show();
                break;
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_device_canister.this);
                break;
        }
    }
}
