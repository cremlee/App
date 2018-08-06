package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_Hopper extends Device {
    private int powder_type = 0x81;
    private int motor_life  = 5000;
    private int max_capability = 15;
    private int dosage_value = 5;
    private float dosage_density =0.5f;
    public Dev_Hopper(int position)
    {
        super(0x0015, 1,position);
    }

    public int getPowder_type() {
        return powder_type;
    }

    public void setPowder_type(int powder_type) {
        this.powder_type = powder_type;
    }

    public int getMotor_life() {
        return motor_life;
    }

    public void setMotor_life(int motor_life) {
        this.motor_life = motor_life;
    }

    public int getMax_capability() {
        return max_capability;
    }

    public void setMax_capability(int max_capability) {
        this.max_capability = max_capability;
    }

    public int getDosage_value() {
        return dosage_value;
    }

    public void setDosage_value(int dosage_value) {
        this.dosage_value = dosage_value;
    }

    public float getDosage_density() {
        return dosage_density;
    }

    public void setDosage_density(float dosage_density) {
        this.dosage_density = dosage_density;
    }
}
