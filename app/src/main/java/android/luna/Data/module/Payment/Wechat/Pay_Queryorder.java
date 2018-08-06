package android.luna.Data.module.Payment.Wechat;

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

public class Pay_Queryorder extends Thread {
    private Handler mhandler;
    public void setQuerycount(int querycount) {
        this.querycount = querycount;
    }

    private int querycount =20;
    private ProductQueryorder productQueryorder;
    private WXPay wxpay;
    public Pay_Queryorder(Handler handler,WXPay wxpay) {
        this.wxpay = wxpay;
        mhandler = handler;
    }
    @Override
    public void run() {
        if (wxpay == null) {
            mhandler.sendEmptyMessage(PayConstant.PAY_CONFIG_FAILED);
            return;
        }
        productQueryorder = new ProductQueryorder(wxpay.getouttradeno());
        Map<String, String> data = productQueryorder.TtoMap();
        while ((querycount--)>0) {
            try {
                Map<String, String> r = wxpay.orderQuery(data);
                Log.i("pay","orderQuery>>>>"+r.toString());
                if (r.get("return_code").equalsIgnoreCase("SUCCESS"))  //通信标识
                {
                    if (r.get("result_code").equalsIgnoreCase("SUCCESS")) {
                        if (r.get("trade_state").equalsIgnoreCase("SUCCESS")) {
                            wxpay.setTransactionid(r.get("transaction_id"));
                            mhandler.sendEmptyMessage(PayConstant.PAY_ORDER_FINISHED);
                            return;
                        }
                    }
                }
                Thread.sleep(3000);
            } catch (Exception e) {
            }
        }
        // TODO: 2018/7/9  关闭交易
        mhandler.sendEmptyMessage(PayConstant.PAY_UNIFIEDORDER_FAILED);
    }
}