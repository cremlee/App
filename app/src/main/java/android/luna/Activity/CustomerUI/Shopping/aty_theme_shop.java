package android.luna.Activity.CustomerUI.Shopping;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.CustomerUI.BaseUi.BaseUi;
import android.luna.Activity.CustomerUI.Shopping.Adapter.CartListAdapter;
import android.luna.Activity.CustomerUI.Shopping.Adapter.ShoppingAdapter;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.IngredientFactoryDao;
import android.luna.Data.DAO.StockFactoryDao;
import android.luna.Data.module.BeverageCount;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientWater;
import android.luna.ViewUi.HintLable.IconNumberView;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/16.
 */

public class aty_theme_shop extends BaseUi {
    private PopupWindow mPopWindow;
    private IconNumberView image_shop;
    private boolean showmPopWindow;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];
    private GridView gv_drink;
    private ShoppingAdapter shoppingAdapter;
    @Override
    public void InitView() {
        super.InitView();
        findViewById(R.id.menu_fav).setVisibility(View.GONE);
        findViewById(R.id.lyt_foot).setVisibility(View.GONE);
        getRlyt_bg().setClipChildren(true);
        IsHide =true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //// TODO: 2018/3/22 fuwei gouwuche
        image_shop.setNumber("0");
        shoppingAdapter.getOrderBean().resetdata();
        shoppingAdapter.notifyDataSetChanged();
    }
    private void addCart(ImageView iv, final int cups) {
        final ImageView goods = new ImageView(aty_theme_shop.this);
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
        getRlyt_bg().addView(goods, params);
        int[] parentLocation = new int[2];
        getRlyt_bg().getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        iv.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        image_shop.getLocationInWindow(endLoc);

        //三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + image_shop.getWidth() / 5;
        float toY = endLoc[1] - parentLocation[1];

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);

        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车的数量加1
                // 把移动的图片imageView从父布局里移除
                getRlyt_bg().removeView(goods);
                image_shop.setNumber((cups)+"");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
    @Override
    public void InitData() {
        super.InitData();
        shoppingAdapter = new ShoppingAdapter(this,drinkMenuButtonList);
        image_shop = new IconNumberView(this);
        image_shop.setId(image_shop_id);
        gv_drink = new GridView(this);
        gv_drink.setNumColumns(3);
        gv_drink.setHorizontalSpacing(8);
        gv_drink.setVerticalSpacing(8);
        gv_drink.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gv_drink.setSelector(R.color.transparent);

    }
    private int image_shop_id=View.generateViewId();
    @Override
    public void InitFunctionLayout() {
        super.InitFunctionLayout();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(100,100);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,1);
        lp.setMargins(50,0,0,0);
        getRlyt_bg().addView(image_shop);
        image_shop.setLayoutParams(lp);
        image_shop.setBackgroundResource(R.mipmap.ic_add_shopping);
        lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT,1);
        lp.addRule(RelativeLayout.RIGHT_OF,image_shop_id);
        getRlyt_bg().addView(gv_drink);
        gv_drink.setLayoutParams(lp);
    }

    @Override
    public void InitEvent() {
         super.InitEvent();
        shoppingAdapter.SetOnCartChanged(new ShoppingAdapter.OnCartChanged() {
            @Override
            public void Add(ImageView view,int cups) {
                addCart(view,cups);
            }

            @Override
            public void Del(int cups) {
                image_shop.setNumber((cups)+"");
            }
        });
        image_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v,Constant.PopType.CART_ITEM);
            }
        });
        gv_drink.setAdapter(shoppingAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private CartListAdapter cartListAdapter =null;
    private ListView lv_item;
    private void showPopupWindow(View parent,int type) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
            if (type == Constant.PopType.ERROR) {
                View contentView = LayoutInflater.from(aty_theme_shop.this).inflate(R.layout.pop_win_info, null);
                mPopWindow = new PopupWindow(contentView);
                mPopWindow.setAnimationStyle(R.style.popup_window_anim);
                mPopWindow.setWidth(400);
                mPopWindow.setHeight(400);
                mPopWindow.showAtLocation(parent, Gravity.CENTER, 0, -30);
            }
            else if(type == Constant.PopType.CART_ITEM)
            {
                cartListAdapter = new CartListAdapter(aty_theme_shop.this,shoppingAdapter.getOrderBean().getcartlistdata());

                View contentView = LayoutInflater.from(aty_theme_shop.this).inflate(R.layout.pop_win_cart, null);
                lv_item = contentView.findViewById(R.id.lv_item);
                contentView.findViewById(R.id.cart_go).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getApp().setCartItems(shoppingAdapter.getOrderBean().getcartlistdata());
                        DismissPopWindow();
                        if(cartListAdapter.getCount()>0 && shoppingAdapter.getOrderBean().getCupsfromMap()>0)
                        {
                            //AppManager.getAppManager().finishActivity(aty_theme_shop.this);
                            startActivity(new Intent(aty_theme_shop.this, aty_customer_ui_41.class));
                        }
                    }
                });
                lv_item.setAdapter(cartListAdapter);
                mPopWindow = new PopupWindow(contentView,500,700);
                mPopWindow.setAnimationStyle(R.style.popup_window_anim);
                mPopWindow.showAtLocation(parent,Gravity.CENTER,0,0);
                cartListAdapter.SetOnItemCountChanged(new CartListAdapter.OnItemCountChanged() {
                    @Override
                    public void itemschanged(int pid, int cnt) {
                        shoppingAdapter.getOrderBean().setordermap(pid,cnt);
                        image_shop.setNumber(shoppingAdapter.getOrderBean().getCupsfromMap()+"");
                        shoppingAdapter.notifyDataSetChanged();
                    }
                });
            }
            showmPopWindow = true;
        }
    }
    private void DismissPopWindow()
    {
        mPopWindow.dismiss();
        showmPopWindow =false;
    }


}
