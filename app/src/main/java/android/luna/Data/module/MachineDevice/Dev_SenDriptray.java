package android.luna.Data.module.MachineDevice;
/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_SenDriptray extends Sensor {
    private int max_capability = 10;
    public Dev_SenDriptray(int position) {
        super(0x0018, 0x01, position);
    }

    public int getMax_capability() {
        return max_capability;
    }

    public void setMax_capability(int max_capability) {
        this.max_capability = max_capability;
    }
}
