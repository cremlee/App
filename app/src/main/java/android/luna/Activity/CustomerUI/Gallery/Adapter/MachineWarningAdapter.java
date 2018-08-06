package android.luna.Activity.CustomerUI.Gallery.Adapter;

import android.content.Context;
import android.luna.Data.Warning.MachineWarn;
import android.luna.Data.module.LogRecord;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;


public class MachineWarningAdapter extends BaseAdapter {

	private Context context;

	public List<MachineWarn> getGridItems() {
		return gridItems;
	}

	public void setGridItems(List<MachineWarn> gridItems) {
		this.gridItems = gridItems;
		notifyDataSetChanged();
	}

	private List<MachineWarn> gridItems;

	public MachineWarningAdapter(List<MachineWarn> gridItems, Context context) {
		this.context = context;
		this.gridItems = gridItems;
	}

	@Override
	public int getCount() {
		return gridItems==null?0:gridItems.size();
	}

	@Override
	public MachineWarn getItem(int position) {
		return gridItems.get(position);
	}

	@Override
	public long getItemId(int id) {
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_warning_show, null);
			holder.lable_icon =  convertView.findViewById(R.id.lable_icon);
			holder.lable_code = convertView.findViewById(R.id.lable_code);
			holder.lable_content =convertView.findViewById(R.id.lable_content);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final MachineWarn gridItem = getItem(position);
		if (gridItem != null) {
			holder.lable_icon.setImageResource(gridItem.getWarninglevel()==2?R.mipmap.ic_error:R.mipmap.ic_warn);
			holder.lable_code.setText(gridItem.getErrorDeviceName(context));
			holder.lable_content.setText(gridItem.getErrorContent());
		}
		return convertView;
	}

	class ViewHolder {
		ImageView lable_icon;
		TextView lable_code;
		TextView lable_content;
	}
}
