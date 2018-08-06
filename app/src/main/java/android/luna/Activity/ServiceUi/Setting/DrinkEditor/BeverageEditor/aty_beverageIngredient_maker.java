package android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.BaseFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.EsperssoFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.FilterbrewFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.InstantFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.WaterFragment;
import android.luna.Data.DAO.EspressoDao;
import android.luna.Data.DAO.FilterBrewDao;
import android.luna.Data.DAO.IngredientDao;
import android.luna.Data.DAO.InstantDao;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.DAO.WaterDao;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientWater;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.rs232.Cmd.CmdMakeIngredient;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/26.
 */

public class aty_beverageIngredient_maker  extends BaseActivity implements View.OnClickListener{
    private static final String INDEX_STRING_TOP = "↑";
    private Button btn_save,btn_close,btn_test;
    private RecyclerView mRv;
    private MaterialDialog progressDialog;
    private FilterbrewFragment Tb_Filterbrew;
    private InstantFragment Tb_Instant;
    private WaterFragment Tb_Water;
    private EsperssoFragment Tb_espresso;
    private int mIngrendientPid=0;
    private int m_ingredientType;
    private boolean iscurrentChanged =false;
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
    private IngredientDao mingredientDao=null;
    private FilterBrewDao mfilterBrewDao=null;
    private InstantDao minstantDao=null;
    private WaterDao mwaterDao=null;
    private EspressoDao mespressoDao=null;
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
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_MAKE_INGREDIENT_ACK);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        registerReceiver(receiver, filter);
        updateIngredientUi(getApp().getMcurrentIngredientPid());
    }
    private void showSavewindow()
    {
        progressDialog = new MaterialDialog.Builder(aty_beverageIngredient_maker.this)
                .title("saving ingredient")
                .canceledOnTouchOutside(false)
                .content("saving...")
                .progress(true, 0)
                .show();
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
                int op = intent.getIntExtra("OP",4);
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
                    }
                    if (ackresult == 1) {
                        showToast("save ingredient ok");
                    } else {
                        showToast("save ingredient failed:" + ackresult);
                    }
                }
            }
        }};
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
        findViewById(R.id.rly_hide).setVisibility(View.GONE);
    }
    @Override
    public void InitData() {
        super.InitData();
        powderFactory = new PowderFactory(this,getApp());
        powderItems =powderFactory.getPowerItemDao().queryall();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_save.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        btn_test.setOnClickListener(this);
    }

    private void updateIngredientUi(int pid ) {

        mIngrendientPid = pid;
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
                        }
                    });
                    getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Instant).commit();
                }
                break;
            case Ingredient.TYPE_FILTER_BREW_ADVANCE:
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
                        }
                    });
                    getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_Water).commit();
                }
                break;
            case  Ingredient.TYPE_ESPRESSO:
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
                        }
                    });
                    getFragmentManager().beginTransaction().replace(R.id.flyt_ingredient, Tb_espresso).commit();
                }
                break;
        }
    }

    public int getmIngrendientPid() {
        return mIngrendientPid;
    }

    @Override
    public void onClick(View view) {
        int pid;
        switch (view.getId())
        {
            case R.id.btn_close:
                if(iscurrentChanged)
                {
                    SaveIngredient();
                }
                AppManager.getAppManager().finishActivity(aty_beverageIngredient_maker.this);
                break;
            case R.id.btn_test:
                perviewtest();
                break;
            case R.id.btn_save:
                SaveIngredient();
                break;
        }
    }
    private void perviewtest()
    {
        CmdMakeIngredient cmdMakeIngredient = new CmdMakeIngredient();
        String ingredientStructure;
        if(m_ingredientType == Ingredient.TYPE_FILTER_BREW)
        {
            if(mingredientFilterBrew!=null && Tb_Filterbrew!=null)
            {
                Tb_Filterbrew.save();
                ingredientStructure = cmdMakeIngredient.buildFilterBrewStructure(mingredientFilterBrew);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientFilterBrew.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_FILTER_BREW), ingredientStructure));
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_INSTANT)
        {
            if(mingredientInstant!=null && Tb_Instant!=null)
            {
                Tb_Instant.save();
                ingredientStructure = cmdMakeIngredient.buildInstantStructure(mingredientInstant);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientInstant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure));
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_WATER)
        {
            if(mingredientWater!=null && Tb_Water!=null)
            {
                Tb_Water.save();
                ingredientStructure = cmdMakeIngredient.buildWaterStructure(mingredientWater);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientWater.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_WATER), ingredientStructure));
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_ESPRESSO)
        {
            if(mingredientEspresso!=null && Tb_espresso!=null)
            {
                Tb_espresso.save();
                ingredientStructure = cmdMakeIngredient.buildEspressoStructure(mingredientEspresso);
                getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_PREVIEW, mingredientEspresso.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_ESPRESSO), ingredientStructure));
            }
        }
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
                iscurrentChanged =false;
            }
        }
        else if(m_ingredientType == Ingredient.TYPE_INSTANT) {
            if (mingredientInstant != null && Tb_Instant != null) {
                Tb_Instant.save();
                ingredientStructure = cmdMakeIngredient.buildInstantStructure(mingredientInstant);
                if (mingredientInstant.getCreatestatus() == 1) {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_ADD, mingredientInstant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure));
                } else if (mingredientInstant.getCreatestatus() == 3) {
                    showSavewindow();
                    getApp().addCmdQueue(cmdMakeIngredient.buildCmd(Constant.OPCMD_MODIFY, mingredientInstant.getPid(), AndroidUtils_Ext.oct2Hex(Ingredient.TYPE_INSTANT), ingredientStructure));
                }
                iscurrentChanged = false;
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
                iscurrentChanged =false;
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
                iscurrentChanged =false;
            }
        }
    }
}
