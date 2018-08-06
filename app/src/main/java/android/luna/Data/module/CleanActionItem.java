package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/7/12.
 */

public class CleanActionItem {

    public static int CLEAN_ACTION_SHORT =0;
    public static int CLEAN_ACTION_SORK =1;
    public static int CLEAN_ACTION_RINGSING =2;
    public static int CLEAN_ACTION_SHOCK =3;
    public static int CLEAN_ACTION_DRY_OPEN =4;
    public static int CLEAN_ACTION_DRY_CLOSE =5;
    private int deviceid;
    private int action;

    public CleanActionItem(int deviceid, int action) {
        this.deviceid = deviceid;
        this.action = action;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
