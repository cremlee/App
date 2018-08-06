package android.luna.rs232.Cmd;
import android.luna.rs232.Cmd.base.*;

public class CmdErrorCheck extends BaseCmd {

	public  static String ERROR_WATER ="0401";
	public  static String ERROR_OVERCURRENT ="0101";
	public  static String ERROR_HEAT ="0202";
	public String buildCmd(String error){
		StringBuffer buffer = new StringBuffer();
		buffer.append(CmdDefine.CMD_ERROR_CHECK);
		buffer.append("01");
		buffer.append(error);
		 return super.buildCmdPkg(buffer.toString());
	}
}
