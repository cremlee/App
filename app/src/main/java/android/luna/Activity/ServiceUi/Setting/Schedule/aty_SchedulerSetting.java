package android.luna.Activity.ServiceUi.Setting.Schedule;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.DAO.ScheduleDaoFactory;
import android.luna.Data.module.Scheduler;
import android.luna.Utils.AndroidUtils_Ext;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import evo.luna.android.R;

public class aty_SchedulerSetting extends BaseActivity implements OnClickListener, OnItemSelectedListener {

	private TextView textViewTitle;
	private TextView stoptimeTitle,priceofftitle;
	private Spinner spinnerEventType;
	private Spinner spinnerStartTime;
	private Spinner spinnerDuration;
	private Spinner sppriceoff;
	private TextView startTimeLayer;
	private TextView durationLayer;

	private Button btnCancel, btnOk;

	private int extraWeek, extraHour;
	/*private SchedulerDao schedulerDao;
	private SchedulerDetailDao schedulerDetailDao;
	private VendSchedulerDetailDao vendschedulerDetailDao;*/

	private ScheduleDaoFactory scheduleDaoFactory =null;
	private Scheduler mScheduler;
	private int m_type =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		extraValues();
		setupViews();
		initViews();
	}

	@Override
	public void InitView() {
		super.InitView();
		setContentView(R.layout.setting_scheduler_setting);
	}

	@Override
	public void InitData() {
		super.InitData();
		scheduleDaoFactory = new ScheduleDaoFactory(this,getApp());
	}

	@Override
	public void InitEvent() {
		super.InitEvent();
	}

	private void extraValues() {
		m_type = getIntent().getIntExtra("type", 0);
		extraWeek = getIntent().getIntExtra("week", 0);
		extraHour = getIntent().getIntExtra("hour", 0);
		int schedulerId = getIntent().getIntExtra("schedulerId", 0);
		mScheduler = scheduleDaoFactory.getScheduleEventDao().findSchedulerById(schedulerId);
		if(mScheduler== null)
			mScheduler = new Scheduler();
	}

	private void setupViews() {

		textViewTitle =  findViewById(R.id.textViewTitle);
		priceofftitle =  findViewById(R.id.priceofftitle);
		spinnerEventType =  findViewById(R.id.spinnerEventType);
		spinnerStartTime =  findViewById(R.id.spinnerStartTime);
		spinnerDuration = findViewById(R.id.spinnerDuration);
		sppriceoff =  findViewById(R.id.sppriceoff);
		stoptimeTitle =findViewById(R.id.stopTimetitle);
		startTimeLayer =  findViewById(R.id.startTimeLayer);
		durationLayer = findViewById(R.id.durationLayer);

		btnCancel =  findViewById(R.id.cancel);
		btnOk =  findViewById(R.id.ok);
		btnCancel.setOnClickListener(this);
		btnOk.setOnClickListener(this);

		textViewTitle.setText(AndroidUtils_Ext.weekday(extraWeek,this));
		stoptimeTitle.setText(R.string.sdl_stopt);
		sppriceoff.setVisibility(View.GONE);
		priceofftitle.setVisibility(View.GONE);
	}

	private void initViews() {

		// 事件类型
		
		String[] eventTypes = new String[] { getString(R.string.sch_off),getString(R.string.sch_daily),getString(R.string.sch_weekly),getString(R.string.sch_eco)};
		if(m_type ==1)
		{
			eventTypes = new String[] {getString(R.string.sch_off),"Price Off"};
		}
		
		// 开始时间
		String[] startTimes = new String[4];
		for (int i = 0; i < 4; i++) {
			String strHour = String.format("%02d:%02d", extraHour, i * 15);
			startTimes[i] = strHour;
		}
		
		String[] priceofflst = new String[] {"Free","50% Off"};
		
		sppriceoff.setAdapter(new ArrayAdapter<>(this, R.layout.adapter_simple_scheduler_sprinner, priceofflst));
		// 持续时间
		spinnerEventType.setAdapter(new ArrayAdapter<>(this, R.layout.adapter_simple_scheduler_sprinner, eventTypes));
		spinnerEventType.setOnItemSelectedListener(this);
		spinnerStartTime.setAdapter(new ArrayAdapter<>(this, R.layout.adapter_simple_scheduler_sprinner, startTimes));
		final DurationAdapter adapter = new DurationAdapter(extraHour,extraWeek);
		spinnerStartTime.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
				// TODO Auto-generated method stub
				//EvoTrace.e("mytest", "position="+position);
				/*
				 * add the data update for duration time
				 * 20160823 modify by lee*/
				adapter.setMin_offset(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}});
		
		
		
		spinnerDuration.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	
		if (mScheduler != null) {	
			if(m_type ==1)
			{
				spinnerEventType.setSelection(1);
				spinnerStartTime.setSelection(startTimeIndexByTime(mScheduler.getStartTime()));
				sppriceoff.setSelection(mScheduler.getOffvalue());
				sppriceoff.setVisibility(View.VISIBLE);
				priceofftitle.setVisibility(View.VISIBLE);
			}else
			{
				spinnerEventType.setSelection(mScheduler.getEventType());
				spinnerStartTime.setSelection(startTimeIndexByTime(mScheduler.getStartTime()));
			}
			spinnerDuration.setSelection(mScheduler.getPersistTime()-1);
		}
		
	}

	public int startTimeIndexByTime(String startTime) {
		if (startTime == null) {
			return 0;
		}
		String time = startTime.substring(3, startTime.length());
		int iTime = Integer.valueOf(time);
		switch (iTime) {
			case 0:
				return 0;
			case 15:
				return 1;
			case 30:
				return 2;
			case 45:
				return 3;
		}
		return 0;
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.cancel:
			AppManager.getAppManager().finishActivity(aty_SchedulerSetting.class);
			break;
		case R.id.ok:
			createScheduler();
			AppManager.getAppManager().finishActivity(aty_SchedulerSetting.class);
			break;
		default:
			break;
		}
	}

	/**
	 * 创建Scheduler到数据库中
	 */
	private void createScheduler() {  
		
		int schedulerId = mScheduler.getId();
		
		if(spinnerEventType.getSelectedItemPosition() != mScheduler.getEventType()){
			scheduleDaoFactory.getScheduleEventDao().deleteById(schedulerId);
			if(m_type==1)
				scheduleDaoFactory.getVendScheduleDetailDao().deleteBySchedulerId(schedulerId);
			else
				scheduleDaoFactory.getScheduleDetailDao().deleteBySchedulerId(schedulerId);
		}
		// 避免选时间是开始结束时间不正确的问题
		if(schedulerId!=0){
			scheduleDaoFactory.getScheduleEventDao().deleteById(schedulerId);
			if(m_type==1)
				scheduleDaoFactory.getVendScheduleDetailDao().deleteBySchedulerId(schedulerId);
			else
				scheduleDaoFactory.getScheduleDetailDao().deleteBySchedulerId(schedulerId);
		}				
		// 查询所选的区域是否有数据存在，
		
		int type = spinnerEventType.getSelectedItemPosition();
		if(m_type ==1 && type==1)
		{
			type = 4;
		}
		/*
		modify by lee
			dealwith the type 0 
		*/
		if(type == 0)
		{
			//do nothing 
		}else
		{
			int persistTime = 0;
			if(type == 3 || type == 4){
				persistTime = Integer.valueOf(spinnerDuration.getSelectedItemPosition()+1);
			}
	
			mScheduler.setOffvalue(sppriceoff.getSelectedItemPosition());
			mScheduler.setPersistTime(persistTime);
			mScheduler.setEventType(type);
			mScheduler.setStartTime(spinnerStartTime.getSelectedItem().toString());
			mScheduler.setWeek(extraWeek);
			scheduleDaoFactory.getScheduleEventDao().createOrUpdateScheduler(mScheduler);
			// 更新SchedulerDetail表
			if(m_type==1)
				scheduleDaoFactory.getVendScheduleDetailDao().updateSchedulerDetails(mScheduler);
			else
				scheduleDaoFactory.getScheduleDetailDao().updateSchedulerDetails(mScheduler);
			
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		stoptimeTitle.setText(R.string.sdl_edrt);
		switch (arg2) {
		case 0:			
				spinnerStartTime.setVisibility(View.GONE);
				spinnerDuration.setVisibility(View.GONE);
				stoptimeTitle.setVisibility(View.GONE);
				startTimeLayer.setVisibility(View.GONE);
				durationLayer.setVisibility(View.GONE);
			
			break;
		case 1:
		case 2:
			spinnerStartTime.setVisibility(View.VISIBLE);
			spinnerDuration.setVisibility(View.GONE);
			stoptimeTitle.setVisibility(View.GONE);
			startTimeLayer.setVisibility(View.GONE);
			durationLayer.setVisibility(View.GONE);
			break;
		case 3:
			startTimeLayer.setVisibility(View.GONE);
			durationLayer.setVisibility(View.GONE);
			stoptimeTitle.setText(R.string.sdl_stopt);
			stoptimeTitle.setVisibility(View.VISIBLE);
			spinnerStartTime.setVisibility(View.VISIBLE);
			spinnerDuration.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
		if(m_type ==1)
		{
			startTimeLayer.setVisibility(View.GONE);
			durationLayer.setVisibility(View.GONE);
			stoptimeTitle.setText(R.string.sdl_stopt);
			stoptimeTitle.setVisibility(View.VISIBLE);
			spinnerStartTime.setVisibility(View.VISIBLE);
			spinnerDuration.setVisibility(View.VISIBLE);
			sppriceoff.setVisibility(View.VISIBLE);
			priceofftitle.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	class DurationAdapter extends BaseAdapter {
		int persistLenght = 60;
		private int starthour;
		private int week;
		private int min_offset = 0;
		public int getMin_offset() {
			return min_offset;
		}

		public void setMin_offset(int min_offset) {
			this.min_offset = min_offset;	
			update();
			notifyDataSetChanged();
		}

		String[] persistTimes = new String[persistLenght];
		public DurationAdapter(int starthour,int week) {
			this.starthour = starthour;
			this.week =week;
			update();
				
		}
		private void update()
		{
			for (int i = 1; i <= persistLenght; i++) {
				persistTimes[i - 1] = GetEndtime(i);
			}
		}
		
		private String GetEndtime(int perhour)
		{
			String ret ="";
			int curhour = (this.starthour+perhour) % 24;
			int curweek=(this.week+(this.starthour+perhour)/24) % 7;
			ret = AndroidUtils_Ext.weekday(curweek,aty_SchedulerSetting.this) + String.format("     %02d:%02d", curhour,min_offset*15);
			return ret;
		}
		@Override
		public int getCount() {
			return persistTimes.length;
		}

		@Override
		public String getItem(int position) {
			return persistTimes[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = (TextView) LayoutInflater.from(aty_SchedulerSetting.this).inflate(R.layout.adapter_simple_scheduler_sprinner, null);
			textView.setText(persistTimes[position]);
			return textView;
		}

	}
}
