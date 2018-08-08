package android.luna.Data.module.System;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.io.Serializable;

/**
 * Created by Lee.li on 2018/8/6.
 */
@DatabaseTable(tableName = "tb_displaysound_setting")
public class DisplaySoundSettings implements Serializable{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "4")  //en
    private int localinfo;
    @DatabaseField(defaultValue = "0")  //off
    private int ledmode;
    @DatabaseField(defaultValue = "0")  //off
    private int ledcolor;
    @DatabaseField(defaultValue = "0")  //off
    private int ledbrightness;
    @DatabaseField(defaultValue = "0")  //off
    private int wledmode;
    @DatabaseField(defaultValue = "0")  //off
    private int wledcolor;
    @DatabaseField(defaultValue = "0")  //off
    private int wledbrightness;
    @DatabaseField(defaultValue = "0")  //off
    private int jugmode;
    @DatabaseField(defaultValue = "0")  //off
    private int nutritionmode;
    @DatabaseField(defaultValue = "0")  //off
    private int beepmode;
}
