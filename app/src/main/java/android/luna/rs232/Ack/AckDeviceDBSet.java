package android.luna.rs232.Ack;

import android.luna.rs232.Ack.DataStruct.CleanFinishResData;
import android.luna.rs232.Ack.DataStruct.DeviceDBSetResData;
import android.luna.rs232.Ack.DataStruct.IBaseCmdRespose;
import android.luna.rs232.Ack.DataStruct.IEncode;

/**
 * Created by Lee.li on 2018/7/31.
 */

public class AckDeviceDBSet extends IBaseCmdRespose<DeviceDBSetResData> implements IEncode {
    private   int dataindex =5;
    @Override
    public void Encodeing2class(String[] ack) {
        int value = (Integer.valueOf(ack[dataindex], 16)<<8) +Integer.valueOf(ack[dataindex+1], 16);
        dataindex+=2;
        setCmd(value);
        setResdata(new DeviceDBSetResData());
        dataindex+= getResdata().getlength();
        value = Integer.valueOf(ack[dataindex], 16);
        setAckresult(value);
    }

    @Override
    public int getlength() {
        return 0;
    }
}
