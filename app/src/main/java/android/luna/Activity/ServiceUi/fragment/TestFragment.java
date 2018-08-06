package android.luna.Activity.ServiceUi.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_boiler_es;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_boiler_g;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_canister;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_esbrewer;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_grinder;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_mixer;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_peripheral;
import android.luna.Activity.ServiceUi.MachineDevice.aty_device_watersys;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Data.module.DeviceLayout.DeviceItemLayout;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.ViewUi.DeviceView.DeviceItemView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class TestFragment extends Fragment {
    private List<DeviceItemLayout> deviceItemLayouts =null;
    private List<Device> devices =null;
    private CremApp app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_demo, container, false);
        app = (CremApp)getActivity().getApplication();
        InitView(view);
        return view;
    }

    private void InitView(View view)
    {
        devices = DeviceXmlFactory.getXmlDevice(FileHelper.PATH_CONFIG+"config.xmld");
        deviceItemLayouts = DeviceXmlFactory.getXmlLayout(FileHelper.PATH_CONFIG+"config.xmll");
        if(devices!=null && devices.size()>0 && deviceItemLayouts!=null && deviceItemLayouts.size()>0)
        {
            ViewGroup viewGroup = view.findViewById(R.id.relative);
            for (DeviceItemLayout item:deviceItemLayouts )
            {
                DeviceItemView tv =new DeviceItemView(this.getActivity());
                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams((int)item.getWidth(),(int)item.getHeight());
                params.setMargins((int)item.getLeft(),(int)item.getTop(),0,0);
                tv.setLayoutParams(params);
                tv.setdev_icon(item.geticonres());
                tv.setdev_name(item.getname());
                tv.Initdata(getMainDeviceByUid(item.getUid()),getAttachDeviceByUid(item.getUid()));
                tv.SetOnDeviceClick(new DeviceItemView.OnDeviceClick() {
                    @Override
                    public void onDeviceClick(Device a, List<Device> b) {
                        app.setMain_device(a);
                        app.setAttach_device(b);
                        Intent intent;
                        if(a.getGroup_id() == 0x0002) //grinder
                        {
                            intent = new Intent(getActivity(),aty_device_grinder.class);
                            startActivity(intent);
                        }
                        else if((a.getGroup_id() == 0x0001) && (a.getCompent_type()==0x0001)) {
                            intent = new Intent(getActivity(), aty_device_esbrewer.class);
                            startActivity(intent);
                        }

                        else if((a.getGroup_id() == 0x0003)) {
                            intent = new Intent(getActivity(), aty_device_canister.class);
                            startActivity(intent);
                        }

                        else if((a.getGroup_id() == 0x0004)) {
                            intent = new Intent(getActivity(), aty_device_mixer.class);
                            startActivity(intent);
                        }
                        else if((a.getGroup_id() == 0x000A))
                        {
                            intent = new Intent(getActivity(), aty_device_watersys.class);
                            startActivity(intent);

                        }
                        else if((a.getGroup_id() == 0x000F) && (a.getCompent_type()==0x0002) ) {
                            intent = new Intent(getActivity(), aty_device_boiler_g.class);
                            startActivity(intent);
                        }
                        else if((a.getGroup_id() == 0x000F) && (a.getCompent_type()==0x0003) ) {
                            intent = new Intent(getActivity(), aty_device_boiler_es.class);
                            startActivity(intent);
                        }
                        else if((a.getGroup_id() == 0x0000) && (a.getCompent_type()==0x0002) ) {
                            intent = new Intent(getActivity(), aty_device_peripheral.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onStClick(Device a) {
                        EvoTrace.e("device","onStClick Id="+a.GetDeviceId());
                    }
                });
                viewGroup.addView(tv);

            }
        }
    }

  /*  private Device getDeviceByUid(String Uid)
    {
        for (Device item:devices)
        {
            if(item.GetDeviceId() == Integer.parseInt(Uid,16))
            {
                return item;
            }
        }
        return null;
    }*/


    private Device getMainDeviceByUid(String Uid)
    {

        for (Device item:devices)
        {
            if(item.GetDeviceId() == Integer.parseInt(Uid,16))
            {
                return item;
            }
        }
        return null;
    }


    private List<Device> getAttachDeviceByUid(String Uid)
    {
        List<Device> ret = new ArrayList<>(2);
        Device main = getMainDeviceByUid(Uid);
        String attstr = "";
        if(main!=null && main.getParent_id_list()!=null && main.getParent_id_list().size()>0)
        {

            for (Integer item:main.getParent_id_list())
            {
                attstr = String.format("%08X",item);
                if(attstr.startsWith("0005") || attstr.startsWith("0006")
                        || attstr.startsWith("0007")
                        ||attstr.startsWith("0008")
                        ||attstr.startsWith("0009")
                        || attstr.startsWith("000C")
                        || attstr.startsWith("000E")
                        || attstr.startsWith("0014")
                        || attstr.startsWith("0015")
                        || attstr.startsWith("0016")
                        || attstr.startsWith("0018")
                        ||attstr.startsWith("0019")
                        || attstr.startsWith("001A"))
                {
                    ret.add(getMainDeviceByUid(attstr));
                }
            }
        }
        //特殊处理boiler 和pump
        if(Uid.startsWith("000F")) //boiler
        {
            for (Device item:devices)
            {
                if(item.getGroup_id() == 0x0006 || item.getGroup_id() == 0x0016)
                {
                    ret.add(item);
                }
            }
        }
        if(Uid.startsWith("000A"))
        {
            if(main!=null && main.getSon_id_list()!=null && main.getSon_id_list().size()>0)
            {
                for (Integer item:main.getSon_id_list())
                {
                    attstr = String.format("%08X",item);
                    if(attstr.startsWith("0005"))
                        ret.add(getMainDeviceByUid(attstr));
                }
            }
        }
        if(ret.size()>0)
            return ret;
        return null;
    }

}
