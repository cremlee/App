package android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter;

import android.content.Context;
import android.luna.Data.module.IngredientMonoProcess;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

public class monostepAdapter extends BaseAdapter {
    public void set_data(List<IngredientMonoProcess> _data) {
        this._data = _data;
    }

    private List<IngredientMonoProcess> _data;
    private Context mcontext;
    public monostepAdapter(Context context,List<IngredientMonoProcess> data)
    {
        mcontext =context;
        _data =data;
    }
    @Override
    public int getCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public IngredientMonoProcess getItem(int position) {
        return _data==null?null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _data==null?0:_data.get(position).getStepindex();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.adapter_mono_step, null);
            holder.name =  convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        IngredientMonoProcess monoProcess = getItem(position);
        if(monoProcess!=null) {
            holder.name.setText(mcontext.getString(R.string.SVR_DRINK_INGREDIENT_MONO_STEP)+monoProcess.getStepindex());
        }
        return convertView;
    }
    public class ViewHolder
    {
        TextView name;
    }
}
