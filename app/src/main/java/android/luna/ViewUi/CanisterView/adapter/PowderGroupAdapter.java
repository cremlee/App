package android.luna.ViewUi.CanisterView.adapter;

import android.content.Context;
import android.luna.Data.module.Powder.PowderItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/11.
 */

public class PowderGroupAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<PowderItem> _data;
    private Context context;
    public PowderGroupAdapter(Context context, List<PowderItem>  data)
    {
        this.context =context;
        this._data =data;
    }
    public int getIndex(int type)
    {
        if(_data== null)
            return 0;
        for (int i=0;i<_data.size();i++)
        {
            if(type == _data.get(i).getPid())
            {
                return  i;
            }
        }
        return 0;
    }
    @Override
    public int getCount() {
        return _data == null?0:_data.size();
    }

    @Override
    public PowderItem getItem(int position) {
        return _data == null?null:_data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return _data.size()>0?_data.get(position).getPid():0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.adp_simple_spinner, null);
            viewHolder.textView = convertView.findViewById(R.id.text1);
        convertView.setTag(viewHolder);
        } else {
        viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(getItem(position).getName());
        return  convertView;
        }

        public static class ViewHolder {
            public TextView textView;
        }
}
