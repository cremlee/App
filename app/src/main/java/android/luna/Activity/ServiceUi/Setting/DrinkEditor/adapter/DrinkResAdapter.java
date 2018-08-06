package android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.PicSelector.DrinkRes;
import android.luna.Utils.PictureManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.lid.lib.LabelImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/1.
 */

public class DrinkResAdapter extends BaseAdapter {
    public void setMlistdata(List<DrinkRes> mlistdata) {
        viewMap.clear();
        this.mlistdata = mlistdata;
        notifyDataSetChanged();
    }

    private List<DrinkRes> mlistdata =new ArrayList<>();
    private Context mcontext;
    @Override
    public int getCount() {
        return mlistdata==null?0:mlistdata.size();
    }

    public void setpicSelectStatus(int position)
    {
        for(DrinkRes item:mlistdata)
        {
            item.setSelected(false);
        }
        mlistdata.get(position).setSelected(true);
    }
    public DrinkResAdapter(Context context , List<DrinkRes> data)
    {
        mcontext = context;
        mlistdata=data;
    }
    @Override
    public DrinkRes getItem(int i) {
        return mlistdata==null?null:mlistdata.get(i);
    }

    public  String getSelectPath()
    {
        for(DrinkRes item:mlistdata)
        {
            if(item.isSelected())
                return item.getResPath();
        }
        return "";
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
            view = LayoutInflater.from(mcontext).inflate(R.layout.adp_drink_res, null);
            GridView.LayoutParams lp = new GridView.LayoutParams(120, 120);
            view.setLayoutParams(lp);

            holder.selectPicture = view.findViewById(R.id.selectPicture);
            holder.picicon =view.findViewById(R.id.pic_icon);
            holder.picicon.setTag(getItem(i));
            /*holder.picicon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ClipData.Item item = new ClipData.Item((String) view.getTag());
                    ClipData data = new ClipData(view.getTag().toString(),
                            new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                            item);
                    view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                    return true;
                }
            });*/
            view.setTag(holder);
            viewMap.put(i, view);
        }
        else {
            view = viewMap.get(i);
            holder = (ViewHolder)view.getTag();

        }
        String path = getItem(i).getResPath();
        if(getItem(i).isSelected())
        {
            holder.selectPicture.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.selectPicture.setVisibility(View.GONE);
        }
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
                        bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
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
        ImageView selectPicture;
    }
}
