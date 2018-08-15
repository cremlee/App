package android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverage_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.BaseFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.IIngredient;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip.IngredientnameTip;
import android.luna.Data.module.BeverageBasic;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientInstant;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/14.
 */

public class BeverageBasicFragment extends BaseFragment implements IIngredient<BeverageBasic>,View.OnClickListener{
    private aty_beverage_maker aty;
    private SettingItemTextView2 beverageNameItem;
    private SettingItemCheckBox stoppableCheck;
    private SettingItemCheckBox cupSensorCheck;
    private LinearLayout drinkPricelyt;
    private EditText drinkPriceValue;
    private SettingItemCheckBox dirtdrinkCheck;
    private SettingItemDropDown ledColorItem;
    // buzzer sound
    private SettingItemDropDown ledModeItem;
    // dispense type
    private SettingItemDropDown dispenseTypeItem;
    // Pre Selection
    private SettingItemCheckBox chocolateCheck;
    private SettingItemCheckBox milkCheck;
    private SettingItemCheckBox strengthCheck;
    private SettingItemCheckBox sugarCheck;
    private SettingItemCheckBox toppingCheck;
    private SettingItemCheckBox volumeCheck;
    private SettingItemCheckBox jugCheck;
    private Map<String, String> type_map = new HashMap<>();
    private Map<String, String> clr_map = new HashMap<>();
    private Map<String, String> mode_map = new HashMap<>();
    private BeverageBasic getBasic()
    {
        return aty.getMbeverageBasic();
    }
    private CremApp app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        String[] strings = getResources().getStringArray(R.array.ledcolor);
        for (int i = 0; i < strings.length; i++) {
            clr_map.put(String.valueOf(i+1), strings[i]);
        }
        strings = getResources().getStringArray(R.array.ledmode);
        for (int i = 0; i < strings.length; i++) {
            mode_map.put(String.valueOf(i+1), strings[i]);
        }
        strings = getResources().getStringArray(R.array.Dispens_Type);
        for (int i = 0; i < strings.length; i++) {
            type_map.put(String.valueOf(i+1), strings[i]);
        }
        View view = inflater.inflate(R.layout.fragment_beverage_basic, container, false);
        beverageNameItem = view.findViewById(R.id.beverageNameItem);
        //stoppableCheck = (SettingItemCheckBox) view.findViewById(R.id.stoppableCheck);
        jugCheck = view.findViewById(R.id.jugCheck);
        dirtdrinkCheck = view.findViewById(R.id.dirctCheck);
        cupSensorCheck =  view.findViewById(R.id.cupSensorCheck);
        drinkPriceValue =  view.findViewById(R.id.drinkPriceValue);
        drinkPricelyt =  view.findViewById(R.id.drinkPricelyt);
        ledColorItem =  view.findViewById(R.id.brewLightItem);
        ledModeItem =  view.findViewById(R.id.ledModeItem);
        ledColorItem.setItemAndValues(clr_map);
        ledModeItem.setItemAndValues(mode_map);
        ledColorItem.refreshData(0);
        dispenseTypeItem =  view.findViewById(R.id.dispenseTypeItem);
        dispenseTypeItem.setItemAndValues(type_map);
        chocolateCheck =  view.findViewById(R.id.chocolateCheck);
        strengthCheck =  view.findViewById(R.id.strengthCheck);
        milkCheck =  view.findViewById(R.id.milkCheck);
        sugarCheck =  view.findViewById(R.id.sugarCheck);
        toppingCheck = view.findViewById(R.id.toppingCheck);
        volumeCheck =  view.findViewById(R.id.volumeCheck);
        chocolateCheck.setVisibility(View.GONE);
        strengthCheck.setVisibility(View.GONE);
        milkCheck.setVisibility(View.GONE);
        sugarCheck.setVisibility(View.GONE);
        toppingCheck.setVisibility(View.GONE);
        InitEvent();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        InitView(null);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private boolean setCheckedValue(int value){
        if(value==1){
            return true;
        }
        return false;
    }
    private int getDownValue(String select) {
        if ("".equals(select)) {
            return 1;
        }
        return Integer.valueOf(select);
    }
    private int getCheckedValue(boolean bool) {
        return bool ? 1 : 0;
    }
    @Override
    public void InitView(BeverageBasic a) {
        if(getBasic()!=null)
        {
            beverageNameItem.setTextValue(getBasic().getName());
            drinkPriceValue.setText(getBasic().getDrinkPrice() + "");
            //stoppableCheck.setChecked(setCheckedValue(getBasic().getStoppable()));
            cupSensorCheck.setChecked(setCheckedValue(getBasic().getCupSensor()));
            strengthCheck.setChecked(setCheckedValue(getBasic().getStrength()));
            chocolateCheck.setChecked(setCheckedValue(getBasic().getChocolate()));
            milkCheck.setChecked(setCheckedValue(getBasic().getMilk()));
            sugarCheck.setChecked(setCheckedValue(getBasic().getSugar()));
            toppingCheck.setChecked(setCheckedValue(getBasic().getTopping()));
            volumeCheck.setChecked(setCheckedValue(getBasic().getVolume()));
            jugCheck.setChecked(setCheckedValue(getBasic().getJug()));
            dirtdrinkCheck.setChecked(setCheckedValue(getBasic().getQuickDrink()));
			/*dispen == push&hold then disable one button*/
            if(getBasic().getDispenseType() == 1)
            {
                jugCheck.setVisibility(View.GONE);
                jugCheck.setChecked(false);
                dirtdrinkCheck.setVisibility(View.GONE);
                dirtdrinkCheck.setChecked(false);
            }else
            {
                jugCheck.setVisibility(View.VISIBLE);
                jugCheck.setEnabled(true);
                dirtdrinkCheck.setVisibility(View.VISIBLE);
                dirtdrinkCheck.setEnabled(true);
            }
            String ledColor = ledColorItem.getItemAndValues().get(getBasic().getLedColor() + "");
            ledColorItem.setSelItem(getBasic().getLedColor() + "", ledColor);
            ledColorItem.refreshData(getBasic().getLedColor());

            String ledMode = ledModeItem.getItemAndValues().get(getBasic().getLedMode() + "");
            ledModeItem.setSelItem(getBasic().getLedMode() + "", ledMode);
            ledModeItem.refreshData(getBasic().getLedMode());

            String dispenseType = dispenseTypeItem.getItemAndValues().get(getBasic().getDispenseType() + "");
            dispenseTypeItem.setSelItem(getBasic().getDispenseType() + "", dispenseType);
            dispenseTypeItem.refreshData(getBasic().getDispenseType());
        }
    }

    @Override
    public void InitEvent() {
        beverageNameItem.setOnClickListener(this);
        ledColorItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {

            @Override
            public void onItemClick(String key) {
                String selText = ledColorItem.getItemAndValues().get(key);
                ledColorItem.setSelItem(key, selText);
                NotifyChange();
            }
        });
        ledModeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {

            @Override
            public void onItemClick(String key) {
                String selText = ledModeItem.getItemAndValues().get(key);
                ledModeItem.setSelItem(key, selText);
                NotifyChange();
            }
        });
        dispenseTypeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {

            @Override
            public void onItemClick(String key) {
                String selText = dispenseTypeItem.getItemAndValues().get(key);
                dispenseTypeItem.setSelItem(key, selText);
                NotifyChange();
                if(key.equalsIgnoreCase("1"))//push
                {
                    jugCheck.setVisibility(View.GONE);
                    dirtdrinkCheck.setVisibility(View.GONE);
                    jugCheck.setChecked(false);
                    dirtdrinkCheck.setChecked(false);
                }else
                {
                    jugCheck.setVisibility(View.VISIBLE);
                    dirtdrinkCheck.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void save() {
        getBasic().setName(beverageNameItem.getTextValue());
        getBasic().setCupSensor(getCheckedValue(cupSensorCheck.isChecked()));
        String price = drinkPriceValue.getText() + "";
        if ("".equals(price)) {
            price = "0.0";
        }
        getBasic().setDrinkPrice(Float.valueOf(price));
        String ledColor = ledColorItem.getSelKey();
        String ledMode = ledModeItem.getSelKey();
        String dispenseType = dispenseTypeItem.getSelKey();
        getBasic().setLedColor(getDownValue(ledColor));
        getBasic().setLedMode(getDownValue(ledMode));
        getBasic().setDispenseType(getDownValue(dispenseType));
        getBasic().setChocolate(getCheckedValue(chocolateCheck.isChecked()));
        getBasic().setMilk(getCheckedValue(milkCheck.isChecked()));
        getBasic().setStrength(getCheckedValue(strengthCheck.isChecked()));
        getBasic().setSugar(getCheckedValue(sugarCheck.isChecked()));
        getBasic().setTopping(getCheckedValue(toppingCheck.isChecked()));
        getBasic().setVolume(getCheckedValue(volumeCheck.isChecked()));
        getBasic().setJug(getCheckedValue(jugCheck.isChecked()));
        getBasic().setQuickDrink(getCheckedValue(dirtdrinkCheck.isChecked()));
       // ModifyPreselection();

    }

    private void ModifyPreselection()
    {
        List<String> lstpre = new ArrayList<>();
        List<BeverageIngredient> beverageIngredients;
        try {
            beverageIngredients = app.getHelper().getBeverageIngredientDao().queryForEq("beveragePid", getBasic().getPid());
            if (beverageIngredients != null && beverageIngredients.size() > 0) {
                for (int i = 0; i < beverageIngredients.size(); i++) {
                    BeverageIngredient ingredient = beverageIngredients.get(i);
                    int ingredientType = ingredient.getIngredientType();
                    switch (ingredientType) {
                        case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                        case Ingredient.TYPE_FILTER_BREW:
                            if(!lstpre.contains("strength"))
                                lstpre.add("strength");
                            break;
                        case Ingredient.TYPE_INSTANT:
                            IngredientInstant instant = app.getHelper().getIngredientInstantDao().queryBuilder().where().eq("pid", ingredient.getIngredientPid()).queryForFirst() ;
                            if(instant==null)
                                break;
                            switch(instant.getPacket1Type())
                            {
                                case 1:
                                case 7:
                                case 8:
                                    if(!lstpre.contains("strength"))
                                        lstpre.add("strength");
                                    break;
                                case 2:
                                    if(!lstpre.contains("chocolate"))
                                        lstpre.add("chocolate");
                                    break;
                                case 3:
                                case 6:
                                    if(!lstpre.contains("sugar"))
                                        lstpre.add("sugar");
                                    break;
                                case 4:
                                case 5:
                                    if(!lstpre.contains("milk"))
                                        lstpre.add("milk");
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case Ingredient.TYPE_WATER:
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(!lstpre.contains("strength"))
            getBasic().setStrength(0);
        if(!lstpre.contains("chocolate"))
            getBasic().setChocolate(0);
        if(!lstpre.contains("sugar"))
            getBasic().setSugar(0);
        if(!lstpre.contains("milk"))
            getBasic().setMilk(0);
    }

    @Override
    public void NotifyChange() {
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(11);
        getBasic().setChanged(true);
    }

    @Override
    public void NotifyNameChange(String name) {
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(99);
    }
    @Override
    public void onAttach(Activity activity) {
        aty = (aty_beverage_maker) activity;
        app = aty.getApp();
        super.onAttach(activity);
    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        switch (id)
        {
            case R.id.beverageNameItem:
                Intent intent = new Intent(getActivity(),IngredientnameTip.class);
                intent.putExtra("argument", getBasic() != null ? getBasic().getName() : "beverage");
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
            getBasic().setName(data.getStringExtra("response"));
            NotifyNameChange("name");
        }
    }
}
