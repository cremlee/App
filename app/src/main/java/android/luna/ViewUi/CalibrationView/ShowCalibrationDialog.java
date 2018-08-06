package android.luna.ViewUi.CalibrationView;


import android.app.Dialog;
import android.content.Context;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.module.DeviceLayout.DeviceCalibrationItem;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.CmdCalibration;
import android.luna.rs232.Cmd.CmdDeviceDbSet;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/24.
 */

public class ShowCalibrationDialog extends Dialog implements OnClickListener{
    private Context mContext;
    private Button n_1,n_2,n_3,n_4,n_5,n_6,n_7,n_8,n_9,n_0;
    private Button n_dot,n_do,n_set,n_del;
    private TextView tx_input,max ,min,tx_unit;
    private DeviceCalibrationItem deviceCalibrationItem;
    private CremApp app;
    private String _txt_value;
    public ShowCalibrationDialog(@NonNull Context context,DeviceCalibrationItem a) {
        super(context);
        this.mContext = context;
        deviceCalibrationItem =a;

    }

    public ShowCalibrationDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    protected ShowCalibrationDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_calibration_win);
        app = ((BaseActivity)mContext).getApp();
        n_0 = findViewById(R.id.n_0);
        n_1 = findViewById(R.id.n_1);
        n_2 = findViewById(R.id.n_2);
        n_3 = findViewById(R.id.n_3);
        n_4 = findViewById(R.id.n_4);
        n_5 = findViewById(R.id.n_5);
        n_6 = findViewById(R.id.n_6);
        n_7 = findViewById(R.id.n_7);
        n_8 = findViewById(R.id.n_8);
        n_9 = findViewById(R.id.n_9);

        n_dot = findViewById(R.id.n_dot);
        n_do = findViewById(R.id.n_do);
        n_set = findViewById(R.id.n_set);
        n_del = findViewById(R.id.n_del);
        min = findViewById(R.id.min);
        max = findViewById(R.id.max);
        tx_unit= findViewById(R.id.tx_unit);
        tx_input = findViewById(R.id.tx_input);


        if(deviceCalibrationItem!=null)
        {
            tx_unit.setText(deviceCalibrationItem.getUnit());
            min.setText(deviceCalibrationItem.getMin()+"");
            max.setText(deviceCalibrationItem.getMax()+"");
            tx_input.setText(deviceCalibrationItem.getValue()+"");
            n_dot.setEnabled(false);
            _txt_value = deviceCalibrationItem.getValue()+"";
            if(deviceCalibrationItem.isfloat()) {
                n_dot.setEnabled(true);
                min.setText(String.format("%.1f",((float)deviceCalibrationItem.getMin())/deviceCalibrationItem.getScale()).toUpperCase(Locale.US));
                max.setText(String.format("%.1f",((float)deviceCalibrationItem.getMax())/deviceCalibrationItem.getScale()).toUpperCase(Locale.US));
                _txt_value = String.format("%.1f",((float)deviceCalibrationItem.getValue())/deviceCalibrationItem.getScale()).toUpperCase(Locale.US);
                tx_input.setText(_txt_value);
            }

        }
        n_del.setOnClickListener(this);
        n_dot.setOnClickListener(this);
        n_do.setOnClickListener(this);
        n_set.setOnClickListener(this);
        n_0.setOnClickListener(this);
        n_1.setOnClickListener(this);
        n_2.setOnClickListener(this);
        n_3.setOnClickListener(this);
        n_4.setOnClickListener(this);
        n_5.setOnClickListener(this);
        n_6.setOnClickListener(this);
        n_7.setOnClickListener(this);
        n_8.setOnClickListener(this);
        n_9.setOnClickListener(this);


    }

    private void setValueTxt(String txt)
    {
        String strvalue = _txt_value+txt;
        int tmp;
        if(deviceCalibrationItem.isfloat())
        {
            tmp = (int)(Float.parseFloat(strvalue)*deviceCalibrationItem.getScale());

        }
        else
        {
            tmp = Integer.parseInt(strvalue)*deviceCalibrationItem.getScale();
        }

        if(tmp > deviceCalibrationItem.getMax() )
        {
            ((BaseActivity)mContext).showToast("Value out of max!");
        }
        else
        {
            _txt_value =_txt_value+txt;
        }
        tx_input.setText(_txt_value);

    }
private CmdCalibration cmdcalibration =new CmdCalibration();
    @Override
    public void onClick(View v) {
        String cmd;
        switch (v.getId())
        {
            case R.id.n_0:
            case R.id.n_1:
            case R.id.n_2:
            case R.id.n_3:
            case R.id.n_4:
            case R.id.n_5:
            case R.id.n_6:
            case R.id.n_7:
            case R.id.n_8:
            case R.id.n_9:
                setValueTxt(v.getTag().toString());
                break;
            case R.id.n_dot:
                if(!_txt_value.contains(".") && deviceCalibrationItem.isfloat())
                {
                    _txt_value = _txt_value+".";
                    tx_input.setText(_txt_value);
                }
                break;
            case R.id.n_del:
                if(_txt_value.length()>0 && !_txt_value.equals(""))
                {
                    _txt_value = _txt_value.substring(0,_txt_value.length()-1);
                    tx_input.setText(_txt_value);
                }
                break;
            case R.id.n_do:
                // TODO: 2018/7/27 do the calibration.
                 cmd = cmdcalibration.buildCalibrationCmd(AndroidUtils_Ext.oct2Hex4(deviceCalibrationItem.getDeviceid())+"","00000000");
                ((BaseActivity)mContext).getApp().addCmdQueue(cmd);
                break;
            case R.id.n_set:
                int tmp;
                if(deviceCalibrationItem.isfloat())
                {
                    tmp = (int)(Float.parseFloat(tx_input.getText().toString())*deviceCalibrationItem.getScale());

                }
                else
                {
                    tmp = Integer.parseInt(tx_input.getText().toString())*deviceCalibrationItem.getScale();
                }
                cmd = (new CmdDeviceDbSet()).buildDeviceParam(deviceCalibrationItem.getDeviceid(),deviceCalibrationItem.getParamid(),tmp,true);
                ((BaseActivity)mContext).getApp().addCmdQueue(cmd);
                break;
        }
    }


}
