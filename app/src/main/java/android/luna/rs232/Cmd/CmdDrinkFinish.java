package android.luna.rs232.Cmd;

import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.BaseCmd;

/**
 * Created by Lee.li on 2018/7/20.
 */

public class CmdDrinkFinish extends BaseCmd{
    public String buildCmd(int pid) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("000F");
        buffer.append(AndroidUtils_Ext.oct2Hex2(pid));
        return super.buildCmdPkg(buffer.toString());
    }
}
