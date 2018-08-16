package android.luna.Activity.Base;

import android.app.Application;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.luna.Activity.CustomerUI.Shopping.Adapter.CartItem;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.DataHelper;
import android.luna.Data.Warning.MachineWarn;
import android.luna.Data.module.FiterBrewStep;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientFilterBrewAdvance;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientMilk;
import android.luna.Data.module.IngredientWater;
import android.luna.Data.module.LogRecord;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Data.module.Production.AutoTestItem;
import android.luna.Data.module.ScreenSettings;
import android.luna.Utils.Lang.LangLocalHelper;
import android.luna.Utils.LogDataBaseHelper;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.rs232.Ack.AckQuery;
import android.luna.service.BlueComService;
import android.luna.service.ComService;
import android.luna.service.ScheduleService;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.squareup.leakcanary.LeakCanary;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.map.HashedMap;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Lee.li on 2018/1/5.
 */

public class CremApp extends Application {

    //log databases
    private LogDataBaseHelper logHelper=null;
    public LogDataBaseHelper getLogHelper()
    {
        if(logHelper==null)
        {
            logHelper = new LogDataBaseHelper(this,"MachineLog.db",null,2);
        }
        return  logHelper;
    }

    public synchronized  void SetLog(LogRecord log)
    {
        SQLiteDatabase db = getLogHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("event_type",log.getEvent_type());
            values.put("event_info",log.getEvent_info());
            values.put("event_help",log.getEvent_help());
            db.insert("tb_log",null,values);
            db.setTransactionSuccessful();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //结束事务
            db.endTransaction();
            db.close();
        }
    }

    public  synchronized void resetLog()
    {
        SQLiteDatabase db = getLogHelper().getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete("tb_log","",new String[]{});
            db.setTransactionSuccessful();
            db.delete("tb_counter","",new String[]{});
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //结束事务
            db.endTransaction();
            db.close();
        }
    }

    public List<LogRecord> QueryByDateTypeKeyWord(String start, String end, int[] typelst, String keyword)
    {
        List<LogRecord> ret = new ArrayList<>();
        SQLiteDatabase db = logHelper.getReadableDatabase();
        String sql = "Select * from tb_log where event_time between ? and ?";
        int pos =0;
        String[] paramlist;
        if(typelst!=null)
        {
            paramlist = new String[2+typelst.length];
            paramlist[pos++]=start;
            paramlist[pos++]=end;
            for (int item : typelst) {
                sql += " and event_type <> ? ";
                paramlist[pos++] = String.valueOf(item);
            }

        }
        else
        {
            paramlist = new String[2];
            paramlist[0]="1970-01-01 00:00:00";
            paramlist[1]="2018-01-01 00:00:00";
            //paramlist[0]=start;
            //paramlist[1]=end;
        }
        sql+="order by event_time desc";
        Cursor cursor = db.rawQuery(sql,paramlist);
        while(cursor.moveToNext()){
            int event_type = cursor.getInt(cursor.getColumnIndex("event_type"));
            String event_info = cursor.getString(cursor.getColumnIndex("event_info"));
            String event_help = cursor.getString(cursor.getColumnIndex("event_help"));
            String event_time = cursor.getString(cursor.getColumnIndex("event_time"));
            LogRecord record = new LogRecord();
            record.setEvent_type(event_type);
            record.setEvent_info(event_info);
            record.setEvent_help(event_help);
            record.setEvent_time(event_time);
            if(keyword!=null)
            {
                if(event_info.toLowerCase().contains(keyword.toLowerCase()))
                {
                    ret.add(record);
                }
            }else {
                ret.add(record);
            }
        }
        cursor.close();
        db.close();

        return ret;
    }

    //USB info
    private String usbpath="";

    public String getUsbpath() {
        return usbpath;
    }

    public void setUsbpath(String usbpath) {
        this.usbpath = usbpath;
    }


    public int getAuth_level() {
        return auth_level;
    }

    public void setAuth_level(int auth_level) {
        this.auth_level = auth_level;
    }

    private int auth_level=3;

    //Machine state (Board)
    private AckQuery MachimeState =new AckQuery();
    private boolean StopHandleWithBoard =false;
    private boolean IsComWithBoard =false;

    public String getBlue_mac() {
        return blue_mac;
    }

    public void setBlue_mac(String blue_mac) {
        this.blue_mac = blue_mac;
    }

    /**
     * Blue mac
     */
    private String blue_mac ="";

    //Machine error
    private List<MachineWarn> machineWarnList = new ArrayList<>(20);

    public List<MachineWarn> getallMachineWarnList()
    {
        return machineWarnList;
    }

    public void resettheWarningList(List<MachineWarn> value)
    {
        Predicate notmachineWarnList = new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                MachineWarn c = (MachineWarn)o;
                if(c.getWarningdevice() == 0)
                    return true;
                return false;
            }
        };
        List<MachineWarn> temp = (List<MachineWarn>)CollectionUtils.select(machineWarnList,notmachineWarnList);
        machineWarnList.clear();
        if(value!=null && value.size()>0)
            machineWarnList.addAll(value);
        if(temp!=null && temp.size()>0)
            machineWarnList.addAll(temp);
    }

    private void removeWarningFromList(MachineWarn value)
    {
        EvoTrace.e("WarningProcess","removeWarningFromList");
        final int b = value.getWarningcode();
        Predicate matchp = new Predicate(){
            @Override
            public boolean evaluate(Object o) {
                MachineWarn c = (MachineWarn)o;
                if(c.getWarningdevice() == 0 && c.getWarningcode() == b)
                    return true;
                return false;
            }
        };
        List<MachineWarn> tmp = (List<MachineWarn>)CollectionUtils.select(machineWarnList,matchp);
        if(tmp!=null || tmp.size()==0) {
            machineWarnList.removeAll(tmp);
        }
    }

    private void addWarningFromList(MachineWarn value)
    {
        EvoTrace.e("WarningProcess","addWarningFromList value ="+value.toString());
        final int b = value.getWarningcode();
        Predicate matchp = new Predicate(){
            @Override
            public boolean evaluate(Object o) {
                MachineWarn c = (MachineWarn)o;
                if(c.getWarningdevice() == 0 && c.getWarningcode() == b)
                    return true;
                return false;
            }
        };
       List<MachineWarn> tmp = (List<MachineWarn>)CollectionUtils.select(machineWarnList,matchp);
        EvoTrace.e("WarningProcess","tmp = "+tmp.toString());
        if(tmp == null || tmp.size()==0)
        {
            machineWarnList.add(value);
        }
    }

    public void WarningHelpper(MachineWarn value,int operation)
    {
        EvoTrace.e("WarningProcess","WarningHelpper");
        if(operation == 0)//remove
            removeWarningFromList(value);
        else
            addWarningFromList(value);
    }

    private Queue<String> cmdQueue = new ArrayBlockingQueue<>(64);

    ////////////////////////////////////////////////////////////////////////////
    ////////////////Language-Map////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

    public Map<String, String> get_machineLanguageMap() {
        return _machineLanguageMap;
    }

    public void set_machineLanguageMap(Map<String, String> _machineLanguageMap) {
        this._machineLanguageMap = _machineLanguageMap;
    }

    Map<String,String> _machineLanguageMap = new HashMap<>();


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    private List<CartItem> cartItems =new ArrayList<>(20);


    public int getCurrent_language() {
        return Current_language;
    }

    public void setCurrent_language(int current_language) {
        Current_language = current_language;
    }

    private int Current_language;


    //// TODO: 2018/4/20 machine device
    private Device main_device;
    private List<Device> attach_device = new ArrayList<>();


    public Device getMain_device() {
        return main_device;
    }

    public void setMain_device(Device main_device) {
        this.main_device = main_device;
    }

    public List<Device> getAttach_device() {
        return attach_device;
    }

    public void setAttach_device(List<Device> attach_device) {
        if(attach_device !=null)
            this.attach_device = attach_device;
        else
            this.attach_device = new ArrayList<>();
    }


    public List<AutoTestItem> getTestItemsReport() {
        return testItemsReport;
    }

    public void setTestItemsReport(List<AutoTestItem> testItemsReport) {
        this.testItemsReport = testItemsReport;
    }

    private List<AutoTestItem> testItemsReport =new ArrayList<>(20);


    //// TODO: 2018/1/5  boolean value
    private  boolean flagEvoService = false;
    private  boolean Isbindservice =false;
    private  boolean flagblueService = false;
    private  boolean flagscheduleService = false;
    //// TODO: 2018/1/5 myclass
    private DataHelper dataHelper=null;

    public int getMcurrentBeveragePid() {
        return mcurrentBeveragePid;
    }

    public void setMcurrentBeveragePid(int mcurrentBeveragePid) {
        this.mcurrentBeveragePid = mcurrentBeveragePid;
    }

    private int mcurrentBeveragePid =-1;

    public int getMcurrentIngredientPid() {
        return mcurrentIngredientPid;
    }

    public void setMcurrentIngredientPid(int mcurrentIngredientPid) {
        this.mcurrentIngredientPid = mcurrentIngredientPid;
    }

    private  int mcurrentIngredientPid;


    public DrinkMenuButton get_drinkMenuButton() {
        return _drinkMenuButton;
    }

    public void set_drinkMenuButton(DrinkMenuButton _drinkMenuButton) {
        this._drinkMenuButton = _drinkMenuButton;
    }

    private DrinkMenuButton _drinkMenuButton=null;


    public boolean ismainpagereload() {
        return ismainpagereload;
    }

    public void setIsmainpagereload(boolean ismainpagereload) {
        this.ismainpagereload = ismainpagereload;
    }

    //// TODO: 2018/2/28 srceen layout info
    private boolean ismainpagereload =false;
    private ScreenSettings _screenSettings =null;
    public ScreenSettings get_screenSettings_instance()
    {
        if(_screenSettings == null) {
            try {
                _screenSettings = getHelper().get_ScreenSettingsDao().queryForId(1);
            } catch (SQLException e) {
                //_screenSettings = new ScreenSettings();
            }
            if(_screenSettings == null )
            {
                _screenSettings = new ScreenSettings();
                _screenSettings.setTextcolor(0xffffffff);
                try {
                    getHelper().get_ScreenSettingsDao().create(_screenSettings);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return _screenSettings;
    }

    public void updatescreenSettings(ScreenSettings screenSettings)
    {
        _screenSettings = screenSettings;
    }
    /*  Application Service Part*/
    private void bindCoffeeService() {
        Intent intent = new Intent(CremApp.this, ComService.class);
        bindService(intent, evoService, Context.BIND_AUTO_CREATE);
    }

    private void bindblueService()
    {
        Intent intent = new Intent(CremApp.this, BlueComService.class);
        bindService(intent, blueservice, Context.BIND_AUTO_CREATE);
    }

    private void bindScheduleService()
    {
        Intent intent = new Intent(CremApp.this, ScheduleService.class);
        bindService(intent, scheduleservice, Context.BIND_AUTO_CREATE);
    }
    private void unbindScheduleService() {
        if (flagscheduleService) {
            unbindService(scheduleservice);
            flagscheduleService = false;
        }

    }
    private void unbindEvoService() {
        if (flagEvoService) {
            unbindService(evoService);
            flagEvoService = false;
        }

    }

    private void unbindBlueService()
    {
        if(flagblueService)
        {
            unbindService(blueservice);
            flagblueService=false;
        }
    }
    private ServiceConnection evoService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            EvoTrace.e("Service","evoService onServiceDisconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            ComService.MyBinder binder = (ComService.MyBinder) service;
            binder.getService();
            flagEvoService = true;
        }
    };

    private ServiceConnection blueservice = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub
            EvoTrace.e("Service","evoService onServiceDisconnected");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub
            BlueComService.MyBinder binder = (BlueComService.MyBinder) service;
            binder.getService();
            flagblueService = true;
        }
    };

    private ServiceConnection scheduleservice = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ScheduleService.MyBinder binder = (ScheduleService.MyBinder)service;
            binder.getService();
            flagscheduleService =true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void bindAllService() {
        if(!Isbindservice)
        {
            bindblueService();
            bindCoffeeService();
            bindScheduleService();
            // 读取机器类型
            Isbindservice =true;
        }
    }

    public void unbindAllService() {
        if(Isbindservice)
        {
            unbindEvoService();
            unbindBlueService();
            unbindScheduleService();
            Isbindservice = false;
        }
    }


    //// TODO: 2018/1/5  communication part
    public void cleanQueue() {
        if (cmdQueue.size() > 0) {
            cmdQueue.clear();
        }
    }

    public Queue<String> getCmdQueue() {
        return cmdQueue;
    }

    public void addCmdQueue(String cmd) {
        if (cmdQueue.size() == 64) {
            return;
        }
        cmdQueue.add(cmd);
    }


    //// TODO: 2018/1/8 databases part
    public DataHelper getHelper() {
        if (dataHelper == null) {
            dataHelper = new DataHelper(this);
        }
        return dataHelper;
    }

    public void SetDataHelper(DataHelper a)
    {

        dataHelper =a;
    }


    //// TODO: 2018/1/8 function part

    public void updateLanguage(Locale locale) {
        Log.d("ANDROID_LAB", locale.toString());
        if(!getResources().getConfiguration().locale.equals(locale)) {
            try {
                Object objIActMag, objActMagNative;
                Class clzIActMag = Class.forName("android.app.IActivityManager");
                Class clzActMagNative = Class.forName("android.app.ActivityManagerNative");
                Method mtdActMagNative$getDefault = clzActMagNative.getDeclaredMethod("getDefault");
                // IActivityManager iActMag = ActivityManagerNative.getDefault();
                objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);
                // Configuration config = iActMag.getConfiguration();
                Method mtdIActMag$getConfiguration = clzIActMag.getDeclaredMethod("getConfiguration");
                Configuration config = (Configuration) mtdIActMag$getConfiguration.invoke(objIActMag);
                config.locale = locale;
                // iActMag.updateConfiguration(config);
                // 此处需要声明权限:android.permission.CHANGE_CONFIGURATION
                // 会重新调用 onCreate();
                Class[] clzParams = {Configuration.class};
                Method mtdIActMag$updateConfiguration = clzIActMag.getDeclaredMethod("updateConfiguration", clzParams);
                mtdIActMag$updateConfiguration.invoke(objIActMag, config);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        setCurrent_language(LangLocalHelper.getlocalinfo());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Locale _UserLocale=LangLocalHelper.getlocalinfo(getCurrent_language());
        Configuration _Configuration = new Configuration(newConfig);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            _Configuration.setLocale(_UserLocale);
        } else {
            _Configuration.locale =_UserLocale;
        }
        getResources().updateConfiguration(_Configuration, getResources().getDisplayMetrics());
    }

    public AckQuery getAckQueryInstance() {
        if(MachimeState==null)
            MachimeState = new AckQuery();
        return MachimeState;
    }

    public boolean isStopHandleWithBoard() {
        return StopHandleWithBoard;
    }

    public void setStopHandleWithBoard(boolean stopHandleWithBoard) {
        StopHandleWithBoard = stopHandleWithBoard;
    }

    public boolean isComWithBoard() {
        return IsComWithBoard;
    }

    public void setComWithBoard(boolean comWithBoard) {
        IsComWithBoard = comWithBoard;
    }


    public static float[] calcFilterBrewValue(IngredientFilterBrew filterBrew) {


        float[] ints = new float[] { 0, 0 };

        if (filterBrew != null) {
			/* 每个phase中 brew motor wait的时间 */
            int Tm_pre = filterBrew.getTmPre13() * 100;

            /*if (machineType == App.MACHINE_TYPE_MF04) {
                Tm_pre = filterBrew.getTmPre04() * 100;
            }*/

            int extractionTime = filterBrew.getExtractionTime() * 100;
            int decompressTime = filterBrew.getDecompressTime() * 100;
            int openDelayTime = filterBrew.getOpenDelayTime() * 100;
			/* water flow time的时间 */
            int waterFlowTime = 0;
            int totalTime = (Constant.BREW_RUNNING_TIME + Tm_pre + extractionTime + decompressTime + openDelayTime + waterFlowTime) / 1000;
            int totalVolume = filterBrew.getWaterVolume();
            ints = new float[] { totalTime, totalVolume };
        }
        return ints;
    }

    public static float[] calcFilterBrewAdvanceValue(IngredientFilterBrewAdvance filterBrew, List<FiterBrewStep> FiterBrewSteplst) {
        float[] ints = new float[] { 0, 0 };

        if (filterBrew != null) {
			/* 每个phase中 brew motor wait的时间 */
            int totalTime = Constant.BREW_RUNNING_TIME;
            if(FiterBrewSteplst!=null)
            {
                for(FiterBrewStep item :FiterBrewSteplst)
                {
                    totalTime+=item.getWait()*100;
                }
            }

            int totalVolume = filterBrew.getWaterVolume();
            ints = new float[] { totalTime/1000.f, totalVolume };
            EvoTrace.e("calc", "calcFilterBrewValue.totalTime:" + totalTime + ";totalVolume:" + totalVolume);
        }
        return ints;
    }


    /**
     * 计算Instant的时间和总量
     *
     * @param instant
     */
    public static float[] calcInstantValue(IngredientInstant instant) {
        float[] ints = new float[] { 0, 0 };
        if (instant != null) {
			/* Mix-whipper pre flush time */
            int preFlushTime = instant.getPreflushVolume();
			/* Mix-water dispense time */
            // int waterDispenseTime = instant.getWaterDispenseTime();// modify
            // by 2015-11-28
            int waterVolume = instant.getWaterVolume();
			/* Mix-water after flush time */
            int waterAfterFlushTime = instant.getWaterAfterFlushVolume();
            // int totalTime = preFlushTime + waterDispenseTime +
            // waterAfterFlushTime;

            float pauseTimeBrforeFlush = instant.getPreflushPauseTime()/1000.0f;
            float pauseTimeAfterDispense = instant.getPauseTimeAfterDispense()/1000.0f;

            float totalTime =  (preFlushTime + waterVolume + waterAfterFlushTime) / Constant.WATER_VOLUME;
            // int totalVolume = instant.getWaterDispenseTime();
            float totalVolume = waterVolume + preFlushTime + waterAfterFlushTime;
            ints = new float[] { totalTime, totalVolume };

            EvoTrace.e("calc", "calcInstantValue.totalTime:" + totalTime + ";totalVolume:" + totalVolume);
        }
        return ints;
    }

    /**
     * 计算water的时间和总量
     *
     * @param water
     * @return
     */
    public static float[] calcWaterValue(IngredientWater water) {
        float[] ints = new float[] { 0, 0 };
        if (water != null) {
            int totalVolume = water.getWaterVolume();
            ints = new float[] { totalVolume / Constant.WATER_VOLUME, totalVolume };
            EvoTrace.e("calc", "calcWaterValue.totalTime:" + 0 + ";totalVolume:" + totalVolume);
        }
        return ints;
    }

    public static float[] calcMilkValue(IngredientMilk milk) {
        float[] ints = new float[] { 0, 0 };
        if (milk != null) {
            int totalVolume = milk.getVolume();
            ints = new float[] { totalVolume / Constant.WATER_VOLUME, totalVolume };
            EvoTrace.e("calc", "calcmilkValue.totalTime:" + 0 + ";totalVolume:" + totalVolume);
        }
        return ints;
    }


}
