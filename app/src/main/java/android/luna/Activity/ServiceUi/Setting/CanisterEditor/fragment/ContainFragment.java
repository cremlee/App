package android.luna.Activity.ServiceUi.Setting.CanisterEditor.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.adapter.ContainAdapter;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.aty_canistereditor_main;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverageIngredient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Data.module.Powder.ContainItem;
import android.luna.Data.module.Powder.PowderItem;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class ContainFragment extends Fragment {
    private GridView gv_contain;
    private BaseActivity aty;
    private List<ContainItem> _data = new ArrayList<>(14);
    private ContainAdapter containAdapter;
    private PowderItem getPowderItem()
    {
        return ((aty_canistereditor_main)aty).get_powderItem();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_powder_contain, container, false);
        gv_contain = view.findViewById(R.id.gv_contain);
        InitData();
        gv_contain.setAdapter(containAdapter);
        return view;
    }


    public void RefreshContainUi()
    {
        _data = getPowderItem().getallItem();
        containAdapter.set_data(_data);
        containAdapter.notifyDataSetChanged();
    }

    private void InitData()
    {
        _data = getPowderItem().getallItem();
        containAdapter = new ContainAdapter(getActivity(),_data);
        containAdapter.setOnContainStateChanged(new ContainAdapter.OnContainStateChanged() {
            @Override
            public void statechanged(int position, int statecode) {
                _data.get(position).selectcode =statecode;
                getPowderItem().setContainMask(_data);
                ((aty_canistereditor_main)aty).getPowderFactory().getPowerItemDao().update(getPowderItem());
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        if(activity instanceof aty_canistereditor_main)
            aty = (aty_canistereditor_main) activity;

        super.onAttach(activity);
    }
}
