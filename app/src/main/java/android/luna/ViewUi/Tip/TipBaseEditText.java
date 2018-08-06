package android.luna.ViewUi.Tip;

import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * 带输入框的提示界面
 * @author Administrator
 *
 */
public abstract class TipBaseEditText extends BaseActivity {

	private TextView txtTitle;
	private EditText editText;
	private Button btnCancel;
	private Button btnOk;
	private String oldname=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_tip_base_edittext);
		setupViews();
	}

	private void setupViews() {
		txtTitle = findViewById(R.id.title);
		txtTitle.setText(title());
		oldname = getIntent().getStringExtra("argument");
		editText =  findViewById(R.id.editText);
		editText.setText(oldname);
		if(editTextInputType()!=0){
			editText.setInputType(editTextInputType());
		}
		editText.setHint(hint());
		editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(24)}); 
		btnCancel =  findViewById(R.id.cancel);
		btnOk =  findViewById(R.id.ok);
		
		btnOk.setOnClickListener(okListener());
		btnCancel.setOnClickListener(cancelListener());
	}
	public void setcanclebtn(OnClickListener a)
	{
		btnCancel.setOnClickListener(a);					
	}
	public TextView getTextViewTitle(){
		return txtTitle;
	}

	public OnClickListener cancelListener() {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				AppManager.getAppManager().finishActivity(TipBaseEditText.this);
			}
		};
	}

	public OnClickListener okListener() {
		return new OnClickListener() {

			@Override
			public void onClick(View v) {
				String input = editText.getText() + "";
				if (editText == null || "".equals(input)) {
					showToast("please input beverage name");
					return;
				}
				else if (!oldname.equals(input))
				{
					/*if(beverageDao.checkName(input.replace("'", "''")))
					{
						showToast(R.string.name_already_exists);
						return;
					}*/
				}
				Intent intent = intent();
				if (isReq()) {
					intent.putExtra("response", input);
					setResult(reqCode(), intent);
				} else {
					startActivity(intent);
				}
				AppManager.getAppManager().finishActivity(TipBaseEditText.this);
			}
		};
	}

	public EditText getEditText() {
		return editText;
	}

	public abstract int reqCode();
	
	public abstract String title();

	public abstract String hint();

	public abstract Intent intent();

	public abstract boolean isReq();
	
	public abstract int editTextInputType();
}
