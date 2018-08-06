package android.luna.Activity.ServiceUi.Setting.Payment.fragment;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Payment.PayFragment;
import android.luna.Data.DAO.PaymentDao;
import android.luna.Data.module.PaymentSetting;
import android.luna.ViewUi.widget.SettingItemCheckBox;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class PayNormalFragment extends PayFragment {
    private PaymentDao paymentDao;
    private PaymentSetting paymentSetting;
    private SettingItemCheckBox wechat;

    @Override
    public void onPause() {
        super.onPause();
        paymentDao.Update(paymentSetting);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_normal, container, false);
        InitData();
        InitView(view);
        InitEvent();
        return view;
    }
    @Override
    public void InitView(View view) {
        wechat = view.findViewById(R.id.wechat);
        wechat.getCheckBox().setChecked(paymentSetting.getIswechaton()==1);
    }

    @Override
    public void InitData() {
        paymentDao = new PaymentDao(getActivity(),((BaseActivity)getActivity()).getApp());
        paymentSetting = paymentDao.Query();
        if(paymentSetting == null)
        {
            paymentSetting = new PaymentSetting(1);
            paymentDao.Update(paymentSetting);
        }
    }

    @Override
    public void InitEvent() {
        wechat.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                paymentSetting.setIswechaton(isChecked?1:0);
            }
        });
    }


}
