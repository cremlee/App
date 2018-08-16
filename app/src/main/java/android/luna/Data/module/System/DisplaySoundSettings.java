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

    public DisplaySoundSettings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocalinfo() {
        return localinfo;
    }

    public void setLocalinfo(int localinfo) {
        this.localinfo = localinfo;
    }

    public int getLedmode() {
        return ledmode;
    }

    public void setLedmode(int ledmode) {
        this.ledmode = ledmode;
    }

    public int getLedcolor() {
        return ledcolor;
    }

    public void setLedcolor(int ledcolor) {
        this.ledcolor = ledcolor;
    }

    public int getLedbrightness() {
        return ledbrightness;
    }

    public void setLedbrightness(int ledbrightness) {
        this.ledbrightness = ledbrightness;
    }

    public int getWledmode() {
        return wledmode;
    }

    public void setWledmode(int wledmode) {
        this.wledmode = wledmode;
    }

    public int getWledcolor() {
        return wledcolor;
    }

    public void setWledcolor(int wledcolor) {
        this.wledcolor = wledcolor;
    }

    public int getWledbrightness() {
        return wledbrightness;
    }

    public void setWledbrightness(int wledbrightness) {
        this.wledbrightness = wledbrightness;
    }

    public int getJugmode() {
        return jugmode;
    }

    public void setJugmode(int jugmode) {
        this.jugmode = jugmode;
    }

    public int getNutritionmode() {
        return nutritionmode;
    }

    public void setNutritionmode(int nutritionmode) {
        this.nutritionmode = nutritionmode;
    }

    public int getBeepmode() {
        return beepmode;
    }

    public void setBeepmode(int beepmode) {
        this.beepmode = beepmode;
    }
}
