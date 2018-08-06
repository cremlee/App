package android.luna.Data.module.DeviceLayout;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/4/24.
 */

public class DeviceCalibrationItem implements Serializable {
    private int deviceid;
    private int paramid;
    private int max;
    private int min;
    private int scale=1;
    private String unit;
    private int value;
    private boolean isfloat=false;

    public DeviceCalibrationItem(int deviceid, int max, int min, int scale, String unit, int value) {
        this.deviceid = deviceid;
        this.max = max;
        this.min = min;
        this.scale = scale;
        this.unit = unit;
        this.value = value;
    }

    public DeviceCalibrationItem(int deviceid, int max, int min, int scale, String unit, int value, boolean isfloat) {
        this.deviceid = deviceid;
        this.max = max;
        this.min = min;
        this.scale = scale;
        this.unit = unit;
        this.value = value;
        this.isfloat = isfloat;
    }

    public DeviceCalibrationItem(int deviceid, int paramid, int max, int min, int scale, String unit, int value, boolean isfloat) {
        this.deviceid = deviceid;
        this.paramid = paramid;
        this.max = max;
        this.min = min;
        this.scale = scale;
        this.unit = unit;
        this.value = value;
        this.isfloat = isfloat;
    }

    public int getParamid() {
        return paramid;
    }

    public void setParamid(int paramid) {
        this.paramid = paramid;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isfloat() {
        return isfloat;
    }

    public void setIsfloat(boolean isfloat) {
        this.isfloat = isfloat;
    }
}
