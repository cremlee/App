package android.luna.Activity.Tool;

import android.luna.Activity.Base.BaseActivity;
import android.view.View;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/4.
 */

public class aty_key_main extends BaseActivity implements View.OnClickListener {
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_key_selector);
    }

    @Override
    public void InitData() {
        super.InitData();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        findViewById(R.id.btn_ali).setOnClickListener(this);
        findViewById(R.id.btn_wechat).setOnClickListener(this);
        findViewById(R.id.btn_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.btn_ali:
                    break;
                case R.id.btn_wechat:
                    break;
                case R.id.btn_close:
                    break;
            }
    }
}
