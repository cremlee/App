package android.luna.BlueCom.cmd;

import android.luna.Utils.AndroidUtils_Ext;

/**
 * Created by Lee.li on 2018/6/8.
 */

public class BlueCmdFactory {
    public String buildCmdPkg(String data)
    {
        StringBuffer cmdBuffer = new StringBuffer();
        cmdBuffer.append("stx#");
        cmdBuffer.append(AndroidUtils_Ext.inttoBCD(data.length()+5));
        cmdBuffer.append(data);
        cmdBuffer.append("endl;");
        return cmdBuffer.toString();
    }
}
