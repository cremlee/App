package android.luna.Data.DAO;
import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.IngredientItems;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.Interface.IBeverageBasic;
import android.luna.Data.Interface.IBeverageCount;
import android.luna.Data.Interface.IBeverageDao;
import android.luna.Data.Interface.IBeverageGroup;
import android.luna.Data.Interface.IBeverageIngredient;
import android.luna.Data.Interface.IBeverageName;
import android.luna.Data.Interface.IBeverageUi;
import android.luna.Data.module.BeverageBasic;
import android.luna.Data.module.BeverageCount;
import android.luna.Data.module.BeverageGroup;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Data.module.BeverageUi;
import android.luna.Data.module.CanisterItemStock;
import android.luna.Data.module.DrinkName;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientFilterBrewAdvance;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientWater;
import android.luna.Data.module.Powder.PowderTypeAmount;
import android.luna.Data.module.WasterBinStock;
import android.luna.Utils.AndroidUtils_Ext;
import android.util.Log;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Lee.li on 2018/2/12.
 */

public class BeverageFactoryDao extends BaseDaobak implements IBeverageDao {
    private Context context;
    private CremApp app;
    private static final int STOCK_OUT_OF_VALUE=100;
    public BeverageFactoryDao(Context context, CremApp app) {
        super(context, app);
        this.context =context;
        this.app =app;
    }

    private IBeverageBasic beverageBasicDao =null;
    private IBeverageUi beverageUiDao =null;
    private IBeverageName beverageNameDao =null;
    private IBeverageIngredient beverageIngredientDao=null;
    private IBeverageGroup beverageGroupDao=null;
    private IBeverageCount beverageCountDao=null;

    private IngredientFactoryDao ingredientFactoryDao =null;

    private StockFactoryDao stockFactoryDao =null;

    private StockFactoryDao getStockFactoryDao()
    {
        if(stockFactoryDao ==null)
            stockFactoryDao = new StockFactoryDao(this.context ,this.app);
        return stockFactoryDao;
    }

    public IngredientFactoryDao getingredientFactoryDao()
    {
        if(ingredientFactoryDao == null)
            ingredientFactoryDao = new IngredientFactoryDao(this.context ,this.app);
        return ingredientFactoryDao;
    }
    public void DeleteBeverage(int pid)
    {
        //shan chu beverage basic
        //shan chu beverage name
        //shanchu ui biao
        //shanchu ingredient-step
        //shanchu ingredient
        getBeverageBasicDao().delete(pid);
        getBeverageUiDao().delete(pid);
        getBeverageNameDao().deleteByPid(pid);
        getBeverageIngerdient().delete(pid);
        getBeverageCountDao().delete(pid);
        getBeverageGroup().delete(pid);
        //panduan shanchu ingredient
    }

    private Map<Integer,Integer> getStockInfo()
    {
        Map<Integer,Integer> tmp = new LinkedHashMap<>();
        List<CanisterItemStock> canisterItemStocks= getStockFactoryDao().getCanisterStockDao().queryall();
        if(canisterItemStocks!=null && canisterItemStocks.size()>0)
        {
            for (CanisterItemStock itemStock:canisterItemStocks)
            {
                tmp.put(itemStock.getPid(),itemStock.getStock());
            }
        }
        return tmp;
    }
    public List<DrinkMenuButton> getDrinkIconItems(int lang) {
        List<DrinkMenuButton> ret = new ArrayList<>(30);
        DrinkMenuButton tmp;
        String name;
        List<BeverageUi> beverageUis = getBeverageUiDao().getShowSortbyIndex();
        Map<Integer, Integer> stocks = getStockInfo();
        WasterBinStock wasterBinStock = getStockFactoryDao().getWasterbinStockDao().queryByPid(99);
        if(wasterBinStock ==null)
        {
            wasterBinStock = new WasterBinStock(150,150);
            wasterBinStock.setId(1);
            getStockFactoryDao().getWasterbinStockDao().create(wasterBinStock);
        }
        if (beverageUis != null && beverageUis.size() > 0) {
            for (BeverageUi item : beverageUis) {
                BeverageBasic beverageBasic = getBeverageBasicDao().query(item.getPid());
                if (beverageBasic != null) {
                    name = getBeverageNameDao().getDrinkname(item.getPid(), lang);
                    if (name.equals(""))
                        name = beverageBasic.getName();
                    tmp = new DrinkMenuButton(name, beverageBasic.getDrinkPrice(), item.getIconPath(), item.getGalleryBkgPath(), item.getStoryTellingPath(), item.getDispenseTellingPath());
                    tmp.setPid(item.getPid());
                    tmp.setPreselect(beverageBasic.getPreselectvalue());
                    tmp.setJugmode(beverageBasic.getJug());
                    // TODO: 2018/7/23 set the drink state according to the machine state
                    List<BeverageIngredient> beverageIngredients = getBeverageIngerdient().queryforbeveragepid(item.getPid());
                    if (beverageIngredients != null && beverageIngredients.size() > 0) {
                outer:     for (BeverageIngredient bi : beverageIngredients) {
                            switch (bi.getIngredientType()) {
                                case Ingredient.TYPE_ESPRESSO:
                                    IngredientEspresso espresso = getingredientFactoryDao().getEspressoDao().findByT(bi.getIngredientPid());
                                    if (espresso == null) {
                                        tmp.setDrinkstate(2);
                                        break outer;
                                    }
                                    if(wasterBinStock.getStock()<10)
                                    {
                                        tmp.setDrinkstate(3);
                                        break outer;
                                    }
                                    if (stocks.size() > 0) {
                                        if (!stocks.containsKey(espresso.getPowdertype())) {
                                            tmp.setDrinkstate(2);
                                            break outer;
                                        } else if (stocks.get(espresso.getPowdertype()) <= STOCK_OUT_OF_VALUE) {
                                            tmp.setDrinkstate(1);
                                            break outer;
                                        }
                                    }
                                    break;
                                case Ingredient.TYPE_FILTER_BREW:
                                    IngredientFilterBrew filterBrew = getingredientFactoryDao().getFilterBrewDao().findByT(bi.getIngredientPid());
                                    if (filterBrew == null) {
                                        tmp.setDrinkstate(2);
                                        break outer;
                                    }
                                    if(wasterBinStock.getStock()<10)
                                    {
                                        tmp.setDrinkstate(3);
                                        break outer;
                                    }
                                    if (stocks.size() > 0) {
                                        if (!stocks.containsKey(filterBrew.getGrinder1Type())) {
                                            tmp.setDrinkstate(2);
                                            break outer;
                                        } else if (stocks.get(filterBrew.getGrinder1Type()) <= STOCK_OUT_OF_VALUE) {
                                            tmp.setDrinkstate(1);
                                            break outer;
                                        }
                                    }
                                    break;
                                case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                                    // IngredientFilterBrewAdvance filterBrewAdvance =getingredientFactoryDao().getFilterBrewDao().findByT(bi.getIngredientPid());
                                    break;
                                case Ingredient.TYPE_INSTANT:
                                    IngredientInstant instant = getingredientFactoryDao().getInstantDao().findByT(bi.getIngredientPid());
                                    if (instant == null) {
                                        tmp.setDrinkstate(2);
                                        break outer;
                                    }
                                    if (stocks.size() > 0) {
                                        if (!stocks.containsKey(instant.getPacket1Type())) {
                                            tmp.setDrinkstate(2);
                                            break outer;
                                        } else if (stocks.get(instant.getPacket1Type()) <= STOCK_OUT_OF_VALUE) {
                                            tmp.setDrinkstate(1);
                                            break outer;
                                        }
                                    }
                                    break;
                                case Ingredient.TYPE_MILK:
                                    break;
                                case Ingredient.TYPE_WATER:
                                    IngredientWater water = getingredientFactoryDao().getWaterDao().findByT(bi.getIngredientPid());
                                    if (water == null) {
                                        tmp.setDrinkstate(2);
                                        break outer;
                                    }
                                    break;
                            }
                        }
                    }
            ret.add(tmp);
                }
            }
            return ret;
        }
        return null;
    }

    public String deleteCmd(int beveragePid)
    {
        StringBuffer buffer = new StringBuffer();
        List<BeverageIngredient> beverageIngredients = getBeverageIngerdient().queryforbeveragepid(beveragePid);
        BeverageBasic beverageBasic = getBeverageBasicDao().query(beveragePid);
        if(beverageBasic!=null && beverageIngredients!=null && beverageIngredients.size()>0)
        {
            buffer.append(AndroidUtils_Ext.oct2Hex2(beveragePid));								// Beverage ID
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageIngredients.size()));
            for (BeverageIngredient item:beverageIngredients)
            {
                buffer.append(AndroidUtils_Ext.oct2Hex2(item.getIngredientPid()));			// Ingredient ID
                buffer.append(AndroidUtils_Ext.oct2Hex(item.getIngredientType()));	// Ingredient Id Type;
                buffer.append(AndroidUtils_Ext.oct2Hex2(item.getStartTime()));		// Start Time
                buffer.append(AndroidUtils_Ext.oct2Hex2(item.getScaleUp()));
            }
            /*Additional Msg 16 byte*/
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getCupSensor()));						// cupSensor
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getUseCount()));							// Usecount
            buffer.append(AndroidUtils_Ext.oct2Hex(Integer.valueOf(beverageBasic.getLedMode())));			// Led mode
            buffer.append(AndroidUtils_Ext.oct2Hex(Integer.valueOf(beverageBasic.getLedColor())));		// Led color
            buffer.append(AndroidUtils_Ext.oct2Hex(0));												// Led  intensity
            buffer.append(AndroidUtils_Ext.oct2Hex(Integer.valueOf(beverageBasic.getDispenseType())));	// Dispense Type
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getStrength()));							// strength adjust
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getVolume()));							// Volume adjust
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getMilk()));								// Milk adjust
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getSugar()));							// Sugar adjust
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getTopping()));							// topping
            buffer.append(AndroidUtils_Ext.oct2Hex(beverageBasic.getJug()));								// Cups adjust
            buffer.append(AndroidUtils_Ext.float2Hex2(beverageBasic.getDrinkPrice()));					// Price value 2byte
            buffer.append("0000"); 															//TODO:// Buzzer interval time 2byte
            return buffer.toString();
        }
        return null;
    }

    public boolean isWasterBinDrink(int pid)
    {
       List<BeverageIngredient> beverageIngredientList = getBeverageIngerdient().queryforbeveragepid(pid);
        if(beverageIngredientList!=null && beverageIngredientList.size()>0)
        {
            for (BeverageIngredient item:beverageIngredientList) {
                if(item.getIngredientType() == Ingredient.TYPE_FILTER_BREW ||
                        item.getIngredientType() == Ingredient.TYPE_ESPRESSO||
                        item.getIngredientType() == Ingredient.TYPE_FILTER_BREW_ADVANCE)
                    return true;
            }
        }
        return false;
    }
    public List<PowderTypeAmount> getPowderInfo(int pid)
    {
        List<PowderTypeAmount> powderTypeAmounts =new ArrayList<>();
        List<BeverageIngredient> beverageIngredientList = getBeverageIngerdient().queryforbeveragepid(pid);
        if(beverageIngredientList!=null && beverageIngredientList.size()>0)
        {
            int ingredientpid;
            for (BeverageIngredient item :beverageIngredientList)
            {
                switch (item.getIngredientType()) {
                    case Ingredient.TYPE_ESPRESSO:
                        break;
                    case Ingredient.TYPE_FILTER_BREW:
                        break;
                    case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                        break;
                    case Ingredient.TYPE_INSTANT:
                        ingredientpid = item.getIngredientPid();
                        IngredientInstant instant = getingredientFactoryDao().getInstantDao().findByT(ingredientpid);
                        if(instant!=null)
                        {
                            powderTypeAmounts.add(new PowderTypeAmount(instant.getPacket1Type(),instant.getPacket1Amount()));
                        }
                        break;
                    case Ingredient.TYPE_MILK:
                        break;
                }
            }
        }

         return powderTypeAmounts;
    }


    @Override
    public IBeverageBasic getBeverageBasicDao() {
        if(beverageBasicDao ==null)
            beverageBasicDao = new IBeverageBasic(){
                @Override
                public int getNewPid() {
                    QueryBuilder<BeverageBasic, Integer> builder = getHelper().getBeverageBasicDao().queryBuilder();
                    builder.orderBy("pid", true);
                    int beveragePid = 1;
                    try {
                        List<BeverageBasic> beverages = builder.query();
                        for (int i = 0; i < beverages.size(); i++) {
                            int pid = beverages.get(i).getPid();
                            if (pid != i + 1) {
                                beveragePid = i + 1;
                                return beveragePid;
                            }
                        }
                        beveragePid = beverages.size() + 1;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return beveragePid;
                }

                @Override
                public BeverageBasic query(int pid) {
                    try {
                        QueryBuilder<BeverageBasic, Integer> builder = getHelper().getBeverageBasicDao().queryBuilder();
                        builder.where().eq("pid",pid);
                        return builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(BeverageBasic beverageBasic) {
                    try {
                       return getHelper().getBeverageBasicDao().update(beverageBasic);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int create(BeverageBasic beverageBasic) {
                    try {
                        return getHelper().getBeverageBasicDao().create(beverageBasic);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int delete(int pid) {
                    DeleteBuilder<BeverageBasic, Integer> builder =getHelper().getBeverageBasicDao().deleteBuilder();
                    try {
                        builder.where().eq("pid",pid);
                        builder.delete();
                        //删除查找Beverage正在使用的非默认的Ingredient
                        List<BeverageIngredient> bis = getBeverageIngerdient().queryforbeveragepid(pid);
                        if(bis!=null && bis.size()>0)
                        {
                            DeleteBuilder builderIngredient;
                            for (BeverageIngredient item:bis)
                            {
                                if(item.getIsIngredientDefault()==0)
                                {
                                    switch (item.getIngredientType())
                                    {
                                        case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                                            break;
                                        case Ingredient.TYPE_FILTER_BREW:
                                            builderIngredient = getHelper().getIngredientFilterBrewDao().deleteBuilder();
                                            builderIngredient.where().eq("pid",item.getIngredientPid());
                                            builderIngredient.delete();
                                            break;
                                        case Ingredient.TYPE_INSTANT:
                                            builderIngredient = getHelper().getIngredientInstantDao().deleteBuilder();
                                            builderIngredient.where().eq("pid",item.getIngredientPid());
                                            builderIngredient.delete();
                                            break;
                                        case Ingredient.TYPE_WATER:
                                            builderIngredient = getHelper().getIngredientWaterDao().deleteBuilder();
                                            builderIngredient.where().eq("pid",item.getIngredientPid());
                                            builderIngredient.delete();
                                            break;
                                    }
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return 0;
                }

                @Override
                public List<BeverageBasic> queryall() {
                    try {
                        return getHelper().getBeverageBasicDao().queryForAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        return beverageBasicDao;
    }

    @Override
    public IBeverageUi getBeverageUiDao() {
        if(beverageUiDao ==null)
            beverageUiDao = new IBeverageUi(){
                @Override
                public List<BeverageUi> getShowSortbyIndex() {
                    try {
                        QueryBuilder<BeverageUi, Integer> builder = getHelper().getBeverageUiDao().queryBuilder();
                        builder.orderBy("sortIndex", true);
                        builder.where().eq("showOrhide", 1);
                        return builder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public List<BeverageUi> getHide() {
                    try {
                        QueryBuilder<BeverageUi, Integer> builder = getHelper().getBeverageUiDao().queryBuilder();
                        builder.where().eq("showOrhide", 0);
                        return builder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public BeverageUi query(int pid) {
                    try {
                        QueryBuilder<BeverageUi, Integer> builder = getHelper().getBeverageUiDao().queryBuilder();
                        builder.where().eq("pid", pid);
                        return builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(BeverageUi beverageUi) {
                    try {
                        return getHelper().getBeverageUiDao().update(beverageUi);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int create(BeverageUi beverageUi) {
                    try {
                        return getHelper().getBeverageUiDao().create(beverageUi);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int delete(int pid) {
                    DeleteBuilder<BeverageUi, Integer> builder =getHelper().getBeverageUiDao().deleteBuilder();
                    try {
                        builder.where().eq("pid", pid);
                        return  builder.delete();
                    }catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public List<BeverageUi> queryall() {
                    return null;
                }
            };
        return beverageUiDao;
    }

    @Override
    public IBeverageName getBeverageNameDao() {
        if(beverageNameDao ==null)
            beverageNameDao = new IBeverageName(){

                public int getlocalinfo()
                {

                    /*Local_cn =1;
                    Local_nl =2;
                    Local_da =3;
                    Local_en =4;
                    Local_fi =5;
                    Local_gm =6;
                    Local_no =7;
                    Local_sv =8;*/
                    int ret =0;
                    switch(Locale.getDefault().getLanguage())
                    {
                        case "de":
                            ret =6;
                            break;
                        case "en":
                            ret =4;
                            break;
                        case "sv":
                            ret =8;
                            break;
                        case "nb":
                            ret =3;
                            break;
                        case "fi":
                            ret =5;
                            break;
                        case "no":
                            ret =7;
                            break;
                        case  "zh":
                            ret=1;
                            break;
                        case  "nl":
                            ret=2;
                            break;
                    }
                    return ret;
                }

                @Override
                public String getDrinkname(int pid) {
                    try {
                        QueryBuilder<DrinkName, Integer> builder = getHelper().getDrinknameDao().queryBuilder();
                        builder.where().eq("pid", pid).and().eq("localinfo", getlocalinfo());
                        return builder.queryForFirst().getName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "";
                }

                @Override
                public String getDrinkname(int pid, int lang) {
                    try {
                        QueryBuilder<DrinkName, Integer> builder = getHelper().getDrinknameDao().queryBuilder();
                        builder.where().eq("pid", pid).and().eq("localinfo", lang);
                        return builder.queryForFirst().getName();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return "";
                }

                @Override
                public void clear() {
                    try {
                        DeleteBuilder<DrinkName, Integer> builder = getHelper().getDrinknameDao().deleteBuilder();
                        builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public List<DrinkName> queryallByPid(int pid) {
                    try {
                        QueryBuilder<DrinkName, Integer> builder = getHelper().getDrinknameDao().queryBuilder();
                        builder.where().eq("pid", pid);
                        return builder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public List<DrinkName> queryallByLocale() {
                    try {
                        QueryBuilder<DrinkName, Integer> builder = getHelper().getDrinknameDao().queryBuilder();
                        builder.where().eq("localinfo", getlocalinfo());
                        return builder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public List<DrinkName> queryall() {
                    try {
                       return getHelper().getDrinknameDao().queryForAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public DrinkName queryByPid(int pid) {
                    try {
                        QueryBuilder<DrinkName, Integer> builder = getHelper().getDrinknameDao().queryBuilder();
                        builder.where().eq("pid", pid).and().eq("localinfo", getlocalinfo());
                        return builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void modify(DrinkName a) {
                    try {
                        getHelper().getDrinknameDao().update(a);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void create(DrinkName a) {
                    try {
                        getHelper().getDrinknameDao().create(a);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public int deleteByPid(int pid) {
                    try {
                        DeleteBuilder<DrinkName, Integer> builder = getHelper().getDrinknameDao().deleteBuilder();
                        builder.where().eq("pid", pid);
                       return builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            };
        return beverageNameDao;

    }

    @Override
    public IBeverageIngredient getBeverageIngerdient() {
        if(beverageIngredientDao == null)
            beverageIngredientDao = new IBeverageIngredient() {
                @Override
                public List<BeverageIngredient> queryforbeveragepid(int pid) {
                    try {
                        return getHelper().getBeverageIngredientDao().queryForEq("beveragePid", pid);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public BeverageIngredient query(int pid) {
                    return null;
                }

                @Override
                public int update(BeverageIngredient beverageIngredient) {
                    try {
                        return getHelper().getBeverageIngredientDao().update(beverageIngredient);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int create(BeverageIngredient beverageIngredient) {
                    try {
                        return getHelper().getBeverageIngredientDao().create(beverageIngredient);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int delete(int pid) {
                    try {
                        List<BeverageIngredient> bis = getHelper().getBeverageIngredientDao().queryForEq("beveragePid", pid);
                        if (bis != null && bis.size() > 0) {
                            for (int i = 0; i < bis.size(); i++) {
                                BeverageIngredient bi = bis.get(i);
                                int ingredientType = bi.getIngredientType();
                                int ingredientPid = bi.getIngredientPid();
                                int isIngredientDefault = bi.getIsIngredientDefault();
                                if (isIngredientDefault == 0) {
                                    @SuppressWarnings("rawtypes")
                                    DeleteBuilder builderIngredient;
                                    builderIngredient = getHelper().getIngredientDao().deleteBuilder();
                                    builderIngredient.where().eq("pid", ingredientPid);
                                    builderIngredient.delete();
                                    switch (ingredientType) {
                                        case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                                            builderIngredient = getHelper().getIngredientFilterBrewAdvanceDao().deleteBuilder();
                                            builderIngredient.where().eq("pid", ingredientPid);
                                            builderIngredient.delete();
                                            break;
                                        case Ingredient.TYPE_FILTER_BREW:
                                            builderIngredient = getHelper().getIngredientFilterBrewDao().deleteBuilder();
                                            builderIngredient.where().eq("pid", ingredientPid);
                                            builderIngredient.delete();
                                            break;
                                        case Ingredient.TYPE_INSTANT:
                                            builderIngredient = getHelper().getIngredientInstantDao().deleteBuilder();
                                            builderIngredient.where().eq("pid", ingredientPid);
                                            builderIngredient.delete();
                                            break;
                                        case Ingredient.TYPE_WATER:
                                            builderIngredient = getHelper().getIngredientWaterDao().deleteBuilder();
                                            builderIngredient.where().eq("pid", ingredientPid);
                                            builderIngredient.delete();
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                        getHelper().getBeverageIngredientDao().delete(bis);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public List<BeverageIngredient> queryall() {
                    return null;
                }

                public List<IngredientItems> GetAllIngredientName()
                {
                    List<IngredientItems> ret = new ArrayList<>(20);
                    IngredientItems aa;
                    try {
                        QueryBuilder<Ingredient, Integer> builder =  getHelper().getIngredientDao().queryBuilder();
                        builder.where().ne("isDefault", 0);
                        List<Ingredient> tmp = builder.query();
                        if(tmp!=null &&tmp.size()>0)
                        {
                            for(Ingredient item:tmp)
                            {
                                aa = new IngredientItems(item.getName(),item.getPid());
                                ret.add(aa);
                            }
                            return ret;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public Ingredient getIngredientByPid(int pid) {
                    QueryBuilder<Ingredient, Integer> builder =  getHelper().getIngredientDao().queryBuilder();
                    try {
                        builder.where().eq("pid",pid);
                        return  builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            return beverageIngredientDao;
    }

    @Override
    public IBeverageGroup getBeverageGroup() {
        if(beverageGroupDao == null) {
            beverageGroupDao = new IBeverageGroup() {

                @Override
                public List<BeverageGroup> querylistbyPid(int pid) {
                    return null;
                }

                @Override
                public int creategroupid() {
                    int ret =1;
                    try {
                        QueryBuilder<BeverageGroup, Integer> builder =  getHelper().getBeverageGroupDao().queryBuilder();
                        builder.orderBy("groupid",true);
                        List<BeverageGroup> beverageGroups= builder.query();
                        if(beverageGroups!=null && beverageGroups.size()>0) {
                            for (BeverageGroup item:beverageGroups)
                            {
                               if(ret == item.getGroupid() )
                               {
                                   ret++;
                               }
                               else if(item.getGroupid() > ret)
                               {
                                   return ret;
                               }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return ret;
                }

                @Override
                public void deletegroup(int id) {
                    try {
                        DeleteBuilder<BeverageGroup, Integer> builder = getHelper().getBeverageGroupDao().deleteBuilder();
                        builder.where().eq("groupid", id);
                        builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public BeverageGroup queryGroupItem(int id) {
                    try {
                        return getHelper().getBeverageGroupDao().queryForEq("groupid",id).get(0);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public BeverageGroup query(int pid) {
                    return null;
                }

                @Override
                public int update(BeverageGroup beverageGroup) {
                    return 0;
                }

                @Override
                public int create(BeverageGroup beverageGroup) {

                    try {
                        getHelper().getBeverageGroupDao().create(beverageGroup);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int delete(int pid)
                {
                    try {
                        DeleteBuilder<BeverageGroup, Integer> builder = getHelper().getBeverageGroupDao().deleteBuilder();
                        builder.where().eq("pid", pid);
                        return builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public List<BeverageGroup> queryall() {
                    try {
                        return   getHelper().getBeverageGroupDao().queryForAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }
        return beverageGroupDao;
    }

    @Override
    public IBeverageCount getBeverageCountDao() {
        if(beverageCountDao ==null)
        {
            beverageCountDao = new IBeverageCount() {
                @Override
                public BeverageCount query(int pid) {
                    try {
                        QueryBuilder<BeverageCount, Integer> builder = getHelper().getBeverageCountDao().queryBuilder();
                        builder.where().eq("pid", pid);
                        return builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(BeverageCount beverageCount) {
                    try {
                        getHelper().getBeverageCountDao().update(beverageCount);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int create(BeverageCount beverageCount) {
                    try {
                        getHelper().getBeverageCountDao().create(beverageCount);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public int delete(int pid) {
                    try {
                        DeleteBuilder<BeverageCount, Integer> builder = getHelper().getBeverageCountDao().deleteBuilder();
                        builder.where().eq("pid", pid);
                        return builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }

                @Override
                public List<BeverageCount> queryall() {
                    try {
                        return getHelper().getBeverageCountDao().queryForAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }
        return beverageCountDao;
    }
}
