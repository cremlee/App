package android.luna.Activity.CustomerUI.Normal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_1;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_11;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_12;
import android.luna.Activity.ServiceUi.aty_service_main;
import android.luna.Activity.Tip.aty_psw_input;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.PersonFactoryDao;
import android.luna.Data.module.PersonDrink;
import android.luna.Data.module.PersonItem;
import android.luna.Utils.Password.PassWordFactory;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.FloatButton.floateutil.FloatBallView;
import android.luna.ViewUi.FloatButton.util.FloatUtil;
import android.luna.ViewUi.HintLable.IconNumberView;
import android.luna.ViewUi.OfficeUi.OfficePopWindow;
import android.luna.ViewUi.myViewFlipper;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/16.
 */

public class aty_customer_ui_3 extends BaseActivity implements View.OnClickListener,GestureDetector.OnGestureListener{
    private OfficePopWindow officePopWindow;
    private PopupWindow mPopWindow;
    private IconNumberView btn_info;
    private TextView tv_time;
    private boolean showmPopWindow;
    private List<DrinkMenuButton> drinkMenuButtonList=new ArrayList<>(20);
    private Timer timer=null;
    private myViewFlipper vf;
    private GestureDetector gestureDetector = null;
    private int totalPage;
    private int columnWidth;
    private int columnHeight;
    private  int ColumnCount=5;
    private  int RowCount=2;
    private ArrayList<Integer> colYs = new ArrayList<>();
    private List<Point> lostPoint = new ArrayList<>(); // 用于记录空白块的位置
    private DrinkMenuButton currentbtn=null;
    private BeverageFactoryDao beverageFactoryDao=null;
    private PersonFactoryDao personFactoryDao =null;
    private List<PersonItem> personItems = new ArrayList<>(10);
    private FloatBallView floatBallView =null;
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_customer_ui_3);
        btn_info = findViewById(R.id.btn_info);
        vf = findViewById(R.id.vf);
        tv_time = findViewById(R.id.tv_time);
        vf.post(new Runnable() {
            @Override
            public void run() {
                columnHeight = vf.getMeasuredHeight()/RowCount;
                columnWidth = vf.getMeasuredWidth()/ColumnCount;
                reloadIcon();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(floatBallView!=null) {
            floatBallView.setVisibility(View.VISIBLE);
        }
    }

    private void reloadIcon()
    {
        int btncnt = ColumnCount*RowCount;
        int totalCount = drinkMenuButtonList.size();
        totalPage = (totalCount / btncnt) + (totalCount % btncnt > 0 ? 1 : 0);
        vf.removeAllViews();
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
            vf.addView(beverageLayout);
        }
    }

    private void placeBrick(View view) {
        RelativeLayout.LayoutParams brick = (RelativeLayout.LayoutParams) view.getLayoutParams();

        int groupCount, colSpan, rowSpan;
        List<Integer> groupY = new ArrayList<>();
        List<Integer> groupColY = new ArrayList<>();
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
    private synchronized View createDrinkView(View view, final DrinkMenuButton beverage, final int position) {
        placeBrick(view);
        if (beverage == null) {
            return null;
        }
        final ImageView drinkicon =  view.findViewById(R.id.drinkicon);
        final TextView drinkname =   view.findViewById(R.id.drinkname);
        final TextView drinkprice =  view.findViewById(R.id.drinkprice);

        drinkname.setText(beverage.getName());
        drinkprice.setText(beverage.getPrice()==0?"":Float.toString(beverage.getPrice()));
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getApp().set_drinkMenuButton(drinkMenuButtonList.get(position));
                startActivity(new Intent(aty_customer_ui_3.this, aty_customer_ui_11.class));

            }
        });
        return view;
    }
    @Override
    public void InitData() {
        super.InitData();

       /* String miwen=PassWordFactory.getEncryptPassword();
        EvoTrace.e("psw","miwen="+ miwen);
        try {
            miwen = PassWordFactory.getdecryptPassword(miwen);
            EvoTrace.e("psw","mingwen="+ miwen);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        List<DrinkMenuButton> tmp = beverageFactoryDao.getDrinkIconItems(beverageFactoryDao.getBeverageNameDao().getlocalinfo());

        if(tmp!=null &&tmp.size()>0)
        {
            drinkMenuButtonList.addAll(tmp);
        }
        personFactoryDao = new PersonFactoryDao(this,getApp());
        personItems.add((PersonItem) new PersonItem("Add new Person","",0,"").setTop(true).setBaseIndexTag("+"));
        List<PersonItem> ptmp = personFactoryDao.getPersonItemDao().quryallRecord();
        if(ptmp!=null && ptmp.size()>0)
        {
            for (PersonItem pitem:ptmp)
            {
                personItems.add(pitem);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
          super.onTouchEvent(event);
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        findViewById(R.id.actionbtn1).setOnClickListener(this);
        findViewById(R.id.actionbtn2).setOnClickListener(this);
        findViewById(R.id.actionbtn3).setOnClickListener(this);
        findViewById(R.id.tv_comp_name).setOnClickListener(this);
        tv_time.setOnClickListener(this);
        gestureDetector = new GestureDetector(this,this);
        vf.setmy(gestureDetector);
        btn_info.setOnClickListener(this);
        vf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
         super.dispatchTouchEvent(ev);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addDragView();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FloatUtil.hideFloatView(this, FloatBallView.class, false);
        floatBallView =null;
    }
    private void showPopupWindow(View parent) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
            View contentView = LayoutInflater.from(aty_customer_ui_3.this).inflate(R.layout.pop_win_info, null);
            mPopWindow = new PopupWindow(contentView);
            mPopWindow.setAnimationStyle(R.style.popup_window_anim);
            mPopWindow.setWidth(400);
            mPopWindow.setHeight(400);
            mPopWindow.showAtLocation(parent, Gravity.CENTER,0,-30);
            showmPopWindow = true;
        }
    }
    private void DismissPopWindow()
    {
        mPopWindow.dismiss();
        showmPopWindow =false;
    }


    public void addDragView()
    {
        floatBallView = new FloatBallView(this);
        FloatUtil.showFloatView(floatBallView, Gravity.CENTER | Gravity.BOTTOM, WindowManager.LayoutParams.TYPE_TOAST,new Point(0,0), null, true);
        floatBallView.getBall().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (officePopWindow == null) {
                        officePopWindow = new OfficePopWindow(aty_customer_ui_3.this, personItems, personFactoryDao, beverageFactoryDao);
                        officePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                officePopWindow = null;
                            }
                        });
                        officePopWindow.SetOnQuickStartClick(new OfficePopWindow.OnQuickStartClick() {
                            @Override
                            public void Start(PersonDrink p) {
                                DrinkMenuButton crt = FindforPid(p.getPid());
                                if(crt!=null) {
                                    getApp().set_drinkMenuButton(crt);
                                    startActivity(new Intent(aty_customer_ui_3.this, aty_customer_ui_12.class));
                                }
                            }
                        });
                    }
                officePopWindow.show(tv_time);

            }
        });
    }


    private DrinkMenuButton FindforPid(int pid)
    {
        for (DrinkMenuButton item :drinkMenuButtonList)
        {
            if(item.getPid() == pid)
                return item;
        }
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.tv_time:
                startActivity(new Intent(aty_customer_ui_3.this, aty_service_main.class));
                floatBallView.setVisibility(View.GONE);
                break;
            case R.id.btn_info:
                showPopupWindow(view);
                break;
            case R.id.actionbtn1:
                if(RowCount!=2)
                {
                    RowCount =2;
                    vf.post(new Runnable() {
                        @Override
                        public void run() {
                            columnHeight = vf.getMeasuredHeight()/RowCount;
                            columnWidth = vf.getMeasuredWidth()/ColumnCount;
                            reloadIcon();
                        }
                    });
                }
                break;
            case R.id.actionbtn2:
                if(RowCount!=3)
                {
                    RowCount =3;
                    vf.post(new Runnable() {
                        @Override
                        public void run() {
                            columnHeight = vf.getMeasuredHeight()/RowCount;
                            columnWidth = vf.getMeasuredWidth()/ColumnCount;
                            reloadIcon();
                        }
                    });
                }
                break;
            case R.id.actionbtn3:
                startActivity(new Intent(aty_customer_ui_3.this, aty_customer_ui_1.class));
                AppManager.getAppManager().finishActivity(aty_customer_ui_3.this);
                break;
            case R.id.tv_comp_name:
                startActivity(new Intent(aty_customer_ui_3.this, aty_psw_input.class));
                break;
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        //EvoTrace.e("touch","onSingleTapUp");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        if(totalPage<=1)
        {
            return false;
        }
        if (motionEvent.getX() - motionEvent1.getX() > 120)
        {

            this.vf.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_left_in));
            this.vf.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_left_out));
            vf.showNext();
            return true;
        }
        else if(motionEvent.getX() - motionEvent1.getX() < -120)
        {
            this.vf.setInAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_right_in));
            this.vf.setOutAnimation(AnimationUtils.loadAnimation(this,
                    R.anim.push_right_out));
            vf.showPrevious();
            return true;
        }

        return false;
    }
}
