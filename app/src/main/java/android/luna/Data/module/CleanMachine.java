package android.luna.Data.module;

import java.io.Serializable;

import android.content.Context;
import evo.luna.android.R;

public class CleanMachine implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static int TIME_DAILY_CLEAN = 35 * 1000;
	public final static int TIME_WEEKLY_CLEAN = (8 * 60 + 29) * 1000;
	public final static int TIME_WATERFILTER_FLUSH = (1 * 60 + 11) * 1000;
	public final static int TIME_FLUSH_BREW = 35 * 1000;
	public final static int TIME_CLEANBREW = (8 * 60+28) * 1000;
	public final static int TIME_CLEANMIXER = 10 * 1000;
	public final static int TIME_CLEAN_VALVE = 18 * 1000;

	public final static int TIME_CLEAN_MILK_SHOT = 15 * 1000;
	public final static int TIME_CLEAN_MILK_CLEAN = 7 * 60 * 1000;

	// **********对应命令的id***********//
	public static final int CLEAN_BREWER = 0;
	public static final int CLEAN_MILK = 1;
	public static final int CLEAN_GRINDER = 2;
	public static final int CLEAN_MIXER = 3;
	public static final int CLEAN_MILK_PRIME = 4;
	public static final int CLEAN_DAILY = 5;
	public static final int CLEAN_SHOCK_VALVE = 6;
	public static final int CLEAN_WEEKLY = 7;
	public static final int CLEAN_WATER_FILTER_FLUSH = 8;
	public static final int CLEAN_FLUSH_BREWER = 9;
	public static final int CLEAN_OPEN_BREWER = 0x0a;
	public static final int CLEAN_OPEN_VALVE = 0x0b;
	public static final int CLEAN_FMU_SHOT = 0x11;
	public static final int CLEAN_FMU_CLEAN = 0x10;
	
	private int type;
	private String name;
	private int time;

	public CleanMachine() {
	}

	public CleanMachine(int type, String name, int time) {
		super();
		this.type = type;
		this.name = name;
		this.time = time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName(Context context) {
		switch (type)
		{
		case CleanMachine.CLEAN_DAILY:
			name = context.getString(R.string.warning_daily_clean);
			break;
		case CleanMachine.CLEAN_WEEKLY:
			name = context.getString(R.string.weekly_clean);
			break;
		case CleanMachine.CLEAN_WATER_FILTER_FLUSH:
			name = context.getString(R.string.counter_waterflush);
			break;
		case CleanMachine.CLEAN_FLUSH_BREWER:
			name = context.getString(R.string.counter_brewflush);
			break;
		case CleanMachine.CLEAN_BREWER:
			name = context.getString(R.string.counter_cleanbrew);
			break;
		case CleanMachine.CLEAN_MIXER:
			name = context.getString(R.string.counter_cleanmix);
			break;
		case CleanMachine.CLEAN_SHOCK_VALVE:
			name = context.getString(R.string.counter_cleanvalve);
			break;
		case CleanMachine.CLEAN_OPEN_BREWER:
			name = context.getString(R.string.counter_openbrew);
			break;
		case CleanMachine.CLEAN_GRINDER:
			name = context.getString(R.string.counter_cleangrd);
			break;
		case CleanMachine.CLEAN_OPEN_VALVE:
			name = context.getString(R.string.counter_openvalve);
			break;
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

}
