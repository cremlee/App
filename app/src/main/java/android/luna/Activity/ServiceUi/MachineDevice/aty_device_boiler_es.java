package android.luna.Activity.ServiceUi.MachineDevice;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.MachineDevice.DEV_Waterpump;
import android.luna.Data.module.MachineDevice.Dev_Airbreak;
import android.luna.Data.module.MachineDevice.Dev_Boiler_ES;
import android.luna.Data.module.MachineDevice.Dev_Heater;
import android.luna.Data.module.MachineDevice.Dev_SenFlowmeter;
import android.luna.Data.module.MachineDevice.Dev_SenNtc;
import android.luna.Data.module.MachineDevice.Dev_SenPressuer;
import android.luna.Data.module.MachineDevice.Dev_SenWater;
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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.util.logging.LogRecord;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/19.
 */

public class aty_device_boiler_es extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;

    private SettingItemTextView2 boiler_id,ntc_id,ntc_type,watersen_id,watersen_type,ntc_offset,t_ntc,t_water;
    private SettingItemDropDown water_type,boiler_cp;
    private SettingItemSeekBar ntc_normal,ntc_warn,ntc_block,ntc_eco;
    private MaintenceItemView m_inlet_valve,m_boiler,m_water_level,m_ntc;
    private SettingItemCheckBox t_boiler_valve,t_heater;

    private Map<String, String> boiler_cp_data = new TreeMap<>();
    private Map<String, String> boiler_water_data = new TreeMap<>();
    private Device current_device =null;
    private List<Dev_SenNtc> devSenNtcs =new ArrayList<>(2);
    private List<Dev_SenWater> devSenWaters =new ArrayList<>(2);
    private Dev_Heater devHeater =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean stopflag =false;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopflag =true;
        mhandle.removeMessages(1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private Handler mhandle =new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what ==1000)
            {
                if(!stopflag) {
                    if(getApp().getAckQueryInstance().getNtcHighTemperature()!=0xffffffff)
                    t_ntc.setTextValue(getApp().getAckQueryInstance().getNtcHighTemperature() + " ℃");
                    mhandle.sendEmptyMessageDelayed(1000, 1500);
                }
            }
        }
    };


    @Override
    public void InitView() {
        setContentView(R.layout.aty_device_boiler_es);
        super.InitView();
        boiler_id = findViewById(R.id.boiler_id);
        water_type = findViewById(R.id.water_type);
        boiler_cp = findViewById(R.id.boiler_cp);
        m_inlet_valve= findViewById(R.id.m_inlet_valve);
        m_boiler= findViewById(R.id.m_boiler);
        t_boiler_valve= findViewById(R.id.t_boiler_valve);
        t_heater= findViewById(R.id.t_heater);

        boiler_id.setTextValue(String.format("%08X",getApp().getMain_device().GetDeviceId()));
        boiler_cp.setItemAndValues(boiler_cp_data);
        boiler_cp.refreshData(0);
        String key =(((Dev_Boiler_ES)getApp().getMain_device()).getMax_capability()+"");
        boiler_cp.setSelItem(key,boiler_cp.getItemAndValues().get(key));

        water_type.setItemAndValues(boiler_water_data);
        water_type.refreshData(0);
        key =(((Dev_Boiler_ES)getApp().getMain_device()).getInlet_water_type()+"");
        water_type.setSelItem(key,water_type.getItemAndValues().get(key));




        ntc_id = findViewById(R.id.ntc_id);
        ntc_type = findViewById(R.id.ntc_type);
        ntc_offset = findViewById(R.id.ntc_offset);
        t_ntc = findViewById(R.id.t_ntc);
        ntc_normal = findViewById(R.id.ntc_normal);
        ntc_warn = findViewById(R.id.ntc_warn);
        ntc_block = findViewById(R.id.ntc_block);
        ntc_eco = findViewById(R.id.ntc_eco);
        m_ntc = findViewById(R.id.m_ntc);



        watersen_id = findViewById(R.id.watersen_id);
        watersen_type = findViewById(R.id.watersen_type);
        t_water = findViewById(R.id.t_water);
        m_water_level = findViewById(R.id.m_water_level);

        ntc_id.setVisibility(View.GONE);
        ntc_type.setVisibility(View.GONE);
        ntc_offset.setVisibility(View.GONE);
        t_ntc.setVisibility(View.GONE);
        ntc_normal.setVisibility(View.GONE);
        ntc_warn.setVisibility(View.GONE);
        ntc_block.setVisibility(View.GONE);
        ntc_eco.setVisibility(View.GONE);
        m_ntc.setVisibility(View.GONE);

        watersen_id.setVisibility(View.GONE);
        watersen_type.setVisibility(View.GONE);
        t_water.setVisibility(View.GONE);
        m_water_level.setVisibility(View.GONE);

        btn_back = findViewById(R.id.btn_back);
        device_rg = findViewById(R.id.device_rg);
        sv_pty = findViewById(R.id.sv_pty);
        sv_tc = findViewById(R.id.sv_tc);
        sv_mt = findViewById(R.id.sv_mt);

        if(getApp().getAttach_device().size()>0) {
            for (Device device : getApp().getAttach_device()) {
                /*if(device.getGroup_id() == 0x0005)
                {
                    devSenFlowmeter = (Dev_SenFlowmeter)device;
                }*/
                 if (device.getGroup_id() == 0x0006)  //ntc
                {
                    devSenNtcs.add((Dev_SenNtc)device);
                }
                else if(device.getGroup_id() == 0x0007) //water
                {
                    devSenWaters.add((Dev_SenWater)device);
                }
                 else if(device.getGroup_id() == 0x0016) //heater
                 {
                     devHeater = (Dev_Heater) device;
                 }
                /*else if(device.getGroup_id() == 0x0009) //pressure
                {
                    devSenPressuer = (Dev_SenPressuer)device;
                }
                else if(device.getGroup_id() == 0x000A) //pressure
                {
                    devWaterpump = (DEV_Waterpump) device;
                }
                else if(device.getGroup_id() == 0x000E) //pressure
                {
                    devAirbreak = (Dev_Airbreak) device;
                }*/

            }
        }

        if(devSenNtcs.size()>0)
        {
            ntc_id.setVisibility(View.VISIBLE);
            ntc_type.setVisibility(View.VISIBLE);
            ntc_offset.setVisibility(View.VISIBLE);
            t_ntc.setVisibility(View.VISIBLE);
            ntc_normal.setVisibility(View.VISIBLE);
            ntc_warn.setVisibility(View.VISIBLE);
            ntc_block.setVisibility(View.VISIBLE);
            ntc_eco.setVisibility(View.VISIBLE);
            m_ntc.setVisibility(View.VISIBLE);
            ntc_id.setTextValue(devSenNtcs.size()==1?String.format("%08X",devSenNtcs.get(0).GetDeviceId()):String.format("%08X",devSenNtcs.get(0).GetDeviceId())+","+String.format("%08X",devSenNtcs.get(1).GetDeviceId()));
            ntc_type.setTextValue(devSenNtcs.size()==0x0001?"Single":"Double");
            ntc_normal.setCur((devSenNtcs.get(0)).getTemperature_normal());
            ntc_warn.setCur((devSenNtcs.get(0)).getTemperature_warning());
            ntc_block.setCur((devSenNtcs.get(0)).getTemperature_block());
            ntc_eco.setCur((devSenNtcs.get(0)).getTemperature_eco());
            // TODO: 2018/7/23 refresh the temperature
            mhandle.sendEmptyMessage(1000);
        }
        else
        {
            findViewById(R.id.lyt_ntc).setVisibility(View.GONE);
            t_ntc.setVisibility(View.GONE);
        }
        if(devSenWaters.size()>0)
        {
            watersen_id.setVisibility(View.VISIBLE);
            watersen_type.setVisibility(View.VISIBLE);
            t_water.setVisibility(View.VISIBLE);
            m_water_level.setVisibility(View.VISIBLE);
            watersen_id.setTextValue(devSenWaters.size()==1?String.format("%08X",devSenWaters.get(0).GetDeviceId()):String.format("%08X",devSenWaters.get(0).GetDeviceId())+","+String.format("%08X",devSenWaters.get(1).GetDeviceId()));
            watersen_type.setTextValue(devSenWaters.size()==0x0001?"Single":"Double");
        }
        else
        {
            findViewById(R.id.lyt_waterlevel).setVisibility(View.GONE);
            t_water.setVisibility(View.GONE);
        }
         if(devHeater == null)
         {
             t_heater.setVisibility(View.GONE);
         }

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
                boiler_cp_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }

        hopper_value = getResources().getStringArray(R.array.water_value);
        hopper_key = getResources().getIntArray(R.array.water_key);
        if(hopper_value.length <= hopper_key.length)
        {
            for (int i=0;i<hopper_key.length;i++)
            {
                boiler_water_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        boiler_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = boiler_cp.getItemAndValues().get(key);
                boiler_cp.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = getApp().getMain_device();
                ((Dev_Boiler_ES) current_device).setMax_capability(Integer.parseInt(key, 10));
                setDeviceData(current_device);
            }
        });

        water_type.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = water_type.getItemAndValues().get(key);
                water_type.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = getApp().getMain_device();
                ((Dev_Boiler_ES) current_device).setInlet_water_type(Integer.parseInt(key, 10));
                setDeviceData(current_device);
            }
        });


        ntc_normal.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    ntc_normal.setCur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int spd = seekBar.getProgress();
                current_device = devSenNtcs.get(0);
                ((Dev_SenNtc) current_device).setTemperature_normal(spd);
                setDeviceData(current_device);
            }
        });

        ntc_warn.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    ntc_warn.setCur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int spd = seekBar.getProgress();
                current_device = devSenNtcs.get(0);
                ((Dev_SenNtc) current_device).setTemperature_warning(spd);
                setDeviceData(current_device);
            }
        });

        ntc_block.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    ntc_block.setCur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int spd = seekBar.getProgress();
                current_device = devSenNtcs.get(0);
                ((Dev_SenNtc) current_device).setTemperature_block(spd);
                setDeviceData(current_device);
            }
        });

        ntc_eco.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser)
                    ntc_eco.setCur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int spd = seekBar.getProgress();
                current_device = devSenNtcs.get(0);
                ((Dev_SenNtc) current_device).setTemperature_eco(spd);
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
                switch (checkedId) {
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
        t_boiler_valve.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_INLET_OPEN,String.format("%08X",getApp().getMain_device().GetDeviceId()),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_INLET_CLOSE,String.format("%08X",getApp().getMain_device().GetDeviceId()),"64"));

                }
            }
        });
        /*t_water_pump.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,String.format("%08X",devWaterpump.GetDeviceId()),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,String.format("%08X",devWaterpump.GetDeviceId()),"64"));
                }
            }
        });*/
        t_heater.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ALWAYS,String.format("%08X",devHeater.GetDeviceId()),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OFF,String.format("%08X",devHeater.GetDeviceId()),"64"));
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
                AppManager.getAppManager().finishActivity(aty_device_boiler_es.this);
                break;
        }
    }
}
