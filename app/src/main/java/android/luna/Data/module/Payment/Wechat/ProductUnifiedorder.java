package android.luna.Data.module.Payment.Wechat;

import android.luna.Data.module.Payment.XmlDecode;
import android.luna.Utils.AndroidUtils_Ext;

import java.util.Date;

/**
 * Created by Lee.li on 2018/7/9.
 */

public class ProductUnifiedorder  extends XmlDecode<ProductUnifiedorder>{
    private String out_trade_no;   // TODO: 2018/7/9 houqi xu yao fuwuqi tongyi shengcheng gai ziduan
    private String body;
    private String device_info;
    private int total_fee;
    private String spbill_create_ip;
    private String trade_type;
    private String notify_url;
    private String fee_type;


    public ProductUnifiedorder(String body, String device_info, int total_fee,String spbill_create_ip) {
        this.out_trade_no = AndroidUtils_Ext.dateToString((new Date()))+"crem";
        this.body = body;
        this.device_info = device_info;
        this.total_fee = total_fee;
        this.spbill_create_ip = spbill_create_ip;
        this.trade_type = "NATIVE";
        this.fee_type = "CNY";
        this.notify_url = "http://www.weixin.qq.com/wxpay/pay.php";

    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

}
