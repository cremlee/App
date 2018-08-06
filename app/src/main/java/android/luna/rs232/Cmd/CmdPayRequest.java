package android.luna.rs232.Cmd;

import android.luna.Utils.Logger.EvoTrace;
import android.util.Log;
import android.luna.rs232.Cmd.base.*;
public class CmdPayRequest extends BaseCmd {

	public static final String PAY_START = "01";
	public static final String PAY_STOP = "02";
	public static final String PAY_SUCCESS = "03";
	public static final String PAY_FAIL = "04";
	
	public static final String PAY_TYPE_NAYAX = "01";
	public static final String PAY_TYPE_COGES = "02";
	public static final String PAY_TYPE_COIN  = "03";
	
	public static final String PAY_CURRENCY_SEK = "01";
	public static final String PAY_CURRENCY_EUR = "02";
	public static final String PAY_CURRENCY_US  = "03";
	
	public String buildPayRequestCmd(String operater,String mode,String current,int value){
		StringBuffer buffer = new StringBuffer();
		buffer.append("19");
		buffer.append(operater);
		buffer.append(mode);
		buffer.append(current);
		buffer.append(String.format("%08x", value));
		EvoTrace.e("pay", "buildPayRequestCmd:" + buffer.toString());
		return super.buildCmdPkg(buffer.toString(),"300");
	}
	public static String getCurrency(int value)
	{
		String ret ="01";
		switch(value)
		{
			case  1:
				ret =PAY_CURRENCY_SEK;
				break;
			case 2:
				ret = PAY_CURRENCY_EUR;
				break;
			case 3:
				ret =PAY_CURRENCY_US;
				break;
		}
		return  ret;


	}
}
