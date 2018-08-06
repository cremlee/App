package android.luna.Activity.ServiceUi.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.luna.Data.module.CanisterItemStock;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lid.lib.LabelImageView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/6.
 */

public class CanisterStockAdapter extends RecyclerView.Adapter<CanisterStockAdapter.ViewHolder> {
    private List<CanisterItemStock> _data;
    private Context _context;
    protected LayoutInflater mInflater;

    private static int LEVEL_LOW =10;
    private static int LEVEL_WARN=30;
    public CanisterStockAdapter(List<CanisterItemStock> data,Context context)
    {
        this._data =data;
        this._context = context;
        mInflater = LayoutInflater.from(_context);
    }

    public interface ItemRefillListener
    {
        void ItemClicked(CanisterItemStock a);
    }

    private ItemRefillListener ItemRefillListener=null;

    public void setOnItemRefillListener(ItemRefillListener a)
    {
        ItemRefillListener =a;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.cansiter_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CanisterItemStock itemStock = _data.get(position);
        if(itemStock.getGroup() == 2)
        holder.icon.setImageResource(R.mipmap.beanhopper);
        else
        holder.icon.setImageResource(R.mipmap.instanthopper);
        holder.type.setText(itemStock.PowderName);
        int level = (itemStock.getStock()*100/itemStock.getCapability());
        holder.show.setText(level+"%");
        holder.icon.setLabelDistance(30);
        holder.icon.setLabelHeight(30);
        if(level<=LEVEL_LOW)
        {
            holder.icon.setLabelText("refill");
            holder.show.setTextColor(Color.RED);
        }
        else if(level<=LEVEL_WARN)
        {
            holder.icon.setLabelText("low level");
            holder.show.setTextColor(Color.YELLOW);
        }
        else
        {
            holder.icon.setLabelText(null);
            holder.show.setTextColor(Color.GREEN);
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ItemRefillListener!=null)
                {
                    ItemRefillListener.ItemClicked(itemStock);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return _data == null ? 0:_data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LabelImageView icon;
        private TextView type;
        private TextView show;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            type = itemView.findViewById(R.id.type);
            show = itemView.findViewById(R.id.show);
        }
    }
}
