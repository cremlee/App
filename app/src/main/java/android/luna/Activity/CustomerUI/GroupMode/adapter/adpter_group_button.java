package android.luna.Activity.CustomerUI.GroupMode.adapter;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Utils.ImageConvertFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

public class adpter_group_button extends BaseAdapter {
    private Context context;
    private CremApp app;
    public void setData(List<DrinkMenuButton> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private List<DrinkMenuButton> data;
    public adpter_group_button(Context context, List<DrinkMenuButton> data,CremApp app)
    {
        this.context =context;
        this.data = data;
        this.app =app;
    }
    @Override
    public int getCount() {
        return this.data==null?0:this.data.size();
    }

    @Override
    public DrinkMenuButton getItem(int position) {
        return this.data ==null?null:this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.lyt_adp_buttonitem, null);
            AbsListView.LayoutParams lp  = new AbsListView.LayoutParams(155,190);
            convertView.setLayoutParams(lp);
            itemView = new ItemView((ImageView)convertView.findViewById(R.id.icon),(TextView)convertView.findViewById(R.id.name));
            convertView.setTag(itemView);
        }else
        {
            itemView = (ItemView) convertView.getTag();
        }
        itemView.icon.setImageBitmap(ImageConvertFactory.getsavefrompath(getItem(position).getIconpath(),155,155));
        itemView.name.setText(getItem(position).getName());
        itemView.name.setTextColor(app.get_screenSettings_instance().getTextcolor());
        return convertView;
    }
    class ItemView
    {
        protected ImageView icon;
        protected TextView name;
        public ItemView(ImageView icon, TextView name)
        {
            this.icon =icon;
            this.name =name;

        }
    }
}
