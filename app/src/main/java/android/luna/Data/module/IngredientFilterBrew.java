package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Administrator
 * 
 */
@DatabaseTable(tableName = "tb_ingredient_filterbrew")
public class IngredientFilterBrew implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int pid;
	@DatabaseField(defaultValue="5")
	private int phaseNumber = 5;// 固定5
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
	@DatabaseField(defaultValue="40")
	private int tmPre13 = 40; // TM_pre FM13/FM04两种机器不一样
	@DatabaseField(defaultValue="80")
	private int tmPre04 = 80;
	@DatabaseField(defaultValue="100")
	private int extractionTime=100;
	@DatabaseField(defaultValue="45")
	private int decompressTime = 45;
	@DatabaseField(defaultValue="80")
	private int openDelayTime = 80;
	@DatabaseField(defaultValue="40")
	private int preBrewPosition = 40;
	@DatabaseField(defaultValue="45")
	private int decompressPosition = 45;
	@DatabaseField(defaultValue="28000")
	private int totalTime=28000;
	@DatabaseField(defaultValue="0")
	private int isDefault = 0;
	//1:new 2:ok 3:update
	@DatabaseField(defaultValue = "1")
	private int createstatus=1;


	public void setChanged(boolean changed) {
		IsChanged = changed;
        if(createstatus==2)
        {
            createstatus =3;
        }
	}

	private boolean IsChanged=false;


	public int getCreatestatus() {
		return createstatus;
	}

	public void setCreatestatus(int createstatus) {
		this.createstatus = createstatus;
	}

	public IngredientFilterBrew() {

	}

	public IngredientFilterBrew(int pid, String name, int phaseNumber,
			int waterVolume, int grinder1Type, float grinder1Amount,
			int grinder2Type, float grinder2Amount, int tmPre13, int tmPre04,
			int extractionTime, int decompressTime, int openDelayTime,
			int preBrewPosition, int decompressPosition, int totalTime,
			int isDefault, int createstatus) {
		super();

		this.name = name;
		this.pid = pid;
		this.phaseNumber = phaseNumber;
		this.waterVolume = waterVolume;
		this.grinder1Type = grinder1Type;
		this.grinder1Amount = grinder1Amount;
		this.grinder2Type = grinder2Type;
		this.grinder2Amount = grinder2Amount;
		this.tmPre13 = tmPre13;
		this.tmPre04 = tmPre04;
		this.extractionTime = extractionTime;
		this.decompressTime = decompressTime;
		this.openDelayTime = openDelayTime;
		this.preBrewPosition = preBrewPosition;
		this.decompressPosition = decompressPosition;
		this.totalTime = totalTime;
		this.isDefault = isDefault;
		this.createstatus = createstatus;
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

	public float getGrinder1Amount() {
		return grinder1Amount;
	}

	public void setGrinder1Amount(float grinder1Amount) {
		this.grinder1Amount = grinder1Amount;
	}

	public int getGrinder1Type() {
		return grinder1Type;
	}

	public void setGrinder1Type(int grinder1Type) {
		this.grinder1Type = grinder1Type;
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

	public int getTmPre13() {
		return tmPre13;
	}

	public void setTmPre13(int tmPre13) {
		this.tmPre13 = tmPre13;
	}

	public int getTmPre04() {
		return tmPre04;
	}

	public void setTmPre04(int tmPre04) {
		this.tmPre04 = tmPre04;
	}

	public int getExtractionTime() {
		return extractionTime;
	}

	public void setExtractionTime(int extractionTime) {
		this.extractionTime = extractionTime;
	}

	public int getDecompressTime() {
		return decompressTime;
	}

	public void setDecompressTime(int decompressTime) {
		this.decompressTime = decompressTime;
	}

	public int getOpenDelayTime() {
		return openDelayTime;
	}

	public void setOpenDelayTime(int openDelayTime) {
		this.openDelayTime = openDelayTime;
	}

	public int getPreBrewPosition() {
		return preBrewPosition;
	}

	public void setPreBrewPosition(int preBrewPosition) {
		this.preBrewPosition = preBrewPosition;
	}

	public int getDecompressPosition() {
		return decompressPosition;
	}

	public void setDecompressPosition(int decompressPosition) {
		this.decompressPosition = decompressPosition;
	}

	public int getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}
	

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	
	public int getTotalVolume()
	{
		return waterVolume;
	}

	@Override
	public String toString() {
		return "IngredientFilterBrew [id=" + id + ", name=" + name + ", pid="
				+ pid + ", phaseNumber=" + phaseNumber + ", waterVolume="
				+ waterVolume + ", grinder1Type=" + grinder1Type
				+ ", grinder1Amount=" + grinder1Amount + ", grinder2Type="
				+ grinder2Type + ", grinder2Amount=" + grinder2Amount
				+ ", tmPre13=" + tmPre13 + ", tmPre04=" + tmPre04
				+ ", extractionTime=" + extractionTime + ", decompressTime="
				+ decompressTime + ", openDelayTime=" + openDelayTime
				+ ", preBrewPosition=" + preBrewPosition
				+ ", decompressPosition=" + decompressPosition + ", totalTime="
				+ totalTime + ", isDefault=" + isDefault + ", createstatus="
				+ createstatus + "]";
	}

	public boolean isChanged() {
		return IsChanged;
	}
}
