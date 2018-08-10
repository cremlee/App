package android.luna.Activity.ServiceUi.Setting.Clean;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.BrewerCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.DailyCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.FreeCleanFragment;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.WeeklyCleanFragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class aty_clean_main extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private RadioGroup myTabRg;
    private TextView btn_back;
    private DailyCleanFragment tb_daily;
    private WeeklyCleanFragment tb_weekly;
    private BrewerCleanFragment tb_brewer;
    private FreeCleanFragment tb_cleanshow;
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
        super.InitView();
        setContentView(R.layout.aty_clean_editor);
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
            case R.id.rb_daily:
               if(tb_daily==null)
                {
                    tb_daily = new DailyCleanFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_clean,tb_daily).commit();
                break;
            case R.id.rb_weekly:
                if(tb_weekly==null)
                {
                    tb_weekly = new WeeklyCleanFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_clean,tb_weekly).commit();
                break;
            case R.id.rb_brewer:

                /*if(tb_brewer==null)
                {
                    tb_brewer = new BrewerCleanFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_clean,tb_brewer).commit();*/
                if(tb_cleanshow==null)
                {
                    tb_cleanshow = new FreeCleanFragment();
                }
                getFragmentManager().beginTransaction().replace(R.id.fly_clean,tb_cleanshow).commit();
                break;
            case R.id.rb_mixer:
                break;
            case R.id.rb_valve:
                break;
            case R.id.rb_grinder:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_clean_main.this);
                break;
        }
    }
}
