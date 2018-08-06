package android.luna.Data.module;

import java.io.Serializable;

import android.content.Context;
import evo.luna.android.R;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable(tableName = "tb_hardware_test")
public class HardwareTest implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int TYPE_GRINDER1 = 1;
	public static final int TYPE_CANISTER1 = 2;
	public static final int TYPE_CANISTER2 = 3;
	public static final int TYPE_CANISTER3 = 4;
	public static final int TYPE_MIXER1_WATER_VALVE = 5;
	public static final int TYPE_MIXER2_WATER_VALVE = 6;
	public static final int TYPE_MIXER1_WHIPPER = 7;
	public static final int TYPE_MIXER2_WHIPPER = 8;
	public static final int TYPE_BREW_MOTOR = 9;
	public static final int TYPE_FAN = 10;
	public static final int TYPE_HOT_WATER_INLET_VALVE = 11;
	public static final int TYPE_COLD_WATER_INLET_VALVE = 12;
	public static final int TYPE_HOT_WATER_OUT_VALVE = 13;
	public static final int TYPE_BREW_HOT_WATER_VALVE = 14;
	public static final int TYPE_COLD_WATER_OUT_VALVE = 15;
	public static final int TYPE_CARBONIC_WATER_OUT_VALVE = 16;
	public static final int TYPE_MIX_COLD_WATER_VALVE = 17;
	public static final int TYPE_GRINDER2 = 18;
	public static final int TYPE_CANISTER4 = 19;
	public static final int TYPE_LED_TEST = 20;
	public static final int TYPE_BEAN_HOPPER = 21;
	public static final int TYPE_HEATER = 22;
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int value; // 0 关，1 开
	@DatabaseField
	private int cmdId;

	public HardwareTest() {
	}

	public HardwareTest(String name, int value, int cmdId) {
		super();
		this.name = name;
		this.value = value;
		this.cmdId = cmdId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName(Context context) {
		if(this.cmdId == HardwareTest.TYPE_GRINDER1)
		{
			name = context.getString(R.string.cal_watertank_txt87);
		}
		else if(this.cmdId == HardwareTest.TYPE_CANISTER2)
		{
			name = context.getString(R.string.cal_watertank_txt8a);
		}
		else if(this.cmdId == HardwareTest.TYPE_CANISTER3)
		{
			name = context.getString(R.string.cal_watertank_txt8b);
		}
		else if(this.cmdId == HardwareTest.TYPE_CANISTER4)
		{
			name = context.getString(R.string.cal_watertank_txt8c);
		}
		else if(this.cmdId == HardwareTest.TYPE_MIXER1_WATER_VALVE)
		{
			name = context.getString(R.string.cal_watertank_txt81);
		}
		else if(this.cmdId == HardwareTest.TYPE_MIXER2_WATER_VALVE)
		{
			name = context.getString(R.string.cal_watertank_txt82);
		}
		else if(this.cmdId == HardwareTest.TYPE_MIXER1_WHIPPER)
		{
			name = context.getString(R.string.tst_mix1whp);
		}
		else if(this.cmdId == HardwareTest.TYPE_MIXER2_WHIPPER)
		{
			name = context.getString(R.string.tst_mix2whp);
		}
		else if(this.cmdId == HardwareTest.TYPE_BREW_MOTOR)
		{
			name = context.getString(R.string.tst_brewmtr);
		}
		else if(this.cmdId == HardwareTest.TYPE_FAN)
		{
			name = context.getString(R.string.tst_fan);
		}
		else if(this.cmdId == HardwareTest.TYPE_HOT_WATER_INLET_VALVE)
		{
			name = context.getString(R.string.tst_valve_inlet);
		}
		else if(this.cmdId == HardwareTest.TYPE_COLD_WATER_INLET_VALVE)
		{
			name = context.getString(R.string.tst_valve_inletcd);
		}
		else if(this.cmdId == HardwareTest.TYPE_HOT_WATER_OUT_VALVE)
		{
			name = context.getString(R.string.cal_watertank_txt85);
		}
		else if(this.cmdId == HardwareTest.TYPE_COLD_WATER_OUT_VALVE)
		{
			name = context.getString(R.string.cal_watertank_txt84);
		}
		else if(this.cmdId == HardwareTest.TYPE_BREW_HOT_WATER_VALVE)
		{
			name = context.getString(R.string.cal_watertank_txt80);
		}
		else if(this.cmdId == HardwareTest.TYPE_CARBONIC_WATER_OUT_VALVE)
		{
			name = context.getString(R.string.cal_watertank_txt86);
		}
		else if(this.cmdId == HardwareTest.TYPE_LED_TEST)
		{
			name = context.getString(R.string.tst_led);
		}
		else if(this.cmdId == HardwareTest.TYPE_BEAN_HOPPER)
		{
			name = context.getString(R.string.tst_hopper);
		}
		else if(this.cmdId == HardwareTest.TYPE_HEATER)
		{
			name = context.getString(R.string.tst_heater);
		}
		else if(this.cmdId == HardwareTest.TYPE_MIX_COLD_WATER_VALVE)
		{
			name = context.getString(R.string.tst_mix_cold);
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getCmdId() {
		return cmdId;
	}

	public void setCmdId(int cmdId) {
		this.cmdId = cmdId;
	}

	@Override
	public String toString() {
		return "HardwareTest [id=" + id + ", name=" + name + ", value=" + value + ", cmdId=" + cmdId + "]";
	}

}
