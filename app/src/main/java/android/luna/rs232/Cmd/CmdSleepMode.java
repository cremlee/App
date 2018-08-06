package android.luna.rs232.Cmd;
import android.luna.rs232.Cmd.base.*;
public class CmdSleepMode extends BaseCmd {

	public static final String SLEEP_MODE_ENTER = "01";
	public static final String SLEEP_MODE_EXIT = "02";
	
	public String build(String opCmd){
		return super.buildCmdPkg("12"+opCmd);
	}
}
