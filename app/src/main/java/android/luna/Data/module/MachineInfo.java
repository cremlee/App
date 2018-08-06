package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_machine_info")
public class MachineInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(defaultValue = "My CQube EVO")
	private String machineName = "My CQube EVO";
	@DatabaseField(defaultValue = "00000000")
	private String servicePhone = "00000000";
	@DatabaseField
	private int machineType; // MF04
	@DatabaseField(defaultValue = "")
	private String installdate="";
	public String getInstalldate() {
		return installdate;
	}

	public void setInstalldate(String installdate) {
		this.installdate = installdate;
	}

	@DatabaseField
	private String site;
	public MachineInfo() {
		super();
	}



	public MachineInfo(String machineName, String servicePhone,
			int machineType, String installdate, String site) {
		super();
		this.machineName = machineName;
		this.servicePhone = servicePhone;
		this.machineType = machineType;
		this.installdate = installdate;
		this.site = site;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public int getMachineType() {
		return machineType;
	}

	public void setMachineType(int machineType) {
		this.machineType = machineType;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

}
