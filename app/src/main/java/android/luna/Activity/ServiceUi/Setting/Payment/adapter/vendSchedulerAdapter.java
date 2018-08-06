package android.luna.Activity.ServiceUi.Setting.Payment.adapter;

import android.content.Context;
import android.content.Intent;
import android.luna.Activity.ServiceUi.Setting.Schedule.aty_SchedulerSetting;
import android.luna.Data.DAO.ScheduleDaoFactory;
import android.luna.Data.module.SchedulerDetail;
import android.luna.Data.module.VendSchedulerDetail;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

public class vendSchedulerAdapter extends BaseAdapter {

	private static final String TAG = "SchedulerOverviewAdapter";

	private Context context;

	private ScheduleDaoFactory scheduleDaoFactory;

	private Map<Integer, List<VendSchedulerDetail>> mapSchedulerDetails;

	private int startColor, endColor, normalColor;

	public vendSchedulerAdapter(Context context, Map<Integer, List<VendSchedulerDetail>> schedulerDetails, ScheduleDaoFactory scheduleDaoFactory) {
		this.context = context;
		this.scheduleDaoFactory = scheduleDaoFactory;
		this.mapSchedulerDetails = schedulerDetails;
		startColor = context.getResources().getColor(android.R.color.holo_orange_light);
		endColor = context.getResources().getColor(android.R.color.holo_orange_dark);
		normalColor = context.getResources().getColor(R.color.title_text_color);

	}

	@Override
	public int getCount() {
		return 24; // 24小时
	}

	@Override
	public List<VendSchedulerDetail> getItem(int position) {
		return mapSchedulerDetails == null ? null : mapSchedulerDetails.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	private HashMap<Integer, View> viewMap = new HashMap<>();
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if(!viewMap.containsKey(position) || viewMap.get(position) == null)
		 {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.setting_scheduler_overview_item, null);
			holder.time = convertView.findViewById(R.id.time);
			holder.sun =  convertView.findViewById(R.id.sun);
			holder.mon =  convertView.findViewById(R.id.mon);
			holder.tue =  convertView.findViewById(R.id.tue);
			holder.wed =  convertView.findViewById(R.id.wed);
			holder.thu =  convertView.findViewById(R.id.thu);
			holder.fri =  convertView.findViewById(R.id.fri);
			holder.sat =  convertView.findViewById(R.id.sat);

			holder.sun.setOnClickListener(clickListener(position, 0));
			holder.mon.setOnClickListener(clickListener(position, 1));
			holder.tue.setOnClickListener(clickListener(position, 2));
			holder.wed.setOnClickListener(clickListener(position, 3));
			holder.thu.setOnClickListener(clickListener(position, 4));
			holder.fri.setOnClickListener(clickListener(position, 5));
			holder.sat.setOnClickListener(clickListener(position, 6));

			convertView.setTag(holder);
			viewMap.put(position, convertView);
		} else {
			convertView = viewMap.get(position);
			holder = (ViewHolder) convertView.getTag();
		}

		holder.time.setText(String.format("%02d:00", position));
		if (position % 2 == 0) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.cararra));
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.spring_wood));
		}
		if (mapSchedulerDetails != null) {
			List<VendSchedulerDetail> schedulerDetails = mapSchedulerDetails.get(position);
			if (schedulerDetails != null) {
				int size = schedulerDetails.size();

				for (int i = 0; i < size; i++) {
					VendSchedulerDetail detail = schedulerDetails.get(i);
					int hour = detail.getHour();
					int week = detail.getWeek();
					String name = detail.getName(context);
					int persistFlag = detail.getPersistFlag();
					int color = normalColor;
					if (persistFlag == 1) {
						color = startColor;
						 } 
					else if (persistFlag == 2) {
						 name = context.getString(R.string.stop);//"Wake Up";
						 }
					
					switch (week) {
					case 0:
						holder.sun.setTextColor(color);
						holder.sun.setText(name);
						if (persistFlag == 0 || persistFlag == 2) {
							holder.sun.setClickable(false);
						}
						break;
					case 1:
						holder.mon.setTextColor(color);
						holder.mon.setText(name);
						if (persistFlag == 0 || persistFlag == 2) {
							holder.mon.setClickable(false);
						}
						break;
					case 2:
						holder.tue.setTextColor(color);
						holder.tue.setText(name);
						if (persistFlag == 0 || persistFlag == 2) {
							holder.tue.setClickable(false);
						}
						break;
					case 3:
						holder.wed.setTextColor(color);
						holder.wed.setText(name);
						if (persistFlag == 0 || persistFlag == 2) {
							holder.wed.setClickable(false);
						}
						break;
					case 4:
						holder.thu.setTextColor(color);
						holder.thu.setText(name);
						if (persistFlag == 0 || persistFlag == 2) {
							holder.thu.setClickable(false);
						}
						break;
					case 5:
						holder.fri.setTextColor(color);
						holder.fri.setText(name);
						if (persistFlag == 0 || persistFlag == 2) {
							holder.fri.setClickable(false);
						}
						break;
					case 6:
						holder.sat.setTextColor(color);
						holder.sat.setText(name);
						if (persistFlag == 0 || persistFlag == 2) {
							holder.sat.setClickable(false);
						}
						break;
					default:
						break;
					}
				}
			}
		}

		return convertView;
	}

	public OnClickListener clickListener(final int hour, final int week) {
		return new OnClickListener() {

			@Override
			public void onClick(View view) {
				VendSchedulerDetail detail = scheduleDaoFactory.getVendScheduleDetailDao().findSchedulerDetailByWeekAndHour(week, hour);
				int schedulerId = 0;
				if (detail != null) {
					schedulerId = detail.getSchedulerId();
				}
				Intent intent = new Intent(context, aty_SchedulerSetting.class);
				intent.putExtra("schedulerId", schedulerId);
				intent.putExtra("hour", hour);
				intent.putExtra("week", week);
				intent.putExtra("type", 1);
				context.startActivity(intent);
			}
		};
	}

	class ViewHolder {
		TableRow row;
		TextView time, sun, mon, tue, wed, thu, fri, sat;
	}
}
