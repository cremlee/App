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
    private int localinfo;
    private int ledmode;
    private int ledcolor;
    private int ledbrightness;
    private int wledmode;
    private int wledcolor;
    private int wledbrightness;
    private int jugmode;
    private int nutritionmode;
    private int beepmode;
}
