package android.luna.Activity.CustomerUI.Normal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.CustomerUI.BaseUi.BaseUi;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_11;
import android.luna.Activity.CustomerUI.Gallery.aty_theme_gallery;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Utils.Lang.LangLocalHelper;
import android.luna.Utils.MyAnimation.Rotate3D;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.FloatButton.floateutil.FloatBallView;
import android.luna.ViewUi.FloatButton.util.FloatUtil;
import android.luna.ViewUi.myViewFlipper;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lid.lib.LabelImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/14.
 */

public class aty_theme_normal extends BaseUi implements GestureDetector.OnGestureListener,BaseUi.Languagechanged{
    private myViewFlipper viewFlipper;
    private int totalPage;
    private int columnWidth;
    private int columnHeight;
    private  int ColumnCount=5;
    private  int RowCount=2;
    private ArrayList<Integer> colYs = new ArrayList<>();
    private List<Point> lostPoint = new ArrayList<>(); // 用于记录空白块的位置
    @Override
    public void InitData() {
        super.InitData();
        viewFlipper = new myViewFlipper(this);
        setGestureDetector(new GestureDetector(this,this));
        viewFlipper.setmy(getGestureDetector());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApp().setIsmainpagereload(false);
        initAnimation();
    }


    @Override
    public void InitEvent() {
        super.InitEvent();
        this.setOnLanguagechanged(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getApp().ismainpagereload())
        {
            reloadIcon();
            getApp().setIsmainpagereload(false);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getGestureDetector().onTouchEvent(ev);
        super.dispatchTouchEvent(ev);
        return true;
    }
    @Override
    public void InitFunctionLayout() {
        super.InitFunctionLayout();
        getRlyt_bg().addView(viewFlipper);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT,1);
        viewFlipper.setLayoutParams(lp );
        viewFlipper.post(new Runnable() {
            @Override
            public void run() {
                columnHeight = viewFlipper.getMeasuredHeight()/RowCount;
                columnWidth = viewFlipper.getMeasuredWidth()/ColumnCount;
                reloadIcon();
            }
        });
    }
    private void reloadIcon()
    {

        int btncnt = ColumnCount*RowCount;
        int totalCount = drinkMenuButtonList.size();
        totalPage = (totalCount / btncnt) + (totalCount % btncnt > 0 ? 1 : 0);
        viewFlipper.removeAllViews();
        int currentPosition = 0;
        for (int i = 0; i < totalPage; i++) {
            colYs.clear();
            for (int k = 0; k < ColumnCount; k++) {
                colYs.add(0);
            }
            lostPoint.clear();
            RelativeLayout beverageLayout = new RelativeLayout(this);
            beverageLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            beverageLayout.setId(0x90 + i);
            int count = btncnt;
            for (int j = currentPosition; j < (totalCount < (currentPosition + count) ? totalCount : (currentPosition + count)); j++) {
                DrinkMenuButton btn = drinkMenuButtonList.get(j);
                View beverageLayoutItem = getLayoutInflater().inflate(R.layout.myviewpager, null);
                if(btn!=null)
                {
                    RelativeLayout.LayoutParams params;
                    params = new RelativeLayout.LayoutParams(columnWidth, columnHeight);
                    beverageLayoutItem.setLayoutParams(params);
                    View view = createDrinkView(beverageLayoutItem, btn,j);
                    view.setBackground(null);
                    beverageLayout.addView(view);
                }
            }
            currentPosition += count;
            viewFlipper.addView(beverageLayout);
        }
    }

    private void placeBrick(View view) {
        RelativeLayout.LayoutParams brick = (RelativeLayout.LayoutParams) view.getLayoutParams();

        int groupCount, colSpan, rowSpan;
        List<Integer> groupY = new ArrayList<>();
        List<Integer> groupColY;
        colSpan = (int) Math.ceil(brick.width / this.columnWidth);// 计算跨几列
        colSpan = Math.min(colSpan, ColumnCount);// 取最小的列数
        rowSpan = (int) Math.ceil(brick.height / this.columnHeight);
        if (colSpan == 1) {
            groupY = this.colYs;
            // 如果存在白块则从添加到白块中
            if (lostPoint.size() > 0 && rowSpan == 1) {
                Point point = lostPoint.get(0);
                int pTop = point.y;
                int pLeft = this.columnWidth * point.x;// 放置的left
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(brick.width, brick.height);
                params.leftMargin = pLeft;
                params.topMargin = pTop;
                view.setLayoutParams(params);
                lostPoint.remove(0);
                int minimumY;

                minimumY = Collections.min(groupY);// 取出几个可选位置中最小的添加
                int shortCol = 0;
                int len = groupY.size();
                for (int i = 0; i < len; i++) {
                    if (groupY.get(i) == minimumY) {
                        shortCol = i;// 获取到最小y值对应的列值
                        break;
                    }
                }
                int setHeight = minimumY + brick.height;
                int setSpan = ColumnCount + 1 - len;
                for (int i = 0; i < setSpan; i++) {
                    this.colYs.set(shortCol + i, setHeight);
                }
                return;
            }

        } else {// 说明有跨列
            groupCount = ColumnCount + 1 - colSpan;// 添加item的时候列可以填充的列index
            for (int j = 0; j < groupCount; j++) {
                groupColY = this.colYs.subList(j, j + colSpan);
                groupY.add(j, Collections.max(groupColY));// 选择几个可添加的位置
            }
        }
        int minimumY;

        minimumY = Collections.min(groupY);// 取出几个可选位置中最小的添加
        int shortCol = 0;
        int len = groupY.size();
        for (int i = 0; i < len; i++) {
            if (groupY.get(i) == minimumY) {
                shortCol = i;// 获取到最小y值对应的列值
                break;
            }
        }
        int pTop = minimumY;// 这是放置的Top
        int pLeft = this.columnWidth * shortCol;// 放置的left
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(brick.width, brick.height);
        params.leftMargin = pLeft;
        params.topMargin = pTop;
        view.setLayoutParams(params);
        if (colSpan != 1) {
            for (int i = 0; i < ColumnCount; i++) {
                if (minimumY > this.colYs.get(i)) {// 出现空行
                    int y = minimumY - this.colYs.get(i);
                    for (int j = 0; j < y / columnHeight; j++) {
                        lostPoint.add(new Point(i, this.colYs.get(i) + columnHeight * j));
                    }
                }
            }
        }
        int setHeight = minimumY + brick.height;
        int setSpan = ColumnCount + 1 - len;
        for (int i = 0; i < setSpan; i++) {
            this.colYs.set(shortCol + i, setHeight);
        }
    }
    private synchronized View createDrinkView(View view, final DrinkMenuButton beverage, int position) {
       final int pos = position;
        placeBrick(view);
        if (beverage == null) {
            return null;
        }
        final LabelImageView drinkicon =  view.findViewById(R.id.drinkicon);
        final TextView drinkname =   view.findViewById(R.id.drinkname);
        final TextView drinkprice =  view.findViewById(R.id.drinkprice);
        final TextView drinkstate =  view.findViewById(R.id.drinkstate);

        drinkname.setText(beverage.getName());
        drinkprice.setText("");
        if(ispayment() && beverage.getPrice()>0)
        {
            // TODO: 2018/7/31 show price
            drinkicon.setLabelDistance(50);
            drinkicon.setLabelHeight(30);
            drinkicon.setLabelTextSize(18);
            drinkicon.setLabelText(Float.toString(beverage.getPrice()));
        }
        String path = beverage.getIconpath();//== null?"":drinkMenuButtonList.get(position).getIconpath();
        if(path!=null) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 400, 400);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            if (bitmap != null)
                drinkicon.setImageBitmap(bitmap);
            else
                drinkicon.setImageBitmap(null);
        }
        view.setId(position);
        view.setClickable(true);
        drinkstate.setVisibility(View.VISIBLE);
        if(beverage.getDrinkstate()==0) {
           drinkstate.setVisibility(View.GONE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getApp().set_drinkMenuButton(drinkMenuButtonList.get(pos));
                    startActivity(new Intent(aty_theme_normal.this, aty_customer_ui_11.class));
                }
            });
        }
        else if(beverage.getDrinkstate()==1){
            drinkstate.setText(getString(R.string.MAIN_UI_OUT_OF_STOCK));
        }
        else if(beverage.getDrinkstate()==2){
            drinkstate.setText(getString(R.string.MAIN_UI_OUT_OF_USE));
        }
        else if(beverage.getDrinkstate()==3){
            drinkstate.setText(getString(R.string.MAIN_UI_OUT_OF_WASTER));
        }


        return view;
    }

    private float mCenterX,mCenterY;
    private Rotate3D lQuest1Animation,lQuest2Animation,rQuest1Animation,rQuest2Animation;
    public void initAnimation() {
        // 获取旋转中心
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        mCenterX = dm.widthPixels / 2;
        mCenterY = dm.heightPixels / 2;

        // 定义旋转方向
        int duration = 1000;
        lQuest1Animation = new Rotate3D(0, -90, mCenterX, mCenterY);    // 下一页的【question1】旋转方向（从0度转到-90，参考系为水平方向为0度）
        lQuest1Animation.setFillAfter(false);
        lQuest1Animation.setDuration(duration);

        lQuest2Animation = new Rotate3D(90, 0, mCenterX, mCenterY);     // 下一页的【question2】旋转方向（从90度转到0，参考系为水平方向为0度）（起始第一题）
        lQuest2Animation.setFillAfter(false);
        lQuest2Animation.setDuration(duration);

        rQuest1Animation = new Rotate3D(0, 90, mCenterX, mCenterY);     // 上一页的【question1】旋转方向（从0度转到90，参考系为水平方向为0度）
        rQuest1Animation.setFillAfter(false);
        rQuest1Animation.setDuration(duration);

        rQuest2Animation = new Rotate3D(-90, 0, mCenterX, mCenterY);    // 上一页的【question2】旋转方向（从-90度转到0，参考系为水平方向为0度）
        rQuest2Animation.setFillAfter(false);
        rQuest2Animation.setDuration(duration);

    }



    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY) {
        if(totalPage<=1)
        {
            return false;
        }
        if (motionEvent.getX() - motionEvent1.getX() > 120)
        {
            this.viewFlipper.setInAnimation(lQuest2Animation);
            this.viewFlipper.setOutAnimation(lQuest1Animation);
            viewFlipper.showNext();
            return true;
        }
        else if(motionEvent.getX() - motionEvent1.getX() < -120)
        {
            this.viewFlipper.setInAnimation(rQuest2Animation);
            this.viewFlipper.setOutAnimation(rQuest1Animation);
            viewFlipper.showPrevious();
            return true;
        }
        return false;
    }

    @Override
    public void updated() {
        LangLocalHelper.updateLocale(this,LangLocalHelper.getlocalinfo(getApp().getCurrent_language()));
        resetaty();
    }
    public void resetaty()
    {
        AppManager.getAppManager().finishActivity(aty_theme_normal.this);
        Intent _Intent = new Intent(this, aty_theme_normal.class);
        startActivity(_Intent);
        //清除Activity退出和进入的动画
        overridePendingTransition(0, 0);
    }
}
