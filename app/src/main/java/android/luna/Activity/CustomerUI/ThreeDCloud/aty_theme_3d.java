package android.luna.Activity.CustomerUI.ThreeDCloud;

import android.content.Intent;
import android.luna.Activity.CustomerUI.BaseUi.BaseUi;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_11;
import android.luna.ViewUi.FloatButton.floateutil.FloatBallView;
import android.luna.ViewUi.FloatButton.util.FloatUtil;
import android.luna.ViewUi.threeDCloudUi.TagCloudView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import static android.luna.ViewUi.threeDCloudUi.TagCloudView.MODE_DECELERATE;

/**
 * Created by Lee.li on 2018/5/14.
 */

public class aty_theme_3d extends BaseUi {
    private CouldAdapter couldAdapter;
    private TagCloudView tag_cloud;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //FloatUtil.hideFloatView(this, FloatBallView.class, false);
    }

    @Override
    public void InitView() {
        super.InitView();
    }

    @Override
    public void InitData() {
        super.InitData();
        tag_cloud = new TagCloudView(this);
        couldAdapter =new CouldAdapter(this,drinkMenuButtonList);
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        tag_cloud.setAdapter(couldAdapter);
        tag_cloud.setOnTagClickListener(new TagCloudView.OnTagClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                getApp().set_drinkMenuButton(drinkMenuButtonList.get(position));
                startActivity(new Intent(aty_theme_3d.this, aty_customer_ui_11.class));
            }
        });
    }

    @Override
    public void InitFunctionLayout() {
        super.InitFunctionLayout();
        getRlyt_bg().addView(tag_cloud);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT,1);
        tag_cloud.setLayoutParams(lp );
        tag_cloud.setClipChildren(false);
        tag_cloud.setAutoScrollMode(MODE_DECELERATE);
        tag_cloud.setScrollSpeed(2f);
    }
}
