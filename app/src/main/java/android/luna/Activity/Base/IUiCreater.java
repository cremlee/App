package android.luna.Activity.Base;

import android.luna.Data.module.MachineDevice.Device;

/**
 * Created by Lee.li on 2017/12/28.
 */

public interface IUiCreater {
    abstract void InitView();
    abstract void InitData();
    abstract void InitEvent();
    abstract void setDeviceData(Device a);

}
