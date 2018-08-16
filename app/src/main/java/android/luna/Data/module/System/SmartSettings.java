package android.luna.Data.module.System;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/8/6.
 */
@DatabaseTable(tableName = "tb_smart_setting")
public class SmartSettings implements Serializable{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "0")  //off
    private int smartmode;
    @DatabaseField(defaultValue = "0")  //off
    private int daylightmode;
    @DatabaseField
    private String dlstart;
    @DatabaseField
    private String dlstop;
    @DatabaseField(defaultValue = "0") //normal
    private int currentmode;

    public SmartSettings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSmartmode() {
        return smartmode;
    }

    public void setSmartmode(int smartmode) {
        this.smartmode = smartmode;
    }

    public int getDaylightmode() {
        return daylightmode;
    }

    public void setDaylightmode(int daylightmode) {
        this.daylightmode = daylightmode;
    }

    public String getDlstart() {
        return dlstart;
    }

    public void setDlstart(String dlstart) {
        this.dlstart = dlstart;
    }

    public String getDlstop() {
        return dlstop;
    }

    public void setDlstop(String dlstop) {
        this.dlstop = dlstop;
    }

    public int getCurrentmode() {
        return currentmode;
    }

    public void setCurrentmode(int currentmode) {
        this.currentmode = currentmode;
    }
}
