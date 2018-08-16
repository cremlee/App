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

    public static Bitmap getsavefrompath(String path,int width,int height)
    {
        if(path!=null && !path.equals("")) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, width, height);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            return bitmap;
        }
        return null;
    }
}
