package android.luna.Activity.ServiceUi.Setting.MachineConfigEditor;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.DAO.SystemSettingFactory;
import android.luna.Data.module.System.DisplaySoundSettings;
import android.luna.Data.module.System.SecretSettings;
import android.luna.Data.module.System.SmartSettings;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/13.
 */

public class aty_machineconfig_main extends BaseActivity implements View.OnClickListener{
    private Button btn_back;
    private SystemSettingFactory systemSettingFactory;
    private SecretSettings secretSettings;
    private SmartSettings smartSettings;
    private DisplaySoundSettings displaySoundSettings;
    private SettingItemCheckBox enableBeepCheck,enableQrscanCheck,enableServicePinCheck,enableUserPinCheck,enableInteECO,enableDaylight;
    private SettingItemTextView2 timeDateItem,qrcode_fact,pin_service,pin_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_machineconfig_main);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        enableBeepCheck = findViewById(R.id.enableBeepCheck);
        enableQrscanCheck = findViewById(R.id.enableQrscanCheck);
        enableServicePinCheck = findViewById(R.id.enableServicePinCheck);
        enableUserPinCheck = findViewById(R.id.enableUserPinCheck);
        enableInteECO = findViewById(R.id.enableInteECO);
        enableDaylight = findViewById(R.id.enableDaylight);
        timeDateItem = findViewById(R.id.timeDateItem);
        qrcode_fact = findViewById(R.id.qrcode_fact);
        pin_service = findViewById(R.id.pin_service);
        pin_operator = findViewById(R.id.pin_operator);
        enableBeepCheck.setChecked(displaySoundSettings.getBeepmode()==1);
        enableQrscanCheck.setChecked(secretSettings.getQrmode()==1);
        enableServicePinCheck.setChecked(secretSettings.getServicemode()==1);
        enableUserPinCheck.setChecked(secretSettings.getUsermode()==1);
        enableInteECO.setChecked(smartSettings.getSmartmode()==1);
        enableDaylight.setChecked(smartSettings.getDaylightmode()==1);
    }

    @Override
    public void InitData() {
        super.InitData();
        systemSettingFactory = new SystemSettingFactory(this,getApp());
        secretSettings = systemSettingFactory.getSecretSettingDao().query();
        displaySoundSettings = systemSettingFactory.getDisplaySoundSetting().query();
        smartSettings = systemSettingFactory.getSmartSettingDao().query();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        enableBeepCheck.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                displaySoundSettings.setBeepmode(isChecked?1:0);
                systemSettingFactory.getDisplaySoundSetting().modify(displaySoundSettings);
            }
        });
        enableQrscanCheck.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                secretSettings.setQrmode(isChecked?1:0);
                systemSettingFactory.getSecretSettingDao().modify(secretSettings);
            }
        });
        enableServicePinCheck.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                secretSettings.setServicemode(isChecked?1:0);
                systemSettingFactory.getSecretSettingDao().modify(secretSettings);
            }
        });
        enableUserPinCheck.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                secretSettings.setUsermode(isChecked?1:0);
                systemSettingFactory.getSecretSettingDao().modify(secretSettings);
            }
        });
        enableInteECO.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                smartSettings.setSmartmode(isChecked?1:0);
                systemSettingFactory.getSmartSettingDao().modify(smartSettings);
            }
        });
        enableDaylight.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                smartSettings.setDaylightmode(isChecked?1:0);
                systemSettingFactory.getSmartSettingDao().modify(smartSettings);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_machineconfig_main.this);
                break;
        }
    }
}
