package android.luna.Data.DAO;
import android.content.Context;
import android.luna.Activity.Base.CremApp;

public class BaseDaobak  {
	private Context mContext;

	public CremApp getApp() {
		return app;
	}

	private CremApp app;
	public Context getmContext() {
		return mContext;
	}

	public BaseDaobak(Context context,CremApp app) {
		this.mContext = context;
		this.app=app;
	}
	
	public DataHelper getHelper() {
		return app.getHelper();
	}
	
}
