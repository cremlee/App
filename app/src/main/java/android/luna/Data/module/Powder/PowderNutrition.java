package android.luna.Data.module.Powder;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/5/17.
 */
@DatabaseTable(tableName = "tb_powder_nutrition")
public class PowderNutrition implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int pid;
    @DatabaseField(defaultValue = "0")
    private float kilocalorie=0;
    @DatabaseField(defaultValue = "0")
    private float protein=0;
    @DatabaseField(defaultValue = "0")
    private float fat=0;
    @DatabaseField(defaultValue = "0")
    private float carbohydrate=0;
    @DatabaseField(defaultValue = "0")
    private float sodium=0;
    @DatabaseField(defaultValue = "0")
    private float sugar=0;
    @DatabaseField(defaultValue = "0")
    private float undefine1=0;
    @DatabaseField(defaultValue = "0")
    private float undefine2=0;
    @DatabaseField(defaultValue = "0")
    private float undefine3=0;

    public PowderNutrition() {
    }

    public PowderNutrition(int pid, float kilocalorie, float protein, float fat, float carbohydrate, float sodium) {
        this.pid = pid;
        this.kilocalorie = kilocalorie;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.sodium = sodium;
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

    public float getKilocalorie() {
        return kilocalorie;
    }

    public void setKilocalorie(float kilocalorie) {
        this.kilocalorie = kilocalorie;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public float getSodium() {
        return sodium;
    }

    public void setSodium(float sodium) {
        this.sodium = sodium;
    }

    public float getUndefine1() {
        return undefine1;
    }

    public void setUndefine1(float undefine1) {
        this.undefine1 = undefine1;
    }

    public float getUndefine2() {
        return undefine2;
    }

    public void setUndefine2(float undefine2) {
        this.undefine2 = undefine2;
    }

    public float getUndefine3() {
        return undefine3;
    }

    public void setUndefine3(float undefine3) {
        this.undefine3 = undefine3;
    }

    public float getSugar() {
        return sugar;
    }

    public void setSugar(float sugar) {
        this.sugar = sugar;
    }

    public void reset()
    {
        this.kilocalorie = 0;
        this.protein = 0;
        this.fat = 0;
        this.carbohydrate = 0;
        this.sodium = 0;
        this.sugar =0;
    }
}
