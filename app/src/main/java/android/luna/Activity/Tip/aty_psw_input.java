package android.luna.Activity.Tip;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Normal.aty_customer_ui_3;
import android.luna.Activity.ServiceUi.aty_service_main;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Password.PassWordFactory;
import android.luna.Utils.Qrcode.QrCodeFactory;
import android.luna.ViewUi.PSW.PasswordInputView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/3.
 */

public class aty_psw_input extends BaseActivity {
    private PasswordInputView psw_input;
    private ImageView psw_qr;
    private String psw;
    private Boolean qrready =false;
    private RelativeLayout lyt_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        this.setContentView(R.layout.aty_psw_tip);
        psw_input = findViewById(R.id.psw_input);
        lyt_out = findViewById(R.id.lyt_out);
        psw_qr = findViewById(R.id.psw_qr);
        if(qrready)
        {
            psw_qr.setImageBitmap(BitmapFactory.decodeFile(FileHelper.PATH_CONFIG+"psw.jpg"));
        }
        else {
            //startActivity(new Intent(aty_psw_input.this, aty_service_main.class));
            AppManager.getAppManager().finishActivity(aty_psw_input.this);
        }
    }

    @Override
    public void InitData() {
        super.InitData();
        psw = PassWordFactory.generatePsw();
        String qrstr = PassWordFactory.getEncryptPassword(psw);
        qrready = QrCodeFactory.createQRImage(qrstr,100,100,null, FileHelper.PATH_CONFIG+"psw.jpg");
    }


    @Override
    public void InitEvent() {
        super.InitEvent();
        psw_input.setOnFinishListener(new PasswordInputView.OnFinishListener() {
            @Override
            public void setOnPasswordFinished() {
                if(psw.equals(psw_input.getOriginText()))
                {
                    startActivity(new Intent(aty_psw_input.this, aty_service_main.class));
                    AppManager.getAppManager().finishActivity(aty_psw_input.this);
                }
                else
                {
                    showToast("Password is wrong!!!!");
                }
            }
        });
        lyt_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppManager.getAppManager().finishActivity(aty_psw_input.this);
            }
        });
    }
}
