package android.luna.ViewUi.ViewPager;

import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Utils.PictureManager;
import evo.luna.android.R;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lid.lib.LabelImageView;

import java.util.List;

/**
 * Created by Lee.li on 2017/12/26.
 */

public class MyAdapter extends  RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    public void setDrinkMenuButtonList(List<DrinkMenuButton> drinkMenuButtonList) {
        this.drinkMenuButtonList = drinkMenuButtonList;
    }

    private List<DrinkMenuButton> drinkMenuButtonList;
    private Context context;
    private boolean fromcreate;
    private CremApp app;
    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }
    private OnItemClickListener mOnItemClickListener = null;
    public  MyAdapter(List<DrinkMenuButton> drinkMenuButtonList,Context context)
    {
        this.drinkMenuButtonList = drinkMenuButtonList;
        this.context = context;
        app = ((BaseActivity)context).getApp();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        fromcreate =true;
        View mview = LayoutInflater.from(context).inflate(R.layout.myviewpager,null);
        ViewHolder viewHolder = new ViewHolder(mview);
        viewHolder.tvname = mview.findViewById(R.id.drinkname);
        viewHolder.tvprice =mview.findViewById(R.id.drinkprice);
        viewHolder.drinkicon = mview.findViewById(R.id.drinkicon);
        viewHolder.drinkstate = mview.findViewById(R.id.drinkstate);
        return viewHolder;
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(drinkMenuButtonList.get(position).getDrinkstate()==0) {
            holder.itemView.setClickable(true);
            holder.itemView.setOnClickListener(this);
            holder.drinkstate.setVisibility(View.GONE);
        }
        else if(drinkMenuButtonList.get(position).getDrinkstate()==1){
            holder.drinkstate.setText(context.getString(R.string.MAIN_UI_OUT_OF_STOCK));
            holder.drinkstate.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }
        else if(drinkMenuButtonList.get(position).getDrinkstate()==2){
            holder.drinkstate.setText(context.getString(R.string.MAIN_UI_OUT_OF_USE));
            holder.drinkstate.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }
        else if(drinkMenuButtonList.get(position).getDrinkstate()==3){
            holder.drinkstate.setText(context.getString(R.string.MAIN_UI_OUT_OF_WASTER));
            holder.drinkstate.setVisibility(View.VISIBLE);
            holder.itemView.setClickable(false);
        }
        holder.tvname.setText(drinkMenuButtonList.get(position).getName());
        holder.tvname.setTextColor(app.get_screenSettings_instance().getTextcolor());
        holder.tvprice.setText(drinkMenuButtonList.get(position).getPrice()==0?"":Float.toString(drinkMenuButtonList.get(position).getPrice()));
        holder.drinkicon.setVisibility(drinkMenuButtonList.get(position).getName().equals("")?View.INVISIBLE:View.VISIBLE);
        String path = drinkMenuButtonList.get(position).getIconpath();
        if(path!=null) {
            if(path.equals(""))
            {
                holder.drinkicon.setBackgroundResource(R.drawable.ic_ics_cup);
            }else {
                Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                if (bitmap == null) {
                    final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 400, 400);
                    if (tmpbitmap != null) {
                        PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                        bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                    }
                }
                if (bitmap != null)
                    holder.drinkicon.setImageBitmap(bitmap);
                else
                    holder.drinkicon.setImageBitmap(null);
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

    }

    @Override
    public int getItemCount() {
        return this.drinkMenuButtonList==null?0:this.drinkMenuButtonList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }
        TextView tvname;
        LabelImageView drinkicon;
        TextView tvprice;
        TextView drinkstate;
    }
}
