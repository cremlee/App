package android.luna.Activity.CustomerUI.Loading;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Path;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;

import android.luna.Activity.CustomerUI.Gallery.aty_theme_gallery;
import android.luna.Activity.CustomerUI.Normal.aty_theme_normal;
import android.luna.Activity.CustomerUI.Shopping.aty_theme_shop;
import android.luna.Activity.CustomerUI.ThreeDCloud.aty_theme_3d;
import android.luna.Utils.XmlPathParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import com.mcxtzhang.pathanimlib.PathAnimView;
import com.mcxtzhang.pathanimlib.utils.SvgPathParser;

import java.text.ParseException;

import evo.luna.android.R;
import me.wangyuwei.particleview.ParticleView;

/**
 * Created by Lee.li on 2018/1/16.
 */

public class aty_loading extends BaseActivity {
    private ParticleView pv;
    PathAnimView pathAnimView1;
    private String parsePath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            parsePath = XmlPathParser.getsvgPath(this.getAssets().open("logo.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);

        getApp().bindAllService();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pathAnimView1.clearAnim();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.activity_loading);
        pv = findViewById(R.id.pv);
        pathAnimView1 = findViewById(R.id.storeView3);

        pathAnimView1.setVisibility(View.INVISIBLE);
    }

    @Override
    public void InitData() {
        super.InitData();

    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    pathAnimView1.stopAnim();
                    switch (getApp().get_screenSettings_instance().getThemetype())
                    {
                        case 1:
                            startActivity(new Intent(aty_loading.this, aty_theme_normal.class));
                            break;
                        case 2:
                            startActivity(new Intent(aty_loading.this, aty_theme_gallery.class));
                            break;
                        case 3:
                            startActivity(new Intent(aty_loading.this, aty_theme_3d.class));
                            break;
                        case 4:
                            startActivity(new Intent(aty_loading.this, aty_theme_shop.class));
                            break;
                    }
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                    AppManager.getAppManager().finishActivity(aty_loading.this);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void InitEvent() {
        super.InitEvent();
        pv.setOnParticleAnimListener(new ParticleView.ParticleAnimListener(){
            @Override
            public void onAnimationEnd() {
                SvgPathParser svgPathParser = new SvgPathParser();
                try {
                    Path path = svgPathParser.parsePath(parsePath);
                    pathAnimView1.setSourcePath(path);
                    pathAnimView1.setAnimTime(10000);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                pathAnimView1.startAnim();
                pathAnimView1.setVisibility(View.VISIBLE);
                mHandler.sendEmptyMessageDelayed(1000,8000);

            }
        });
        pv.postDelayed(new Runnable() {
            @Override
            public void run() {
                pv.startAnim();
            }
        },1500);
    }

}
