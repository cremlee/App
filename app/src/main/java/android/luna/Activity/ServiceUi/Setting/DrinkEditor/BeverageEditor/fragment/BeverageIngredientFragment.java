package android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.PicSelector.aty_uiRes_selector;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverageIngredient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverage_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.BaseFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.IIngredient;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.IngredientSelectAdapter;
import android.luna.Data.DAO.EspressoDao;
import android.luna.Data.DAO.MonoStepDao;
import android.luna.Data.module.BeverageBasic;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Data.module.BeverageUi;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientFilterBrewAdvance;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientMono;
import android.luna.Data.module.IngredientMonoProcess;
import android.luna.Data.module.IngredientWater;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.widget.RecipeDragView;
import android.luna.ViewUi.widget.RecipeEditWidget;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.luna.rs232.Cmd.CmdMakeIngredient;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/14.
 */

public class BeverageIngredientFragment extends BaseFragment implements IIngredient<BeverageUi> ,View.OnClickListener{
    private aty_beverage_maker aty;
    private TextView addIngredient;
    private  SettingItemTextView2 recipeNameItem;
    private ScrollView scrollView;
    private LinearLayout layoutRecipeEditGroup;
    private LinearLayout layoutScaleUpGroup;
    private ArrayList<RecipeEditWidget> recipeEditViews = new ArrayList<>();
    private ArrayList<SettingItemSeekBar> scaleViews = new ArrayList<>();
    private HashMap<Integer, BeverageIngredient> startTimeMap = new HashMap<>();
    private CremApp app;
    private List<BeverageIngredient> getBeverageIngredientList()
    {
        return aty.getMbeverageIngredientlist();
    }
    private MaterialDialog ingredientSelectDialog;
    private MaterialDialog deleteDialog;
    private IngredientSelectAdapter ingredientSelectAdapter;
    private float totalVolume = 0;
    private int deleteLocation;
    private BeverageBasic getBasic()
    {
        return aty.getMbeverageBasic();
    }
    private MonoStepDao monoStepDao=null;
    private Object currentIngredient =null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beverage_ingredient, container, false);
        addIngredient = view.findViewById(R.id.addIngredient);
        recipeNameItem = view.findViewById(R.id.recipeNameItem);
        layoutRecipeEditGroup = view.findViewById(R.id.layoutRecipeEditGroup);
        layoutScaleUpGroup = view.findViewById(R.id.layoutScaleGroup);
        scrollView = view.findViewById(R.id.scrollView);
        InitEvent();
        ingredientSelectAdapter = new IngredientSelectAdapter( aty.getMbeverageFactoryDao().getBeverageIngerdient().GetAllIngredientName());
        ingredientSelectAdapter.setCallbacks(new IngredientSelectAdapter.ItemCallback(){
            @Override
            public void onItemClicked(int itemIndex) {
                ingredientSelectDialog.dismiss();
                showSavewindow();
                AddAnIngredientStep(ingredientSelectAdapter.GetItemPid(itemIndex));
            }
        });
        monoStepDao = new MonoStepDao(aty,aty.getApp());
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void AddAnIngredientStep(int pid)
    {
        //// TODO: 2018/2/26 xinjian yige ingredient
        int oldpid = pid;
        int newpid=-1;
        Ingredient oldIngredient = aty.getMbeverageFactoryDao().getBeverageIngerdient().getIngredientByPid(oldpid);
        int ingredientType = oldIngredient.getType();
        String name = oldIngredient.getName()+"copy";
        String ingredientStructure;
        CmdMakeIngredient cmdMakeIngredient = new CmdMakeIngredient();
        switch (ingredientType) {
            case Ingredient.TYPE_ESPRESSO:
                try {
                    newpid = aty.getMingredientFactoryDao().getIngredientDao().createIngredient(name, ingredientType, 0);
                    IngredientEspresso espresso = aty.getMingredientFactoryDao().getEspressoDao().copyEspresso(oldpid, newpid, name);
                    if (espresso != null) {
                        //// TODO: 2018/2/26 send water to luna
                        currentIngredient = espresso;
                        ingredientStructure = cmdMakeIngredient.buildEspressoStructure(espresso);
                        aty.getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, espresso.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_ESPRESSO), ingredientStructure));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                break;
            case Ingredient.TYPE_FILTER_BREW:
                try {
                    newpid = aty.getMingredientFactoryDao().getIngredientDao().createIngredient(name, ingredientType, 0);
                    IngredientFilterBrew filter = aty.getMingredientFactoryDao().getFilterBrewDao().copyFilterBrew(oldpid, newpid, name);
                    if (filter != null) {
                        //// TODO: 2018/2/26 send water to luna
                        currentIngredient = filter;
                        ingredientStructure = cmdMakeIngredient.buildFilterBrewStructure(filter);
                        aty.getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, filter.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_FILTER_BREW), ingredientStructure));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case Ingredient.TYPE_INSTANT:
                try {
                    newpid = aty.getMingredientFactoryDao().getIngredientDao().createIngredient(name, ingredientType, 0);
                    IngredientInstant instant = aty.getMingredientFactoryDao().getInstantDao().copyInstant(oldpid, newpid, name);
                    if (instant != null) {
                        //// TODO: 2018/2/26 send water to luna
                        currentIngredient = instant;
                        ingredientStructure = cmdMakeIngredient.buildInstantStructure(instant);
                        aty.getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, instant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case Ingredient.TYPE_WATER:
                try {
                    newpid = aty.getMingredientFactoryDao().getIngredientDao().createIngredient(name, ingredientType, 0);
                    IngredientWater water = aty.getMingredientFactoryDao().getWaterDao().copyWater(oldpid, newpid, name);
                    if (water != null) {
                        //// TODO: 2018/2/26 send water to luna
                        currentIngredient = water;
                        ingredientStructure = cmdMakeIngredient.buildWaterStructure(water);
                        aty.getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, water.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_WATER), ingredientStructure));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case Ingredient.TYPE_MONO:
                try {
                    newpid = aty.getMingredientFactoryDao().getIngredientDao().createIngredient(name, ingredientType, 0);
                    IngredientMono mono = aty.getMingredientFactoryDao().getMonoDao().copymono(oldpid, newpid, name);
                    if (mono != null) {
                        //// TODO: 2018/2/26 send water to luna
                        List<IngredientMonoProcess> monosteps = monoStepDao.getmonosteps(oldpid);
                        if(monosteps!=null)
                        {
                            monoStepDao.updatemonosteplist(newpid, monosteps);
                        }
                        currentIngredient = mono;
                        ingredientStructure = cmdMakeIngredient.buildMonoStructure(mono,monosteps);
                        aty.getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, mono.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_MONO), ingredientStructure));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        //// TODO: 2018/2/26 add the beverage-ingredient table
        BeverageIngredient beverageIngredient = new BeverageIngredient(aty.getMbeverageBasic().getPid(),newpid,ingredientType,100,0,0);
        aty.getMbeverageFactoryDao().getBeverageIngerdient().create(beverageIngredient);
        aty.getMbeverageIngredientlist().add(beverageIngredient);
        //// TODO: 2018/2/26 refresh ui
        InitView(null);
    }
    @Override
    public void onResume() {
        super.onResume();
        InitView(null);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_MAKE_INGREDIENT_ACK);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        getActivity().registerReceiver(receiver, filter);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constant.ACTION_MAKE_INGREDIENT_ACK)) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                int ackresult = intent.getIntExtra("ACK", 2);
                int op = intent.getIntExtra("OP", 4);
                if(op!=4) {
                    if (ackresult == 1) {
                        if(currentIngredient instanceof IngredientEspresso)
                        {
                            ((IngredientEspresso)currentIngredient).setCreatestatus(2);
                            aty.getMingredientFactoryDao().getEspressoDao().modify((IngredientEspresso)currentIngredient);
                        }
                        else if(currentIngredient instanceof IngredientFilterBrew)
                        {
                            ((IngredientFilterBrew)currentIngredient).setCreatestatus(2);
                            aty.getMingredientFactoryDao().getFilterBrewDao().modify((IngredientFilterBrew)currentIngredient);
                        }
                        else if(currentIngredient instanceof IngredientInstant)
                        {
                            ((IngredientInstant)currentIngredient).setCreatestatus(2);
                            aty.getMingredientFactoryDao().getInstantDao().modify((IngredientInstant)currentIngredient);
                        }
                        else if(currentIngredient instanceof IngredientWater)
                        {
                            ((IngredientWater)currentIngredient).setCreatestatus(2);
                            aty.getMingredientFactoryDao().getWaterDao().modify((IngredientWater)currentIngredient);
                        }
                        ((BaseActivity)getActivity()).showToast("save ingredient ok");
                    } else {
                        ((BaseActivity)getActivity()).showToast("save ingredient failed:" + ackresult);
                    }
                }
            }
        }
    };
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private MaterialDialog progressDialog;
    private void showSavewindow()
    {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Add ingredient")
                .canceledOnTouchOutside(false)
                .content("saving...")
                .progress(true, 0)
                .show();
    }
    private int getIngredientPid(int ingredientPid) {
        /*List<Ingredient> ingredients = null;
        try {
            ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientPid);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ingredients != null && ingredients.size() > 0) {
            Ingredient ingredient = ingredients.get(0);

            if (ingredient != null) {
                return ingredient.getPid();
            }
        }
        return 0;*/
       return ingredientPid;
    }

    private float addRecipeEditWidget(BeverageIngredient pub, final int position)
    {
        float beverageTotalTime = 0;
        // 确定需要查询的ingredientID，通过BeverageIngredientPub中的PID查询IngredientPubId表中的ingredientId
        int ingredientId = getIngredientPid(pub.getIngredientPid());
        //EvoTrace.e(TAG, "BeverageIngredient:" + pub.toString() + ";postion:" + position);
        RecipeEditWidget editWidget = null;
        SettingItemSeekBar seekBar = scaleViews.get(position);
        float cur = seekBar.getCur() / 100.0f;
        try {
            switch (pub.getIngredientType()) {
                case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                   /* final IngredientFilterBrewAdvance filterBrewadv = getHelper().getIngredientFilterBrewAdvanceDao().queryBuilder().where().eq("pid", ingredientId).queryForFirst();
                    if(filterBrewadv!=null)
                    {

                        float[] filterValues = App.calcFilterBrewAdvanceValue(filterBrewadv,filterBrewStepDao.getFilterBrewStep(filterBrewadv.getPid()));
                        beverageTotalTime = filterValues[0];
                        totalVolume += filterValues[1] * cur;

                        editWidget = new RecipeEditWidget(this, 10 + position);
                        editWidget.getDragView().setRecipeWidth((filterValues[0]));
                        editWidget.setBaseValue(pub, filterBrewadv);
                        editWidget.getDragView().setOnDoubleClickListener(new RecipeDragView.DoubleClickListener() {

                            @Override
                            public void onDoubleClickListener(View v) {
                                Intent intent = new Intent(BeverageIngredientFragment.this, IngredientFilterBrewerAdvanceActivity.class);
                                intent.putExtra("extra", filterBrewadv);
                                startActivity(intent);
                            }
                        });
                    }*/
                    break;
                case Ingredient.TYPE_FILTER_BREW:
                    final IngredientFilterBrew filterBrew = app.getHelper().getIngredientFilterBrewDao().queryBuilder().where().eq("pid", ingredientId).queryForFirst();
                    if(filterBrew!=null)
                    {
                        float[] filterValues = CremApp.calcFilterBrewValue(filterBrew);
                        beverageTotalTime = filterValues[0];
                        totalVolume += filterValues[1] * cur;

                        editWidget = new RecipeEditWidget(aty, 10 + position);
                        editWidget.getDragView().setRecipeWidth((filterValues[0]));
                        editWidget.setBaseValue(pub, filterBrew);
                        editWidget.getDragView().setOnDoubleClickListener(new RecipeDragView.DoubleClickListener() {

                            @Override
                            public void onDoubleClickListener(View v) {
                                app.setMcurrentIngredientPid(filterBrew.getPid());
                                Intent intent = new Intent(aty, aty_beverageIngredient_maker.class);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                case Ingredient.TYPE_INSTANT:
                    final IngredientInstant instant = app.getHelper().getIngredientInstantDao().queryBuilder().where().eq("pid", ingredientId).queryForFirst();
                    if (instant == null) {
                        return 0;
                    }
                    float[] instantValues = CremApp.calcInstantValue(instant);
                    beverageTotalTime = instantValues[0] * cur + Constant.MIX_DELAY_TM / 1000.0f + (instant.getPauseTimeAfterDispense() + instant.getPreflushPauseTime()) / 1000.0f;
                    totalVolume += instantValues[1] * cur;
                    editWidget = new RecipeEditWidget(aty, 10 + position);
                    editWidget.setBaseValue(pub, instant);
                    editWidget.getDragView().setRecipeWidth(beverageTotalTime); // 右边总长度为500，计120秒，所以乘以4
                    editWidget.getDragView().setOnDoubleClickListener(new RecipeDragView.DoubleClickListener() {

                        @Override
                        public void onDoubleClickListener(View v) {
                            app.setMcurrentIngredientPid(instant.getPid());
                            Intent intent = new Intent(aty, aty_beverageIngredient_maker.class);
                            startActivity(intent);
                            aty.showToast("Water Double click!!!"+instant.toString());
                        }
                    });
                    break;
                case Ingredient.TYPE_WATER:
                    final IngredientWater water = app.getHelper().getIngredientWaterDao().queryBuilder().where().eq("pid", ingredientId).queryForFirst();
                    if(water!=null)
                    {
                        float[] waterValues = CremApp.calcWaterValue(water);
                        beverageTotalTime = waterValues[0] * cur;
                        totalVolume += waterValues[1] * cur;
                        editWidget = new RecipeEditWidget(aty, 10 + position);
                        editWidget.setBaseValue(pub, water);
                        editWidget.getDragView().setRecipeWidth(beverageTotalTime);
                        editWidget.getDragView().setOnDoubleClickListener(new RecipeDragView.DoubleClickListener() {
                            @Override
                            public void onDoubleClickListener(View v) {
                                app.setMcurrentIngredientPid(water.getPid());
                                Intent intent = new Intent(aty, aty_beverageIngredient_maker.class);
                                startActivity(intent);
                               aty.showToast("Water Double click!!!"+water.toString());
                            }
                        });
                    }
                    break;
                case Ingredient.TYPE_MILK:
                    /*final IngredientMilk mmilk = getHelper().getIngredientMilkDao().queryBuilder().where().eq("pid", ingredientId).queryForFirst();
                    if(mmilk!=null)
                    {
                        float[] waterValues = App.calcMilkValue(mmilk);
                        beverageTotalTime = waterValues[0] * cur;
                        totalVolume += waterValues[1] * cur;
                        editWidget = new RecipeEditWidget(this, 10 + position);
                        editWidget.setBaseValue(pub, mmilk);
                        editWidget.getDragView().setRecipeWidth(waterValues[0]);
                        editWidget.getDragView().setOnDoubleClickListener(new DoubleClickListener() {
                            @Override
                            public void onDoubleClickListener(View v) {
                                Intent intent = new Intent(BeverageMakerEditRecipe.this, IngredientMilkActivity.class);
                                intent.putExtra("extra", mmilk);
                                intent.putExtra("beverage", mBeverage);
                                intent.putExtra("beveragePid", mBeverage.getPid());
                                intent.putExtra("name", mBeverage.getName() + "_" + mmilk.getName());
                                startActivity(intent);
                            }
                        });
                    }*/
                    break;
                case Ingredient.TYPE_ESPRESSO:
                    final IngredientEspresso espresso = app.getHelper().getIngredientEspressoDao().queryBuilder().where().eq("pid", ingredientId).queryForFirst();
                    if(espresso!=null)
                    {
                        float brewtime = (espresso.getBrewtime()+espresso.getPreinfusiontime()+espresso.getPrebrewtime())/10;
                        totalVolume += espresso.getWatervolume() * cur;
                        beverageTotalTime = brewtime*cur;
                        editWidget = new RecipeEditWidget(aty, 10 + position);
                        editWidget.setBaseValue(pub, espresso);
                        editWidget.getDragView().setRecipeWidth(beverageTotalTime);
                        editWidget.getDragView().setOnDoubleClickListener(new RecipeDragView.DoubleClickListener() {
                            @Override
                            public void onDoubleClickListener(View v) {
                                app.setMcurrentIngredientPid(espresso.getPid());
                                Intent intent = new Intent(aty, aty_beverageIngredient_maker.class);
                                startActivity(intent);
                                aty.showToast("Water Double click!!!"+espresso.toString());
                            }
                        });
                    }
                    break;
                    case Ingredient.TYPE_MONO:
                        final IngredientMono mono = app.getHelper().getIngredientMonoDao().queryBuilder().where().eq("pid", ingredientId).queryForFirst();
                        if(mono!=null)
                        {
                            float brewtime = mono.getInfusiontime()+mono.getBubblerruntime()+mono.getAirruntime()+mono.getBrewtime();
                            totalVolume += (mono.getInfusionwatervolume()+mono.getDispensewatervolume()) * cur;
                            beverageTotalTime = brewtime*cur;
                            editWidget = new RecipeEditWidget(aty, 10 + position);
                            editWidget.setBaseValue(pub, mono);
                            editWidget.getDragView().setRecipeWidth(beverageTotalTime);
                            editWidget.getDragView().setOnDoubleClickListener(new RecipeDragView.DoubleClickListener() {
                                @Override
                                public void onDoubleClickListener(View v) {
                                    app.setMcurrentIngredientPid(mono.getPid());
                                    Intent intent = new Intent(aty, aty_beverageIngredient_maker.class);
                                    startActivity(intent);
                                    aty.showToast("Water Double click!!!"+mono.toString());
                                }
                            });
                        }
                        break;
                default:
                    break;
            }
            editWidget.getRecipeClose().setOnClickListener(closeLinstener(position));
            recipeEditViews.add(editWidget);
            layoutRecipeEditGroup.addView(editWidget);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beverageTotalTime;
    }
    private View.OnClickListener closeLinstener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aty.showToast("delete = "+position);
                deleteLocation = position;
                //// TODO: 2018/2/26 delete the current ingredient
                deleteDialog =new MaterialDialog.Builder(aty)
                                  .title("Delete Ingredient")
                        .content("Do you want to delete this ingredient step?")
                        .negativeText("Later")
                        .negativeColor(aty.getResources().getColor(R.color.green))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                deleteDialog.dismiss();
                            }
                        })
                        .positiveText("Yes")
                        .positiveColor(aty.getResources().getColor(R.color.red_wine))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    //// TODO: 2018/2/26 delete the ingredient
                                //recipeEditViews.remove(deleteLocation);
                                //scaleViews.remove(deleteLocation);
                                RecipeEditWidget recipeEditWidget = (RecipeEditWidget) layoutRecipeEditGroup.getChildAt(deleteLocation);
                                BeverageIngredient bi = recipeEditWidget.getBeverageIngredient();
                                getBeverageIngredientList().remove(bi);
                                Ingredient ingredient = aty.getMingredientFactoryDao().getIngredientDao().getIngredient(bi.getIngredientPid());
                                if (ingredient != null) {
                                    String buildStructure = aty.getMingredientFactoryDao().getIngredientDao().deleteIngredientById(ingredient,0);
                                    if (!"".equals(buildStructure)) {
                                        String cmd = new CmdMakeIngredient().buildCmd(Constant.OPCMD_DELETE, ingredient.getPid(), AndroidUtils_Ext.oct2Hex(ingredient.getType()), buildStructure);
                                        app.addCmdQueue(cmd);
                                    }
                                }
                                InitView(null);
                                //layoutRecipeEditGroup.removeViewAt(deleteLocation);
                                //layoutScaleUpGroup.removeViewAt(deleteLocation);
                            }
                        })
                        .show();
            }

        };

    };
    @Override
    public void InitView(BeverageUi a) {
        layoutRecipeEditGroup.removeAllViews();
        layoutScaleUpGroup.removeAllViews();
        recipeEditViews.clear();
        startTimeMap.clear();
        totalVolume = 0;
        if(getBeverageIngredientList()!=null && getBeverageIngredientList().size()>0)
        {
            String ingredientName="";
            int pos=0;
            for(final BeverageIngredient item:getBeverageIngredientList())
            {
                final SettingItemSeekBar itemSeekBar = new SettingItemSeekBar(aty, null);
                int ingredientType = item.getIngredientType();
                switch (ingredientType)
                {
                    case Ingredient.TYPE_WATER:
                        IngredientWater water = null;
                        try {
                            water = app.getHelper().getIngredientWaterDao().queryBuilder().where().eq("pid", item.getIngredientPid()).queryForFirst();
                            ingredientName =water.getName();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Ingredient.TYPE_INSTANT:
                        IngredientInstant instant =null;
                        try {
                            instant =app.getHelper().getIngredientInstantDao().queryBuilder().where().eq("pid", item.getIngredientPid()).queryForFirst();
                            ingredientName =instant.getName();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;
                    case Ingredient.TYPE_FILTER_BREW:
                        IngredientFilterBrew filterBrew =null;
                        try {
                            filterBrew =app.getHelper().getIngredientFilterBrewDao().queryBuilder().where().eq("pid", item.getIngredientPid()).queryForFirst();
                            ingredientName =filterBrew.getName();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                }
                itemSeekBar.setMin(50);
                itemSeekBar.setMax(200);
                if(ingredientType == Ingredient.TYPE_FILTER_BREW )
                {
                    itemSeekBar.setMin(50);
                    itemSeekBar.setMax(100);
                }
                itemSeekBar.setName(ingredientName  + " "+getString(R.string.scale));
                itemSeekBar.setCurProgress(item.getScaleUp());
                itemSeekBar.setUnint("%");

                layoutScaleUpGroup.addView(itemSeekBar);
                scaleViews.add(itemSeekBar);
                float beverageTotalTime = addRecipeEditWidget(item, pos++);
                item.setTotalTime(beverageTotalTime);
                itemSeekBar.setonValueChange(new SettingItemSeekBar.ICoallBack(){

                    @Override
                    public void onValueChange(int value) {
                        // TODO Auto-generated method stub
                        BeverageIngredient bi = startTimeMap.get(item.getIngredientPid());
                        bi.setScaleUp(value);

                        try {
                            app.getHelper().getBeverageIngredientDao().update(bi);
                            InitView(null);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void stopchange() {
                        // TODO Auto-generated method stub
                    }});
                SeekBar bar = itemSeekBar.getSeekbar();
                //bar.setId(i + 1);
                bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                        int value = seekBar.getProgress() + itemSeekBar.getMin();
                        BeverageIngredient bi = startTimeMap.get(item.getIngredientPid());
                        bi.setScaleUp(value);

                        try {
                            app.getHelper().getBeverageIngredientDao().update(bi);
                            InitView(null);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        //EvoTrace.e(TAG, ingredient.toString() + "\n------------->value:" + value);
                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                       // EvoTrace.e(TAG, item.toString() + "onProgressChanged");
                        itemSeekBar.setCurProgress(progress + itemSeekBar.getMin());
                        //initViewTest();
                    }
                });
                startTimeMap.put(item.getIngredientPid(), item);
            }
        }
        float maxStartTime = getMaxStartTime(null);
        recipeNameItem.setTextTitle(aty.getMbeverageBasic().getName());
        recipeNameItem.setTextValue(getString(R.string.change_base_recipe_param, ( maxStartTime / 1000), totalVolume));
    }

    private float getMaxStartTime(BeverageIngredient bIngredient) {
        int curStartTime = 0;
        int pid = 0;
        float scaleUp = 1.0f;
        if (bIngredient != null) {
            pid = bIngredient.getIngredientPid();
            curStartTime = bIngredient.getStartTime();
        }

        Iterator<Map.Entry<Integer, BeverageIngredient>> iter = startTimeMap.entrySet().iterator();
        float maxValue = 0;
        while (iter.hasNext()) {
            Map.Entry<Integer, BeverageIngredient> entry = iter.next();
            BeverageIngredient bi = entry.getValue();
            float value = 0;
            int startTime = bi.getStartTime();
            float totalTime = bi.getTotalTime() * 1000;
            if (pid == bi.getIngredientPid()) {
                value = totalTime * scaleUp + curStartTime;
            } else {
                value = totalTime + startTime;
            }
            maxValue = maxValue > value ? maxValue : value;
        }
        return maxValue + Constant.BEVERAGE_DELAY_TM;
    }
    @Override
    public void InitEvent() {
        addIngredient.setOnClickListener(this);
    }

    @Override
    public void save() {

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
       // getBasic().setChanged(true);
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
        Intent intent;
        switch (id)
        {
            case R.id.addIngredient:
                ingredientSelectDialog = new MaterialDialog.Builder(aty)
                        .title("Ingredient selection")
                        .negativeText("Later")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ingredientSelectDialog.dismiss();
                            }
                        })
                        .adapter(ingredientSelectAdapter,null)
                        .canceledOnTouchOutside(false)
                        .show();
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == Constant.REQ_DRINKICONPICUTE) {
            //getUi().setIconPath(data.getStringExtra("newpath"));
            aty.saveUiData();
        }
    }
}
