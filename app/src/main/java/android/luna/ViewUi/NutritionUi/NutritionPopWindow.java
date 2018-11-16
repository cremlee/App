package android.luna.ViewUi.NutritionUi;

import android.content.Context;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Utils.Nutrition.NutritionListItem;
import android.luna.ViewUi.NutritionUi.adapter.Containadapter;
import android.luna.ViewUi.NutritionUi.adapter.Nutritionadapter;
import android.luna.ViewUi.NutritionUi.data.PowderBean;
import android.luna.ViewUi.radarview.RadarData;
import android.luna.ViewUi.radarview.RadarView;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/26.
 */

public class NutritionPopWindow extends PopupWindow implements View.OnClickListener {
    private View conentView;
    private BaseActivity mcontext;
    private RelativeLayout rly_outside;
    private List<NutritionListItem> _data;
    private ListView lv_nutrition;
    private Nutritionadapter nutritionadapter;
    private RadarView rd_nutrition;
    private GridView gv_contain ,gv_maycontain;
    private int containdata,maycontaindata;
    private Containadapter containadapter1,containadapter2;

    public NutritionPopWindow(final BaseActivity context,List<NutritionListItem> data,int a,int b) {
        super(context);
        this.mcontext = context;
        this._data = data;
        containdata = a;
        maycontaindata = b;
        PowderBean powderBean =new PowderBean();
        nutritionadapter = new Nutritionadapter(mcontext,_data);
        powderBean.MaskCode = containdata;
        containadapter1 = new Containadapter(mcontext,powderBean.getdata());
        powderBean.MaskCode =maycontaindata;
        containadapter2 = new Containadapter(mcontext,powderBean.getdata());

        this.initPopupWindow();
    }

    private void InitRadar()
    {
        rd_nutrition.setEmptyHint("no data");
        List<Integer> layerColor = new ArrayList<>();
        Collections.addAll(layerColor, 0x3300bcd4, 0x3303a9f4, 0x335677fc, 0x333f51b5, 0x33673ab7);
        rd_nutrition.setLayerColor(layerColor);
        List<String> vertexText = new ArrayList<>();
        //Collections.addAll(vertexText, "Fat", "Fat", "Fat", "Fat", "Fat", "Fat");
        Collections.addAll(vertexText, "Fat", "Sugar", "Sodium", "Protein", "Carb", "Kilo");
        rd_nutrition.setVertexText(vertexText);
        List<Integer> res = new ArrayList<>();
        Collections.addAll(res, R.mipmap.coffee, R.mipmap.coffee, R.mipmap.coffee,
                R.mipmap.coffee, R.mipmap.coffee, R.mipmap.coffee);
        rd_nutrition.setVertexIconResid(res);
        List<Float> values = new ArrayList<>();
        Collections.addAll(values, 2f, 6f, 2f, 7f, 5f, 1f);
        RadarData data = new RadarData(values);
        rd_nutrition.addData(data);
        rd_nutrition.setRotationEnable(false);
        rd_nutrition.animeValue(2000);
    }

    private void InitContain()
    {
        gv_contain.setAdapter(containadapter1);
        gv_maycontain.setAdapter(containadapter2);
    }

    private void initPopupWindow()
    {
        LayoutInflater inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.pop_win_drink, null);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.AnimationPreview);
        rly_outside = conentView.findViewById(R.id.rly_outside);
        lv_nutrition = conentView.findViewById(R.id.lv_nutrition);
        lv_nutrition.setAdapter(nutritionadapter);
        rd_nutrition = conentView.findViewById(R.id.rd_nutrition);
        InitRadar();
        gv_contain= conentView.findViewById(R.id.gv_contain);
        gv_maycontain= conentView.findViewById(R.id.gv_maycontain);
        InitContain();
        rly_outside.setOnClickListener(this);
        this.setContentView(conentView);
    }
    public void show(View parent) {
        if (this.isShowing()) {
            this.dismiss();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                this.showAsDropDown(parent, 0, 0,Gravity.FILL);
            }
        }
    }

    public void showincenter(View parent) {
        if (this.isShowing()) {
            this.dismiss();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                this.showAtLocation(parent, Gravity.CENTER,0, 0);
            }
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
