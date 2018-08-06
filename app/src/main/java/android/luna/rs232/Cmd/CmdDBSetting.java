package android.luna.rs232.Cmd;
import android.luna.Data.module.AdditionalSetting;
import android.luna.Data.module.DisplaySetting;
import android.luna.Data.module.Soldout;
import android.luna.Data.module.TemperatureSetting;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.BaseCmd;
import java.util.Map;
public class CmdDBSetting extends BaseCmd {

	// ===============DB ID=================//
	/**
	 * Water tank block temperature
	 */
	public static final String DBID_WATER_TANK_BLOCK_TEMP = "01";
	/**
	 * Water tank idle temperature
	 */
	public static final String DBID_WATER_TANK_IDLE_TEMP = "02";
	/**
	 * led
	 */
	public static final String DBID_LED = "03";
	/**
	 * led-warn
	 */
	public static final String DBID_LED_WARN = "12";
	/**
	 * led-warn
	 */
	public static final String DBID_DOUBLE_NTC = "13";
	/**
	 * led-warn
	 */
	public static final String DBID_OVERCURRENT_WIRE = "14";
	
	/*0x15 	Beep switch*/
	
	public static final String DBID_BEEP_SWITCH = "15";
	
	

	/**
	 * Price unit
	 */
	public static final String DBID_PRICE_UNIT = "04";
	/**
	 * Start Heating Temperature
	 */
	public static final String DBID_WARNING_TEMP = "05";
	/**
	 * Stop heating temperature
	 */
	public static final String DBID_WATER_TANK_TEMP = "06";
	/**
	 * Fan speed
	 */
	public static final String DBID_FAN_SPEED = "07";
	/**
	 * Drip tray function enable
	 */
	public static final String DBID_DRIP_TRAY_FUNCTION_ENABLE = "08";
	/**
	 * Jug mode
	 */
	public static final String DBID_JUG_MODE = "09";
	/**
	 * Fan run time
	 */
	public static final String DBID_FAN_RUNTIME = "11";
	/**
	 * Grinder
	 */
	public static final String DBID_GRINDER = "0A";
	/**
	 * Canister
	 */
	public static final String DBID_CANISTER = "0B";
	/**
	 * Date-year
	 */
	public static final String DBID_DATE_YEAR = "91";
	/**
	 * Date-month & year
	 */
	public static final String DBID_DATE_MONTH_DAY = "92";
	/**
	 * Date-hour & minute
	 */
	public static final String DBID_DATE_HOUR_MINUTE = "93";
	/**
	 * Date-second
	 */
	public static final String DBID_DATE_SECOND = "94";
	/**
	 * Cup sensor enable
	 */
	public static final String DBID_CUPSENSOR = "95";
	/**
	 * Firmware Version
	 */
	public static final String DBID_FIRMWARE_VER = "96";
	/**
	 * Machine Type
	 */
	public static final String DBID_MACHINE_TYPE = "97";

	
	
	public static final String DBID_CALC_Brew = "80";
	public static final String DBID_CALC_Mix_1 = "81";
	public static final String DBID_CALC_Mix_2 = "82";
	public static final String DBID_CALC_Mix_cold = "83";
	public static final String DBID_CALC_Cold = "84";
	public static final String DBID_CALC_Hot = "85";
	public static final String DBID_CALC_Carbon  = "86";
	public static final String DBID_CALC_Grinder1  = "87";
	public static final String DBID_CALC_Grinder2 = "88";
	public static final String DBID_CALC_Canister1 = "89";
	public static final String DBID_CALC_Canister2 = "8a";
	public static final String DBID_CALC_Canister3 = "8b";
	public static final String DBID_CALC_Canister4 = "8c";
	public static final String DBID_CALC_temperature  = "8d";
	/**
	 * 
	 * @param dbId
	 * @param struction
	 *            数据结构 （4 byte）
	 * @return
	 */
	public String buildCmd(String dbId, String struction) {

		StringBuffer buffer = new StringBuffer();
		buffer.append("0C"); // cmd word
		buffer.append("01"); // DB count
		buffer.append(dbId);
		buffer.append(struction);

		return super.buildCmdPkg(buffer.toString());
	}
	
	public String buildCmdSave(String data){
		return super.buildCmdPkg(data);
	}

	/**
	 * 构建设置时间的指令
	 * 
	 * @param date
	 *            yyyyMMddHHmmss
	 * @return
	 */
	public String buildCmdTime(String date) {
		int i = 0;
		StringBuffer buffer = new StringBuffer();
		buffer.append("0C");
		buffer.append("04");
		// Year
		buffer.append(DBID_DATE_YEAR);
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		// month & day
		buffer.append(DBID_DATE_MONTH_DAY);
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		// hour & minute
		buffer.append(DBID_DATE_HOUR_MINUTE);
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		// second
		buffer.append(DBID_DATE_SECOND);
		buffer.append("0000");
		buffer.append(string2Ascii(date.substring(i, i += 1)));
		buffer.append(string2Ascii(date.substring(i, i += 1)));

		return super.buildCmdPkg(buffer.toString());
	}

	private String string2Ascii(String arg) {
		return String.valueOf(Integer.valueOf(arg) + 30);
	}

	
	/**
	 * 读取Calibration value
	 * @return
	 */
	public String readCalibration(String type){
		StringBuffer buffer = new StringBuffer();
		buffer.append("14");
		buffer.append("01");// DB Count
		buffer.append(type);
		return super.buildCmdPkg(buffer.toString());
	}
	
	/**
	 * 读取下位机时间
	 * 
	 * @return
	 */
	public String readDeviceTime() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("14");
		buffer.append("04");// db count
		buffer.append(DBID_DATE_YEAR);// year
		buffer.append(DBID_DATE_MONTH_DAY);// month & day
		buffer.append(DBID_DATE_HOUR_MINUTE);// hour & minute
		buffer.append(DBID_DATE_SECOND);// second
		return super.buildCmdPkg(buffer.toString());
	}
	
	/**
	 * 读取下位机固件版本
	 * @return
	 */
	public String readFirmwareVer(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("14");
		buffer.append("01");// DB Count
		buffer.append(DBID_FIRMWARE_VER);
		return super.buildCmdPkg(buffer.toString());
	}

	public String readMachineType() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("14");
		buffer.append("01");// DB Count
		buffer.append(DBID_MACHINE_TYPE);
		return super.buildCmdPkg(buffer.toString());
	}

	public String readTempOffset() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("14");
		buffer.append("01");// DB Count
		buffer.append(DBID_CALC_temperature);
		return super.buildCmdPkg(buffer.toString());
	}
	
	public String buildFactoryReset() {

		StringBuffer buffer = new StringBuffer();
		buffer.append("0C");
		buffer.append("09");// DB Count
		buffer.append(DBID_WATER_TANK_BLOCK_TEMP); // Water tank block temperature
		buffer.append("00000050"); // 80
		buffer.append(DBID_WATER_TANK_IDLE_TEMP);// Water tank idle temperature
		buffer.append("0000003C"); // 60
		buffer.append(DBID_LED);// LED
//		buffer.append("05010101");
		buffer.append("01030101");
		buffer.append(DBID_PRICE_UNIT);// Price unit
		buffer.append("00000000");
		buffer.append(DBID_WARNING_TEMP);// Start Heating Temperature
		buffer.append("00000056");
		buffer.append(DBID_WATER_TANK_TEMP);// Stop heating temperature
		buffer.append("00000060"); // 96
		buffer.append(DBID_FAN_SPEED); // Fan speed
		buffer.append("00000064");
		buffer.append(DBID_DRIP_TRAY_FUNCTION_ENABLE);// Drip tray function enable
		buffer.append("00000001");
		buffer.append(DBID_JUG_MODE);// Jug mode
		buffer.append("00000000");
		return super.buildCmdPkg(buffer.toString());
	}

	public String buildImportDBTemp(TemperatureSetting ts)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("0C");
		buffer.append("04");// DB Count
		buffer.append(DBID_WATER_TANK_BLOCK_TEMP); // Water tank block temperature
		buffer.append("000000"+ AndroidUtils_Ext.oct2Hex(ts.getBlockingTemp())); // 80
		buffer.append(DBID_WATER_TANK_IDLE_TEMP);// Water tank idle temperature
		buffer.append("000000"+AndroidUtils_Ext.oct2Hex(ts.getEnergySavingTemp())); // 60
		buffer.append(DBID_WARNING_TEMP);// Start Heating Temperature
		buffer.append("000000"+AndroidUtils_Ext.oct2Hex(ts.getWarningTemp()));
		buffer.append(DBID_WATER_TANK_TEMP);// Stop heating temperature
		buffer.append("000000"+AndroidUtils_Ext.oct2Hex(ts.getTankTemp())); // 96
		return super.buildCmdPkg(buffer.toString());
	}
	
	public String buildImportDBFan(AdditionalSetting as)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("0C");
		buffer.append("03");// DB Count
		buffer.append(CmdDBSetting.DBID_FAN_SPEED);
		buffer.append("000000" + AndroidUtils_Ext.oct2Hex(as.getFanSpeed()));
		buffer.append(CmdDBSetting.DBID_FAN_RUNTIME);
		buffer.append(as.getFanRunContinuously()==1?"01":"FF" + String.format("%06X",as.getFanRunTime() ));
		buffer.append(CmdDBSetting.DBID_BEEP_SWITCH);
		buffer.append("000000" + AndroidUtils_Ext.oct2Hex(as.getBeepmode()));
		return super.buildCmdPkg(buffer.toString());
	}
	
	public String buildImportDBLED(DisplaySetting ds, DisplaySetting dsw)
	{
		String ledwclor = (dsw==null?"1":dsw.getLedColor());
		StringBuffer buffer = new StringBuffer();
		buffer.append("0C");
		buffer.append("02");// DB Count
		buffer.append(CmdDBSetting.DBID_LED);
		buffer.append("0"+ds.getLedMode()+"0"+ds.getLedColor()+"0"+ds.getLedBrightness()+"01");			
		buffer.append(CmdDBSetting.DBID_LED_WARN);
		buffer.append("01"+"0"+ledwclor+"0101");
		return super.buildCmdPkg(buffer.toString());
	}
	public String buildImportDBSoldout(int type, Soldout sd, Map<String, Integer> mapcan)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("0C");
		buffer.append("02");// DB Count
		buffer.append(CmdDBSetting.DBID_GRINDER);
		buffer.append("81000000");
		//buffer.append((type==App.MACHINE_TYPE_MF13?AndroidUtils.oct2Hex(129):AndroidUtils.oct2Hex(130)) + "81000000");
		int canister2 = Integer.valueOf(sd.getCanister2());
		int canister3 = Integer.valueOf(sd.getCanister3());
		int canister4 = Integer.valueOf(sd.getCanister4());
		String struction = "00" + AndroidUtils_Ext.oct2Hex(canister2) + AndroidUtils_Ext.oct2Hex(canister3) + AndroidUtils_Ext.oct2Hex(canister4);
		buffer.append(CmdDBSetting.DBID_CANISTER);
		buffer.append(struction);	
		return super.buildCmdPkg(buffer.toString());
	} 
	
}
	

