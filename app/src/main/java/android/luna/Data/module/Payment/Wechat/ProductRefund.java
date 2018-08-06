package android.luna.Data.module.Payment.Wechat;

import android.luna.Data.module.Payment.XmlDecode;

/**
 * Created by Lee.li on 2018/7/9.
 */

public class ProductRefund extends XmlDecode<ProductRefund> {
    private String transaction_id;
    private String out_trade_no;
    private String out_refund_no;
    private int total_fee;
    private int refund_fee;


    public ProductRefund(String transaction_id, String out_trade_no, String out_refund_no, int total_fee, int refund_fee) {
        this.transaction_id = transaction_id;
        this.out_trade_no = out_trade_no;
        this.out_refund_no = out_refund_no;
        this.total_fee = total_fee;
        this.refund_fee = refund_fee;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(int refund_fee) {
        this.refund_fee = refund_fee;
    }
}
