package android.luna.Activity.ProductLineUi.adapter;

import android.content.Context;
import android.luna.Data.module.Production.AutoTestItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class adapter_production_test extends BaseAdapter {
    private List<AutoTestItem> _data;
    private Context mcontext;
    public adapter_production_test(Context context,List<AutoTestItem> data)
    {
        mcontext =context;
        _data =data;
    }
    @Override
    public int getCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public AutoTestItem getItem(int position) {
        return _data==null?null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _data==null?0:_data.get(position).getDeviceid();
    }

    private HashMap<Integer, View> viewMap = new HashMap<>();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int id = position;
        ViewHolder holder =null;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.lv_device_item, null);
            holder.Index = convertView.findViewById(R.id.Index);
            holder.Name = convertView.findViewById(R.id.Name);
            holder.Mac = convertView.findViewById(R.id.Mac);
            holder.result = convertView.findViewById(R.id.result);
            convertView.setTag(holder);
            viewMap.put(position, convertView);
        }
        else {
            convertView = viewMap.get(position);
            holder = (ViewHolder)convertView.getTag();
        }
        holder.Index.setText((position+1)+"");
        holder.Name.setText(getItem(position).getName(mcontext));
        holder.Mac.setText(String.format("%08X",getItem(position).getDeviceid()));
        holder.result.setText(getItem(position).getResultString(mcontext));
        return  convertView;
    }
    class ViewHolder
    {
        TextView Name;
        TextView Index;
        TextView Mac;
        TextView result;
    }
}
