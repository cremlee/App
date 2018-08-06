package android.luna.Activity.ServiceUi.Setting.Payment.fragment;

import android.app.Fragment;
import android.luna.SDK.Wechat.WXPayConfigImpl;
import android.luna.SDK.Wechat.WXPayUtil;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class PayOnlineFragment extends Fragment implements View.OnClickListener {
    private SettingItemTextView2 auth_wechat_info,auth_wechat_import;
    private SettingItemTextView2 ip,net;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_online, container, false);
        InitView(view);
        return view;
    }
    public void InitView(View view)
    {
        net = view.findViewById(R.id.net);
        net.setTextValue(WXPayUtil.isNetworkAvalible(getActivity())?"Online":"Offline");
        ip = view.findViewById(R.id.ip);
        ip.setTextValue(WXPayUtil.getLocalIpAddress(getActivity()));
        auth_wechat_info = view.findViewById(R.id.auth_wechat_info);
        try {
            auth_wechat_info.setTextValue(WXPayConfigImpl.getInstance().getKeyInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        auth_wechat_import = view.findViewById(R.id.auth_wechat_import);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId())
        {
            case R.id.auth_wechat_import:
                // TODO: 2018/8/6 tip insert the usb key & import the authfile
                break;
        }
    }
}
