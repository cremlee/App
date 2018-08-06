package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tb_drink_setting")
public class DrinkSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(defaultValue = "1")
	private int enableWaterDuringDispening = 1;
	@DatabaseField(defaultValue = "1")
	private int enableHotWater = 1;
	@DatabaseField
	private int enableColdWater;
	@DatabaseField
	private int enableCo2Water;

	public DrinkSetting() {
		super();
	}

	public DrinkSetting(int enableWaterDuringDispening, int enableHotWater, int enableColdWater, int enableCo2Water) {
		super();
		this.enableWaterDuringDispening = enableWaterDuringDispening;
		this.enableHotWater = enableHotWater;
		this.enableColdWater = enableColdWater;
		this.enableCo2Water = enableCo2Water;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnableWaterDuringDispening() {
		return enableWaterDuringDispening;
	}

	public void setEnableWaterDuringDispening(int enableWaterDuringDispening) {
		this.enableWaterDuringDispening = enableWaterDuringDispening;
	}

	public int getEnableHotWater() {
		return enableHotWater;
	}

	public void setEnableHotWater(int enableHotWater) {
		this.enableHotWater = enableHotWater;
	}

	public int getEnableColdWater() {
		return enableColdWater;
	}

	public void setEnableColdWater(int enableColdWater) {
		this.enableColdWater = enableColdWater;
	}

	public int getEnableCo2Water() {
		return enableCo2Water;
	}

	public void setEnableCo2Water(int enableCo2Water) {
		this.enableCo2Water = enableCo2Water;
	}

}
