package android.luna.Data.module;

import java.io.Serializable;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import evo.luna.android.R;

@DatabaseTable(tableName="tb_scheduler_detail")
public class SchedulerDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private int schedulerId;
	@DatabaseField
	private String name;
	@DatabaseField
	private int hour;
	@DatabaseField
	private int week;
	@DatabaseField
	private int persistFlag; // 0,开始，1中间或者普通的，2结束

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(int schedulerId) {
		this.schedulerId = schedulerId;
	}

	public String getName(Context context) {
		switch(name)
		{
			case "Daily Clean":
				return context.getString(R.string.sch_daily);
			case "Weekly Clean":
				return context.getString(R.string.sch_weekly);
			case "Energy Saving":
				return context.getString(R.string.sch_eco);
			case "Wake Up":
				return context.getString(R.string.sch_wakeup);
		}
		
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getPersistFlag() {
		return persistFlag;
	}

	public void setPersistFlag(int persistFlag) {
		this.persistFlag = persistFlag;
	}

}
