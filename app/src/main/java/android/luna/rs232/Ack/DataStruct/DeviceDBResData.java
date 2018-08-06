package android.luna.rs232.Ack.DataStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee.li on 2018/7/30.
 */

public class DeviceDBResData implements IEncode {
    private int length=0;
    private int devicecount;
    private List<DeviceDBItem> deviceDBItems;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDevicecount() {
        return devicecount;
    }

    public void setDevicecount(int devicecount) {
        this.devicecount = devicecount;
    }

    public List<DeviceDBItem> getDeviceDBItems() {
        return deviceDBItems;
    }

    public void setDeviceDBItems(List<DeviceDBItem> deviceDBItems) {
        this.deviceDBItems = deviceDBItems;
    }

    @Override
    public void Encodeing2class(String[] ack) {
        devicecount = Integer.valueOf(ack[length++], 16);
        deviceDBItems = new ArrayList<>(devicecount);
        DeviceDBItem dbItem;
        for (int i=0;i<devicecount;i++)
        {
            dbItem =new DeviceDBItem();
            int deviceid = (Integer.valueOf(ack[length], 16)<<24) + (Integer.valueOf(ack[length+1], 16)<<16) +
                    (Integer.valueOf(ack[length+2], 16)<<8) +Integer.valueOf(ack[length+3], 16);
            length+=4;
            dbItem.setDeviceid(deviceid);
            int paramcount = Integer.valueOf(ack[length++], 16);
            dbItem.setParamcount(paramcount);
            DeviceParamItem paramItem;
            for(int j=0;j<paramcount;j++)
            {
                paramItem = new DeviceParamItem();
                int paramid = Integer.valueOf(ack[length++], 16);
                paramItem.setParamid(paramid);
                int paramvalue = (Integer.valueOf(ack[length], 16)<<24) + (Integer.valueOf(ack[length+1], 16)<<16) +
                        (Integer.valueOf(ack[length+2], 16)<<8) +Integer.valueOf(ack[length+3], 16);
                length+=4;
                paramItem.setParamvalue(paramvalue);
            }
        }
    }

    @Override
    public int getlength() {
        return length;
    }
}
