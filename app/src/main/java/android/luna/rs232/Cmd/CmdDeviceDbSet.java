package android.luna.rs232.Cmd;

import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.BaseCmd;

/**
 * Created by Lee.li on 2018/7/27.
 */

public class CmdDeviceDbSet extends BaseCmd {
    public String buildDeviceParam(int deviceid,int param, int value,boolean iscalbration)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("000C");
        buffer.append("01");
        buffer.append(AndroidUtils_Ext.oct2Hex4(deviceid));
        buffer.append("01");
        buffer.append(AndroidUtils_Ext.oct2Hex(param)); //param id
        if(iscalbration) {
            buffer.append("0000");
            buffer.append(AndroidUtils_Ext.octscale2Hex2(value,10));
        }else
        {
            buffer.append(AndroidUtils_Ext.oct2Hex4(value));
        }
        return super.buildCmdPkg(buffer.toString());
    }


}
