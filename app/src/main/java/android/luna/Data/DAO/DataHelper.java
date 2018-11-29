package android.luna.Data.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.luna.Data.module.*;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Data.module.Powder.PowderNutrition;
import android.luna.Data.module.System.DisplaySoundSettings;
import android.luna.Data.module.System.SecretSettings;
import android.luna.Data.module.System.SmartSettings;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Lee.li on 2018/1/5.
 */

public class DataHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "cqube.db";

    public static String getDatabaseVersion() {
        return DATABASE_VERSION+"";
    }

    private static final int DATABASE_VERSION = 1;
    private Context context;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    //###############################################################################################//
    //###############################################################################################//
    //###############################################################################################//
    //// TODO: 2018/3/1 Ingredient xiangguan
    private Dao<Ingredient, Integer> ingredientIDDao = null;
    private Dao<BeverageIngredient, Integer> beverageIngredientDao = null;
    private Dao<IngredientFilterBrew, Integer> ingredientFilterBrewDao = null;
    private Dao<IngredientWater, Integer> ingredientWaterDao = null;
    private Dao<IngredientMilk, Integer> ingredientMilkDao = null;
    private Dao<IngredientInstant, Integer> ingredientInstantDao = null;
    private Dao<IngredientEspresso, Integer> ingredientEspressoDao = null;
    private Dao<FiterBrewStep,Integer> _fiterBrewStep = null;
    private Dao<IngredientFilterBrewAdvance, Integer> _IngredientFilterBrewAdvanceDao = null;
    private Dao<IngredientMonoProcess,Integer> _ingredientMonoProcessesDao =null;
    private Dao<IngredientMono, Integer> ingredientMonosDao = null;

    public Dao<IngredientMonoProcess, Integer> getingredientMonoProcessesDao() {
        if (_ingredientMonoProcessesDao == null) {
            try {
                _ingredientMonoProcessesDao = getDao(IngredientMonoProcess.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _ingredientMonoProcessesDao;
    }
    public Dao<IngredientMono, Integer> getIngredientMonoDao() {
        if (ingredientMonosDao == null) {
            try {
                ingredientMonosDao = getDao(IngredientMono.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientMonosDao;
    }
    public Dao<FiterBrewStep, Integer> getFiterBrewStep() {
        if (_fiterBrewStep == null) {
            try {
                _fiterBrewStep = getDao(FiterBrewStep.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _fiterBrewStep;
    }
    public Dao<Ingredient, Integer> getIngredientDao() {
        if (ingredientIDDao == null) {
            try {
                ingredientIDDao = getDao(Ingredient.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientIDDao;
    }
    public Dao<IngredientFilterBrew, Integer> getIngredientFilterBrewDao() {
        if (ingredientFilterBrewDao == null) {
            try {
                ingredientFilterBrewDao = getDao(IngredientFilterBrew.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientFilterBrewDao;
    }
    public Dao<IngredientFilterBrewAdvance, Integer> getIngredientFilterBrewAdvanceDao() {
        if (_IngredientFilterBrewAdvanceDao == null) {
            try {
                _IngredientFilterBrewAdvanceDao = getDao(IngredientFilterBrewAdvance.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _IngredientFilterBrewAdvanceDao;
    }
    public Dao<IngredientWater, Integer> getIngredientWaterDao() {
        if (ingredientWaterDao == null) {
            try {
                ingredientWaterDao = getDao(IngredientWater.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientWaterDao;
    }
    public Dao<IngredientEspresso, Integer> getIngredientEspressoDao() {
        if (ingredientEspressoDao == null) {
            try {
                ingredientEspressoDao = getDao(IngredientEspresso.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientEspressoDao;
    }
    public Dao<IngredientMilk, Integer> getIngredientMilkDao() {
        if (ingredientMilkDao == null) {
            try {
                ingredientMilkDao = getDao(IngredientMilk.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientMilkDao;
    }
    public Dao<BeverageIngredient, Integer> getBeverageIngredientDao() {
        if (beverageIngredientDao == null) {
            try {
                beverageIngredientDao = getDao(BeverageIngredient.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return beverageIngredientDao;
    }
    public Dao<IngredientInstant, Integer> getIngredientInstantDao() {
        if (ingredientInstantDao == null) {
            try {
                ingredientInstantDao = getDao(IngredientInstant.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ingredientInstantDao;
    }
 //###############################################################################################//
 //###############################################################################################//
 //###############################################################################################//
    //// TODO: 2018/3/1 beverage xiangguan
    private Dao<DrinkName,Integer> _DrinkName=null;
    private Dao<BeverageBasic, Integer> beverageBasicDao = null;
    private Dao<BeverageUi, Integer> beverageUiDao = null;
    private Dao<BeverageCount, Integer> beverageCountDao = null;
    private Dao<BeverageGroup, Integer> beverageGroupDao = null;
    public Dao<BeverageGroup, Integer> getBeverageGroupDao(){
        if(beverageGroupDao==null)
        {
            try {
                beverageGroupDao = getDao(BeverageGroup.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return beverageGroupDao;
    }
    public Dao<BeverageBasic, Integer> getBeverageBasicDao(){
        if(beverageBasicDao==null)
        {
            try {
                beverageBasicDao = getDao(BeverageBasic.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return beverageBasicDao;
    }
    public Dao<BeverageUi, Integer> getBeverageUiDao(){
        if(beverageUiDao==null)
        {
            try {
                beverageUiDao = getDao(BeverageUi.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return beverageUiDao;
    }
    public Dao<BeverageCount, Integer> getBeverageCountDao(){
        if(beverageCountDao==null)
        {
            try {
                beverageCountDao = getDao(BeverageCount.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return beverageCountDao;
    }
    public Dao<DrinkName,Integer> getDrinknameDao(){
        if(_DrinkName == null)
        {
            try {
                _DrinkName = getDao(DrinkName.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _DrinkName;
    }
//###############################################################################################//
//###############################################################################################//
//###############################################################################################//
    //// TODO: 2018/3/1 Screen layout xiangguan

    private Dao<ScreenSettings,Integer> _ScreenSettingsDao =null;
    private Dao<ThemeNormal,Integer> _ThemeNormalDao =null;
    private Dao<ThemeGallery,Integer> _ThemeGalleryDao =null;
    private Dao<ThemeCould,Integer> _ThemeCouldDao =null;
    private Dao<Languageitem,Integer> _LanguageitemIntegerDao=null;
    public Dao<Languageitem,Integer> get_LanguageitemIntegerDao()
    {
        if(_LanguageitemIntegerDao ==null) {
            try {
                _LanguageitemIntegerDao = getDao(Languageitem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _LanguageitemIntegerDao;
    }
    public Dao<ScreenSettings,Integer> get_ScreenSettingsDao()
    {
        if(_ScreenSettingsDao ==null) {
            try {
                _ScreenSettingsDao = getDao(ScreenSettings.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            return _ScreenSettingsDao;
    }
    public Dao<ThemeNormal,Integer> get_ThemeNormalDao()
    {
        if(_ThemeNormalDao ==null) {
            try {
                _ThemeNormalDao = getDao(ThemeNormal.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _ThemeNormalDao;
    }
    public Dao<ThemeGallery,Integer> get_ThemeGalleryDao()
    {
        if(_ThemeGalleryDao ==null) {
            try {
                _ThemeGalleryDao = getDao(ThemeGallery.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _ThemeGalleryDao;
    }
    public Dao<ThemeCould,Integer> get_ThemeCouldDao()
    {
        if(_ThemeCouldDao ==null) {
            try {
                _ThemeCouldDao = getDao(ThemeCould.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _ThemeCouldDao;
    }
    //###############################################################################################//
    //###############################################################################################//
    //###############################################################################################//
    //// TODO: 2018/4/25 gexinghua caidan xiangguan
    private Dao<PersonItem,Integer> _PersonItemDao =null;
    private Dao<PersonDrink,Integer> _PersonDrinkDao =null;
    public Dao<PersonItem,Integer> get_PersonItemDao()
    {
        if(_PersonItemDao ==null) {
            try {
                _PersonItemDao = getDao(PersonItem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _PersonItemDao;
    }
    public Dao<PersonDrink,Integer> get_PersonDrinkDao()
    {
        if(_PersonDrinkDao ==null) {
            try {
                _PersonDrinkDao = getDao(PersonDrink.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _PersonDrinkDao;
    }
    //###############################################################################################//
    //###############################################################################################//
    //###############################################################################################//
    //// TODO: 2018/5/17 yuancailiao xiangguan
    private Dao<PowderItem,Integer> _powderItemDao =null;
    private Dao<PowderNutrition,Integer> _powderNutritionDao =null;
    public Dao<PowderItem,Integer> get_powderItemDao()
    {
        if(_powderItemDao ==null) {
            try {
                _powderItemDao = getDao(PowderItem.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _powderItemDao;
    }
    public Dao<PowderNutrition,Integer> get_powderNutritionDao()
    {
        if(_powderNutritionDao ==null) {
            try {
                _powderNutritionDao = getDao(PowderNutrition.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _powderNutritionDao;
    }
    //###############################################################################################//
    //###############################################################################################//
    //###############################################################################################//
    //// TODO: 2018/5/30 schedule
    private Dao<Scheduler,Integer>  _SchedulerDao =null;
    private Dao<SchedulerDetail,Integer>  _SchedulerDetailDao =null;
    private Dao<VendSchedulerDetail,Integer>  _VendSchedulerDetailDao =null;

    private Dao<Smart,Integer>  _smartDao =null;
    private Dao<SmartDetail,Integer>  _smartDetailsDao =null;

    private Dao<DaylightSetting,Integer>  _daylightSettingsDao =null;

    public Dao<Scheduler,Integer> get_SchedulerDao()
    {
        if(_SchedulerDao ==null)
        {
            try {
                _SchedulerDao = getDao(Scheduler.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _SchedulerDao;
    }
    public Dao<SchedulerDetail,Integer> get_SchedulerDetailDao()
    {
        if(_SchedulerDetailDao ==null)
        {
            try {
                _SchedulerDetailDao = getDao(SchedulerDetail.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _SchedulerDetailDao;
    }
    public Dao<VendSchedulerDetail,Integer> get_VendSchedulerDetailDao()
    {
        if(_VendSchedulerDetailDao ==null)
        {
            try {
                _VendSchedulerDetailDao = getDao(VendSchedulerDetail.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _VendSchedulerDetailDao;
    }
    public Dao<Smart,Integer> get_SmartDao()
    {
        if(_smartDao ==null)
        {
            try {
                _smartDao = getDao(Smart.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _smartDao;
    }
    public Dao<SmartDetail,Integer> get_SmartDetailDao()
    {
        if(_smartDetailsDao ==null)
        {
            try {
                _smartDetailsDao = getDao(SmartDetail.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _smartDetailsDao;
    }
    public Dao<DaylightSetting,Integer> get_daylightSettingsDao()
    {
        if(_daylightSettingsDao ==null)
        {
            try {
                _daylightSettingsDao = getDao(DaylightSetting.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _daylightSettingsDao;
    }
    //###############################################################################################//
    //###############################################################################################//
    //###############################################################################################//
    // TODO: 2018/6/6 stock
    private Dao<CanisterItemStock,Integer>  _canisterItemStocks =null;
    private Dao<WasterBinStock,Integer> _wasterBinStocks=null;
    public Dao<CanisterItemStock,Integer> get_canisterItemStocksDao()
    {
        if(_canisterItemStocks == null)
        {
            try {
                _canisterItemStocks = getDao(CanisterItemStock.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _canisterItemStocks;
    }
    public Dao<WasterBinStock,Integer> get_wasterBinStocksDao()
    {
        if(_wasterBinStocks == null)
        {
            try {
                _wasterBinStocks = getDao(WasterBinStock.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _wasterBinStocks;
    }
    //###############################################################################################//
    //###############################################################################################//
    //###############################################################################################//
    // TODO: 2018/7/30 payment
    private Dao<PaymentSetting,Integer>  _paymentSettingIntegerDao =null;
    public Dao<PaymentSetting,Integer> get_paymentSettingIntegerDao()
    {
        if(_paymentSettingIntegerDao == null)
        {
            try {
                _paymentSettingIntegerDao = getDao(PaymentSetting.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _paymentSettingIntegerDao;
    }
    //###############################################################################################//
    //#####################################system param #############################################//
    //###############################################################################################//
    private Dao<DisplaySoundSettings,Integer>  _displaySoundSettingsIntegerDao =null;
    private Dao<SecretSettings,Integer> _secretSettingsIntegerDao=null;
    private Dao<SmartSettings,Integer> _smartSettingsIntegerDao =null;

    public Dao<DisplaySoundSettings,Integer> get_displaySoundSettingsIntegerDao()
    {
        if(_displaySoundSettingsIntegerDao ==null)
        {
            try {
                _displaySoundSettingsIntegerDao = getDao(DisplaySoundSettings.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _displaySoundSettingsIntegerDao;
    }

    public Dao<SecretSettings,Integer> get_secretSettingsIntegerDao()
    {
        if(_secretSettingsIntegerDao ==null)
        {
            try {
                _secretSettingsIntegerDao = getDao(SecretSettings.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _secretSettingsIntegerDao;
    }

    public Dao<SmartSettings,Integer> get_smartSettingsIntegerDao()
    {
        if(_smartSettingsIntegerDao ==null)
        {
            try {
                _smartSettingsIntegerDao = getDao(SmartSettings.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _smartSettingsIntegerDao;
    }
    //###############################################################################################//
    //###############################v###########Machine info part#####################################//
    //###############################################################################################//
    private Dao<MachineInfo,Integer> _machineInfoIntegerDao =null;

    public Dao<MachineInfo,Integer> get_machineInfoIntegerDao()
    {
        if(_machineInfoIntegerDao ==null)
        {
            try {
                _machineInfoIntegerDao = getDao(MachineInfo.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return _machineInfoIntegerDao;
    }
    //###############################################################################################//
    //###############################################################################################//
    //###############################################################################################//

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        createtable(connectionSource);
        InitDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createtable(ConnectionSource connectionSource)
    {
        try {
            TableUtils.createTable(connectionSource, IngredientMonoProcess.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, MachineInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, CanisterItemStock.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, WasterBinStock.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, Smart.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, SmartDetail.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, Scheduler.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, SchedulerDetail.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, VendSchedulerDetail.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, Languageitem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, ThemeCould.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, ThemeGallery.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, ThemeNormal.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, ScreenSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, BeverageBasic.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, BeverageUi.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, BeverageCount.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, BeverageGroup.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, Ingredient.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, BeverageIngredient.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, IngredientWater.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, IngredientInstant.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, IngredientFilterBrew.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, FiterBrewStep.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, IngredientFilterBrewAdvance.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, IngredientMilk.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, IngredientEspresso.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, IngredientMono.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, DrinkName.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, PersonDrink.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, PersonItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, PowderItem.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, PowderNutrition.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, PaymentSetting.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, DaylightSetting.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, DisplaySoundSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, SmartSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, SecretSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void cleartable()
    {
        try {
            TableUtils.dropTable(getConnectionSource(),SecretSettings.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void InitDatabase()
    {
        try {
            get_LanguageitemIntegerDao().createOrUpdate(new Languageitem(1,1,0,0,0,0,0,0,0));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            get_paymentSettingIntegerDao().createOrUpdate(new PaymentSetting(1));
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            get_displaySoundSettingsIntegerDao().createOrUpdate(new DisplaySoundSettings());
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            get_smartSettingsIntegerDao().createOrUpdate(new SmartSettings());
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            get_secretSettingsIntegerDao().createOrUpdate(new SecretSettings());
        }catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            get_machineInfoIntegerDao().createOrUpdate(new MachineInfo());
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void testadd()
    {
       /* try {
            TableUtils.createTable(connectionSource, DisplaySoundSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, SmartSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, SecretSettings.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }
}
