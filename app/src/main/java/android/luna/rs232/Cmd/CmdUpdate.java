package android.luna.rs232.Cmd;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.Logger.EvoTrace;
import android.util.Log;
import android.luna.rs232.Cmd.base.*;

//import evo.luna.lee.Utils.LogHelper;
public class CmdUpdate extends BaseCmd {
	public static String TYPE_DB =  "01";
	public static String TYPE_CFG = "02";
	public static String TYPE_SCR = "03";
	public static String TYPE_PRG = "80";
	
	public String BulidCmdUpdate(String type,short totalcnt,short crtcnt,String cmd)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("0D");
		buffer.append(type);
		buffer.append(AndroidUtils_Ext.oct2Hex2(totalcnt));
		buffer.append(AndroidUtils_Ext.oct2Hex2(crtcnt));
		buffer.append(cmd.replace(" ", ""));
		EvoTrace.e("update", "buildData:" + buffer.toString());
		return super.buildCmdPkg(buffer.toString());
	}
		

}
