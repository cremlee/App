package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tb_temperature_setting")
public class TemperatureSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(defaultValue = "96")
	private int tankTemp = 96; // water tank
	@DatabaseField(defaultValue = "86")
	private int warningTemp = 86; // warning temp
	@DatabaseField(defaultValue = "80")
	private int blockingTemp = 80;
	@DatabaseField(defaultValue = "60")
	private int energySavingTemp = 60;

	public TemperatureSetting() {
		super();
	}

	public TemperatureSetting(int tankTemp, int warningTemp, int blockingTemp, int energySavingTemp) {
		super();
		this.tankTemp = tankTemp;
		this.warningTemp = warningTemp;
		this.blockingTemp = blockingTemp;
		this.energySavingTemp = energySavingTemp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTankTemp() {
		return tankTemp;
	}

	public void setTankTemp(int tankTemp) {
		this.tankTemp = tankTemp;
	}

	public int getWarningTemp() {
		return warningTemp;
	}

	public void setWarningTemp(int warningTemp) {
		this.warningTemp = warningTemp;
	}

	public int getBlockingTemp() {
		return blockingTemp;
	}

	public void setBlockingTemp(int blockingTemp) {
		this.blockingTemp = blockingTemp;
	}

	public int getEnergySavingTemp() {
		return energySavingTemp;
	}

	public void setEnergySavingTemp(int energySavingTemp) {
		this.energySavingTemp = energySavingTemp;
	}

	@Override
	public String toString() {
		return "TemperatureSetting [id=" + id + ", tankTemp=" + tankTemp + ", warningTemp=" + warningTemp + ", blockingTemp=" + blockingTemp + ", energySavingTemp=" + energySavingTemp + "]";
	}
	
	

}
