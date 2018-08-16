package android.luna.Activity.ServiceUi.Setting.Payment.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.BaseActivity;
import android.luna.BlueCom.BlueActionDefine;
import android.luna.SDK.Wechat.WXPayConfigImpl;
import android.luna.SDK.Wechat.WXPayUtil;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class PayOnlineFragment extends Fragment implements View.OnClickListener {
    private SettingItemTextView2 auth_wechat_info,auth_wechat_import;
    private SettingItemTextView2 ip,net;
    private MaterialDialog progressDialog;
    private int currentkeymode =0;
    private IntentFilter filter;
    private String usbpath="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_online, container, false);
         filter = new IntentFilter();
        filter.addAction(BlueActionDefine.ACTION_USB_REMOVE);
        filter.addAction(BlueActionDefine.ACTION_USB_INSERT);

        InitView(view);
        InitEvent();
        return view;
    }
    public void InitView(View view)
    {
        net = view.findViewById(R.id.net);
        net.setTextValue(WXPayUtil.isNetworkAvalible(getActivity())?getString(R.string.SVR_PAY_ONLINE_ON):getString(R.string.SVR_PAY_ONLINE_OFF));
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

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BlueActionDefine.ACTION_USB_INSERT.equals(action)) {
                usbpath = ((BaseActivity)getActivity()).getApp().getUsbpath();
                if(progressDialog!=null && currentkeymode!=0)
                progressDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.VISIBLE);
            } else if (BlueActionDefine.ACTION_USB_REMOVE.equals(action)) {
                usbpath="";
                if(progressDialog!=null && currentkeymode!=0)
                {
                    progressDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.INVISIBLE);
                }
            }
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    private void importAuthfile()
    {
        if(!usbpath.equals("") && currentkeymode!=0)
        {
            if(currentkeymode == 1)
            {
                String filepath = usbpath+"/key/pay.wuk";
                if(FileHelper.isDirExist(filepath))
                {
                    if(FileHelper.copyFile(new File(filepath),FileHelper.PATH_KEY,"pay.wuk"))
                    {
                        progressDialog.dismiss();
                        // TODO: 2018/8/16
                        try {
                            WXPayConfigImpl.getInstance().reloadWechatkey();
                            auth_wechat_info.setTextValue(WXPayConfigImpl.getInstance().getKeyInfo());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        progressDialog.dismiss();
                    }
                }
            }
            else if(currentkeymode == 2)
            {

            }
        }
    }
    private void showtip()
    {
       progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Import auth file")
                .content("Please insert the usb stick with the auth file,then press import button!")
                .positiveText("Import")
                .positiveColor(getResources().getColor(R.color.green_grass))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO: 2018/8/16 import the auth file
                        importAuthfile();
                    }
                })
                .negativeText("Later")
               .negativeColor(getResources().getColor(R.color.red_wine))
               .onNegative(new MaterialDialog.SingleButtonCallback() {
                   @Override
                   public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       progressDialog.dismiss();
                   }
               })
               .canceledOnTouchOutside(false)
               .show();
                currentkeymode=1;
                progressDialog.getActionButton(DialogAction.POSITIVE).setVisibility(View.INVISIBLE);
                progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        progressDialog =null;
                        currentkeymode =0;
                    }
                });
    }
    private void InitEvent()
    {
        auth_wechat_import.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch ( v.getId())
        {
            case R.id.auth_wechat_import:
                // TODO: 2018/8/6 tip insert the usb key & import the authfile
                showtip();
                break;
        }
    }
}
