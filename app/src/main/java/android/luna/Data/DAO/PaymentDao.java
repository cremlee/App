package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IPayment;
import android.luna.Data.module.PaymentSetting;

import java.sql.SQLException;

/**
 * Created by Lee.li on 2018/7/31.
 */

public class PaymentDao extends BaseDaobak implements IPayment<PaymentSetting> {
    public PaymentDao(Context context, CremApp app) {
        super(context, app);
    }


    @Override
    public PaymentSetting Query() {
        try {
            return getHelper().get_paymentSettingIntegerDao().queryForId(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void Update(PaymentSetting paymentSetting) {
        try {
            getHelper().get_paymentSettingIntegerDao().createOrUpdate(paymentSetting);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
