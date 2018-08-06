package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public interface IDevice extends ICleanAble {
    public static final int  GROUP_BREWER = 0x0001;
    public  static final int GROUP_GRINDER = 0x0002;
    /* compnent type */
    public  static final int TYPE_ESBREWER = 0x0001;
    public  static final int TYPE_MONOBREWER = 0x0002;
    public static  final int TYPE_FILTERBREWER = 0x0003;
}
