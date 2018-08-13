package android.luna.Activity.ProductLineUi.Fragment;

import android.graphics.SurfaceTexture;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.rs232.Cmd.CmdCleanMachine;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ViewFlipper;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class Fgt_production_dryclean extends productionFragment implements View.OnClickListener,TextureView.SurfaceTextureListener {
    private Button next,step_1,step_2,step_3,step_4;
    private MaterialDialog progressDialog;
    private MediaPlayer mMediaPlayer;
    private Surface _surface;
    private ViewFlipper op_vf;
    private Fg_step_1 fgstep1;
    private Fg_step_2 fgstep2;
    private Fg_step_3 fgstep3;
    private Fg_step_4 fgstep4;
    private void Playhelp(int index)
    {
        String path = FileHelper.FILE_PRODUCTION+"step"+index+".mp4";
        if(mMediaPlayer == null)
        {
           mMediaPlayer= new MediaPlayer();
            mMediaPlayer.setSurface(_surface);
        }else {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.release();
            mMediaPlayer = null;
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setSurface(_surface);
        }
        try {
            mMediaPlayer.setDataSource(getActivity(), Uri.parse(path));
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

    private class PlayerVideo extends Thread{
        @Override
        public void run(){
            try {
                mMediaPlayer= new MediaPlayer();
                mMediaPlayer.setDataSource(getActivity(), Uri.parse(""));
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
        step_1.setVisibility(View.VISIBLE);
        step_2.setVisibility(View.VISIBLE);
        step_3.setVisibility(View.VISIBLE);
        step_4.setVisibility(View.VISIBLE);
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

    public  interface nextListerner
    {
        void Clicked(int a);
    }
    private nextListerner _nextListerner;
    public void setOnnextListerner(nextListerner a)
    {
        _nextListerner =a;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_production_dryclean, container, false);
        InitView(view);
        return view;
    }
    private View viewVideo;
    private TextureView step_video;
    @Override
    public void InitView(View view) {

        step_1 = view.findViewById(R.id.step_1);
        step_2 = view.findViewById(R.id.step_2);
        step_3 = view.findViewById(R.id.step_3);
        step_4 = view.findViewById(R.id.step_4);

        step_1.setVisibility(View.INVISIBLE);
        step_2.setVisibility(View.INVISIBLE);
        step_3.setVisibility(View.INVISIBLE);
        step_4.setVisibility(View.INVISIBLE);
        step_1.setOnClickListener(this);
        step_2.setOnClickListener(this);
        step_3.setOnClickListener(this);
        step_4.setOnClickListener(this);

        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);
        op_vf = view.findViewById(R.id.op_vf);
        viewVideo = getActivity().getLayoutInflater().inflate(R.layout.lyt_step_texture, null);
        step_video = viewVideo.findViewById(R.id.textureView);
        op_vf.addView(viewVideo);
        step_video.setSurfaceTextureListener(this);
        try {
           String cmd = new CmdCleanMachine().buildCmdStart(DeviceXmlFactory.getCleanComponent(FileHelper.FILE_DEVICE_CONFIG,DeviceXmlFactory.CLEAN_TYPE_DRY_OPEN));
        }
        catch (Exception e){}
    }

    private void showuploadwindow()
    {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Upload config")
                .canceledOnTouchOutside(false)
                .content("uploading...")
                .progress(true, 0)
                .show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next:
                if(_nextListerner!=null)
                    _nextListerner.Clicked(5);
                break;
            case R.id.step_1:
                if(fgstep1 == null)
                {
                    fgstep1 = new Fg_step_1();
                }
                getChildFragmentManager().beginTransaction().replace(R.id.op_fun, fgstep1).commit();
                Playhelp(1);
                break;
            case R.id.step_2:
                if(fgstep2 == null)
                {
                    fgstep2 = new Fg_step_2();
                }
                getChildFragmentManager().beginTransaction().replace(R.id.op_fun, fgstep2).commit();
                Playhelp(2);
                break;
            case R.id.step_3:
                if(fgstep3 == null)
                {
                    fgstep3 = new Fg_step_3();
                }
                getChildFragmentManager().beginTransaction().replace(R.id.op_fun, fgstep3).commit();
                Playhelp(3);
                break;
            case R.id.step_4:
                if(fgstep4 == null)
                {
                    fgstep4 = new Fg_step_4();
                }
                getChildFragmentManager().beginTransaction().replace(R.id.op_fun, fgstep4).commit();
                Playhelp(4);
                break;
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if(progressDialog!=null)
                    {
                        progressDialog.dismiss();
                    }
                    next.setVisibility(View.VISIBLE);
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mMediaPlayer!=null)
        {
            if(mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            _surface =null;
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }
}
