package android.luna.Utils.Device;

/**
 * Created by Lee.li on 2018/7/16.
 */

public class MachineConfig {
    //gravitation system
    public static final int MACHINE_MF13 = 0x00001300;
    public static final int MACHINE_MF13_SW = 0x00001301;
    public static final int MACHINE_MF13_SSW = 0x00001302;
    public static final int MACHINE_MF04 = 0x00000400;
    public static final int MACHINE_MF04_SW = 0x00000401;
    public static final int MACHINE_MF04_SSW = 0x00000402;
    //pressure system
    public static final int MACHINE_CARARRA = 0x01000000;
    public static final int MACHINE_UNITY = 0x01002300;
    public static final int MACHINE_CL22 = 0x01002200;
    public static final int MACHINE_PSL50_12= 0x01001200;
    public static final int MACHINE_PSL50_13= 0x01001300;
    public static final int MACHINE_PSL50_14= 0x01001400;
    //zi dingyi system
    public static final int MACHINE_DEFINE= 0x00ff0000;

    // TODO: 2018/11/15 define the machine type 01-tea machine 02-coffee machine
    public static final int MACHINE_TYPE_TEA = 0x01;
    public static final int MACHINE_TYPE_COFFEE = 0x02;

    public static final int MACHINE_TYPE = 0x01;
}
