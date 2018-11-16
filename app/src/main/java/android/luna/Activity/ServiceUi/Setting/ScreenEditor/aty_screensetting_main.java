package android.luna.Activity.ServiceUi.Setting.ScreenEditor;

import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;

import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.PicSelector.aty_uiRes_selector;
import android.luna.Data.DAO.ScreenFactoryDao;
import android.luna.Data.module.Languageitem;
import android.luna.Data.module.ScreenSettings;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.ColorPicker.ColorPickerDialog;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemTextView1;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/3/1.
 */

public class aty_screensetting_main extends BaseActivity implements View.OnClickListener{
    private Button btn_back;
    private ColorPickerDialog colorPickerDialog;
    private SettingItemDropDown themeItem,screenSaverTimeItem;
    private SettingItemTextView2 txtfontitem;
    private ScreenFactoryDao _screenFactoryDao =null;
    private ScreenSettings _screenSettings =null;
    private Languageitem _languageitem;
    private SettingItemTextView1 backgroundPictureItem,brandPictureItem,screenSaverImageItem,sleepPictureItem,logoPictureItem,languageItem,screenLayoutItem;
    private LinearLayout function_normal,function_gallery,function_could;
    private SettingItemCheckBox logoPicturecheckItem ,faveritecheckItem,languagecheckItem,profilecheckItem;
    private MaterialDialog _languageitemdlg;
    private void refreshUi()
    {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(txtfontitem.getTextView2().getLayoutParams());
        lp.setMargins(0,5,30,5);
        txtfontitem.getTextView2().setLayoutParams(lp);
        txtfontitem.getTextView2().setBackgroundColor(_screenSettings.getTextcolor());

        String key = _screenSettings.getThemetype()+"";
        String themetype = themeItem.getItemAndValues().get(key);
        if(themetype!=null) {
            themeItem.setSelItem(key, themetype);
            themeItem.refreshData(0);
        }

         key = _screenSettings.getScreensaverflag()+"";
         themetype = screenSaverTimeItem.getItemAndValues().get(key);
        if(themetype!=null) {
            screenSaverTimeItem.setSelItem(key, themetype);
            screenSaverTimeItem.refreshData(0);
        }
        logoPicturecheckItem.setChecked(_screenSettings.getLogoflag() == 1);
        if(_screenSettings.getLogoflag() == 1)
            logoPictureItem.setVisibility(View.VISIBLE);
        else
            logoPictureItem.setVisibility(View.GONE);
        faveritecheckItem.setChecked(_screenSettings.getShowfavourite() == 1);

        languagecheckItem.setChecked(_screenSettings.getShowlanguage() == 1);
        if(_screenSettings.getShowlanguage() == 1)
            languageItem.setVisibility(View.VISIBLE);
        else
            languageItem.setVisibility(View.GONE);
        profilecheckItem.setChecked(_screenSettings.getShowprofile() == 1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshUi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_screensetting_main);
        btn_back = findViewById(R.id.btn_back);
        txtfontitem = findViewById(R.id.txtfontitem);
        themeItem= findViewById(R.id.themeItem);
        screenSaverTimeItem= findViewById(R.id.screenSaverTimeItem);

        function_normal= findViewById(R.id.function_normal);
        function_gallery= findViewById(R.id.function_gallery);
        function_could= findViewById(R.id.function_could);

        backgroundPictureItem= findViewById(R.id.backgroundPictureItem);
        brandPictureItem= findViewById(R.id.brandPictureItem);
        screenSaverImageItem= findViewById(R.id.screenSaverImageItem);
        sleepPictureItem= findViewById(R.id.sleepPictureItem);
        logoPictureItem= findViewById(R.id.logoPictureItem);
        languageItem= findViewById(R.id.languageItem);

        logoPicturecheckItem= findViewById(R.id.logoPicturecheckItem);
        faveritecheckItem= findViewById(R.id.faveritecheckItem);
        languagecheckItem= findViewById(R.id.languagecheckItem);
        profilecheckItem= findViewById(R.id.profilecheckItem);

        screenLayoutItem= findViewById(R.id.screenLayoutItem);
    }

    @Override
    public void InitData() {
        super.InitData();
        _screenFactoryDao = new ScreenFactoryDao(this,getApp());
        _screenSettings = _screenFactoryDao.getScreenSettingDao().query();
        if(_screenSettings == null)
            _screenSettings = new ScreenSettings();
        _languageitem = _screenFactoryDao.getLanguageitemDao().query();
        if(_languageitem == null)
            _languageitem = new Languageitem();
    }

    private void updateTheme(String key)
    {
        function_normal.setVisibility(View.GONE);
        function_gallery.setVisibility(View.GONE);
        function_could.setVisibility(View.GONE);
        switch (key)
        {
            case "1":
                function_normal.setVisibility(View.VISIBLE);
                break;
            case "2":
                function_gallery.setVisibility(View.VISIBLE);
                break;
            case "3":
                function_could.setVisibility(View.VISIBLE);
                break;
        }
    }
    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_back.setOnClickListener(this);
        txtfontitem.setOnClickListener(this);
        backgroundPictureItem.setOnClickListener(this);
        brandPictureItem.setOnClickListener(this);
        screenSaverImageItem.setOnClickListener(this);
        sleepPictureItem.setOnClickListener(this);
        logoPictureItem.setOnClickListener(this);
        languageItem.setOnClickListener(this);
        screenLayoutItem.setOnClickListener(this);

        themeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = themeItem.getItemAndValues().get(key);
                themeItem.setSelItem(key, name);
                _screenSettings.setThemetype(Integer.valueOf(key));
                updateTheme(key);
                _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
            }
        });

        screenSaverTimeItem.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = screenSaverTimeItem.getItemAndValues().get(key);
                screenSaverTimeItem.setSelItem(key, name);
                _screenSettings.setScreensaverflag(Integer.valueOf(key));
                _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
            }
        });

        logoPicturecheckItem.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _screenSettings.setLogoflag(logoPicturecheckItem.getCheckBox().isChecked() ? 1 : 0);
                _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
                if(logoPicturecheckItem.getCheckBox().isChecked())
                    logoPictureItem.setVisibility(View.VISIBLE);
                else
                    logoPictureItem.setVisibility(View.GONE);
            }
        });


        languagecheckItem.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _screenSettings.setShowlanguage(languagecheckItem.getCheckBox().isChecked() ? 1 : 0);
                _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
                if(languagecheckItem.getCheckBox().isChecked())
                    languageItem.setVisibility(View.VISIBLE);
                else
                    languageItem.setVisibility(View.GONE);
            }
        });
        faveritecheckItem.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _screenSettings.setShowfavourite(faveritecheckItem.getCheckBox().isChecked() ? 1 : 0);
                _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
            }
        });
        profilecheckItem.getCheckBox().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _screenSettings.setShowprofile(profilecheckItem.getCheckBox().isChecked() ? 1 : 0);
                _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
            }
        });


    }
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.screenLayoutItem:
                startActivity(new Intent(aty_screensetting_main.this,aty_drink_layout.class));
                break;
            case R.id.btn_back:
                getApp().setIsmainpagereload(true);
                getApp().updatescreenSettings(_screenSettings);
                AppManager.getAppManager().finishActivity(aty_screensetting_main.this);
                break;
            case R.id.txtfontitem:
                colorPickerDialog = new ColorPickerDialog(aty_screensetting_main.this,_screenSettings.getTextcolor(),"Color",new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color, boolean isdefault) {
                        _screenSettings.setTextcolor(color);
                        _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
                        colorPickerDialog.dismiss();
                        refreshUi();
                    }
                });
                colorPickerDialog.show();
                break;
            case R.id.backgroundPictureItem:
                intent = new Intent(aty_screensetting_main.this,aty_uiRes_selector.class);
                intent.putExtra("path", _screenSettings.getMainbkgpath());
                intent.putExtra("folder", FileHelper.PATH_MAIN_BKG);
                intent.putExtra("reqCode", Constant.REQ_SCR_BKG);
                startActivityForResult(intent, Constant.REQ_SCR_BKG);
                break;
            case R.id.brandPictureItem:
                intent = new Intent(aty_screensetting_main.this,aty_uiRes_selector.class);
                intent.putExtra("path", _screenSettings.getBannerpath());
                intent.putExtra("folder", FileHelper.PATH_BRAND);
                intent.putExtra("reqCode", Constant.REQ_SCR_BRAND);
                startActivityForResult(intent, Constant.REQ_SCR_BRAND);
                break;
            case R.id.screenSaverImageItem:
                intent = new Intent(aty_screensetting_main.this,aty_uiRes_selector.class);
                intent.putExtra("path", _screenSettings.getScreensaverpath());
                intent.putExtra("folder", FileHelper.PATH_SCREEN_SAVER);
                intent.putExtra("reqCode", Constant.REQ_SCR_SAVER);
                startActivityForResult(intent, Constant.REQ_SCR_SAVER);
                break;
            case R.id.sleepPictureItem:
                intent = new Intent(aty_screensetting_main.this,aty_uiRes_selector.class);
                intent.putExtra("path", _screenSettings.getEcopath());
                intent.putExtra("folder", FileHelper.PATH_SCREEN_SAVER);
                intent.putExtra("reqCode", Constant.REQ_SCR_ECO);
                startActivityForResult(intent, Constant.REQ_SCR_ECO);
                break;
            case R.id.logoPictureItem:
                intent = new Intent(aty_screensetting_main.this,aty_uiRes_selector.class);
                intent.putExtra("path", _screenSettings.getLogopath());
                intent.putExtra("folder", FileHelper.PATH_SCREEN_LOGO);
                intent.putExtra("reqCode", Constant.REQ_SCR_CUP);
                startActivityForResult(intent, Constant.REQ_SCR_CUP);
                break;
            case R.id.languageItem:
                showToast("languageItem");
                if(_languageitemdlg==null || !_languageitemdlg.isShowing()) {
                    _languageitemdlg = new MaterialDialog.Builder(aty_screensetting_main.this)
                            .title("Select Language")
                            .content("Please select the language you want to show on the Screen.")
                            .items(R.array.locallanguage)
                            .alwaysCallMultiChoiceCallback()
                            .itemsCallbackMultiChoice(_languageitem.getlangIndice(), new MaterialDialog.ListCallbackMultiChoice() {
                                @Override
                                public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                    boolean allowSelectionChange =
                                            which.length
                                                    >= 1; // selection count must stay above 1, the new (un)selection is included
                                    // in the which array
                                    if (!allowSelectionChange) {
                                        showToast(R.string.selection_min_limit_reached);
                                        return false;
                                    }
                                    StringBuilder str = new StringBuilder();
                                    for (int i = 0; i < which.length; i++) {
                                        if (i > 0) {
                                            str.append('\n');
                                        }
                                        str.append(which[i]);
                                        str.append(": ");
                                        str.append(text[i]);
                                    }
                                    _languageitem.SetallLanguage(which);
                                    showToast(str.toString());
                                    return allowSelectionChange;
                                }
                            })
                            .onNeutral(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    _languageitemdlg.dismiss();

                                }
                            })
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    _screenFactoryDao.getLanguageitemDao().update(_languageitem);
                                    _languageitemdlg.dismiss();

                                }
                            })
                            .positiveText("Go")
                            .neutralText("Later")
                            .autoDismiss(false)
                            .canceledOnTouchOutside(false)
                            .show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String path = data.getStringExtra("newpath");
        switch (requestCode)
        {
            case Constant.REQ_SCR_BKG:
                _screenSettings.setMainbkgpath(path);
                break;
            case Constant.REQ_SCR_BRAND:
                _screenSettings.setBannerpath(path);
                break;
            case Constant.REQ_SCR_SAVER:
                _screenSettings.setScreensaverpath(path);
                break;
            case Constant.REQ_SCR_ECO:
                _screenSettings.setEcopath(path);
                break;
            case Constant.REQ_SCR_CUP:
                _screenSettings.setLogopath(path);
                break;
        }
        _screenFactoryDao.getScreenSettingDao().update(_screenSettings);
    }
}
