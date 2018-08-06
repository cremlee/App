package android.luna.rs232.Cmd;
import android.luna.rs232.Cmd.base.*;
public class CmdMachineErrGet extends BaseCmd {

	public String buildCmd(String ms){
		return super.buildCmdPkg("0B"+ms);
	}
}
