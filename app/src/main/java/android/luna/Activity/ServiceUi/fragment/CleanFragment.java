package android.luna.Activity.ServiceUi.fragment;


import android.content.Intent;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.Clean.aty_clean_main;
import android.luna.Activity.ServiceUi.Setting.Schedule.aty_schedule_clean_main;
import android.luna.Activity.ServiceUi.Setting.ScreenEditor.aty_screensetting_main;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class CleanFragment extends Fragment implements IAuthManage,View.OnClickListener{
    private CremApp app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean, container, false);
        view.findViewById(R.id.btn_schedule).setOnClickListener(this);
        view.findViewById(R.id.btn_clean).setOnClickListener(this);
        AuthManage(view);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CremApp)getActivity().getApplication();

    }
    @Override
    public void AuthManage(View view) {
         if(app.getAuth_level()== Constant.AUTH_FIRSTLINE)
         {
             view.findViewById(R.id.btn_schedule).setVisibility(View.INVISIBLE);
         }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.btn_schedule:
                intent = new Intent(getActivity(),aty_schedule_clean_main.class);
                startActivity(intent);
                break;
            case R.id.btn_clean:
                intent = new Intent(getActivity(),aty_clean_main.class);
                startActivity(intent);
                break;
        }
    }
}
