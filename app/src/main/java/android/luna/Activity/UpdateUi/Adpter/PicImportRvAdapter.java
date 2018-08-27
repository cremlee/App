package android.luna.Activity.UpdateUi.Adpter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Utils.ImageConvertFactory;
import android.luna.Utils.PictureManager;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.lid.lib.LabelImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/1.
 */

public class PicImportRvAdapter extends RecyclerView.Adapter<PicImportRvAdapter.ViewHolder> {
    public void setMlistdata(List<String> mlistdata) {
        viewMap.clear();
        this.mlistdata = mlistdata;
        notifyDataSetChanged();
    }
    private List<String> mlistdata;
    private Context mcontext;
    protected LayoutInflater mInflater;
    public PicImportRvAdapter(Context context , List<String> data)
    {
        mcontext = context;
        mlistdata=data;
        mInflater =LayoutInflater.from(mcontext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = mInflater.inflate(R.layout.adp_importpic, parent, false);
        GridView.LayoutParams lp = new GridView.LayoutParams(120, 120);
        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String path = mlistdata.get(position);
        holder.setdata(path);
        holder.picicon.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData.Item item = new ClipData.Item(path);
                ClipData data = new ClipData(path,
                        new String[] { ClipDescription.MIMETYPE_TEXT_PLAIN },
                        item);
                view.startDrag(data, new View.DragShadowBuilder(view), null, 0);
                return true;
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return mlistdata==null?0:mlistdata.size();
    }


    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        LabelImageView picicon;
        public ViewHolder(View itemView) {
            super(itemView);
            picicon =itemView.findViewById(R.id.pic_icon);

        }
        private void setdata(String path)
        {
            if(!path.endsWith("mp4")) {
                picicon.setImageBitmap(ImageConvertFactory.getfrompath(path, 100, 100));
            }else
            {
                picicon.setImageBitmap(PictureManager.getInstance().getVidioBitmap(path,100,100, MediaStore.Images.Thumbnails.MICRO_KIND));
            }
            picicon.setLabelText(path.substring(path.lastIndexOf(".") + 1));
        }
    }
}
