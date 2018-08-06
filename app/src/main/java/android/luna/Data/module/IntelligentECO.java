package android.luna.Data.module;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_inteco")
public class IntelligentECO {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int week;
	@DatabaseField
	private int timespan;
	@DatabaseField
	private int usecount;
	@DatabaseField
	private int checkpoint;
	@DatabaseField(defaultValue = "0")
	private int  frequency;
	
	public IntelligentECO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IntelligentECO(int week, int timespan, int usecount, int checkpoint,
			int frequency) {
		super();
		this.week = week;
		this.timespan = timespan;
		this.usecount = usecount;
		this.checkpoint = checkpoint;
		this.frequency = frequency;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getTimespan() {
		return timespan;
	}
	public void setTimespan(int timespan) {
		this.timespan = timespan;
	}
	public int getUsecount() {
		return usecount;
	}
	public void setUsecount(int usecount) {
		this.usecount = usecount;
	}
	public int getCheckpoint() {
		return checkpoint;
	}
	public void setCheckpoint(int checkpoint) {
		this.checkpoint = checkpoint;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	

}
