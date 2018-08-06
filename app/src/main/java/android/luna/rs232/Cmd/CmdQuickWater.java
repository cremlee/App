package android.luna.rs232.Cmd;

import android.util.Log;
import android.luna.rs232.Cmd.base.*;

public class CmdQuickWater extends BaseCmd {

	public static final String WATER_TYPE_COLD  = "01";
	public static final String WATER_TYPE_SPARK = "02";
	public static final String WATER_TYPE_HOT   = "00";
	
	public static final String WATER_START   = "01";
	public static final String WATER_STOP    = "00";
	
	public String build(String opCmd,String waterType){
		StringBuffer buffer = new StringBuffer();
		buffer.append("1B");
		buffer.append(waterType);
		buffer.append(opCmd);
		return super.buildCmdPkg(buffer.toString());
	}
}
