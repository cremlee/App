package android.luna.Activity.ServiceUi.Setting.DrinkEditor.NameEditor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.BrewerCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.DailyCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.WeeklyCleanFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.NameEditor.fragment.NameExportFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.NameEditor.fragment.NameImportFragment;
import android.luna.BlueCom.BlueActionDefine;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class aty_name_main extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private RadioGroup myTabRg;
    private MaterialDialog progressDialog;
    private TextView btn_back;
    private NameExportFragment tb_export;
    private NameImportFragment tb_import;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BlueActionDefine.ACTION_USB_REMOVE);
        filter.addAction(BlueActionDefine.ACTION_USB_INSERT);
        registerReceiver(receiver, filter);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BlueActionDefine.ACTION_USB_INSERT.equals(action)) {
                String path = getApp().getUsbpath();
                if (tb_export!=null && tb_export.isAdded() && tb_export.isVisible()) {
                    tb_export.enableUSB(path);
                }
                else if(tb_import!=null && tb_import.isAdded() && tb_import.isVisible())
                {
                    tb_import.enableUSB(path);
                }
            } else if (BlueActionDefine.ACTION_USB_REMOVE.equals(action)) {
                if (tb_export!=null && tb_export.isAdded() && tb_export.isVisible()) {
                    tb_export.disableUSB();
                }
                else if(tb_import!=null &&tb_import.isAdded() && tb_import.isVisible())
                {
                    tb_import.disableUSB();
                }
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
        super.InitView();
        setContentView(R.layout.aty_name_editor);
        myTabRg = findViewById(R.id.myTabRg);
        myTabRg.setOnCheckedChangeListener(this);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void InitData() {
        super.InitData();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId)
        {
            case R.id.rb_export:
               if(tb_export==null)
                {
                    tb_export = new NameExportFragment();
                }
                tb_export.setUsbReady(false);
                getFragmentManager().beginTransaction().replace(R.id.fly_clean,tb_export).commit();
                break;
            case R.id.rb_import:
                if(tb_import==null)
                {
                    tb_import = new NameImportFragment();
                }
                tb_import.setUsbReady(false);
                getFragmentManager().beginTransaction().replace(R.id.fly_clean,tb_import).commit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_name_main.this);
                break;
        }
    }
}
