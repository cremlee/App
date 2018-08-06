package android.luna.ViewUi.NutritionUi.adapter;

import android.content.Context;
import android.luna.Utils.Nutrition.NutritionListItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/24.
 */

public class Nutritionadapter extends BaseAdapter {
    private List<NutritionListItem> _data;
    private Context context;
    public Nutritionadapter(Context context,List<NutritionListItem> data)
    {
        this.context =context;
        this._data =data;
    }
    @Override
    public int getCount() {
        return this._data == null?0 : this._data.size();
    }

    @Override
    public NutritionListItem getItem(int position) {
        return this._data == null?null : this._data.get(position);
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
            convertView = LayoutInflater.from(this.context).inflate(R.layout.adapter_nutrition_show, null);
            viewHolder.txt1 = convertView.findViewById(R.id.txt1);
            viewHolder.txt2 = convertView.findViewById(R.id.txt2);
            viewHolder.txt3 = convertView.findViewById(R.id.txt3);
            viewHolder.txt4 = convertView.findViewById(R.id.txt4);
            viewHolder.txt5 = convertView.findViewById(R.id.txt5);
            viewHolder.txt6 = convertView.findViewById(R.id.txt6);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt1.setText(getItem(position).Fat);
        viewHolder.txt2.setText(getItem(position).Protein);
        viewHolder.txt3.setText(getItem(position).Sodium);
        viewHolder.txt4.setText(getItem(position).Sugar);
        viewHolder.txt5.setText(getItem(position).Carbohydrate);
        viewHolder.txt6.setText(getItem(position).Kilocal);
        return  convertView;
    }

    public static class ViewHolder {
        public TextView txt1;
        public TextView txt2;
        public TextView txt3;
        public TextView txt4;
        public TextView txt5;
        public TextView txt6;
    }
}
