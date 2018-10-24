package android.luna.Activity.ServiceUi.MachineDevice;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.MachineDevice.Dev_Fan;
import android.luna.Data.module.MachineDevice.Dev_Mono;
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
import android.widget.LinearLayout;
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

public class aty_device_monobrewer extends BaseActivity implements View.OnClickListener {
    private Button btn_back;
    private RadioGroup device_rg;
    private ScrollView sv_pty,sv_tc,sv_mt;
    private SettingItemTextView2 brewer_id,fan_id,cal_brew_inlet;
    private SettingItemDropDown brewer_cp;
    private SettingItemSeekBar fan_spd;
    private MaintenceItemView m_brewer,m_valve,m_fan;
    private SettingItemCheckBox t_brew_motor,t_brew_valve,t_fan;
    private Map<String, String> es_cp_data = new TreeMap<>();
    private Device current_device =null;
    private LinearLayout lyt_fan;
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
        setContentView(R.layout.aty_device_esbrewer);
        super.InitView();
        btn_back = findViewById(R.id.btn_back);
        device_rg = findViewById(R.id.device_rg);
        sv_pty = findViewById(R.id.sv_pty);
        sv_tc = findViewById(R.id.sv_tc);
        sv_mt = findViewById(R.id.sv_mt);
        lyt_fan = findViewById(R.id.lyt_fan);
        brewer_id = findViewById(R.id.brewer_id);
        cal_brew_inlet = findViewById(R.id.cal_brew_inlet);
        brewer_cp = findViewById(R.id.brewer_cp);
        m_brewer = findViewById(R.id.m_brewer);
        m_valve = findViewById(R.id.m_valve);
        t_brew_motor = findViewById(R.id.t_brew_motor);
        t_brew_valve = findViewById(R.id.t_brew_valve);


        fan_id = findViewById(R.id.fan_id);
        fan_spd = findViewById(R.id.fan_spd);
        m_fan= findViewById(R.id.m_fan);
        t_fan = findViewById(R.id.t_fan);

        fan_id.setVisibility(View.GONE);
        fan_spd.setVisibility(View.GONE);
        m_fan.setVisibility(View.GONE);
        t_fan.setVisibility(View.GONE);
        lyt_fan.setVisibility(View.GONE);
        brewer_id.setTextValue(String.format("%08X",getApp().getMain_device().GetDeviceId()));
        brewer_cp.setItemAndValues(es_cp_data);
        brewer_cp.refreshData(0);
        String key =(((Dev_Mono)getApp().getMain_device()).getMax_capability()+"");
        brewer_cp.setSelItem(key,brewer_cp.getItemAndValues().get(key));


        if(getApp().getAttach_device().size()>0) {
            for (Device device : getApp().getAttach_device()) {
                final Device deviceitem = device;
                if (deviceitem.getGroup_id() == 0x0014) {
                    lyt_fan.setVisibility(View.VISIBLE);
                    fan_id.setVisibility(View.VISIBLE);
                    fan_spd.setVisibility(View.VISIBLE);
                    m_fan.setVisibility(View.VISIBLE);
                    t_fan.setVisibility(View.VISIBLE);
                    fan_id.setTextValue(String.format("%08X",getApp().getAttach_device().get(0).GetDeviceId()));
                    fan_spd.setCur(((Dev_Fan)deviceitem).getRun_speed());
                }
            }
        }
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
                es_cp_data.put(hopper_key[i]+"",hopper_value[i]);
            }
        }


    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        brewer_cp.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = brewer_cp.getItemAndValues().get(key);
                brewer_cp.setSelItem(key, name);
                //// TODO: 2018/4/20  xiugai yingjian peizhi wenjian tongbu device shu ju
                current_device = getApp().getMain_device();
                ((Dev_Mono)current_device).setMax_capability(Integer.parseInt(key,10));
                setDeviceData(current_device);
            }
        });
        if(getApp().getAttach_device().size()>0) {
            for (Device device : getApp().getAttach_device()) {
                final Device deviceitem = device;
                if (deviceitem.getGroup_id()== 0x0014)
                {
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
                            current_device = deviceitem;
                            ((Dev_Fan)current_device).setRun_speed(spd);
                            setDeviceData(current_device);
                        }
                    });
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
        t_brew_motor.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_ONCE,String.format("%08X",getApp().getMain_device().GetDeviceId()),"64"));
                }
            }
        });
        t_brew_valve.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CmdHardwareTest cmdHardwareTest;
                if(isChecked)
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_OPEN_HOT,String.format("%08X",getApp().getMain_device().GetDeviceId()),"64"));
                }
                else
                {
                    cmdHardwareTest =new CmdHardwareTest();
                    getApp().addCmdQueue(cmdHardwareTest.buildHardwareTestCmd(CmdHardwareTest.OPERATOR_OUTLET_CLOSE_HOT,String.format("%08X",getApp().getMain_device().GetDeviceId()),"64"));

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
                AppManager.getAppManager().finishActivity(aty_device_monobrewer.this);
                break;
        }
    }
}
