package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Heater extends Device {
    private int max_heat_time=180;
    private int life_times = 100000;
    public Heater(int group, int type, int position)  { super(group, type, position);}

    public int getMax_heat_time() {
        return max_heat_time;
    }

    public void setMax_heat_time(int max_heat_time) {
        this.max_heat_time = max_heat_time;
    }

    public int getLife_times() {
        return life_times;
    }

    public void setLife_times(int life_times) {
        this.life_times = life_times;
    }
}
