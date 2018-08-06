package android.luna.Data.module.Payment.Wechat;

import android.luna.Data.module.Payment.XmlDecode;

/**
 * Created by Lee.li on 2018/7/9.
 */

public class ProductQueryorder extends XmlDecode<ProductQueryorder> {
    private String out_trade_no;

    public ProductQueryorder(String transaction_id) {
        this.out_trade_no = transaction_id;
    }

    public String getTransaction_id() {
        return out_trade_no;
    }

    public void setTransaction_id(String transaction_id) {
        this.out_trade_no = transaction_id;
    }
}
