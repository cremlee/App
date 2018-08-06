package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 用来存放Scheuler临时事件
 * @author tangcheng
 */
@DatabaseTable(tableName="tb_scheduler_additional")
public class SchedulerAdditional {

	@DatabaseField(generatedId=true)
	private int id;
	@DatabaseField
	private int eventType;
	@DatabaseField
	private int count; // 允许延迟多少次
	
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}


}
