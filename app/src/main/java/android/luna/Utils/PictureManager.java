package android.luna.Utils;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.media.ThumbnailUtils;
import android.support.v4.util.LruCache;
import android.util.Log;

import static android.R.attr.path;


public  class PictureManager {
	private static PictureManager _PictureManager=null;
	private static  LruCache<String, Bitmap> mMemoryCache; 
	public static PictureManager getInstance()
	{
		if(_PictureManager==null)
		{
			_PictureManager = new PictureManager();
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024); 
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {  
			        @Override  
			        protected int sizeOf(String key, Bitmap bitmap) {
			        	return bitmap.getByteCount()/1024; 
			        }  
			        @Override
					protected void entryRemoved(boolean evicted, String key,Bitmap oldValue, Bitmap newValue) {
								        	
					}

			   };  
		}
		return _PictureManager;
	}
	
	public void clearLruCache()
	{
		if (mMemoryCache != null) {
		 if (mMemoryCache.size() > 0) {
			 mMemoryCache.evictAll();
			}
		 mMemoryCache = null;
		 _PictureManager =null;
		}

	}
	public  void addBitmapToMemoryCache(String key, Bitmap bitmap) {  
		    if (getBitmapFromMemCache(key) == null ) {  
		    	 if (key != null && bitmap != null)
		    	 {
		    		 mMemoryCache.put(key, bitmap);  
		    	 }
		        
		    }  
		    
		}
	public synchronized  Bitmap getBitmapFromMemCache(String key) {
		if(key!=null)
		{
		     return mMemoryCache.get(key);  
		}
		return null;
		}
	
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// 源图片的高度和宽度
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// 计算出实际宽高和目标宽高的比率
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
			// 一定都会大于等于目标的宽和高。
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	public static Bitmap decodeSampledBitmapFromResource(String Path,
	        int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true; 
	    //options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    BitmapFactory.decodeFile(Path,options);
	    // 调用上面定义的方法计算inSampleSize值
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    // 使用获取到的inSampleSize值再次解析图片 
	    options.inJustDecodeBounds = false;
	    try{
	    	Bitmap a =BitmapFactory.decodeFile(Path, options);
	    	return a;
	    }
	    catch(OutOfMemoryError  e)
	    {
	    	System.gc();
	    	return null;
	    }
	    
	}
	
	public static Bitmap decodeSampledBitmapFromResource(String Path, 
	        int reqWidth, int reqHeight ,boolean flagfromdispen) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true; 
	    //options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    BitmapFactory.decodeFile(Path,options);
	    reqWidth =(options.outWidth <1024 ?options.outWidth:reqWidth);
	    reqHeight =(options.outHeight <768 ?options.outHeight:reqHeight);
	    // 调用上面定义的方法计算inSampleSize值
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    // 使用获取到的inSampleSize值再次解析图片 
	    options.inJustDecodeBounds = false;
	    try{
	    	Bitmap a =BitmapFactory.decodeFile(Path, options);
	    	return a;
	    }
	    catch(OutOfMemoryError  e)
	    {
	    	System.gc();
	    	return null;
	    }
	}
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int id,
	        int reqWidth, int reqHeight) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res,id,options);
	    // 调用上面定义的方法计算inSampleSize值
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
	    // 使用获取到的inSampleSize值再次解析图片
	    options.inJustDecodeBounds = false;	    
	    try{
	    	Bitmap a =BitmapFactory.decodeResource(res,id,options);
	    	return a;
	    }
	    catch(OutOfMemoryError  e)
	    {
	    	System.gc();
	    	return null;
	    }
	}

	public Bitmap getVidioBitmap(String filePath, int width, int height,
								 int kind) {
		Bitmap bitmap = null;
		bitmap = ThumbnailUtils.createVideoThumbnail(filePath, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/*public Bitmap getPDFBitmap(String filePath, int width, int height) {

	}*/
	
}
