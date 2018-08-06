package android.luna.ViewUi.OfficeUi.Adapter;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Data.module.IconDateBean;
import android.luna.Utils.PictureManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/16.
 */

public class RecyclerViewGridAdapter extends RecyclerView.Adapter<RecyclerViewGridAdapter.GridViewHolder>  {
    private Context mContext;
    private List<IconDateBean> _data;


    public RecyclerViewGridAdapter(Context context, List<IconDateBean> dateBeen) {
        mContext = context;
        _data = dateBeen;
    }

    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(mContext, R.layout.grid_item, null);
        //创建一个ViewHodler对象
        GridViewHolder gridViewHolder = new GridViewHolder(itemView);
        //把ViewHolder传出去
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {
        //从集合里拿对应的item的数据对象
        IconDateBean dateBean = _data.get(position);
        //给Holder里面的控件对象设置数据
        holder.mImageView.setTag(dateBean.getPath());
        holder.setData(dateBean);
    }

    @Override
    public int getItemCount() {
        if (_data != null && _data.size() > 0) {
            return _data.size();
        }
        return 0;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;
        private final TextView mTextView;
        public GridViewHolder(View itemView) {
            super(itemView);
            mImageView =  itemView.findViewById(R.id.item_list_icon);
            mTextView =  itemView.findViewById(R.id.item_list_name);
            mImageView.setOnLongClickListener(new View.OnLongClickListener() {
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
        }
        public void setData(IconDateBean data) {
            if(data.getPath()!=null) {
                Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(data.getPath());
                if (bitmap == null) {
                    final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(data.getPath(), 80, 80);
                    if (tmpbitmap != null) {
                        PictureManager.getInstance().addBitmapToMemoryCache(data.getPath(), tmpbitmap);
                        bitmap = PictureManager.getInstance().getBitmapFromMemCache(data.getPath());
                    }
                }
                if (bitmap != null)
                    mImageView.setImageBitmap(bitmap);
                else
                    mImageView.setImageResource(R.mipmap.ic_person_default);
            }
            mTextView.setText(data.getName());
        }
    }
}
