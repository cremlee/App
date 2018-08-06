package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_Grinder extends Device {
    private int dosage_value =5;
    private int motor_life=5000;
    public Dev_Grinder(int position)
    {
        super(0x0002,01,position);
    }

    public int getDosage_value() {
        return dosage_value;
    }

    public void setDosage_value(int dosage_value) {
        this.dosage_value = dosage_value;
    }

    public int getMotor_life() {
        return motor_life;
    }

    public void setMotor_life(int motor_life) {
        this.motor_life = motor_life;
    }
}
