package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_canister_setting")
public class CanisterSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int enableCanisterWarning;
	@DatabaseField(defaultValue="1")
	private int enableWasteBinWarning=1;
	@DatabaseField(defaultValue = "150")
	private int wasteBinResetVolume = 150;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEnableCanisterWarning() {
		return enableCanisterWarning;
	}

	public void setEnableCanisterWarning(int enableCanisterWarning) {
		this.enableCanisterWarning = enableCanisterWarning;
	}

	public int getEnableWasteBinWarning() {
		return enableWasteBinWarning;
	}

	public void setEnableWasteBinWarning(int enableWasteBinWarning) {
		this.enableWasteBinWarning = enableWasteBinWarning;
	}

	public int getWasteBinResetVolume() {
		return wasteBinResetVolume;
	}

	public void setWasteBinResetVolume(int wasteBinResetVolume) {
		this.wasteBinResetVolume = wasteBinResetVolume;
	}

}
