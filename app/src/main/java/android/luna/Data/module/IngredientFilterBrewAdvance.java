package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Administrator
 * 
 */
@DatabaseTable(tableName = "tb_ingredient_filterbrewadvance")
public class IngredientFilterBrewAdvance implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int pid;
	@DatabaseField(defaultValue="0")
	private int phaseNumber = 0;// map to filterbrewstep
	@DatabaseField(defaultValue="155")
	private int waterVolume = 155;
	@DatabaseField(defaultValue="129")
	private int grinder1Type = 0x81; // key
	@DatabaseField
	private float grinder1Amount;
	@DatabaseField
	private int grinder2Type;
	@DatabaseField
	private float grinder2Amount;
	@DatabaseField(defaultValue="0")
	private int totalTime=0;
	public int getWaterDispenDelay() {
		return waterDispenDelay;
	}

	public void setWaterDispenDelay(int waterDispenDelay) {
		this.waterDispenDelay = waterDispenDelay;
	}

	public int getGrinder1DispenDelay() {
		return grinder1DispenDelay;
	}

	public void setGrinder1DispenDelay(int grinder1DispenDelay) {
		this.grinder1DispenDelay = grinder1DispenDelay;
	}

	public int getGrinder2DispenDelay() {
		return grinder2DispenDelay;
	}

	public void setGrinder2DispenDelay(int grinder2DispenDelay) {
		this.grinder2DispenDelay = grinder2DispenDelay;
	}
	@DatabaseField(defaultValue="0")
	private int isDefault = 0;
	@DatabaseField(defaultValue="0")
	private int waterDispenDelay=0;
	@DatabaseField(defaultValue="0")
	private int grinder1DispenDelay=0;
	@DatabaseField(defaultValue="0")
	private int grinder2DispenDelay=0;
	//1:new 2:ok 3:update
	@DatabaseField(defaultValue = "0")
	private int createstatus=0;
	
	
	public int getCreatestatus() {
		return createstatus;
	}

	public void setCreatestatus(int createstatus) {
		this.createstatus = createstatus;
	}

	public IngredientFilterBrewAdvance(int id, String name, int pid,
			int phaseNumber, int waterVolume, int grinder1Type,
			float grinder1Amount, int grinder2Type, float grinder2Amount,
			int totalTime, int isDefault, int waterDispenDelay,
			int grinder1DispenDelay, int grinder2DispenDelay) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.phaseNumber = phaseNumber;
		this.waterVolume = waterVolume;
		this.grinder1Type = grinder1Type;
		this.grinder1Amount = grinder1Amount;
		this.grinder2Type = grinder2Type;
		this.grinder2Amount = grinder2Amount;
		this.totalTime = totalTime;
		this.isDefault = isDefault;
		this.waterDispenDelay = waterDispenDelay;
		this.grinder1DispenDelay = grinder1DispenDelay;
		this.grinder2DispenDelay = grinder2DispenDelay;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getPhaseNumber() {
		return phaseNumber;
	}

	public void setPhaseNumber(int phaseNumber) {
		this.phaseNumber = phaseNumber;
	}

	public int getWaterVolume() {
		return waterVolume;
	}

	public void setWaterVolume(int waterVolume) {
		this.waterVolume = waterVolume;
	}

	public int getGrinder1Type() {
		return grinder1Type;
	}

	public void setGrinder1Type(int grinder1Type) {
		this.grinder1Type = grinder1Type;
	}

	public float getGrinder1Amount() {
		return grinder1Amount;
	}

	public void setGrinder1Amount(float grinder1Amount) {
		this.grinder1Amount = grinder1Amount;
	}

	public int getGrinder2Type() {
		return grinder2Type;
	}

	public void setGrinder2Type(int grinder2Type) {
		this.grinder2Type = grinder2Type;
	}

	public float getGrinder2Amount() {
		return grinder2Amount;
	}

	public void setGrinder2Amount(float grinder2Amount) {
		this.grinder2Amount = grinder2Amount;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	

	public IngredientFilterBrewAdvance() {
	}

	

}
