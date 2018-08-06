package android.luna.ViewUi.widget;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.luna.Utils.Logger.EvoTrace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import evo.luna.android.R;

/**
 * 下拉控件
 * 
 * @author Administrator
 * 
 */
public class SettingItemDropDown extends LinearLayout implements MySpinerAdapter.OnItemSelectListener {

	protected static final String TAG = "SettingItemDropDown";

	private Context mContext;

	private TextView textView1;
	private TextView textView2;
	private View line;

	private Map<String, String> items = new LinkedHashMap<>();

	private String selText;
	private String selKey=""; // key

	private String textName;
	private String textValue;
	private Drawable drawable;
	private boolean showLine;

	private View view;

	private MySpinerPopWindow mSpinerPopWindow;

	public SettingItemDropDown(Context context) {
		super(context, null);
		this.mContext = context;
	}

	public SettingItemDropDown(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		setupViews();

		final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingItemDropDown);
		String item = array.getString(R.styleable.SettingItemDropDown_dropdownItems);
		String value = array.getString(R.styleable.SettingItemDropDown_dropdownValues);
		setTextName(array.getString(R.styleable.SettingItemDropDown_dropdownTextName));
		setTextValue(array.getString(R.styleable.SettingItemDropDown_dropdownTextValue));
		setDrawable(array.getDrawable(R.styleable.SettingItemDropDown_dropdownDrawble));
		setShowLine(array.getBoolean(R.styleable.SettingItemDropDown_dropdownLine, true));
		array.recycle();

		int keyPostion = 0;
		if (item != null) {
			
			String[] strItems = item.split("#");
			String[] strValues = new String[strItems.length];
			
			if (value != null) {
				String[] defValues = value.split("#");
				for (int i = 0; i < defValues.length; i++) {
					strValues[i] = defValues[i];
				}
			} else {// 默认值
				for (int i = 0; i < strItems.length; i++) {
					strValues[i] = String.format("%2d", i);
				}
			}

			for (int i = 0; i < strItems.length; i++) {
				items.put(strValues[i], strItems[i]);
			}
			keyPostion = Integer.valueOf(strValues[0].trim());
		}

		mSpinerPopWindow = new MySpinerPopWindow(mContext);
		mSpinerPopWindow.refreshData(items, keyPostion);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EvoTrace.e(TAG, "SettingItemDropDown");
				mSpinerPopWindow.setWidth(150);
				mSpinerPopWindow.showAsDropDown(textView2);
			}
		});
	}
	
	public void watershowupdate()
	{
		mSpinerPopWindow.setadt(true);
	}
	public void refreshData(int key){
		mSpinerPopWindow.refreshData(items, key);
	}
	
	public String getSelKey(Map<String,String> map,int position){
		if(map==null || map.size() == 0){
			return "";
		}
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		
		String key = "";
		
		int i=0;
		while (iterator.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			if(i == position){
				key = entry.getKey();
				break;
			}
			i++;
		}
		return key;
	}
	
	private void setupViews() {
		view = LayoutInflater.from(mContext).inflate(R.layout.layout_setting_item_dropdown, this, true);
		line = view.findViewById(R.id.line);
		textView1 = (TextView) view.findViewById(R.id.text1);
		textView2 = (TextView) view.findViewById(R.id.text2);
	}

	@Override
	public void onItemClick(String key) {
		this.selKey = key;
		String text = items.get(key);
		textView2.setText(text);
		EvoTrace.e(TAG, "key:" + key + ";text:" + text);
		
	}

	/**
	 * map<对应的值用16进制表示,名称>
	 * 
	 * @return
	 */
	public Map<String, String> getItemAndValues() {
		return items;
	}

	public void setItemAndValues(Map<String, String> map) {
		items.putAll(map);
		Iterator<Entry<String, String>> i = map.entrySet().iterator();
		while (i.hasNext()) {
			Entry<String, String> entry = i.next();
			items.put(entry.getKey(), entry.getValue());
			EvoTrace.e(TAG, "setItemAndValues:key:" + entry.getKey() + ";value:" + entry.getValue());
		}
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
		if (textView1 != null) {
			textView1.setText(textName);
		}
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
		if (textView2 != null) {
			textView2.setText(textValue);
		}
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;

		if (textView2 != null && drawable != null) {
			textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
		}
	}

	public boolean isShowLine() {
		return showLine;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
		if (!showLine) {
			line.setVisibility(View.INVISIBLE);
		}
	}
	
	public String getSelText(){
		return selText;
	}

	public String getSelKey() {
		EvoTrace.e(TAG, "selKey:"+selKey);
		return selKey;
	}

	public void setSelItem(String selKey, String selText) {
		this.selKey = selKey;
		this.selText = selText;
		EvoTrace.e(TAG, "selKey:"+selKey+";selText:"+selText);
		setTextValue(selText);
	}

	public MySpinerPopWindow getSpinerPopWindow() {
		return mSpinerPopWindow;
	}

}
