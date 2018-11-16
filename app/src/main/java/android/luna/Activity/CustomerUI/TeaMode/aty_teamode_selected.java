package android.luna.Activity.CustomerUI.TeaMode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Data.module.Powder.PowderNutrition;
import android.luna.Data.module.Powder.PowderTypeAmount;
import android.luna.Utils.ImageConvertFactory;
import android.luna.Utils.Nutrition.CalcHelpper;
import android.luna.Utils.Nutrition.NutritionListItem;
import android.luna.ViewUi.NutritionUi.NutritionPopWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;

import java.util.List;

import evo.luna.android.R;
public class aty_teamode_selected extends BaseActivity implements View.OnClickListener,RatingBar.OnRatingBarChangeListener {
    private Button back,info,start,jug;
    private RatingBar strengthBatingBar,extractionBatingBar,volumeBatingBar;
    private ImageView icon;
    private TextView name;
    private RelativeLayout bg;

    private PowderFactory powderFactory =null;
    private BeverageFactoryDao beverageFactoryDao =null;
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_teamode_selected);
        back = findViewById(R.id.back);
        info = findViewById(R.id.info);
        start = findViewById(R.id.start);
        jug = findViewById(R.id.jug);


        strengthBatingBar = findViewById(R.id.strengthBatingBar);
        extractionBatingBar = findViewById(R.id.extractionBatingBar);
        volumeBatingBar = findViewById(R.id.volumeBatingBar);

        strengthBatingBar.setProgress(3);
        extractionBatingBar.setProgress(3);
        volumeBatingBar.setProgress(3);

        icon = findViewById(R.id.icon);
        name = findViewById(R.id.name);

        name.setText(getApp().get_drinkMenuButton().getName());
        icon.setImageBitmap(ImageConvertFactory.getfrompath(getApp().getGroupicon()));

        bg = findViewById(R.id.bg);
        if(!getApp().get_drinkMenuButton().getStorypath().equals(""))
        {
            Bitmap bitmap = ImageConvertFactory.getfrompath(getApp().get_drinkMenuButton().getStorypath(),800,600);
            bg.setBackground(new BitmapDrawable(getResources(),bitmap));
        }
        if(getApp().get_drinkMenuButton().getJugmode()!=1)
        {
            jug.setEnabled(false);
            jug.setAlpha(0.2f);
        }
    }

    @Override
    public void InitData() {
        super.InitData();
        powderFactory = new PowderFactory(this,getApp());
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        back.setOnClickListener(this);
        info.setOnClickListener(this);
        start.setOnClickListener(this);
        jug.setOnClickListener(this);

        strengthBatingBar.setOnRatingBarChangeListener(this);
        extractionBatingBar.setOnRatingBarChangeListener(this);
        volumeBatingBar.setOnRatingBarChangeListener(this);
    }
    private PowderNutrition totalpowderNutrition = new PowderNutrition();
    private  int _containmask=0;
    private  int _maycontainmask=0;
    private void calcpowder()
    {
        totalpowderNutrition.reset();
        _containmask =0;
        _maycontainmask=0;
        PowderNutrition powderNutrition = null;
        List<PowderTypeAmount> powderTypeAmountList = beverageFactoryDao.getPowderInfo(getApp().get_drinkMenuButton().getPid());
        if(powderTypeAmountList.size()>0)
        {
            for (PowderTypeAmount item :powderTypeAmountList)
            {
                powderNutrition = powderFactory.getPowderNutritionDao().query(item.getType());
                if(powderNutrition!=null)
                {
                    totalpowderNutrition.setSugar(totalpowderNutrition.getSugar()+powderNutrition.getSugar()*item.getAmount()/100);
                    totalpowderNutrition.setSodium(totalpowderNutrition.getSodium()+powderNutrition.getSodium()*item.getAmount()/100);
                    totalpowderNutrition.setFat(totalpowderNutrition.getFat()+powderNutrition.getFat()*item.getAmount()/100);
                    totalpowderNutrition.setProtein(totalpowderNutrition.getProtein()+powderNutrition.getProtein()*item.getAmount()/100);
                    totalpowderNutrition.setCarbohydrate(totalpowderNutrition.getCarbohydrate()+powderNutrition.getCarbohydrate()*item.getAmount()/100);
                    totalpowderNutrition.setKilocalorie(totalpowderNutrition.getKilocalorie()+powderNutrition.getKilocalorie()*item.getAmount()/100);
                }
                PowderItem powderItem = powderFactory.getPowerItemDao().query(item.getType());
                if(powderItem!=null)
                {
                    _containmask |=powderItem.getContainmask();
                    _maycontainmask |=powderItem.getMaycontainmask();
                }
            }
        }
        dealwithmaskcode();
    }
    private NutritionPopWindow mPopWindow;
    private boolean showmPopWindow;
    private void showPopupWindow(View view) {
        calcpowder();
        List<NutritionListItem> data = CalcHelpper.getNutritionfromdrink(totalpowderNutrition);
        if(mPopWindow == null) {
            mPopWindow = new NutritionPopWindow(aty_teamode_selected.this,data,_containmask,_maycontainmask);
        }
        mPopWindow.showincenter(view);
    }
    private void DismissPopWindow()
    {
        mPopWindow.dismiss();
        showmPopWindow =false;
    }
    private void dealwithmaskcode()
    {
        _maycontainmask&=(~_containmask);
    }
    CustomPopWindow mCustomPopWindow=null;
    private void handleLogic(View contentView){
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "1";
                        break;
                    case R.id.menu2:
                        showContent = "2";
                        break;
                    case R.id.menu3:
                        showContent = "3";
                        break;
                    case R.id.menu4:
                        showContent = "4";
                        break;
                    case R.id.menu5:
                        showContent = "5" ;
                        break;
                }
                jug.setText(showContent);
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
    }
    @Override
    public void onClick(View v) {
         int id =v.getId();
         switch (id)
         {
             case R.id.back:
                 AppManager.getAppManager().finishActivity(aty_teamode_selected.this);
                 break;
             case R.id.info:
                 showPopupWindow(bg);
                 break;
             case R.id.start:
                 startActivity(new Intent(aty_teamode_selected.this, aty_teamode_dispense.class));
                 AppManager.getAppManager().finishActivity(aty_teamode_selected.this);
                 break;
             case R.id.jug:
                 View contentView = LayoutInflater.from(this).inflate(R.layout.pop_win_jug,null);
                 handleLogic(contentView);
                 mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                         .setView(contentView).setFocusable(true).setOutsideTouchable(true).create().showAsDropDown(jug,0,-300);

                 break;
         }
    }
    private int cmdvolume = 0x80;
    private int cmdstrenght = 0x80;
    private int cmdext = 0x80;
    private int convertdrinkvalue(float a)
    {
       int value = Math.round(a);
       if(value>2)
       {
           value=value-3+0x80;
       }
       return value;
    }
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (fromUser)
        {
            switch (ratingBar.getId())
            {
                case R.id.strengthBatingBar:
                     cmdstrenght = convertdrinkvalue(rating);
                    break;
                case R.id.volumeBatingBar:
                     cmdvolume = convertdrinkvalue(rating);
                    break;
                case R.id.extractionBatingBar:
                    cmdext = convertdrinkvalue(rating);
                    break;
            }
        }
    }
}
