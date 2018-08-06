package android.luna.ViewUi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/19.
 */

public class MaintenceItemView extends LinearLayout {

    private Context mContext;
    private String textTitle; // 前面的标题
    private String textValue; // 后面的描述
    private int progressvalue;// 进度条
    private int progressmax;
    private String textbtn1;
    private String textbtn2;


    private TextView textName,textUsedTime;
    private Button btn_c,btn_r;
    private SeekBar seekBarUsed;

    public MaintenceItemView(Context context) {
        super(context);
        mContext = context;
    }

    public MaintenceItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews(attrs);
    }

    private void initViews(AttributeSet attrs) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.aty_maintenceitem, this, true);
        textName = view.findViewById(R.id.textName);
        textUsedTime = view.findViewById(R.id.textUsedTime);
        btn_c = view.findViewById(R.id.btn_c);
        btn_r = view.findViewById(R.id.btn_r);
        seekBarUsed = view.findViewById(R.id.seekBarUsed);
        final TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.MaintenceItemView);
        setTextTitle(array.getString(R.styleable.MaintenceItemView_itemTitle));
        setProgressvalue(array.getInt(R.styleable.MaintenceItemView_progress,0));
        setProgressmax(array.getInt(R.styleable.MaintenceItemView_max,100));
        setTextbtn1(array.getString(R.styleable.MaintenceItemView_first_txt));
        setTextbtn2(array.getString(R.styleable.MaintenceItemView_sec_txt));
        array.recycle();
    }

    public String getTextTitle() {
        return textTitle;
    }

    public void setTextTitle(String textTitle) {
        this.textTitle = textTitle;
        textName.setText(textTitle);
    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
        textUsedTime.setText(textValue);
    }

    public int getProgressvalue() {
        return progressvalue;
    }

    public void setProgressvalue(int progressvalue) {
        this.progressvalue = progressvalue;
        seekBarUsed.setProgress(progressvalue);
    }

    public String getTextbtn1() {
        return textbtn1;
    }

    public void setTextbtn1(String textbtn1) {
        this.textbtn1 = textbtn1;
        btn_c.setText(textbtn1);
    }

    public String getTextbtn2() {
        return textbtn2;
    }

    public void setTextbtn2(String textbtn2) {
        this.textbtn2 = textbtn2;
        btn_r.setText(textbtn2);
    }

    public int getProgressmax() {
        return progressmax;
    }

    public void setProgressmax(int progressmax) {
        this.progressmax = progressmax;
        seekBarUsed.setMax(progressmax);
    }
}
