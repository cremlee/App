package android.luna.rs232.Cmd;

import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.BaseCmd;

import java.util.List;

/**
 * Created by Lee.li on 2018/7/16.
 */

public class CmdMachineConfig extends BaseCmd {
    public String buildCmdByType(int type)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("0000");
        buffer.append(AndroidUtils_Ext.oct2Hex4(type));
        buffer.append("00");
        return super.buildCmdPkg(buffer.toString());
    }
    public String buildCmdByConfig(List<Device> devices)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("0000");
        buffer.append("01ff0000");
        buffer.append(AndroidUtils_Ext.oct2Hex(devices.size()));
        for (Device item:devices)
        {
            buffer.append(AndroidUtils_Ext.oct2Hex4(item.GetDeviceId()));
            buffer.append(AndroidUtils_Ext.oct2Hex(item.GetParentCount()));
            if(item.GetParentCount()>0)
            {
                for (int parentid:item.getParent_id_list())
                {
                    buffer.append(AndroidUtils_Ext.oct2Hex4(parentid));
                }
            }
            buffer.append(AndroidUtils_Ext.oct2Hex(item.GetSonCount()));
            if(item.GetSonCount()>0)
            {
                for (int sonid:item.getSon_id_list())
                {
                    buffer.append(AndroidUtils_Ext.oct2Hex4(sonid));
                }
            }
        }
        return super.buildCmdPkg(buffer.toString());
    }
}
