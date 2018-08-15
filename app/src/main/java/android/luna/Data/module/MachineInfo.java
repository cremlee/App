package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_machine_info")
public class MachineInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int type;
	@DatabaseField(defaultValue = "EVO")
	private String name = "EVO";
	@DatabaseField(defaultValue = "00000000")
	private String call = "00000000";
	@DatabaseField(defaultValue = "")
	private String date="";
	@DatabaseField(defaultValue = "Shanghai")
	private String site="Shanghai";
	@DatabaseField(defaultValue = "0000-0000-0000-0000")
	private String sn = "0000-0000-0000-0000";

	public MachineInfo() {
	}

	public MachineInfo(int type, String name, String call, String date, String site, String sn) {
		this.type = type;
		this.name = name;
		this.call = call;
		this.date = date;
		this.site = site;
		this.sn = sn;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
}
