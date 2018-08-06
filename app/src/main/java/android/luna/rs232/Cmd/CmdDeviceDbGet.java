package android.luna.rs232.Cmd;

import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.BaseCmd;

/**
 * Created by Lee.li on 2018/7/27.
 */

public class CmdDeviceDbGet extends BaseCmd {
    public String buildDeviceParam(int deviceid,int param)
    {
        StringBuffer buffer = new StringBuffer();
        buffer.append("0014");
        buffer.append("01");
        buffer.append(AndroidUtils_Ext.oct2Hex4(deviceid));
        buffer.append("01");
        buffer.append(AndroidUtils_Ext.oct2Hex(param)); //param id
        return super.buildCmdPkg(buffer.toString());
    }
}
