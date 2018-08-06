package android.luna.Activity.ServiceUi.Adapter;
import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Data.module.BeverageCount;
import android.luna.Utils.PictureManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

public class CoffeeCountAdapter extends BaseAdapter {

	private Context context;
	private List<BeverageCount> counters = new ArrayList<>();

	public CoffeeCountAdapter(Context context, List<BeverageCount> counters) {
		this.context = context;
		this.counters = counters;
	}

	@Override
	public int getCount() {
		return counters.size();
	}

	@Override
	public BeverageCount getItem(int position) {
		return counters.get(position);
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
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_setting_counter, null);
			holder.imageView =  convertView.findViewById(R.id.imageview);
			holder.txtTitle =   convertView.findViewById(R.id.text_title);
			holder.txtCount =   convertView.findViewById(R.id.text_counter);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		BeverageCount beverage = getItem(position);

		if (beverage != null) {
			holder.txtTitle.setText(beverage.getName());
			//holder.imageView
			if(!beverage.getIconpath().equals("")) {
				Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(beverage.getIconpath());
				if (bitmap == null) {
					final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(beverage.getIconpath(), 240, 240);
					if (tmpbitmap != null) {
						PictureManager.getInstance().addBitmapToMemoryCache(beverage.getIconpath(), tmpbitmap);
						bitmap = PictureManager.getInstance().getBitmapFromMemCache(beverage.getIconpath());
					}
				}
				if (bitmap != null)
					holder.imageView.setImageBitmap(bitmap);
				else
					holder.imageView.setImageBitmap(null);
			}

			holder.txtCount.setText(beverage.getDrinkCount() + "");
		}

		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView txtTitle;
		TextView txtCount;
	}
}
