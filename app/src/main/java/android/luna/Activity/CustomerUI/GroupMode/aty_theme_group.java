package android.luna.Activity.CustomerUI.GroupMode;
import android.content.Context;
import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.CustomerUI.BaseUi.BaseUi;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_11;
import android.luna.Activity.CustomerUI.GroupMode.adapter.adpter_group_button;
import android.luna.Activity.CustomerUI.TeaMode.aty_teamode_dispense;
import android.luna.Activity.CustomerUI.TeaMode.aty_teamode_selected;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.BeverageGroup;
import android.luna.Utils.Device.MachineConfig;
import android.luna.Utils.ImageConvertFactory;
import android.luna.Utils.PictureManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
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

import evo.luna.android.R;

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
        new Handler().postDelayed(new Runnable() {
            public void run() {
                groupdrinkMenuButtons = getBeverageFactoryDao().getDrinkIconItems(getApp().getCurrent_language(), grouplist.get(0));
                adpterGroupButton.setData(groupdrinkMenuButtons);
                //getApp().setGroupicon(groupiconset.get(0));
            }
        },  500);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        AppManager.getAppManager().RemoveRefInList(aty_theme_group.this.getClass());
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
                getApp().setGroupicon(groupiconset.get((Integer) view.getTag()));
                groupdrinkMenuButtons = getBeverageFactoryDao().getDrinkIconItems(getApp().getCurrent_language(), (Integer) view.getTag());
                adpterGroupButton.setData(groupdrinkMenuButtons);
            }
        });
        adpterGroupButton.setOnIconClickListener(new adpter_group_button.IconClickListener() {
            @Override
            public void onClicked(DrinkMenuButton a) {
                showTestToast("clicked icon:"+a.getName());
                getApp().set_drinkMenuButton(a);
                if(getApp().getGroupicon().equals(""))
                    getApp().setGroupicon(groupiconset.valueAt(0));
                if(MachineConfig.MACHINE_TYPE == MachineConfig.MACHINE_TYPE_TEA) {
                    startActivity(new Intent(aty_theme_group.this, aty_teamode_selected.class));
                }
                else if(MachineConfig.MACHINE_TYPE == MachineConfig.MACHINE_TYPE_COFFEE) {
                    startActivity(new Intent(aty_theme_group.this, aty_customer_ui_11.class));
                }
            }
        });
    }
    @Override
    public void InitFunctionLayout() {
        super.InitFunctionLayout();
        groupview = new CarrouselLayout(this);
        groupview.setAutoScrollDirection(CarrouselRotateDirection.anticlockwise);
        groupview.setAutoRotationTime(2000);
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        float width=dm.widthPixels;
        float height=dm.heightPixels;
        float widthfact =  (width/1280);
        float heightfact =  (height/800);
        adpterGroupButton.setMheightfact(heightfact);
        adpterGroupButton.setMwidthfact(widthfact);
        Log.i("sc","widthfact ="+widthfact);
        Log.i("sc","heightfact ="+heightfact);
        groupview.setR(width/8);
        groupview.setRotationX(-10);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)(700*widthfact),(int)(400*heightfact));
        lp.addRule(RelativeLayout.CENTER_VERTICAL,1);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT,1);
        groupview.setLayoutParams(lp);
        if(grouplist!=null && grouplist.size()>0)
        {
            ImageView group = new ImageView(this);
            String iconpath =groupiconset.get(grouplist.get(0));
            RelativeLayout.LayoutParams llp =new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            llp.addRule(RelativeLayout.CENTER_IN_PARENT,1);
            group.setImageBitmap(ImageConvertFactory.getsavefrompath(iconpath, (int) (600*widthfact), (int) (466*heightfact)));
            group.setTag(grouplist.get(0));
            group.setLayoutParams(llp);
            groupview.addView(group);
            if(grouplist.size()>1) {
                iconpath =groupiconset.get(grouplist.get(1));
                group = new ImageView(this);
                group.setLayoutParams(llp);
                group.setTag(grouplist.get(1));
                group.setImageBitmap(ImageConvertFactory.getsavefrompath(iconpath,(int) (600*widthfact), (int) (466*heightfact)));
                groupview.addView(group);
            }
            if(grouplist.size()>2) {
                group = new ImageView(this);
                group.setLayoutParams(llp);
                iconpath =groupiconset.get(grouplist.get(2));
                group.setTag(grouplist.get(2));
                group.setImageBitmap(ImageConvertFactory.getsavefrompath(iconpath,(int) (600*widthfact), (int) (466*heightfact)));
                groupview.addView(group);
            }
        }
        item_view = new GridView(this);
        item_view.setNumColumns(2);
        item_view.setHorizontalSpacing(40);
        item_view.setVerticalSpacing(20);
        lp = new RelativeLayout.LayoutParams((int)(350*widthfact),(int)(420*heightfact));
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
