package android.luna.ViewUi.widget;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import evo.luna.android.R;


public class MySpinerPopWindow extends PopupWindow implements OnItemClickListener {

	private Context mContext;
	private ListView mListView;
	private MySpinerAdapter mAdapter;
	private MySpinerAdapter.OnItemSelectListener mItemSelectListener;
	private Map<String,String> treemap = new TreeMap<>();

	public MySpinerPopWindow(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public void setItemListener(MySpinerAdapter.OnItemSelectListener listener) {
		mItemSelectListener = listener;
	}

	public void setshowIcon(int a,int clr)
	{
		mAdapter.setDfcolor(clr);
		mAdapter.setShowicon(a);
		mAdapter.notifyDataSetChanged();
	}
	public void setadt(boolean a)
	{
		mAdapter.setIsgrey(a);
		mAdapter.notifyDataSetChanged();
	}
	private void init() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.layout_spiner_window, null);
		setContentView(view);
		setWidth(LayoutParams.WRAP_CONTENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		setBackgroundDrawable(dw);
		mListView = view.findViewById(R.id.listview);
		mListView.setDividerHeight(0);// 去除黑线 
		mAdapter = new MySpinerAdapter(mContext);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
	}

	public void refreshData(Map<String,String> map, int keyPostion) {
		if (map != null) {
			treemap.putAll(map);
			mAdapter.refreshData(treemap, getSelKey(keyPostion));
			mAdapter.notifyDataSetChanged();
		}
	}
	
	public String getSelKey(int position){
		if(treemap==null || treemap.size() == 0){
			return "";
		}
		Iterator<Entry<String, String>> iterator = treemap.entrySet().iterator();
		
		String key = "";
		
		int i=0;
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			if(i == position){
				key = entry.getKey();
				break;
			}
			i++;
		}
		return key;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
		dismiss();
		if (mItemSelectListener != null) {
			mItemSelectListener.onItemClick(getSelKey(pos));
		}
	}

}
