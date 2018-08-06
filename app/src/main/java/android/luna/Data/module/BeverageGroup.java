package android.luna.Data.module;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Comparator;

@DatabaseTable(tableName = "tb_beverage_group")
public class BeverageGroup implements Serializable ,Comparator<BeverageGroup>,Comparable<BeverageGroup>{
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private int groupid;
	@DatabaseField
	private String name="";
	@DatabaseField
	private String iconpath="";
	@DatabaseField
	private int bigmode=0;
	@DatabaseField
	private int showinscreen=0;

	public BeverageGroup() {
	}

	public BeverageGroup(int pid, String name, String iconpath) {
		this.pid = pid;
		this.name = name;
		this.iconpath = iconpath;
	}

	public BeverageGroup(int pid, int groupid, String name, String iconpath) {
		this.pid = pid;
		this.groupid = groupid;
		this.name = name;
		this.iconpath = iconpath;
	}

	public BeverageGroup(int pid, int groupid, String name, String iconpath, int bigmode, int showinscreen) {
		this.pid = pid;
		this.groupid = groupid;
		this.name = name;
		this.iconpath = iconpath;
		this.bigmode = bigmode;
		this.showinscreen = showinscreen;
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

	public String getIconpath() {
		return iconpath;
	}

	public void setIconpath(String iconpath) {
		this.iconpath = iconpath;
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public int getBigmode() {
		return bigmode;
	}

	public void setBigmode(int bigmode) {
		this.bigmode = bigmode;
	}

	public int getShowinscreen() {
		return showinscreen;
	}

	public void setShowinscreen(int showinscreen) {
		this.showinscreen = showinscreen;
	}


    @Override
    public int compareTo(@NonNull BeverageGroup o) {
        return o.compare(o,this);
    }

    @Override
    public int compare(BeverageGroup o1, BeverageGroup o2) {
        return  o1.getName().compareToIgnoreCase(o2.getName());
    }
}
