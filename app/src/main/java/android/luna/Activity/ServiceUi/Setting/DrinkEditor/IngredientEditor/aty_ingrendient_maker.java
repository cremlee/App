package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.BaseFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.EsperssoFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.FilterbrewFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.InstantFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.MonoFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.WaterFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.DrinkAdapter;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.decoration.DividerItemDecoration;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.model.DrinkBean;
import android.luna.Data.DAO.EspressoDao;
import android.luna.Data.DAO.FilterBrewDao;
import android.luna.Data.DAO.IngredientDao;
import android.luna.Data.DAO.InstantDao;
import android.luna.Data.DAO.MonoDao;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.DAO.WaterDao;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientMono;
import android.luna.Data.module.IngredientWater;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.rs232.Ack.AckQuery;
import android.luna.rs232.Cmd.CmdMakeIngredient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/8.
 */

public class aty_ingrendient_maker extends BaseActivity implements View.OnClickListener {
    private static final String INDEX_STRING_TOP = "↑";
    private PopupWindow mPopWindow;
    private boolean showmPopWindow;
    private Button btn_save,btn_close,btn_test;
    private RecyclerView mRv;
    private SwipeDelMenuAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<DrinkBean> mDatas = new ArrayList<>();
    private SuspensionDecoration mDecoration;
    private IndexBar mIndexBar;
    private TextView mTvSideBarHint;
    private MaterialDialog progressDialog;
    private FilterbrewFragment Tb_Filterbrew;
    private InstantFragment Tb_Instant;
    private WaterFragment Tb_Water;
    private EsperssoFragment Tb_espresso;
    private MonoFragment Tb_mono;
    private int mIngrendientPid=0;
    private int m_ingredientType;
    private boolean iscurrentChanged =false;
    private int _lastpostion =-1;


    private static IngredientFilterBrew mingredientFilterBrew=null;
    public static IngredientFilterBrew getMingredientFilterBrew() {
        return mingredientFilterBrew;
    }

    private static IngredientInstant mingredientInstant=null;
    public static IngredientInstant getMingredientInstant() {
        return mingredientInstant;
    }

    private static IngredientWater mingredientWater=null;
    public  static IngredientWater getMingredientWater() {
        return mingredientWater;
    }

    private static IngredientEspresso mingredientEspresso=null;
    public  static IngredientEspresso getMingredientEspresso() {
        return mingredientEspresso;
    }

    private static IngredientMono mingredientMono=null;
    public  static IngredientMono getMingredientMono() {
        return mingredientMono;
    }

    private IngredientDao mingredientDao=null;
    private FilterBrewDao mfilterBrewDao=null;
    private InstantDao minstantDao=null;
    private WaterDao mwaterDao=null;
    private EspressoDao mespressoDao=null;
    private MonoDao mmonoDao=null;


    private PowderFactory powderFactory;
    public List<PowderItem> getPowderItems() {
        return powderItems;
    }
    private List<PowderItem> powderItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mingredientDao = new IngredientDao(this,getApp());
        mfilterBrewDao = new FilterBrewDao(this,getApp());
        minstantDao = new InstantDao(this,getApp());
        mwaterDao = new WaterDao(this,getApp());
        mespressoDao = new EspressoDao(this,getApp());
        mmonoDao= new MonoDao(this,getApp());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_MAKE_INGREDIENT_ACK);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if(action.equals(Constant.ACTION_MAKE_INGREDIENT_ACK))
            {

                int createst=1;
                int ackresult = intent.getIntExtra("ACK",2);
                int op =intent.getIntExtra("OP",4);
                if(op!=4) {
                    if(progressDialog!=null)
                    {
                        progressDialog.dismiss();
                    }
                    switch (m_ingredientType) {
                        case Ingredient.TYPE_FILTER_BREW:
                            if (mingredientFilterBrew != null) {
                                switch (ackresult)
                                {
                                    case Ingredient.ACK_ERR:
                                    case Ingredient.ACK_ID_ERR:
                                    case Ingredient.ACK_REJECT:
                                    case Ingredient.ACK_NO_SPACE:
                                    case Ingredient.ACK_PACKAGE_ERR:
                                        createst = mingredientFilterBrew.getCreatestatus();
                                        break;
                                    case Ingredient.ACK_OK:
                                        createst =2;
                                        break;
                                    case Ingredient.ACK_ID_NOT_EXIST:
                                        createst = 1;
                                        break;
                                    case Ingredient.ACK_ID_ALREADY_EXIST:
                                        createst = 3;
                                        break;
                                }
                                mingredientFilterBrew.setCreatestatus(createst);
                                mfilterBrewDao.modify(mingredientFilterBrew);
                            }
                            break;
                        case Ingredient.TYPE_ESPRESSO:
                            if (mingredientEspresso != null) {
                                switch (ackresult)
                                {
                                    case Ingredient.ACK_ERR:
                                    case Ingredient.ACK_ID_ERR:
                                    case Ingredient.ACK_REJECT:
                                    case Ingredient.ACK_NO_SPACE:
                                    case Ingredient.ACK_PACKAGE_ERR:
                                        createst = mingredientEspresso.getCreatestatus();
                                        break;
                                    case Ingredient.ACK_OK:
                                        createst =2;
                                        break;
                                    case Ingredient.ACK_ID_NOT_EXIST:
                                        createst = 1;
                                        break;
                                    case Ingredient.ACK_ID_ALREADY_EXIST:
                                        createst = 3;
                                        break;
                                }
                                mingredientEspresso.setCreatestatus(createst);
                                mespressoDao.modify(mingredientEspresso);
                            }
                            break;
                        case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                            break;
                        case Ingredient.TYPE_INSTANT:
                            if (mingredientInstant != null) {
                                switch (ackresult)
                                {
                                    case Ingredient.ACK_ERR:
                                    case Ingredient.ACK_ID_ERR:
                                    case Ingredient.ACK_REJECT:
                                    case Ingredient.ACK_NO_SPACE:
                                    case Ingredient.ACK_PACKAGE_ERR:
                                        createst = mingredientInstant.getCreatestatus();
                                        break;
                                    case Ingredient.ACK_OK:
                                        createst =2;
                                        break;
                                    case Ingredient.ACK_ID_NOT_EXIST:
                                        createst = 1;
                                        break;
                                    case Ingredient.ACK_ID_ALREADY_EXIST:
                                        createst = 3;
                                        break;
                                }
                                mingredientInstant.setCreatestatus(createst);
                                minstantDao.modify(mingredientInstant);
                            }
                            break;
                        case Ingredient.TYPE_MILK:
                            break;
                        case Ingredient.TYPE_WATER:
                            if (mingredientWater != null) {
                                switch (ackresult)
                                {
                                    case Ingredient.ACK_ERR:
                                    case Ingredient.ACK_ID_ERR:
                                    case Ingredient.ACK_REJECT:
                                    case Ingredient.ACK_NO_SPACE:
                                    case Ingredient.ACK_PACKAGE_ERR:
                                        createst = mingredientWater.getCreatestatus();
                                        break;
                                    case Ingredient.ACK_OK:
                                        createst =2;
                                        break;
                                    case Ingredient.ACK_ID_NOT_EXIST:
                                        createst = 1;
                                        break;
                                    case Ingredient.ACK_ID_ALREADY_EXIST:
                                        createst = 3;
                                        break;
                                }
                                mingredientWater.setCreatestatus(createst);
                                mwaterDao.modify(mingredientWater);
                            }
                            break;
                        case Ingredient.TYPE_MONO:
                            if (mingredientMono != null) {
                                switch (ackresult)
                                {
                                    case Ingredient.ACK_ERR:
                                    case Ingredient.ACK_ID_ERR:
                                    case Ingredient.ACK_REJECT:
                                    case Ingredient.ACK_NO_SPACE:
                                    case Ingredient.ACK_PACKAGE_ERR:
                                        createst = mingredientMono.getCreatestatus();
                                        break;
                                    case Ingredient.ACK_OK:
                                        createst =2;
                                        break;
                                    case Ingredient.ACK_ID_NOT_EXIST:
                                        createst = 1;
                                        break;
                                    case Ingredient.ACK_ID_ALREADY_EXIST:
                                        createst = 3;
                                        break;
                                }
                                mingredientMono.setCreatestatus(createst);
                                mmonoDao.modify(mingredientMono);
                            }
                            break;
                    }
                    if (ackresult == 1) {
                        showToast("save ingredient ok");
                    } else {
                        showToast("save ingredient failed:" + ackresult);
                    }
                }
                else
                {
                    mHandler.sendEmptyMessageDelayed(1000,makeDrinkTime());
                }
            }
        }};
    private int makeDrinkTime()
    {
        int ingredientTime =30000;
        switch (m_ingredientType) {
            case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                        /*IngredientFilterBrewAdvance filterBrewadv = getHelper().getIngredientFilterBrewAdvanceDao().queryBuilder().where().eq("pid", beverageIngredient.getIngredientPid()).queryForFirst();
                        if(filterBrewadv!=null)
                        {
                            FilterBrewStepDao aa = new FilterBrewStepDao(this,getApp());
                            ingredientTime = 20000+aa.getsteptime(beverageIngredient.getIngredientPid());
                        }*/
                break;
            case Ingredient.TYPE_FILTER_BREW:
                /*if (filterBrew != null) {						// 计算总时间
                    final int BREW_RUNNING_TIME = 10000;
                    int time = filterBrew.getTmPre13();
                    ingredientTime = (filterBrew.getDecompressTime() + filterBrew.getExtractionTime() + filterBrew.getOpenDelayTime() + time) * 100 + BREW_RUNNING_TIME;
                }*/
                break;
            case Ingredient.TYPE_INSTANT:
               if (mingredientInstant != null) {
                    ingredientTime =  ((mingredientInstant.getWaterVolume()  + mingredientInstant.getPreflushVolume() + mingredientInstant.getWaterAfterFlushVolume()) * 100) + (mingredientInstant.getPauseTimeAfterDispense() + mingredientInstant.getPreflushPauseTime()) + 5000;
                }
                break;
            case Ingredient.TYPE_WATER:
                if (mingredientWater != null) {
                    ingredientTime =  (mingredientWater.getWaterVolume() * 100);
                }
                break;
            case Ingredient.TYPE_ESPRESSO:
                if(mingredientEspresso!=null)
                {
                    ingredientTime =  (mingredientEspresso.getBrewtime()+mingredientEspresso.getPrebrewtime()+mingredientEspresso.getPreinfusiontime())*1000+10000+(int)(mingredientEspresso.getPowderamount()*100)+mingredientEspresso.getWatervolume()*100;
                }
                break;
            case Ingredient.TYPE_MONO:
                if(mingredientMono!=null)
                {
                    ingredientTime = mingredientMono.getBrewtime()+mingredientMono.getInfusiontime()+mingredientMono.getAirruntime();
                }
                break;
            default:
                break;
        }
        EvoTrace.e("ack","test wait time ="+ingredientTime);
        return ingredientTime;
    }
    @SuppressLint("HandlerLeak")
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
                    showToast("preview Ingredient ok");
                default:
                    break;
            }
        }
    };
    private void showornot(View contentView)
    {
        contentView.findViewById(R.id.add_espresso).setVisibility(View.GONE);
        contentView.findViewById(R.id.add_mono).setVisibility(View.GONE);
        contentView.findViewById(R.id.add_filter).setVisibility(View.GONE);
        contentView.findViewById(R.id.add_filter_ad).setVisibility(View.GONE);
        contentView.findViewById(R.id.add_instant).setVisibility(View.GONE);
        contentView.findViewById(R.id.add_milk).setVisibility(View.GONE);
        List<Device> devices = DeviceXmlFactory.getXmlDevice(FileHelper.PATH_CONFIG+"config.xmld");
        if(devices.contains(new Device(0x0001,0x01)))
        {
            contentView.findViewById(R.id.add_espresso).setVisibility(View.VISIBLE);
        }
        if(devices.contains(new Device(0x0001,0x02)))
        {
            contentView.findViewById(R.id.add_mono).setVisibility(View.VISIBLE);
        }
        if(devices.contains(new Device(0x0001,0x03)))
        {
            contentView.findViewById(R.id.add_filter).setVisibility(View.VISIBLE);
            contentView.findViewById(R.id.add_filter_ad).setVisibility(View.VISIBLE);
        }
        if(devices.contains(new Device(0x0003,0x01))||devices.contains(new Device(0x0003,0x02))||devices.contains(new Device(0x0003,0x03)))
        {
            contentView.findViewById(R.id.add_instant).setVisibility(View.VISIBLE);
        }
    }
    private void showPopupWindow(View parent,int type) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
                View contentView = LayoutInflater.from(aty_ingrendient_maker.this).inflate(R.layout.pop_win_add_ingredient, null);
                mPopWindow = new PopupWindow(contentView);
                contentView.findViewById(R.id.add_exit).setOnClickListener(this);
                contentView.findViewById(R.id.add_filter).setOnClickListener(this);
                contentView.findViewById(R.id.add_filter_ad).setOnClickListener(this);
                contentView.findViewById(R.id.add_instant).setOnClickListener(this);
                contentView.findViewById(R.id.add_mono).setOnClickListener(this);
                contentView.findViewById(R.id.add_espresso).setOnClickListener(this);
                contentView.findViewById(R.id.add_water).setOnClickListener(this);
                contentView.findViewById(R.id.add_milk).setOnClickListener(this);
                showornot(contentView);
                mPopWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                mPopWindow.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
                mPopWindow.showAtLocation(btn_save,Gravity.CENTER, 0, 0);
        }
            showmPopWindow = true;
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_drinkedit_ingredient);
        btn_save =  findViewById(R.id.btn_save);
        btn_close =  findViewById(R.id.btn_close);
        btn_test =  findViewById(R.id.btn_test);
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(aty_ingrendient_maker.this, DividerItemDecoration.VERTICAL_LIST));
        mTvSideBarHint =  findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = findViewById(R.id.indexBar);//IndexBar
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }
    List<Ingredient> ingredientList =new ArrayList<>();
    @Override
    public void InitData() {
        super.InitData();
        mDatas = new ArrayList<>();
        mAdapter = new SwipeDelMenuAdapter(this, mDatas);
        try {
            ingredientList = getApp().getHelper().getIngredientDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            ingredientList =null;
        }
        mDatas.add((DrinkBean) new DrinkBean(999,getString(R.string.SVR_DRINK_INGREDIENT_ADD),"",false,false).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
        if(ingredientList!=null) {
            for (Ingredient item: ingredientList )
            {
                if(item.getIsDefault() ==2) {
                    DrinkBean drinkBean = new DrinkBean(item.getPid(), item.getName(), "", (item.getIsDefault() == 1 ? false : true), false);
                    mDatas.add(drinkBean);
                }
            }
        }
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
        powderFactory = new PowderFactory(this,getApp());
        powderItems =powderFactory.getPowerItemDao().queryall();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_save.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        mAdapter.SetdrinkitemOnClicked(new DrinkAdapter.OndrinkitemClicked() {
            @Override
            public void OnitemClick(int position) {
                final int pos = position;
                if(_lastpostion == position && position!=0)
                {
                    return;
                }
                if(iscurrentChanged)
                {
                     progressDialog = new MaterialDialog.Builder(aty_ingrendient_maker.this)
                            .title("Save")
                            .content("your Ingredient has been changed ,do you want to save it?")
                            .positiveText("Save")
                            .positiveColor(getResources().getColor(R.color.green_grass))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mAdapter.rollback(_lastpostion);
                                    progressDialog.dismiss();
                                            SaveIngredient();
                                }
                            })
                            .negativeText("Later")
                            .negativeColor(getResources().getColor(R.color.red_wine))
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    updateIngredientUi(pos);
                                }
                            })
                             .canceledOnTouchOutside(false)
                            .show();
                }
                else {
                    updateIngredientUi(pos);
                }
            }
        });
    }

    private void updateIngredientUi(int position ,boolean rstchang)
    {
            _lastpostion = position;
            mIngrendientPid = mAdapter.getDatas().get(position).getPid();
            Ingredient ingredient = mingredientDao.getIngredient(mIngrendientPid);
            if (ingredient == null)
                return;
            m_ingredientType = ingredient.getType();
            switch (m_ingredientType) {
                case Ingredient.TYPE_FILTER_BREW:
                    mingredientFilterBrew = mfilterBrewDao.findByT(ingredient.getPid());
                    if (mingredientFilterBrew != null) {
                        if (Tb_Filterbrew == null)
                            Tb_Filterbrew = new FilterbrewFragment();
                        if (Tb_Filterbrew.isAdded()) {
                            Tb_Filterbrew.InitView(mingredientFilterBrew);
                        }
                        Tb_Filterbrew.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientFilterBrew.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Filterbrew).commit();
                    }
                    break;
                case Ingredient.TYPE_INSTANT:
                    mingredientInstant = minstantDao.findByT(ingredient.getPid());
                    if (mingredientInstant != null) {
                        if (Tb_Instant == null)
                            Tb_Instant = new InstantFragment();
                        if (Tb_Instant.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_Instant.InitView(mingredientInstant);
                        }
                        Tb_Instant.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientInstant.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Instant).commit();
                    }
                    break;
                case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                    break;
                case Ingredient.TYPE_ESPRESSO:
                    mingredientEspresso = mespressoDao.findByT(ingredient.getPid());
                    if (mingredientEspresso != null) {
                        if (Tb_espresso == null)
                            Tb_espresso = new EsperssoFragment();
                        if (Tb_espresso.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_espresso.InitView(mingredientEspresso);
                        }
                        Tb_espresso.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientEspresso.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_espresso).commit();
                    }
                    break;
                case Ingredient.TYPE_WATER:
                    mingredientWater = mwaterDao.findByT(ingredient.getPid());
                    if (mingredientWater != null) {
                        if (Tb_Water == null)
                            Tb_Water = new WaterFragment();
                        if (Tb_Water.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_Water.InitView(mingredientWater);
                        }
                        Tb_Water.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientWater.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Water).commit();
                    }
                    break;
                case Ingredient.TYPE_MONO:
                    mingredientMono = mmonoDao.findByT(ingredient.getPid());
                    if (mingredientMono != null) {
                        if (Tb_mono == null)
                            Tb_mono = new MonoFragment();
                        if (Tb_mono.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_mono.InitView(mingredientMono);
                        }
                        Tb_mono.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientMono.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_mono).commit();
                    }
                    break;
            }
        }

    private void updateIngredientUi(int position ) {
        _lastpostion = position;
        if(position == 0) //添加新的菜单,该部分菜单先置顶
        {
            showPopupWindow(null,0);
        }
        else {
            mIngrendientPid = mAdapter.getDatas().get(position).getPid();
            Ingredient ingredient = mingredientDao.getIngredient(mIngrendientPid);
            if (ingredient == null)
                return;
            m_ingredientType = ingredient.getType();
            switch (m_ingredientType) {
                case Ingredient.TYPE_FILTER_BREW:
                    mingredientFilterBrew = mfilterBrewDao.findByT(ingredient.getPid());
                    if (mingredientFilterBrew != null) {
                            if (Tb_Filterbrew == null)
                                Tb_Filterbrew = new FilterbrewFragment();
                            if (Tb_Filterbrew.isAdded()) {
                                Tb_Filterbrew.InitView(mingredientFilterBrew);
                            }
                            Tb_Filterbrew.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                                @Override
                                public void itemchanged(int type) {
                                    iscurrentChanged = true;
                                    if(type==99)
                                    {
                                        //// TODO: 2018/2/11 shuxin lbiao xianshi
                                        mDatas.get(_lastpostion).setdrinkname(mingredientFilterBrew.getName());
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                            getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Filterbrew).commit();
                        }
                    break;
                case Ingredient.TYPE_INSTANT:
                    mingredientInstant = minstantDao.findByT(ingredient.getPid());
                    if (mingredientInstant != null) {
                        if (Tb_Instant == null)
                            Tb_Instant = new InstantFragment();
                        if (Tb_Instant.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_Instant.InitView(mingredientInstant);
                        }
                        Tb_Instant.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientInstant.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Instant).commit();
                    }
                    break;
                case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                    break;
                case Ingredient.TYPE_ESPRESSO:
                    mingredientEspresso = mespressoDao.findByT(ingredient.getPid());
                    if (mingredientEspresso != null) {
                        if (Tb_espresso == null)
                            Tb_espresso = new EsperssoFragment();
                        if (Tb_espresso.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_espresso.InitView(mingredientEspresso);
                        }
                        Tb_espresso.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientEspresso.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_espresso).commit();
                    }
                    break;
                case Ingredient.TYPE_WATER:
                    mingredientWater = mwaterDao.findByT(ingredient.getPid());
                    if (mingredientWater != null) {
                        if (Tb_Water == null)
                            Tb_Water = new WaterFragment();
                        if (Tb_Water.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_Water.InitView(mingredientWater);
                        }
                        Tb_Water.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientWater.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Water).commit();
                    }
                    break;
                case Ingredient.TYPE_MONO:
                    mingredientMono = mmonoDao.findByT(ingredient.getPid());
                    if (mingredientMono != null) {
                        if (Tb_mono == null)
                            Tb_mono = new MonoFragment();
                        if (Tb_mono.isAdded()) {
                            //// TODO: 2018/2/11 shuaxin jiemian
                            Tb_mono.InitView(mingredientMono);
                        }
                        Tb_mono.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                            @Override
                            public void itemchanged(int type) {
                                iscurrentChanged = true;
                                if(type==99)
                                {
                                    //// TODO: 2018/2/11 shuxin lbiao xianshi
                                    mDatas.get(_lastpostion).setdrinkname(mingredientMono.getName());
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                        getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_mono).commit();
                    }
                    break;
            }
            iscurrentChanged = false;
        }
    }

    public int getmIngrendientPid() {
        return mIngrendientPid;
    }

    private void perviewtest()
    {
        CmdMakeIngredient cmdMakeIngredient = new CmdMakeIngredient();
        String ingredientStructure;
        if(m_ingredientType == Ingredient.TYPE_FILTER_BREW)
        {
            if(mingredientFilterBrew!=null && Tb_Filterbrew!=null)
            {
                showSavewindow();
                Tb_Filterbrew.save();
                ingredientStructure = cmdMakeIngredient.buildFilterBrewStructure(mingredientFilterBrew);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientFilterBrew.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_FILTER_BREW), ingredientStructure));
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_INSTANT)
        {
            if(mingredientInstant!=null && Tb_Instant!=null)
            {
                showSavewindow();
                Tb_Instant.save();
                ingredientStructure = cmdMakeIngredient.buildInstantStructure(mingredientInstant);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientInstant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure));
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_WATER)
        {
            if(mingredientWater!=null && Tb_Water!=null)
            {
                showSavewindow();
                Tb_Water.save();
                ingredientStructure = cmdMakeIngredient.buildWaterStructure(mingredientWater);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientWater.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_WATER), ingredientStructure));
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_ESPRESSO)
        {
            if(mingredientEspresso!=null && Tb_espresso!=null)
            {
                showSavewindow();
                Tb_espresso.save();
                ingredientStructure = cmdMakeIngredient.buildEspressoStructure(mingredientEspresso);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientEspresso.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_ESPRESSO), ingredientStructure));
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_MONO)
        {
            if(mingredientMono!=null && Tb_mono!=null)
            {
                showSavewindow();
                Tb_mono.save();
                ingredientStructure = cmdMakeIngredient.buildMonoStructure(mingredientMono);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientMono.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_MONO), ingredientStructure));
            }
        }
    }
    @Override
    public void onClick(View view) {
        int pid;
        switch (view.getId())
        {
            case R.id.btn_close:
                AppManager.getAppManager().finishActivity(aty_ingrendient_maker.this);
                break;
            case R.id.btn_test:
                if(getApp().getAckQueryInstance().getMachine_state()!= AckQuery.MS_BLOCK_MODE)
                    perviewtest();
                else
                    showTestToast("Machine Error state:"+getApp().getAckQueryInstance().getMachine_state());
                break;
            case R.id.btn_save:
                SaveIngredient();
                break;
            case R.id.add_exit:
                DismissPopWindow();
                break;
            case R.id.add_filter:
                mingredientFilterBrew = new IngredientFilterBrew();
                mingredientFilterBrew.setName("new filter");
                mingredientFilterBrew.setIsDefault(2);
                pid = mfilterBrewDao.create(mingredientFilterBrew,0,0);
                if(pid!=0) {
                    iscurrentChanged = true;
                    mDatas.add(1, (DrinkBean) new DrinkBean(pid, "new filter", "", true, true).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                    mAdapter.setDatas(mDatas);
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    mAdapter.rollback(1);
                    updateIngredientUi(1, true);
                }
                DismissPopWindow();
                break;
            case R.id.add_filter_ad:
                showToast("not completed");
                DismissPopWindow();
                break;
            case R.id.add_instant:
                mingredientInstant = new IngredientInstant();
                mingredientInstant.setName("new instant");
                mingredientInstant.setIsDefault(2);
                pid = minstantDao.create(mingredientInstant,0,0);
                if(pid!=0) {
                    iscurrentChanged = true;
                    mDatas.add(1, (DrinkBean) new DrinkBean(pid, "new instant", "", true, true).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                    mAdapter.setDatas(mDatas);
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    mAdapter.rollback(1);
                    updateIngredientUi(1, true);
                }
                DismissPopWindow();
                break;
            case R.id.add_espresso:
                mingredientEspresso = new IngredientEspresso();
                mingredientEspresso.setName("new espresso");
                mingredientEspresso.setIsDefault(2);
                pid = mespressoDao.create(mingredientEspresso,0,0);
                if(pid!=0) {
                    iscurrentChanged = true;
                    mDatas.add(1, (DrinkBean) new DrinkBean(pid, "new espresso", "", true, true).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                    mAdapter.setDatas(mDatas);
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    mAdapter.rollback(1);
                    updateIngredientUi(1, true);
                }
                DismissPopWindow();
                break;
            case R.id.add_water:
                mingredientWater = new IngredientWater();
                mingredientWater.setName("new water");
                mingredientWater.setIsDefault(2);
                pid = mwaterDao.create(mingredientWater,0,0);
                if(pid!=0) {
                    iscurrentChanged = true;
                    mDatas.add(1, (DrinkBean) new DrinkBean(pid, "new water", "", true, true).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                    mAdapter.setDatas(mDatas);
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    mAdapter.rollback(1);
                    updateIngredientUi(1, true);
                }
                DismissPopWindow();
                break;
            case R.id.add_milk:
                showToast("not completed");
                DismissPopWindow();
                break;
            case R.id.add_mono:
                mingredientMono = new IngredientMono();
                mingredientMono.setName("new mono");
                mingredientMono.setIsDefault(2);
                pid = mmonoDao.create(mingredientMono,0,0);
                if(pid!=0) {
                    iscurrentChanged = true;
                    mDatas.add(1, (DrinkBean) new DrinkBean(pid, "new mono", "", true, true).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
                    mAdapter.setDatas(mDatas);
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    mAdapter.rollback(1);
                    updateIngredientUi(1, true);
                }
                DismissPopWindow();
                break;
        }
    }

    private void showSavewindow()
    {
        progressDialog = new MaterialDialog.Builder(aty_ingrendient_maker.this)
                .title("saving ingredient")
                .canceledOnTouchOutside(false)
                .content("saving...")
                .progress(true, 0)
                .show();
    }
    private void SaveIngredient() {
        CmdMakeIngredient cmdMakeIngredient = new CmdMakeIngredient();
        String ingredientStructure;
        if(m_ingredientType == Ingredient.TYPE_FILTER_BREW)
        {
            if(mingredientFilterBrew!=null && Tb_Filterbrew!=null)
            {
                Tb_Filterbrew.save();
                ingredientStructure = cmdMakeIngredient.buildFilterBrewStructure(mingredientFilterBrew);
                if(mingredientFilterBrew.getCreatestatus()==1) {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, mingredientFilterBrew.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_FILTER_BREW), ingredientStructure));
                }
                else if(mingredientFilterBrew.getCreatestatus()==3)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, mingredientFilterBrew.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_FILTER_BREW), ingredientStructure));
                }
                mfilterBrewDao.modify(mingredientFilterBrew);
                iscurrentChanged =false;
               /* if(progressDialog!=null)
                    progressDialog.dismiss();*/
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_INSTANT)
        {
            if(mingredientInstant!=null && Tb_Instant!=null)
            {
                Tb_Instant.save();
                ingredientStructure = cmdMakeIngredient.buildInstantStructure(mingredientInstant);
                if(mingredientInstant.getCreatestatus()==1)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, mingredientInstant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure));
                }else if(mingredientInstant.getCreatestatus()==3)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, mingredientInstant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure));
                }
                minstantDao.modify(mingredientInstant);
                iscurrentChanged =false;
               /* if(progressDialog!=null)
                    progressDialog.dismiss();*/
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_WATER)
        {
            if(mingredientWater!=null && Tb_Water!=null)
            {
                Tb_Water.save();
                ingredientStructure = cmdMakeIngredient.buildWaterStructure(mingredientWater);
                if(mingredientWater.getCreatestatus()==1)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, mingredientWater.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_WATER), ingredientStructure));
                }else if(mingredientWater.getCreatestatus()==3)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, mingredientWater.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_WATER), ingredientStructure));
                }
                mwaterDao.modify(mingredientWater);
                iscurrentChanged =false;
               /* if(progressDialog!=null)
                    progressDialog.dismiss();*/
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_ESPRESSO)
        {
            if(mingredientEspresso!=null && Tb_espresso!=null)
            {
                Tb_espresso.save();
                ingredientStructure = cmdMakeIngredient.buildEspressoStructure(mingredientEspresso);
                if(mingredientEspresso.getCreatestatus()==1)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, mingredientEspresso.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_ESPRESSO), ingredientStructure));
                }else if(mingredientEspresso.getCreatestatus()==3)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, mingredientEspresso.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_ESPRESSO), ingredientStructure));
                }
                mespressoDao.modify(mingredientEspresso);
                iscurrentChanged =false;
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_MONO)
        {
            if(mingredientMono!=null && Tb_mono!=null)
            {
                Tb_mono.save();
                ingredientStructure = cmdMakeIngredient.buildMonoStructure(mingredientMono);
                if(mingredientMono.getCreatestatus()==1)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, mingredientMono.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_MONO), ingredientStructure));
                }else if(mingredientMono.getCreatestatus()==3)
                {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, mingredientMono.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_MONO), ingredientStructure));
                }
                mmonoDao.modify(mingredientMono);
                iscurrentChanged =false;
            }
        }
    }

    private class SwipeDelMenuAdapter extends DrinkAdapter {

        public SwipeDelMenuAdapter(Context mContext, List<DrinkBean> mDatas) {
            super(mContext, mDatas);
        }

        @Override
        public SwipeDelMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.item_drink_swipe, parent, false));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            super.onBindViewHolder(holder, position);

            holder.itemView.findViewById(R.id.btnDel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 2018/2/11 delete the ingredient
                    Ingredient ingredient = mingredientDao.getIngredient(mAdapter.getDatas().get(holder.getAdapterPosition()).getPid());
                    String buildStructure = mingredientDao.deleteIngredientById(ingredient, 0);
                    if(!"".equals(buildStructure)) {
                        String cmd =  new CmdMakeIngredient().buildCmd(Constant.OPCMD_DELETE, ingredient.getPid(),AndroidUtils_Ext.oct2Hex(ingredient.getType()), buildStructure);
                        getApp().addCmdQueue(cmd);
                    }
                    ((SwipeMenuLayout) holder.itemView).quickClose();
                    mDatas.remove(holder.getAdapterPosition());
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    notifyDataSetChanged();

                }
            });
        }
    }
}
