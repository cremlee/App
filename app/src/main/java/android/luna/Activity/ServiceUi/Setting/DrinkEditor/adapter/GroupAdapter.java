package android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter;

import android.content.Context;
import android.luna.Activity.Base.Constant;
import android.luna.ViewUi.widget.MySpinerAdapter;
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

public class GroupAdapter  extends BaseAdapter implements SpinnerAdapter {
    private HashMap<Integer,String> _data;
    //private List<>
    private Context context;
    public  GroupAdapter(Context context,HashMap<Integer,String>  data)
    {
        this.context =context;
        this._data =data;
    }
    @Override
    public int getCount() {
        return _data == null?0:_data.size();
    }

    @Override
    public String getItem(int position) {
        if(_data==null)
            return "";
        Iterator<Map.Entry<Integer, String>> iterator = _data.entrySet().iterator();
        String value = "";
        int i=0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry =  iterator.next();
            if(i == position){
                value = entry.getValue();
                break;
            }
            i++;
        }
        return value;
    }

    public int getgroupid(int position)
    {
        if(_data==null)
            return -1;
        Iterator<Map.Entry<Integer, String>> iterator = _data.entrySet().iterator();
        int value = -1;
        int i=0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry =  iterator.next();
            if(i == position){
                value = entry.getKey().intValue();
                break;
            }
            i++;
        }
        return value;
    }
    @Override
    public long getItemId(int position) {
        return position;
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
        viewHolder.textView.setText(getItem(position));
        return  convertView;
        }

        public static class ViewHolder {
            public TextView textView;
        }
}
