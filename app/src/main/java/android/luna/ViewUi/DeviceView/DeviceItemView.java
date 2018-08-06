package android.luna.ViewUi.DeviceView;

import android.content.Context;
import android.luna.Data.module.MachineDevice.Device;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/18.
 */

public class DeviceItemView extends LinearLayout {
    private  Context mContext;
    private ImageView dev_icon,dev_st;
    private TextView dev_name;

    private Device main_device =null;
    private List<Device> attach_devices = null;
    public interface OnDeviceClick
    {
        void onDeviceClick(Device a,List<Device> b);
        void onStClick(Device a);
    }
    OnDeviceClick _OnDeviceClick;

    public void SetOnDeviceClick(OnDeviceClick a)
    {
        _OnDeviceClick =a;
    }
    public DeviceItemView(Context context) {
        super(context);
        mContext =context;
        InitView();
    }

    public void Initdata(Device main,List<Device> attach)
    {
        main_device = main;
        attach_devices = attach;
    }
    private void InitView()
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_device_item, this, true);
        dev_st= view.findViewById(R.id.dev_st);
        dev_icon = view.findViewById(R.id.dev_icon);
        dev_name = view.findViewById(R.id.dev_name);
        dev_icon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_OnDeviceClick!=null)
                    _OnDeviceClick.onDeviceClick(main_device,attach_devices);
            }
        });
        dev_st.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_OnDeviceClick!=null)
                    _OnDeviceClick.onStClick(main_device);
            }
        });
    }

    public void setdev_icon(int res)
    {
        dev_icon.setImageResource(res);
    }

    public void setdev_name(String name)
    {
        dev_name.setText(name);
    }
}
