package android.luna.rs232.Cmd;
import android.util.Log;
import android.luna.rs232.Cmd.base.*;
public class CmdStorageMgt extends BaseCmd {
	public static String TYPE_DELETE_ALL=  "01";
	public static String TYPE_DELETE_INGREDIENT = "02";
	public static String TYPE_DELETE_BEVERAGE = "03";
	
	public String BulidCmd(String type)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("1E");
		buffer.append(type);
		return super.buildCmdPkg(buffer.toString());
	}
		

}
