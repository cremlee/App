package android.luna.Data.module;
import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_filterstep")
public class FiterBrewStep implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int stepindex;
	@DatabaseField
	private int pid ;
	@DatabaseField
	private int position ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@DatabaseField
	private int wait ;
	@DatabaseField
	private int speed ;
	@DatabaseField
	private String name ;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStepindex() {
		return stepindex;
	}
	public void setStepindex(int stepindex) {
		this.stepindex = stepindex;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getWait() {
		return wait;
	}
	public void setWait(int wait) {
		this.wait = wait;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public FiterBrewStep()
	{
		
	}
	public FiterBrewStep(int stepindex,String name,int pid,int position,int wait,int speed)
	{
		this.stepindex = stepindex;
		this.pid =pid;
		this.position=position;
		this.wait=wait;
		this.speed=speed;
		this.name =name;
	}


}
