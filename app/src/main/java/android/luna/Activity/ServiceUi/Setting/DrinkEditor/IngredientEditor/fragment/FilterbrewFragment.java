package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;


import android.app.Activity;
import android.content.Intent;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverageIngredient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;

import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip.IngredientnameTip;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.ViewUi.Tip.TipBaseEditText;
import android.luna.ViewUi.widget.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.TreeMap;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class FilterbrewFragment extends BaseFragment implements IIngredient<IngredientFilterBrew>,View.OnClickListener{
    private SettingItemTextView2 ingredientNameItem;
    private SettingItemSeekBar  waterVolumeItem;
    private SettingItemDropDown grinder1TypeItem;
    private SettingItemSeekBar  grinder1AmountItem; // TODO: 最大值是20，条最大是200.
    private SettingItemDropDown grinder2TypeItem;
    private SettingItemSeekBar  grinder2AmountItem; // TODO: 最大值是20，条最大是200.
    private SettingItemSeekBar  preBrewTimeItem; // TODO: 最大值是20，条最大是200.
    private SettingItemSeekBar  extractionTimeItem; // TODO: 最大值是20，条最大是200.
    private SettingItemSeekBar  decompressTimeItem; // TODO: 最大值是20，条最大是200.
    private SettingItemSeekBar  openDelayTimeItem; // TODO: 最大值是20，条最大是200.
    private SettingItemSeekBar  preBrewPositionItem;
    private SettingItemSeekBar  decompressPositionItem;
    private SettingItemTextView2 totalTimeItem;
    private int totalTime;
    //private IngredientFilterBrew mingredientFilterBrew=null;
    private BaseActivity aty;

    private IngredientFilterBrew getIngredientFilterBrew()
    {
        if(aty instanceof aty_ingrendient_maker)
            return  ((aty_ingrendient_maker)aty).getMingredientFilterBrew();
        else
            return  ((aty_beverageIngredient_maker)aty).getMingredientFilterBrew();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_filterbrew, container, false);
        ingredientNameItem   = view.findViewById(R.id.ingredientNameItem);
        waterVolumeItem      = view.findViewById(R.id.waterVolumeItem);
        grinder1TypeItem     = view.findViewById(R.id.grinder1TypeItem);
        grinder1AmountItem   = view.findViewById(R.id.grinder1AmountItem);
        grinder2TypeItem     = view.findViewById(R.id.grinder2TypeItem);
        grinder2AmountItem   = view.findViewById(R.id.grinder2AmountItem);
        preBrewTimeItem      = view.findViewById(R.id.preBrewTimeItem);
        extractionTimeItem   = view.findViewById(R.id.extractionTimeItem);
        decompressTimeItem   = view.findViewById(R.id.decompressTimeItem);
        openDelayTimeItem   = view.findViewById(R.id.emptyTimeItem);
        preBrewPositionItem   = view.findViewById(R.id.aerationItem);
        decompressPositionItem   = view.findViewById(R.id.pressItem);
        totalTimeItem        = view.findViewById(R.id.totalTimeItem);
        InitEvent();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void save()
    {
        initFilterBrew();
    }

    @Override
    public void NotifyChange() {
        getIngredientFilterBrew().setChanged(true);
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(1);
    }

    @Override
    public void NotifyNameChange(String name) {
        getIngredientFilterBrew().setChanged(true);
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(99);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initFilterBrew() {
        String strIngredientName = ingredientNameItem.getTextValue();
        if (strIngredientName == null || "".equals(strIngredientName)) {
            return;
        }
        int waterVolume = waterVolumeItem.getCur();
        String grinder1Type = grinder1TypeItem.getSelKey();
        float grinder1Amount = grinder1AmountItem.getCur();
        String grinder2Type = grinder2TypeItem.getSelKey();
        float grinder2Amount = grinder2AmountItem.getCur();

        int preBrewTime = preBrewTimeItem.getCur();
        int extractionTime = extractionTimeItem.getCur();
        int decompressTime = decompressTimeItem.getCur();
        int openDelayTime = openDelayTimeItem.getCur();
        int preBrewPosition = preBrewPositionItem.getCur();
        int decompressPosition = decompressPositionItem.getCur();



        getIngredientFilterBrew().setName(strIngredientName);
        getIngredientFilterBrew().setPhaseNumber(5);// 固定为5
        getIngredientFilterBrew().setWaterVolume(waterVolume);
        getIngredientFilterBrew().setGrinder1Type(Integer.valueOf(grinder1Type));
        // filterBrew.setGrinder1Amount(grinder1Amount * 10);
        getIngredientFilterBrew().setGrinder1Amount(grinder1Amount);
        getIngredientFilterBrew().setGrinder2Type(Integer.valueOf(grinder2Type));
        // filterBrew.setGrinder2Amount(grinder2Amount * 10); // 高位，小数点前，低位，小数点后
        getIngredientFilterBrew().setGrinder2Amount(grinder2Amount); // 高位，小数点前，低位，小数点后
        getIngredientFilterBrew().setTmPre13(preBrewTime);

        getIngredientFilterBrew().setExtractionTime(extractionTime);
        getIngredientFilterBrew().setDecompressTime(decompressTime);
        getIngredientFilterBrew().setOpenDelayTime(openDelayTime);
        getIngredientFilterBrew().setPreBrewPosition(preBrewPosition);
        getIngredientFilterBrew().setDecompressPosition(decompressPosition);

        totalTime = (preBrewTime + extractionTime + openDelayTime + decompressTime) * 100 + Constant.BREW_RUNNING_TIME;
        getIngredientFilterBrew().setTotalTime((int) totalTime);
    }

    public void InitEvent()
    {
        ingredientNameItem.setOnClickListener(this);
        waterVolumeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    progress += waterVolumeItem.getMin();
                    waterVolumeItem.setCur(progress);
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
                if (fromUser) {
                    progress += grinder1AmountItem.getMin();
                    grinder1AmountItem.setCur2(progress);
                    NotifyChange();
                }
            }
        });
        preBrewPositionItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    progress += preBrewPositionItem.getMin();
                    preBrewPositionItem.setCur(progress);
                    NotifyChange();
                }
            }
        });
        decompressPositionItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    progress += decompressPositionItem.getMin();
                    decompressPositionItem.setCur(progress);
                    NotifyChange();
                }
            }
        });
        //shijian
        preBrewTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time1 = extractionTimeItem.getSeekbar().getProgress() + extractionTimeItem.getMin();
                int time2 = decompressTimeItem.getSeekbar().getProgress() + decompressTimeItem.getMin();
                int time3 = openDelayTimeItem.getSeekbar().getProgress() + openDelayTimeItem.getMin();
                int time = progress;
                if (fromUser) {
                    time += preBrewTimeItem.getMin();
                    preBrewTimeItem.setCur2(time);
                    NotifyChange();
                }
                totalTime = (time + time1 + time2 + time3) * 100 + Constant.BREW_RUNNING_TIME;
                totalTimeItem.setTextValue(totalTime / 1000.0f + "s");
            }
        });
        extractionTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time1 = preBrewTimeItem.getSeekbar().getProgress() + preBrewTimeItem.getMin();
                int time2 = decompressTimeItem.getSeekbar().getProgress() + decompressTimeItem.getMin();
                int time3 = openDelayTimeItem.getSeekbar().getProgress() + openDelayTimeItem.getMin();
                int time = progress;
                if (fromUser) {
                    time += extractionTimeItem.getMin();
                    extractionTimeItem.setCur2(time);
                    NotifyChange();
                }
                totalTime = (time + time1 + time2 + time3) * 100 + Constant.BREW_RUNNING_TIME;
                totalTimeItem.setTextValue(totalTime / 1000.0f + "s");
            }
        });
        decompressTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time1 = preBrewTimeItem.getSeekbar().getProgress() + preBrewTimeItem.getMin();
                int time2 = extractionTimeItem.getSeekbar().getProgress() + extractionTimeItem.getMin();
                int time3 = openDelayTimeItem.getSeekbar().getProgress() + openDelayTimeItem.getMin();
                int time = progress;
                if (fromUser) {
                    time += decompressTimeItem.getMin();
                    decompressTimeItem.setCur2(time);
                    NotifyChange();
                }
                totalTime = (time + time1 + time2 + time3) * 100 + Constant.BREW_RUNNING_TIME;
                totalTimeItem.setTextValue(totalTime / 1000.0f + "s");
            }
        });
        openDelayTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time1 = preBrewTimeItem.getSeekbar().getProgress() + preBrewTimeItem.getMin();
                int time2 = decompressTimeItem.getSeekbar().getProgress() + decompressTimeItem.getMin();
                int time3 = extractionTimeItem.getSeekbar().getProgress() + extractionTimeItem.getMin();
                int time = progress;
                if (fromUser) {
                    time += openDelayTimeItem.getMin();
                    openDelayTimeItem.setCur2(time);
                    NotifyChange();
                }
                totalTime = (time + time1 + time2 + time3) * 100 + Constant.BREW_RUNNING_TIME;
                totalTimeItem.setTextValue(totalTime / 1000.0f + "s");
            }
        });
    }

    @Override
    public void InitView(IngredientFilterBrew filter)
    {
        //// TODO: 2018/2/9 shuaxin peizhi jiemian

        //mingredientFilterBrew =filter;
        if(getIngredientFilterBrew()!=null)
        {
            Map<String, String> grinderType = new TreeMap<String, String>();
            grinderType.put("0", getString(R.string.OTHER));
            grinderType.put("129", getString(R.string.Canister_bean));
            grinderType.put("130", getString(R.string.Canister_pre));

            grinder1TypeItem.setItemAndValues(grinderType);
            grinder2TypeItem.setItemAndValues(grinderType);

            grinder1TypeItem.setSelItem("0", grinder1TypeItem.getItemAndValues().get("0"));
            grinder1TypeItem.refreshData(0);

            grinder2TypeItem.setSelItem("0", grinder2TypeItem.getItemAndValues().get("0"));
            grinder2TypeItem.refreshData(0);

            float extractionTime = getIngredientFilterBrew().getExtractionTime();
            float decompressTime = getIngredientFilterBrew().getDecompressTime();
            float openDelayTime = getIngredientFilterBrew().getOpenDelayTime();
            totalTime = (int) (Constant.BREW_RUNNING_TIME + (extractionTime + decompressTime + openDelayTime) * 100);
            ingredientNameItem.setTextValue(getIngredientFilterBrew().getName());
            waterVolumeItem.setCur(getIngredientFilterBrew().getWaterVolume());
            grinder1AmountItem.setProgressMaxTimes(10);
            grinder1AmountItem.setCur2(getIngredientFilterBrew().getGrinder1Amount());
            preBrewPositionItem.setCur(getIngredientFilterBrew().getPreBrewPosition());
            decompressPositionItem.setCur(getIngredientFilterBrew().getDecompressPosition());
            preBrewTimeItem.setProgressMaxTimes(10);
            preBrewTimeItem.setCur2(getIngredientFilterBrew().getTmPre13());
            extractionTimeItem.setProgressMaxTimes(10);
            extractionTimeItem.setCur2(getIngredientFilterBrew().getExtractionTime());
            decompressTimeItem.setProgressMaxTimes(10);
            decompressTimeItem.setCur2(getIngredientFilterBrew().getDecompressTime());
            openDelayTimeItem.setProgressMaxTimes(10);
            openDelayTimeItem.setCur2(getIngredientFilterBrew().getOpenDelayTime());
            totalTimeItem.setTextValue(new DecimalFormat("0.0s").format(totalTime / 1000.0f));


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ingredientNameItem:
                Intent intent = new Intent(getActivity(),IngredientnameTip.class);
                intent.putExtra("argument", getIngredientFilterBrew() != null ? getIngredientFilterBrew().getName() : "Coffee");
                startActivityForResult(intent, Constant.REQ_INPUT);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == Constant.REQ_INPUT) {
            getIngredientFilterBrew().setName(data.getStringExtra("response"));
            NotifyNameChange("name");
            ingredientNameItem.setTextValue(data.getStringExtra("response"));
        }
    }
}
