package android.luna.Activity.ServiceUi.Setting.CanisterEditor.fragment;

import android.app.Fragment;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Data.DAO.PowderFactory;
import android.luna.Data.DAO.StockFactoryDao;
import android.luna.Data.module.CanisterItemStock;
import android.luna.Data.module.DeviceLayout.DeviceItemLayout;
import android.luna.Data.module.MachineDevice.Dev_Canister;
import android.luna.Data.module.MachineDevice.Dev_Hopper;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.CanisterView.CanisterItemView;
import android.luna.rs232.Cmd.CmdDeviceDbSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/25.
 */

public class CanisterFragment extends Fragment {
    private List<Device> devices =null;
    private List<DeviceItemLayout> deviceItemLayouts =null;
    private List<Device> canisteritems =new ArrayList<>(4);
    private List<PowderItem> _data_grind=null;
    private List<PowderItem> _data_instant=null;
    private PowderFactory powderFactory;
    private StockFactoryDao stockFactoryDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_powder_canister, container, false);
        InitData();
        InitView(view);
        stockFactoryDao =new StockFactoryDao(getActivity(),((BaseActivity)getActivity()).getApp());
        return view;
    }

    public void setCanisteritems(List<PowderItem> a,List<PowderItem> b,PowderFactory c )
    {
        _data_grind =a;
        _data_instant =b;
        powderFactory = c;
    }
    private void InitView(View view)
    {

        CanisterItemView canisterItemView;
        ViewGroup viewGroup = view.findViewById(R.id.ly_main);
        viewGroup.removeAllViews();
        if(canisteritems!=null && canisteritems.size()>0)
        {
            for (final Device item :canisteritems)
            {
                if(item.getGroup_id() == 0x0015) //grinder
                {
                    canisterItemView =new CanisterItemView(this.getActivity());
                    canisterItemView.setdev_icon(R.mipmap.grinder);
                    canisterItemView.setdev_name("ID:"+String.format("%08X",item.GetDeviceId()));
                    canisterItemView.setdev_spinner(_data_grind);
                    canisterItemView.setPowderType(((Dev_Hopper)item ).getPowder_type());
                    canisterItemView.getDev_type().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(id!= ((Dev_Hopper)item).getPowder_type()) {
                                CanisterItemStock canisterItemStock = stockFactoryDao.getCanisterStockDao().queryByPid(((Dev_Hopper)item).getPowder_type());
                                if(canisterItemStock!=null)
                                {
                                    canisterItemStock.setPid((int)id);
                                    stockFactoryDao.getCanisterStockDao().update(canisterItemStock);
                                }
                                if(stockFactoryDao.getCanisterStockDao().queryByPid(((Dev_Hopper)item).getPowder_type())==null)
                                    powderFactory.getPowerItemDao().updatePowderSelectedSt(((Dev_Hopper)item).getPowder_type(),0);
                                ((Dev_Hopper)item).setPowder_type((int)id);
                                powderFactory.getPowerItemDao().updatePowderSelectedSt((int)id,1);
                                DeviceXmlFactory.UpdateDeviceXml(item);
                                // TODO: 2018/8/1 DB setting
                                String cmd =(new CmdDeviceDbSet()).buildDeviceParam(item.GetDeviceId(),4,(int)id,false);
                                ((BaseActivity)getActivity()).getApp().addCmdQueue(cmd);
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    } );
                    viewGroup.addView(canisterItemView);
                }
                else if(item.getGroup_id() == 0x0003)
                {
                    canisterItemView =new CanisterItemView(this.getActivity());
                    canisterItemView.setdev_icon(R.mipmap.canister);
                    canisterItemView.setdev_name("ID:"+String.format("%08X",item.GetDeviceId()));
                    canisterItemView.setdev_spinner(_data_instant);
                    canisterItemView.setPowderType(((Dev_Canister)item ).getPowder_type());
                    canisterItemView.getDev_type().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(id!= ((Dev_Canister) item).getPowder_type())
                            {
                                CanisterItemStock canisterItemStock = stockFactoryDao.getCanisterStockDao().queryByPid(((Dev_Hopper)item).getPowder_type());
                                if(canisterItemStock!=null)
                                {
                                    canisterItemStock.setPid((int)id);
                                    stockFactoryDao.getCanisterStockDao().update(canisterItemStock);
                                }
                                if(stockFactoryDao.getCanisterStockDao().queryByPid(((Dev_Hopper)item).getPowder_type())==null)
                                    powderFactory.getPowerItemDao().updatePowderSelectedSt(((Dev_Canister)item).getPowder_type(),0);
                                ((Dev_Canister) item).setPowder_type((int)id);
                                powderFactory.getPowerItemDao().updatePowderSelectedSt((int)id,1);
                                DeviceXmlFactory.UpdateDeviceXml(item);
                                // TODO: 2018/8/1 DB setting
                                String cmd =(new CmdDeviceDbSet()).buildDeviceParam(item.GetDeviceId(),1,(int)id,false);
                                ((BaseActivity)getActivity()).getApp().addCmdQueue(cmd);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    } );
                    viewGroup.addView(canisterItemView);
                }


            }
        }

    }
    private void InitData()
    {
        canisteritems.clear();
        devices = DeviceXmlFactory.getXmlDevice(FileHelper.PATH_CONFIG+"config.xmld");
        deviceItemLayouts = DeviceXmlFactory.getXmlLayout(FileHelper.PATH_CONFIG+"config.xmll");
        if(deviceItemLayouts!=null && deviceItemLayouts.size()>0)
        {
            for (DeviceItemLayout item:deviceItemLayouts)
            {
                if(item.getUid().startsWith("0002"))
                {
                    List<Device> devices =getAttachDeviceByUid(item.getUid());
                    if(devices!=null)
                        canisteritems.addAll(devices);
                }
                else if(item.getUid().startsWith("0003"))
                {
                    canisteritems.add(getMainDeviceByUid(item.getUid()));
                }
            }
        }
    }
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
                attstr = String.format("%08X",item.intValue());
                //attstr = Integer.toHexString(item.intValue()).toUpperCase();
                if(attstr.startsWith("0005") || attstr.startsWith("0006") || attstr.startsWith("0007") ||attstr.startsWith("0008")
                        || attstr.startsWith("000C") || attstr.startsWith("0014") || attstr.startsWith("0015") || attstr.startsWith("0018") ||attstr.startsWith("0019") || attstr.startsWith("001A"))
                {
                    ret.add(getMainDeviceByUid(attstr));
                }
            }
        }
        if(ret.size()>0)
            return ret;
        return null;
    }
}
