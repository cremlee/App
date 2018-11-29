package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;


import android.app.Activity;
import android.content.Intent;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverageIngredient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip.IngredientnameTip;
import android.luna.Data.module.IngredientMono;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class MonoStepFragment extends BaseFragment implements IIngredient<IngredientMono>, View.OnClickListener {
    private BaseActivity aty;
    private SettingItemTextView2 ingredientNameItem;
    private SettingItemDropDown  grinder1TypeItem,washtype;
    private SettingItemSeekBar   grinder1AmountItem,powderwait;
    private SettingItemCheckBox  washenable;
    private SettingItemSeekBar   washcount,washvolume,washtemp;
    private SettingItemSeekBar   emptytime,emptyspeed;
    private SettingItemTextView2 monostep;

    private SettingItemTextView2 totalTimeItem;
    private float totalTime;

    private IngredientMono getIngredient()
    {
        if(aty instanceof aty_ingrendient_maker)
            return  ((aty_ingrendient_maker)aty).getMingredientMono();
        else
            return  ((aty_beverageIngredient_maker)aty).getMingredientMono();
    }
    private List<PowderItem> getpowderItems()
    {
        if(aty instanceof aty_ingrendient_maker)
            return  ((aty_ingrendient_maker)aty).getPowderItems();
        else
            return  ((aty_beverageIngredient_maker)aty).getPowderItems();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_mono_ad, container, false);
        initmap();
        ingredientNameItem=view.findViewById(R.id.ingredientNameItem);
        grinder1TypeItem = view.findViewById(R.id.grinder1TypeItem);
        grinder1AmountItem=view.findViewById(R.id.grinder1AmountItem);
        powderwait =view.findViewById(R.id.powderwait);

        washenable=view.findViewById(R.id.washenable);
        washcount=view.findViewById(R.id.washcount);
        washvolume=view.findViewById(R.id.washvolume);
        washtype=view.findViewById(R.id.washtype);
        washtemp=view.findViewById(R.id.washtemp);

        emptytime=view.findViewById(R.id.emptytime);
        emptyspeed=view.findViewById(R.id.emptyspeed);

        monostep=view.findViewById(R.id.monostep);

        totalTimeItem=view.findViewById(R.id.totalTimeItem);

        for(PowderItem item:getpowderItems())
        {
            if( item.getSelected() ==1)
                map1.put(item.getPid()+"",item.getName());
        }
        grinder1TypeItem.setItemAndValues(map1);
        grinder1TypeItem.refreshData(0);

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
    private Map<String, String> map1 = new HashMap<>();
    private void initmap()
    {
        String[] strings = getResources().getStringArray(R.array.filter_water_value);
        int keys[] = getResources().getIntArray(R.array.filter_water_key);
        for (int i = 0; i < strings.length; i++) {
            map.put(String.valueOf(i), strings[i]);
        }

    }
    @Override
    public void InitView(IngredientMono a) {
        if(getIngredient()!=null)
        {
            ingredientNameItem.setTextValue(getIngredient().getName());
            // 设置默认值
            String key = String.valueOf(getIngredient().getPowdertype());
            grinder1TypeItem.setSelItem(key, grinder1TypeItem.getItemAndValues().get(key));
            grinder1AmountItem.setProgressMaxTimes(10);
            grinder1AmountItem.setCur2(getIngredient().getPowderamount());
            powderwait.setCur(getIngredient().getPowderwait());

            washenable.setChecked(getIngredient().getWashenable()==1);
            if(washenable.isChecked())
                showwash();
            else
                hidewash();
            washcount.setCur(getIngredient().getWashcount());
            washtype.setItemAndValues(map);
            washtype.refreshData(0);
            key = String.valueOf(getIngredient().getWashtype());
            washtype.setSelItem(key, washtype.getItemAndValues().get(key));
            washvolume.setCur(getIngredient().getWashvolume());
            washtemp.setCur(getIngredient().getWashtemp());

            emptytime.setCur(getIngredient().getEmptytime());
            emptyspeed.setCur(getIngredient().getEmptyspeed());



           /*// brewTimeItem.setCur(getIngredient().getBrewtime());

            //totalTime = Float.valueOf((getIngredient().getWaterVolume() / Constant.WATER_VOLUME) + "");
            //totalTimeItem.setTextValue(getString(R.string.sec_float, totalTime));*/

        }
    }

    private void showwash()
    {
        washcount.setVisibility(View.VISIBLE);
        washvolume.setVisibility(View.VISIBLE);
        washtype.setVisibility(View.VISIBLE);
        washtemp.setVisibility(View.VISIBLE);
    }
    private void hidewash()
    {
        washcount.setVisibility(View.GONE);
        washvolume.setVisibility(View.GONE);
        washtype.setVisibility(View.GONE);
        washtemp.setVisibility(View.GONE);
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

        grinder1TypeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                NotifyChange();
                String name = grinder1TypeItem.getItemAndValues().get(key);
                grinder1TypeItem.setSelItem(key, name);
            }
        });

        powderwait.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += powderwait.getMin();
                    powderwait.setCur(volume);
                    NotifyChange();
                }
            }
        });

        washenable.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    showwash();
                }else
                {
                    hidewash();
                }
            }
        });
        washcount.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += washcount.getMin();
                    washcount.setCur(volume);
                    NotifyChange();
                }
            }
        });
        washvolume.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += washvolume.getMin();
                    washvolume.setCur(volume);
                    NotifyChange();
                }
            }
        });
        washtype.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                NotifyChange();
                String name = washtype.getItemAndValues().get(key);
                washtype.setSelItem(key, name);
            }
        });
        washtemp.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += washtemp.getMin();
                    washtemp.setCur(volume);
                    NotifyChange();
                }
            }
        });
        emptytime.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += emptytime.getMin();
                    emptytime.setCur(volume);
                    NotifyChange();
                }
                totalTime = Float.valueOf((volume / Constant.WATER_VOLUME) + "");
                totalTimeItem.setTextValue(new DecimalFormat("0.0s").format(totalTime));
            }
        });
        emptyspeed.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                    volume += emptyspeed.getMin();
                    emptyspeed.setCur(volume);
                    NotifyChange();
                }
            }
        });

        monostep.setOnClickListener(this);
    }

    private void initMono()
    {
        getIngredient().setName(ingredientNameItem.getTextValue());
        getIngredient().setPowdertype(Integer.valueOf(grinder1TypeItem.getSelKey()));
        getIngredient().setPowderamount(grinder1AmountItem.getCur());
        getIngredient().setPowderwait(powderwait.getCur());
        getIngredient().setWashenable(washenable.isChecked()?1:0);
        getIngredient().setWashcount(washcount.getCur());
        getIngredient().setWashvolume(washvolume.getCur());
        getIngredient().setWashtype(Integer.valueOf(washtype.getSelKey()));
        getIngredient().setWashtemp(washtemp.getCur());
        getIngredient().setEmptytime(emptytime.getCur());
        getIngredient().setEmptyspeed(emptyspeed.getCur());
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.monostep:
                // TODO: 2018/11/27 show the step for mono save
                initMono();
                Intent intent =new Intent(getActivity(), aty_mono_step.class);
                intent.putExtra("ingredientpid",getIngredient().getPid());
                startActivity(intent);
                break;
        }
    }
}
