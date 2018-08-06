package android.luna.Activity.CustomerUI.Shopping;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.CustomerUI.Shopping.Adapter.CartListAdapter;
import android.luna.Activity.CustomerUI.Shopping.Adapter.ShoppingAdapter;
import android.luna.Activity.ServiceUi.aty_service_main;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.ViewUi.HintLable.IconNumberView;
import android.luna.ViewUi.myViewFlipper;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/16.
 */

public class aty_customer_ui_4 extends BaseActivity implements View.OnClickListener{
    private PopupWindow mPopWindow;
    private IconNumberView btn_info,image_shop;
    private TextView tv_time;
    private boolean showmPopWindow;
    private List<DrinkMenuButton> drinkMenuButtonList=new ArrayList<>(20);
    private Timer timer=null;
    private myViewFlipper vf;
    private GestureDetector gestureDetector = null;
    private ArrayList<Integer> colYs = new ArrayList<>();
    private List<Point> lostPoint = new ArrayList<>(); // 用于记录空白块的位置
    private  DrinkMenuButton currentbtn=null;
    private BeverageFactoryDao beverageFactoryDao=null;
    private  RelativeLayout rlyt_bg;
    private PathMeasure mPathMeasure;
    private float[] mCurrentPosition = new float[2];
    private GridView gv_drink;
    private ShoppingAdapter shoppingAdapter;
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_customer_ui_4);
        gv_drink = findViewById(R.id.gv_drink);
        image_shop = findViewById(R.id.image_shop);
        btn_info = findViewById(R.id.btn_info);
        rlyt_bg = findViewById(R.id.rlyt_bg);
        vf = findViewById(R.id.vf);
        gv_drink.setAdapter(shoppingAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //// TODO: 2018/3/22 fuwei gouwuche
        image_shop.setNumber("0");
        shoppingAdapter.getOrderBean().resetdata();
        shoppingAdapter.notifyDataSetChanged();
       // cartListAdapter.notifyDataSetChanged();
    }
    private void addCart(ImageView iv, final int cups) {
        final ImageView goods = new ImageView(aty_customer_ui_4.this);
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(60, 60);
        rlyt_bg.addView(goods, params);
        int[] parentLocation = new int[2];
        rlyt_bg.getLocationInWindow(parentLocation);

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
                rlyt_bg.removeView(goods);
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
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        List<DrinkMenuButton> tmp = beverageFactoryDao.getDrinkIconItems(beverageFactoryDao.getBeverageNameDao().getlocalinfo());
        if(tmp!=null &&tmp.size()>0)
        {
            drinkMenuButtonList.addAll(tmp);
        }
        shoppingAdapter = new ShoppingAdapter(this,drinkMenuButtonList);
        if (timer == null) {
            timer = new Timer();
        }
    }



    @Override
    public void InitEvent() {
        super.InitEvent();
        findViewById(R.id.tv_comp_name).setOnClickListener(this);
        vf.setmy(gestureDetector);
        btn_info.setOnClickListener(this);
        vf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
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
        image_shop.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApp().bindAllService();
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
                View contentView = LayoutInflater.from(aty_customer_ui_4.this).inflate(R.layout.pop_win_info, null);
                mPopWindow = new PopupWindow(contentView);
                mPopWindow.setAnimationStyle(R.style.popup_window_anim);
                mPopWindow.setWidth(400);
                mPopWindow.setHeight(400);
                mPopWindow.showAtLocation(parent, Gravity.CENTER, 0, -30);
            }
            else if(type == Constant.PopType.CART_ITEM)
            {
                cartListAdapter = new CartListAdapter(aty_customer_ui_4.this,shoppingAdapter.getOrderBean().getcartlistdata());

                View contentView = LayoutInflater.from(aty_customer_ui_4.this).inflate(R.layout.pop_win_cart, null);
                lv_item = contentView.findViewById(R.id.lv_item);
                contentView.findViewById(R.id.cart_go).setOnClickListener(this);
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
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.cart_go:
                getApp().setCartItems(shoppingAdapter.getOrderBean().getcartlistdata());
                DismissPopWindow();
                if(cartListAdapter.getCount()>0 && shoppingAdapter.getOrderBean().getCupsfromMap()>0)
                {
                    startActivity(new Intent(aty_customer_ui_4.this, aty_customer_ui_41.class));
                }
                break;
            case R.id.image_shop:
                showPopupWindow(view,Constant.PopType.CART_ITEM);
                break;
            case R.id.btn_info:
                showPopupWindow(view,Constant.PopType.ERROR);
                break;
            case R.id.tv_comp_name:
                startActivity(new Intent(aty_customer_ui_4.this, aty_service_main.class));
                break;
        }
    }
}
