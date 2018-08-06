package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_drinkname")
public class DrinkName implements Serializable {
	public final  static int Local_other = 9;
	public final  static int Local_cn =1;
	public final  static int Local_nl =2;
	public final  static int Local_da =3;
	public final  static int Local_en =4;
	public final  static int Local_fi =5;
	public final  static int Local_gm =6;
	public final  static int Local_no =7;
	public final  static int Local_sv =8;

/*	public static int Local_en =1;
	public static int Local_en =1;*/
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private String name;
	@DatabaseField
	private int localinfo;
	
	public DrinkName(){}
	public DrinkName(int id, int pid, String name, int localinfo) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.localinfo = localinfo;
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
	public int getLocalinfo() {
		return localinfo;
	}
	public void setLocalinfo(int localinfo) {
		this.localinfo = localinfo;
	}
	
	

}
