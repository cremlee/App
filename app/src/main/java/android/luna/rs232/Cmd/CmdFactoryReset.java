package android.luna.rs232.Cmd;
import android.luna.rs232.Cmd.base.*;
public class CmdFactoryReset extends BaseCmd {

	public String buildCmd(){
		return super.buildCmdPkg("15");
	}
}
