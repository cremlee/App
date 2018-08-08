package android.luna.Activity.ProductLineUi.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.DAO.StockFactoryDao;
import android.luna.Data.module.CanisterItemStock;
import android.luna.Data.module.DeviceLayout.DeviceItemLayout;
import android.luna.Data.module.MachineDevice.Dev_Canister;
import android.luna.Data.module.MachineDevice.Dev_Hopper;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Data.module.WasterBinStock;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.BuildConfig;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.Device.MachineConfig;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.rs232.Cmd.CmdMachineConfig;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import evo.luna.android.R;
/**
 * Created by Lee.li on 2018/7/4.
 */
public class Fgt_production_config extends productionFragment implements View.OnClickListener {
    private Button next,upload;
    private MaterialDialog progressDialog;
    private Map<String, String> lang_map = new LinkedHashMap<>();
    private Map<String, String> type_map = new LinkedHashMap<>();
    private SettingItemDropDown lang ,machine;
    private CremApp app;
    public  interface nextListerner
    {
        void Clicked(int a);
        void UpdateLanguage();
    }
    private nextListerner _nextListerner;
    public void setOnnextListerner(nextListerner a)
    {
        _nextListerner =a;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_production_config, container, false);
        initver();
        InitView(view);
        app = ((BaseActivity)getActivity()).getApp();
        filter = new IntentFilter();
        filter.addAction(Constant.ACTION_CONFIG_MACHINE_FINISH);
        return view;
    }
    private void initver()
    {
        String[] strings = getResources().getStringArray(R.array.locallanguage);
        for (int i = 0; i < strings.length; i++) {
            lang_map.put(String.valueOf(i+1), strings[i]);
        }
        String[] stringskey = getResources().getStringArray(R.array.machine_type_key);
        strings = getResources().getStringArray(R.array.machine_type);
        for (int i = 0; i < strings.length; i++) {
            type_map.put(stringskey[i], strings[i]);
        }
    }
    @Override
    public void InitView(View view) {
        upload = view.findViewById(R.id.upload);
        next = view.findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        next.setOnClickListener(this);
        upload.setOnClickListener(this);
        lang = view.findViewById(R.id.lang);
        machine = view.findViewById(R.id.machine);
        lang.setItemAndValues(lang_map);
        machine.setItemAndValues(type_map);
        lang.refreshData(0);
        machine.refreshData(0);
        lang.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = lang.getItemAndValues().get(key);
                lang.setSelItem(key, name);
                Locale locale = AndroidUtils_Ext.getLocal(Integer.valueOf(key));
                if(!getResources().getConfiguration().locale.equals(locale))
                {
                    Configuration _Configuration = getResources().getConfiguration();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        _Configuration.setLocale(locale);
                    } else {
                        _Configuration.locale =locale;
                    }
                    DisplayMetrics _DisplayMetrics = getResources().getDisplayMetrics();
                    getResources().updateConfiguration(_Configuration, _DisplayMetrics);
                    if(_nextListerner!=null)
                        _nextListerner.UpdateLanguage();
                }
                   // updateLanguage(locale);
            }
        });
        machine.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = machine.getItemAndValues().get(key);
                machine.setSelItem(key, name);
            }
        });
    }

    /**
     * 系统的都改变<br/>
     * $ADB shell<br/>
     * $pm grant com.android.coffee android.permission.CHANGE_CONFIGURATION<br/>
     *
     * @param locale
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void updateLanguage(Locale locale) {
        try {
            Object objIActMag;
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
            Class[] clzParams = { Configuration.class };
            Method mtdIActMag$updateConfiguration = clzIActMag.getDeclaredMethod("updateConfiguration", clzParams);
            mtdIActMag$updateConfiguration.invoke(objIActMag, config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showuploadwindow()
    {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Upload config")
                .canceledOnTouchOutside(false)
                .content("uploading...")
                .progress(true, 0)
                .show();
    }
    private  IntentFilter filter;
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver,filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constant.ACTION_CONFIG_MACHINE_FINISH))
            {
                if(BuildConfig.isDebug) {
                    mHandler.sendEmptyMessageDelayed(1000, 1000);
                }
            }
        }
    };
    class UploadMachineConfigAsyncTask extends AsyncTask<String,Integer,String> {
        private List<Device> devices = null;
        private List<DeviceItemLayout> deviceItemLayouts = null;
        private List<Device> canisteritems = new ArrayList<>(4);
        private PowderFactory powderFactory = null;
        private StockFactoryDao _stockFactoryDao = null;

        @Override
        protected String doInBackground(String... params) {
            /**
             * step 1 : import res file ,if exit overwrite
             */
            if (FileHelper.isDirExist(FileHelper.PATH_RES)) {
                FileHelper.RecursionDeleteFile(new File(FileHelper.PATH_RES));
            }
            if (!FileHelper.isDirExist(app.getUsbpath() + "/machine/resource/")) {
                return "ERR1";
            }
            if (!FileHelper.copyFolder(app.getUsbpath() + "/machine/resource/", FileHelper.PATH_RES)) {
                return "ERR2";
            }
            /**
             * step 2 : import device config ,check the file
             */
            if (!checkconfig()) {
                return "ERR3";
            }
            return "OK";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("ok")) {
                // TODO: 2018/7/17 send cmd ,wait for ack
                importconfig();
            } else {
                ((BaseActivity) getActivity()).showToast("upload failed +" + s);
                if (progressDialog != null)
                    progressDialog.dismiss();
            }
        }

        private boolean checkconfig() {
            if (!FileHelper.isDirExist(app.getUsbpath() + "/machine/config/config.chk"))
                return false;
            ConfigKeyItem configKeyItem = new ConfigKeyItem();
            boolean isdevice = false;
            try {
                InputStream xml = new FileInputStream(app.getUsbpath() + "/machine/config/config.chk");
                XmlPullParser pullParser = Xml.newPullParser();
                pullParser.setInput(xml, "UTF-8");
                String tagName = "";
                int event = pullParser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {
                    switch (event) {
                        case XmlPullParser.START_TAG:
                            tagName = pullParser.getName();
                            if ("Device".equalsIgnoreCase(pullParser.getName())) {
                                isdevice = true;
                            } else if ("Layout".equalsIgnoreCase(pullParser.getName())) {
                                isdevice = false;
                            }
                            break;
                        case XmlPullParser.TEXT:
                            if ("Name".equalsIgnoreCase(tagName)) {
                                if (isdevice)
                                    configKeyItem.devicefile = pullParser.getText();
                                else
                                    configKeyItem.layoutfile = pullParser.getText();
                            } else if ("Check".equalsIgnoreCase(tagName)) {
                                if (isdevice)
                                    configKeyItem.devicecheck = pullParser.getText();
                                else
                                    configKeyItem.layoutcheck = pullParser.getText();
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            tagName = "";
                            break;
                    }
                    event = pullParser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            if (!DeviceXmlFactory.getFileMD5(new File(app.getUsbpath() + "/machine/config/" + configKeyItem.devicefile)).equalsIgnoreCase(configKeyItem.devicecheck))
                return false;
            if (!DeviceXmlFactory.getFileMD5(new File(app.getUsbpath() + "/machine/config/" + configKeyItem.layoutfile)).equalsIgnoreCase(configKeyItem.layoutcheck))
                return false;
            // TODO: 2018/7/17 copy the file to pad
            FileHelper.copyFile(new File(app.getUsbpath() + "/machine/config/" + configKeyItem.devicefile), FileHelper.PATH_CONFIG, configKeyItem.devicefile);
            FileHelper.copyFile(new File(app.getUsbpath() + "/machine/config/" + configKeyItem.layoutfile), FileHelper.PATH_CONFIG, configKeyItem.layoutfile);
            // TODO: 2018/7/17 initdatabases
            InitDatabases();
            return true;
        }

        private void rebuiltcanisterstock() {
            canisteritems.clear();
            devices = DeviceXmlFactory.getXmlDevice(FileHelper.PATH_CONFIG + "config.xmld");
            deviceItemLayouts = DeviceXmlFactory.getXmlLayout(FileHelper.PATH_CONFIG + "config.xmll");
            if (deviceItemLayouts != null && deviceItemLayouts.size() > 0) {
                for (DeviceItemLayout item : deviceItemLayouts) {
                    if (item.getUid().startsWith("0002")) {
                        List<Device> devices = DeviceXmlFactory.getAttachDeviceByUid(item.getUid(), this.devices);
                        if (devices != null)
                            canisteritems.addAll(devices);
                    } else if (item.getUid().startsWith("0003")) {
                        canisteritems.add(DeviceXmlFactory.getMainDeviceByUid(item.getUid(), devices));
                    }
                }
            }
            if (canisteritems.size() > 0) {
                CanisterItemStock stock;
                for (Device item : canisteritems) {
                    if (item.getGroup_id() == 0x0015) //grinder
                    {
                        stock = new CanisterItemStock();
                        stock.setGroup(2);
                        stock.setPid(((Dev_Hopper) item).getPowder_type());
                        stock.setCapability(Math.round(((Dev_Hopper) item).getMax_capability() * 1000 * ((Dev_Hopper) item).getDosage_density()));
                        stock.setStock(Math.round(((Dev_Hopper) item).getMax_capability() * 1000 * ((Dev_Hopper) item).getDosage_density()));
                        _stockFactoryDao.getCanisterStockDao().create(stock);
                        powderFactory.getPowerItemDao().updatePowderSelectedSt(stock.getPid(), 1);
                    } else if (item.getGroup_id() == 0x0003) {
                        stock = new CanisterItemStock();
                        stock.setGroup(3);
                        stock.setPid(((Dev_Canister) item).getPowder_type());
                        stock.setCapability(Math.round(((Dev_Hopper) item).getMax_capability() * 1000 * ((Dev_Hopper) item).getDosage_density()));
                        stock.setStock(Math.round(((Dev_Hopper) item).getMax_capability() * 1000 * ((Dev_Hopper) item).getDosage_density()));
                        _stockFactoryDao.getCanisterStockDao().create(stock);
                        powderFactory.getPowerItemDao().updatePowderSelectedSt(stock.getPid(), 1);
                    }
                }
            }
        }

        private void InitDatabases() {
            powderFactory = new PowderFactory(getActivity(), app);
            _stockFactoryDao = new StockFactoryDao(getActivity(), app);
            powderFactory.InitPowderFactory();
            //for waster bin
            WasterBinStock ret = _stockFactoryDao.getWasterbinStockDao().queryByPid(99);
            if (ret == null) {
                ret = new WasterBinStock();
                ret.setId(1);
                _stockFactoryDao.getWasterbinStockDao().create(ret);
            }
            //for canister 1.clear 2.rebuilt
            _stockFactoryDao.getCanisterStockDao().cleartable();
            rebuiltcanisterstock();
        }

        private void importconfig() {
            String cmd="";
            int key = Integer.valueOf(machine.getSelKey(), 16);
            switch (key) {
                case MachineConfig.MACHINE_DEFINE:
                    cmd = (new CmdMachineConfig()).buildCmdByConfig(DeviceXmlFactory.getXmlDevice(FileHelper.PATH_CONFIG+"config.xmld"));
                    //cmd = (new CmdMachineConfig()).buildCmdByType(MachineConfig.MACHINE_CARARRA);
                    break;
                case MachineConfig.MACHINE_CARARRA:
                case MachineConfig.MACHINE_MF13:
                case MachineConfig.MACHINE_CL22:
                case MachineConfig.MACHINE_MF04:
                case MachineConfig.MACHINE_MF04_SSW:
                case MachineConfig.MACHINE_MF04_SW:
                case MachineConfig.MACHINE_MF13_SSW:
                case MachineConfig.MACHINE_MF13_SW:
                case MachineConfig.MACHINE_PSL50_12:
                case MachineConfig.MACHINE_PSL50_13:
                case MachineConfig.MACHINE_PSL50_14:
                case MachineConfig.MACHINE_UNITY:
                    cmd = (new CmdMachineConfig()).buildCmdByType(MachineConfig.MACHINE_CARARRA);
                    break;
            }
            if(!"".equalsIgnoreCase(cmd))
                ((BaseActivity) getActivity()).getApp().addCmdQueue(cmd);

        }
    }

    class ConfigKeyItem
    {
        public String devicefile;
        public String devicecheck;
        public String layoutfile;
        public String layoutcheck;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.upload:
                if("".equalsIgnoreCase(machine.getSelKey()))
                {
                    ((BaseActivity)getActivity()).showToast("Please Select the Machine Type!!");
                    break;
                }
                showuploadwindow();
                new UploadMachineConfigAsyncTask().execute(new String[]{});
                break;
            case R.id.next:
                if(_nextListerner!=null)
                    _nextListerner.Clicked(1);
                break;
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if(progressDialog!=null)
                    {
                        progressDialog.dismiss();
                    }
                    next.setVisibility(View.VISIBLE);
                default:
                    break;
            }
        }
    };
}
