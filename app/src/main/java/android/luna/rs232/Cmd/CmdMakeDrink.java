package android.luna.rs232.Cmd;

import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.rs232.Cmd.base.*;

import android.util.Log;



public class CmdMakeDrink extends BaseCmd {

	public static final String OPERATE_START = "01";
	public static final String OPERATE_STOP =  "00";
	public static final String OPERATE_ABORT =  "02";
	/**
	 * 3.11 Make drink request cmd (饮料制作请求0x05)
	 * 
	 * @param beverageId
	 * @return
	 */
	public String buildMakeDrinkRequestData(int beverageId) {
		return super.buildCmdPkg("05" + AndroidUtils_Ext.oct2Hex2(beverageId));
	}
	/**
	 * 饮料制作
	 * 
	 * @param pid
	 * @param operate
	 * @param strength
	 * @param volume
	 * @param milk
	 * @param topping
	 * @param chocolate
	 * @param sugar
	 * @return
	 */
	/*public String buildMakeDrinkCmd(int pid, String operate, int strength, int volume, int milk, int topping, int chocolate, int sugar,int cups,SpiderDatastruct spddate) {
		*//*
		4 Strength
		5 Volume
		6 Milk
		7 Sugar
		8 Cups
		9 Topping
		*//*
		if(pid == 0){
			EvoTrace.e("CmdMakeDrink", "pid is null!! ERROR!!!!!");
		}			
		StringBuffer buffer = new StringBuffer();
		buffer.append("06");
		buffer.append(AndroidUtils_Ext.oct2Hex2(pid));
		buffer.append(beverageSettingValue(strength));
		buffer.append(beverageSettingValue(volume));
		buffer.append(beverageSettingValue(milk));
//		buffer.append(beverageSettingValue(chocolate));
		buffer.append(beverageSettingValue(sugar));
		buffer.append(AndroidUtils_Ext.oct2Hex(cups));
		buffer.append(beverageSettingValue(topping));
		buffer.append(operate);
		buffer.append(AndroidUtils_Ext.oct2Hex2(spddate.getGrindercount()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(spddate.getPrebrewtime()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(spddate.getExtractiontime()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(spddate.getDecompresstime()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(spddate.getAeration()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(spddate.getPress()));
		return super.buildCmdPkg(buffer.toString());

	}*/

	public String buildMakeDrinkCmd(int pid, String operate, int strength, int volume, int milk, int topping,int sugar,int cups) {
		/*
		4 Strength
		5 Volume
		6 Milk
		7 Sugar
		8 Cups
		9 Topping
		*/		
		if(pid == 0){
			EvoTrace.e("ack", "pid is null!! ERROR!!!!!");
		}			
		StringBuffer buffer = new StringBuffer();
		buffer.append("0006");
		buffer.append(AndroidUtils_Ext.oct2Hex2(pid));
		buffer.append(beverageSettingValue(strength));
		buffer.append(beverageSettingValue(volume));
		buffer.append(beverageSettingValue(milk));
		buffer.append(beverageSettingValue(sugar));
		buffer.append(AndroidUtils_Ext.oct2Hex(cups));
		buffer.append(beverageSettingValue(topping));
		buffer.append(operate);
		buffer.append("0000");
		buffer.append("0000");
		buffer.append("0000");
		buffer.append("0000");
		buffer.append("0000");
		buffer.append("0000");
		return super.buildCmdPkg(buffer.toString());

	}
	public String buildMakeDrinkFinishedCmd(int beveragePid) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("000f").append(AndroidUtils_Ext.oct2Hex2(beveragePid));
		return super.buildCmdPkg(buffer.toString());
	}

	private static String beverageSettingValue(int value) {
		switch (value) {
		case 1:
			return "02";
		case 2:
			return "01";
		case 3:
			return "80";
		case 4:
			return "81";
		case 5:
			return "82";
		default:
			break;
		}
		return "80";
	}
}
