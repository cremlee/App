package android.luna.Activity.OtherUi;

import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.CustomerUI.Gallery.aty_customer_ui_12;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.Utils.PictureManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import evo.luna.android.R;
public class ScreenSaverActivity extends BaseActivity implements TextureView.SurfaceTextureListener{
	private static final String TAG = "ScreenSaverActivity";
	private ImageView screenSaverImage;
	private TextureView screenSavervideo;
	private MediaPlayer mMediaPlayer;
	private int imageIndex = 0;
	private String[] scrpicpathlst;
	private Timer timer;
	private String picpath;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		picpath = (getApp().get_screenSettings_instance().getScreensaverpath().equals(null)?"":getApp().get_screenSettings_instance().getScreensaverpath());
		scrpicpathlst = picpath.split(";");
		//// TODO: 2018/2/13 phase1.5  tianjia video de neirong zhege gengju saverzhong de wenjian geshi laijueding
		if(scrpicpathlst[0].endsWith(".mp4"))
		{
			//shiping
			setContentView(R.layout.activity_screen_saver_video);
			setupViewsVideo();
		}else
		{
			//tupian
			setContentView(R.layout.activity_screen_saver_pic);
			setupViewsPic();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
			timer.purge();
			timer = null;
		}
		if(mMediaPlayer!=null)
		{
			if(mMediaPlayer.isPlaying())
				mMediaPlayer.stop();
			_surface =null;
			mMediaPlayer.release();
			mMediaPlayer=null;
		}
		
	}
	private void setupViewsVideo()
	{
		screenSavervideo = findViewById(R.id.screenSavervideo);
		screenSavervideo.setSurfaceTextureListener(this);
		screenSavervideo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EvoTrace.e(TAG, "exit ScreenSaverActivity!");
				AppManager.getAppManager().finishActivity(ScreenSaverActivity.class);
			}
		});
	}
	private Surface _surface;
	private class PlayerVideo extends Thread{
		@Override
		public void run(){
			try {
				mMediaPlayer= new MediaPlayer();
				mMediaPlayer.setDataSource(ScreenSaverActivity.this, Uri.parse(scrpicpathlst[0]));
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

	private void setupViewsPic() {
		screenSaverImage =  findViewById(R.id.screenSaverImage);
		loadImage(scrpicpathlst[0]);
		showScreenSaverTimer();
		screenSaverImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				EvoTrace.e(TAG, "exit ScreenSaverActivity!");
				AppManager.getAppManager().finishActivity(ScreenSaverActivity.class);
			}
		});
	}

	private void loadImage(String path) {
		EvoTrace.e(TAG, "path:" + path);
		Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
		if(bitmap == null)
		{
			final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path,800,600);
			if(tmpbitmap!=null)
			{
			PictureManager.getInstance().addBitmapToMemoryCache(path,tmpbitmap);
			bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
			}
		}
		if(bitmap!=null)
			screenSaverImage.setImageBitmap(bitmap);
					
	}

	private void showScreenSaverTimer() {
		// 加载ScreenSaver文件夹下所有文件，按照设置的时间进行切换
		int intervalIndex = Integer.valueOf(getApp().get_screenSettings_instance().getScreensaverflag());
		int intervalTime = 10;
		switch (intervalIndex) {
		case 1://3 Seconds
			intervalTime = 3;
			break;
		case 2:// 5 Seconds
			intervalTime = 5;
			break;
		case 3:// 10 Seconds
			intervalTime = 10;
			break;
		case 4:// 15 Seconds
			intervalTime = 15;
			break;
		case 5:// 30 Seconds
			intervalTime = 30;
			break;
		case 6:// 60 Seconds
			intervalTime = 60;
			break;
		case 7:// 300 Seconds
			intervalTime = 300;
			break;

		default:
			break;
		}
       /* need to show the selected picture*/
		//final String[] filePaths = FileHelper.getDirAllFile(Constant.PATH_SCREEN_SAVER);
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (imageIndex >= scrpicpathlst.length) {
					imageIndex = 0;
				}
				Message msg = new Message();
				msg.what = 1000;
				msg.obj = scrpicpathlst[imageIndex];
				handler.sendMessage(msg);
				imageIndex++;
			}
		}, 0, intervalTime * 1000);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1000) {
				super.handleMessage(msg);
				loadImage(msg.obj.toString());
			}
		}
	};

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
		_surface = new Surface(surface);
		new ScreenSaverActivity.PlayerVideo().start();
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
}
