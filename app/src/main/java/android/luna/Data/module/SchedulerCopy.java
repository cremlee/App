package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_schedulercopy")
public class SchedulerCopy implements Serializable {

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


	public SchedulerCopy() {
	}

	public SchedulerCopy(int eventType, String startTime, int persistTime,
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

}
