package android.luna.ViewUi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import evo.luna.android.R;

public class SettingItemTextView2 extends LinearLayout {

	private static final String TAG = "SettingItemTextView2";

	private Context mContext;

	private TextView textView1;
	private TextView textView2;
	private View line;

	private String textTitle; // 前面的标题
	private String textValue; // 后面的描述
	private Drawable titleDrawable; // 标题图标，左边显示的图标,不设置不显示
	private Drawable valueDrawable;// 描述的右箭头,不设置不显示
	private boolean showLine;
	private int textBackground; // 背景色
	private int textNameColor;// 文字颜色

	public SettingItemTextView2(Context context) {
		super(context, null);
		this.mContext = context;
	}

	public SettingItemTextView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initViews(attrs);
	}

	private void initViews(AttributeSet attrs) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setting_item_textview2, this, true);
		textView1 = (TextView) view.findViewById(R.id.textView1);
		textView2 = (TextView) view.findViewById(R.id.textView2);

		final TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.SettingItemTextView2);
		setTextTitle(array.getString(R.styleable.SettingItemTextView2_textview2TextTitle));
		setTextValue(array.getString(R.styleable.SettingItemTextView2_textview2TextValue));
		setTextBackground(array.getColor(R.styleable.SettingItemTextView2_textview2TextBackground, 0xFFF));
		setTextNameColor(array.getColor(R.styleable.SettingItemTextView2_textview2TextColor, 0xFF3C3127));
		setTitleDrawable(array.getDrawable(R.styleable.SettingItemTextView2_textview2DrawbleLeft));
		setValueDrawable(array.getDrawable(R.styleable.SettingItemTextView2_textview2DrawbleRight));
		setShowLine(array.getBoolean(R.styleable.SettingItemTextView2_textview2Line, true));
		array.recycle();
	}

	public String getTextTitle() {
		return textTitle;
	}

	public TextView getTextView2() {
		return textView2;
	}

	public void setTextTitle(String textTitle) {
		this.textTitle = textTitle;
		textView1.setText(textTitle);
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
		textView2.setText(textValue);
	}

	public int getTextBackground() {
		return textBackground;
	}

	public void setTextBackground(int textBackground) {
		this.textBackground = textBackground;
		textView1.setBackgroundColor(textBackground);
		textView2.setBackgroundColor(textBackground);
	}

	public int getTextNameColor() {
		return textNameColor;
	}

	public void setTextNameColor(int textNameColor) {
		this.textNameColor = textNameColor;
		textView1.setTextColor(textNameColor);
		textView2.setTextColor(textNameColor);
	}

	public Drawable getTitleDrawable() {
		return titleDrawable;
	}

	public void setTitleDrawable(Drawable titleDrawable) {
		this.titleDrawable = titleDrawable;
		textView1.setCompoundDrawablesRelativeWithIntrinsicBounds(titleDrawable, null, null, null);
	}

	public Drawable getValueDrawable() {
		return valueDrawable;
	}

	public void setValueDrawable(Drawable valueDrawable) {
		this.valueDrawable = valueDrawable;
		textView2.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, valueDrawable, null);
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
}
