package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_ingredient_milk")
public class IngredientMilk implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private String name;
	@DatabaseField(defaultValue="1")
	private int Type=1; // 1:hotwet 2:hotdry 3:coldwet 4:colddry
	@DatabaseField(defaultValue="100")
	private int Volume=100; // water dispense time(ms)
	@DatabaseField(defaultValue="0")
	private int isDefault;
	@DatabaseField(defaultValue = "0")
	private int createstatus=0;
	
	public IngredientMilk() {
	}

	public IngredientMilk(int pid, String name, int type, int volume,
			int isDefault, int createstatus) {
		super();
		this.pid = pid;
		this.name = name;
		Type = type;
		Volume = volume;
		this.isDefault = isDefault;
		this.createstatus = createstatus;
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

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getVolume() {
		return Volume;
	}

	public void setVolume(int volume) {
		Volume = volume;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	public int getCreatestatus() {
		return createstatus;
	}

	public void setCreatestatus(int createstatus) {
		this.createstatus = createstatus;
	}

	@Override
	public String toString() {
		return "IngredientMilk [id=" + id + ", pid=" + pid + ", name=" + name
				+ ", Type=" + Type + ", Volume=" + Volume + ", isDefault="
				+ isDefault + ", createstatus=" + createstatus + "]";
	}

	

}
