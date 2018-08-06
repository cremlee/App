package android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.model.DrinkBean;
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

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {
    protected Context mContext;
    protected List<DrinkBean> mDatas;
    protected LayoutInflater mInflater;
    private List<Boolean> isClicks;
    private OndrinkitemClicked m_OndrinkitemClicked=null;
    public interface OndrinkitemClicked
    {
        void OnitemClick(int position);
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

    public void SetdrinkitemOnClicked(OndrinkitemClicked a)
    {
        m_OndrinkitemClicked =a;
    }
    public DrinkAdapter(Context mContext, List<DrinkBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        isClicks = new ArrayList<>();
        for(int i = 0;i<mDatas.size();i++){
            isClicks.add(false);
        }
        mInflater = LayoutInflater.from(mContext);
    }

    public List<DrinkBean> getDatas() {
        return mDatas;
    }

    public DrinkAdapter setDatas(List<DrinkBean> datas) {
        mDatas = datas;
        isClicks.clear();
        for(int i = 0;i<mDatas.size();i++){
            isClicks.add(false);
        }
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_drink_swipe, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final DrinkBean cityBean = mDatas.get(position);
        holder.tvCity.setText(cityBean.getDrinkname());
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
                if(m_OndrinkitemClicked!=null)
                {
                    m_OndrinkitemClicked.OnitemClick(position);
                }
            }
        });
        if(cityBean.getIconpath().equals("")) {
            if(cityBean.getPid()==999)
            {
                holder.avatar.setImageResource(ic_input_add);
                holder.btnDel.setVisibility(View.GONE);
            }else {
                if (cityBean.isCandelete()) {
                    holder.avatar.setImageResource(R.drawable.ic_lock_open_black_48dp);
                    holder.btnDel.setVisibility(View.VISIBLE);
                }
                else {
                    holder.avatar.setImageResource(R.drawable.ic_lock_black_48dp);
                    holder.btnDel.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCity;
        ImageView avatar;
        View content;
        Button btnDel;

        public ViewHolder(View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tvCity);
            avatar = itemView.findViewById(R.id.ivAvatar);
            content = itemView.findViewById(R.id.content);
            btnDel = itemView.findViewById(R.id.btnDel);
        }
    }
}
