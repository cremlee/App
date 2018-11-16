package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;


import android.app.Activity;
import android.content.Intent;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverageIngredient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip.IngredientnameTip;
import android.luna.Data.module.IngredientMono;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class MonoFragment extends BaseFragment implements IIngredient<IngredientMono> {
    private BaseActivity aty;
    private SettingItemTextView2 ingredientNameItem;
    private SettingItemDropDown  infusionwaterTypeItem,brewwaterTypeItem;
    private SettingItemSeekBar   pressureItem,grinder1AmountItem,preBrewTimeItem,brewTimeItem;
    private SettingItemSeekBar infusionwaterTempItem,infusionwaterVolumeItem;
    private SettingItemSeekBar brewwaterTempItem,brewwaterVolumeItem;
    private SettingItemSeekBar   airSpeedItem,airTimeItem,bubTimeItem,bubSpeedItem;
    private SettingItemTextView2 totalTimeItem;
    private float totalTime;

    private IngredientMono getIngredient()
    {
        if(aty instanceof aty_ingrendient_maker)
            return  ((aty_ingrendient_maker)aty).getMingredientMono();
        else
            return  ((aty_beverageIngredient_maker)aty).getMingredientMono();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_mono, container, false);
        initmap();
        ingredientNameItem=view.findViewById(R.id.ingredientNameItem);
        infusionwaterTypeItem=view.findViewById(R.id.infusionwaterTypeItem);
        infusionwaterTempItem=view.findViewById(R.id.infusionwaterTempItem);
        infusionwaterVolumeItem=view.findViewById(R.id.infusionwaterVolumeItem);
        brewwaterTypeItem=view.findViewById(R.id.brewwaterTypeItem);
        brewwaterTempItem=view.findViewById(R.id.brewwaterTempItem);
        brewwaterVolumeItem=view.findViewById(R.id.brewwaterVolumeItem);


        pressureItem=view.findViewById(R.id.pressureItem);
        grinder1AmountItem=view.findViewById(R.id.grinder1AmountItem);
        preBrewTimeItem=view.findViewById(R.id.preBrewTimeItem);
        brewTimeItem=view.findViewById(R.id.brewTimeItem);

        airSpeedItem=view.findViewById(R.id.airSpeedItem);
        airTimeItem=view.findViewById(R.id.airTimeItem);
        bubTimeItem=view.findViewById(R.id.bubTimeItem);
        bubSpeedItem=view.findViewById(R.id.bubSpeedItem);


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
    public void InitView(IngredientMono a) {
        if(getIngredient()!=null)
        {
            ingredientNameItem.setTextValue(getIngredient().getName());
            infusionwaterTypeItem.setItemAndValues(map);
            // 设置默认值
            String key = String.valueOf(getIngredient().getInfusionwatertype());
            infusionwaterTypeItem.setSelItem(key, infusionwaterTypeItem.getItemAndValues().get(key));
            infusionwaterVolumeItem.setCur(getIngredient().getInfusionwatervolume());
            infusionwaterTempItem.setCur(getIngredient().getInfusionwaterntc());
            preBrewTimeItem.setCur(getIngredient().getInfusiontime());


            brewwaterTypeItem.setItemAndValues(map);
            // 设置默认值
            key = String.valueOf(getIngredient().getDispensewatertype());
            brewwaterTypeItem.setSelItem(key, brewwaterTypeItem.getItemAndValues().get(key));
            brewwaterVolumeItem.setCur(getIngredient().getDispensewatervolume());
            brewwaterTempItem.setCur(getIngredient().getDispensewaterntc());
            brewTimeItem.setCur(getIngredient().getBrewtime());

            grinder1AmountItem.setProgressMaxTimes(10);
            grinder1AmountItem.setCur2(getIngredient().getPowderamount());
            pressureItem.setCur(getIngredient().getWaterpressure());

            airSpeedItem.setCur(getIngredient().getAirspeed());
            airTimeItem.setCur(getIngredient().getAirruntime());
            bubSpeedItem.setCur(getIngredient().getBubblerspeed());
            bubTimeItem.setCur(getIngredient().getBubblerruntime());

           /*// brewTimeItem.setCur(getIngredient().getBrewtime());

            //totalTime = Float.valueOf((getIngredient().getWaterVolume() / Constant.WATER_VOLUME) + "");
            //totalTimeItem.setTextValue(getString(R.string.sec_float, totalTime));*/

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
        infusionwaterTypeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                NotifyChange();
                String name = infusionwaterTypeItem.getItemAndValues().get(key);
                infusionwaterTypeItem.setSelItem(key, name);
            }
        });
        infusionwaterVolumeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += infusionwaterVolumeItem.getMin();
                    infusionwaterVolumeItem.setCur(volume);
                    NotifyChange();
                }
                totalTime = Float.valueOf((volume / Constant.WATER_VOLUME) + "");
                totalTimeItem.setTextValue(new DecimalFormat("0.0s").format(totalTime));
            }
        });
        infusionwaterTempItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += infusionwaterTempItem.getMin();
                    infusionwaterTempItem.setCur(volume);
                    NotifyChange();
                }
            }
        });

        brewwaterTypeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                NotifyChange();
                String name = brewwaterTypeItem.getItemAndValues().get(key);
                brewwaterTypeItem.setSelItem(key, name);
            }
        });
        brewwaterVolumeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += brewwaterVolumeItem.getMin();
                    brewwaterVolumeItem.setCur(volume);
                    NotifyChange();
                }
                totalTime = Float.valueOf((volume / Constant.WATER_VOLUME) + "");
                totalTimeItem.setTextValue(new DecimalFormat("0.0s").format(totalTime));
            }
        });
        brewwaterTempItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += brewwaterTempItem.getMin();
                    brewwaterTempItem.setCur(volume);
                    NotifyChange();
                }
            }
        });


        grinder1AmountItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += grinder1AmountItem.getMin();
                    grinder1AmountItem.setCur2(volume);
                    NotifyChange();
                }
            }
        });

        pressureItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += pressureItem.getMin();
                    pressureItem.setCur(volume);
                    NotifyChange();
                }
            }
        });
        preBrewTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += preBrewTimeItem.getMin();
                    preBrewTimeItem.setCur(volume);
                    NotifyChange();
                }
            }
        });
        brewTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += brewTimeItem.getMin();
                    brewTimeItem.setCur(volume);
                    NotifyChange();
                }
            }
        });
        airSpeedItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += airSpeedItem.getMin();
                    airSpeedItem.setCur(volume);
                    NotifyChange();
                }
            }
        });
        airTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += airTimeItem.getMin();
                    airTimeItem.setCur(volume);
                    NotifyChange();
                }
            }
        });
    }

    private void initMono()
    {
        getIngredient().setName(ingredientNameItem.getTextValue());
        getIngredient().setInfusionwatertype(Integer.valueOf(infusionwaterTypeItem.getSelKey()));
        getIngredient().setInfusionwatervolume(infusionwaterVolumeItem.getCur());
        getIngredient().setInfusionwaterntc(infusionwaterTempItem.getCur());

        getIngredient().setDispensewatertype(Integer.valueOf(brewwaterTypeItem.getSelKey()));
        getIngredient().setDispensewatervolume(brewwaterVolumeItem.getCur());
        getIngredient().setDispensewaterntc(brewwaterTempItem.getCur());

        getIngredient().setPowderamount(grinder1AmountItem.getCur());
        getIngredient().setWaterpressure(pressureItem.getCur());
        getIngredient().setInfusiontime(preBrewTimeItem.getCur());
        getIngredient().setBrewtime(brewTimeItem.getCur());
        getIngredient().setAirspeed(airSpeedItem.getCur());
        getIngredient().setAirruntime(airTimeItem.getCur());
        getIngredient().setBubblerruntime(bubSpeedItem.getCur());
        getIngredient().setBubblerspeed(bubTimeItem.getCur());
    }
    @Override
    public void save() {
        initMono();
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
        }
    }
}
