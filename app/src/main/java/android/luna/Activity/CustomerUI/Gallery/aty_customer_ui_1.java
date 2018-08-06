package android.luna.Activity.CustomerUI.Gallery;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.CustomerUI.Gallery.Adapter.MachineWarningAdapter;
import android.luna.Activity.CustomerUI.Normal.aty_customer_ui_3;
import android.luna.Activity.CustomerUI.ThreeDCloud.aty_customer_ui_2;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.Warning.MachineWarn;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.FloatActionMenu.FloatingActionsMenu;
import android.luna.ViewUi.HintLable.IconNumberView;
import android.luna.ViewUi.SquareMenu.OnMenuClickListener;
import android.luna.ViewUi.SquareMenu.SquareMenu;
import android.luna.ViewUi.ViewPager.MyAdapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2017/12/28.
 */

public class aty_customer_ui_1 extends BaseActivity implements View.OnClickListener {
    private static String Tag = "aty_customer_ui_1";
    private RecyclerView mRecycleView;
    private PopupWindow mPopWindow;
    private TextView tv_time;
    private ListView lv_warn;
    private boolean showmPopWindow;
    private MyAdapter mAdapter;
    private List<DrinkMenuButton> drinkMenuButtonList=new ArrayList<>(20);
    private int mScreenWidth;
    private  final float MIN_SCALE = 0.8f;
    private  final float MAX_SCALE = 1.5f;


    private float Screen3Items =0.333333f;
    private float Screen5Items = 0.2f;




    private LinearLayoutManager mLinearLayoutManager;
    private int mMinWidth;
    private int mMaxWidth;
    private IconNumberView btn_info;
    private Timer timer=null;
    private TimeThread timeThread=null;
    private boolean tmflag = true;
    private  RelativeLayout rlyt_bg;

    private MachineWarningAdapter warningAdapter;
    private BeverageFactoryDao beverageFactoryDao=null;

    private void StartTmClk()
    {
        if(timeThread!=null)
        {
            return;
        }
        if (timeThread == null) {
            timeThread = new TimeThread();
            timeThread.start();
        }
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
                    refreshTime();
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
                    btn_info.setNumber(getApp().getallMachineWarnList().size()+"");
                    warningAdapter.setGridItems(getApp().getallMachineWarnList());
                    break;
                default:
                    break;
            }
        }
    };

    SimpleDateFormat dayformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String sysTimeStr ="";
    private void refreshTime() {
         sysTimeStr = dayformat.format(new Date());
         tv_time.setText(sysTimeStr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //// TODO: 2018/2/6 zhuce jguangbo jieshouqi
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_WARNING_NOTIFICATION);
        registerReceiver(m_receiver, filter);
        warningAdapter = new MachineWarningAdapter(getApp().getallMachineWarnList(),this);
    }
    private BroadcastReceiver m_receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Constant.ACTION_WARNING_NOTIFICATION.equals(action))
            {
                //// TODO: 2018/2/6 gengxin warning jiemian
                mHandler.sendEmptyMessage(1004);
            }
        }
    };

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_customer_ui_1);
        //// TODO: 2017/12/28 中间的画廊部分
        mRecycleView = findViewById(R.id.rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mMinWidth = (int) (mScreenWidth * Screen5Items);
        mMaxWidth = mMinWidth;//mScreenWidth - 2* mMinWidth;
        //// TODO: 2017/12/28 语言选择

        btn_info = findViewById(R.id.btn_info);
        tv_time = findViewById(R.id.tv_time);
        rlyt_bg = findViewById(R.id.rlyt_bg);
    }

    private void refreshUi()
    {
        if(getApp().ismainpagereload())
        {
            //// TODO: 2018/2/28 panduan layout type jueding
            mMinWidth = (int) (mScreenWidth * Screen5Items);
            mMaxWidth = mMinWidth;//mScreenWidth - 2* mMinWidth;
            List<DrinkMenuButton> tmp = beverageFactoryDao.getDrinkIconItems(getApp().getCurrent_language());
            if(tmp!=null &&tmp.size()>0) {
                drinkMenuButtonList.clear();
                drinkMenuButtonList.add(new DrinkMenuButton(""));
                drinkMenuButtonList.add(new DrinkMenuButton(""));
                drinkMenuButtonList.addAll(tmp);
                drinkMenuButtonList.add(new DrinkMenuButton(""));
                drinkMenuButtonList.add(new DrinkMenuButton(""));
            }
            mAdapter.setDrinkMenuButtonList(drinkMenuButtonList);
            mAdapter.notifyDataSetChanged();
            getApp().setIsmainpagereload(false);
        }
    }
    @Override
    public void InitData() {
        super.InitData();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        getApp().setCurrent_language(beverageFactoryDao.getBeverageNameDao().getlocalinfo());
        List<DrinkMenuButton> tmp = beverageFactoryDao.getDrinkIconItems(getApp().getCurrent_language());
        if(tmp!=null &&tmp.size()>0) {
            drinkMenuButtonList = new ArrayList<>();
            drinkMenuButtonList.add(new DrinkMenuButton(""));
            drinkMenuButtonList.add(new DrinkMenuButton(""));
            drinkMenuButtonList.addAll(tmp);
            drinkMenuButtonList.add(new DrinkMenuButton(""));
            drinkMenuButtonList.add(new DrinkMenuButton(""));
        }
        if (timer == null) {
            timer = new Timer();
        }
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        mAdapter = new MyAdapter(drinkMenuButtonList,this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(mOnScrollListener);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                //// TODO: 2017/12/28  need add the function for drink button
                if(drinkMenuButtonList.get(position).getName()!="") {

                    getApp().set_drinkMenuButton(drinkMenuButtonList.get(position));
                    startActivity(new Intent(aty_customer_ui_1.this, aty_customer_ui_11.class));
                }
            }
        });

        btn_info.setOnClickListener(this);
        findViewById(R.id.actionbtn1).setOnClickListener(this);
        findViewById(R.id.actionbtn2).setOnClickListener(this);
        findViewById(R.id.actionbtn3).setOnClickListener(this);
        findViewById(R.id.actionbtn4).setOnClickListener(this);


        findViewById(R.id.lang1).setOnClickListener(this);
        findViewById(R.id.lang4).setOnClickListener(this);


        tv_time.setOnClickListener(this);
        StartTmClk();
    }




    private int currentcenterpostion=0;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int childCount = recyclerView.getChildCount();
            int maxpostion=0;
            for (int i = 0; i < childCount; i++) {
                RelativeLayout child = (RelativeLayout) recyclerView.getChildAt(i);
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                lp.rightMargin = 15;
                lp.height = (int) (getResources().getDisplayMetrics().heightPixels *3.5/8);
                int left = child.getLeft();
                int right = mScreenWidth - child.getRight();
                final float percent = left < 0 || right < 0 ? 0 : Math.min(left, right) * 1f / Math.max(left, right);
                float scaleFactor = MIN_SCALE + Math.abs(percent) * (MAX_SCALE - MIN_SCALE);
                int width = (int) (mMinWidth + Math.abs(percent) * (mMaxWidth - mMinWidth));
                lp.width = width;
                lp.topMargin = (recyclerView.getHeight() - lp.height)/2;
                child.setLayoutParams(lp);
                child.setScaleY(scaleFactor);
                child.setScaleX(scaleFactor);
                if(scaleFactor>1.22f)
                {
                    maxpostion = (int)child.getTag();
                }
            }
            if(maxpostion!=0)
            ShowBackground(maxpostion);
        }
    };

    private void ShowBackground(int a)
    {
        if(a == currentcenterpostion)
            return;
        //// TODO: 2018/1/3 shuaxin beijingtu
        String path = drinkMenuButtonList.get(a).getBgPath();
        if(path!=null && !path.equals("")) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, rlyt_bg.getWidth(), rlyt_bg.getHeight());
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            if (bitmap != null)
                rlyt_bg.setBackground(new BitmapDrawable(getResources(), bitmap));
            else
                rlyt_bg.setBackground(null);
        }
        else
        {
            rlyt_bg.setBackground(null);
        }
    }
    private void showPopupWindow(View parent,int type) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
            if (type == Constant.PopType.ERROR) {
                View contentView = LayoutInflater.from(aty_customer_ui_1.this).inflate(R.layout.pop_win_info, null);
                lv_warn = contentView.findViewById(R.id.lv_warn);

                lv_warn.setAdapter(warningAdapter);
                mPopWindow = new PopupWindow(contentView);
                mPopWindow.setAnimationStyle(R.style.popup_window_anim);
                mPopWindow.setWidth(600);
                mPopWindow.setHeight(600);
                mPopWindow.showAtLocation(parent, Gravity.CENTER, 0, -30);
            }
            else if(type == Constant.PopType.SERVICE_PASSWORD)
            {
                View contentView = LayoutInflater.from(aty_customer_ui_1.this).inflate(R.layout.pop_win_lock, null);
                mPopWindow = new PopupWindow(contentView);
                mPopWindow.setAnimationStyle(R.style.popup_window_anim);
                mPopWindow.setWidth(600);
                mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                mPopWindow.showAtLocation(parent, Gravity.CENTER, 0, -30);
                final PatternLockView mPatternLockView =  contentView.findViewById(R.id.pattern_lock_view);
                final TextView tv_pswinfo = contentView.findViewById(R.id.tv_pswinfo);
                mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                    @Override
                    public void onStarted() {
                        Log.d(getClass().getName(), "Pattern drawing started");
                        tv_pswinfo.setText("Welcome");
                    }

                    @Override
                    public void onProgress(List<PatternLockView.Dot> progressPattern) {
                        Log.d(getClass().getName(), "Pattern progress: " +
                                PatternLockUtils.patternToString(mPatternLockView, progressPattern));
                    }

                    @Override
                    public void onComplete(List<PatternLockView.Dot> pattern) {
                        Log.d(getClass().getName(), "Pattern complete: " +
                                PatternLockUtils.patternToString(mPatternLockView, pattern));
                        if (PatternLockUtils.patternToString(mPatternLockView, pattern).equals("0124678"))
                        {
                            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                            tv_pswinfo.setText("OK");
                            DismissPopWindow();
                        }
                        else
                        {
                            mPatternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                            tv_pswinfo.setText("Incorrect");
                        }
                    }

                    @Override
                    public void onCleared() {
                        Log.d(getClass().getName(), "Pattern has been cleared");
                    }
                });
            }
            showmPopWindow = true;
        }
    }
    private void DismissPopWindow()
    {
        mPopWindow.dismiss();
        showmPopWindow =false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(m_receiver);
        if(timeThread!=null)
        {
            if(timeThread.isAlive())
            {
                tmflag = false;
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.lang1:
                getApp().setIsmainpagereload(true);
                getApp().setCurrent_language(1);
                ((FloatingActionsMenu)findViewById(R.id.menu_language)).collapse();
                refreshUi();
                break;
            case R.id.lang4:
                getApp().setIsmainpagereload(true);
                getApp().setCurrent_language(4);
                refreshUi();
                ((FloatingActionsMenu)findViewById(R.id.menu_language)).collapse();
                break;
            case R.id.btn_info:
                showPopupWindow(view, Constant.PopType.ERROR);
                break;
            case R.id.actionbtn1:
                startActivity(new Intent(aty_customer_ui_1.this, aty_customer_ui_2.class));
                AppManager.getAppManager().finishActivity(aty_customer_ui_1.this);
                break;
            case R.id.actionbtn2:
                break;
            case R.id.actionbtn3:
                startActivity(new Intent(aty_customer_ui_1.this, aty_customer_ui_3.class));
                AppManager.getAppManager().finishActivity(aty_customer_ui_1.this);
                break;
            case R.id.actionbtn4:
                showPopupWindow(view, Constant.PopType.SERVICE_PASSWORD);
                break;
            case R.id.tv_time:
                getApp().WarningHelpper(new MachineWarn(0, RandomUtils.randInt(20),1),Constant.WARN_ADD);
                break;
        }
    }
}
