package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
@DatabaseTable(tableName = "tb_mono_step")
public class IngredientMonoProcess implements Serializable {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int stepindex;
    @DatabaseField
    private int pid;
    @DatabaseField(defaultValue = "5")
    private int infusion_tm;
    @DatabaseField(defaultValue = "50")
    private int infusion_volume;
    @DatabaseField(defaultValue = "0")
    private int infusion_type;
    @DatabaseField(defaultValue = "0")
    private int infusion_temperature;
    @DatabaseField(defaultValue = "5")
    private int bubpump_tm;
    @DatabaseField(defaultValue = "100")
    private int bubpump_spd;
    @DatabaseField(defaultValue = "15")
    private int press_tm;
    @DatabaseField(defaultValue = "50")
    private int dispense_volume;
    @DatabaseField(defaultValue = "0")
    private int dispense_type;
    @DatabaseField(defaultValue = "0")
    private int dispense_temperature;
    @DatabaseField(defaultValue = "10")
    private int outlet_tm;
    @DatabaseField(defaultValue = "100")
    private int outlet_speed;

    public IngredientMonoProcess() {
    }

    public IngredientMonoProcess(int infusion_tm, int infusion_volume, int infusion_type, int infusion_temperature, int bubpump_tm, int bubpump_spd, int press_tm, int dispense_volume, int dispense_type, int dispense_temperature, int outlet_tm, int outlet_speed) {
        this.infusion_tm = infusion_tm;
        this.infusion_volume = infusion_volume;
        this.infusion_type = infusion_type;
        this.infusion_temperature = infusion_temperature;
        this.bubpump_tm = bubpump_tm;
        this.bubpump_spd = bubpump_spd;
        this.press_tm = press_tm;
        this.dispense_volume = dispense_volume;
        this.dispense_type = dispense_type;
        this.dispense_temperature = dispense_temperature;
        this.outlet_tm = outlet_tm;
        this.outlet_speed = outlet_speed;
    }

    public int getStepindex() {
        return stepindex;
    }

    public void setStepindex(int stepindex) {
        this.stepindex = stepindex;
    }

    public int getId() {
        return id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getInfusion_tm() {
        return infusion_tm;
    }

    public void setInfusion_tm(int infusion_tm) {
        this.infusion_tm = infusion_tm;
    }

    public int getInfusion_volume() {
        return infusion_volume;
    }

    public void setInfusion_volume(int infusion_volume) {
        this.infusion_volume = infusion_volume;
    }

    public int getInfusion_type() {
        return infusion_type;
    }

    public void setInfusion_type(int infusion_type) {
        this.infusion_type = infusion_type;
    }

    public int getInfusion_temperature() {
        return infusion_temperature;
    }

    public void setInfusion_temperature(int infusion_temperature) {
        this.infusion_temperature = infusion_temperature;
    }

    public int getBubpump_tm() {
        return bubpump_tm;
    }

    public void setBubpump_tm(int bubpump_tm) {
        this.bubpump_tm = bubpump_tm;
    }

    public int getBubpump_spd() {
        return bubpump_spd;
    }

    public void setBubpump_spd(int bubpump_spd) {
        this.bubpump_spd = bubpump_spd;
    }

    public int getPress_tm() {
        return press_tm;
    }

    public void setPress_tm(int press_tm) {
        this.press_tm = press_tm;
    }

    public int getDispense_volume() {
        return dispense_volume;
    }

    public void setDispense_volume(int dispense_volume) {
        this.dispense_volume = dispense_volume;
    }

    public int getDispense_type() {
        return dispense_type;
    }

    public void setDispense_type(int dispense_type) {
        this.dispense_type = dispense_type;
    }

    public int getDispense_temperature() {
        return dispense_temperature;
    }

    public void setDispense_temperature(int dispense_temperature) {
        this.dispense_temperature = dispense_temperature;
    }

    public int getOutlet_tm() {
        return outlet_tm;
    }

    public void setOutlet_tm(int outlet_tm) {
        this.outlet_tm = outlet_tm;
    }

    public int getOutlet_speed() {
        return outlet_speed;
    }

    public void setOutlet_speed(int outlet_speed) {
        this.outlet_speed = outlet_speed;
    }

    @Override
    public String toString() {
        return "IngredientMonoProcess{" +
                "infusion_tm=" + infusion_tm +
                ", infusion_volume=" + infusion_volume +
                ", infusion_type=" + infusion_type +
                ", infusion_temperature=" + infusion_temperature +
                ", bubpump_tm=" + bubpump_tm +
                ", bubpump_spd=" + bubpump_spd +
                ", press_tm=" + press_tm +
                ", dispense_volume=" + dispense_volume +
                ", dispense_type=" + dispense_type +
                ", dispense_temperature=" + dispense_temperature +
                ", outlet_tm=" + outlet_tm +
                ", outlet_speed=" + outlet_speed +
                '}';
    }
}
