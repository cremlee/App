package android.luna.Activity.CustomerUI.TeaMode;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.SurfaceTexture;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_1;
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
import android.luna.Utils.ImageConvertFactory;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.ViewUi.Progressbar.CircleProgressBar;
import android.luna.rs232.Cmd.CmdMakeDrink;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import evo.luna.android.R;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Lee.li on 2017/12/28.
 */

public class aty_teamode_dispense extends BaseActivity implements View.OnClickListener,TextureView.SurfaceTextureListener {
    private static String Tag = "aty_teamode_dispense";
    private ViewFlipper vf_bg;
    private CircleProgressBar cpbdrink;
    private View Viewgif,ViewPic,ViewPictxt,viewVideo;
    private TextureView step_video;
    private ImageView step_pic;
    private GifImageView step_gif;
    private MediaPlayer mMediaPlayer;
    private Surface _surface;
    private BeverageFactoryDao beverageFactoryDao;
    private BeverageCount beverageCount;
    private StockFactoryDao stockFactoryDao;
    private IngredientFactoryDao ingredientFactoryDao;
    private int curPid ;
    IntentFilter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curPid =1;//getApp().get_drinkMenuButton().getPid();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        ingredientFactoryDao = new IngredientFactoryDao(this,getApp());
        stockFactoryDao = new StockFactoryDao(this,getApp());
        filter = new IntentFilter();
        filter.addAction(Constant.ACTION_MAKE_DRINK_FINISH_ACK);
        filter.addAction(Constant.ACTION_MAKE_DRINK_ACK);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        // TODO: 2018/6/28 send make drink cmd for test
        //getApp().addCmdQueue((new CmdMakeDrink()).buildMakeDrinkCmd(getApp().get_drinkMenuButton().getPid(), CmdMakeDrink.OPERATE_START, 3, 3, 3, 3, 3, 0));
        setTheNumberProgressBar();
    }
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
                // TODO: 2018/7/23 qiehuan jiemian
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
        // TODO: 2018/7/3 this is count part
        beverageCount = beverageFactoryDao.getBeverageCountDao().query(getApp().get_drinkMenuButton().getPid());
        if(beverageCount==null) {
            beverageCount = new BeverageCount();
            beverageCount.setPid(getApp().get_drinkMenuButton().getPid());
            beverageCount.setDrinkCount(1);
            beverageFactoryDao.getBeverageCountDao().create(beverageCount);
        }
        else
        {
            beverageCount.setDrinkCount(beverageCount.getDrinkCount()+1);
            beverageFactoryDao.getBeverageCountDao().update(beverageCount);
        }
        // TODO: 2018/7/3 canister part
        stockFactoryDao.updateCanisterStockByPid(getApp().get_drinkMenuButton().getPid());

        // TODO: 2018/7/3 stock part
        if(beverageFactoryDao.isWasterBinDrink(getApp().get_drinkMenuButton().getPid()))
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
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_teamode_dispens);
        vf_bg = findViewById(R.id.vf_bg);
        cpbdrink = findViewById(R.id.cpbdrink);
        String res = FileHelper.PATH_DRINK_AM+"a1.mp4";//getApp().get_drinkMenuButton().getDispensepath();
        if(res.toLowerCase().endsWith("mp4")) {
            //// TODO: 2018/2/28 panduan shebei yingliao zhizuo shi de ziyuan
            viewVideo = getLayoutInflater().inflate(R.layout.lyt_step_texture, null);
            step_video = viewVideo.findViewById(R.id.textureView);
            vf_bg.addView(viewVideo);
            step_video.setSurfaceTextureListener(this);
        }
        else if(res.toLowerCase().endsWith("jpg") || res.toLowerCase().endsWith("png"))
        {
            // TODO: 2018/9/3 xianshi tupian zuowei ziyuan
            ViewPic = getLayoutInflater().inflate(R.layout.lyt_step_picture, null);
            step_pic = ViewPic.findViewById(R.id.step_pic);
            step_pic.setImageBitmap(ImageConvertFactory.getsavefrompath(res,800,600));
            vf_bg.addView(ViewPic);
        }
        else if(res.toLowerCase().endsWith("gif"))
        {
            // TODO: 2018/9/3 show gif when dispense
            Viewgif = getLayoutInflater().inflate(R.layout.lyt_step_gif, null);
            step_gif = Viewgif.findViewById(R.id.step_gif);
            try {
                GifDrawable gifDrawable = new GifDrawable(res);
                step_gif.setImageDrawable(gifDrawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
            vf_bg.addView(Viewgif);
        }
    }

    @Override
    public void InitData() {
        super.InitData();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    AppManager.getAppManager().finishActivity(aty_teamode_dispense.this);
                default:
                    break;
            }
        }
    };

    private int makeDrinkTime() {
        int totalTime = 0;
        try {
            List<BeverageIngredient> beverageIngredients = beverageFactoryDao.getBeverageIngerdient().queryforbeveragepid(curPid);
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
                            ingredientTime = (int) ((instant.getWaterVolume() * beverageIngredient.getScaleUp() / 100 + instant.getPreflushVolume() + instant.getWaterAfterFlushVolume()) * 100) + (instant.getPauseTimeAfterDispense() + instant.getPreflushPauseTime()) + 2000;
                        }
                        break;
                    case Ingredient.TYPE_WATER:
                        IngredientWater water = ingredientFactoryDao.getWaterDao().findByT(beverageIngredient.getIngredientPid());
                        if (water != null) {
                            ingredientTime = (int) (water.getWaterVolume() *100);
                        }
                        break;
                    case Ingredient.TYPE_ESPRESSO:
                        IngredientEspresso espresso = ingredientFactoryDao.getEspressoDao().findByT(beverageIngredient.getIngredientPid());
                        if(espresso!=null)
                        {
                            ingredientTime = (int) (espresso.getBrewtime()+espresso.getPrebrewtime()+espresso.getPreinfusiontime())*1000+10000+espresso.getWatervolume()*100+(int)espresso.getPowderamount()*100;
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
        EvoTrace.e("time", "makeDrinkTime:" + (totalTime) );
        return (totalTime + 4000) * 1;
    }
    private  void setTheNumberProgressBar()
    {
        final int totaltm =5000;// makeDrinkTime();
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
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanSendTimerTask();
        if(mMediaPlayer!=null)
        {
            if(mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            _surface =null;
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }

    @Override
    public void onClick(View view) {
    }
    private class PlayerVideo extends Thread{
        @Override
        public void run(){
            String res =FileHelper.PATH_DRINK_AM+"a1.mp4";// getApp().get_drinkMenuButton().getDispensepath();
            try {
                mMediaPlayer= new MediaPlayer();
                mMediaPlayer.setDataSource(aty_teamode_dispense.this, Uri.parse(res));
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
        new aty_teamode_dispense.PlayerVideo().start();
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
}
