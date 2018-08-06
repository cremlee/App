package android.luna.Activity.UpdateUi;


import android.app.Fragment;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.fragment.CleanFragment;
import android.luna.Activity.ServiceUi.fragment.DrinkFragment;
import android.luna.Activity.ServiceUi.fragment.InfoFragment;
import android.luna.Activity.ServiceUi.fragment.SettingFragment;
import android.luna.Activity.ServiceUi.fragment.TestFragment;
import android.luna.Activity.UpdateUi.fragment.ExportFragment;
import android.luna.Activity.UpdateUi.fragment.ImportFragment;
import android.luna.Activity.UpdateUi.fragment.UpdateFragment;
import android.luna.Data.module.LogRecord;
import android.luna.ViewUi.bottombar.BottomBar;
import android.luna.ViewUi.bottombar.OnTabSelectListener;
import android.os.Bundle;
import android.support.annotation.IdRes;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class aty_update_main extends BaseActivity {
    private BottomBar bottomBar;
    private Fragment tab_update;
    private Fragment tab_export;
    private Fragment tab_import;
    private CremApp app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CremApp)getApplication();
        setContentView(R.layout.aty_update_main);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_update)
                {
                    if(tab_update ==null)
                    {
                        tab_update = new UpdateFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.updateflyt,tab_update).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_update",null));
                }
                else if(tabId == R.id.tab_import)
                {
                    if(tab_import ==null)
                    {
                        tab_import = new ImportFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.updateflyt,tab_import).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_test",null));
                }
                else if(tabId == R.id.tab_export)
                {
                    if(tab_export ==null)
                    {
                        tab_export = new ExportFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.updateflyt,tab_export).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_log",null));
                }
                else if(tabId == R.id.tab_home) {
                    AppManager.getAppManager().finishActivity(aty_update_main.this);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
