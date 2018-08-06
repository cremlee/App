package android.luna.Activity.ServiceUi.Setting.ScreenEditor.Adapter;

import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.IngredientItems;
import android.luna.Data.module.BeverageUi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/24.
 */

public class DrinkSelectAdapter extends RecyclerView.Adapter<DrinkSelectAdapter.IngredientVH>{

    public interface ItemCallback {
        void onItemClicked(int itemIndex);
    }
    private ItemCallback itemCallback;
   public void setCallbacks(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }

    public void setItems(List<BeverageUi> items) {
        this.items = items;
    }

    private List<BeverageUi> items =null;
    public DrinkSelectAdapter(List<BeverageUi> items)
    {
        this.items = items;
    }

   public int GetItemPid(int pos)
   {
       return  items.get(pos).getPid();
   }

    @Override
    public IngredientVH onCreateViewHolder(ViewGroup parent, int viewType) {

        final View view =LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.adp_dialog_customlistitem, parent, false);
        return new IngredientVH(view, this);
    }

    @Override
    public void onBindViewHolder(IngredientVH holder, int position) {
        holder.lb_name.setText(items.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return items ==null?0:items.size();
    }

    static class IngredientVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        final DrinkSelectAdapter adapter;
        final TextView lb_name;
        public IngredientVH(View itemView,DrinkSelectAdapter adapter) {
            super(itemView);
            lb_name = itemView.findViewById(R.id.lb_name);
            this.adapter = adapter;
            lb_name.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (adapter.itemCallback != null) {
                adapter.itemCallback.onItemClicked(getAdapterPosition());
            }
        }
    }
}
