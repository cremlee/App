package android.luna.ViewUi.LanguageUi;

import android.content.Context;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.LangItem;
import android.luna.Data.module.Languageitem;
import android.luna.Utils.Nutrition.NutritionListItem;
import android.luna.ViewUi.LanguageUi.adapter.LanguageAdapter;
import android.luna.ViewUi.NutritionUi.adapter.Containadapter;
import android.luna.ViewUi.NutritionUi.adapter.Nutritionadapter;
import android.luna.ViewUi.NutritionUi.data.PowderBean;
import android.luna.ViewUi.radarview.RadarData;
import android.luna.ViewUi.radarview.RadarView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/26.
 */

public class LanguagePopWindow extends PopupWindow implements View.OnClickListener {
    private View conentView;
    private BaseActivity mcontext;
    private RelativeLayout rly_outside;
    private GridView gv_item;

    public LanguageAdapter getLanguageAdapter() {
        return languageAdapter;
    }

    private LanguageAdapter languageAdapter;

    private List<Integer> _data;
    public LanguagePopWindow(final BaseActivity context,List<Integer> data) {
        super(context);
        this.mcontext = context;
        _data=data;
        this.initPopupWindow();
    }


    private void initPopupWindow()
    {
        LayoutInflater inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_win_language, null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.AnimationPreview);
        rly_outside = conentView.findViewById(R.id.rly_outside);
        gv_item= conentView.findViewById(R.id.gv_item);
        rly_outside.setOnClickListener(this);
        languageAdapter = new LanguageAdapter(mcontext,_data);
        gv_item.setAdapter(languageAdapter);
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
