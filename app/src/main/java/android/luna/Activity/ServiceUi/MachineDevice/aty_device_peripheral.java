package android.luna.Activity.ServiceUi.MachineDevice;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.MachineDevice.Dev_Led;
import android.luna.Data.module.MachineDevice.Dev_Mixer_L;
import android.luna.Data.module.MachineDevice.Dev_SenCup;
import android.luna.Data.module.MachineDevice.Dev_SenDoor;
import android.luna.Data.module.MachineDevice.Dev_SenDriptray;
import android.luna.Data.module.MachineDevice.Dev_SenWaster;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.ViewUi.widget.MaintenceItemView;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
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

public class aty_device_peripheral extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;

    private LinearLayout lyt_door,lyt_cup,lyt_drip,lyt_waster,lyt_led;

    private SettingItemTextView2 door_id,cup1_id,cup2_id,drip_id,waster_id,led_id,led_type;
    private SettingItemDropDown drip_cp,waster_cp;
    private SettingItemDropDown led_i_mode,led_i_color,led_i_int;
    private SettingItemDropDown led_w_mode,led_w_color,led_w_int;
    private SettingItemCheckBox t_led;
    private SettingItemTextView2 t_door_st,t_drip_st,t_waster_st,t_cup_st;
    private MaintenceItemView m_door , m_led;

    private Device current_device =null;
    private Map<String, String> led_mode_data = new TreeMap<>();
    private Map<String, String> led_color_data = new TreeMap<>();
    private Map<String, String> led_int_data = new TreeMap<>();

    private Map<String, String> waster_cp_data = new TreeMap<>();
    private Map<String, String> drip_cp_data = new TreeMap<>();

    private  Dev_SenDriptray    devSenDriptray=null;
    private  Dev_SenWaster      devSenWaster=null;
    private  Dev_Led            devLed=null;
    private  Dev_SenCup         devSenCup1=null;
    private  Dev_SenCup         devSenCup2=null;
    private  Dev_SenDoor        devSenDoor=null;

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
        setContentView(R.layout.aty_device_peripheral);
        super.InitView();

        lyt_door = findViewById(R.id.lyt_door);
        lyt_cup = findViewById(R.id.lyt_cup);
        lyt_drip = findViewById(R.id.lyt_drip);
        lyt_waster = findViewById(R.id.lyt_waster);
        lyt_led = findViewById(R.id.lyt_led);


        cup1_id = findViewById(R.id.cup1_id);
        cup2_id = findViewById(R.id.cup2_id);
        door_id = findViewById(R.id.door_id);
        drip_id = findViewById(R.id.drip_id);
        waster_id = findViewById(R.id.waster_id);
        led_id = findViewById(R.id.led_id);
        led_type = findViewById(R.id.led_type);

        drip_cp= findViewById(R.id.drip_cp);
        waster_cp= findViewById(R.id.waster_cp);

        led_i_mode= findViewById(R.id.led_i_mode);
        led_i_color= findViewById(R.id.led_i_color);
        led_i_int= findViewById(R.id.led_i_int);

        led_w_mode= findViewById(R.id.led_w_mode);
        led_w_color= findViewById(R.id.led_w_color);
        led_w_int= findViewById(R.id.led_w_int);


        t_led = findViewById(R.id.t_led);
        t_door_st = findViewById(R.id.t_door_st);
        t_drip_st = findViewById(R.id.t_drip_st);
        t_waster_st = findViewById(R.id.t_waster_st);
        t_cup_st = findViewById(R.id.t_cup_st);
        m_door = findViewById(R.id.m_door);
        m_led = findViewById(R.id.m_led);


        lyt_door.setVisibility(View.GONE);
        lyt_cup.setVisibility(View.GONE);
        lyt_drip.setVisibility(View.GONE);
        lyt_waster.setVisibility(View.GONE);
        lyt_led.setVisibility(View.GONE);

        if(getApp().getAttach_device().size()>0)
        {
            for (Device device:getApp().getAttach_device())
            {
                if(device.getGroup_id() ==0x0008) //cup
                {
                    lyt_cup.setVisibility(View.VISIBLE);
                    if(cup1_id.getTextValue().equalsIgnoreCase("none"))
                    {
                        devSenCup1 = (Dev_SenCup)device;
                        cup1_id.setTextValue(String.format("%08X",device.GetDeviceId()));
                    }
                    else if(cup2_id.getTextValue().equalsIgnoreCase("none"))
                    {
                        devSenCup2 = (Dev_SenCup)device;
                        cup2_id.setTextValue(String.format("%08X",device.GetDeviceId()));
                    }

                }
                else if(device.getGroup_id() ==0x000c) //led
                {
                    devLed = (Dev_Led)device;
                    lyt_led.setVisibility(View.VISIBLE);
                    led_id.setTextValue(String.format("%08X",device.GetDeviceId()));
                    led_type.setTextValue(device.getCompent_type()==1?"single color":"three colors");

                    led_i_mode.setItemAndValues(led_mode_data);
                    led_i_mode.refreshData(0);
                    String key =(((Dev_Led)device).getLed_idel_mode()+"");
                    led_i_mode.setSelItem(key,led_i_mode.getItemAndValues().get(key));

                    led_w_mode.setItemAndValues(led_mode_data);
                    led_w_mode.refreshData(0);
                    key=(((Dev_Led)device).getLed_warn_mode()+"");
                    led_w_mode.setSelItem(key,led_w_mode.getItemAndValues().get(key));

                    led_i_color.setItemAndValues(led_color_data);
                    led_i_color.refreshData(0);
                    key =(((Dev_Led)device).getLed_idel_color()+"");
                    led_i_color.setSelItem(key,led_i_color.getItemAndValues().get(key));

                    led_w_color.setItemAndValues(led_color_data);
                    led_w_color.refreshData(0);
                    key =(((Dev_Led)device).getLed_warn_color()+"");
                    led_w_color.setSelItem(key,led_w_color.getItemAndValues().get(key));

                    led_i_int.setItemAndValues(led_int_data);
                    led_i_int.refreshData(0);
                    key =(((Dev_Led)device).getLed_idel_intensity()+"");
                    led_i_int.setSelItem(key,led_i_int.getItemAndValues().get(key));

                    led_w_int.setItemAndValues(led_int_data);
                    led_w_int.refreshData(0);
                    key =(((Dev_Led)device).getLed_warn_intensity()+"");
                    led_w_int.setSelItem(key,led_w_int.getItemAndValues().get(key));
                }
                else if(device.getGroup_id() ==0x0018) //drip
                {
                    devSenDriptray =(Dev_SenDriptray) device;
                    lyt_drip.setVisibility(View.VISIBLE);
                    drip_id.setTextValue(String.format("%08X",device.GetDeviceId()));
                    drip_cp.setItemAndValues(drip_cp_data);
                    drip_cp.refreshData(0);
                    String key =(((Dev_SenDriptray)device).getMax_capability()+"");
                    drip_cp.setSelItem(key,drip_cp.getItemAndValues().get(key));
                }
                else if(device.getGroup_id() ==0x0019) //waster
                {
                    devSenWaster = (Dev_SenWaster) device;
                    lyt_waster.setVisibility(View.VISIBLE);
                    waster_id.setTextValue(String.format("%08X",device.GetDeviceId()));
                    waster_cp.setItemAndValues(waster_cp_data);
                    waster_cp.refreshData(0);
                    String key =(((Dev_SenWaster)device).getMax_capability()+"");
                    waster_cp.setSelItem(key,waster_cp.getItemAndValues().get(key));
                }
                else if(device.getGroup_id() ==0x001a) //door
                {
                    devSenDoor =(Dev_SenDoor)device;
                    lyt_door.setVisibility(View.VISIBLE);
                    door_id.setTextValue(String.format("%08X",device.GetDeviceId()));
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
                waster_cp_data.put(hopper_key[i]+"",hopper_value[i]);
                drip_cp_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }

        hopper_value = getResources().getStringArray(R.array.led_color_value);
        hopper_key = getResources().getIntArray(R.array.led_color_key);
        if(hopper_value.length <= hopper_key.length)
        {
            for (int i=0;i<hopper_key.length;i++)
            {
                led_color_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }

        hopper_value = getResources().getStringArray(R.array.led_mode_value);
        hopper_key = getResources().getIntArray(R.array.led_mode_key);
        if(hopper_value.length <= hopper_key.length)
        {
            for (int i=0;i<hopper_key.length;i++)
            {
                led_mode_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }

        hopper_value = getResources().getStringArray(R.array.led_int_value);
        hopper_key = getResources().getIntArray(R.array.led_int_key);
        if(hopper_value.length <= hopper_key.length)
        {
            for (int i=0;i<hopper_key.length;i++)
            {
                led_int_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        //drip_cp,waster_cp
        drip_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = drip_cp.getItemAndValues().get(key);
                drip_cp.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devSenDriptray;
                devSenDriptray.setMax_capability(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        waster_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = waster_cp.getItemAndValues().get(key);
                waster_cp.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devSenWaster;
                devSenWaster.setMax_capability(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        led_i_mode.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = led_i_mode.getItemAndValues().get(key);
                led_i_mode.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devLed;
                devLed.setLed_idel_mode(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        led_i_color.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = led_i_color.getItemAndValues().get(key);
                led_i_color.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devLed;
                devLed.setLed_idel_color(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        led_i_int.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = led_i_int.getItemAndValues().get(key);
                led_i_int.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devLed;
                devLed.setLed_idel_intensity(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        led_w_mode.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = led_w_mode.getItemAndValues().get(key);
                led_w_mode.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devLed;
                devLed.setLed_warn_mode(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        led_w_color.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = led_w_color.getItemAndValues().get(key);
                led_w_color.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devLed;
                devLed.setLed_warn_color(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });

        led_w_int.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = led_w_int.getItemAndValues().get(key);
                led_w_int.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = devLed;
                devLed.setLed_warn_intensity(Integer.parseInt(key,10));
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
                AppManager.getAppManager().finishActivity(aty_device_peripheral.this);
                break;
        }
    }
}
