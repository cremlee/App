package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverageIngredient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip.IngredientnameTip;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.ViewUi.SeekBar.SeekBarPressure;
import android.luna.ViewUi.widget.*;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class InstantFragment extends BaseFragment implements IIngredient<IngredientInstant> {
    private SettingItemTextView2 ingredientNameItem;
    private SettingItemSeekBar   whipperSpeedItem;
    private SettingItemSeekBar   waterVolumeItem; // value/15
    private SettingItemDropDown  package1TypeItem;
    private SettingItemSeekBar   package1AmountItem; // TODO:条最大值是200，显示是20
    private SettingItemDropDown  package2TypeItem;
    private SettingItemSeekBar   package2AmountItem; // TODO:条最大值是200，显示是20

    private SettingItemSeekBar   preFlushVolumeItem; // value/App.WATER_VOLUME
    private SettingItemSeekBar   preflushPauseTimeItem;
    private SettingItemSeekBar   afterFlushVolumeItem;// value/App.WATER_VOLUME
    private SettingItemSeekBar   postFlushPauseTimeItem;
    private SettingItemDropDown  waterTypeItem;
    private SettingItemTextView2 totalTimeItem;
    private SeekBarPressure      instantadvanceItem;
    private BaseActivity aty;
    private Map<String, String> map = new HashMap<>();

    private List<PowderItem> getpowderItems()
    {
        if(aty instanceof aty_ingrendient_maker)
            return  ((aty_ingrendient_maker)aty).getPowderItems();
        else
            return  ((aty_beverageIngredient_maker)aty).getPowderItems();
    }



    private IngredientInstant getIngredient()
    {
        if(aty instanceof aty_ingrendient_maker)
            return  ((aty_ingrendient_maker)aty).getMingredientInstant();
        else
            return  ((aty_beverageIngredient_maker)aty).getMingredientInstant();


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ingredient_instant, container, false);
        ingredientNameItem=view.findViewById(R.id.ingredientNameItem);
        whipperSpeedItem=view.findViewById(R.id.whipperSpeedItem);
        waterVolumeItem=view.findViewById(R.id.waterVolumeItem);
        package1TypeItem=view.findViewById(R.id.package1TypeItem);
        package1AmountItem=view.findViewById(R.id.package1AmountItem);
        package2TypeItem=view.findViewById(R.id.package2TypeItem);
        package2AmountItem=view.findViewById(R.id.package2AmountItem);
        preFlushVolumeItem=view.findViewById(R.id.preFlushVolumeItem);
        preflushPauseTimeItem=view.findViewById(R.id.preflushPauseTimeItem);
        afterFlushVolumeItem =view.findViewById(R.id.afterFlushVolumeItem);
        postFlushPauseTimeItem=view.findViewById(R.id.postFlushPauseTimeItem);
        waterTypeItem=view.findViewById(R.id.waterTypeItem);
        totalTimeItem=view.findViewById(R.id.totalTimeItem);
        instantadvanceItem=view.findViewById(R.id.instantadvanceItem);
          for(PowderItem item:getpowderItems())
          {
              if(item.getGroup() == PowderItem.GROUP_INSTANT && item.getSelected() ==1)
                map.put(item.getPid()+"",item.getName());
          }
        package1TypeItem.setItemAndValues(map);
        package1TypeItem.refreshData(0);
        InitEvent();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        InitView(null);
    }

    @Override
    public void InitView(IngredientInstant a) {

        if(getIngredient()!=null)
        {
            instantadvanceItem.setProgressLow(getIngredient().getMixStart());
            instantadvanceItem.setProgressHigh(getIngredient().getMixStop());
            ingredientNameItem.setTextValue(getIngredient().getName());
            whipperSpeedItem.setCur(getIngredient().getWhipperSpeed());
            String type1 = getIngredient().getPacket1Type() + "";
            package1TypeItem.setSelItem(type1, package1TypeItem.getItemAndValues().get(type1));
            waterVolumeItem.setCur(getIngredient().getWaterVolume());
            preFlushVolumeItem.setCur(getIngredient().getPauseTimeAfterDispense() / 100);
            preflushPauseTimeItem.setProgressMaxTimes(10);
            preflushPauseTimeItem.setCur2(getIngredient().getPreflushPauseTime() / 100);
            postFlushPauseTimeItem.setProgressMaxTimes(10);
            postFlushPauseTimeItem.setCur2(getIngredient().getPauseTimeAfterDispense() / 100);
            afterFlushVolumeItem.setCur(getIngredient().getWaterAfterFlushVolume());
            package1AmountItem.setProgressMaxTimes(10);
            package1AmountItem.setCur2(getIngredient().getPacket1Amount());
            package2AmountItem.setProgressMaxTimes(10);
            package2AmountItem.setCur2(getIngredient().getPacket2Amount());
            preFlushVolumeItem.setCur(getIngredient().getPreflushVolume());
            afterFlushVolumeItem.setCur(getIngredient().getWaterAfterFlushVolume());
            waterTypeItem.setSelItem(getIngredient().getWaterType() + "", waterTypeItem.getItemAndValues().get(getIngredient().getWaterType() + ""));
        }
    }
    private String setPackage(String currentValue, String compare) {
        if(!currentValue.equalsIgnoreCase(compare))
            return currentValue;
        return "none";
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
        instantadvanceItem.setOnSeekBarChangeListener(new SeekBarPressure.OnSeekBarChangeListener() {

            @Override
            public void onProgressBefore() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBarPressure seekBar,
                                          double progressLow, double progressHigh) {
                // TODO Auto-generated method stub
                if(oningredientChanged!=null)
                    oningredientChanged.itemchanged(2);
            }

            @Override
            public void onProgressAfter() {
                // TODO Auto-generated method stub

            }});
        package1TypeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {

            @Override
            public void onItemClick(String key) {
                String name = package1TypeItem.getItemAndValues().get(key);
                String value = setPackage(name, package2TypeItem.getSelText());
                package1TypeItem.setSelItem(key, value);
                NotifyChange();
            }
        });
        waterTypeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = waterTypeItem.getItemAndValues().get(key);
                waterTypeItem.setSelItem(key, name);
                NotifyChange();
            }
        });
        whipperSpeedItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = progress;
                if (fromUser) {
                    time += whipperSpeedItem.getMin();
                    whipperSpeedItem.setCur(time);
                    NotifyChange();
                }
            }
        });
        package1AmountItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = progress;
                if (fromUser) {
                    time += package1AmountItem.getMin();
                    package1AmountItem.setCur(time);
                    NotifyChange();
                }
            }
        });



        waterVolumeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = progress;
                if (fromUser) {
                    time += waterVolumeItem.getMin();
                    waterVolumeItem.setCur(time);
                    NotifyChange();
                }
                calcTotalTime();
            }
        });
        preFlushVolumeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = progress;
                if (fromUser) {
                    time += preFlushVolumeItem.getMin();
                    preFlushVolumeItem.setCur(time);
                    NotifyChange();
                }
                calcTotalTime();
            }
    });
                preflushPauseTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        int time = progress;
                        if (fromUser) {
                            time += preflushPauseTimeItem.getMin();
                            preflushPauseTimeItem.setCur2(time);
                            NotifyChange();
                        }
                        calcTotalTime();
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }

                });
        postFlushPauseTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = progress;
                if (fromUser) {
                    time += postFlushPauseTimeItem.getMin();
                    postFlushPauseTimeItem.setCur2(time);
                    NotifyChange();
                }
                calcTotalTime();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        afterFlushVolumeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int time = progress;
                if (fromUser) {
                    time += afterFlushVolumeItem.getMin();
                    afterFlushVolumeItem.setCur(time);
                    NotifyChange();
                }
                calcTotalTime();
            }
        });
        package1AmountItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
                if (fromUser) {
                    package1AmountItem.setCur2(progress);
                    NotifyChange();
                }
            }
        });
        package2AmountItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean fromUser) {
                if (fromUser) {
                    package2AmountItem.setCur2(progress);
                    NotifyChange();
                }
            }
        });
    }

    private void initInstant()
    {
        int whipperSpeed = whipperSpeedItem.getCur();
        int waterVolume = waterVolumeItem.getCur();
        String packet1Type = (package1TypeItem.getSelKey().equals("")?"0":package1TypeItem.getSelKey());
        // int packet1Amount = package1AmountItem.getCur() * 10;
        int packet1Amount = package1AmountItem.getCur();
        String packet2Type = (package2TypeItem.getSelKey().equals("")?"0":package2TypeItem.getSelKey());
        int packet2Amount = package2AmountItem.getCur();
        int preFlushVolume = preFlushVolumeItem.getCur();
        int afterFlushVolume = afterFlushVolumeItem.getCur();
        String waterType = waterTypeItem.getSelKey();
        int postFlushPauseTime = postFlushPauseTimeItem.getCur() * 100;
        int preflushPauseTime = preflushPauseTimeItem.getCur() * 100;

        getIngredient().setMixStart((int)(instantadvanceItem.getProgressLow()));
        getIngredient().setMixStop((int)(instantadvanceItem.getProgressHigh()));
        getIngredient().setName(ingredientNameItem.getTextValue());
        getIngredient().setMixNumber(0);// 固定填0
        getIngredient().setPreflushVolume(preFlushVolume);
        getIngredient().setWaterVolume(waterVolume);
        getIngredient().setWaterAfterFlushVolume(afterFlushVolume);
        getIngredient().setWhipperSpeed(whipperSpeed);
        getIngredient().setWaterType(Integer.valueOf(waterType));
        getIngredient().setPacket1Amount(packet1Amount);
        getIngredient().setPacket1Type(Integer.valueOf(packet1Type));
        getIngredient().setPacket2Amount(packet2Amount);
        getIngredient().setPacket2Type(Integer.valueOf(packet2Type));
        getIngredient().setPreflushPauseTime(preflushPauseTime);
        getIngredient().setPauseTimeAfterDispense(postFlushPauseTime);
        getIngredient().setPacketNumber(2);
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
    public void save() {
        initInstant();
    }

    @Override
    public void NotifyChange() {
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(2);
        getIngredient().setChanged(true);
    }

    @Override
    public void NotifyNameChange(String name) {
        getIngredient().setChanged(true);
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(99);
    }

    private float calcTotalTime() {

        int waterVolume = waterVolumeItem.getSeekbar().getProgress() + waterVolumeItem.getMin();
        int preflushVolume = preFlushVolumeItem.getSeekbar().getProgress() + preFlushVolumeItem.getMin();
        int postflushVolume = afterFlushVolumeItem.getSeekbar().getProgress() + afterFlushVolumeItem.getMin();
        float preflushPauseTime = (preflushPauseTimeItem.getSeekbar().getProgress() + preflushPauseTimeItem.getMin()) / 10.0f;
        float pauseTimeAfterDispense = (postFlushPauseTimeItem.getSeekbar().getProgress() + postFlushPauseTimeItem.getMin()) / 10.0f;

        float totalTime = (waterVolume + preflushVolume + postflushVolume) / Constant.WATER_VOLUME + preflushPauseTime + pauseTimeAfterDispense + Constant.MIX_DELAY_TM / 1000.0f;
        totalTimeItem.setTextValue(new DecimalFormat("0.0s").format(totalTime));
        return totalTime;
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
            ingredientNameItem.setTextValue(data.getStringExtra("response"));
        }
    }
}
