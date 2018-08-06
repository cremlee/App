package android.luna.ViewUi.OfficeUi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.model.DrinkBean;
import android.luna.Data.module.PersonItem;
import android.luna.Utils.PictureManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

import static android.R.drawable.ic_input_add;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    protected Context mContext;
    protected List<PersonItem> mDatas;
    protected LayoutInflater mInflater;
    private List<Boolean> isClicks;
    public interface OnPersonItemClicked
    {
        void OnitemClick(int position);
    }
    private  OnPersonItemClicked onPersonItemClicked=null;

    public void setOnPersonItemClicked(OnPersonItemClicked a)
    {
        this.onPersonItemClicked = a;
    }
    public void rollback(int position)
    {
        for(int i = 0; i <isClicks.size();i++){
            isClicks.set(i,false);
        }
        if(position!=0)
        isClicks.set(position,true);
        notifyDataSetChanged();
    }
    public PersonAdapter(Context mContext, List<PersonItem> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        isClicks = new ArrayList<>();
        if(mDatas!=null) {
            for (int i = 0; i < mDatas.size(); i++) {
                isClicks.add(false);
            }
        }
        mInflater = LayoutInflater.from(mContext);
    }

    public List<PersonItem> getDatas() {
        return mDatas;
    }

    public PersonAdapter setDatas(List<PersonItem> datas) {
        mDatas = datas;
        isClicks.clear();
        if(mDatas!=null) {
            for (int i = 0; i < mDatas.size(); i++) {
                isClicks.add(false);
            }
        }
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_item_person, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PersonItem personItem = mDatas.get(position);
        if(isClicks.size()>0) {
            if (isClicks.get(position)) {
                holder.content.setBackgroundColor(Color.MAGENTA);
            } else {
                holder.content.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i <isClicks.size();i++){
                    isClicks.set(i,false);
                }
                isClicks.set(position,true);
                notifyDataSetChanged();
                if(onPersonItemClicked!=null)
                {
                    onPersonItemClicked.OnitemClick(position);
                }
            }
        });
        if(personItem.isTop() && position ==0)
        {
            holder.name.setText("Add new One");
            holder.icon.setImageResource(R.mipmap.ic_add);
        }else {
            holder.name.setText(personItem.getName());
            if (personItem.getIconpath() != null && !personItem.equals("")) {
                Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(personItem.getIconpath());
                if (bitmap == null) {
                    final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(personItem.getIconpath(), 80, 80);
                    if (tmpbitmap != null) {
                        PictureManager.getInstance().addBitmapToMemoryCache(personItem.getIconpath(), tmpbitmap);
                        bitmap = PictureManager.getInstance().getBitmapFromMemCache(personItem.getIconpath());
                    }
                }
                if (bitmap != null)
                    holder.icon.setImageBitmap(bitmap);
                else
                    holder.icon.setImageResource(R.mipmap.ic_person_default);
            }
            else
            {
                holder.icon.setImageResource(R.mipmap.ic_person_default);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        View content;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
        }
    }
}
