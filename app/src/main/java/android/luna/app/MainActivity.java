package android.luna.app;

import android.graphics.Color;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.ViewUi.ViewPager.GallyPageTransformer;
import android.luna.ViewUi.ViewPager.MyAdapter;
import android.luna.ViewUi.ViewPager.MyViewPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

public class MainActivity extends AppCompatActivity {
    private static   String Tag = "MainActivity";
    private RecyclerView mRecycleView;
    private MyAdapter mAdapter;
    private List<DrinkMenuButton> drinkMenuButtonList=null;
    private int mScreenWidth;
    private static final float MIN_SCALE = .8f;
    private static final float MAX_SCALE = 1.3f;
    private LinearLayoutManager mLinearLayoutManager;
    private int mMinWidth;
    private int mMaxWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_customer_ui_1);
        mRecycleView = (RecyclerView)findViewById(R.id.rv);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mMinWidth = (int) (mScreenWidth * 0.2f);
        mMaxWidth = mScreenWidth - 4 * mMinWidth;
        drinkMenuButtonList = new ArrayList<>();
        drinkMenuButtonList.add(new DrinkMenuButton("drink1"));
        drinkMenuButtonList.add(new DrinkMenuButton("drink2"));
        drinkMenuButtonList.add(new DrinkMenuButton("drink3"));
        drinkMenuButtonList.add(new DrinkMenuButton("drink4"));
        drinkMenuButtonList.add(new DrinkMenuButton("drink5"));
        drinkMenuButtonList.add(new DrinkMenuButton("drink6"));
        drinkMenuButtonList.add(new DrinkMenuButton("drink7"));
        drinkMenuButtonList.add(new DrinkMenuButton("drink8"));
        mAdapter = new MyAdapter(drinkMenuButtonList,this);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.addOnScrollListener(mOnScrollListener);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
                //// TODO: 2017/12/28  need add the function for drink button
            }
        });

    }
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
                child.setLayoutParams(lp);
                child.setScaleY(scaleFactor);
                child.setScaleX(scaleFactor);
                if (percent > 1f / 3) {
                    //((TextView) child.getChildAt(1)).setTextColor(Color.BLUE);
                } else {
                    //((TextView) child.getChildAt(1)).setTextColor(Color.BLACK);
                }
            }
        }
    };
}
