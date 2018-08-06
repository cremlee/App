package android.luna.Activity.ServiceUi.fragment;


import android.content.Intent;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverage_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.GroupEditor.aty_group_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.NameEditor.aty_name_main;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class DrinkFragment extends Fragment implements View.OnClickListener,IAuthManage{
    private Button edt_ingredient,edt_beverage,edt_group,namebtn;
    private CremApp app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        edt_ingredient = view.findViewById(R.id.edt_ingredient);
        edt_beverage   = view.findViewById(R.id.edt_beverage);
        edt_group = view.findViewById(R.id.edt_group);
        namebtn = view.findViewById(R.id.namebtn);
        edt_ingredient.setOnClickListener(this);
        edt_beverage.setOnClickListener(this);
        edt_group.setOnClickListener(this);
        namebtn.setOnClickListener(this);
        AuthManage(view);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CremApp)getActivity().getApplication();

    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.namebtn:
                intent = new Intent(getActivity(),aty_name_main.class);
                startActivity(intent);
                break;
            case R.id.edt_ingredient:
                 intent = new Intent(getActivity(),aty_ingrendient_maker.class);
                 startActivity(intent);
                break;
            case R.id.edt_beverage:
                 intent = new Intent(getActivity(),aty_beverage_maker.class);
                 startActivity(intent);
                break;
            case R.id.edt_group:
                intent = new Intent(getActivity(),aty_group_maker.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void AuthManage(View view) {
        if(app.getAuth_level() < 3) {
            edt_ingredient.setVisibility(View.INVISIBLE);
            edt_group.setVisibility(View.INVISIBLE);
        }
    }
}
