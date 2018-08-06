package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Sensor extends Device {
    private int life_sensor=500;
    public Sensor(int group,int type,int position)  { super(group, type, position);}

    public int getLife_sensor() {
        return life_sensor;
    }

    public void setLife_sensor(int life_sensor) {
        this.life_sensor = life_sensor;
    }
}
