package android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter;

import android.content.Context;
import android.graphics.Color;
import android.luna.Data.module.BeverageBasic;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/8/13.
 */

public class BeverageItemSelectorAdpter extends BaseAdapter {
    private List<BeverageBasic> _data;
    private Context mcontext;
    private List<Boolean> isClicks;
    private OndrinkitemClicked ondrinkitemClicked=null;
    public interface OndrinkitemClicked
    {
        void OnitemClick(int pid,int pos);
    }
    public void SetdrinkitemOnClicked(OndrinkitemClicked a)
    {
        ondrinkitemClicked =a;
    }
    public void set_data(List<BeverageBasic> _data) {
        this._data = _data;
        isClicks.clear();
        if(_data!=null) {
            for (int i = 0; i < _data.size(); i++) {
                isClicks.add(false);
            }
        }
    }

    public void resetClick()
    {
        isClicks.clear();
        if(_data!=null) {
            for (int i = 0; i < _data.size(); i++) {
                isClicks.add(false);
            }
        }
    }
    public BeverageItemSelectorAdpter(Context context, List<BeverageBasic> data)
    {
        _data=data;
        mcontext =context;
        isClicks = new ArrayList<>();
        if(_data!=null) {
            for (int i = 0; i < _data.size(); i++) {
                isClicks.add(false);
            }
        }
    }
    @Override
    public int getCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public BeverageBasic getItem(int position) {
        return _data==null?null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder =null;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.adp_simple_drink, null);
            holder.text1 = convertView.findViewById(R.id.text1);
            viewMap.put(position, convertView);
            holder.text1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0; i <isClicks.size();i++){
                        isClicks.set(i,false);
                    }
                    isClicks.set(position,true);
                    notifyDataSetChanged();
                    if(ondrinkitemClicked!=null)
                    {
                        ondrinkitemClicked.OnitemClick(_data.get(position).getPid(),position);
                    }
                }
            });
            convertView.setTag(holder);
        }
        else {
            convertView = viewMap.get(position);
            holder = (ViewHolder)convertView.getTag();
        }
        if(isClicks.size()>0) {
            if (isClicks.get(position)) {
                holder.text1.setBackgroundColor(mcontext.getResources().getColor(R.color.sr_txt_selected));
            } else {
                holder.text1.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        holder.text1.setText(_data.get(position).getName());
        return convertView;
    }
    class ViewHolder
    {
        TextView text1;
    }
}
