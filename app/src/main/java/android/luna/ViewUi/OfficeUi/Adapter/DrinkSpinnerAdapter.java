package android.luna.ViewUi.OfficeUi.Adapter;

import android.content.Context;
import android.luna.Data.CustomerUI.DrinkMenuButton;
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

public class DrinkSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
    private List<DrinkMenuButton> _data;
    //private List<>
    private Context context;

    public void setSelectposition(int selectposition) {
        this.selectposition = selectposition;
    }

    private int selectposition =-1;
    public DrinkSpinnerAdapter(Context context, List<DrinkMenuButton>  data)
    {
        this.context =context;
        this._data =data;
    }
    @Override
    public int getCount() {
        return _data == null?0:_data.size();
    }

    @Override
    public DrinkMenuButton getItem(int position) {
        return _data == null?null:_data.get(position);
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
        if(position != selectposition)
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.material_white));
        else
            viewHolder.textView.setTextColor(context.getResources().getColor(R.color.material_red_300));
        viewHolder.textView.setText(getItem(position).getName());
        return  convertView;
        }

        public static class ViewHolder {
            public TextView textView;
        }
}
