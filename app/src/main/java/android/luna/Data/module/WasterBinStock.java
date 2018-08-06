package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Lee.li on 2018/6/6.
 */
@DatabaseTable(tableName = "tb_watser_stock")
public class WasterBinStock {
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "99")
    private int pid=99;
    @DatabaseField(defaultValue = "99")
    private int group=99;
    @DatabaseField(defaultValue = "150")
    private int stock=150;
    @DatabaseField(defaultValue = "150")
    private int capability =150;

    public WasterBinStock() {
    }

    public WasterBinStock(int stock, int capability) {
        this.stock = stock;
        this.capability = capability;
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

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCapability() {
        return capability;
    }

    public void setCapability(int capability) {
        this.capability = capability;
    }
    public void Empty()
    {
        this.stock =this.capability;
    }
}
