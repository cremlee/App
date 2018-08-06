package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Administrator
 * 
 */
@DatabaseTable(tableName = "tb_ingredient_instant")
public class IngredientInstantNew implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private String name;
	//mixNumber now it is invalible, value = 0 对应xml文件中的MixIndex-RSV
	@DatabaseField
	private int mixNumber; 
	@DatabaseField(defaultValue = "57")
	private int waterVolume = 57;
	@DatabaseField(defaultValue = "2")
	private int preflushVolume = 2;
	@DatabaseField(defaultValue = "15")
	private int preflushPauseTime = 15; // 1500 ms Preflush pause time
	@DatabaseField(defaultValue = "35")
	private int pauseTimeAfterDispense = 35; // 3500 ms PostFlush pause time
	@DatabaseField(defaultValue = "1")
	@Deprecated
	private int waterDispenseTime = 1;	
	@DatabaseField(defaultValue = "1")
	private int mixStart=1;
	@DatabaseField(defaultValue = "8")
	private int mixStop=8;
	@DatabaseField(defaultValue = "5")
	private int waterAfterFlushVolume = 5;
	@DatabaseField(defaultValue = "50")
	private int whipperSpeed = 50;
	@DatabaseField(defaultValue = "0")
	private int waterType = 0;
	@DatabaseField
	private int packetNumber; // 最多2个
	@DatabaseField(defaultValue = "1")
	private int packet1Type = 1;
	@DatabaseField(defaultValue = "8")
	private float packet1Amount = 8;
	@DatabaseField
	private int packet2Type;
	@DatabaseField
	private float packet2Amount;
	@DatabaseField(defaultValue = "0")
	private int isDefault;
	//1:new 2:ok 3:update
		@DatabaseField(defaultValue = "0")
		private int createstatus=0;
		
		public int getCreatestatus() {
			return createstatus;
		}

		public void setCreatestatus(int createstatus) {
			this.createstatus = createstatus;
		}
	public IngredientInstantNew() {
	}

	public IngredientInstantNew(int pid, String name, int waterVolume, int mixNumber, int preflushVolume, int waterDispenseTime, int waterAfterFlushVolume, int whipperSpeed, int waterType, int packetNumber, int packet1Type, float packet1Amount, int packet2Type, float packet2Amount, int isDefault,int preflushPauseTime,int pauseTimeAfterDispense) {
		super();
		this.pid = pid;
		this.name = name;
		this.waterVolume = waterVolume;
		this.mixNumber = mixNumber;
		this.preflushVolume = preflushVolume;
		this.waterDispenseTime = waterDispenseTime; //  对应xml Volume
		this.waterAfterFlushVolume = waterAfterFlushVolume;
		this.whipperSpeed = whipperSpeed;
		this.waterType = waterType;
		this.packetNumber = packetNumber;
		this.packet1Type = packet1Type;
		this.packet1Amount = packet1Amount;
		this.packet2Type = packet2Type;
		this.packet2Amount = packet2Amount;
		this.isDefault = isDefault;
		this.preflushPauseTime = preflushPauseTime;
		this.pauseTimeAfterDispense = pauseTimeAfterDispense;
	}

	public int getTotalVolume()
	{
		return preflushVolume+waterVolume+waterAfterFlushVolume;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMixNumber() {
		return mixNumber;
	}

	public void setMixNumber(int mixNumber) {
		this.mixNumber = mixNumber;
	}

	public int getWaterVolume() {
		return waterVolume;
	}

	public void setWaterVolume(int waterVolume) {
		this.waterVolume = waterVolume;
	}

	public int getPreflushVolume() {
		return preflushVolume;
	}

	public void setPreflushVolume(int preflushVolume) {
		this.preflushVolume = preflushVolume;
	}

	public int getPreflushPauseTime() {
		return preflushPauseTime;
	}

	public void setPreflushPauseTime(int preflushPauseTime) {
		this.preflushPauseTime = preflushPauseTime;
	}

	public int getPauseTimeAfterDispense() {
		return pauseTimeAfterDispense;
	}

	public void setPauseTimeAfterDispense(int pauseTimeAfterDispense) {
		this.pauseTimeAfterDispense = pauseTimeAfterDispense;
	}

	public int getWaterDispenseTime() {
		return waterDispenseTime;
	}

	public void setWaterDispenseTime(int waterDispenseTime) {
		this.waterDispenseTime = waterDispenseTime;
	}

	public int getWaterAfterFlushVolume() {
		return waterAfterFlushVolume;
	}

	public void setWaterAfterFlushVolume(int waterAfterFlushVolume) {
		this.waterAfterFlushVolume = waterAfterFlushVolume;
	}

	public int getWhipperSpeed() {
		return whipperSpeed;
	}

	public void setWhipperSpeed(int whipperSpeed) {
		this.whipperSpeed = whipperSpeed;
	}

	public int getWaterType() {
		return waterType;
	}

	public void setWaterType(int waterType) {
		this.waterType = waterType;
	}

	public int getPacketNumber() {
		return packetNumber;
	}

	public void setPacketNumber(int packetNumber) {
		this.packetNumber = packetNumber;
	}

	public int getPacket1Type() {
		return packet1Type;
	}

	public void setPacket1Type(int packet1Type) {
		this.packet1Type = packet1Type;
	}

	public float getPacket1Amount() {
		return packet1Amount;
	}

	public void setPacket1Amount(float packet1Amount) {
		this.packet1Amount = packet1Amount;
	}

	public int getPacket2Type() {
		return packet2Type;
	}

	public void setPacket2Type(int packet2Type) {
		this.packet2Type = packet2Type;
	}

	public float getPacket2Amount() {
		return packet2Amount;
	}

	public void setPacket2Amount(float packet2Amount) {
		this.packet2Amount = packet2Amount;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	

}
