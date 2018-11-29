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

import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

public class adpter_group_button extends BaseAdapter {
    private Context context;
    private CremApp app;

    public void setMwidthfact(float mwidthfact) {
        this.mwidthfact = mwidthfact;
    }

    public void setMheightfact(float mheightfact) {
        this.mheightfact = mheightfact;
    }

    private float mwidthfact,mheightfact;
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
    public adpter_group_button(Context context, List<DrinkMenuButton> data,CremApp app,float widthfact,float heightfact)
    {
        this.context =context;
        this.data = data;
        this.app =app;
        this.mheightfact=heightfact;
        this.mwidthfact =widthfact;
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

    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView;
        final int pos = position;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.lyt_adp_buttonitem, null);
            AbsListView.LayoutParams lp  = new AbsListView.LayoutParams((int) (155*mwidthfact), (int) (190*mheightfact));
            convertView.setLayoutParams(lp);
            itemView = new ItemView((ImageView)convertView.findViewById(R.id.icon),(TextView)convertView.findViewById(R.id.name));
            convertView.setTag(itemView);
            viewMap.put(position, convertView);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(iconClickListener!=null)
                        iconClickListener.onClicked(getItem(pos));
                }
            });
        }else
        {
            convertView = viewMap.get(position);
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
    public void setOnIconClickListener(IconClickListener a)
    {
        iconClickListener =a;
    }
    private IconClickListener iconClickListener;
    public interface IconClickListener
    {
        void onClicked(DrinkMenuButton a);
    }
}
