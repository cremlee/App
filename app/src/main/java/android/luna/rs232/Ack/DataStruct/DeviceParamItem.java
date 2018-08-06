package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/30.
 */

public class DeviceParamItem {
    private int paramid;
    private int paramvalue;

    public int getParamid() {
        return paramid;
    }

    public void setParamid(int paramid) {
        this.paramid = paramid;
    }

    public int getParamvalue() {
        return paramvalue;
    }

    public void setParamvalue(int paramvalue) {
        this.paramvalue = paramvalue;
    }
}
