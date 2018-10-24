package android.luna.Activity.UpdateUi.fragment;

import android.app.Fragment;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.UpdateUi.Adpter.GridSpacingItemDecoration;
import android.luna.Activity.UpdateUi.Adpter.PicImportRvAdapter;
import android.luna.Activity.UpdateUi.PicImportAdapter;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.FilterBrewStepDao;
import android.luna.Data.module.DrinkName;
import android.luna.Data.module.FiterBrewStep;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientFilterBrewAdvance;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientWater;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.FileHelper;

import android.luna.Utils.Lang.LangConstant;
import android.luna.Utils.Lang.LangLocalHelper;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.rs232.Ack.AckQuery;
import android.luna.rs232.Cmd.CmdMakeBeverage;
import android.luna.rs232.Cmd.CmdMakeIngredient;
import android.luna.rs232.Cmd.CmdStorageMgt;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;
import is.arontibo.library.ElasticDownloadView;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class ImportFragment extends Fragment implements View.OnDragListener ,View.OnClickListener{
    private PopupWindow mPopWindow;
    private boolean showmPopWindow;
    private RecyclerView gdv_pic;
    private TextView dir_icon,dir_background,dir_dispense,dir_stroy,dir_gallery,dir_cuplogo,dir_banner,dir_saver,dir_pdf;
    private PicImportRvAdapter picImportAdapter=null;
    private RadioButton config_1,config_2,config_3,config_4;
    private CremApp app;
    private RadioGroup rgp_config;
    private FilterBrewStepDao filterBrewStepDao = null;
    private ElasticDownloadView elastic_download_view;
    private TextView tv_update_content;
    private Button btn_retry,btn_import,btn_update_lang;
    private String ConfigPath="";
    private BeverageFactoryDao beverageFactoryDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_import, container, false);
        app = (CremApp)getActivity().getApplication();
        filterBrewStepDao = new FilterBrewStepDao(getActivity(),app);
        beverageFactoryDao = new BeverageFactoryDao(getActivity(),app);
        InitView(view);
        return view;
    }
    private void InitView(View view) {
        btn_import = view.findViewById(R.id.btn_import);
        btn_import.setOnClickListener(this);
        btn_update_lang = view.findViewById(R.id.btn_update_lang);
        btn_update_lang.setOnClickListener(this);

        rgp_config = view.findViewById(R.id.rgp_config);
        rgp_config.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.config_1:
                        picImportAdapter.setMlistdata(FileHelper.getAllFile(new File(app.getUsbpath() + FileHelper.PATH_USB + "Configure 1/")));
                        ConfigPath = "Configure 1";
                        break;
                    case R.id.config_2:
                        picImportAdapter.setMlistdata(FileHelper.getAllFile(new File(app.getUsbpath() + FileHelper.PATH_USB + "Configure 2/")));
                        ConfigPath = "Configure 2";
                        break;
                    case R.id.config_3:
                        ConfigPath = "Configure 3";
                        picImportAdapter.setMlistdata(FileHelper.getAllFile(new File(app.getUsbpath() + FileHelper.PATH_USB + "Configure 3/")));
                        break;
                    case R.id.config_4:
                        picImportAdapter.setMlistdata(FileHelper.getAllFile(new File(app.getUsbpath() + FileHelper.PATH_USB + "Configure 4/")));
                        ConfigPath = "Configure 4";
                        break;
                }
            }
        });

        config_1 = view.findViewById(R.id.config_1);
        config_2 = view.findViewById(R.id.config_2);
        config_3 = view.findViewById(R.id.config_3);
        config_4 = view.findViewById(R.id.config_4);

        InitRadiogroup();
        gdv_pic = view.findViewById(R.id.gdv_pic);
        picImportAdapter = new PicImportRvAdapter(getActivity(), null);
        gdv_pic.setAdapter(picImportAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        //通过布局管理器可以控制条目排列的顺序 true反向显示 false正常显示(默认)
        gridLayoutManager.setReverseLayout(false);
        //设置RecycleView显示的方向是水平还是垂直
        //GridLayout.HORIZONTAL水平 GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数linearLayoutManager对象
        gdv_pic.setLayoutManager(gridLayoutManager);
        gdv_pic.addItemDecoration(new GridSpacingItemDecoration(4, 1, false));
        dir_icon = view.findViewById(R.id.dir_icon);
        dir_background = view.findViewById(R.id.dir_background);
        dir_dispense = view.findViewById(R.id.dir_dispense);
        dir_stroy = view.findViewById(R.id.dir_stroy);
        dir_gallery = view.findViewById(R.id.dir_gallery);
        dir_cuplogo = view.findViewById(R.id.dir_cuplogo);
        dir_banner = view.findViewById(R.id.dir_banner);
        dir_saver = view.findViewById(R.id.dir_saver);
        dir_pdf = view.findViewById(R.id.dir_pdf);
        dir_icon.setOnDragListener(this);
        dir_background.setOnDragListener(this);
        dir_dispense.setOnDragListener(this);
        dir_stroy.setOnDragListener(this);
        dir_gallery.setOnDragListener(this);
        dir_cuplogo.setOnDragListener(this);
        dir_banner.setOnDragListener(this);
        dir_saver.setOnDragListener(this);
        dir_pdf.setOnDragListener(this);
    }

    private void InitRadiogroup()
    {
        if(!app.getUsbpath().equals(""))
        {
            if(FileHelper.isDirExist(app.getUsbpath()+FileHelper.PATH_USB+"Configure 1"))
            {
                config_1.setEnabled(true);
            }else
            {
                config_1.setEnabled(false);
            }
            if(FileHelper.isDirExist(app.getUsbpath()+FileHelper.PATH_USB+"Configure 2"))
            {
                config_2.setEnabled(true);
            }else
            {
                config_2.setEnabled(false);
            }
            if(FileHelper.isDirExist(app.getUsbpath()+FileHelper.PATH_USB+"Configure 3/"))
            {
                config_3.setEnabled(true);
            }else
            {
                config_3.setEnabled(false);
            }
            if(FileHelper.isDirExist(app.getUsbpath()+FileHelper.PATH_USB+"Configure 4/"))
            {
                config_4.setEnabled(true);
            }else
            {
                config_4.setEnabled(false);
            }
        }else
        {
            config_1.setEnabled(false);
            config_2.setEnabled(false);
            config_3.setEnabled(false);
            config_4.setEnabled(false);
        }
    }

    public final static int FILE_MEDIA =1;
    public final static int FILE_PICTURE =2;
    public final static int FILE_GIF =3;
    public final static int FILE_VIDEO =4;
    public final static int FILE_DOCUMENT =5;

    private void showtoast(String string)
    {
        ((BaseActivity)getActivity()).showToast(string);
    }
    private void showPutfile2destfolder(final String scr, final String dest,int filetype)
    {

        switch ( filetype)
        {
            case FILE_MEDIA:
                if(!(scr.endsWith("jpg") || scr.endsWith("png") || scr.endsWith("mp4") || scr.endsWith("gif"))) {
                    showtoast("File type error!");
                    return;
                }
                break;
            case FILE_PICTURE:
                if(!(scr.endsWith("jpg") || scr.endsWith("png"))) {
                    showtoast("File type error!");
                    return;
                }
                break;
            case FILE_GIF:
                if(!scr.endsWith("gif"))
                {
                    showtoast("File type error!");
                    return;
                }
                break;
            case FILE_VIDEO:
                if(!scr.endsWith("mp4"))
                {
                    showtoast("File type error!");
                    return;
                }
                break;
            case FILE_DOCUMENT:
                if(!scr.endsWith("pdf"))
                {
                    showtoast("File type error!");
                    return;
                }
                break;
        }
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title("Copy files to pad")
                .content("Do you want to cop the files to pad?")
                .positiveText("agree")
                .positiveColor(getResources().getColor(R.color.green_grass))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       if(FileHelper.copyFile(scr,dest+scr.substring(scr.lastIndexOf("/")+1)))
                       {
                           ((BaseActivity)getActivity()).showTestToast("copy finished!");
                       }
                       else
                       {
                           ((BaseActivity)getActivity()).showTestToast("copy failed!");
                       }
                        dialog.dismiss();
                    }
                })
                .negativeText("later")
                .negativeColor(getResources().getColor(R.color.red_wine))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .canceledOnTouchOutside(false)
                .show();
    }
    @Override
    public boolean onDrag(View view, final DragEvent dragEvent) {
        int action = dragEvent.getAction();
        int id = view.getId();
        switch (action)
        {
            case DragEvent.ACTION_DROP:
                final String srcpath = dragEvent.getClipData().getDescription().getLabel().toString();
                switch (id)
                {
                    case R.id.dir_background:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_MAIN_BKG,FILE_PICTURE);
                        break;
                    case R.id.dir_icon:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_ICON,FILE_PICTURE);
                        break;
                    case R.id.dir_dispense:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_DRINK_AM,FILE_MEDIA);
                        break;
                    case R.id.dir_stroy:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_STORY,FILE_MEDIA);
                        break;
                    case R.id.dir_gallery:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_DRINK_BKG,FILE_PICTURE);
                        break;
                    case R.id.dir_cuplogo:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_SCREEN_LOGO,FILE_PICTURE);
                        break;
                    case R.id.dir_banner:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_BRAND,FILE_PICTURE);
                        break;
                    case R.id.dir_saver:
                        showPutfile2destfolder(srcpath,FileHelper.PATH_SCREEN_SAVER,FILE_MEDIA);
                        break;
                    case R.id.dir_pdf:
                        // TODO: 2018/9/3 PDF?
                            showPutfile2destfolder(srcpath,FileHelper.PATH_HELP_SERVICE,FILE_DOCUMENT);
                        break;
                }
                break;
        }
        return true;
    }

    private void showPopupWindow(View parent) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_win_import, null);
            elastic_download_view = contentView.findViewById(R.id.elastic_download_view);
            tv_update_content = contentView.findViewById(R.id.tv_update_content);
            btn_retry= contentView.findViewById(R.id.btn_retry);
            btn_retry.setOnClickListener(this);
            elastic_download_view.startIntro();
            mPopWindow = new PopupWindow(contentView);
            mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopWindow.showAtLocation(parent, Gravity.CENTER,0,0);
            showmPopWindow = true;
        }
    }
    private void DismissPopWindow()
    {
        mPopWindow.dismiss();
        showmPopWindow =false;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_retry)
        {
            DismissPopWindow();
        }
       else if(view.getId() == R.id.btn_import)
        {
            showPopupWindow(view);
            String srcDbPath2 =  app.getUsbpath()+ FileHelper.PATH_USB + ConfigPath + "/db/cqube.db";
            String toDbPath2 = "/data/data/evo.luna.android/databases/cqube.db";
            String srcResPath2 = app.getUsbpath()+ FileHelper.PATH_USB + ConfigPath + "/crem/";
            String toResPath2 = "/mnt/sdcard/crem/";
            String[] exportParams2 = new String[] { srcDbPath2, toDbPath2, srcResPath2, toResPath2 };
            new ImportAsyncTask().execute(exportParams2);
        }
        else if(view.getId() == R.id.btn_update_lang)
        {
            //导入菜单的名字
            showPopupWindow(view);
            String srcPath =  app.getUsbpath()+ FileHelper.PATH_LANG + "/drinkname.lang";
            String[] exportParams = new String[] { srcPath};
            new updateLanguageAsyncTask().execute(exportParams);
        }
    }
    /*
     * 导入配置任务  /一些信息需要保留
     * 首先停止和主板的指令，切换到导入状态
     * 1.判断数据库是否存在，替换数据库
     * 2.导入所有资源文件
     * 3.切换状态导入---》开始同步
     * 4.通知主板清除菜单信息，等待主板状态（清除完成）
     * 5.重新下发新的菜单，改写菜单的更新状态
     * 6.同步机器的设置信息。
     * 7.提示用户导入完成，重启设备。
     */

    class ImportAsyncTask extends AsyncTask<String,Integer,String>
    {
        private List<String> beveragelsit = new ArrayList<>();
        private FileHelper.myCallBack myCallback =new FileHelper.myCallBack()
        {
            @Override
            public void postprogress(int value) {
                publishProgress(10,value);
            }
        };
        private  float m_totalecount;

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("ok"))
            {
                elastic_download_view.success();
                btn_retry.setVisibility(View.VISIBLE);
                btn_retry.setText("Done");
            }
            else
            {
                elastic_download_view.fail();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            elastic_download_view.setProgress(values[0]+((float)values[1]/m_totalecount)*30);
        }

        @Override
        protected String doInBackground(String... strings) {
            String scrDBpath = strings[0];
            String toDBPath = strings[1];
            String scrRespath = strings[2];
            String toResPath = strings[3];
            FileHelper.mfilescount =0;
            m_totalecount = FileHelper.CountFile(new File(scrRespath));
            app.setStopHandleWithBoard(true);
            if(!ImportDBFile(scrDBpath,toDBPath))
                return "failed";
            publishProgress(10,0);
           if(!ImportResFiles(scrRespath,toResPath))
               return "failed";
            publishProgress(40,0);
            app.setStopHandleWithBoard(false);
            AsyncMachineData();
            NotifyBoardClearBeverage();
            AsyncBeverageData();
            publishProgress(50,0);
            return "ok";
        }
        //判断数据库是否存在，替换数据库
        private boolean ImportDBFile(String scr,String to)
        {
            if(!FileHelper.IsValidDB(scr))
                return false;
            if(!FileHelper.copyFile(scr,to))
                return false;
            if(app.getHelper()!=null)
            {
                app.getHelper().close();
                app.SetDataHelper(null);
            }
            return true;
        }
        //导入所有资源文件
        private boolean ImportResFiles(String scr,String to)
        {
            return FileHelper.copyFolder(scr,to,myCallback);
        }
        //通知主板清除菜单信息，等待主板状态（清除完成）
        private void NotifyBoardClearBeverage()
        {
           /* int tst =0;
            CmdStorageMgt cmdStorageMgt = new CmdStorageMgt();
            app.addCmdQueue(cmdStorageMgt.BulidCmd(CmdStorageMgt.TYPE_DELETE_ALL));
            app.getMachimeState().setMachine_state(AckQuery.M_STATE_STORAGE_START);
            while (app.getMachimeState().getMachine_state() == AckQuery.M_STATE_STORAGE_START)
            {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(tst++ == 5000)
                {
                    app.getMachimeState().setMachine_state(AckQuery.M_STATE_NORMAL);
                }
            }*/
        }
        //同步机器的设置信息
        private void AsyncMachineData()
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //重新下发新的菜单，改写菜单的更新状态
        private void AsyncBeverageData()
        {
            allbeveragecmd();
            for(int i=0;i<beveragelsit.size();i++) {
                EvoTrace.e("usb", beveragelsit.get(i));
            }
        }
        private void allbeveragecmd()
        {
            beveragelsit.clear();
            CmdMakeIngredient cmdMakeIngredient = new CmdMakeIngredient();
            try {
                List<IngredientFilterBrew> filterBrews = app.getHelper().getIngredientFilterBrewDao().queryForAll();
                for (int i = 0; i < filterBrews.size(); i++) {
                    IngredientFilterBrew filterBrew = filterBrews.get(i);
                    String ingredientStructure = cmdMakeIngredient.buildFilterBrewStructure(filterBrew);
                    String cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, filterBrew.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_FILTER_BREW), ingredientStructure);
                    beveragelsit.add(cmd);
                    /*cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, filterBrew.getPid(), AndroidUtils.oct2Hex(Ingredient.TYPE_FILTER_BREW), ingredientStructure);
                    beveragelsit.add(cmd);*/
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                List<IngredientInstant> instants = app.getHelper().getIngredientInstantDao().queryForAll();
                for (int i = 0; i < instants.size(); i++) {
                    IngredientInstant instant = instants.get(i);
                    String ingredientStructure = cmdMakeIngredient.buildInstantStructure(instant);
                    String cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, instant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure);
                    beveragelsit.add(cmd);
                    /*cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, instant.getPid(), AndroidUtils.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure);
                    commands.add(cmd);*/

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<IngredientWater> waters = null;
            try {
                waters = app.getHelper().getIngredientWaterDao().queryForAll();
                for (int i = 0; i < waters.size(); i++) {
                    IngredientWater water = waters.get(i);
                    String ingredientStructure = cmdMakeIngredient.buildWaterStructure(water);
                    String cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, water.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_WATER), ingredientStructure);
                    beveragelsit.add(cmd);
                /*cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, water.getPid(), AndroidUtils.oct2Hex(Ingredient.TYPE_WATER), ingredientStructure);
                commands.add(cmd);*/
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            List<IngredientFilterBrewAdvance> lstfilterBrewAdvances = null;
            try {
                lstfilterBrewAdvances = app.getHelper().getIngredientFilterBrewAdvanceDao().queryForAll();
                if(lstfilterBrewAdvances!=null) {
                    for (int i = 0; i < lstfilterBrewAdvances.size(); i++) {
                        IngredientFilterBrewAdvance ingredientFilterBrewAdvance = lstfilterBrewAdvances.get(i);
                        List<FiterBrewStep> lstfilterstep =filterBrewStepDao.getFilterBrewStep(ingredientFilterBrewAdvance.getPid());
                        if(lstfilterstep!=null) {
                            String ingredientStructure = cmdMakeIngredient.buildFilterBrewAdvanceStructure(ingredientFilterBrewAdvance,lstfilterstep);
                            String cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, ingredientFilterBrewAdvance.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_FILTER_BREW_ADVANCE), ingredientStructure);
                            beveragelsit.add(cmd);
                        /*cmd = cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, ingredientFilterBrewAdvance.getPid(), AndroidUtils.oct2Hex(Ingredient.TYPE_FILTER_BREW_ADVANCE), ingredientStructure);
                        commands.add(cmd);*/
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //CmdMakeBeverage cmdMakeBeverage = new CmdMakeBeverage(app);
           /* List<Beverage> beverages = null;
            try {
                beverages = app.getHelper().getBeverageDao().queryForAll();
                for (int i = 0; i < beverages.size(); i++) {
                    Beverage beverage = beverages.get(i);
                    String cmd = null;
                    try {
                        cmd = cmdMakeBeverage.buildCmd(Constant.OPCMD_ADD, beverage);
                        beveragelsit.add(cmd);
                    } catch (CmdNullException e) {
                        EvoTrace.e("usb",e.toString());
                    }

                *//*cmd = cmdMakeBeverage.buildCmd(Constant.OPCMD_MODIFY, beverage);
                commands.add(cmd);*//*
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }*/


        }
    }

    /*
     * 导入菜单的名字（多国语言）
     * 1.判断语言文件合法性
     * 2.清空数据库中的语言资源
     * 3.同步导入菜单名字
     */
    class updateLanguageAsyncTask extends AsyncTask<String,Integer,String>
    {
        private List<DrinkName> drinkNames =null;
        private int totalsize =0;
        @Override
        protected String doInBackground(String... params) {
            String respath = params[0];
            if(!FileHelper.IsValidDB(respath))
            {
                return "failed";
            }
            drinkNames =LangLocalHelper.getAllDrinkfromjson(respath);
            if(drinkNames!= null && drinkNames.size()>0)
            {
                totalsize = drinkNames.size();
                syncDrinkName();
                return "ok";
            }
            return "failed";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("ok"))
            {
                elastic_download_view.success();
                btn_retry.setVisibility(View.VISIBLE);
                btn_retry.setText("Done");
            }
            else
            {
                elastic_download_view.fail();
                btn_retry.setVisibility(View.VISIBLE);
                btn_retry.setText("Close");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            elastic_download_view.setProgress(values[0].floatValue()/totalsize);
        }
        private void syncDrinkName()
        {
            int i =0;
            beverageFactoryDao.getBeverageNameDao().clear();
            for (DrinkName item:drinkNames)
            {
                i++;
                beverageFactoryDao.getBeverageNameDao().create(item);
                publishProgress(i);
            }
        }
    }

}
