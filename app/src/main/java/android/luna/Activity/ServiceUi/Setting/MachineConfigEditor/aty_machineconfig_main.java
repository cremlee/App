package android.luna.Activity.ServiceUi.Setting.MachineConfigEditor;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.DAO.SystemSettingFactory;
import android.luna.Data.module.System.DisplaySoundSettings;
import android.luna.Data.module.System.SecretSettings;
import android.luna.Data.module.System.SmartSettings;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/13.
 */

public class aty_machineconfig_main extends BaseActivity implements View.OnClickListener{
    private TextView btn_back;
    private SystemSettingFactory systemSettingFactory;
    private SecretSettings secretSettings;
    private SmartSettings smartSettings;
    private DisplaySoundSettings displaySoundSettings;
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
    }

    @Override
    public void InitData() {
        super.InitData();
        systemSettingFactory = new SystemSettingFactory(this,getApp());
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
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
