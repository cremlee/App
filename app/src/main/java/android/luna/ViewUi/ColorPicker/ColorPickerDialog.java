package android.luna.ViewUi.ColorPicker;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import evo.luna.android.R;

public class ColorPickerDialog extends Dialog implements View.OnClickListener {
	private final boolean debug = true;
	private final String TAG = "ColorPicker";
	private Context context;
	private String title;//标题
	private int mInitialColor;//初始颜色
	private int mdefaultColre;
    private OnColorChangedListener mListener;
	private ColorPickerView _ColorPickerView;
	private EditText edt_r,edt_g,edt_b;
	/**
     * 初始颜色黑色
     * @param context
     * @param title 对话框标题
     * @param listener 回调
     */
    public ColorPickerDialog(Context context, String title, 
    		OnColorChangedListener listener) {
    	this(context, Color.BLACK, title, listener);
    }
    
    /**
     * 
     * @param context
     * @param initialColor 初始颜色
     * @param title 标题
     * @param listener 回调
     */
    public ColorPickerDialog(Context context, int initialColor, 
    		String title, OnColorChangedListener listener) {
        super(context);
        this.context = context;
        mListener = listener;
        mInitialColor = initialColor;
        this.title = title;
    }
    
    public ColorPickerDialog(Context context, int initialColor, int defaultColor,
    		String title, OnColorChangedListener listener) {
        super(context);
        this.context = context;
        mListener = listener;
        mInitialColor = initialColor;
        this.title = title;
        this.mdefaultColre = defaultColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_color_picker);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setTitle(title);
		_ColorPickerView = findViewById(R.id.clrpicker);
		_ColorPickerView.setmInitialColor(mInitialColor);
		edt_r = findViewById(R.id.edt_r);
		edt_g = findViewById(R.id.edt_g);
		edt_b = findViewById(R.id.edt_b);
		edt_r.setText(((mInitialColor&0xff0000)>>16)+"");
		edt_g.setText(((mInitialColor&0xff00)>>8)+"");
		edt_b.setText(((mInitialColor&0xff))+"");
		_ColorPickerView.setOnColorChangedListener(new ColorPickerView.OnColorChangedListener() {
			@Override
			public void colorChanged(int color, boolean isdefault) {
				edt_r.setText(((color&0xff0000)>>16)+"");
				edt_g.setText(((color&0xff00)>>8)+"");
				edt_b.setText(((color&0xff))+"");
			}
		});
		_ColorPickerView.setOnColorSelectedListener(new ColorPickerView.OnColorSelectedListener() {
			@Override
			public void colorChanged(int color, boolean isdefault) {
				if(mListener!=null)
				{
					mListener.colorChanged(color,isdefault);

				}
			}
		});
		edt_r.addTextChangedListener(new TextWatcher() {
			int l=0;////////记录字符串被删除字符之前，字符串的长度
			int location=0;//记录光标的位置

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				l=s.length();
				location=edt_r.getSelectionStart();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if(!("").equals(s.toString()))
				{
					int value = Integer.valueOf(s.toString());
					if(value>=0 && value<=255)
					{
						mInitialColor =(_ColorPickerView.getmInitialColor()&0xff00ffff)+(value<<16);
						_ColorPickerView.setmInitialColor(mInitialColor);
					}
					else{

						Toast.makeText(context, "Invalid Value", Toast.LENGTH_SHORT).show();
					}
				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		edt_g.addTextChangedListener(new TextWatcher() {
			int l=0;////////记录字符串被删除字符之前，字符串的长度
			int location=0;//记录光标的位置

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				l=s.length();
				location=edt_g.getSelectionStart();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if(!("").equals(s.toString()))
				{
					int value = Integer.valueOf(s.toString());
					if(value>=0 && value<=255)
					{
						mInitialColor =(_ColorPickerView.getmInitialColor()&0xffff00ff)+(value<<8);
						_ColorPickerView.setmInitialColor(mInitialColor);
					}
					else{

						Toast.makeText(context, "Invalid Value", Toast.LENGTH_SHORT).show();
					}
				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		edt_b.addTextChangedListener(new TextWatcher() {
			int l=0;////////记录字符串被删除字符之前，字符串的长度
			int location=0;//记录光标的位置

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				l=s.length();
				location=edt_b.getSelectionStart();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

				if(!("").equals(s.toString()))
				{
					int value = Integer.valueOf(s.toString());
					if(value>=0 && value<=255)
					{
						mInitialColor =(_ColorPickerView.getmInitialColor()&0xffffff00)+(value);
						_ColorPickerView.setmInitialColor(mInitialColor);
					}
					else{

						Toast.makeText(context, "Invalid Value", Toast.LENGTH_SHORT).show();
					}
				}

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
    }

	@Override
	public void onClick(View v) {

	}


	public interface OnColorChangedListener {
    	/**
    	 * 回调函数
    	 * @param color 选中的颜色
    	 */
        void colorChanged(int color, boolean isdefault);
    }
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public void setmInitialColor(int mInitialColor) {
		this.mInitialColor = mInitialColor;
	}

	public OnColorChangedListener getmListener() {
		return mListener;
	}

	public void setmListener(OnColorChangedListener mListener) {
		this.mListener = mListener;
	}
}
