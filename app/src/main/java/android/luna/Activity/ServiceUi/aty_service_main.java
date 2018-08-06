package android.luna.Activity.ServiceUi;


import android.app.Fragment;
import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.CustomerUI.Gallery.aty_theme_gallery;
import android.luna.Activity.CustomerUI.Normal.aty_theme_normal;
import android.luna.Activity.CustomerUI.Shopping.aty_theme_shop;
import android.luna.Activity.CustomerUI.ThreeDCloud.aty_theme_3d;
import android.luna.Activity.ServiceUi.fragment.CleanFragment;
import android.luna.Activity.ServiceUi.fragment.DrinkFragment;
import android.luna.Activity.ServiceUi.fragment.InfoFragment;
import android.luna.Activity.ServiceUi.fragment.SettingFragment;
import android.luna.Activity.ServiceUi.fragment.TestFragment;
import android.luna.Data.DAO.ScreenFactoryDao;
import android.luna.Data.module.LogRecord;
import android.luna.Data.module.ScreenSettings;
import android.luna.ViewUi.bottombar.BottomBar;
import android.luna.ViewUi.bottombar.OnTabSelectListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;


import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class aty_service_main extends BaseActivity {
    private BottomBar bottomBar;
    private Fragment tab_cup;
    private Fragment tab_test;
    private Fragment tab_log;
    private Fragment tab_setting;
    private Fragment tab_clean;
    private CremApp app;
    private ScreenSettings _screenSettings =null;
    private ScreenFactoryDao _screenFactoryDao =null;
    private int lasttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _screenFactoryDao = new ScreenFactoryDao(this,getApp());
        _screenSettings = _screenFactoryDao.getScreenSettingDao().query();
        if(_screenSettings == null)
            _screenSettings = new ScreenSettings();
        lasttype = _screenSettings.getThemetype();
       /* LangItem tmp;
        List<LangItem>  langItemList =new ArrayList<>(100);
        for (int i=0;i<100;i++)
        {
            tmp = new LangItem(String.format("TXT_MSG_%d",i),String.format("TXT_VALUE_%d",i+2));
            langItemList.add(tmp);
        }
        //LangLocalHelper.UpdateLangfile(0,langItemList);


        langItemList = LangLocalHelper.getlanglistfromfile(1);

        EvoTrace.e("aa","count = "+langItemList.size());
        EvoTrace.e("aa",langItemList.toString());*/

        app = (CremApp)getApplication();
        setContentView(R.layout.aty_service_main);
        bottomBar = findViewById(R.id.bottomBar);

        if(app.getAuth_level() == Constant.AUTH_FIRSTLINE)
        {
            bottomBar.setItems(R.xml.menu_firstline);
        }
        else if(app.getAuth_level() == Constant.AUTH_SECONDLINE)
        {
            bottomBar.setItems(R.xml.menu_secondline);
        }
        else if (app.getAuth_level() == Constant.AUTH_SERVICE)
        {
            bottomBar.setItems(R.xml.menu_service);
        }
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_cup)
                {
                    if(tab_cup ==null)
                    {
                        tab_cup = new DrinkFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.contentContainer,tab_cup).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_cup",null));
                }
                else if(tabId == R.id.tab_test)
                {
                    if(tab_test ==null)
                    {
                        tab_test = new TestFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.contentContainer,tab_test).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_test",null));
                }
                else if(tabId == R.id.tab_log)
                {
                    if(tab_log ==null)
                    {
                        tab_log = new InfoFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.contentContainer,tab_log).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_log",null));
                }
                else if(tabId == R.id.tab_setting)
                {
                    if(tab_setting ==null)
                    {
                        tab_setting = new SettingFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.contentContainer,tab_setting).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_setting",null));

                }
                else if(tabId == R.id.tab_clean)
                {
                    if(tab_clean ==null)
                    {
                        tab_clean = new CleanFragment();
                    }
                    getFragmentManager().beginTransaction().replace(R.id.contentContainer,tab_clean).commit();
                    app.SetLog(new LogRecord(LogRecord.COLOR_NOTE,"onTabSelected:tab_clean",null));

                }
                else if(tabId == R.id.tab_home) {
                    AppManager.getAppManager().finishActivity(aty_service_main.this);
                    _screenSettings = _screenFactoryDao.getScreenSettingDao().query();
                    int type = _screenSettings.getThemetype();
                    if(lasttype!=type) {
                        try {
                            if(lasttype == 1)
                                AppManager.getAppManager().finishActivity(aty_theme_normal.class);
                            else if(lasttype == 2)
                                AppManager.getAppManager().finishActivity(aty_theme_gallery.class);
                            else if(lasttype == 3)
                                AppManager.getAppManager().finishActivity(aty_theme_3d.class);
                            else if(lasttype == 4)
                                AppManager.getAppManager().finishActivity(aty_theme_shop.class);
                        }
                        catch (Exception e)
                        {

                        }
                        //1 normal 2 gallery 3 cloud 4 shop
                        if (type == 1) {
                            startActivity(new Intent(aty_service_main.this, aty_theme_normal.class));
                        } else if (type == 2) {
                            startActivity(new Intent(aty_service_main.this, aty_theme_gallery.class));
                        } else if (type == 3) {
                            startActivity(new Intent(aty_service_main.this, aty_theme_3d.class));
                        } else if (type == 4) {
                            startActivity(new Intent(aty_service_main.this, aty_theme_shop.class));
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
