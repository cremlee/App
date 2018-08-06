package android.luna.Activity.WelcomeUi;

import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Gallery.aty_theme_gallery;
import android.luna.Activity.CustomerUI.Normal.aty_theme_normal;
import android.luna.Activity.CustomerUI.Shopping.aty_theme_shop;
import android.luna.Activity.CustomerUI.ThreeDCloud.aty_theme_3d;
import android.luna.Utils.DateTime;
import android.luna.ViewUi.pickerview.TimePickerDialog;
import android.luna.ViewUi.pickerview.data.Type;
import android.luna.ViewUi.pickerview.listener.OnDateSetListener;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import java.text.SimpleDateFormat;
import java.util.Date;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/22.
 */

public class aty_wizard_main extends BaseActivity implements View.OnClickListener,OnDateSetListener {

    private ViewFlipper vf_fun;
    private Button btn_pre ,btn_skip,btn_next;
    private static int VIEW_REGION = 0;
    private static int VIEW_TIME = 1;
    private static int VIEW_WIFI = 2;
    private static int VIEW_INFO = 3;
    private static int VIEW_FILTER= 4;
    private static int VIEW_FINISH= 5;
    private View v1;
    private View v2;
    private View v3;
    private View v4;
    private View v5;
    private View v6;
    private ArrayAdapter<CharSequence> regionadapter;
    private ArrayAdapter<CharSequence> filteradapter;
    private ArrayAdapter<CharSequence> hardnessadapter;
    private long offfsetYears = (long)5 * 365 * 1000 * 60 * 60 * 24;
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
        setContentView(R.layout.welcome_page_base);
        vf_fun = findViewById(R.id.vf_fun);
        btn_pre = findViewById(R.id.btn_pre);
        btn_skip = findViewById(R.id.btn_skip);
        btn_next = findViewById(R.id.btn_next);
        InitRegionView();
        InitDatatimeView();
        InitWifiView();
        InitInfoView();
        InitFilterView();
        InitFinishView();
        refreshbtn();
    }

    private Spinner sp_region;
    private void InitRegionView()
    {
        v1 = LayoutInflater.from(this).inflate(R.layout.view_region, null);
        sp_region = v1.findViewById(R.id.sp_region);
        sp_region.setAdapter(regionadapter);
        //regionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vf_fun.addView(v1);

    }

    private TimePickerDialog mDialogAll;
    private Button time_wizard;
    private void InitDatatimeView()
    {
        v2 = LayoutInflater.from(this).inflate(R.layout.view_time, null);
        time_wizard = v2.findViewById(R.id.time_wizard);
        time_wizard.setText("2018-01-01 00:00:00");
        vf_fun.addView(v2);
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(this)
                .setCancelStringId("Cancel")
                .setSureStringId("Sure")
                .setTitleStringId("TimePicker")
                .setYearText("Year")
                .setMonthText("Month")
                .setDayText("Day")
                .setHourText("Hour")
                .setMinuteText("Minute")
                .setCyclic(false)
                .setMinMillseconds(DateTime.parse("2018-01-01 00:00:00","yyyy-MM-dd HH:mm:ss").getMillis())
                .setMaxMillseconds(DateTime.parse("2018-01-01 00:00:00","yyyy-MM-dd HH:mm:ss").getMillis() + offfsetYears)
                .setCurrentMillseconds(DateTime.parse("2018-01-01 00:00:00","yyyy-MM-dd HH:mm:ss").getMillis())
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg))
                .setWheelItemTextSize(24)
                .build();
            time_wizard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mDialogAll.show(getFragmentManager(), "all");
                }
                catch (Exception e){}
            }
        });
    }

    private CheckBox wifi_open;
    private Button   wifi_find;
    private void InitWifiView()
    {
        v3 = LayoutInflater.from(this).inflate(R.layout.view_wifi, null);
        wifi_open = v3.findViewById(R.id.wifi_open);
        wifi_find = v3.findViewById(R.id.wifi_find);
        vf_fun.addView(v3);
        //// TODO: 2018/5/22 chushihua open anjian he find anjian de zhuangtai
    }
    private SettingItemTextView2 stv_name,stv_phone,stv_site,stv_id;
    private void InitInfoView()
    {
        v4 = LayoutInflater.from(this).inflate(R.layout.view_machine, null);
        stv_name = v4.findViewById(R.id.stv_name);
        stv_phone = v4.findViewById(R.id.stv_phone);
        stv_site = v4.findViewById(R.id.stv_site);
        stv_id = v4.findViewById(R.id.stv_id);
        vf_fun.addView(v4);
    }
    private Spinner sp_filter,sp_hardness;
    private void InitFilterView()
    {
        v5 = LayoutInflater.from(this).inflate(R.layout.view_filter, null);
        sp_filter = v5.findViewById(R.id.sp_filter);
        sp_hardness = v5.findViewById(R.id.sp_hardness);
        sp_filter.setAdapter(filteradapter);
        sp_hardness.setAdapter(hardnessadapter);
        vf_fun.addView(v5);
    }
    private void InitFinishView()
    {
        v6 = LayoutInflater.from(this).inflate(R.layout.view_finish, null);
        vf_fun.addView(v6);
    }
    @Override
    public void InitData() {
        super.InitData();
        regionadapter = ArrayAdapter.createFromResource(this, R.array.region, android.R.layout.simple_spinner_dropdown_item);
        filteradapter = ArrayAdapter.createFromResource(this, R.array.filter, android.R.layout.simple_spinner_dropdown_item);
        hardnessadapter = ArrayAdapter.createFromResource(this, R.array.hardness, android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_pre.setOnClickListener(this);
        btn_skip.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }


    private int position =0;
    private void refreshbtn()
    {
        if(position == VIEW_REGION)
        {
            btn_pre.setVisibility(View.INVISIBLE);
            btn_skip.setVisibility(View.INVISIBLE);
        }
        else if(position == VIEW_TIME || position == VIEW_WIFI || position == VIEW_INFO ||position == VIEW_FILTER)
        {
            btn_pre.setVisibility(View.VISIBLE);
            btn_skip.setVisibility(View.VISIBLE);
        }
        else if(position == VIEW_FINISH )
        {
            btn_pre.setVisibility(View.INVISIBLE);
            btn_skip.setVisibility(View.INVISIBLE);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_pre:
                if(position == VIEW_TIME || position == VIEW_WIFI ||position == VIEW_INFO || position == VIEW_FILTER || position ==VIEW_FINISH)
                {
                    position --;
                    vf_fun.showPrevious();
                }
                break;
            case R.id.btn_skip:
                 if(position == VIEW_TIME || position == VIEW_WIFI)
                {
                    position ++;
                    vf_fun.showNext();
                }
                break;
            case R.id.btn_next:
                if(position == VIEW_REGION)
                {
                    position ++;
                    vf_fun.showNext();
                }
                else if(position == VIEW_TIME)
                {
                    position ++;
                    vf_fun.showNext();
                }
                else if(position == VIEW_WIFI)
                {
                    position ++;
                    vf_fun.showNext();
                }
                else if(position ==VIEW_INFO)
                {
                    position ++;
                    vf_fun.showNext();
                }
                else if(position ==VIEW_FILTER)
                {
                    position ++;
                    vf_fun.showNext();
                }
                else if(position ==VIEW_FINISH)
                {
                    startcoffee();
                    AppManager.getAppManager().finishActivity(aty_wizard_main.this);

                }
                break;

        }
        refreshbtn();
    }

    private void startcoffee()
    {

        switch (getApp().get_screenSettings_instance().getThemetype())
        {
            case 1:
                startActivity(new Intent(aty_wizard_main.this, aty_theme_normal.class));
                break;
            case 2:
                startActivity(new Intent(aty_wizard_main.this, aty_theme_gallery.class));
                break;
            case 3:
                startActivity(new Intent(aty_wizard_main.this, aty_theme_3d.class));
                break;
            case 4:
                startActivity(new Intent(aty_wizard_main.this, aty_theme_shop.class));
                break;
        }
    }
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        time_wizard.setText(getDateToString(millseconds));
    }
}
