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

}
