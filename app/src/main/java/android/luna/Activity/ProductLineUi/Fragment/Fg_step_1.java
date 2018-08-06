package android.luna.Activity.ProductLineUi.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.Payment.PayConstant;
import android.luna.Data.module.Payment.Wechat.Pay_Queryorder;
import android.luna.Data.module.Payment.Wechat.Pay_Refundorder;
import android.luna.Data.module.Payment.Wechat.Pay_Unifiedorder;
import android.luna.Data.module.Payment.Wechat.ProductUnifiedorder;
import android.luna.Data.module.Payment.Wechat.RefundTest;
import android.luna.Data.module.Payment.Wechat.RefundTesthttpok;
import android.luna.SDK.Wechat.WXPay;
import android.luna.SDK.Wechat.WXPayConfigImpl;
import android.luna.SDK.Wechat.WXPayUtil;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Qrcode.QrCodeFactory;
import android.luna.rs232.Cmd.CmdCleanMachine;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.util.logging.LogRecord;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/6.
 */

public class Fg_step_1 extends  productionFragment implements View.OnClickListener{
    private TextView info;
    private String cmd="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_step_1, container, false);
        InitView(view);
        try {
            cmd = new CmdCleanMachine().buildCmdStart(DeviceXmlFactory.getCleanComponent(FileHelper.FILE_DEVICE_CONFIG,DeviceXmlFactory.CLEAN_TYPE_DRY_OPEN));
        }
        catch (Exception e){}
        return view;
    }

    @Override
    public void InitView(View view) {
        view.findViewById(R.id.open).setOnClickListener(this);
        info=view.findViewById(R.id.info);
    }
    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.open:
                    // TODO: 2018/7/12 da kai jiqi de shui fa
                    if(!cmd.equals(""))
                        ((BaseActivity)getActivity()).getApp().addCmdQueue(cmd);
                    break;
            }
    }
}
