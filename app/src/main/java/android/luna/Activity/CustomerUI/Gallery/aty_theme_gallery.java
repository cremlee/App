package android.luna.Activity.CustomerUI.Gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.luna.Activity.CustomerUI.BaseUi.BaseUi;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.FloatButton.floateutil.FloatBallView;
import android.luna.ViewUi.FloatButton.util.FloatUtil;
import android.luna.ViewUi.ViewPager.MyAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by Lee.li on 2018/5/14.
 */

public class aty_theme_gallery extends BaseUi {
    private RecyclerView mRecycleView;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int mMinWidth;
    private int mMaxWidth;
    private int mScreenWidth;
    private  final float MIN_SCALE = 0.8f;
    private  final float MAX_SCALE = 1.5f;
    private float Screen3Items =0.333333f;
    private float Screen5Items = 0.2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitData() {
        super.InitData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        drinkMenuButtonList.add(new DrinkMenuButton(""));
        drinkMenuButtonList.add(new DrinkMenuButton(""));
        drinkMenuButtonList.add(0,new DrinkMenuButton(""));
        drinkMenuButtonList.add(0,new DrinkMenuButton(""));
        if(getApp().ismainpagereload()) {
            mAdapter.notifyDataSetChanged();
            getApp().setIsmainpagereload(false);
        }
    }

    @Override
    public void galleryfunction() {
        drinkMenuButtonList.add(new DrinkMenuButton(""));
        drinkMenuButtonList.add(new DrinkMenuButton(""));
        drinkMenuButtonList.add(0,new DrinkMenuButton(""));
        drinkMenuButtonList.add(0,new DrinkMenuButton(""));
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        mAdapter = new MyAdapter(drinkMenuButtonList,this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(mOnScrollListener);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                //// TODO: 2017/12/28  need add the function for drink button
                if(drinkMenuButtonList.get(position).getName()!="") {

                    getApp().set_drinkMenuButton(drinkMenuButtonList.get(position));
                    startActivity(new Intent(aty_theme_gallery.this, aty_customer_ui_11.class));
                }
            }
        });
    }
    private int maxpostion=0;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            final int childCount = recyclerView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                RelativeLayout child = (RelativeLayout) recyclerView.getChildAt(i);
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
                lp.rightMargin = 15;
                lp.height = (int) (getResources().getDisplayMetrics().heightPixels *3.5/8);
                int left = child.getLeft();
                int right = mScreenWidth - child.getRight();
                final float percent = left < 0 || right < 0 ? 0 : Math.min(left, right) * 1f / Math.max(left, right);
                float scaleFactor = MIN_SCALE + Math.abs(percent) * (MAX_SCALE - MIN_SCALE);
                int width = (int) (mMinWidth + Math.abs(percent) * (mMaxWidth - mMinWidth));
                lp.width = width;
                lp.topMargin = (recyclerView.getHeight() - lp.height)/2;
                child.setLayoutParams(lp);
                child.setScaleY(scaleFactor);
                child.setScaleX(scaleFactor);
                if(scaleFactor>1.22f)
                {
                    maxpostion = (int)child.getTag();
                }
            }

        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == SCROLL_STATE_IDLE )
            {
                if(maxpostion!=0) {
                    ShowBackground(maxpostion);
                }
            }
        }
    };
    private int currentcenterpostion=0;
    private void ShowBackground(int a)
    {
        if(a == currentcenterpostion)
            return;
        //// TODO: 2018/1/3 shuaxin beijingtu
        currentcenterpostion =a;
        String path = drinkMenuButtonList.get(a).getBgPath();
        if(path!=null && !path.equals("")) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, mScreenWidth, 600);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            if (bitmap != null) {
                bitmap.setHasAlpha(true);
                Drawable bg = new BitmapDrawable(bitmap);
                bg.setAlpha(50);
                mRecycleView.setBackground(bg);

            }
            else
                mRecycleView.setBackground(null);
        }
        else
        {
            mRecycleView.setBackground(null);
        }

    }
    @Override
    public void InitFunctionLayout() {
        super.InitFunctionLayout();
        mRecycleView = new RecyclerView(this);
        getRlyt_bg().addView(mRecycleView);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT,1);
        mRecycleView.setLayoutParams(lp );
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mMinWidth = (int) (mScreenWidth * Screen5Items);
        mMaxWidth = mMinWidth;
    }
}
