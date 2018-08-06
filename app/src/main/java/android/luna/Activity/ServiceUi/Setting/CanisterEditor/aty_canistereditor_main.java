package android.luna.Activity.ServiceUi.Setting.CanisterEditor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.adapter.PowderAdapter;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.fragment.CanisterFragment;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.fragment.ContainFragment;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.fragment.NutritionFragment;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.DAO.StockFactoryDao;
import android.luna.Data.module.CanisterItemStock;
import android.luna.Data.module.DeviceLayout.DeviceItemLayout;
import android.luna.Data.module.MachineDevice.Dev_Canister;
import android.luna.Data.module.MachineDevice.Dev_Hopper;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Data.module.Powder.PowderNutrition;
import android.luna.Data.module.WasterBinStock;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.bottombar.BottomBar;
import android.luna.ViewUi.bottombar.OnTabSelectListener;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/17.
 */

public class aty_canistereditor_main extends BaseActivity implements View.OnClickListener{
    private Button btn_add;
    private RecyclerView rv_canister;
    private FrameLayout fly_canister_edt;
    private BottomBar bottomBar;
    private PowderAdapter powderAdapter =null;
    private List<PowderItem> _data=new ArrayList<>(10);

    private List<PowderItem> _data_grind=new ArrayList<>(2);
    private List<PowderItem> _data_instant=new ArrayList<>(4);

    public PowderFactory getPowderFactory() {
        return powderFactory;
    }

    private PowderFactory powderFactory=null;
    private LinearLayoutManager mManager;
    private ContainFragment Tb_containFragment=null;
    private NutritionFragment Tb_NutritionFragment =null;
    private CanisterFragment  Tb_canisterFragment =null;
    public PowderItem get_powderItem() {
        return _powderItem;
    }

    public void set_powderItem(PowderItem _powderItem) {
        this._powderItem = _powderItem;
    }

    public PowderNutrition get_powderNutrition() {
        return _powderNutrition;
    }

    public void set_powderNutrition(PowderNutrition _powderNutrition) {
        this._powderNutrition = _powderNutrition;
    }

    private PowderItem _powderItem;
    private PowderNutrition _powderNutrition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_DB_SET_ACK);
        registerReceiver(receiver, filter);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Constant.ACTION_DB_SET_ACK.equals(action))
            {
                showTestToast("Canister setting finish!");
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_canister_editor);
        btn_add = findViewById(R.id.btn_add);
        rv_canister = findViewById(R.id.rv_canister);
        fly_canister_edt = findViewById(R.id.fly_canister_edt);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setDefaultTabPosition(2);
        rv_canister.setLayoutManager(mManager = new LinearLayoutManager(this));
        rv_canister.setAdapter(powderAdapter);
        getApp().setIsmainpagereload(true);
    }

    @Override
    public void InitData() {
        super.InitData();
        _stockFactoryDao = new StockFactoryDao(this,getApp());
        powderFactory = new PowderFactory(this,getApp());
        _data =powderFactory.getPowerItemDao().queryall();

        if(_data!=null && _data.size()>0)
        {
            for (PowderItem item:_data)
            {
                if(item.getGroup() == 2)
                    _data_grind.add(item);
                else if(item.getGroup() == 3)
                    _data_instant.add(item);
            }
        }
        powderAdapter = new PowderAdapter(this,_data);
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        powderAdapter.SetdrinkitemOnClicked(new PowderAdapter.OndrinkitemClicked() {
            @Override
            public void OnitemClick(int position) {
                _powderItem = powderAdapter.getDatas().get(position);
                _powderNutrition = powderFactory.getPowderNutritionDao().query(_powderItem.getPid());
                if(Tb_containFragment!=null && Tb_containFragment.isAdded())
                {
                    Tb_containFragment.RefreshContainUi();
                }
                else if(Tb_NutritionFragment!= null && Tb_NutritionFragment.isAdded())
                {
                    Tb_NutritionFragment.RefreshUi();
                }
            }
        });

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId)
                {
                    case R.id.tab_nutrition:
                        if(_powderNutrition!=null) {
                            if (Tb_NutritionFragment == null) {
                                Tb_NutritionFragment = new NutritionFragment();
                            }
                            getFragmentManager().beginTransaction().replace(R.id.fly_canister_edt, Tb_NutritionFragment).commit();
                        }
                        break;
                    case R.id.tab_contain:
                        if(_powderItem!=null) {
                            if (Tb_containFragment == null) {
                                Tb_containFragment = new ContainFragment();
                            }
                            getFragmentManager().beginTransaction().replace(R.id.fly_canister_edt, Tb_containFragment).commit();
                        }
                        break;
                    case R.id.tab_import:
                        if(Tb_canisterFragment ==null)
                        {
                            Tb_canisterFragment = new CanisterFragment();
                        }
                        Tb_canisterFragment.setCanisteritems(_data_grind,_data_instant,powderFactory);
                        getFragmentManager().beginTransaction().replace(R.id.fly_canister_edt, Tb_canisterFragment).commit();
                        break;
                }
            }
        });
       findViewById(R.id.btn_back).setOnClickListener(this);
        btn_add.setOnClickListener(this);
    }

    private StockFactoryDao _stockFactoryDao;
    private List<Device> devices =null;
    private List<DeviceItemLayout> deviceItemLayouts =null;
    private List<Device> canisteritems =new ArrayList<>(4);
    private void rebuiltcanisterstock()
    {
        canisteritems.clear();
        devices = DeviceXmlFactory.getXmlDevice(FileHelper.PATH_CONFIG+"config.xmld");
        deviceItemLayouts = DeviceXmlFactory.getXmlLayout(FileHelper.PATH_CONFIG+"config.xmll");
        if(deviceItemLayouts!=null && deviceItemLayouts.size()>0)
        {
            for (DeviceItemLayout item:deviceItemLayouts)
            {
                if(item.getUid().startsWith("0002"))
                {
                    List<Device> devices =DeviceXmlFactory.getAttachDeviceByUid(item.getUid(),this.devices);
                    if(devices!=null)
                        canisteritems.addAll(devices);
                }
                else if(item.getUid().startsWith("0003"))
                {
                    canisteritems.add(DeviceXmlFactory.getMainDeviceByUid(item.getUid(),devices));
                }
            }
        }
        if(canisteritems.size()>0)
        {
            CanisterItemStock stock;
            for ( Device item :canisteritems)
            {
                if(item.getGroup_id() == 0x0015) //grinder
                {
                    stock = new CanisterItemStock();
                    stock.setGroup(2);
                    stock.setPid(((Dev_Hopper)item).getPowder_type());
                    stock.setCapability(Math.round(((Dev_Hopper)item).getMax_capability()*1000*((Dev_Hopper)item).getDosage_density()));
                    stock.setStock(Math.round(((Dev_Hopper)item).getMax_capability()*1000*((Dev_Hopper)item).getDosage_density()));
                    _stockFactoryDao.getCanisterStockDao().create(stock);
                    powderFactory.getPowerItemDao().updatePowderSelectedSt(stock.getPid(),1);
                }
                else if(item.getGroup_id() == 0x0003)
                {
                    stock = new CanisterItemStock();
                    stock.setGroup(3);
                    stock.setPid(((Dev_Canister)item).getPowder_type());
                    stock.setCapability(Math.round(((Dev_Hopper)item).getMax_capability()*1000*((Dev_Hopper)item).getDosage_density()));
                    stock.setStock(Math.round(((Dev_Hopper)item).getMax_capability()*1000*((Dev_Hopper)item).getDosage_density()));
                    _stockFactoryDao.getCanisterStockDao().create(stock);
                    powderFactory.getPowerItemDao().updatePowderSelectedSt(stock.getPid(),1);
                }
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                //// TODO: 2018/5/18 xinzeng xinde liaobao zhonglie
                powderFactory.InitPowderFactory();
                //test part//
                //for waster bin
                 WasterBinStock ret= _stockFactoryDao.getWasterbinStockDao().queryByPid(99);
                if(ret == null)
                {
                    ret =new WasterBinStock();
                    ret.setId(1);
                    _stockFactoryDao.getWasterbinStockDao().create(ret);
                }
                //for canister 1.clear 2.rebuilt
                _stockFactoryDao.getCanisterStockDao().cleartable();
                rebuiltcanisterstock();
                break;
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_canistereditor_main.this);
                break;
        }
    }
}
