package android.luna.ViewUi.LanguageUi.adapter;

import android.content.Context;
import android.luna.Activity.ServiceUi.Adapter.HelpAdapter;
import android.luna.Data.module.Languageitem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.lid.lib.LabelImageView;

import java.util.HashMap;
import java.util.List;


import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/25.
 */

public class LanguageAdapter extends BaseAdapter{
    private List<Integer> _data;
    private int[] langResId = new int[]{R.mipmap.zh,R.mipmap.en,R.mipmap.en,
                                        R.mipmap.en,R.mipmap.en,R.mipmap.en,
                                        R.mipmap.en,R.mipmap.en,R.mipmap.en};
    private Context mcontext;
    public interface OnFileSelect
    {
        void FileSelect(int id);
    }

    private OnFileSelect onFileSelect =null;

    public void setOnFileSelect(OnFileSelect a)
    {
        this.onFileSelect =a;
    }

    public LanguageAdapter(Context context,List<Integer> data)
    {
        mcontext =context;
        _data =data;
    }
    @Override
    public int getCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public Integer getItem(int position) {
        return _data ==null?null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private HashMap<Integer, View> viewMap = new HashMap<>();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int id = position;
        ViewHolder holder =null;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.adp_help_file, null);
            GridView.LayoutParams lp = new GridView.LayoutParams(120, 120);
            convertView.setLayoutParams(lp);
            holder.picicon =convertView.findViewById(R.id.pic_icon);
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
            viewMap.put(position, convertView);
            holder.picicon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onFileSelect!=null)
                    {
                        onFileSelect.FileSelect(_data.get(id));
                    }
                }
            });
        }
        else {
            convertView = viewMap.get(position);
            holder = (ViewHolder)convertView.getTag();
        }
        holder.picicon.setBackgroundResource(langResId[getItem(position)-1]);
        holder.name.setText(Languageitem.getLanguageName(getItem(position)));
        return  convertView;
    }

    class ViewHolder
    {
        LabelImageView picicon;
        TextView name;
    }
}
