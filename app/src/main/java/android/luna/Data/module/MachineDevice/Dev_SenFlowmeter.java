package android.luna.Data.module.MachineDevice;
/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_SenFlowmeter extends Sensor {
    private int pluse = 1250;
    public Dev_SenFlowmeter(int type,int position) {
        super(0x0005, type, position);
    }

    public int getPluse() {
        return pluse;
    }

    public void setPluse(int pluse) {
        this.pluse = pluse;
    }
}
