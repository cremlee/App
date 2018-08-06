package android.luna.Activity.ServiceUi.Setting.CanisterEditor.adapter;

import android.content.Context;
import android.luna.Activity.ServiceUi.Adapter.HelpAdapter;
import android.luna.Data.module.Powder.ContainItem;
import android.support.annotation.IdRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lid.lib.LabelImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/17.
 */

public class ContainAdapter extends BaseAdapter {
    private Context context;

    public void set_data(List<ContainItem> _data) {
        this._data = _data;
    }

    private List<ContainItem> _data;
    public interface OnContainStateChanged
    {
        void statechanged(int position,int statecode);
    }
    private OnContainStateChanged containStateChanged =null;
    public void setOnContainStateChanged(OnContainStateChanged a)
    {
        this.containStateChanged =a;
    }
    public  ContainAdapter(Context context,List<ContainItem> _data)
    {
        this.context =context;
        this._data =_data;
    }
    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public ContainItem getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_powder_contain, null);
            holder.icon = convertView.findViewById(R.id.icon);
            holder.name = convertView.findViewById(R.id.name);
            holder.a1 = convertView.findViewById(R.id.a1);
            holder.a2 = convertView.findViewById(R.id.a2);
            holder.a3 = convertView.findViewById(R.id.a3);
            holder.rgp = convertView.findViewById(R.id.rgp);
            holder.rgp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    switch (checkedId)
                    {
                        case R.id.a1:
                            if(containStateChanged!=null)
                            {
                                containStateChanged.statechanged(position,0);
                            }
                            break;
                        case R.id.a2:
                            if(containStateChanged!=null)
                            {
                                containStateChanged.statechanged(position,1);
                            }
                            break;
                        case R.id.a3:
                            if(containStateChanged!=null)
                            {
                                containStateChanged.statechanged(position,2);
                            }
                            break;
                    }
                }
            });
            convertView.setTag(holder);
            viewMap.put(position, convertView);
        } else {
            convertView = viewMap.get(position);
            holder = (ViewHolder) convertView.getTag();
        }
        holder.icon.setImageResource(getItem(position).iconresid);
        holder.name.setText(getItem(position).itemname);
        if (getItem(position).selectcode == 0)
            holder.a1.setChecked(true);
        else if (getItem(position).selectcode == 1)
            holder.a2.setChecked(true);
        else if(getItem(position).selectcode == 2)
            holder.a3.setChecked(true);
        return convertView;
    }
    class ViewHolder
    {
        ImageView icon;
        TextView name;
        RadioButton a1;
        RadioButton a2;
        RadioButton a3;
        RadioGroup rgp;
    }
}
