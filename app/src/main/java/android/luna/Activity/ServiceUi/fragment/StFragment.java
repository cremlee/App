package android.luna.Activity.ServiceUi.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.CustomerUI.Gallery.Adapter.MachineWarningAdapter;
import android.luna.Activity.ServiceUi.Adapter.CanisterStockAdapter;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.DAO.StockFactoryDao;
import android.luna.Data.module.CanisterItemStock;
import android.luna.Data.module.WasterBinStock;
import android.luna.ViewUi.DashBoard.DialChart01View;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class StFragment extends Fragment implements View.OnClickListener {
    private RecyclerView rv_canister;
    private LinearLayoutManager mLinearLayoutManager;
    private StockFactoryDao _stockFactoryDao;
    private PowderFactory _powderFactory;
    private List<CanisterItemStock> canisterItemStockList;
    private WasterBinStock wasterBinStock;
    private CanisterStockAdapter canisterStockAdapter;
    private DialChart01View dash_ntc;
    private CremApp app;
    private TextView waster_per,error_help;
    private MaterialDialog tipdialog;
    private ListView errorlsv;
    private MachineWarningAdapter warningAdapter;
    private TextView st_door,st_water,st_drip,st_bin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_st_impove, container, false);

        InitView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _stockFactoryDao = new StockFactoryDao(getActivity(),((BaseActivity)getActivity()).getApp());
        _powderFactory = new PowderFactory(getActivity(),((BaseActivity)getActivity()).getApp());
        canisterItemStockList = _stockFactoryDao.getCanisterStockDao().queryall();
        if(canisterItemStockList!=null && canisterItemStockList.size()>0)
        {
            for (CanisterItemStock itemStock:canisterItemStockList)
            {
                itemStock.PowderName = _powderFactory.getPowerItemDao().getNamebyPid(itemStock.getPid());
            }
        }
        canisterStockAdapter = new CanisterStockAdapter(canisterItemStockList,getActivity());
        canisterStockAdapter.setOnItemRefillListener(new CanisterStockAdapter.ItemRefillListener() {
            @Override
            public void ItemClicked(CanisterItemStock a) {
                ((BaseActivity)getActivity()).showToast(a.toString());
                ShowCanisterTip("Do you want to refill the canister?",a);
                // TODO: 2018/7/30 show refill msg
            }
        });
        wasterBinStock = _stockFactoryDao.getWasterbinStockDao().queryByPid(99);
    }
    private void ShowWasterbinTip()
    {
        tipdialog = new MaterialDialog.Builder(getActivity())
                .title("Maintence")
                .content("Do you want to empty the Wasterbin?")
                .positiveText("Yes")
                .positiveColor(getResources().getColor(R.color.green_grass))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        wasterBinStock.Empty();
                        _stockFactoryDao.getWasterbinStockDao().update(wasterBinStock);
                        if(wasterBinStock!=null)
                            waster_per.setText((wasterBinStock.getCapability()-wasterBinStock.getStock())*100/wasterBinStock.getCapability()+"%");
                        tipdialog.dismiss();
                    }
                })
                .negativeText("Later")
                .negativeColor(getResources().getColor(R.color.red_wine))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tipdialog.dismiss();
                    }
                })
                .canceledOnTouchOutside(false)
                .show();
    }
    private void ShowCanisterTip(String info, final CanisterItemStock a)
    {
        tipdialog = new MaterialDialog.Builder(getActivity())
                .title("Maintence")
                .content(info)
                .positiveText("Yes")
                .positiveColor(getResources().getColor(R.color.green_grass))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        a.refill();
                        _stockFactoryDao.getCanisterStockDao().update(a);
                        canisterStockAdapter.notifyDataSetChanged();
                        tipdialog.dismiss();
                        app.setIsmainpagereload(true);
                    }
                })
                .negativeText("Later")
                .negativeColor(getResources().getColor(R.color.red_wine))
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tipdialog.dismiss();
                    }
                })
                .canceledOnTouchOutside(false)
                .show();
    }
    private void InitView(View view)
    {
        app = (CremApp) getActivity().getApplication();
        warningAdapter = new MachineWarningAdapter(app.getallMachineWarnList(),getActivity());
        rv_canister = view.findViewById(R.id.rv_canister);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rv_canister.setLayoutManager(mLinearLayoutManager);
        rv_canister.setAdapter(canisterStockAdapter);
        rv_canister.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                final int childCount = recyclerView.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    LinearLayout child = (LinearLayout) recyclerView.getChildAt(i);
                    RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                    lp.topMargin = 10;
                    lp.rightMargin=5;
                    lp.leftMargin =5;
                    lp.width = (recyclerView.getWidth()-60)/4;
                    child.setLayoutParams(lp);
                }
            }
        });
        dash_ntc = view.findViewById(R.id.dash_ntc);
        waster_per = view.findViewById(R.id.waster_per);
        if(wasterBinStock!=null)
        waster_per.setText((wasterBinStock.getCapability()-wasterBinStock.getStock())*100/wasterBinStock.getCapability()+"%");
        view.findViewById(R.id.lyt_waster).setOnClickListener(this);
        errorlsv = view.findViewById(R.id.errorlsv);
        error_help = view.findViewById(R.id.error_help);
        errorlsv.setAdapter(warningAdapter);
        st_door = view.findViewById(R.id.st_door);
        st_water = view.findViewById(R.id.st_water);
        st_drip = view.findViewById(R.id.st_drip);
        st_bin = view.findViewById(R.id.st_bin);
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if(app.getAckQueryInstance().getNtcHighTemperature()!=0xffffffff)
                        dash_ntc.setCurrentStatus(app.getAckQueryInstance().getNtcHighTemperature()/100.f);
                    if(app.getAckQueryInstance().getWaterHighState()!=0xffffffff)
                    {
                        st_water.setVisibility(View.VISIBLE);
                        st_water.setText(app.getAckQueryInstance().getWaterString());
                    }
                    else
                    {
                        st_water.setVisibility(View.GONE);
                    }
                    if(app.getAckQueryInstance().getIndexDoorState()!=0xffffffff)
                    {
                        st_door.setVisibility(View.VISIBLE);
                        st_door.setText(app.getAckQueryInstance().getDoorString());
                    }
                    else
                    {
                        st_door.setVisibility(View.GONE);
                    }
                    if(app.getAckQueryInstance().getIndexDriptrayState()!=0xffffffff)
                    {
                        st_drip.setVisibility(View.VISIBLE);
                        st_drip.setText(app.getAckQueryInstance().getDripString());
                    }
                    else
                    {
                        st_drip.setVisibility(View.GONE);
                    }
                    if(app.getAckQueryInstance().getIndexWasterBinState()!=0xffffffff)
                    {

                        st_bin.setVisibility(View.VISIBLE);
                        st_bin.setText(app.getAckQueryInstance().getBinString());
                    }
                    else
                    {
                        st_bin.setVisibility(View.GONE);
                    }
                    mHandler.sendEmptyMessageDelayed(1000,1000);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if(!mHandler.hasMessages(1000))
            mHandler.sendEmptyMessageDelayed(1000,1000);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_WARNING_NOTIFICATION);
        getActivity().registerReceiver(receiver, filter);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(Constant.ACTION_WARNING_NOTIFICATION.equals(action))
            {
                warningAdapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeMessages(1000);
        getActivity().unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.lyt_waster:
                ShowWasterbinTip();
                break;
        }
    }
}
