package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_daylight")
public class DaylightSetting {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String start;
	@DatabaseField
	private String end;
	@DatabaseField(defaultValue = "0")
	private int usedaylight;
	@DatabaseField(defaultValue = "0") // 0:normal1:daylight
	private int timemode;

	public DaylightSetting() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DaylightSetting(String start, String end, int usedaylight, int timemode) {
		this.start = start;
		this.end = end;
		this.usedaylight = usedaylight;
		this.timemode = timemode;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getUsedaylight() {
		return usedaylight;
	}

	public void setUsedaylight(int usedaylight) {
		this.usedaylight = usedaylight;
	}

	public int getTimemode() {
		return timemode;
	}

	public void setTimemode(int timemode) {
		this.timemode = timemode;
	}
}
