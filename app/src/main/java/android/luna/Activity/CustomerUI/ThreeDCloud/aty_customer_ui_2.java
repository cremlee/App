package android.luna.Activity.CustomerUI.ThreeDCloud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_1;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_11;
import android.luna.Activity.CustomerUI.Normal.aty_customer_ui_3;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.ViewUi.HintLable.IconNumberView;
import android.luna.ViewUi.threeDCloudUi.TagCloudView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2017/12/28.
 */

public class aty_customer_ui_2 extends BaseActivity implements View.OnClickListener {
    private static String Tag = "aty_customer_ui_2";
    private PopupWindow mPopWindow;
    private TextView tv_time;
    private boolean showmPopWindow;
    private List<DrinkMenuButton> drinkMenuButtonList=new ArrayList<>(20);
    private IconNumberView btn_info;
    private CouldAdapter couldAdapter;
    private TagCloudView tag_cloud;
    private Timer timer=null;
    private TimeThread timeThread=null;
    private boolean tmflag = true;
    private  RelativeLayout rlyt_bg;
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
        @Override
        public void run() {
            do {
                try {
                    Message msg = new Message();
                    msg.what = 1000;
                    mHandler.sendMessage(msg);
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
                    break;
                default:
                    break;
            }
        }
    };
    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String sysTimeStr = "";
    private void refreshTime() {
        sysTimeStr = format.format(new Date());
        tv_time.setText(sysTimeStr);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_customer_ui_2);
        //// TODO: 2017/12/28 中间的画廊部分
        tag_cloud = findViewById(R.id.tag_cloud);
        btn_info = findViewById(R.id.btn_info);
        tv_time = findViewById(R.id.tv_time);
        rlyt_bg = findViewById(R.id.rlyt_bg);
    }

    @Override
    public void InitData() {
        super.InitData();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        List<DrinkMenuButton> tmp = beverageFactoryDao.getDrinkIconItems(beverageFactoryDao.getBeverageNameDao().getlocalinfo());
        if(tmp!=null &&tmp.size()>0)
        {
            drinkMenuButtonList.addAll(tmp);
        }
        couldAdapter =new CouldAdapter(this,drinkMenuButtonList);
        if (timer == null) {
            timer = new Timer();
        }
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        tag_cloud.setAdapter(couldAdapter);
        tag_cloud.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                getApp().set_drinkMenuButton(drinkMenuButtonList.get(position));
                startActivity(new Intent(aty_customer_ui_2.this, aty_customer_ui_11.class));
            }
        });
        StartTmClk();
        btn_info.setOnClickListener(this);
        findViewById(R.id.actionbtn1).setOnClickListener(this);
        findViewById(R.id.actionbtn2).setOnClickListener(this);
        findViewById(R.id.actionbtn3).setOnClickListener(this);
    }

    private void showPopupWindow(View parent) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
            View contentView = LayoutInflater.from(aty_customer_ui_2.this).inflate(R.layout.pop_win_info, null);
            mPopWindow = new PopupWindow(contentView);
            mPopWindow.setAnimationStyle(R.style.popup_window_anim);
            mPopWindow.setWidth(400);
            mPopWindow.setHeight(400);
            mPopWindow.showAtLocation(parent, Gravity.CENTER,0,-30);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApp().unbindAllService();
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
            case R.id.btn_info:
                showPopupWindow(view);
                break;
            case R.id.actionbtn1:
                break;
            case R.id.actionbtn2:
                startActivity(new Intent(aty_customer_ui_2.this, aty_customer_ui_1.class));
                AppManager.getAppManager().finishActivity(aty_customer_ui_2.this);
                break;
            case R.id.actionbtn3:
                startActivity(new Intent(aty_customer_ui_2.this, aty_customer_ui_3.class));
                AppManager.getAppManager().finishActivity(aty_customer_ui_2.this);
                break;
        }
    }
}
