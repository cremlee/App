package android.luna.ViewUi.widget;

import java.text.DecimalFormat;

import android.content.Context;
import android.content.res.TypedArray;
import android.luna.Utils.Logger.EvoTrace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import evo.luna.android.R;

/**
 * 格式如（（最小值）======（进度条）======最大值 当前值）
 * 
 * @author Administrator
 * 
 */
public class SettingItemSeekBar extends LinearLayout {

	protected static final String TAG = "SettingItemSeekBar";
	private String name; // 当前进度条名称（在最前开始显示）
	private int min;// 最小值
	private int max;// 最大值
	private int cur;// 当前值
	private String unint;// 单位

	private Context mContext;

	private TextView txtName;
	private TextView txtMinValue;
	private SeekBar seekbar;
	private TextView txtMaxValue;
	private TextView txtCurValue;
	private TextView btndes;
	private TextView btnplus;
	private View line;

	private float times;
	private int stepvalue =1;
	 
	public int getStepvalue() {
		return stepvalue;
	}



	public void setStepvalue(int stepvalue) {
		this.stepvalue = stepvalue;
	}
	public interface ICoallBack{  
		     public void onValueChange(int value);
		     public void stopchange();
		    }  
	ICoallBack icallBack = null; 
	
	public void setonValueChange(ICoallBack iBack)  
	    {  
	        icallBack = iBack;  
	    } 

	
	
	public SettingItemSeekBar(Context context) {
		super(context);
		this.mContext = context;
	}

	public SettingItemSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		setupViews(attrs);
	}

	@SuppressWarnings("deprecation")
	public void SettxtCurValuecolor(boolean iscorrect)
	{
		if(iscorrect)
			txtCurValue.setTextColor(getResources().getColor(R.color.title_text_color));
		else
			txtCurValue.setTextColor(getResources().getColor(R.color.red));
	}
	private void setupViews(AttributeSet attrs) {
		View view = LayoutInflater.from(mContext).inflate(R.layout.layout_setting_item_seekbar, this, true);
		line = view.findViewById(R.id.line);
		txtName = (TextView) view.findViewById(R.id.name);
		txtMinValue = (TextView) view.findViewById(R.id.minValue);
		seekbar = (SeekBar) view.findViewById(R.id.seekBar);
		txtMaxValue = (TextView) view.findViewById(R.id.maxValue);
		txtCurValue = (TextView) view.findViewById(R.id.currentValue);
		btndes = (TextView) view.findViewById(R.id.btndes);
		btnplus =(TextView) view.findViewById(R.id.btnplus);
		TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.SettingItemSeekBar);
		name = array.getString(R.styleable.SettingItemSeekBar_sbName);
		unint = array.getString(R.styleable.SettingItemSeekBar_sbUnit);
		min = array.getInt(R.styleable.SettingItemSeekBar_sbMin, 0);
		max = array.getInt(R.styleable.SettingItemSeekBar_sbMax, 0);
		setCurProgress(array.getInt(R.styleable.SettingItemSeekBar_sbCur, 0));
		boolean isShowLine = array.getBoolean(R.styleable.SettingItemSeekBar_sbLine, true);
		
		
		
		OnClickListener uplisten = new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!seekbar.isEnabled())
					return;
				if(times>0){
					if(cur+stepvalue <= Integer.valueOf(getMax() + getMin())*times){
						setCur2(cur += stepvalue);
						if(icallBack!=null)
							icallBack.onValueChange(cur);
					}
				}else{
					if(cur+1 <= Integer.valueOf(getMax() + getMin())){
						setCur(cur += stepvalue);
						if(icallBack!=null)
							icallBack.onValueChange(cur);
					}
				}
			}
			
		};
		
		OnClickListener downlisten = new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!seekbar.isEnabled())
					return;
				if (cur - stepvalue >= getMin()) {
					if (times > 0) {
						setCur2(cur -= stepvalue);
						if(icallBack!=null)
							icallBack.onValueChange(cur);
					} else {
						setCur(cur -= stepvalue);
						if(icallBack!=null)
							icallBack.onValueChange(cur);
					}
				}
			}
			
		};		
		txtMinValue.setOnClickListener(downlisten);
		btndes.setOnClickListener(downlisten);
		
		txtMaxValue.setOnClickListener(uplisten);
		btnplus.setOnClickListener(uplisten);
		setName(name);
		

		setMin(min);
		setMax(max);

		seekbar.setMax(max);
		seekbar.setProgress(cur);
		if (!isShowLine) {
			line.setVisibility(View.INVISIBLE);
		}

		seekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(icallBack!=null)
					icallBack.stopchange();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				changed(fromUser, progress);
			}
		});

		array.recycle();
	}

	/**
	 * SeekBar setOnSeekBarChangeListener 必须调用次方法
	 * 
	 * @param fromUser
	 * @param progress
	 */
	public void changed(boolean fromUser, int progress) {
		if (fromUser) {
			if(times>0)
				setCur2((progress/stepvalue)*stepvalue + getMin());
			else
				setCur((progress/stepvalue)*stepvalue + getMin());	
			if(icallBack!=null)
				icallBack.onValueChange(cur);			
		}
	}

	public void changed2(int progress) {
		setCur2(progress);
	}

	public TextView getTextMinValue() {
		return txtMinValue;
	}

	public void setTextMaxValue(int max) {
		txtMaxValue.setText(max + "");
	}

	public TextView getTextCurValue() {
		return txtCurValue;
	}

	public TextView getTextMaxValue() {
		return txtMaxValue;
	}

	public void setName(String name) {
		this.name = name;
		txtName.setText(name);
	}

	public void setUnint(String unint) {
		this.unint = unint;

		txtMinValue.setText(String.valueOf(min));
		txtMaxValue.setText(String.valueOf(max+min));

		if (unint != null) {
			txtCurValue.setText(cur + unint);
		} else {
			txtCurValue.setText(String.valueOf(cur));
		}
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
		txtMinValue.setText(String.valueOf(min));
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max - getMin();
		seekbar.setMax(this.max);
		txtMaxValue.setText(String.valueOf(max));
	}

	public int getCur() {
		EvoTrace.e(TAG, "当前值为:" + cur);
		return cur;
	}

	public void setCurProgress(int cur) {
		seekbar.setProgress(cur - getMin());
		setCur(cur);
	}

	/**
	 * 最大值的多少倍
	 * 
	 * @param times
	 */
	public void setProgressMaxTimes(int times) {
		seekbar.setMax(getMax() * times);
		this.times = times;
	}

	/**
	 * g 专用
	 * 
	 * @param cur
	 */
	public void setCur2(float cur) {
		EvoTrace.e(TAG, "2=========================" + cur + ";" + (cur - getMin()));
		seekbar.setProgress((int) (cur - getMin()));
		txtCurValue.setText(new DecimalFormat("0.0").format(cur / times) + unint);
		this.cur = (int) cur;
	}

	public void setCur(int cur) {
		EvoTrace.e(TAG, "1=========================" + cur + ";" + (cur - getMin()));
		this.cur = cur;
		seekbar.setProgress(cur - getMin());
		
		if (unint != null) {
			txtCurValue.setText(cur + unint);
		} else {
			txtCurValue.setText(String.valueOf(cur));
		}
	}

	public SeekBar getSeekbar() {
		return seekbar;
	}

	public void setSeekbar(SeekBar seekbar) {
		this.seekbar = seekbar;
	}
	 public void setEnabled(boolean enabled) {
		 seekbar.setEnabled(enabled);
		 btndes.setEnabled(enabled); 
		 btnplus.setEnabled(enabled); 		 
	 }
	
}
