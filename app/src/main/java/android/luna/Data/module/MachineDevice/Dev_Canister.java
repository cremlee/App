package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_Canister extends Dev_Hopper {
    public Dev_Canister(int type,int position) {
        super(position);
        this.setGroup_id(0x0003);
        this.setCompent_type(type);
    }
}
