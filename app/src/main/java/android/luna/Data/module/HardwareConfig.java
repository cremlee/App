package android.luna.Data.module;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_hardwareconfig")
public class HardwareConfig {
	
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int adress;   //硬件端口
	@DatabaseField
	private int type;     //da 编号           valve-1 , pwm-2 ,inport-3 ,extern-4  ,virturl-5
	@DatabaseField
	private int uid;      //xiao 编号 
	@DatabaseField
	private int groupid;  
	@DatabaseField(defaultValue = "0")
	private int enable;
	@DatabaseField
	private String description;
	@DatabaseField(defaultValue = "0")
	private  int showinsetting;
			
	

	public HardwareConfig(int adress, int type, int uid, int groupid,
			int enable, String description, int showinsetting) {
		super();
		this.adress = adress;
		this.type = type;
		this.uid = uid;
		this.groupid = groupid;
		this.enable = enable;
		this.description = description;
		this.showinsetting = showinsetting;
	}

	

	public HardwareConfig()
	{
		
	}

	public int getAdress() {
		return adress;
	}

	public void setAdress(int adress) {
		this.adress = adress;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getGroupid() {
		return groupid;
	}



	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}



	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}
	public int isShowinsetting() {
		return showinsetting;
	}

	public void setShowinsetting(int showinsetting) {
		this.showinsetting = showinsetting;
	}
	@Override
	public String toString() {
		return "HardwareConfig [id=" + id + ", adress=" + adress + ", type="
				+ type + ", uid=" + uid + ", enable=" + enable
				+ ", description=" + description + "]";
	}
	


}
