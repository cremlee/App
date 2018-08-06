package android.luna.rs232.Cmd;

import android.luna.Utils.Logger.EvoTrace;
import android.util.Log;
import android.luna.rs232.Cmd.base.*;
public class CmdPayCharge extends BaseCmd {

	public static final String OP_RECHARGE = "01";
	public static final String OP_REFUND = "02";
		
	public static final String PAY_CURRENCY_SEK = "01";
	public static final String PAY_CURRENCY_EUR = "02";
	public static final String PAY_CURRENCY_US  = "03";
	
	private static String STRING_CMD = "1A";
	
	public String buildPayChargeCmd(String operater,String current,int value){
		StringBuffer buffer = new StringBuffer();
		buffer.append(STRING_CMD);
		buffer.append(operater);
		buffer.append(current);
		buffer.append(String.format("%08x", value));
		EvoTrace.e("pay", "buildPayChargeCmd:" + buffer.toString());
		return super.buildCmdPkg(buffer.toString(),"300");
	}
}
