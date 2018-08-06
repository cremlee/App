package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Brewer extends Device {
    private int max_capability =10;
    private int inlet_flow=10;
    private int life_inlet_valve=10000;
    private int life_brewer_motor=5000;

    public Brewer(int componttype,int position) {
        super(0x0001, componttype, position);
    }

    public int getMax_capability() {
        return max_capability;
    }

    public void setMax_capability(int max_capability) {
        this.max_capability = max_capability;
    }

    public int getInlet_flow() {
        return inlet_flow;
    }

    public void setInlet_flow(int inlet_flow) {
        this.inlet_flow = inlet_flow;
    }

    public int getLife_inlet_valve() {
        return life_inlet_valve;
    }

    public void setLife_inlet_valve(int life_inlet_valve) {
        this.life_inlet_valve = life_inlet_valve;
    }

    public int getLife_brewer_motor() {
        return life_brewer_motor;
    }

    public void setLife_brewer_motor(int life_brewer_motor) {
        this.life_brewer_motor = life_brewer_motor;
    }
}
