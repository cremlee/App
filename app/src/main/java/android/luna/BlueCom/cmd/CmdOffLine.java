package android.luna.BlueCom.cmd;

import android.luna.BlueCom.BlueCmdDefine;

/**
 * Created by Lee.li on 2018/6/11.
 */

public class CmdOffLine extends BlueCmdFactory implements IBluecmd {
    @Override
    public String toCmd() {
        return super.buildCmdPkg(BlueCmdDefine.CMD_OFF_LINE);
    }
}
