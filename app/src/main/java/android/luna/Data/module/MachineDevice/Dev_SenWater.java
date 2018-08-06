package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_SenWater extends Sensor {
    public Dev_SenWater( int type) {
        super(0x0007, type, 0x01);
    }
}
