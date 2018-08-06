package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/5/7.
 */
@DatabaseTable(tableName = "tb_drinkgroup")
public class DrinkGroup implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int pid;
    @DatabaseField
    private String name="";
    @DatabaseField
    private String iconpath="";

    public DrinkGroup() {
    }

    public DrinkGroup(int pid, String name, String iconpath) {
        this.pid = pid;
        this.name = name;
        this.iconpath = iconpath;
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
}
