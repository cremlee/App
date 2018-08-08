package android.luna.rs232.Cmd;

import android.luna.rs232.Cmd.base.BaseCmd;

/**
 * Created by Lee.li on 2018/7/20.
 */

public class CmdConfigFinish extends BaseCmd{
    public String buildCmd() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("0020");
        return super.buildCmdPkg(buffer.toString());
    }
}
