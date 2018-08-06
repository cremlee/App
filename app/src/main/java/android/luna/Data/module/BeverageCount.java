package android.luna.Data.module;



import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Comparator;

@DatabaseTable(tableName = "tb_beverage_counter")
public class BeverageCount implements Serializable,Comparator<BeverageCount> ,Comparable<BeverageCount> ,Cloneable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField(defaultValue = "0")
	private int drinkCount;


	public String getIconpath() {
		return iconpath;
	}

	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}

	private String iconpath="";
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name="";

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

	public int getDrinkCount() {
		return drinkCount;
	}

	public void setDrinkCount(int drinkCount) {
		this.drinkCount = drinkCount;
	}


	@Override
	public int compare(BeverageCount o1, BeverageCount o2) {
		if(o1.getDrinkCount()>o2.getDrinkCount())
			return 1;
		else if(o1.getDrinkCount()==o2.getDrinkCount())
			return 0;
		else
			return -1;
	}

	@Override
	public int compareTo(@NonNull BeverageCount o) {
		return o.compare(o,this);
	}


	public BeverageCount clone() {
		BeverageCount o = null;
		try {
			o = (BeverageCount) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

}
