package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_scheduler")
public class Scheduler implements Serializable {

	public static final int TYPE_DAILY_CLEAN = 1;
	public static final int TYPE_WEEKLY_CLEAN = 2;
	public static final int TYPE_ENERGY_SAVING = 3;
	public static final int TYPE_FREE_DRINK = 4;
	
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int eventType; // 事件类型
	@DatabaseField
	private String startTime; // 从几点开始
	@DatabaseField
	private int persistTime; // （EnerySaving特有）持续多长时间
	@DatabaseField
	private int week; // 是星期几
	@DatabaseField
	private int offvalue; // 折扣 0：free 1：50 %off
	
	
	public Scheduler() {
	}
		
	public Scheduler(int eventType, String startTime, int persistTime,
			int week, int offvalue) {
		super();
		this.eventType = eventType;
		this.startTime = startTime;
		this.persistTime = persistTime;
		this.week = week;
		this.offvalue = offvalue;
	}

	public int getOffvalue() {
		return offvalue;
	}

	public void setOffvalue(int offvalue) {
		this.offvalue = offvalue;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getPersistTime() {
		return persistTime;
	}

	public void setPersistTime(int persistTime) {
		this.persistTime = persistTime;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	@Override
	public String toString() {
		return "Scheduler [id=" + id + ", eventType=" + eventType + ", startTime=" + startTime + ", persistTime="
				+ persistTime + ", week=" + week + "]";
	}

	public String getWeekName()
	{
		String ret ="";
		switch (this.week)
		{
			case 1:
				ret = "Mon";
				break;
			case 2:
				ret = "Tue";
				break;
			case 3:
				ret = "Wed";
				break;
			case 4:
				ret = "Thu";
				break;
			case 5:
				ret = "Fri";
				break;
			case 6:
				ret = "Sat";
				break;
			case 7:
				ret = "Sun";
				break;
		}
		return ret;
	}
}
