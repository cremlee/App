package android.luna.ViewUi.warnPopWin;

import android.content.Context;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Gallery.Adapter.MachineWarningAdapter;
import android.luna.ViewUi.LanguageUi.adapter.LanguageAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/26.
 */

public class WarningPopWindow extends PopupWindow implements View.OnClickListener {
    private View conentView;
    private BaseActivity mcontext;
    private RelativeLayout rly_outside;
    private ListView lv_warn;
    private MachineWarningAdapter warningAdapter;
    public WarningPopWindow(final BaseActivity context, MachineWarningAdapter warningAdapter) {
        super(context);
        this.mcontext = context;
        this.warningAdapter=warningAdapter;
        this.initPopupWindow();
    }


    private void initPopupWindow()
    {
        LayoutInflater inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_win_info, null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.AnimationPreview);
        rly_outside = conentView.findViewById(R.id.rly_outside);
        lv_warn= conentView.findViewById(R.id.lv_warn);
        rly_outside.setOnClickListener(this);
        lv_warn.setAdapter(warningAdapter);
        this.setContentView(conentView);
    }
    public void show(View parent) {
        if (this.isShowing()) {
            this.dismiss();
        } else {
            this.showAsDropDown(parent, 0, 0,Gravity.FILL);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rly_outside:
                this.dismiss();
                break;
        }
    }
}
