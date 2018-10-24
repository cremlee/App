package android.luna.Activity.CustomerUI.GroupMode;
import android.content.Context;
import android.luna.Activity.CustomerUI.BaseUi.BaseUi;
import android.luna.Activity.CustomerUI.GroupMode.adapter.adpter_group_button;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.BeverageGroup;
import android.luna.Utils.ImageConvertFactory;
import android.luna.Utils.PictureManager;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.dalong.carrousellayout.CarrouselLayout;
import com.dalong.carrousellayout.CarrouselRotateDirection;
import com.dalong.carrousellayout.OnCarrouselItemSelectedListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
public class aty_theme_group extends BaseUi implements BaseUi.Languagechanged {
    private CarrouselLayout groupview;
    private GridView item_view;
    private adpter_group_button adpterGroupButton;
    private List<BeverageGroup> beverageGroupList =null;
    private BeverageFactoryDao beverageFactoryDao =null;
    private HashMap<Integer,String> groupadapter = new HashMap<>();
    private SparseArray<String> groupiconset = new SparseArray<>();
    private List<Integer> grouplist = new ArrayList<>(3);
    private List<DrinkMenuButton> groupdrinkMenuButtons =new ArrayList<>(4);
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
    }

    @Override
    public void InitData() {
        super.InitData();
        adpterGroupButton = new adpter_group_button(this,groupdrinkMenuButtons,getApp());
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        getgroupdataset();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        initLinstener();
    }

    private void getgroupdataset() {
        grouplist.clear();
        groupadapter.clear();
        beverageGroupList = beverageFactoryDao.getBeverageGroup().queryall();
        Collections.sort(beverageGroupList);
        int pid = -1;
        String name = "";
        for (int i = 0; i < beverageGroupList.size(); i++) {
            if (pid == -1 && name.equals("")) {
                pid = beverageGroupList.get(i).getGroupid();
                name = beverageGroupList.get(i).getName();
                groupadapter.put(pid, name);
            } else {
                if (pid != beverageGroupList.get(i).getGroupid()) {
                    pid = beverageGroupList.get(i).getGroupid();
                    name = beverageGroupList.get(i).getName();
                    groupadapter.put(pid, name);
                }
            }
            groupiconset.put(pid,beverageGroupList.get(i).getIconpath());
        }

        Iterator iter = groupadapter.keySet().iterator();
        while (iter.hasNext()) {
            Object key = iter.next();
            grouplist.add((Integer) key);
        }
    }
    private void initLinstener() {
        groupview.setOnCarrouselItemSelectedListener(new OnCarrouselItemSelectedListener() {

            @Override
            public void selected(View view, int position) {
                groupdrinkMenuButtons = getBeverageFactoryDao().getDrinkIconItems(getApp().getCurrent_language(), (Integer) view.getTag());
                adpterGroupButton.setData(groupdrinkMenuButtons);
            }
        });
    }
    @Override
    public void InitFunctionLayout() {
        super.InitFunctionLayout();
        groupview = new CarrouselLayout(this);
        //groupview.setBackgroundColor(this.getResources().getColor(R.color.red));
        groupview.setAutoScrollDirection(CarrouselRotateDirection.anticlockwise);
        groupview.setAutoRotationTime(2000);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        float width=dm.widthPixels;
        groupview.setR(width/8);
        groupview.setRotationX(-10);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(700,400);
        lp.addRule(RelativeLayout.CENTER_VERTICAL,1);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,1);
        lp.setMarginStart(50);
        groupview.setLayoutParams(lp);
        if(grouplist!=null && grouplist.size()>0)
        {
            ImageView group = new ImageView(this);
            String iconpath =groupiconset.get(grouplist.get(0));
            group.setImageBitmap(ImageConvertFactory.getsavefrompath(iconpath,600,466));
            group.setTag(grouplist.get(0));
            groupview.addView(group);
            if(grouplist.size()>1) {
                iconpath =groupiconset.get(grouplist.get(1));
                group = new ImageView(this);
                group.setTag(grouplist.get(1));
                group.setImageBitmap(ImageConvertFactory.getsavefrompath(iconpath,600,466));
                groupview.addView(group);
            }
            if(grouplist.size()>2) {
                group = new ImageView(this);
                iconpath =groupiconset.get(grouplist.get(2));
                group.setTag(grouplist.get(2));
                group.setImageBitmap(ImageConvertFactory.getsavefrompath(iconpath,600,466));
                groupview.addView(group);
            }
        }

        item_view = new GridView(this);
        //item_view.setBackgroundColor(this.getResources().getColor(R.color.red));
        item_view.setNumColumns(2);
        item_view.setHorizontalSpacing(40);
        item_view.setVerticalSpacing(20);
        lp = new RelativeLayout.LayoutParams(350,420);
        //lp.addRule(RelativeLayout.CENTER_VERTICAL,1);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
        lp.setMargins(0,120,50,0);
        item_view.setLayoutParams(lp);
        item_view.setAdapter(adpterGroupButton);
        getRlyt_bg().addView(groupview);
        getRlyt_bg().addView(item_view);
    }

    @Override
    public void updated() {

    }
}
