package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Mixer extends Device {
    private int max_capability = 10;
    private int run_speed = 80;
    private int hot_valve_flow  = 10;
    private int cold_valve_flow = 10;
    private int life_motor = 5000;
    private int life_hot_valve = 5000;
    private int life_cold_valve = 5000;
    private int life_whipper = 5000;

    public Mixer(int type,int position) { super(0x0004, type, position);}

    public int getMax_capability() {
        return max_capability;
    }

    public void setMax_capability(int max_capability) {
        this.max_capability = max_capability;
    }

    public int getRun_speed() {
        return run_speed;
    }

    public void setRun_speed(int run_speed) {
        this.run_speed = run_speed;
    }

    public int getHot_valve_flow() {
        return hot_valve_flow;
    }

    public void setHot_valve_flow(int hot_valve_flow) {
        this.hot_valve_flow = hot_valve_flow;
    }

    public int getCold_valve_flow() {
        return cold_valve_flow;
    }

    public void setCold_valve_flow(int cold_valve_flow) {
        this.cold_valve_flow = cold_valve_flow;
    }

    public int getLife_motor() {
        return life_motor;
    }

    public void setLife_motor(int life_motor) {
        this.life_motor = life_motor;
    }

    public int getLife_hot_valve() {
        return life_hot_valve;
    }

    public void setLife_hot_valve(int life_hot_valve) {
        this.life_hot_valve = life_hot_valve;
    }

    public int getLife_cold_valve() {
        return life_cold_valve;
    }

    public void setLife_cold_valve(int life_cold_valve) {
        this.life_cold_valve = life_cold_valve;
    }

    public int getLife_whipper() {
        return life_whipper;
    }

    public void setLife_whipper(int life_whipper) {
        this.life_whipper = life_whipper;
    }
}
