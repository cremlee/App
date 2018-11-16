package android.luna.ViewUi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import evo.luna.android.R;

public class SettingItemCheckBox extends LinearLayout {

	private Context mContext;
	private CheckBox checkBox;
	private View lineView;
    private LinearLayout main_bg;
	private String text;
	private int textColor;

	public SettingItemCheckBox(Context context) {
		super(context, null);
		this.mContext = context;
	}

	public SettingItemCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;

		setupViews();
		final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SettingItemCheckBox);
		setText(array.getString(R.styleable.SettingItemCheckBox_checkBoxText));
		setChecked(array.getBoolean(R.styleable.SettingItemCheckBox_checkBoxChecked, false));
		setTextColor(array.getColor(R.styleable.SettingItemCheckBox_cbkTextColor, 0xff000000));
		setLinecolor(array.getColor(R.styleable.SettingItemCheckBox_cbkLineColor, 0xff000000));
		setmainbg(array.getDrawable(R.styleable.SettingItemCheckBox_cbkMainbg));
		boolean isVisibility = array.getBoolean(R.styleable.SettingItemCheckBox_lineEnable, true);
		lineView.setVisibility(isVisibility ? VISIBLE : GONE);
		array.recycle();
	}

	public void setmainbg(Drawable color)
	{
		if(color!=null)
			this.checkBox.setBackground(color);
		else
			this.checkBox.setBackgroundResource(R.drawable.setting_item_bg);
	}
	public void setLinecolor(int color)
	{
		lineView.setBackgroundColor(color);
	}
	private void setupViews() {
		View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setting_item_checkbox, this, true);
		checkBox = view.findViewById(R.id.checkbox);
		lineView = view.findViewById(R.id.lineView);
		main_bg = view.findViewById(R.id.main_bg);
	}

	public boolean isChecked() {
		return checkBox.isChecked();
	}

	public void setChecked(boolean checked) {
		if (checkBox != null) {
			checkBox.setChecked(checked);
		}
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
		if (checkBox != null && textColor != -1) {
			checkBox.setTextColor(textColor);
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		if (checkBox != null) {
			checkBox.setText(text);
		}
	}

	public CheckBox getCheckBox() {
		return checkBox;
	}

}
