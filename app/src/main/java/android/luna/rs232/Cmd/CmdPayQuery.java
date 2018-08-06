package android.luna.rs232.Cmd;

import android.util.Log;
import android.luna.rs232.Cmd.base.*;
public class CmdPayQuery extends BaseCmd {			
	public static final String PAY_CURRENCY_SEK = "01";
	public static final String PAY_CURRENCY_EUR = "02";
	public static final String PAY_CURRENCY_US  = "03";
	
	private static String STRING_CMD = "1C";
	
	public String buildPayQueryCMD(String currency){
		StringBuffer buffer = new StringBuffer();
		buffer.append(STRING_CMD);	
		buffer.append(currency);
		//EvoTrace.e("pay", "buildPayQueryCMD:" + buffer.toString());
		return super.buildCmdPkg(buffer.toString());
	}
}
