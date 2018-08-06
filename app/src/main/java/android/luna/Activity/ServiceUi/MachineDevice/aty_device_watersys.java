package android.luna.Activity.ServiceUi.MachineDevice;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.MachineDevice.Dev_Canister;
import android.luna.Data.module.MachineDevice.Dev_Heater;
import android.luna.Data.module.MachineDevice.Dev_SenFlowmeter;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.Device.DeviceXmlFactory;
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
import android.widget.SeekBar;

import java.util.Map;
import java.util.TreeMap;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/19.
 */

public class aty_device_watersys extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;

    private SettingItemTextView2 pump_id,flowmeter_id;
    private SettingItemSeekBar flowmeter_pluse;
    private MaintenceItemView m_pump_motor;
    private SettingItemCheckBox t_pump;

    private Device current_device =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Dev_SenFlowmeter devSenFlowmeter =null;
    @Override
    public void InitView() {
        setContentView(R.layout.aty_device_watersys);
        super.InitView();
        pump_id = findViewById(R.id.pump_id);
        flowmeter_id = findViewById(R.id.flowmeter_id);
        flowmeter_pluse = findViewById(R.id.flowmeter_pluse);
        m_pump_motor = findViewById(R.id.m_pump_motor);
        t_pump = findViewById(R.id.t_pump);

        btn_back = findViewById(R.id.btn_back);
        device_rg = findViewById(R.id.device_rg);
        sv_pty = findViewById(R.id.sv_pty);
        sv_tc = findViewById(R.id.sv_tc);
        sv_mt = findViewById(R.id.sv_mt);
        ((RadioButton)findViewById(R.id.rb_1)).setChecked(true);

        pump_id.setTextValue(String.format("%08X",getApp().getMain_device().GetDeviceId()));
        if(getApp().getAttach_device().size()>0) {
            for (Device device : getApp().getAttach_device()) {
                if (device.getGroup_id() == 0x0005) {
                    devSenFlowmeter = (Dev_SenFlowmeter) device;
                }
            }
        }
        if(devSenFlowmeter!=null)
        {
            flowmeter_id.setTextValue(String.format("%08X",devSenFlowmeter.GetDeviceId()));
            flowmeter_pluse.setCur(devSenFlowmeter.getPluse());
        }
    }

    @Override
    public void InitData() {
        super.InitData();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();

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
        t_pump.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,String.format("%08X",getApp().getMain_device().GetDeviceId()),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,String.format("%08X",getApp().getMain_device().GetDeviceId()),"64"));
                }
            }
        });
        flowmeter_pluse.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    flowmeter_pluse.setCur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int spd = seekBar.getProgress();
                current_device = devSenFlowmeter;
                ((Dev_SenFlowmeter) current_device).setPluse(spd);
                setDeviceData(current_device);
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
                AppManager.getAppManager().finishActivity(aty_device_watersys.this);
                break;
        }
    }
}
