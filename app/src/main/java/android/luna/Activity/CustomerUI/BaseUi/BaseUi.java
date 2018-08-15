package android.luna.Activity.CustomerUI.BaseUi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.CustomerUI.Gallery.Adapter.MachineWarningAdapter;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_12;
import android.luna.Activity.CustomerUI.Gallery.aty_theme_gallery;
import android.luna.Activity.CustomerUI.Normal.aty_customer_ui_3;
import android.luna.Activity.CustomerUI.Normal.aty_theme_normal;
import android.luna.Activity.CustomerUI.Shopping.aty_theme_shop;
import android.luna.Activity.CustomerUI.ThreeDCloud.aty_theme_3d;
import android.luna.Activity.EventUi.ErrorBlockUi;
import android.luna.Activity.OtherUi.ScreenSaverActivity;
import android.luna.Activity.ServiceUi.aty_service_main;
import android.luna.Activity.Tip.aty_psw_input;
import android.luna.BlueCom.BlueActionDefine;
import android.luna.BlueCom.BlueCmdDefine;
import android.luna.BlueCom.cmd.RespQueryBeverage;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.PaymentDao;
import android.luna.Data.DAO.PersonFactoryDao;
import android.luna.Data.DAO.ScreenFactoryDao;
import android.luna.Data.module.BeverageBasic;
import android.luna.Data.module.Key.AliAuthKey;
import android.luna.Data.module.Languageitem;
import android.luna.Data.module.PaymentSetting;
import android.luna.Data.module.PersonDrink;
import android.luna.Data.module.PersonItem;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.BASE64Decoder;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Key.KeyManager;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.FloatActionMenu.FloatingActionButton;
import android.luna.ViewUi.FloatActionMenu.FloatingActionsMenu;
import android.luna.ViewUi.FloatButton.floateutil.FloatBallView;
import android.luna.ViewUi.FloatButton.util.FloatUtil;
import android.luna.ViewUi.HintLable.IconNumberView;
import android.luna.ViewUi.LanguageUi.LanguagePopWindow;
import android.luna.ViewUi.LanguageUi.adapter.LanguageAdapter;
import android.luna.ViewUi.OfficeUi.OfficePopWindow;
import android.luna.ViewUi.warnPopWin.WarningPopWindow;
import android.luna.rs232.Cmd.CmdMakeDrink;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.cqube.communication.BluePortManager;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import evo.luna.android.R;

import static android.luna.BlueCom.BlueActionDefine.ACTION_DATA_TO_GAME;

/**
 * Created by Lee.li on 2018/5/14.
 */

public class BaseUi extends BaseActivity implements View.OnClickListener ,IBaseUI{
    private static final int WATH = 2001;
    /*profile part*/
    private OfficePopWindow officePopWindow;
    private FloatBallView floatBallView =null;
    /*other*/
    private TimeThread timeThread=null;
    private boolean tmflag = true;

    public boolean IsHide =false;
    public void setGestureDetector(GestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    private GestureDetector gestureDetector = null;
    /* UI layout part */
    private IconNumberView btn_info;
    private TextView tv_comp_name;
    private ImageView image_banner,profile;
    private FloatingActionsMenu menu_fav;
    private TextClock tv_time;
    private Button lang;
    public RelativeLayout getRlyt_bg() {
        return rlyt_bg;
    }
    private RelativeLayout rlyt_bg;
    /* data part */
    protected List<DrinkMenuButton> drinkMenuButtonList=new ArrayList<>(20);
    private DrinkMenuButton currentbtn=null;
    private BeverageFactoryDao beverageFactoryDao=null;
    private PersonFactoryDao personFactoryDao =null;
    private List<PersonItem> personItems = new ArrayList<>(10);
    private LanguagePopWindow languagePopWindow;
    private boolean isActived = false;
    private MachineWarningAdapter warningAdapter;
    private ScreenFactoryDao screenFactoryDao;
    private PaymentSetting _paymentSetting;
    private PaymentDao paymentDao;
    private FloatingActionButton top1,top2,top3;



    public interface Languagechanged
    {
        void updated();
    }
    private Languagechanged languagechanged;
    public void setOnLanguagechanged(Languagechanged a)
    {
        languagechanged =a;
    }



    private void sendEmptyMessageDelayed() {
        if (handler.hasMessages(WATH)) {
            handler.removeMessages(WATH);
        }
        handler.sendEmptyMessageDelayed(WATH, AndroidUtils_Ext.getScreenSaverTime(getApp().get_screenSettings_instance().getScreensaverflag()));
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WATH ) {
                String simpleName = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                if (!"ScreenSaverActivity".equals(simpleName)) {
                    startActivity(new Intent(BaseUi.this, ScreenSaverActivity.class));
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                handler.removeMessages(WATH);
                break;
            case  MotionEvent.ACTION_UP:
                sendEmptyMessageDelayed();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApp().getHelper().testadd();
        //addDragView();
        getApp().bindAllService();
        warningAdapter = new MachineWarningAdapter(getApp().getallMachineWarnList(),this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BlueActionDefine.ACTION_USB_REMOVE);
        filter.addAction(BlueActionDefine.ACTION_USB_INSERT);
        filter.addAction(BlueActionDefine.ACTION_TELNET_CMD_ECO);
        filter.addAction(BlueActionDefine.ACTION_TELNET_CMD_QUERY_BEVERAGE);
        filter.addAction(BlueActionDefine.ACTION_TELNET_CMD_SET_THEME);
        filter.addAction(BlueActionDefine.ACTION_TELNET_CMD_MAKE_DRINK);
        filter.addAction(Constant.ACTION_WARNING_NOTIFICATION);
        filter.addAction(Constant.ACTION_ERROR_CHECK_BLOCK_UI);
        registerReceiver(receiver, filter);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if(Constant.ACTION_WARNING_NOTIFICATION.equals(action))
            {
                mHandler.sendEmptyMessage(1004);
            }
            else if(Constant.ACTION_ERROR_CHECK_BLOCK_UI.equals(action))
            {
                EvoTrace.e("ack","get Constant.ACTION_ERROR_CHECK_BLOCK_UI");
                startActivity(new Intent(BaseUi.this, ErrorBlockUi.class));
            }
            else if (BlueActionDefine.ACTION_USB_INSERT.equals(action)) {
                if(officePopWindow!=null && officePopWindow.isShowing())
                {
                    String path = intent.getStringExtra("PATH");
                    getApp().setUsbpath(path);
                    officePopWindow.NotifyUSB(true);
                }
            } else if (BlueActionDefine.ACTION_USB_REMOVE.equals(action)) {
                if(officePopWindow!=null && officePopWindow.isShowing())
                {
                    officePopWindow.NotifyUSB(false);
                }
            }
            else if (BlueActionDefine.ACTION_TELNET_CMD_ECO.equals(action)) {
                 //showToast("ACTION_TELNET_CMD_ECO "+intent.getStringExtra("bluedata"));
                if(BlueCmdDefine.ECO_ENTER.equals(intent.getStringExtra("bluedata"))) {
                    String simpleName = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                    if(isActived) {
                        if (!"ScreenSaverActivity".equals(simpleName)) {
                            startActivity(new Intent(BaseUi.this, ScreenSaverActivity.class));
                        }
                    }
                }
                else if (BlueCmdDefine.ECO_EXIT.equals(intent.getStringExtra("bluedata")))
                {
                    String simpleName = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                    if ("ScreenSaverActivity".equals(simpleName)) {
                        AppManager.getAppManager().finishActivity(ScreenSaverActivity.class);
                    }
                }
            }
            else if(BlueActionDefine.ACTION_TELNET_CMD_QUERY_BEVERAGE.equals(action))
            {
                if(isActived) {
                    String cmddata = (new RespQueryBeverage(drinkMenuButtonList)).toCmd();
                    showToast("ACTION_TELNET_CMD_QUERY_BEVERAGE " + cmddata);
                    // TODO: 2018/6/8 fasong caidan xing xi
                    Intent actIntent = new Intent(ACTION_DATA_TO_GAME);
                    actIntent.putExtra("cmddata", cmddata);
                    sendBroadcast(actIntent);
                }
            }
            else if(BlueActionDefine.ACTION_TELNET_CMD_SET_THEME.equals(action))
            {
                if(isActived) {
                    String simpleName;
                    if (BlueCmdDefine.MODE_NORMAL.equals(intent.getStringExtra("bluedata"))) {
                        simpleName = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                        EvoTrace.e("blue", "simpleName =" + simpleName);
                            if (!"aty_theme_normal".equals(simpleName)) {
                                getApp().get_screenSettings_instance().setThemetype(1);
                                try {
                                    getApp().getHelper().get_ScreenSettingsDao().update(getApp().get_screenSettings_instance());
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                startActivity(new Intent(BaseUi.this, aty_theme_normal.class));
                                AppManager.getAppManager().finishActivity(AppManager.getAppManager().currentActivity().getClass());

                            }
                    } else if (BlueCmdDefine.MODE_GALLERY.equals(intent.getStringExtra("bluedata"))) {
                        simpleName = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                        EvoTrace.e("blue", "simpleName =" + simpleName);
                        if (!"aty_theme_gallery".equals(simpleName)) {
                            getApp().get_screenSettings_instance().setThemetype(2);
                            try {
                                getApp().getHelper().get_ScreenSettingsDao().update(getApp().get_screenSettings_instance());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(BaseUi.this, aty_theme_gallery.class));
                            AppManager.getAppManager().finishActivity(AppManager.getAppManager().currentActivity().getClass());
                        }
                    } else if (BlueCmdDefine.MODE_CLOUD.equals(intent.getStringExtra("bluedata"))) {
                        simpleName = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                        EvoTrace.e("blue", "simpleName =" + simpleName);
                        if (!"aty_theme_3d".equals(simpleName)) {
                            getApp().get_screenSettings_instance().setThemetype(3);
                            try {
                                getApp().getHelper().get_ScreenSettingsDao().update(getApp().get_screenSettings_instance());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(BaseUi.this, aty_theme_3d.class));
                            AppManager.getAppManager().finishActivity(AppManager.getAppManager().currentActivity().getClass());
                        }
                    } else if (BlueCmdDefine.MODE_SHOP.equals(intent.getStringExtra("bluedata"))) {
                        simpleName = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                        EvoTrace.e("blue", "simpleName =" + simpleName);
                        if (!"aty_theme_shop".equals(simpleName)) {
                            getApp().get_screenSettings_instance().setThemetype(4);
                            try {
                                getApp().getHelper().get_ScreenSettingsDao().update(getApp().get_screenSettings_instance());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            startActivity(new Intent(BaseUi.this, aty_theme_shop.class));
                            AppManager.getAppManager().finishActivity(AppManager.getAppManager().currentActivity().getClass());
                        }
                    }
                }
            }
            else if((BlueActionDefine.ACTION_TELNET_CMD_MAKE_DRINK.equals(action)))
            {
                int pid = Integer.valueOf(intent.getStringExtra("bluedata"));
                //获取菜单号，（检测合法性）开始制作饮料
                DrinkMenuButton currentitem = getCurrentbtn(pid);
                if(currentitem!=null) {
                    getApp().set_drinkMenuButton(currentitem);
                    startActivity(new Intent(BaseUi.this, aty_customer_ui_12.class));
                    //getApp().addCmdQueue((new CmdMakeDrink()).buildMakeDrinkCmd(getApp().get_drinkMenuButton().getPid(), CmdMakeDrink.OPERATE_START, 3, 3, 3, 3, 3, 0));

                }
            }
        }
    };
    private DrinkMenuButton getCurrentbtn(int pid)
    {
        DrinkMenuButton ret =null;
        if(drinkMenuButtonList!=null && drinkMenuButtonList.size()>0)
        {
            for (DrinkMenuButton drinkMenuButton:drinkMenuButtonList)
            {
                if(drinkMenuButton.getPid() == pid) {
                    ret = drinkMenuButton;
                    break;
                }
            }
        }
        return ret;
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(IsHide)
        {
            profile.setVisibility(View.GONE);
        }else {
            if (getApp().get_screenSettings_instance().getShowprofile() == 1) {
                profile.setVisibility(View.VISIBLE);
            } else {
                profile.setVisibility(View.GONE);
            }
        }
        isActived = true;
        initver();
        if(getApp().ismainpagereload())
        {
            setupview();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        profile.setVisibility(View.GONE);
        handler.removeMessages(WATH);
        isActived = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timeThread!=null)
        {
            if(timeThread.isAlive())
            {
                tmflag = false;
            }
        }
        unregisterReceiver(receiver);
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_base_ui);
        btn_info = findViewById(R.id.btn_info);
        tv_time = findViewById(R.id.tv_time);
        tv_comp_name = findViewById(R.id.tv_comp_name);
        rlyt_bg = findViewById(R.id.rlyt_bg);
        profile = findViewById(R.id.profile);
        menu_fav = findViewById(R.id.menu_fav);
        lang = findViewById(R.id.lang);
        image_banner= findViewById(R.id.image_banner);
        top1 = findViewById(R.id.top1);
        top2 = findViewById(R.id.top2);
        top3 = findViewById(R.id.top3);
        setupview();
        InitFunctionLayout();
        StartTmClk();
    }

    private Languageitem languageitem;
    private List<BeverageBasic> basics_top5;
    @Override
    public void InitData() {
        super.InitData();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        personFactoryDao = new PersonFactoryDao(this,getApp());
        screenFactoryDao = new ScreenFactoryDao(this,getApp());
        paymentDao= new PaymentDao(this,getApp());
        _paymentSetting = paymentDao.Query();
        if(_paymentSetting==null) {
            _paymentSetting = new PaymentSetting(1);
            paymentDao.Update(_paymentSetting);
        }
        languageitem = screenFactoryDao.getLanguageitemDao().query();
        initver();
        basics_top5 = beverageFactoryDao.getBeverageCountDao().getTopFive();

    }

    public boolean ispayment()
    {
        return  _paymentSetting.isPaymentEnable();
    }
    private void initver() {
        drinkMenuButtonList.clear();
        personItems.clear();

        List<DrinkMenuButton> tmp = beverageFactoryDao.getDrinkIconItems(getApp().getCurrent_language());

        if(tmp!=null &&tmp.size()>0)
        {
            drinkMenuButtonList.addAll(tmp);
        }
        personItems.add((PersonItem) new PersonItem("Add new Person","",0,"").setTop(true).setBaseIndexTag("+"));
        List<PersonItem> ptmp = personFactoryDao.getPersonItemDao().quryallRecord();
        if(ptmp!=null && ptmp.size()>0)
        {
            for (PersonItem pitem:ptmp)
            {
                personItems.add(pitem);
            }
        }
    }

    private void refreshlanguage(int lang)
    {
        drinkMenuButtonList.clear();
        personItems.clear();
        getApp().setCurrent_language(lang);
        List<DrinkMenuButton> tmp = beverageFactoryDao.getDrinkIconItems(lang);
        if(tmp!=null &&tmp.size()>0)
        {
            drinkMenuButtonList.addAll(tmp);
        }
        personItems.add((PersonItem) new PersonItem("Add new Person","",0,"").setTop(true).setBaseIndexTag("+"));
        List<PersonItem> ptmp = personFactoryDao.getPersonItemDao().quryallRecord();
        if(ptmp!=null && ptmp.size()>0)
        {
            for (PersonItem pitem:ptmp)
            {
                personItems.add(pitem);
            }
        }
    }
    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_info.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_comp_name.setOnClickListener(this);
        lang.setOnClickListener(this);
        tv_time.setFormat24Hour("MM/dd HH:mm");
        profile.setOnClickListener(this);
    }
    private WarningPopWindow warningPopWindow=null;
    public void galleryfunction()
    {

    }
    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.profile:
                if (officePopWindow == null) {
                    initver();
                    officePopWindow = new OfficePopWindow(BaseUi.this, personItems, personFactoryDao, beverageFactoryDao);
                    officePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            officePopWindow = null;
                            galleryfunction();
                        }
                    });
                    officePopWindow.SetOnQuickStartClick(new OfficePopWindow.OnQuickStartClick() {
                        @Override
                        public void Start(PersonDrink p) {
                            DrinkMenuButton crt = FindforPid(p.getPid());
                            if(crt!=null) {
                                getApp().set_drinkMenuButton(crt);
                                startActivity(new Intent(BaseUi.this, aty_customer_ui_12.class));
                            }
                        }
                    });
                }
                officePopWindow.show(tv_time);
                break;
            case R.id.lang:
                if(languagePopWindow==null) {
                    languagePopWindow = new LanguagePopWindow(this, screenFactoryDao.getLanguageitemDao().query().getLanguageList());
                    languagePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            languagePopWindow =null;
                        }
                    });
                    languagePopWindow.getLanguageAdapter().setOnFileSelect(new LanguageAdapter.OnFileSelect() {
                        @Override
                        public void FileSelect(int id) {
                            // TODO: 2018/7/2 change the language dismiss the popwin
                            if(getApp().getCurrent_language()!=id) {
                                getApp().setCurrent_language(id);
                                if (languagechanged != null) {
                                    languagechanged.updated();
                                }
                            }
                            languagePopWindow.dismiss();
                        }
                    });
                }
                languagePopWindow.show(v);
                break;
            case R.id.btn_info:
                if(warningPopWindow == null)
                {
                    warningPopWindow = new WarningPopWindow(this,warningAdapter);
                    warningPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            warningPopWindow = null;
                        }
                    });
                }
                warningPopWindow.show(v);
                break;
            case R.id.tv_time:
                startActivity(new Intent(BaseUi.this, aty_service_main.class));
                break;
            case R.id.tv_comp_name:
                startActivity(new Intent(BaseUi.this, aty_psw_input.class));
                //startActivity(new Intent(BaseUi.this, ErrorBlockUi.class));
                break;
        }
    }

    @Override
    public void InitFunctionLayout() {

    }

    private void setupview()
    {
        String path = getApp().get_screenSettings_instance().getBannerpath();
        if(path!=null && !"".equals(path)) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 200, 100);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            if (bitmap != null)
                image_banner.setImageBitmap(bitmap);
            else
                image_banner.setImageBitmap(null);
        }
        else {
            image_banner.setImageBitmap(null);
        }
        menu_fav.setVisibility(getApp().get_screenSettings_instance().getShowfavourite()==1?View.VISIBLE:View.GONE);
        lang.setVisibility(getApp().get_screenSettings_instance().getShowlanguage()==1?View.VISIBLE:View.GONE);
        if(languageitem==null)
            lang.setVisibility(View.GONE);

        if(basics_top5!=null && basics_top5.size()>0)
        {
            if(basics_top5.size()>=1)
                top1.setTitle("Top1 : "+basics_top5.get(0).getName());

            if(basics_top5.size()>=2)
                top2.setTitle("Top2 : "+basics_top5.get(1).getName());
            if(basics_top5.size()>=3)
                top3.setTitle("Top3 : "+basics_top5.get(2).getName());

        }else
        {
            menu_fav.setVisibility(View.GONE);
        }
    }
    private void StartTmClk()
    {
        if(timeThread!=null)
        {
            return;
        }
            timeThread = new TimeThread();
            timeThread.start();
    }

    public class TimeThread extends Thread {
        Message tmrmsg;
        @Override
        public void run() {
            do {
                try {
                    tmrmsg = new Message();
                    tmrmsg.what = 1000;
                    mHandler.sendMessage(tmrmsg);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (tmflag);
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    //recipeWarning();
                    break;
                case 1002:
                    /*getApp().bindAllService();
                    Intent intent = new Intent(CoffeeActivity.this, WizardLanguage.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    // 刷一次界面，要不然第一次运行没有数据
                    reloadIcons();
                    if (progressDialog != null) {
                        progressDialog.dismiss();
                    }*/
                case 1004: //gengxin warning biao
                    if(getApp().getallMachineWarnList().size()>0)
                        btn_info.setVisibility(View.VISIBLE);
                    else
                        btn_info.setVisibility(View.GONE);
                    btn_info.setNumber(getApp().getallMachineWarnList().size()+"");
                    warningAdapter.setGridItems(getApp().getallMachineWarnList());
                    break;
                default:
                    break;
            }
        }
    };


    private DrinkMenuButton FindforPid(int pid)
    {
        for (DrinkMenuButton item :drinkMenuButtonList)
        {
            if(item.getPid() == pid)
                return item;
        }
        return null;
    }
    public void addDragView()
    {
       /* floatBallView = new FloatBallView(this);
        FloatUtil.showFloatView(floatBallView, Gravity.CENTER | Gravity.BOTTOM, WindowManager.LayoutParams.TYPE_TOAST,new Point(0,0), null, true);
        EvoTrace.e("blue","addDragView");
        floatBallView.getBall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (officePopWindow == null) {
                    initver();
                    officePopWindow = new OfficePopWindow(BaseUi.this, personItems, personFactoryDao, beverageFactoryDao);
                    officePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            officePopWindow = null;
                        }
                    });
                    officePopWindow.SetOnQuickStartClick(new OfficePopWindow.OnQuickStartClick() {
                        @Override
                        public void Start(PersonDrink p) {
                            DrinkMenuButton crt = FindforPid(p.getPid());
                            if(crt!=null) {
                                getApp().set_drinkMenuButton(crt);
                                startActivity(new Intent(BaseUi.this, aty_customer_ui_12.class));
                            }
                        }
                    });
                }
                officePopWindow.show(tv_time);

            }
        });*/
    }


}
