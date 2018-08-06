package android.luna.Activity.ServiceUi.Setting.Schedule.adapter;

import android.content.Context;
import android.luna.Data.module.Scheduler;
import android.luna.Utils.AndroidUtils_Ext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class ECOSchedulerAdapter extends BaseAdapter {
    private List<Scheduler> _data;
    private Context _context;

    public interface OnSchedulerChanged
    {
           void delete(int position);
           void edit(int position);
    }
    private OnSchedulerChanged onSchedulerChanged=null;
    public void  setOnSchedulerChanged(OnSchedulerChanged a)
    {
        onSchedulerChanged =a;
    }
    public ECOSchedulerAdapter(List<Scheduler> _data, Context _context) {
        this._data = _data;
        this._context = _context;
    }

    @Override
    public int getCount() {
        return _data == null ?0:_data.size();
    }

    @Override
    public Scheduler getItem(int position) {
       return  _data == null ? null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder =null;
        final int pos = position;
        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(_context).inflate(R.layout.simple_list_clean_item, null);
            holder.index =  convertView.findViewById(R.id.index);
            holder.week =   convertView.findViewById(R.id.week);
            holder.time =   convertView.findViewById(R.id.time);
            holder.edit =   convertView.findViewById(R.id.edit);
            holder.del =   convertView.findViewById(R.id.del);
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onSchedulerChanged!=null)
                    {
                        onSchedulerChanged.edit(pos);
                    }
                }
            });
            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onSchedulerChanged!=null)
                    {
                        onSchedulerChanged.delete(pos);
                    }
                }
            });
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Scheduler scheduler = _data.get(position);
        if(scheduler!=null)
        {
            holder.index.setText(position+"");
            holder.week.setText(GetEndtime(scheduler.getWeek(),scheduler.getStartTime(),0));
            holder.time.setText(GetEndtime(scheduler.getWeek(),scheduler.getStartTime(),scheduler.getPersistTime()));
        }
        return convertView;
    }

    private String GetEndtime(int week,String starttime,int perhour)
    {
        int min_offset = Integer.parseInt(starttime.substring(3))/15;
        int starthour = Integer.parseInt(starttime.substring(0,2));
        String ret ="";
        int curhour = (starthour+perhour) % 24;
        int curweek=(week+(starthour+perhour)/24) % 7;
        ret = AndroidUtils_Ext.weekday(curweek,_context) + String.format("     %02d:%02d", curhour,min_offset*15);
        return ret;
    }
    class ViewHolder
    {
        TextView index;
        TextView week;
        TextView time;
        Button edit;
        Button del;
    }
}
