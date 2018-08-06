package android.luna.rs232.Cmd;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.UpdateUi.CmdNullException;
import android.luna.Data.DAO.DataHelper;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.*;

import android.util.Log;

/**
 * 菜单制作
 * 
 * @author Administrator
 * 
 */
public class CmdMakeBeverage extends BaseCmd{
	private static final String TAG = "CmdMakeBeverage";
	private DataHelper dataHelper;
	//private Context context;
	//private CremApp app;
	public CmdMakeBeverage() {

		//this.app =app;
	}

	/*public DataHelper getHelper() {
		if (dataHelper == null) {
			dataHelper = app.getHelper();
		}
		return dataHelper;
	}*/
	public String buildCmd(String operaterCmd ,String beveragestring)
	{
		if(beveragestring==null)
			return null;
		StringBuffer buffer = new StringBuffer();
		buffer.append("0008");                                                            // Cmd word
		/* Cmd parameter(start) */
		buffer.append(operaterCmd);
		buffer.append(beveragestring);
		return super.buildCmdPkg(buffer.toString());
	}
}
