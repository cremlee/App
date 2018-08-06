package android.luna.rs232.Ack.DataStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee.li on 2018/7/30.
 */

public class DeviceDBItem {
    private int deviceid;
    private int paramcount;
    private List<DeviceParamItem> deviceParamItems;

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public int getParamcount() {
        return paramcount;
    }

    public void setParamcount(int paramcount) {
        this.paramcount = paramcount;
        deviceParamItems = new ArrayList<>(paramcount);
    }

    public List<DeviceParamItem> getDeviceParamItems() {
        return deviceParamItems;
    }

    public void setDeviceParamItems(List<DeviceParamItem> deviceParamItems) {
        this.deviceParamItems = deviceParamItems;
    }
}
