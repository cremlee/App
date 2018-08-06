package android.luna.ViewUi.CanisterView;

import android.content.Context;
import android.luna.Activity.ServiceUi.Setting.CanisterEditor.adapter.PowderAdapter;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.ViewUi.CanisterView.adapter.PowderGroupAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/18.
 */

public class CanisterItemView extends LinearLayout {
    private  Context mContext;
    private ImageView dev_icon;
    private TextView dev_name;

    public Spinner getDev_type() {
        return dev_type;
    }

    private Spinner dev_type;



    private PowderGroupAdapter powderGroupAdapter =null;

    public CanisterItemView(Context context) {
        super(context);
        mContext =context;
        InitView();
    }
    private void InitView()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_canister_item, this, true);
        dev_type= view.findViewById(R.id.dev_type);
        dev_icon = view.findViewById(R.id.dev_icon);
        dev_name = view.findViewById(R.id.dev_name);
    }

    public void setdev_icon(int res)
    {
        dev_icon.setImageResource(res);
    }

    public void setdev_name(String name)
    {
        dev_name.setText(name);
    }

    public void setdev_spinner(List<PowderItem> data)
    {
        powderGroupAdapter = new PowderGroupAdapter(mContext,data);
        dev_type.setAdapter(powderGroupAdapter);
    }

    public void setPowderType(int type)
    {
        dev_type.setSelection(powderGroupAdapter.getIndex(type));
    }
}
