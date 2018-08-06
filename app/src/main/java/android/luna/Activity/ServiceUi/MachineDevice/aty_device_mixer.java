package android.luna.Activity.ServiceUi.MachineDevice;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.MachineDevice.Dev_ES;
import android.luna.Data.module.MachineDevice.Dev_Fan;
import android.luna.Data.module.MachineDevice.Dev_Mixer_L;
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

import static evo.luna.android.R.id.hopper2_id;

/**
 * Created by Lee.li on 2018/4/19.
 */

public class aty_device_mixer extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;

    private SettingItemTextView2 mixer_id,fan_id,hot_valve_flow,cold_valve_flow;
    private SettingItemDropDown mixer_cp;
    private SettingItemSeekBar mixer_spd,fan_spd;
    private MaintenceItemView m_mix_motor,m_cold_valve,m_hot_valve,m_mix_whipper,m_fan_motor;
    private SettingItemCheckBox t_mixer_motor,t_mixer_hot,t_mixer_cold,t_fan_motor;


    private Map<String, String> mixer_cp_data = new TreeMap<>();
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

    @Override
    public void InitView() {
        setContentView(R.layout.aty_device_mixer);
        super.InitView();

        mixer_id = findViewById(R.id.mixer_id);
        mixer_cp = findViewById(R.id.mixer_cp);
        hot_valve_flow = findViewById(R.id.hot_valve_flow);
        cold_valve_flow = findViewById(R.id.cold_valve_flow);
        mixer_spd = findViewById(R.id.mixer_spd);
        m_mix_motor = findViewById(R.id.m_mix_motor);
        m_cold_valve = findViewById(R.id.m_cold_valve);
        m_hot_valve = findViewById(R.id.m_hot_valve);
        m_mix_whipper = findViewById(R.id.m_mix_whipper);
        t_mixer_motor = findViewById(R.id.t_mixer_motor);
        t_mixer_hot = findViewById(R.id.t_mixer_hot);
        t_mixer_cold = findViewById(R.id.t_mixer_cold);

        t_fan_motor = findViewById(R.id.t_fan_motor);
        m_fan_motor = findViewById(R.id.m_fan_motor);
        fan_spd = findViewById(R.id.fan_spd);
        fan_id = findViewById(R.id.fan_id);

        t_fan_motor.setVisibility(View.GONE);
        m_fan_motor.setVisibility(View.GONE);
        fan_spd.setVisibility(View.GONE);
        fan_id.setVisibility(View.GONE);


        mixer_id.setTextValue(String.format("%08X",getApp().getMain_device().GetDeviceId()));
        mixer_cp.setItemAndValues(mixer_cp_data);
        mixer_cp.refreshData(0);
        String key =(((Dev_Mixer_L)getApp().getMain_device()).getMax_capability()+"");
        mixer_cp.setSelItem(key,mixer_cp.getItemAndValues().get(key));
        mixer_spd.setCur(((Dev_Mixer_L)getApp().getMain_device()).getRun_speed());

        if(getApp().getAttach_device().size()>0)
        {
            t_fan_motor.setVisibility(View.VISIBLE);
            m_fan_motor.setVisibility(View.VISIBLE);
            fan_spd.setVisibility(View.VISIBLE);
            fan_id.setVisibility(View.VISIBLE);
            fan_id.setTextValue(String.format("%08X",getApp().getAttach_device().get(0).GetDeviceId()));
            fan_spd.setCur(((Dev_Fan)getApp().getAttach_device().get(0)).getRun_speed());
        }

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
        String[] hopper_value = getResources().getStringArray(R.array.es_cp_value);
        int[] hopper_key = getResources().getIntArray(R.array.es_cp_key);
        if(hopper_value.length <= hopper_key.length)
        {
            for (int i=0;i<hopper_key.length;i++)
            {
                mixer_cp_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }
    }

    @Override
    public void InitEvent() {
        super.InitEvent();

        mixer_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = mixer_cp.getItemAndValues().get(key);
                mixer_cp.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = getApp().getMain_device();
                ((Dev_Mixer_L)current_device).setMax_capability(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });
        mixer_spd.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    mixer_spd.setCur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int spd = seekBar.getProgress();
                current_device = getApp().getMain_device();
                ((Dev_Mixer_L)current_device).setRun_speed(spd);
                setDeviceData(current_device);
            }
        });
        fan_spd.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser)
                    fan_spd.setCur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int spd = seekBar.getProgress();
                current_device = getApp().getAttach_device().get(0);
                ((Dev_Fan)current_device).setRun_speed(spd);
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
        t_mixer_motor.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,mixer_id.getTextValue(),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,mixer_id.getTextValue(),"64"));

                }
            }
        });

        t_mixer_hot.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_OPEN_HOT,mixer_id.getTextValue(),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_CLOSE_HOT,mixer_id.getTextValue(),"64"));

                }
            }
        });
        t_mixer_cold.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_OPEN_COLD,mixer_id.getTextValue(),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_CLOSE_COLD,mixer_id.getTextValue(),"64"));

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
                AppManager.getAppManager().finishActivity(aty_device_mixer.this);
                break;
        }
    }
}
