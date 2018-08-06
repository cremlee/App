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

/**
 * @author Administrator
 * 
 */
public class SettingItemTextView1 extends LinearLayout {

	private Context mContext;

	private TextView textView;
	private View line;

	private String textName; // 标题文字
	private int textBackground; // 背景色
	private int textNameColor;// 文字颜色
	private int lineMarginLeft;
	private boolean isTextBold; // 是否加粗
	private Drawable textDrawableRight;
	private Drawable textDrawableLeft;
	private boolean showLine;

	public SettingItemTextView1(Context context) {
		super(context, null);
		this.mContext = context;
	}

	public SettingItemTextView1(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initViews();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingItemTextView1);
		int bgColor = array.getInt(R.styleable.SettingItemTextView1_textview1TextBackground, -1);
		setTextBackground(bgColor);
		setTextNameColor(array.getInt(R.styleable.SettingItemTextView1_textview1TextColor, R.color.white));
		setTextBold(array.getBoolean(R.styleable.SettingItemTextView1_textview1IsTextBold, false));
		setTextName(array.getString(R.styleable.SettingItemTextView1_textview1Text));
		textDrawableLeft = array.getDrawable(R.styleable.SettingItemTextView1_textview1DrawbleLeft);
		textDrawableRight = array.getDrawable(R.styleable.SettingItemTextView1_textview1DrawbleRight);
		lineMarginLeft = array.getInt(R.styleable.SettingItemTextView1_textview1LineMarginLeft, 30);
		boolean showLine = array.getBoolean(R.styleable.SettingItemTextView1_textview1Line, true);
		if(!showLine){
			line.setVisibility(View.GONE);
		}
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
		params.leftMargin = lineMarginLeft;
		line.setLayoutParams(params);
		
		setDrawable(textDrawableLeft, textDrawableRight);

		array.recycle();
	}

	private void initViews() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setting_item_textview1, this, true);
		textView = (TextView) view.findViewById(R.id.text1);
		line = view.findViewById(R.id.line);
	}

	public TextView getTextView() {
		return textView;
	}

	public void setTextView(TextView textView) {
		this.textView = textView;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
		if (textView != null) {
			textView.setText(textName);
		}
	}

	public int getTextBackground() {
		return textBackground;
	}

	public void setTextBackground(int textBackground) {
		if (textBackground != -1) {
			this.textBackground = textBackground;
			if (textView != null) {
				textView.setBackgroundColor(textBackground);
			}
		}
	}

	public int getTextNameColor() {
		return textNameColor;
	}

	public void setTextNameColor(int textNameColor) {
		this.textNameColor = textNameColor;
		if (textView != null) {
			textView.setTextColor(textNameColor);
		}
	}

	public void setDrawable(Drawable textDrawableLeft, Drawable textDrawableRight) {
		if (textView != null) {
			textView.setCompoundDrawablesRelativeWithIntrinsicBounds(textDrawableLeft, null, textDrawableRight, null);
		}
		this.textDrawableRight = textDrawableRight;
		this.textDrawableLeft = textDrawableLeft;
	}

	public boolean isTextBold() {
		return isTextBold;
	}

	public void setTextBold(boolean isTextBold) {
		this.isTextBold = isTextBold;
		if (isTextBold) {
			textView.getPaint().setFakeBoldText(true);
		}
	}

	public boolean isShowLine() {
		return showLine;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
		if (!showLine) {
			line.setVisibility(INVISIBLE);
		}
	}

}
