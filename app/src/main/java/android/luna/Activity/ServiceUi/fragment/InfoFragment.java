package android.luna.Activity.ServiceUi.fragment;


import android.graphics.drawable.Drawable;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.module.LogRecord;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.google.zxing.client.result.VINParsedResult;

import java.lang.reflect.Field;

import evo.luna.android.R;



/**
 * Created by Lee.li on 2018/1/29.
 */

public class InfoFragment extends Fragment implements IAuthManage{
    private Fragment tb_st;
    private Fragment tb_log;
    private Fragment tb_help;
    private Fragment tb_about;
    private CounterFragment tb_counter;
    //CounterFragment
    private RadioGroup myTabRg;
    private CremApp app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_machineinfo, container, false);
        InitView(view);
        AuthManage(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CremApp)getActivity().getApplication();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            Field f = Fragment.class.getDeclaredField("mChildFragmentManager");
            f.setAccessible(true);
            f.set(this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void  setSelect(int index)
    {
        if(index ==0) {
            if (tb_st == null) {
                tb_st = new StFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.flt_st, tb_st).commit();
            app.SetLog(new LogRecord(LogRecord.COLOR_NOTE, "onTabSelected:tb_st", null));
        }
        else if(index ==2) {
            if (tb_log == null) {
                tb_log = new LogFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.flt_st, tb_log).commit();
            app.SetLog(new LogRecord(LogRecord.COLOR_NOTE, "onTabSelected:tb_log", null));
        }
        else if(index ==1)
        {
            if (tb_counter == null) {
                tb_counter = new CounterFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.flt_st, tb_counter).commit();
            app.SetLog(new LogRecord(LogRecord.COLOR_NOTE, "onTabSelected:tb_log", null));
        }
        else if(index ==3)
        {
            if (tb_help == null) {
                tb_help = new HelpFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.flt_st, tb_help).commit();
            app.SetLog(new LogRecord(LogRecord.COLOR_NOTE, "onTabSelected:tb_log", null));
        }
        else if(index ==4)
        {
            if (tb_about == null) {
                tb_about = new AboutFragment();
            }
            getChildFragmentManager().beginTransaction().replace(R.id.flt_st, tb_about).commit();
            app.SetLog(new LogRecord(LogRecord.COLOR_NOTE, "onTabSelected:tb_log", null));
        }

    }
    private  void InitView(View view)
    {


        Drawable drawable1 = getResources().getDrawable(R.drawable.ics_log);
        drawable1.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        RadioButton tmp = view.findViewById(R.id.rblog);
        tmp.setCompoundDrawables(drawable1, null, null, null);

        tmp = view.findViewById(R.id.rbStatus);
        drawable1 = getResources().getDrawable(R.drawable.ic_st);
        drawable1.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tmp.setCompoundDrawables(drawable1, null, null, null);

        tmp = view.findViewById(R.id.rbcounter);
        drawable1 = getResources().getDrawable(R.drawable.ics_counters);
        drawable1.setBounds(0, 0, 40, 40);
        tmp.setCompoundDrawables(drawable1, null, null, null);

        tmp = view.findViewById(R.id.rbhelp);
        drawable1 = getResources().getDrawable(R.drawable.ic_help);
        drawable1.setBounds(0, 0, 40, 40);
        tmp.setCompoundDrawables(drawable1, null, null, null);

        tmp = view.findViewById(R.id.rbabout);
        drawable1 = getResources().getDrawable(R.drawable.ics_instruction);
        drawable1.setBounds(0, 0, 40, 40);
        tmp.setCompoundDrawables(drawable1, null, null, null);



        myTabRg = view.findViewById(R.id.myTabRg);
        myTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i)
                {
                    case R.id.rbStatus:
                        setSelect(0);
                        break;
                    case R.id.rbcounter:
                        setSelect(1);
                        break;
                    case R.id.rblog:
                        setSelect(2);
                        break;
                    case R.id.rbhelp:
                        setSelect(3);
                        break;
                    case R.id.rbabout:
                        setSelect(4);
                        break;
                }
            }
        });
        setSelect(0);
    }

    @Override
    public void AuthManage(View view) {
        if(app.getAuth_level() == Constant.AUTH_FIRSTLINE)
        {
            view.findViewById(R.id.rblog).setVisibility(View.GONE);
            view.findViewById(R.id.rbcounter).setVisibility(View.GONE);
        }else
        if(app.getAuth_level() == Constant.AUTH_SECONDLINE)
        {
            view.findViewById(R.id.rblog).setVisibility(View.GONE);
        }
    }
}
