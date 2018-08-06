package android.luna.Data.module.MachineDevice;
/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_SenDoor extends Sensor {
    public Dev_SenDoor(int position) {
        super(0x001a, 0x01, position);
    }
}
