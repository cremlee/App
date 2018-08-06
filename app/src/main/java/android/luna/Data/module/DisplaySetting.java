package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_display_setting")
public class DisplaySetting implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(defaultValue = "0")
	private String languageKey = "0";
	@DatabaseField(defaultValue = "1")
	private String ledMode = "1"; // always on
	@DatabaseField(defaultValue = "1")
	private String ledColor = "1";
	@DatabaseField(defaultValue = "1")
	private String ledBrightness = "1";

	public DisplaySetting() {
		super();
	}

	public DisplaySetting(String languageKey, String ledMode, String ledColor, String ledBrightness) {
		super();
		this.languageKey = languageKey;
		this.ledMode = ledMode;
		this.ledColor = ledColor;
		this.ledBrightness = ledBrightness;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLanguageKey() {
		return languageKey;
	}

	public void setLanguageKey(String languageKey) {
		this.languageKey = languageKey;
	}

	public String getLedMode() {
		return ledMode;
	}

	public void setLedMode(String ledMode) {
		this.ledMode = ledMode;
	}

	public String getLedColor() {
		return ledColor;
	}

	public void setLedColor(String ledColor) {
		this.ledColor = ledColor;
	}

	public String getLedBrightness() {
		return ledBrightness;
	}

	public void setLedBrightness(String ledBrightness) {
		this.ledBrightness = ledBrightness;
	}

}
