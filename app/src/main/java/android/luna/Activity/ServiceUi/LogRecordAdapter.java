package android.luna.Activity.ServiceUi;

import android.content.Context;
import android.luna.Data.module.LogRecord;
import android.luna.Utils.AndroidUtils_Ext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import evo.luna.android.R;


public class LogRecordAdapter extends BaseAdapter {

	private Context context;

	public List<LogRecord> getGridItems() {
		return gridItems;
	}

	public void setGridItems(List<LogRecord> gridItems) {
		this.gridItems = gridItems;
		notifyDataSetChanged();
	}

	private List<LogRecord> gridItems;

	public LogRecordAdapter(List<LogRecord> gridItems, Context context) {
		this.context = context;
		this.gridItems = gridItems;
	}

	@Override
	public int getCount() {
		return gridItems==null?0:gridItems.size();
	}

	@Override
	public LogRecord getItem(int position) {
		return gridItems.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_log_show, null);
			holder.lb_time =  convertView.findViewById(R.id.lable_time);
			holder.lb_type = convertView.findViewById(R.id.lable_type);
			holder.btn_content =convertView.findViewById(R.id.lable_content);
			//holder.btn_content.setTextSize(23.0f);
			//holder.btn_content.setTextColor(context.getResources().getColor(R.color.rfab__color_text_label_item));
			//holder.btn_content.getPaint().setFakeBoldText(true);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final LogRecord gridItem = getItem(position);
		if (gridItem != null) {
			holder.lb_time.setText(gridItem.getEvent_time().substring(5,gridItem.getEvent_time().length()-3));
			//holder.lb_type.setText(String.valueOf(gridItem.getEvent_type()));
			//holder.lb_type.setBackgroundColor(AndroidUtils_Ext.fontColor(String.valueOf(gridItem.getEvent_type())));
			holder.btn_content.setText(gridItem.getEvent_info());
		}
		return convertView;
	}

	class ViewHolder {
		TextView lb_time;
		TextView lb_type;
		TextView btn_content;
	}
}
