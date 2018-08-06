package android.luna.Activity.UpdateUi;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Utils.PictureManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lid.lib.LabelImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/1.
 */

public class PicImportAdapter extends BaseAdapter {
    public void setMlistdata(List<String> mlistdata) {
        viewMap.clear();
        this.mlistdata = mlistdata;
        notifyDataSetChanged();
    }

    private List<String> mlistdata =new ArrayList<>();
    private Context mcontext;
    @Override
    public int getCount() {
        return mlistdata==null?0:mlistdata.size();
    }

    public  PicImportAdapter(Context context ,List<String> data)
    {
        mcontext = context;
        mlistdata=data;
    }
    @Override
    public String getItem(int i) {
        return mlistdata==null?null:mlistdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder =null;
        if(!viewMap.containsKey(i) || viewMap.get(i) == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(mcontext).inflate(R.layout.adp_importpic, null);
            GridView.LayoutParams lp = new GridView.LayoutParams(120, 120);
            view.setLayoutParams(lp);

            holder.picicon =view.findViewById(R.id.pic_icon);
            holder.picicon.setTag(getItem(i));
            holder.picicon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ClipData.Item item = new ClipData.Item((String) view.getTag());
                    ClipData data = new ClipData(view.getTag().toString(),
                            new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                            item);
                    view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                    return true;
                }
            });
            view.setTag(holder);
            viewMap.put(i, view);
        }
        else {
            view = viewMap.get(i);
            holder = (ViewHolder)view.getTag();

        }
        String path = getItem(i);
        if(path!=null)
        {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if(bitmap == null)
            {
                Bitmap tmpbitmap =null;
                if(path.endsWith("mp4"))
                {
                    tmpbitmap = PictureManager.getInstance().getVidioBitmap(path,100,100, MediaStore.Images.Thumbnails.MICRO_KIND);
                }
                else
                {
                     tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 1024, 768,false);
                }
                    if (tmpbitmap != null) {
                        PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                        bitmap = tmpbitmap;//PictureManager.getInstance().getBitmapFromMemCache(path);
                    }
                }
                if (bitmap != null) {
                    holder.picicon.setImageBitmap(bitmap);
                    holder.picicon.setLabelText(path.substring(path.lastIndexOf(".") + 1));
                }
            }
        return  view;
    }
    class ViewHolder
    {
        LabelImageView picicon;
    }
}
