package android.luna.Activity.ServiceUi.Adapter;
import android.content.Context;
import android.luna.Data.module.FAQItem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lid.lib.LabelImageView;
import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import evo.luna.android.R;

public class ListViewFaqAdapter extends BaseAdapter {
    private List<FAQItem> _date;
    private LayoutInflater mLayoutInflater;
    private HashSet<Integer> mExpandedPositionSet = new HashSet<>();
    private TextView label,faq_answer;
    public ListViewFaqAdapter(Context context, List<FAQItem> data) {
        mLayoutInflater=LayoutInflater.from(context);
        this._date =data;
    }

    @Override
    public int getCount() {
        return this._date == null?0:this._date.size();
    }


    @Override
    public FAQItem getItem(int position) {
        return this._date == null?null:this._date.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    private HashMap<Integer, View> viewMap = new HashMap<>();
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ExpandableLayout expandableLayout;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            convertView=mLayoutInflater.inflate(R.layout.item_faq,parent,false);
            expandableLayout = convertView.findViewById(R.id.expandable_layout);
            expandableLayout.setExpandWithParentScroll(false);
            convertView.setTag(expandableLayout);
            //convertView.setTag(holder);
            viewMap.put(position, convertView);
        }
        else
        {
            convertView = viewMap.get(position);
           // holder = (ViewHolder)convertView.getTag();
            expandableLayout =(ExpandableLayout) convertView.getTag();
        }

        if(expandableLayout !=null) {
            expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
                @Override
                public void onExpand(boolean expanded) {
                    registerExpand(position);
                }
            });
        }
        label = convertView.findViewById(R.id.label);
        faq_answer = convertView.findViewById(R.id.faq_answer);
        expandableLayout.setExpand(mExpandedPositionSet.contains(position));
        label.setText(_date.get(position).getQ_content());
        faq_answer.setText(_date.get(position).getA_content());
        return convertView;
    }

    private void registerExpand(int position) {
        if (mExpandedPositionSet.contains(position)) {
            removeExpand(position);
        }else {
            addExpand(position);
        }
    }

    private void removeExpand(int position) {
        mExpandedPositionSet.remove(position);
    }

    private void addExpand(int position) {
        mExpandedPositionSet.add(position);
    }
    class ViewHolder
    {
        TextView label;
        TextView faq_answer;
    }
}
