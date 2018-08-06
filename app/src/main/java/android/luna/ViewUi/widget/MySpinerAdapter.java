package android.luna.ViewUi.widget;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import evo.luna.android.R;

public class MySpinerAdapter extends BaseAdapter {

	private Context mContext;
	private Map<String,String> items = new TreeMap<String,String>();
	private String selKey = "";
	private boolean isgrey=false;
	private int showicon=0;
	private int dfcolor =Color.parseColor("#000000");
	public void setDfcolor(int dfcolor) {
		this.dfcolor = dfcolor;
	}
	public  int fontColor(int paramString)
	  {
	    switch (paramString+1)
	    {
	    case 1:
	      return Color.parseColor("#000000");
	    case 2:
	      return Color.parseColor("#FFFF00");
	    case 3:
	      return Color.parseColor("#FF0000");
	    case 4:
	      return Color.parseColor("#00FF00");
	    case 5:
	      return Color.parseColor("#DD14DD");
	    case 6:
	      return Color.parseColor("#F39438");
	    case 7:
	      return Color.parseColor("#4A82CC");
	    case 8:
	      return Color.parseColor("#FFFFFF");
	    }
	    return dfcolor;
	  }	
	public int getShowicon() {
		return showicon;
	}
	public void setShowicon(int showicon) {
		this.showicon = showicon;
	}
	public void setIsgrey(boolean isgrey) {
		this.isgrey = isgrey;
	}

	public static interface OnItemSelectListener {
		public void onItemClick(String key);
	};

	public MySpinerAdapter(Context context) {
		mContext = context;
		
	}
	public MySpinerAdapter(Context context,boolean a) {
		mContext = context;
		isgrey =a;
	}
	public MySpinerAdapter(Context context,int a) {
		mContext = context;
		showicon =a;
	}
	public void refreshData(Map<String,String> items, String selKey) {
		this.items = items;
		this.selKey = selKey;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public String getItem(int position) {
		
		Iterator<Entry<String, String>> iterator = items.entrySet().iterator();
		
		String value = "";
		
		int i=0;
		while (iterator.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			if(i == position){
				value = entry.getValue();
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
	public View getView(int pos, View convertView, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_simple_dropdown_icon_item, null);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.text1);
			viewHolder.icon = (TextView) convertView.findViewById(R.id.text2);
			
			viewHolder.textView.setSingleLine();
			viewHolder.textView.setTextSize(20.0f);
			viewHolder.textView.setTextColor(0xFFFFFFFF);			
			if(isgrey && pos!=0)
			{
				//viewHolder.textView.setTextColor(0xFFFF0000);
				viewHolder.textView.setVisibility(View.GONE);
			}
			if(showicon!=1)
			{
				viewHolder.icon.setVisibility(View.GONE);
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.icon.setBackgroundColor(fontColor(pos));
		viewHolder.textView.setText(getItem(pos));
		return convertView;
	}

	public static class ViewHolder {
		public TextView textView;
		public TextView icon;
	}

}
