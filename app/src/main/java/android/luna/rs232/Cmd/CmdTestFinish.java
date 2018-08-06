package android.luna.rs232.Cmd;

import android.luna.rs232.Cmd.base.BaseCmd;

/**
 * Created by Lee.li on 2018/7/20.
 */

public class CmdTestFinish extends BaseCmd{
    public String buildCmd(String op,String id) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("001F");
        buffer.append("00");
        buffer.append("00000000");
        return super.buildCmdPkg(buffer.toString());
    }
    public String buildCmd() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("001F");
        buffer.append("00");
        buffer.append("00000000");
        return super.buildCmdPkg(buffer.toString());
    }
}
