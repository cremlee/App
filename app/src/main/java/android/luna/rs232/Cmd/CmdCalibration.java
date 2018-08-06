package android.luna.rs232.Cmd;

import android.luna.rs232.Cmd.base.BaseCmd;

public class CmdCalibration extends BaseCmd {

	public String buildCalibrationCmd(String partNo,String param) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("0011");
		buffer.append(partNo);
		buffer.append(param);
		return super.buildCmdPkg(buffer.toString());
	}
	
	public String buildCalibrationFinishedCmd(){
		return super.buildCmdPkg("0016");
	}
}
