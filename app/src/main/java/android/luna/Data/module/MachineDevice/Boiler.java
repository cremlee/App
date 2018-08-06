package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/17.
 */

public class Boiler extends Device{
    private int max_capability     = 20;
    private int inlet_water_type   = 1;
    private int life_intlet_valve  = 5000;
    private int cycle_boiler_clean = 6000;

    public Boiler(int type,int position)   {super(0x000f, type, position); }

    public int getMax_capability() {
        return max_capability;
    }

    public void setMax_capability(int max_capability) {
        this.max_capability = max_capability;
    }

    public int getInlet_water_type() {
        return inlet_water_type;
    }

    public void setInlet_water_type(int inlet_water_type) {
        this.inlet_water_type = inlet_water_type;
    }

    public int getLife_intlet_valve() {
        return life_intlet_valve;
    }

    public void setLife_intlet_valve(int life_intlet_valve) {
        this.life_intlet_valve = life_intlet_valve;
    }

    public int getCycle_boiler_clean() {
        return cycle_boiler_clean;
    }

    public void setCycle_boiler_clean(int cycle_boiler_clean) {
        this.cycle_boiler_clean = cycle_boiler_clean;
    }
}
