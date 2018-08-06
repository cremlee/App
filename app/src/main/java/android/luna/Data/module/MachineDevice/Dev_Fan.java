package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_Fan extends Device {
    private int life_motor =500;
    private int run_speed =80;
    public Dev_Fan(int position)
    {
        super(0x0014,01,position);
    }

    public int getLife_motor() {
        return life_motor;
    }

    public void setLife_motor(int life_motor) {
        this.life_motor = life_motor;
    }

    public int getRun_speed() {
        return run_speed;
    }

    public void setRun_speed(int run_speed) {
        this.run_speed = run_speed;
    }
}
