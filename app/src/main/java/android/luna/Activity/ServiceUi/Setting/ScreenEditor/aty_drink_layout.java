package android.luna.Activity.ServiceUi.Setting.ScreenEditor;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.ScreenEditor.Adapter.DrinkSelectAdapter;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.BeverageUi;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.DragUi.DetailView;
import android.luna.ViewUi.DragUi.FreeSizeDraggableLayout;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.lid.lib.LabelImageView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/21.
 */

public class aty_drink_layout extends BaseActivity implements View.OnClickListener,View.OnTouchListener {
    private FreeSizeDraggableLayout _DraggableLayout;
    private List<DetailView> m_list=new ArrayList<>();
    private static final  int XCELLCOUNT = 5;
    private static final  int YCELLCOUNT = 2;
    private int onepagecnt = XCELLCOUNT*YCELLCOUNT;
    private int	mXCellsCount;
    private int	mYCellsCount;
    private boolean[][]	mOccupied = null;
    private int currentPage = 1;
    private int totalPage = 1;

    private List<BeverageUi> beverageUiList = new ArrayList<>(20);
    private BeverageFactoryDao beverageFactoryDao;
    private MaterialDialog drinkSelectDialog;
    private DrinkSelectAdapter drinkadapter;

    private MaterialDialog deleteDialog;

    //private List<DrinkMenuButton> drinkMenuButtonList = new ArrayList<>(20);
    /**
     *  private method
     */
    private void initmOccupied()
    {
        if (mOccupied == null)
            mOccupied = new boolean[mXCellsCount][mYCellsCount];
        for(int i = 0;i < mXCellsCount;i++)
            for(int j = 0;j < mYCellsCount;j++)
                mOccupied[i][j] = false;

    }

    List<BeverageUi> drinkhidelist = new ArrayList<>();
    private void loadBeverages()
    {
        String name;
        beverageUiList.clear();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        beverageUiList = beverageFactoryDao.getBeverageUiDao().getShowSortbyIndex();
        if(beverageUiList!=null && beverageUiList.size()>0)
        {
            for (BeverageUi it:beverageUiList) {
                name = beverageFactoryDao.getBeverageNameDao().getDrinkname(it.getPid(), beverageFactoryDao.getBeverageNameDao().getlocalinfo());
                if (!name.equals(""))
                    it.setName(name);
            }
            int totalCount = beverageUiList.size()+1;
            totalPage = (totalCount / onepagecnt) + (totalCount % onepagecnt > 0 ? 1 : 0);
        }

        drinkhidelist = beverageFactoryDao.getBeverageUiDao().getHide();
        if(drinkhidelist!=null && drinkhidelist.size()>0)
        {
            for (BeverageUi it:drinkhidelist) {
                name = beverageFactoryDao.getBeverageNameDao().getDrinkname(it.getPid(), beverageFactoryDao.getBeverageNameDao().getlocalinfo());
                if (!name.equals(""))
                    it.setName(name);
            }
        }
        drinkadapter =new DrinkSelectAdapter(drinkhidelist);
        drinkadapter.setCallbacks(new DrinkSelectAdapter.ItemCallback(){
            @Override
            public void onItemClicked(int itemIndex) {
               if(drinkSelectDialog!=null) {
                   BeverageUi tmp =  drinkhidelist.get(itemIndex);
                   tmp.setShowOrhide(1);
                   tmp.setSortIndex(999);
                   beverageFactoryDao.getBeverageUiDao().update(tmp);
                   drinkhidelist.remove(itemIndex);
                   drinkSelectDialog.dismiss();
                   setLayoutData();
               }
            }
        });
    }
    private Point getvalidIndex(int bigflag)
    {
        Point ret =null;
        for(int i = 0;i < mXCellsCount;i++)
        {
            for(int j = 0;j < mYCellsCount;j++)
            {
                if(bigflag ==1)
                {
                    if(!mOccupied[i][j])
                    {
                        mOccupied[i][j] =true;
                        ret =new Point(j,i);
                        return ret;
                    }
                }
                else
                {
                    if(!mOccupied[i][j])
                    {
                        if(i>=3 || j>=3)
                            return null;
                        if(!mOccupied[i + 1][j] && !mOccupied[i][j + 1] && !mOccupied[i + 1][j + 1])
                        {
                            mOccupied[i][j] =true;
                            mOccupied[i+1][j] =true;
                            mOccupied[i][j+1] =true;
                            mOccupied[i+1][j+1] =true;
                            ret =new Point(j,i);
                            return ret;
                        }
                    }
                }

            }
        }
        return null;
    }

    private void setLayoutData() {
        viewFlipper.removeAllViews();
        _DraggableLayout.clearlistManager(null);
        loadBeverages();
        m_list.clear();
        if (beverageUiList != null ) {
            List<DetailView> list;
            int pos = 0;
            for (int i = 0; i < totalPage; i++) {
                initmOccupied();
                list = new ArrayList<>();
                int laysize = onepagecnt;
                for(int j=pos;j<beverageUiList.size();j++)
                {
                    Point pl =getvalidIndex(1);
                    if(pl!=null)
                        list.add(new DetailView(pl, 1, 1, createview(beverageUiList.get(j)),beverageUiList.get(j).getSortIndex(),beverageUiList.get(j).getPid()));
                    laysize--;
                    if(laysize<=0)
                    {
                        pos=j+1;
                        break;
                    }
                }
                if(i>=(totalPage-1))
                {
                    Point pl =getvalidIndex(1);
                    if(pl!=null)
                        list.add(new DetailView(pl, 1, 1,createAddButton(),1,0));

                }
                _DraggableLayout.addtolistManager(list);
                m_list.addAll(list);
            }
            viewFlipper.addView(_DraggableLayout);
            _DraggableLayout.changelistsource(0);
            viewFlipper.setDisplayedChild(currentPage-1);
        }
    }
    @SuppressWarnings("deprecation")
    private View createAddButton()
    {
        View addview = getLayoutInflater().inflate(R.layout.layout_add_item,null);
        Display display = getWindowManager().getDefaultDisplay();
        int sizeaa = (display.getWidth()-2*XCELLCOUNT)/XCELLCOUNT;
        int sizeab = (display.getHeight()-2*YCELLCOUNT)/YCELLCOUNT;
        addview.setLayoutParams(new RelativeLayout.LayoutParams(sizeaa,sizeab));
        addview.setClickable(true);
        addview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/6/21 xianshi caindan liebiao to xuanze
                showToast("add new drink to screen");
                drinkSelectDialog = new MaterialDialog.Builder(aty_drink_layout.this)
                        .title("Drink selection")
                        .negativeText("Later")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                drinkSelectDialog.dismiss();
                            }
                        })
                        .adapter(drinkadapter,null)
                        .canceledOnTouchOutside(false)
                        .show();
            }
        });
        return addview;
    }
    private long firstClick;
    private long lastClick;
    private int count;
    @SuppressLint("InflateParams")
    @SuppressWarnings("deprecation")
    private View createview(final BeverageUi item)
    {
        final int pid = item.getId();
        final int id = item.getSortIndex();
        final View beverageLayoutItem1 = getLayoutInflater().inflate(R.layout.layout_beverage_item,null);
        Display display = getWindowManager().getDefaultDisplay();

        int sizeaa = (display.getWidth()-2*XCELLCOUNT)/XCELLCOUNT;
        int sizeab = (display.getHeight()-30*YCELLCOUNT)/YCELLCOUNT;
        beverageLayoutItem1.setLayoutParams(new RelativeLayout.LayoutParams(sizeaa,sizeab));

        //final FrameLayout bg2 =  beverageLayoutItem1.findViewById(R.id.bg2);
        final LabelImageView ivIcon =  beverageLayoutItem1.findViewById(R.id.ivIcon);
        final TextView textView =  beverageLayoutItem1.findViewById(R.id.txtTitle);

        textView.setText(item.getName());
        String path = item.getIconPath() == null?"":item.getIconPath();
        Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
        if(bitmap == null)
        {
            final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path,200,200);
            if(tmpbitmap!=null)
            {
                PictureManager.getInstance().addBitmapToMemoryCache(path,tmpbitmap);
                bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            }
        }
        if(bitmap!=null)
            ivIcon.setImageBitmap(bitmap);
        if(id == 0)
            beverageLayoutItem1.setVisibility(View.INVISIBLE);
        else
            beverageLayoutItem1.setBackgroundResource(R.drawable.icon_rect);
        beverageLayoutItem1.setClickable(true);
        beverageLayoutItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstClick != 0 && System.currentTimeMillis() - firstClick > 300) {
                    count = 0;
                }
                count++;
                if (count == 1) {
                    firstClick = System.currentTimeMillis();
                } else if (count == 2) {
                    lastClick = System.currentTimeMillis();
                    // 两次点击小于300ms 也就是连续点击
                    if (lastClick - firstClick < 300) {// 判断是否是执行了双击事件
                        // TODO: 2018/6/25 shanchu gai tubiao tishi kuang
                        deleteDialog = new MaterialDialog.Builder(aty_drink_layout.this)
                                .title("Delete from laytout")
                                .content("Do you want to delete this drink form screen layout")
                                .positiveText("Yes")
                                .negativeText("No")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        item.setShowOrhide(0);
                                        beverageFactoryDao.getBeverageUiDao().update(item);
                                        setLayoutData();
                                    }
                                })
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(MaterialDialog dialog, DialogAction which) {
                                        deleteDialog.dismiss();
                                    }
                                })
                                .canceledOnTouchOutside(false)
                                .show();
                        /*boolean canbig=false;
                        for(DetailView a:m_list)
                        {
                            if(a.getIdSort() == id)
                            {
                                for(Beverage b:mBeverages)
                                {
                                    if(b.getUiSort() == id)
                                    {
                                        if(a.getPoint().x>=3 || a.getPoint().y>=3)
                                            canbig =false;
                                        else
                                        {
                                            canbig =true;
                                        }
                                    }
                                }
                            }
                        }
                        Intent intent = new Intent(ScreenLayoutActivityNew.this, ScreenLayoutChangeButton.class);
                        intent.putExtra("beverageId", pid);
                        intent.putExtra("canbig", canbig);
                        startActivityForResult(intent, REQ_CHANGE_BUTTON);*/
                    }
                }
            }
        });
        return beverageLayoutItem1;
    }
    /****************************************************************************/



    private ViewFlipper viewFlipper;
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_screensetting_layout);
        viewFlipper = findViewById(R.id.viewFlipper);
    }

    @Override
    public void InitData() {
        super.InitData();
        mYCellsCount = XCELLCOUNT;
        mXCellsCount = YCELLCOUNT;
        _DraggableLayout = new FreeSizeDraggableLayout(this);
        _DraggableLayout.setUnitWidthNum(mYCellsCount);
        _DraggableLayout.setUnitHeightNum(mXCellsCount);
        initmOccupied();
        loadBeverages();

    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        findViewById(R.id.btn_back).setOnClickListener(this);
        viewFlipper.setOnTouchListener(this);
        _DraggableLayout.setonDragFinishedListener(new FreeSizeDraggableLayout.MyDragListener(){

            @Override
            public void onDragFinishedListener(HashMap<Integer, Integer> a) {
                // TODO Auto-generated method stub
                reSortIcon(a);
            }});
        _DraggableLayout.setonDragMoveListener(new FreeSizeDraggableLayout.MyDragMoveLister(){
            @Override
            public void onDragMoveListener(int a) {
                // TODO Auto-generated method stub
                if(a==1)
                {
                    currentPage-=1;
                    currentPage =( currentPage<1?1:currentPage);
                    _DraggableLayout.changelistsource(currentPage-1);
                }else
                {

                    currentPage+=1;
                    currentPage =( currentPage>totalPage?totalPage:currentPage);
                    _DraggableLayout.changelistsource(currentPage-1);
                }
            }});
    }
    private void reSortIcon(HashMap<Integer,Integer> a)
    {

        Iterator<Map.Entry<Integer, Integer>> iter = a.entrySet().iterator();
        while (iter.hasNext()) {
            HashMap.Entry<Integer, Integer> entry = iter.next();
            Object key = entry.getKey();
            Object val = entry.getValue();
            String sql = "update tb_beverage_ui set sortIndex=? where pid = ?";
            try {
                getApp().getHelper().getBeverageUiDao().updateRaw(sql,val.toString(), key.toString());
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setLayoutData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_drink_layout.this);
                break;
        }
    }
    // 左右滑动时手指按下的X坐标
    private float touchDownX;
    // 左右滑动时手指松开的X坐标
    private float touchUpX;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDownX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                touchUpX = event.getX();
                if(totalPage<=1)
                {
                    return false;
                }
                if(touchDownX!=0)
                {
                    if (touchDownX - touchUpX > 120)
                    {
                        // 添加动画
                        touchDownX =0;
                        currentPage+=1;
                        currentPage =( currentPage>totalPage?totalPage:currentPage);
                        _DraggableLayout.changelistsource(currentPage-1);
                        return true;
                    }// 从右向左滑动
                    else if (touchDownX - touchUpX < -120)
                    {
                        touchDownX=0;
                        currentPage-=1;
                        currentPage =( currentPage<1?1:currentPage);
                        _DraggableLayout.changelistsource(currentPage-1);
                        return true;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
