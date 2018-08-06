package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;


import android.app.Activity;
import android.content.Intent;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverageIngredient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip.IngredientnameTip;
import android.luna.Data.module.IngredientWater;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class WaterFragment extends BaseFragment implements IIngredient<IngredientWater> {
    private BaseActivity aty;
    private SettingItemTextView2 ingredientNameItem;
    private SettingItemDropDown  waterTypeItem;
    private SettingItemSeekBar   waterVolumeItem;
    private SettingItemTextView2 totalTimeItem;
    private float totalTime;

    private IngredientWater getIngredient()
    {
        if(aty instanceof aty_ingrendient_maker)
            return  ((aty_ingrendient_maker)aty).getMingredientWater();
        else
            return  ((aty_beverageIngredient_maker)aty).getMingredientWater();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_water, container, false);
        initmap();
        ingredientNameItem=view.findViewById(R.id.ingredientNameItem);
        waterTypeItem=view.findViewById(R.id.waterTypeItem);
        waterVolumeItem=view.findViewById(R.id.waterVolumeItem);
        totalTimeItem=view.findViewById(R.id.totalTimeItem);
        InitEvent();
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        if(activity instanceof aty_ingrendient_maker)
            aty = (aty_ingrendient_maker) activity;
        else
            aty = (aty_beverageIngredient_maker) activity;
        super.onAttach(activity);
    }
    @Override
    public void onResume() {
        super.onResume();
        InitView(null);
    }
    private Map<String, String> map = new HashMap<>();
    private void initmap()
    {
        String[] strings = getResources().getStringArray(R.array.watertype);
        for (int i = 0; i < strings.length; i++) {
            map.put(String.valueOf(i), strings[i]);
        }

    }
    @Override
    public void InitView(IngredientWater a) {
        if(getIngredient()!=null)
        {
            ingredientNameItem.setTextValue(getIngredient().getName());
            waterTypeItem.setItemAndValues(map);
            // 设置默认值
            String key = String.valueOf(getIngredient().getWaterType());
            waterTypeItem.setSelItem(key, waterTypeItem.getItemAndValues().get(key));
            waterVolumeItem.setCurProgress(getIngredient().getWaterVolume());

            totalTime = Float.valueOf((getIngredient().getWaterVolume() / Constant.WATER_VOLUME) + "");
            totalTimeItem.setTextValue(getString(R.string.sec_float, totalTime));
            waterVolumeItem.setCur(getIngredient().getWaterVolume());
        }
    }
    @Override
    public void InitEvent() {
        ingredientNameItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),IngredientnameTip.class);
                intent.putExtra("argument", getIngredient() != null ? getIngredient().getName() : "water");
                startActivityForResult(intent, Constant.REQ_INPUT);
            }
        });
        waterVolumeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                int volume = progress;
                if (fromUser) {
                    volume += waterVolumeItem.getMin();
                    waterVolumeItem.setCur(volume);
                    NotifyChange();
                }
                totalTime = Float.valueOf((volume / Constant.WATER_VOLUME) + "");
                totalTimeItem.setTextValue(new DecimalFormat("0.0s").format(totalTime));
            }
        });
        waterTypeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                NotifyChange();
                String name = waterTypeItem.getItemAndValues().get(key);
                waterTypeItem.setSelItem(key, name);
            }
        });
    }

    private void initWater()
    {
        getIngredient().setName(ingredientNameItem.getTextValue());
        getIngredient().setWaterType(Integer.valueOf(waterTypeItem.getSelKey()));
        getIngredient().setWaterVolume(waterVolumeItem.getCur());
    }
    @Override
    public void save() {
        initWater();
    }

    @Override
    public void NotifyChange() {
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(3);
        getIngredient().setChanged(true);
    }

    @Override
    public void NotifyNameChange(String name) {
        getIngredient().setChanged(true);
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(99);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == Constant.REQ_INPUT) {
            getIngredient().setName(data.getStringExtra("response"));
            NotifyNameChange("name");
            //ingredientNameItem.setTextValue(data.getStringExtra("response"));
        }
    }
}
