package android.luna.Activity.ServiceUi.Setting.Clean.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.Utils.ImageConvertFactory;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.ViewUi.CleanProcessView.CleanProcessView;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.rs232.Cmd.CmdCleanMachine;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class FreeCleanFragment extends Fragment implements View.OnClickListener ,TextureView.SurfaceTextureListener {
    private TextView title;
    private Button btnaction;
    private TextView content;
    private MaterialDialog progressDialog;
    private IntentFilter filter;
    private CremApp app;
    private  int stepcount =4;
    private int currentcleanstep;
    private CleanProcessView clean_index;
    private MediaPlayer mMediaPlayer;
    private Surface _surface;
    private ViewFlipper icon;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean_step_base, container, false);
        InitView(view);
        app = ((BaseActivity)getActivity()).getApp();
        return view;
    }
    private View viewVideo,viewImage;
    private TextureView step_video;
    private ImageView step_pic;
    public void InitView(View view)
    {
        clean_index = view.findViewById(R.id.clean_index);
        clean_index.setTotalcount(5);
        clean_index.activeSite(0,0);
        title = view.findViewById(R.id.title);
        icon = view.findViewById(R.id.icon);
        viewVideo = getActivity().getLayoutInflater().inflate(R.layout.lyt_step_texture, null);
        step_video = viewVideo.findViewById(R.id.textureView);
        icon.addView(viewVideo);
        step_video.setSurfaceTextureListener(this);
        viewImage = getActivity().getLayoutInflater().inflate(R.layout.lyt_step_picture, null);
        step_pic = viewImage.findViewById(R.id.step_pic);
        icon.addView(viewImage);

        content = view.findViewById(R.id.content);
        btnaction = view.findViewById(R.id.action);
        btnaction.setOnClickListener(this);
        icon.setDisplayedChild(0);

        currentcleanstep =0;
    }


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

    @Override
    public void onResume() {
        super.onResume();
        filter = new IntentFilter();
        filter.addAction(Constant.ACTION_ACK_CLEAN_REQUEST);
        filter.addAction(Constant.ACTION_CLEAN_MACHINE_FINISH);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        getActivity().registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int ackresult;
            if(action.equals(Constant.ACTION_ACK_CLEAN_REQUEST))
            {
                // TODO: 2018/7/13 diaoqi dialog ,block ui
                 ackresult = intent.getIntExtra("ACK",2);
                if (ackresult ==1)
                {
                    showCleanDialog();
                }else
                {
                    ((BaseActivity)getActivity()).showToastLong("Clean failed");
                }
            }
            else if(action.equals(Constant.ACTION_CMD_RSP_TIME_OUT))
            {
                // TODO: 2018/7/13 if ui block ,then release it otherwise show failed
                if(progressDialog!=null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
                setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB\n3.CCCCCCCCCCCCC");
                setactiontitle("Start");
                setIcon(2);
                currentcleanstep =2;
                btnaction.setVisibility(View.VISIBLE);
                ((BaseActivity)getActivity()).showToastLong("Clean Cmd Timeout!");
            }
            else if(action.equals(Constant.ACTION_CLEAN_MACHINE_FINISH)) {
               // TODO: 2018/7/13  release ui
                if(progressDialog!=null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                    setIcon(3);
                    currentcleanstep =3;
                    setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB");
                    btnaction.setVisibility(View.VISIBLE);
                    setactiontitle("Next");
                }

            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }


    private void showCleanDialog()
    {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Machine clean in process")
                .canceledOnTouchOutside(true)
                .content("waiting...")
                .progress(true, 0)
                .show();
    }

    public void setTitle(String a)
    {
        title.setText(a);
    }

    public void setContent(String a)
    {
        content.setText(a);
    }

    public void setIcon(Bitmap rsid)
    {
        step_pic.setImageBitmap(rsid);
        icon.setDisplayedChild(1);
    }

    public void setIcon(int id)
    {
        Playhelp(id);
        icon.setDisplayedChild(0);
    }
    public void setactiontitle(String a)
    {
        btnaction.setText(a);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null)
            progressDialog.dismiss();
        if(mMediaPlayer!=null)
        {
            if(mMediaPlayer.isPlaying())
                mMediaPlayer.stop();
            _surface =null;
            mMediaPlayer.release();
            mMediaPlayer=null;
        }
    }

    private void dealwithCleanUi(int step)
    {
        switch (step)
        {
            case 0:
                setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB");
                setactiontitle("Next");
                setIcon(1);
                currentcleanstep =1;
                break;
            case 1:
                setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB\n3.CCCCCCCCCCCCC");
                setactiontitle("Start");
                setIcon(2);
                currentcleanstep =2;
                break;
            case 2:
                String  cmd="";
                try {cmd = new CmdCleanMachine().buildCmdStart(DeviceXmlFactory.getCleanComponent(FileHelper.FILE_DEVICE_CONFIG,DeviceXmlFactory.CLEAN_TYPE_DAILY));
                }
                catch (Exception e){}
                if(!cmd.equals(""))
                    ((BaseActivity)getActivity()).getApp().addCmdQueue(cmd);
                setContent("Waiting....");
                btnaction.setVisibility(View.GONE);
                currentcleanstep =21;
                break;
            case 3:
                setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB");
                setactiontitle("Next");
                setIcon(3);
                currentcleanstep =4;
                break;
            case 4:
                setContent("Clean finished");
                setIcon(4);
                setactiontitle("Done");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.action:
                dealwithCleanUi(currentcleanstep);
                break;
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        _surface = new Surface(surface);
        btnaction.setVisibility(View.VISIBLE);
        setTitle("Machine cleanning");
        setContent("Please follow the guide to finish the cleanning \n 1.Press the Start button.");
        setactiontitle("Start");
        setIcon(ImageConvertFactory.getfrompath(FileHelper.PATH_CLEAN+"1.png"));

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
        EvoTrace.e("video","onSurfaceTextureUpdated");
    }
}
