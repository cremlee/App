package android.luna.Utils.Logger;

import android.luna.Utils.BuildConfig;
import android.util.Log;

/**
 * Created by Lee.li on 2018/7/17.
 */

public class EvoTrace {
    public final static void e(String tag, String msg, Throwable tr) {
        if (BuildConfig.isDebug)
            Log.e(tag, msg, tr);
    }
    public final static void e(String tag, String msg) {
        if (BuildConfig.isDebug)
            Log.e(tag, msg);
    }

    public final static void e(String msg) {
        if (BuildConfig.isDebug)
            Log.e("", msg);
    }

    public final static void e(Throwable tr) {
        if (BuildConfig.isDebug)
            Log.e("", "", tr);
    }

    public final static void d(String tag, String msg) {
        if (BuildConfig.isDebug)
            Log.d(tag, msg);
    }

    public final static void d(String msg) {
        if (BuildConfig.isDebug)
            Log.d("", msg);
    }

    public final static void d(Throwable tr) {
        if (BuildConfig.isDebug)
            Log.d("", "", tr);
    }
}
