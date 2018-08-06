package android.luna.Data.module.Payment.Wechat;

import android.content.Context;
import android.luna.Data.module.Payment.PayConstant;
import android.luna.SDK.Wechat.WXPay;
import android.luna.SDK.Wechat.WXPayConfigImpl;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Qrcode.QrCodeFactory;
import android.os.Handler;
import android.util.Log;

import java.util.Map;

/**
 * Created by Lee.li on 2018/7/9.
 */

public class Pay_Unifiedorder extends Thread {
    private Handler mhandler;
    private ProductUnifiedorder productUnifiedorder;
    private WXPay wxpay;
    public Pay_Unifiedorder(Handler handler,WXPay wxpay) {
        this.wxpay = wxpay;
        mhandler = handler;
    }

    public void setProductUnifiedorder(ProductUnifiedorder productUnifiedorder) {
        this.productUnifiedorder = productUnifiedorder;
    }

    @Override
    public void run() {
        if (wxpay == null) {
            mhandler.sendEmptyMessage(PayConstant.PAY_CONFIG_FAILED);
            return;
        }
        Map<String, String> data = productUnifiedorder.TtoMap();
        if (data == null) {
            mhandler.sendEmptyMessage(PayConstant.PAY_CONFIG_FAILED);
            return;
        }
        try {
            Map<String, String> r = wxpay.unifiedOrder(data);
            if(r.get("return_code").equalsIgnoreCase("SUCCESS"))  //通信标识
            {
                if(r.get("result_code").equalsIgnoreCase("SUCCESS"))
                {
                    String urlstr = r.get("code_url");
                    wxpay.setouttradeno(productUnifiedorder.getOut_trade_no());
                    boolean isready= QrCodeFactory.createQRImage(urlstr,200,200,null, FileHelper.PATH_CONFIG+"qr_tmp.jpg");
                    if(isready)
                    {
                        // TODO: 2018/7/9 sendmsg to tell ui show qr to scan
                        mhandler.sendEmptyMessage(PayConstant.PAY_UNIFIEDORDER_OK);
                    }
                }
            }
        } catch (Exception e) {
            mhandler.sendEmptyMessage(PayConstant.PAY_UNIFIEDORDER_FAILED);
        }
    }
}