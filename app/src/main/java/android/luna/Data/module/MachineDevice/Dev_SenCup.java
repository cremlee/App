package android.luna.Data.module.MachineDevice;
/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_SenCup extends Sensor {
    public Dev_SenCup( int position) {
        super(0x0008, 0x01, position);
    }
}
