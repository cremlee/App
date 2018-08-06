package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_ingredient")
public class Ingredient implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int TYPE_ESPRESSO = 1;
	public static final int TYPE_FILTER_BREW = 2;
	public static final int TYPE_MILK = 3;
	public static final int TYPE_INSTANT = 4;
	public static final int TYPE_WATER = 5;
	public static final int TYPE_FILTER_BREW_ADVANCE = 6;

	public static final int ACK_REJECT = 0;
	public static final int ACK_OK = 1;
	public static final int ACK_ERR = 2;
	public static final int ACK_PACKAGE_ERR = 3;
	public static final int ACK_ID_ERR = 4;
	public static final int ACK_NO_SPACE = 5;
	public static final int ACK_ID_NOT_EXIST = 6;
	public static final int ACK_ID_ALREADY_EXIST = 7;
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private int type;
	@DatabaseField
	private String name;
	@DatabaseField(defaultValue="0")
	/**
	 * 2是通过Ingredient Maker--Add添加的，可以通过Ingredient Maker界面滑动删除，
	 * 在Edit Recipe中点击删除只将之间的引用删掉
	 * 1表示是默认的，在Edit Recipe中不能删除，点击Edit Recipe的删除只将之间的引用关系去掉
	 * 0表示通过Change Base Recipe添加进去的，在EditRecipe中能删除，为0不在Ingredient List中显示出来
	 */
	private int isDefault=0; 

	public Ingredient() {
	}

	public Ingredient(int pid, int type, String name,int isDefault) {
		super();
		this.pid = pid;
		this.type = type;
		this.name = name;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", pid=" + pid + ", type=" + type + ", name=" + name + "]";
	}

}
