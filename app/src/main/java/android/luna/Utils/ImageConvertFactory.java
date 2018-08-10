package android.luna.Utils;

import android.graphics.Bitmap;

/**
 * Created by Lee.li on 2018/8/8.
 */

public class ImageConvertFactory {
    public static Bitmap getfrompath(String path)
    {
        Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
        if (bitmap == null) {
            final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 200, 200);
            if (tmpbitmap != null) {
                bitmap = tmpbitmap;
            }
        }
        return bitmap;
    }
}
