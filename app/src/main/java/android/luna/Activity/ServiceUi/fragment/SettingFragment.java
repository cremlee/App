package android.luna.Activity.ServiceUi.fragment;


import android.content.Intent;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.aty_canistereditor_main;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.MachineConfigEditor.aty_machineconfig_main;
import android.luna.Activity.ServiceUi.Setting.Payment.aty_payment_main;
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

public class SettingFragment extends Fragment implements View.OnClickListener,IAuthManage{
    private CremApp app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_improve, container, false);
        view.findViewById(R.id.btn_screensetting).setOnClickListener(this);
        view.findViewById(R.id.btn_canistersetting).setOnClickListener(this);
        view.findViewById(R.id.btn_machinesetting).setOnClickListener(this);
        view.findViewById(R.id.btn_payment).setOnClickListener(this);
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
        if(app.getAuth_level() < 3) {
            view.findViewById(R.id.btn_canistersetting).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.btn_machinesetting).setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public void onClick(View view) {
        int id =view.getId();
        Intent intent;
        switch (id)
        {
            case R.id.btn_screensetting:
                intent = new Intent(getActivity(),aty_screensetting_main.class);
                startActivity(intent);
                break;
            case R.id.btn_canistersetting:
                intent = new Intent(getActivity(),aty_canistereditor_main.class);
                startActivity(intent);
                break;
            case R.id.btn_payment:
                intent = new Intent(getActivity(),aty_payment_main.class);
                startActivity(intent);
                break;
            case R.id.btn_machinesetting:
                intent = new Intent(getActivity(),aty_machineconfig_main.class);
                startActivity(intent);
                break;
        }
    }
}
