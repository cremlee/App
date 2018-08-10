package android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.fragment.BeverageBasicFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.fragment.BeverageIngredientFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.fragment.BeverageUiFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.BaseFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.DrinkAdapter;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.decoration.DividerItemDecoration;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.model.DrinkBean;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.IngredientDao;
import android.luna.Data.DAO.IngredientFactoryDao;
import android.luna.Data.module.BeverageBasic;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Data.module.BeverageUi;
import android.luna.Data.module.DrinkName;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientWater;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.rs232.Ack.AckQuery;
import android.luna.rs232.Cmd.CmdMakeBeverage;
import android.luna.rs232.Cmd.CmdMakeIngredient;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/12.
 */

public class aty_beverage_maker extends BaseActivity implements View.OnClickListener {
    private static final String INDEX_STRING_TOP = "↑";
    private PopupWindow mPopWindow;
    private boolean showmPopWindow;
    private Button btn_save,btn_close,btn_test,btn_ui,btn_basci,btn_ingredient;
    private RecyclerView mRv;
    private SwipeDelMenuAdapter mAdapter;
    private LinearLayoutManager mManager;
    private List<DrinkBean> mDatas = new ArrayList<>();
    private SuspensionDecoration mDecoration;
    private IndexBar mIndexBar;
    private TextView mTvSideBarHint;
    private MaterialDialog progressDialog;
    private String opType = Constant.OPCMD_MODIFY;
    private int mcurrentBeveragePid = 0;


    public BeverageFactoryDao getMbeverageFactoryDao() {
        return mbeverageFactoryDao;
    }

    private BeverageFactoryDao mbeverageFactoryDao=null;
    private List<BeverageBasic> beverageBasicList=null;
    private List<DrinkName> drinkNameList=null;
    private BeverageBasicFragment Tb_Basic =null;
    private BeverageUiFragment Tb_Ui =null;
    private BeverageIngredientFragment Tb_IngredientStep =null;
    private Fragment Tb_DrinkName =null;

    public BeverageBasic getMbeverageBasic() {
        return mbeverageBasic;
    }

    public void setMbeverageBasic(BeverageBasic mbeverageBasic) {
        this.mbeverageBasic = mbeverageBasic;
    }

    private BeverageBasic mbeverageBasic=null;

    public BeverageUi getMbeverageUi() {
        return mbeverageUi;
    }

    public void setMbeverageUi(BeverageUi mbeverageUi) {
        this.mbeverageUi = mbeverageUi;
    }

    private BeverageUi mbeverageUi=null;

    public List<BeverageIngredient> getMbeverageIngredientlist() {
        return mbeverageIngredientlist;
    }

    public void setMbeverageIngredientlist(List<BeverageIngredient> mbeverageIngredientlist) {
        this.mbeverageIngredientlist = mbeverageIngredientlist;
    }
    private List<BeverageIngredient> mbeverageIngredientlist=null;
    private DrinkName mdrinkName =null;

    public IngredientFactoryDao getMingredientFactoryDao() {
        return mingredientFactoryDao;
    }

    private IngredientFactoryDao  mingredientFactoryDao=null;

    private boolean iscurrentChanged =false;
    private int _lastpostion =-1;
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
                    showToast("preview Beverage ok");
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mingredientFactoryDao = new IngredientFactoryDao(this,getApp());
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_MAKE_BEVERAGE_ACK);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        registerReceiver(receiver, filter);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constant.ACTION_MAKE_BEVERAGE_ACK))
            {

                int ackresult = intent.getIntExtra("ACK",2);
                int op = intent.getIntExtra("OP",4);
                if(op!=4) {
                    if(progressDialog!=null)
                    {
                        progressDialog.dismiss();
                    }
                    if (mbeverageBasic != null) {
                        mbeverageBasic.setCreatestatus(ackresult == 1 ? 2 : mbeverageBasic.getCreatestatus());
                        mbeverageFactoryDao.getBeverageBasicDao().update(mbeverageBasic);
                    }
                    if (ackresult == 1) {
                        showToast("save Beverage ok");
                    } else {
                        showToast("save Beverage failed:" + ackresult);
                    }
                }else
                {
                    mHandler.sendEmptyMessageDelayed(1000,makeDrinkTime());
                }

            }
        }
    };

    private int makeDrinkTime() {
        int totalTime = 0;
        try {
            List<BeverageIngredient> beverageIngredients = mbeverageFactoryDao.getBeverageIngerdient().queryforbeveragepid(mbeverageBasic.getPid());
            int tempTime = 0;
            for (int i = 0; i < beverageIngredients.size(); i++) {
                BeverageIngredient beverageIngredient = beverageIngredients.get(i);
                int ingredientType = beverageIngredient.getIngredientType();
                int startTime = beverageIngredient.getStartTime();// *																	// (beverageIngredient.getScaleUp()																	// / 100);
                int ingredientTime = 0;
                switch (ingredientType) {
                    case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                        /*IngredientFilterBrewAdvance filterBrewadv = getHelper().getIngredientFilterBrewAdvanceDao().queryBuilder().where().eq("pid", beverageIngredient.getIngredientPid()).queryForFirst();
                        if(filterBrewadv!=null)
                        {
                            FilterBrewStepDao aa = new FilterBrewStepDao(this,getApp());
                            ingredientTime = 20000+aa.getsteptime(beverageIngredient.getIngredientPid());
                        }*/
                        break;
                    case Ingredient.TYPE_FILTER_BREW:
                        IngredientFilterBrew filterBrew = mingredientFactoryDao.getFilterBrewDao().findByT(beverageIngredient.getIngredientPid());
                        if (filterBrew != null) {						// 计算总时间
                            final int BREW_RUNNING_TIME = 10000;
                            int time = filterBrew.getTmPre13();
                            ingredientTime = (filterBrew.getDecompressTime() + filterBrew.getExtractionTime() + filterBrew.getOpenDelayTime() + time) * 100 + BREW_RUNNING_TIME;
                        }
                        break;
                    case Ingredient.TYPE_INSTANT:
                        IngredientInstant instant = mingredientFactoryDao.getInstantDao().findByT(beverageIngredient.getIngredientPid());
                        if (instant != null) {
                            ingredientTime = (int) ((instant.getWaterVolume() * beverageIngredient.getScaleUp() / 100 + instant.getPreflushVolume() + instant.getWaterAfterFlushVolume()) * 1000 / 20) + (instant.getPauseTimeAfterDispense() + instant.getPreflushPauseTime()) + 2000;
                        }
                        break;
                    case Ingredient.TYPE_WATER:
                        IngredientWater water = mingredientFactoryDao.getWaterDao().findByT(beverageIngredient.getIngredientPid());
                        if (water != null) {
                            ingredientTime = (int) (water.getWaterVolume() / 20 * 1000);
                        }
                        break;
                    case Ingredient.TYPE_ESPRESSO:
                        IngredientEspresso espresso = mingredientFactoryDao.getEspressoDao().findByT(beverageIngredient.getIngredientPid());
                        if(espresso!=null)
                        {
                            ingredientTime = (int) (espresso.getBrewtime()+espresso.getPrebrewtime()+espresso.getPreinfusiontime())*1000+10000;
                        }
                        break;
                    default:
                        break;
                }
                tempTime = startTime + ingredientTime;
                totalTime = tempTime > totalTime ? tempTime : totalTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        EvoTrace.e("time", "makeDrinkTime:" + (totalTime) );
        return (totalTime + 4000) * 1;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        mHandler.removeMessages(1000);
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_drinkedit_beverage);
        btn_save =  findViewById(R.id.btn_save);
        btn_close =  findViewById(R.id.btn_close);
        btn_test =  findViewById(R.id.btn_test);
        btn_ui = findViewById(R.id.btn_ui);
        btn_basci= findViewById(R.id.btn_basci);
        btn_ingredient= findViewById(R.id.btn_ingredient);

        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(mManager = new LinearLayoutManager(this));

        mRv.setAdapter(mAdapter);
        mRv.addItemDecoration(mDecoration = new SuspensionDecoration(this, mDatas));
        mRv.addItemDecoration(new DividerItemDecoration(aty_beverage_maker.this, DividerItemDecoration.VERTICAL_LIST));
        mTvSideBarHint =  findViewById(R.id.tvSideBarHint);//HintTextView
        mIndexBar = findViewById(R.id.indexBar);//IndexBar
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mDatas);
    }

    @Override
    public void InitData() {
        super.InitData();
        mbeverageFactoryDao = new BeverageFactoryDao(this,getApp());
        mDatas = new ArrayList<>();
        mAdapter = new SwipeDelMenuAdapter(this, mDatas);
        drinkNameList = mbeverageFactoryDao.getBeverageNameDao().queryallByLocale();
        mDatas.add((DrinkBean) new DrinkBean(999,"Add new beverage","",false,false).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
        if(drinkNameList!=null && drinkNameList.size()>0)
        {
            for (DrinkName item: drinkNameList )
            {
                DrinkBean drinkBean = new DrinkBean(item.getPid(),item.getName(),"",true,false);
                mDatas.add(drinkBean);
            }
        }
        mAdapter.setDatas(mDatas);
        mAdapter.notifyDataSetChanged();
    }

    private void showsavewin()
    {
        progressDialog = new MaterialDialog.Builder(aty_beverage_maker.this)
                .title("saving")
                .canceledOnTouchOutside(false)
                .content("waiting...")
                .progress(true, 0)
                .show();
    }
    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_save.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_ui.setOnClickListener(this);
        btn_basci.setOnClickListener(this);
        btn_ingredient.setOnClickListener(this);
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
                    progressDialog = new MaterialDialog.Builder(aty_beverage_maker.this)
                            .title("Save")
                            .content("your beverage has been changed ,do you want to save it?")
                            .positiveText("Save")
                            .positiveColor(getResources().getColor(R.color.green_grass))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mAdapter.rollback(_lastpostion);
                                    progressDialog.dismiss();

                                    SaveBeverage();
                                }
                            })
                            .negativeText("Later")
                            .negativeColor(getResources().getColor(R.color.red_wine))
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    UpdateBeverage(pos);
                                }
                            })
                            .canceledOnTouchOutside(false)
                            .show();
                }
                else {
                    UpdateBeverage(pos);
                }
            }
        });
    }

    public void saveUiData()
    {
        mbeverageFactoryDao.getBeverageUiDao().update(mbeverageUi);
    }

    private void SwitchBeverageInfo(int id,String name)
    {
        switch (id)
        {
            case R.id.btn_ingredient:
                mbeverageIngredientlist = mbeverageFactoryDao.getBeverageIngerdient().queryforbeveragepid(mcurrentBeveragePid);
                if(Tb_IngredientStep == null)
                    Tb_IngredientStep =new BeverageIngredientFragment();
                if(Tb_IngredientStep.isAdded()){
                    Tb_IngredientStep.InitView(null);
                }

                getFragmentManager().beginTransaction().replace(R.id.flyt_beverage, Tb_IngredientStep).commit();
                break;
            case R.id.btn_ui:
                mbeverageUi = mbeverageFactoryDao.getBeverageUiDao().query(mcurrentBeveragePid);
                if(mbeverageUi!=null)
                {
                    if(Tb_Ui == null)
                        Tb_Ui =new BeverageUiFragment();
                    if(Tb_Ui.isAdded()){
                        //Tb_IngredientStep.InitView(null);
                    }
                    getFragmentManager().beginTransaction().replace(R.id.flyt_beverage, Tb_Ui).commit();
                }
                break;
            case R.id.btn_basci:
                mbeverageBasic = mbeverageFactoryDao.getBeverageBasicDao().query(mcurrentBeveragePid);
                if(mbeverageBasic!=null)
                {
                    mbeverageBasic.setName(name);
                    if(Tb_Basic == null)
                        Tb_Basic = new BeverageBasicFragment();
                    if(Tb_Basic.isAdded())
                    {
                        //refresh ui
                        Tb_Basic.InitView(mbeverageBasic);
                    }
                    Tb_Basic.SetOningredientChanged(new BaseFragment.OningredientChanged() {
                        @Override
                        public void itemchanged(int type) {
                            iscurrentChanged = true;
                            if(type==99)
                            {
                                //// TODO: 2018/2/11 shuxin lbiao xianshi
                                mDatas.get(_lastpostion).setdrinkname(mbeverageBasic.getName());
                                mAdapter.notifyDataSetChanged();
                                DrinkName tmp =mbeverageFactoryDao.getBeverageNameDao().queryByPid(mcurrentBeveragePid);
                                if(tmp!=null)
                                {
                                    tmp.setName(mbeverageBasic.getName());
                                    mbeverageFactoryDao.getBeverageNameDao().modify(tmp);
                                }
                            }
                        }
                    });
                    getFragmentManager().beginTransaction().replace(R.id.flyt_beverage, Tb_Basic).commit();
                }
                break;
        }
    }

    private void CreateNewOne()
    {
        int pid = mbeverageFactoryDao.getBeverageBasicDao().getNewPid();
        mbeverageBasic = new BeverageBasic();
        mbeverageBasic.setPid(pid);
        mbeverageBasic.setName("new beverage");
        mbeverageUi =new BeverageUi();
        mbeverageUi.setPid(pid);
        mbeverageUi.setName("new beverage");
        DrinkName drinkName = new DrinkName();
        drinkName.setName("new beverage");
        drinkName.setPid(pid);
        drinkName.setLocalinfo(mbeverageFactoryDao.getBeverageNameDao().getlocalinfo());
        mbeverageFactoryDao.getBeverageNameDao().create(drinkName);
        mbeverageFactoryDao.getBeverageBasicDao().create(mbeverageBasic);
        mbeverageFactoryDao.getBeverageUiDao().create(mbeverageUi);
        mDatas.add(1, (DrinkBean) new DrinkBean(pid, "new beverage", "", true, true).setTop(true).setBaseIndexTag(INDEX_STRING_TOP));
        mAdapter.setDatas(mDatas);
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(mDatas)//设置数据
                .invalidate();
        mAdapter.rollback(1);
        UpdateBeverage(1);
        iscurrentChanged =true;
    }

    private  void UpdateBeverage(int pos)
    {
        _lastpostion = pos;
        if(_lastpostion == 0) // add new beverage
        {
            CreateNewOne();
            opType = Constant.OPCMD_ADD;
        }
        else //shua xin jiemian xinxi
        {
            mcurrentBeveragePid = mAdapter.getDatas().get(pos).getPid();
            SwitchBeverageInfo(R.id.btn_basci,mAdapter.getDatas().get(pos).getDrinkname());
            opType = Constant.OPCMD_MODIFY;
        }
    }

    private void SaveBeverage()
    {
        Tb_Basic.save();
        if(mbeverageBasic!=null)
        {
            // TODO: 2018/7/2 send cmd
            String beveragecmd = getbeveragecmd();
            if("".equals(beveragecmd))
            {
                showToast("Can not save!!!");
            }
            else {
                showsavewin();
                CmdMakeBeverage cmdMakeBeverage = new CmdMakeBeverage();
                if(mbeverageBasic.getCreatestatus() ==1)
                {
                    opType = Constant.OPCMD_ADD;
                }else
                {
                    opType = Constant.OPCMD_MODIFY;
                }
                getApp().addCmdQueue(cmdMakeBeverage.buildCmd(opType, beveragecmd));
            }
            mbeverageFactoryDao.getBeverageBasicDao().update(mbeverageBasic);
        }
        iscurrentChanged =false;
    }

    private void PreviewTest()
    {
        Tb_Basic.save();
        if(mbeverageBasic!=null)
        {
            // TODO: 2018/7/2 send cmd
            String beveragecmd = getbeveragecmd();
            if("".equals(beveragecmd))
            {
                showToast("Can not save!!!");
            }
            else {
                showsavewin();
                CmdMakeBeverage cmdMakeBeverage = new CmdMakeBeverage();
                getApp().addCmdQueue(cmdMakeBeverage.buildCmd(Constant.OPCMD_PREVIEW, beveragecmd));
            }
        }
    }
    private String getbeveragecmd()
    {
        List<BeverageIngredient> beverageIngredientList=  mbeverageFactoryDao.getBeverageIngerdient().queryforbeveragepid(mbeverageBasic.getPid());
        int ingredientCount = 0;
        if(beverageIngredientList!=null && beverageIngredientList.size()>0)
        {
             ingredientCount = beverageIngredientList.size();
        }
        if(ingredientCount==0)
            return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append(AndroidUtils_Ext.oct2Hex2(mbeverageBasic.getPid()));								// Beverage ID
        buffer.append(AndroidUtils_Ext.oct2Hex(ingredientCount));
        BeverageIngredient bi;
        for (int i = 0; i < ingredientCount; i++) {
            bi = beverageIngredientList.get(i);
            int ingredientPid = bi.getIngredientPid(); 				// Ingredient ID
            buffer.append(AndroidUtils_Ext.oct2Hex2(ingredientPid));			// Ingredient ID
            buffer.append(AndroidUtils_Ext.oct2Hex(bi.getIngredientType()));	// Ingredient Id Type;
            buffer.append(AndroidUtils_Ext.oct2Hex2(bi.getStartTime()));		// Start Time
            buffer.append(AndroidUtils_Ext.oct2Hex2(bi.getScaleUp()));
        }
        /*Additional Msg 16 byte*/
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getCupSensor()));						// cupSensor
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getUseCount()));							// Usecount
        buffer.append(AndroidUtils_Ext.oct2Hex(Integer.valueOf(mbeverageBasic.getLedMode())));			// Led mode
        buffer.append(AndroidUtils_Ext.oct2Hex(Integer.valueOf(mbeverageBasic.getLedColor())));		// Led color
        buffer.append(AndroidUtils_Ext.oct2Hex(0));												// Led  intensity
        buffer.append(AndroidUtils_Ext.oct2Hex(Integer.valueOf(mbeverageBasic.getDispenseType())));	// Dispense Type
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getStrength()));							// strength adjust
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getVolume()));							// Volume adjust
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getMilk()));								// Milk adjust
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getSugar()));							// Sugar adjust
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getTopping()));							// topping
        buffer.append(AndroidUtils_Ext.oct2Hex(mbeverageBasic.getJug()));								// Cups adjust
        buffer.append(AndroidUtils_Ext.float2Hex2(mbeverageBasic.getDrinkPrice()));					// Price value 2byte
        buffer.append("0000");
        return buffer.toString();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_ui:
            case R.id.btn_basci:
            case R.id.btn_ingredient:
                if(mbeverageBasic!=null)
                    SwitchBeverageInfo(id,mbeverageBasic.getName());
                break;
            case R.id.btn_test:
                if(getApp().getAckQueryInstance().getMachine_state()!= AckQuery.MS_BLOCK_MODE && mbeverageBasic!=null)
                    PreviewTest();
                else
                    showTestToast("Machine Error state:"+getApp().getAckQueryInstance().getMachine_state());
                break;
            case R.id.btn_save:
                if(mbeverageBasic!=null)
                    SaveBeverage();
                break;
            case R.id.btn_close:
                getApp().setIsmainpagereload(true);
                AppManager.getAppManager().finishActivity(aty_beverage_maker.this);
                break;
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
                    //// TODO: 2018/2/11 delete the beverage
                    int beveragepid =mAdapter.getDatas().get(holder.getAdapterPosition()).getPid();
                    String cmd =  new CmdMakeBeverage().buildCmd(Constant.OPCMD_DELETE,mbeverageFactoryDao.deleteCmd(beveragepid));
                    if(cmd!=null)
                        getApp().addCmdQueue(cmd);
                    mbeverageFactoryDao.DeleteBeverage(beveragepid);
                    ((SwipeMenuLayout) holder.itemView).quickClose();
                    mDatas.remove(holder.getAdapterPosition());
                    mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                            .setNeedRealIndex(true)//设置需要真实的索引
                            .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                            .setmSourceDatas(mDatas)//设置数据
                            .invalidate();
                    notifyDataSetChanged();
                    _lastpostion =-1;
                    rollback(0);
                    if(Tb_Basic!=null && Tb_Basic.isAdded())
                        getFragmentManager().beginTransaction().remove(Tb_Basic).commit();
                    if(Tb_Ui!=null && Tb_Ui.isAdded())
                        getFragmentManager().beginTransaction().remove(Tb_Ui).commit();
                    if(Tb_IngredientStep!=null && Tb_IngredientStep.isAdded())
                        getFragmentManager().beginTransaction().remove(Tb_IngredientStep).commit();

                }
            });
        }
    }

}
