package android.luna.Data.module.Payment.Wechat;

import android.luna.Data.module.Payment.PayConstant;
import android.luna.SDK.Wechat.WXPay;
import android.os.Handler;
import android.util.Log;

import java.util.Map;

/**
 * Created by Lee.li on 2018/7/9.
 */

public class Pay_Refundorder extends Thread {
    private Handler mhandler;
    private ProductRefund productRefund;
    private WXPay wxpay;

    public Pay_Refundorder(Handler handler, WXPay wxpay) {
        this.wxpay = wxpay;
        mhandler = handler;
    }

    @Override
    public void run() {
        if (wxpay == null) {
            mhandler.sendEmptyMessage(PayConstant.PAY_CONFIG_FAILED);
            return;
        }
        productRefund = new ProductRefund("4200000136201807091160672239", "20180709132620crem", "20180709132620crem", 1, 1);
       // productRefund = new ProductRefund(wxpay.getTransactionid(), wxpay.getouttradeno(), wxpay.getouttradeno(), 1, 1);
        Map<String, String> data = productRefund.TtoMap();
        try {
            Map<String, String> r = wxpay.refund(data);
            Log.i("pay", "productRefund>>>>" + r.toString());
            if (r.get("return_code").equalsIgnoreCase("SUCCESS"))  //通信标识
            {
                if (r.get("result_code").equalsIgnoreCase("SUCCESS")) {

                }
            }
            Thread.sleep(3000);
        } catch (Exception e) {
           Log.i("pay", "productRefund Exception >>>>" + e.toString());
        }
    }
}