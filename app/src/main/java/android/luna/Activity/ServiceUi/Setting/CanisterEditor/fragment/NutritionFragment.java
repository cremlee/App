package android.luna.Activity.ServiceUi.Setting.CanisterEditor.fragment;
import android.app.Activity;
import android.app.Fragment;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.adapter.ContainAdapter;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.aty_canistereditor_main;
import android.luna.Data.module.Powder.ContainItem;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Data.module.Powder.PowderNutrition;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class NutritionFragment extends Fragment {
    private SettingItemTextView2 NameItem;
    private SettingItemSeekBar KiloItem,ProteinItem,FatItem,CarbohydrateItem,SodiumItem,SugarItem,Density;
    private BaseActivity aty;
    private PowderItem getPowderItem()
    {
        return ((aty_canistereditor_main)aty).get_powderItem();
    }
    private PowderNutrition getPowderNutrition()
    {
        return ((aty_canistereditor_main)aty).get_powderNutrition();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_powder_nutrition, container, false);
        NameItem = view.findViewById(R.id.NameItem);
        KiloItem = view.findViewById(R.id.KiloItem);
        ProteinItem = view.findViewById(R.id.ProteinItem);
        FatItem = view.findViewById(R.id.FatItem);
        CarbohydrateItem = view.findViewById(R.id.CarbohydrateItem);
        SodiumItem = view.findViewById(R.id.SodiumItem);
        SugarItem = view.findViewById(R.id.SugarItem);
        Density = view.findViewById(R.id.Density);
        InitEvent();
        RefreshUi();
        return view;
    }

    private void InitEvent()
    {
        KiloItem.setProgressMaxTimes(10);
        ProteinItem.setProgressMaxTimes(10);
        FatItem.setProgressMaxTimes(10);
        CarbohydrateItem.setProgressMaxTimes(10);
        SodiumItem.setProgressMaxTimes(10);
        SugarItem.setProgressMaxTimes(10);
        Density.setProgressMaxTimes(10);
        Density.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aty.showToast("onProgressChanged");
                float time = progress;
                if (fromUser) {
                    time += Density.getMin();
                    Density.setCur2(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStopTrackingTouch"+seekBar.getProgress());
                getPowderItem().setDensity(seekBar.getProgress());
                ((aty_canistereditor_main)aty).getPowderFactory().getPowerItemDao().update(getPowderItem());
            }
        });

        KiloItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aty.showToast("onProgressChanged");
                float time = progress;
                if (fromUser) {
                    time += KiloItem.getMin();
                    KiloItem.setCur2(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStopTrackingTouch"+seekBar.getProgress());
                getPowderNutrition().setKilocalorie(seekBar.getProgress());
                ((aty_canistereditor_main)aty).getPowderFactory().getPowderNutritionDao().update(getPowderNutrition());
            }
        });

        ProteinItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aty.showToast("onProgressChanged");
                float time = progress;
                if (fromUser) {
                    time += ProteinItem.getMin();
                    ProteinItem.setCur2(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStopTrackingTouch"+seekBar.getProgress());
                getPowderNutrition().setProtein(seekBar.getProgress());
                ((aty_canistereditor_main)aty).getPowderFactory().getPowderNutritionDao().update(getPowderNutrition());
            }
        });

        FatItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aty.showToast("onProgressChanged");
                float time = progress;
                if (fromUser) {
                    time += FatItem.getMin();
                    FatItem.setCur2(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStopTrackingTouch"+seekBar.getProgress());
                getPowderNutrition().setFat(seekBar.getProgress());
                ((aty_canistereditor_main)aty).getPowderFactory().getPowderNutritionDao().update(getPowderNutrition());
            }
        });

        CarbohydrateItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aty.showToast("onProgressChanged");
                float time = progress;
                if (fromUser) {
                    time += CarbohydrateItem.getMin();
                    CarbohydrateItem.setCur2(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStopTrackingTouch"+seekBar.getProgress());
                getPowderNutrition().setCarbohydrate(seekBar.getProgress());
                ((aty_canistereditor_main)aty).getPowderFactory().getPowderNutritionDao().update(getPowderNutrition());
            }
        });

        SodiumItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aty.showToast("onProgressChanged");
                float time = progress;
                if (fromUser) {
                    time += SodiumItem.getMin();
                    SodiumItem.setCur2(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStopTrackingTouch"+seekBar.getProgress());
                getPowderNutrition().setSodium(seekBar.getProgress());
                ((aty_canistereditor_main)aty).getPowderFactory().getPowderNutritionDao().update(getPowderNutrition());
            }
        });

        SugarItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //aty.showToast("onProgressChanged");
                float time = progress;
                if (fromUser) {
                    time += SugarItem.getMin();
                    SugarItem.setCur2(time);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //aty.showToast("onStopTrackingTouch"+seekBar.getProgress());
                getPowderNutrition().setSugar(seekBar.getProgress());
                ((aty_canistereditor_main)aty).getPowderFactory().getPowderNutritionDao().update(getPowderNutrition());
            }
        });

    }
    public void RefreshUi()
    {
        NameItem.setTextValue(getPowderItem().getName());
        KiloItem.setCur2(getPowderNutrition().getKilocalorie());
        ProteinItem.setCur2(getPowderNutrition().getProtein());
        FatItem.setCur2(getPowderNutrition().getFat());
        CarbohydrateItem.setCur2(getPowderNutrition().getCarbohydrate());
        SodiumItem.setCur2(getPowderNutrition().getSodium());
        SugarItem.setCur2(getPowderNutrition().getSugar());
        Density.setCur2(getPowderItem().getDensity());
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
