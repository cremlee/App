package android.luna.ViewUi.NutritionUi.adapter;

import android.content.Context;
import android.luna.Data.module.Powder.ContainItem;
import android.luna.ViewUi.NutritionUi.data.PowderBean;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/17.
 */

public class Containadapter extends BaseAdapter {
    private Context context;

    public void set_data(List<PowderBean.itembean> _data) {
        this._data = _data;
    }

    private List<PowderBean.itembean> _data;
    public Containadapter(Context context, List<PowderBean.itembean> _data)
    {
        this.context =context;
        this._data =_data;
    }
    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public PowderBean.itembean getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private HashMap<Integer, View> viewMap = new HashMap<>();
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int id = position;
        ViewHolder holder = null;
        if (!viewMap.containsKey(position) || viewMap.get(position) == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_simple_contain, null);
            holder.icon = convertView.findViewById(R.id.icon);
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
            viewMap.put(position, convertView);
        } else {
            convertView = viewMap.get(position);
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setImageResource(getItem(position).resid);
        holder.name.setText(getItem(position).name);
        return convertView;
    }
    class ViewHolder
    {
        ImageView icon;
        TextView name;
    }
}
