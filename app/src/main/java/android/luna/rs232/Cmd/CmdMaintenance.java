package android.luna.rs232.Cmd;
import android.luna.rs232.Cmd.base.*;
public class CmdMaintenance extends BaseCmd {

	/**
	 * Total water outlet volume
	 */
	public static final String MAINTENANCE_TOTAL_WATER = "01";
	/**
	 * Hot water out valve counts
	 */
	public static final String MAINTENANCE_HOT_WATER = "02";
	/**
	 * Cold water out valve counts
	 */
	public static final String MAINTENANCE_COLD_WATER = "03";
	/**
	 * Carbonic water out valve counts
	 */
	public static final String MAINTENANCE_CARBONIC_WATER = "04";
	/**
	 * Mix cold water valve counts
	 */
	public static final String MAINTENANCE_MIX_COLD = "05";
	/**
	 * brew hot water valve counts
	 */
	public static final String MAINTENANCE_BREW_HOT_WATER = "06";
	/**
	 * brew motor counts
	 */
	public static final String MAINTENANCE_BREW_MOTOR = "07";
	/**
	 * MIXER1 water valve counts
	 */
	public static final String MAINTENANCE_MIXER1_WATER = "08";
	/**
	 * MIXER2 water valve counts
	 */
	public static final String MAINTENANCE_MIXER2_WATER = "09";
	/**
	 * Grinder running time (s)
	 */
	public static final String MAINTENANCE_GRINDER_RUNNING = "0A";
	/**
	 * Canister1 running time (s)
	 */
	public static final String MAINTENANCE_CANISTER1_RUNNING = "0B";
	/**
	 * Canister2 running time (s)
	 */
	public static final String MAINTENANCE_CANISTER2_RUNNING = "0C";
	/**
	 * Canister3 running time (s)
	 */
	public static final String MAINTENANCE_CANISTER3_RUNNING = "0D";
	/**
	 * Mixer1 whipper running time(s)
	 */
	public static final String MAINTENANCE_MIXER1_WHIPPER_RUNNING = "0E";
	/**
	 * Mixer2 whipper running time(s)
	 */
	public static final String MAINTENANCE_MIXER2_WHIPPER_RUNNING = "0F";

	/*0x10	Grinder2 running time (s)
	public static final String MAINTENANCE_GRINDER2_TIMES  = "10";
	0x11	Canister4 running time (s)
	public static final String MAINTENANCE_CANISTER4_TIMES = "11";
	0x12	Water filter (ml)
	public static final String MAINTENANCE_WATER_FILTERS   = "12";*/
	
	
	
	public String buildResetMsgCmd(String cmdid) {
		return super.buildCmdPkg("1301"+cmdid);
	}

	/**
	 * 查询所有信息
	 * 
	 * @return
	 */
	public String buildAllMsgCmd() {
		return super.buildCmdPkg("1000");
	}

	/**
	 * 查询指定ID的信息
	 * @param strings
	 * @return
	 */
	public String buildMsgCmd(String... strings) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("10");
		if (strings != null && strings.length > 0) {
			for (int i = 0; i < strings.length; i++) {
				buffer.append(strings[i]);
			}
		}
		return super.buildCmdPkg(buffer.toString());
	}
	
	public String buildMaintenceSigleCmd(String cmdid)
	{
		return super.buildCmdPkg("1001"+cmdid);
	}
}
