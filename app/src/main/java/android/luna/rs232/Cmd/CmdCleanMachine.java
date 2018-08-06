package android.luna.rs232.Cmd;

import android.luna.Data.module.CleanActionItem;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.BaseCmd;
import android.util.Log;

import java.util.List;

public class CmdCleanMachine extends BaseCmd {
	public String buildCmdStart(List<CleanActionItem> actions){
		StringBuffer buffer = new StringBuffer();
		buffer.append("0002");
		buffer.append(AndroidUtils_Ext.oct2Hex(actions.size()));
		for (CleanActionItem actionItem:actions) {
			buffer.append(AndroidUtils_Ext.oct2Hex4(actionItem.getDeviceid()));
			buffer.append(AndroidUtils_Ext.oct2Hex(actionItem.getAction()));
		}
		return super.buildCmdPkg(buffer.toString());
	}
	public String buildCmdStart(CleanActionItem action){
		StringBuffer buffer = new StringBuffer();
		buffer.append("0002");
		buffer.append(AndroidUtils_Ext.oct2Hex(1));
		buffer.append(AndroidUtils_Ext.oct2Hex4(action.getDeviceid()));
		buffer.append(AndroidUtils_Ext.oct2Hex(action.getAction()));
		return super.buildCmdPkg(buffer.toString());
	}
	public String buildCmdStop(List<CleanActionItem> actions){
		StringBuffer buffer = new StringBuffer();
		buffer.append("0003");
		buffer.append(AndroidUtils_Ext.oct2Hex(actions.size()));
		for (CleanActionItem actionItem:actions) {
			buffer.append(AndroidUtils_Ext.oct2Hex4(actionItem.getDeviceid()));
			buffer.append(AndroidUtils_Ext.oct2Hex(actionItem.getAction()));
		}
		return super.buildCmdPkg(buffer.toString());
	}
	public String buildCmdStop(CleanActionItem action){
		StringBuffer buffer = new StringBuffer();
		buffer.append("0003");
		buffer.append(AndroidUtils_Ext.oct2Hex(1));
		buffer.append(AndroidUtils_Ext.oct2Hex4(action.getDeviceid()));
		buffer.append(AndroidUtils_Ext.oct2Hex(action.getAction()));
		return super.buildCmdPkg(buffer.toString());
	}
}
