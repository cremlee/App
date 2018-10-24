package android.luna.Activity.CustomerUI.Gallery;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Payment.Fgt_Wechat;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.PaymentDao;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.module.Payment.PayConstant;
import android.luna.Data.module.Payment.Wechat.Pay_Queryorder;
import android.luna.Data.module.Payment.Wechat.Pay_Unifiedorder;
import android.luna.Data.module.Payment.Wechat.ProductUnifiedorder;
import android.luna.Data.module.PaymentSetting;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Data.module.Powder.PowderNutrition;
import android.luna.Data.module.Powder.PowderTypeAmount;
import android.luna.SDK.Wechat.WXPay;
import android.luna.SDK.Wechat.WXPayConfigImpl;
import android.luna.SDK.Wechat.WXPayUtil;
import android.luna.Utils.Nutrition.CalcHelpper;
import android.luna.Utils.Nutrition.NutritionListItem;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.NutritionUi.NutritionPopWindow;
import android.luna.ViewUi.Ratebar.MyRateBar;
import android.luna.ViewUi.ViewPager.Rotate3dAnimation;
import evo.luna.android.R;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.zhouwei.library.CustomPopWindow;
import com.lid.lib.LabelImageView;

import java.util.List;

/**
 * Created by Lee.li on 2017/12/28.
 */

public class aty_customer_ui_11 extends BaseActivity implements View.OnClickListener,GestureDetector.OnGestureListener ,View.OnTouchListener,TextureView.SurfaceTextureListener,SeekBar.OnSeekBarChangeListener {
    private static String Tag = "aty_customer_ui_11";
    private NutritionPopWindow mPopWindow;
    private boolean showmPopWindow;
    private TextView drinkname,drinkprice;
    private LabelImageView drinkimage;
    private ViewFlipper vp_drinkstory;
    private Button btndrinkinfo ,btndrinkstart,btnback,btn_jug;
    private View preselectView,PicView,textureView;
    private ImageView step_pic;
    private GestureDetector mGestureDetector;
    private TextureView step_texture;
    private int mposition =0;
    private MediaPlayer mMediaPlayer;
    private Surface _surface;
    private MyRateBar rbStrength, rbVolume, rbMilk, rbTopping;
    private PowderFactory powderFactory =null;
    private BeverageFactoryDao beverageFactoryDao =null;
    private ImageView wechatcode ,alicode;
    private LinearLayout item_cup,item_strength,item_milk,item_top;
    private PaymentDao paymentDao;
    private RelativeLayout rly_pay;
    private FrameLayout fly_pay;
    private Button pay_qr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_customer_ui_11);
        wechatcode = findViewById(R.id.wechatcode);
        alicode = findViewById(R.id.alicode);
        drinkname = findViewById(R.id.drinkname);
        drinkprice = findViewById(R.id.drinkprice);
        vp_drinkstory= findViewById(R.id.vp_drinkstory);
        btndrinkinfo= findViewById(R.id.btndrinkinfo);
        btndrinkstart= findViewById(R.id.btndrinkstart);
        btn_jug = findViewById(R.id.btn_jug);
        btnback = findViewById(R.id.btnback);
        drinkimage = findViewById(R.id.drinkimage);
        preselectView = getLayoutInflater().inflate(R.layout.lyt_step_preselect_improve, null);
        vp_drinkstory.addView(preselectView);
        rly_pay = findViewById(R.id.rly_pay);
        fly_pay = findViewById(R.id.fly_pay);
        if(_paymentSetting.isPaymentEnable() && getApp().get_drinkMenuButton().getPrice()>0)
        {
            rly_pay.setVisibility(View.VISIBLE);
            drinkprice.setText(Float.toString(getApp().get_drinkMenuButton().getPrice()));
            btndrinkstart.setVisibility(View.INVISIBLE);
        }else
        {
            vp_drinkstory.setVisibility(View.VISIBLE);
        }

        pay_qr = findViewById(R.id.pay_qr);
        item_cup =preselectView.findViewById(R.id.item_cup);
        item_strength =preselectView.findViewById(R.id.item_strength);
        item_milk =preselectView.findViewById(R.id.item_milk);
        item_top =preselectView.findViewById(R.id.item_top);

        if(getApp().get_drinkMenuButton().isVolume())
            item_cup.setVisibility(View.VISIBLE);
        if(getApp().get_drinkMenuButton().isStrength())
            item_strength.setVisibility(View.VISIBLE);
        if(getApp().get_drinkMenuButton().isMilk())
            item_milk.setVisibility(View.VISIBLE);
        if(getApp().get_drinkMenuButton().isTopping())
            item_top.setVisibility(View.VISIBLE);

        rbStrength = preselectView.findViewById(R.id.rbStrength);
        rbMilk = preselectView.findViewById(R.id.rbMilk);
        rbVolume = preselectView.findViewById(R.id.rbVolume);
        rbTopping = preselectView.findViewById(R.id.rbTopping);


        rbStrength.setProgress(2);
        rbVolume.setProgress(2);
        rbMilk.setProgress(2);
        rbTopping.setProgress(2);

        String res = getApp().get_drinkMenuButton().getStorypath();
        if(res.endsWith(".mp4")) {
            textureView = getLayoutInflater().inflate(R.layout.lyt_step_texture, null);
            step_texture = textureView.findViewById(R.id.textureView);
            vp_drinkstory.addView(textureView);
            step_texture.setSurfaceTextureListener(this);
        }
        else if(res.endsWith(".png") ||  res.endsWith(".jpg"))
        {
            PicView = getLayoutInflater().inflate(R.layout.lyt_step_picture, null);
            step_pic = PicView.findViewById(R.id.step_pic);
            vp_drinkstory.addView(PicView);

            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(res);
            if (bitmap == null) {
                 Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(res, 1024, 768,false);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(res, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(res);
                }
            }
            if (bitmap != null)
                step_pic.setImageBitmap(bitmap);
            else
                step_pic.setImageBitmap(null);
        }
        drinkname.setText(getApp().get_drinkMenuButton().getName());
        String path = getApp().get_drinkMenuButton().getIconpath();
        if(path!=null) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path,250,250);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            if (bitmap != null)
                drinkimage.setImageBitmap(bitmap);
            else
                drinkimage.setImageBitmap(null);
        }
    }

    @Override
    public void InitData() {
        super.InitData();
        mGestureDetector = new GestureDetector(this);
        powderFactory = new PowderFactory(this,getApp());
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        paymentDao= new PaymentDao(this,getApp());
        _paymentSetting = paymentDao.Query();
        if(_paymentSetting==null) {
            _paymentSetting = new PaymentSetting(1);
            paymentDao.Update(_paymentSetting);
        }
        try {
            config = WXPayConfigImpl.getInstance();

            wxpay = new WXPay(config);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private  PowderNutrition totalpowderNutrition = new PowderNutrition();
    private  int _containmask=0;
    private  int _maycontainmask=0;
    private PaymentSetting _paymentSetting;
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

    private void dealwithmaskcode()
    {
        _maycontainmask&=(~_containmask);
    }
    @Override
    public void InitEvent() {
        super.InitEvent();
        btndrinkinfo.setOnClickListener(this);
        btndrinkstart.setOnClickListener(this);
        btnback.setOnClickListener(this);
        btn_jug.setOnClickListener(this);
        //// TODO: 2018/1/2 video player (Story)

        //// TODO: 2018/1/2 picture (Story)
        vp_drinkstory.setOnTouchListener(this);
        //// TODO: 2018/1/25 Preselection event
        rbMilk.setOnSeekBarChangeListener(this);
        rbVolume.setOnSeekBarChangeListener(this);
        pay_qr.setOnClickListener(this);
    }

    private void showPopupWindow(View view) {
        calcpowder();
        List<NutritionListItem> data = CalcHelpper.getNutritionfromdrink(totalpowderNutrition);
        if(mPopWindow == null) {
            mPopWindow = new NutritionPopWindow(aty_customer_ui_11.this,data,_containmask,_maycontainmask);
        }
        mPopWindow.show(view);
    }
    private void DismissPopWindow()
    {
        mPopWindow.dismiss();
        showmPopWindow =false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer!=null)
        {
            if(mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer=null;
            _surface =null;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(null);
        if(payQueryorder!=null)
            payQueryorder.setQuerycount(0);
    }

    private Fgt_Wechat fgtWechat;
    private Pay_Unifiedorder payUnifiedorderthread=null;
    private Pay_Queryorder payQueryorder=null;
    private WXPay wxpay;
    private WXPayConfigImpl config;
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.pay_qr:
                if(fgtWechat==null)
                    fgtWechat = new Fgt_Wechat();
                getFragmentManager().beginTransaction().replace(R.id.fly_pay,fgtWechat).commit();
                if(payUnifiedorderthread==null)
                {
                    payUnifiedorderthread = new Pay_Unifiedorder(mHandler,wxpay);
                    ProductUnifiedorder productUnifiedorder =new ProductUnifiedorder("evo test","lab c machine",1, WXPayUtil.getLocalIpAddress(this));
                    payUnifiedorderthread.setProductUnifiedorder(productUnifiedorder);
                    payUnifiedorderthread.start();
                    findViewById(R.id.pay_select).setVisibility(View.GONE);
                }
                break;
            case R.id.btndrinkinfo:
                showPopupWindow(btnback);
                break;
            case R.id.btnback:
                AppManager.getAppManager().finishActivity(aty_customer_ui_11.this);
                break;
            case R.id.btndrinkstart:
                startActivity(new Intent(aty_customer_ui_11.this, aty_customer_ui_12.class));
                //// TODO: 2018/7/4 chuandi yingliao canshu
                AppManager.getAppManager().finishActivity(aty_customer_ui_11.this);
                break;
            case R.id.btn_jug:
                View contentView = LayoutInflater.from(this).inflate(R.layout.pop_win_jug,null);
                handleLogic(contentView);
                mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(contentView).setFocusable(true).setOutsideTouchable(true).create().showAsDropDown(view,0,10);
                break;

        }
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
                btn_jug.setText(showContent);
               showToast(showContent);
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
        contentView.findViewById(R.id.menu3).setOnClickListener(listener);
        contentView.findViewById(R.id.menu4).setOnClickListener(listener);
        contentView.findViewById(R.id.menu5).setOnClickListener(listener);
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
        if(vp_drinkstory.getChildCount()<=1)
            return false;
        float halfWidth=vp_drinkstory.getWidth()/2.0f;
        float halfHeight=vp_drinkstory.getHeight()/2.0f;
        int duration=500;
        int depthz=0;//viewFlipper.getWidth()/2;
        if (motionEvent1.getX() - motionEvent.getX() > 0) {
            Rotate3dAnimation in=new Rotate3dAnimation(-90, 0,halfWidth,halfHeight,depthz,false);
            in.setDuration(duration);
            in.setStartOffset(duration);
            vp_drinkstory.setInAnimation(in);
            Rotate3dAnimation out=new Rotate3dAnimation(0, 90,halfWidth,halfHeight,depthz,false);
            out.setDuration(duration);
            vp_drinkstory.setOutAnimation(out);
            vp_drinkstory.showPrevious();

        } else {
            Rotate3dAnimation in=new Rotate3dAnimation(90, 0,halfWidth,halfHeight,depthz,false);
            in.setDuration(duration);
            in.setStartOffset(duration);
            vp_drinkstory.setInAnimation(in);
            Rotate3dAnimation out=new Rotate3dAnimation(0, -90,halfWidth,halfHeight,depthz,false);
            out.setDuration(duration);
            vp_drinkstory.setOutAnimation(out);
            vp_drinkstory.showNext();
        }
        return  true;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
         mGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class PlayerVideo extends Thread{
        @Override
        public void run(){
            String res = getApp().get_drinkMenuButton().getStorypath();
            try {
                mMediaPlayer= new MediaPlayer();
                mMediaPlayer.setDataSource(aty_customer_ui_11.this, Uri.parse(res));
                mMediaPlayer.setSurface(_surface);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp){
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        _surface = new Surface(surfaceTexture);
        new PlayerVideo().start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (mMediaPlayer != null) {
            if(mMediaPlayer.isPlaying())
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
            _surface =null;
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    private int cmdVolume=2;
    private int cmdMilk=2;
    private boolean Iswork =false;
    int mProgress;

    private  void changemilk(final int changevalue)
    {
       // drinkimage.adjustMilk(changevalue+3);
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int value = i;
        switch (seekBar.getId()) {
            case R.id.rbMilk:
               //changemilk(value);
                cmdMilk =value;
                break;
            case R.id.rbVolume:
                /*final float x1,x2;
                x1=1.0f+(cmdVolume-2)*0.05f;
                x2=1.0f+(value-2)*0.05f;*/
                cmdVolume = value;
                //drinkimage.SetScale(x2);
               /* ScaleAnimation sanimation = new ScaleAnimation(x1, x2, x1, x2,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                sanimation.setDuration(300); //设置持续时间5秒
                sanimation.setFillAfter(true);
                drinkimage.startAnimation(sanimation);
                sanimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        drinkimage.setLayoutParams(new RelativeLayout.LayoutParams((int)(drinkimage.getWidth()*x2),(int)(drinkimage.getHeight()*x2)));
                        //drinkimage.setons
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });*/
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PayConstant.PAY_UNIFIEDORDER_OK:
                    if(fgtWechat!=null && fgtWechat.isAdded())
                    {
                        fgtWechat.showqrcode();
                        // TODO: 2018/8/1 query the order
                        payQueryorder = new Pay_Queryorder(mHandler,wxpay);
                        payQueryorder.start();
                    }
                    break;
                case PayConstant.PAY_CONFIG_FAILED:
                    showTestToast("PAY_CONFIG_FAILED");
                    break;
                case PayConstant.PAY_ORDER_FAILED:
                    showTestToast("PAY_ORDER_FAILED");
                    break;
                case PayConstant.PAY_ORDER_FINISHED:
                    showTestToast("PAY_ORDER_FINISHED");
                    rly_pay.setVisibility(View.GONE);
                    vp_drinkstory.setVisibility(View.VISIBLE);
                    btndrinkstart.setVisibility(View.VISIBLE);
                    break;
                case PayConstant.PAY_UNIFIEDORDER_FAILED:
                    showTestToast("PAY_UNIFIEDORDER_FAILED");
                    break;
                default:
                    break;
            }
        }
    };
}
