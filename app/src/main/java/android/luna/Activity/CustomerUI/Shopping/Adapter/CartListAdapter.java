package android.luna.Activity.CustomerUI.Shopping.Adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Utils.PictureManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.snappingstepper.SnappingStepper;
import com.bigkoo.snappingstepper.listener.SnappingStepperValueChangeListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/3/21.
 */

public class CartListAdapter extends BaseAdapter {


    public  interface  OnItemCountChanged
    {
        void itemschanged(int pid,int cnt);
    }

    private OnItemCountChanged _OnItemCountChanged;
    public void SetOnItemCountChanged(OnItemCountChanged a)
    {
        _OnItemCountChanged = a;
    }

    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    private Context mcontext;
    private List<CartItem> _data = new ArrayList<>(20);

    private int  get_data_size()
    {
        int ret =0;
        if(_data == null )
            return 0;
        for (CartItem item: _data)
        {
            if(item.getCups()>0)
                ret++;
        }
        return  ret;
    }
    public CartListAdapter(Context constant,List<CartItem> data)
    {
        mcontext = constant;
        _data =data;
        Collections.sort(_data);
    }
    @Override
    public int getCount() {
        return get_data_size();
    }

    @Override
    public Object getItem(int position) {
        return _data ==null?null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.adapter_item_list, null);
            holder.drinkicon =  convertView.findViewById(R.id.item_icon);
            holder.drinkname = convertView.findViewById(R.id.item_name);
            holder.drinkprice = convertView.findViewById(R.id.item_price);
            holder.item_tol_price = convertView.findViewById(R.id.item_tol_price);
            holder.item_spin = convertView.findViewById(R.id.item_spin);
            holder.item_remove = convertView.findViewById(R.id.item_remove);
            convertView.setTag(holder);
            viewMap.put(position, convertView);
        } else {
            convertView = viewMap.get(position);
            holder = (ViewHolder) convertView.getTag();
        }
        final CartItem item = _data.get(position);
        if(item!=null && item.getCups()>0)
        {
            holder.drinkprice.setText(item.getDrinkMenuButton().getPrice() +" / cup");
            holder.item_spin.setValue(item.getCups());
            holder.item_tol_price.setText(item.getCups()*item.getDrinkMenuButton().getPrice()+"");
            holder.drinkname.setText(item.getDrinkMenuButton().getName());
            String path = item.getDrinkMenuButton().getIconpath();//== null?"":drinkMenuButtonList.get(position).getIconpath();
            if(path!=null) {
                Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                if (bitmap == null) {
                    final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 240, 240);
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

            final ViewHolder finalHolder = holder;
            holder.item_spin.setOnValueChangeListener(new SnappingStepperValueChangeListener() {
                @Override
                public void onValueChange(View view, int value) {
                    item.setCups(value);
                    finalHolder.item_tol_price.setText(item.getCups()*item.getDrinkMenuButton().getPrice()+"");
                    if(_OnItemCountChanged!=null)
                    {
                        _OnItemCountChanged.itemschanged(item.getDrinkMenuButton().getPid(),value);
                    }
                }
            });

            holder.item_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _data.remove(item);
                    if(_OnItemCountChanged!=null)
                    {
                        _OnItemCountChanged.itemschanged(item.getDrinkMenuButton().getPid(),0);
                    }
                    notifyDataSetChanged();
                }
            });

        }
        return convertView;
    }

    class ViewHolder {
        ImageView drinkicon;
        TextView drinkname;
        TextView drinkprice;
        TextView item_tol_price;
        SnappingStepper item_spin;
        Button item_remove;
    }
}
