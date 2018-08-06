package android.luna.Activity.ServiceUi.Adapter;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Activity.UpdateUi.PicImportAdapter;
import android.luna.Utils.PictureManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.lid.lib.LabelImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/27.
 */

public class HelpAdapter  extends BaseAdapter {
    public void set_data(List<String> _data) {
        this._data = _data;
        notifyDataSetChanged();
    }

    private List<String> _data =new ArrayList<>();
    private Context mcontext=null;
    public HelpAdapter(Context context,List<String> data)
    {
        this.mcontext =context;
        this._data =data;
    }

    public interface OnFileSelect
    {
        void FileSelect(String path);
    }

    private  OnFileSelect onFileSelect =null;

    public void setOnFileSelect(OnFileSelect a)
    {
        this.onFileSelect =a;
    }

    @Override
    public int getCount() {
        return _data==null?0:_data.size();
    }

    @Override
    public String getItem(int position) {
        return _data==null?null:_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int id = position;
        ViewHolder holder =null;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.adp_help_file, null);
            GridView.LayoutParams lp = new GridView.LayoutParams(200, 200);
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
                        onFileSelect.FileSelect(getItem(id));
                    }
                }
            });
        }
        else {
            convertView = viewMap.get(position);
            holder = (ViewHolder)convertView.getTag();
        }
        holder.picicon.setBackgroundResource(R.drawable.ics_log);
        holder.name.setText(getItem(position).substring(getItem(position).lastIndexOf("/")+1));
        return  convertView;
    }
    class ViewHolder
    {
        LabelImageView picicon;
        TextView name;
    }
}
