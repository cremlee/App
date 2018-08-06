package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Pump extends Device {
    private int motor_life= 0xe9dad0;
    private int speed=100;
    public Pump(int group, int type, int position)  { super(group, type, position);}

    public int getMotor_life() {
        return motor_life;
    }

    public void setMotor_life(int motor_life) {
        this.motor_life = motor_life;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
