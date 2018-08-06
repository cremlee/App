package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Lee.li on 2018/6/6.
 */
@DatabaseTable(tableName = "tb_canister_stock")
public class CanisterItemStock {
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int pid;
    @DatabaseField
    private int group;
    @DatabaseField
    private int stock;    //dangqian kucun
    @DatabaseField
    private int capability; //zong rongliang

    public String PowderName="";
    public CanisterItemStock() {
    }

    public CanisterItemStock(int pid, int group, int stock, int capability) {
        this.pid = pid;
        this.group = group;
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


    public void refill()
    {
        this.stock = this.capability;
    }
    @Override
    public String toString() {
        return "CanisterItemStock{" +
                "id=" + id +
                ", pid=" + pid +
                ", group=" + group +
                ", stock=" + stock +
                ", capability=" + capability +
                ", PowderName='" + PowderName + '\'' +
                '}';
    }
}
