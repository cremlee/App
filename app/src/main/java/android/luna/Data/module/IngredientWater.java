package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_ingredient_water")
public class IngredientWater implements Serializable {
	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private String name;
	@DatabaseField(defaultValue="0")
	private int waterType=0; // hot water/coldwater/carbon water
	@DatabaseField(defaultValue="180")
	private int waterVolume=180; // water dispense time(ms)
	@DatabaseField(defaultValue="0")
	private int isDefault;
	@DatabaseField(defaultValue = "1")
	private int createstatus=1;
	
	public int getCreatestatus() {
		return createstatus;
	}

	public void setCreatestatus(int createstatus) {
		this.createstatus = createstatus;
	}

	public boolean isChanged() {
		return IsChanged;
	}

	private boolean IsChanged=false;
	public void setChanged(boolean changed) {
		IsChanged = changed;
		if(createstatus==2)
		{
			createstatus =3;
		}
	}
	public IngredientWater() {
	}

	public IngredientWater(int pid, String name, int waterType, int waterVolume,int isDefault) {
		super();
		this.pid = pid;
		this.name = name;
		this.waterType = waterType;
		this.waterVolume = waterVolume;
		this.isDefault = isDefault;
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

	public int getWaterType() {
		return waterType;
	}

	public void setWaterType(int waterType) {
		this.waterType = waterType;
	}

	public int getWaterVolume() {
		return waterVolume;
	}

	public void setWaterVolume(int waterVolume) {
		this.waterVolume = waterVolume;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "IngredientWater [id=" + id + ", pid=" + pid + ", name=" + name + ", waterType=" + waterType
				+ ", waterVolume=" + waterVolume + ", isDefault=" + isDefault + "]";
	}

}
