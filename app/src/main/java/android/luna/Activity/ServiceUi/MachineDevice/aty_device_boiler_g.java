package android.luna.Activity.ServiceUi.MachineDevice;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.MachineDevice.Dev_Boiler_G;
import android.luna.Data.module.MachineDevice.Dev_ES;
import android.luna.Data.module.MachineDevice.Dev_Fan;
import android.luna.Data.module.MachineDevice.Dev_Mixer_L;
import android.luna.Data.module.MachineDevice.Dev_SenNtc;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.ViewUi.widget.MaintenceItemView;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
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

public class aty_device_boiler_g extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;

    private SettingItemTextView2 boiler_id,ntc_id,ntc_type,watersen_id,watersen_type,ntc_offset,t_ntc,t_water;
    private SettingItemDropDown water_type,boiler_cp;
    private SettingItemSeekBar ntc_normal,ntc_warn,ntc_block,ntc_eco;
    private MaintenceItemView m_inlet_valve,m_boiler,m_water_level,m_ntc;
    private SettingItemCheckBox t_boiler_valve;

    private Map<String, String> boiler_cp_data = new TreeMap<>();
    private Map<String, String> boiler_water_data = new TreeMap<>();
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
        setContentView(R.layout.aty_device_boiler_g);
        super.InitView();
        boiler_id = findViewById(R.id.boiler_id);
        water_type = findViewById(R.id.water_type);
        boiler_cp = findViewById(R.id.boiler_cp);
        m_inlet_valve= findViewById(R.id.m_inlet_valve);
        m_boiler= findViewById(R.id.m_boiler);
        t_boiler_valve= findViewById(R.id.t_boiler_valve);


        boiler_id.setTextValue(String.format("%08X",getApp().getMain_device().GetDeviceId()));
        boiler_cp.setItemAndValues(boiler_cp_data);
        boiler_cp.refreshData(0);
        String key =(((Dev_Boiler_G)getApp().getMain_device()).getMax_capability()+"");
        boiler_cp.setSelItem(key,boiler_cp.getItemAndValues().get(key));

        water_type.setItemAndValues(boiler_water_data);
        water_type.refreshData(0);
        key =((getApp().getMain_device()).getCompent_type()+"");
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

        if(getApp().getAttach_device().size()>0) {
            for (Device device : getApp().getAttach_device()) {
                if (device.getGroup_id() == 0x0006)  //ntc
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
                    ntc_id.setTextValue(String.format("%08X",device.GetDeviceId()));
                    ntc_type.setTextValue(device.getCompent_type()==0x0001?"Single":"Double");
                    ntc_normal.setCur(((Dev_SenNtc )device).getTemperature_normal());
                    ntc_warn.setCur(((Dev_SenNtc )device).getTemperature_warning());
                    ntc_block.setCur(((Dev_SenNtc )device).getTemperature_block());
                    ntc_eco.setCur(((Dev_SenNtc )device).getTemperature_eco());
                }
                else if(device.getGroup_id() == 0x0007) //water
                {
                    watersen_id.setVisibility(View.VISIBLE);
                    watersen_type.setVisibility(View.VISIBLE);
                    t_water.setVisibility(View.VISIBLE);
                    m_water_level.setVisibility(View.VISIBLE);
                    watersen_id.setTextValue(String.format("%08X",device.GetDeviceId()));
                    watersen_type.setTextValue(device.getCompent_type()==0x0001?"Single":"Double");
                }
            }
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
                ((Dev_Boiler_G)current_device).setMax_capability(Integer.parseInt(key,10));
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
                ((Dev_Boiler_G)current_device).setInlet_water_type(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        if(getApp().getAttach_device().size()>0) {
            for (Device device : getApp().getAttach_device()) {
                final Device deviceitem = device;
                if (device.getGroup_id() == 0x0006)  //ntc
                {
                    ntc_normal.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(fromUser)
                                ntc_normal.setCur(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            int spd = seekBar.getProgress();
                            current_device = deviceitem;
                            ((Dev_SenNtc)deviceitem).setTemperature_normal(spd);
                            setDeviceData(current_device);
                        }
                    });

                    ntc_warn.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(fromUser)
                                ntc_warn.setCur(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            int spd = seekBar.getProgress();
                            current_device = deviceitem;
                            ((Dev_SenNtc)deviceitem).setTemperature_warning(spd);
                            setDeviceData(current_device);
                        }
                    });

                    ntc_block.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(fromUser)
                                ntc_block.setCur(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            int spd = seekBar.getProgress();
                            current_device = deviceitem;
                            ((Dev_SenNtc)deviceitem).setTemperature_block(spd);
                            setDeviceData(current_device);
                        }
                    });

                    ntc_eco.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if(fromUser)
                                ntc_eco.setCur(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            int spd = seekBar.getProgress();
                            current_device = deviceitem;
                            ((Dev_SenNtc)deviceitem).setTemperature_eco(spd);
                            setDeviceData(current_device);
                        }
                    });

                }
                else if (device.getGroup_id() == 0x0007)  //water
                {

                }
            }
        }


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
                AppManager.getAppManager().finishActivity(aty_device_boiler_g.this);
                break;
        }
    }
}
