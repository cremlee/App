package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_pin_setting")
public class PinSetting implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int useUserPin;
	@DatabaseField(defaultValue = "000000")
	private String userPin = "000000";
	@DatabaseField
	private int useServicePin;
	@DatabaseField(defaultValue = "000000")
	private String servicePin = "000000";
	@DatabaseField
	private int useVendPin;
	@DatabaseField(defaultValue = "000000")
	private String VendPin = "000000";
	
	
	public PinSetting() {
		super();
	}

	public PinSetting(int useUserPin, String userPin, int useServicePin, String servicePin) {
		super();
		this.useUserPin = useUserPin;
		this.userPin = userPin;
		this.useServicePin = useServicePin;
		this.servicePin = servicePin;
	}

	public int getUseVendPin() {
		return useVendPin;
	}

	public void setUseVendPin(int useVendPin) {
		this.useVendPin = useVendPin;
	}

	public String getVendPin() {
		return VendPin;
	}

	public void setVendPin(String vendPin) {
		VendPin = vendPin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUseUserPin() {
		return useUserPin;
	}

	public void setUseUserPin(int useUserPin) {
		this.useUserPin = useUserPin;
	}

	public String getUserPin() {
		return userPin;
	}

	public void setUserPin(String userPin) {
		this.userPin = userPin;
	}

	public int getUseServicePin() {
		return useServicePin;
	}

	public void setUseServicePin(int useServicePin) {
		this.useServicePin = useServicePin;
	}

	public String getServicePin() {
		return servicePin;
	}

	public void setServicePin(String servicePin) {
		this.servicePin = servicePin;
	}

}
