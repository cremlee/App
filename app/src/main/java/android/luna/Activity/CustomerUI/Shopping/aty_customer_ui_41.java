package android.luna.Activity.CustomerUI.Shopping;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_1;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_12;
import android.luna.Activity.CustomerUI.Shopping.Adapter.CartItem;
import android.luna.Activity.CustomerUI.Shopping.Adapter.CartListAdapter;
import android.luna.Activity.CustomerUI.Shopping.Adapter.ShoppingAdapter;
import android.luna.Activity.ServiceUi.aty_service_main;
import android.luna.Data.CustomerUI.DrinkMenuButton;
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
import android.luna.Data.module.WasterBinStock;
import android.luna.Utils.FileHelper;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.HintLable.IconNumberView;
import android.luna.ViewUi.Progressbar.CircleProgressBar;
import android.luna.ViewUi.myViewFlipper;
import android.luna.rs232.Cmd.CmdMakeDrink;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/16.
 */

public class aty_customer_ui_41 extends BaseActivity implements View.OnClickListener,TextureView.SurfaceTextureListener{
    private ViewFlipper vf_bg;
    private View nextView,makeView;
    private CircleProgressBar cpbdrink;
    private Button step_next,btn_Ok;
    private TextView step_name;
    private ImageView step_pic;
    private MediaPlayer mMediaPlayer;
    private Surface _surface;
    private TextureView textureView;
    private CartItem cartitem;
    private BeverageFactoryDao beverageFactoryDao;
    private BeverageCount beverageCount;
    private StockFactoryDao stockFactoryDao;
    private IngredientFactoryDao ingredientFactoryDao;
    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_customer_ui_41);
        vf_bg = findViewById(R.id.vf_bg);
        nextView = getLayoutInflater().inflate(R.layout.lyt_step_next, null);
        vf_bg.addView(nextView);
        step_name =nextView.findViewById(R.id.step_name);
        step_next =nextView.findViewById(R.id.step_next);
        step_pic = nextView.findViewById(R.id.step_pic);
        makeView = getLayoutInflater().inflate(R.layout.lyt_step_texture, null);
        vf_bg.addView(makeView);
        textureView = makeView.findViewById(R.id.textureView);
        cpbdrink = findViewById(R.id.cpbdrink);
        btn_Ok = findViewById(R.id.btn_Ok);
        textureView.setSurfaceTextureListener(this);
        vf_bg.setDisplayedChild(1);

    }
    IntentFilter filter;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals(Constant.ACTION_MAKE_DRINK_ACK))
            {
                int ackresult = intent.getIntExtra("ACK",2);
                if(ackresult ==1)
                {
                    setTheNumberProgressBar();
                    new Thread(new DatabaseRunnable()).start();
                }
                else
                {
                    showToast("make drink failed:"+ackresult);
                    mHandler.sendEmptyMessageDelayed(1000,500);
                }
            }
            else if(action.equals(Constant.ACTION_MAKE_DRINK_FINISH_ACK))
            {
                // TODO: 2018/7/23 pan duan shi fou wei jugmode && cups left ==0?
                mHandler.sendEmptyMessageDelayed(1000,2000);
            }
        }
    };
    private class DatabaseRunnable implements Runnable
    {
        @Override
        public void run() {
            updateAllDrinkInformation();
        }
    }
    private void updateAllDrinkInformation()
    {
        int pid = cartitem.getDrinkMenuButton().getPid();
        // TODO: 2018/7/3 this is count part
        beverageCount = beverageFactoryDao.getBeverageCountDao().query(pid);
        if(beverageCount==null) {
            beverageCount = new BeverageCount();
            beverageCount.setPid(pid);
            beverageCount.setDrinkCount(1);
            beverageFactoryDao.getBeverageCountDao().create(beverageCount);
        }
        else
        {
            beverageCount.setDrinkCount(beverageCount.getDrinkCount()+1);
            beverageFactoryDao.getBeverageCountDao().update(beverageCount);
        }
        // TODO: 2018/7/3 canister part
        stockFactoryDao.updateCanisterStockByPid(pid);

        // TODO: 2018/7/3 stock part
        if(beverageFactoryDao.isWasterBinDrink(pid))
        {
            WasterBinStock tmp = stockFactoryDao.getWasterbinStockDao().queryByPid(99);
            if(tmp!=null) {
                tmp.setStock(tmp.getStock()-1);
                stockFactoryDao.getWasterbinStockDao().update(tmp);
            }
        }
        // TODO: 2018/7/3 payment part
    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }
    @Override
    public void InitData() {
        super.InitData();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        ingredientFactoryDao = new IngredientFactoryDao(this,getApp());
        stockFactoryDao = new StockFactoryDao(this,getApp());
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_Ok.setOnClickListener(this);
        step_next.setOnClickListener(this);

    }

    private Timer timer = null;
    private TimerTask mSendTimerTask = null;
    private boolean TimerPause =false;
    private boolean isFinish=false;
    private void cleanSendTimerTask() {
        if (mSendTimerTask != null) {
            mSendTimerTask.cancel();
            mSendTimerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
    private  void setTheNumberProgressBar()
    {
        TimerPause =false;
        isFinish=false;
        cpbdrink.setProgress(0);
        cpbdrink.setVisibility(View.VISIBLE);
        final int totaltm = makeDrinkTime();
        cpbdrink.setMax(totaltm);
        if (timer == null) {
            timer = new Timer();
        }
        mSendTimerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFinish && !TimerPause) {
                            cpbdrink.incrementProgressBy(100);
                            if (cpbdrink.getProgress() >= (totaltm -100)) {
                                cleanSendTimerTask();
                                isFinish = true;
                            }
                        }
                    }
                });
            }
        };
        timer.schedule(mSendTimerTask, 0, 100);
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    //AppManager.getAppManager().finishActivity(aty_customer_ui_12.this);
                    //// TODO: 2018/3/22 panduan shifou zuowan ruzuowan xianshi done
                    cpbdrink.setVisibility(View.INVISIBLE);
                    btn_Ok.setVisibility(View.VISIBLE);
                    cartitem.setCups(cartitem.getCups()-1);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filter = new IntentFilter();
        filter.addAction(Constant.ACTION_MAKE_DRINK_FINISH_ACK);
        filter.addAction(Constant.ACTION_MAKE_DRINK_ACK);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        cartitem = getApp().getCartItems().get(0);
        getApp().addCmdQueue((new CmdMakeDrink()).buildMakeDrinkCmd(cartitem.getDrinkMenuButton().getPid(), CmdMakeDrink.OPERATE_START, 3, 3, 3, 3, 3, 0));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initnextdrink(DrinkMenuButton a)
    {
        btn_Ok.setVisibility(View.INVISIBLE);
        step_name.setText(a.getName());
        String path = a.getIconpath();
        if(path!=null) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 240, 240);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            if (bitmap != null)
                step_pic.setImageBitmap(bitmap);
            else
                step_pic.setImageBitmap(null);
        }
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.step_next:
                vf_bg.setDisplayedChild(1);
                getApp().addCmdQueue((new CmdMakeDrink()).buildMakeDrinkCmd(cartitem.getDrinkMenuButton().getPid(), CmdMakeDrink.OPERATE_START, 3, 3, 3, 3, 3, 0));
                break;
            case R.id.btn_Ok:
                if(cartitem.getCups()==0) {
                    getApp().getCartItems().remove(0);
                    if (getApp().getCartItems().size() == 0) {
                        AppManager.getAppManager().finishActivity(aty_customer_ui_41.this);
                    }
                    else
                    {
                        cartitem =  getApp().getCartItems().get(0);
                        initnextdrink(cartitem.getDrinkMenuButton());
                        vf_bg.setDisplayedChild(0);
                    }
                }
                else
                {
                    initnextdrink(cartitem.getDrinkMenuButton());
                    vf_bg.setDisplayedChild(0);
                }
                break;
        }
    }

    private class PlayerVideo extends Thread{
        @Override
        public void run(){
            String res = cartitem.getDrinkMenuButton().getDispensepath();
            try {
                mMediaPlayer= new MediaPlayer();
                mMediaPlayer.setDataSource(aty_customer_ui_41.this, Uri.parse(res));
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
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        _surface = new Surface(surface);
        new aty_customer_ui_41.PlayerVideo().start();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
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
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
    private int makeDrinkTime() {
        int totalTime = 0;
        try {
            List<BeverageIngredient> beverageIngredients = beverageFactoryDao.getBeverageIngerdient().queryforbeveragepid(cartitem.getDrinkMenuButton().getPid());
            int tempTime = 0;
            for (int i = 0; i < beverageIngredients.size(); i++) {
                BeverageIngredient beverageIngredient = beverageIngredients.get(i);
                int ingredientType = beverageIngredient.getIngredientType();
                int startTime = beverageIngredient.getStartTime();// *																	// (beverageIngredient.getScaleUp()																	// / 100);
                int ingredientTime = 0;
                switch (ingredientType) {
                    case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                        /*IngredientFilterBrewAdvance filterBrewadv = getHelper().getIngredientFilterBrewAdvanceDao().queryBuilder().where().eq("pid", beverageIngredient.getIngredientPid()).queryForFirst();
                        if(filterBrewadv!=null)
                        {
                            FilterBrewStepDao aa = new FilterBrewStepDao(this,getApp());
                            ingredientTime = 20000+aa.getsteptime(beverageIngredient.getIngredientPid());
                        }*/
                        break;
                    case Ingredient.TYPE_FILTER_BREW:
                        IngredientFilterBrew filterBrew = ingredientFactoryDao.getFilterBrewDao().findByT(beverageIngredient.getIngredientPid());
                        if (filterBrew != null) {						// 计算总时间
                            final int BREW_RUNNING_TIME = 10000;
                            int time = filterBrew.getTmPre13();
                            ingredientTime = (filterBrew.getDecompressTime() + filterBrew.getExtractionTime() + filterBrew.getOpenDelayTime() + time) * 100 + BREW_RUNNING_TIME;
                        }
                        break;
                    case Ingredient.TYPE_INSTANT:
                        IngredientInstant instant = ingredientFactoryDao.getInstantDao().findByT(beverageIngredient.getIngredientPid());
                        if (instant != null) {
                            ingredientTime = (int) ((instant.getWaterVolume() * beverageIngredient.getScaleUp() / 100 + instant.getPreflushVolume() + instant.getWaterAfterFlushVolume()) * 1000 / 20) + (instant.getPauseTimeAfterDispense() + instant.getPreflushPauseTime()) + 2000;
                        }
                        break;
                    case Ingredient.TYPE_WATER:
                        IngredientWater water = ingredientFactoryDao.getWaterDao().findByT(beverageIngredient.getIngredientPid());
                        if (water != null) {
                            ingredientTime = (int) (water.getWaterVolume() / 20 * 1000);
                        }
                        break;
                    case Ingredient.TYPE_ESPRESSO:
                        IngredientEspresso espresso = ingredientFactoryDao.getEspressoDao().findByT(beverageIngredient.getIngredientPid());
                        if(espresso!=null)
                        {
                            ingredientTime = (int) (espresso.getBrewtime()+espresso.getPrebrewtime()+espresso.getPreinfusiontime())*1000+10000;
                        }
                        break;
                    default:
                        break;
                }
                tempTime = startTime + ingredientTime;
                totalTime = tempTime > totalTime ? tempTime : totalTime;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (totalTime + 4000) * 1;
    }
}
