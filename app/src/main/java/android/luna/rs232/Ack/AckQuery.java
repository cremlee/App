package android.luna.rs232.Ack;

import android.luna.rs232.Ack.DataStruct.DeviceErrorItem;
import android.luna.rs232.Ack.DataStruct.DeviceStateItem;
import android.luna.rs232.Ack.DataStruct.IEncode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee.li on 2018/2/5.
 */

public class AckQuery implements IEncode {
    public static final int MS_CLEAN_FINISH_CHECK_FREQUENCY = 4;
    public static final int MS_DRINK_FINISH_CHECK_FREQUENCY = 4;
    public static final int MS_ERROR_CHECK_FREQUENCY = 4;

    public static final int MS_NORMAL_IDEL = 0x08;
    public static final int MS_NORMAL_FILLING = 0x09;
    public static final int MS_NORMAL_HEATING = 0x0a;
    public static final int MS_SLEEPING = 0x10;
    public static final int MS_BACKUP = 0x18;
    public static final int MS_CLEAN_CLEANING = 0x23;
    public static final int MS_CLEAN_MILK = 0x22;
    public static final int MS_CLEAN_FINISH= 0x21;
    public static final int MS_DISPEN_FINISH= 0x29;
    public static final int MS_DISPEN_ING= 0x2a;
    public static final int MS_BLOCK_MODE= 0x30;
    public static final int MS_CAL_ING= 0x3a;
    public static final int MS_CAL_FINISH= 0x39;
    public static final int MS_FRESET_FINISH= 0x41;
    public static final int MS_FRESET_ING= 0x42;
    public static final int MS_TEST_FINISH= 0x4a;
    public static final int MS_TEST_ING= 0x49;
    public static final int MS_UPDATE_ING= 0x51;
    public static final int MS_PAY_ING= 0x58;
    public static final int MS_STORAGE_OP= 0x62;
    public static final int MS_STORAGE_FINISH= 0x61;

    //public static final int INDEX_COOLING_TEMPERATURE =0;
    public static final int INDEX_NTC_HIGH_TEMPERATURE =0x00060101;
    public static final int INDEX_NTC_LOW_TEMPERATURE =0x00060102;
    public static final int INDEX_DOOR_STATE =0x00110101;
    public static final int INDEX_DRIPTRAY_STATE =0x00180101;
    public static final int INDEX_HEATER_STATE =0x00160101;
    public static final int INDEX_WASTER_BIN_STATE =0x00190101;
    public static final int INDEX_WATER_HIGH_STATE =0x00070101;
    public static final int INDEX_WATER_LOW_STATE =0x00070102;

    public int cleanfinishcheck;
    public int dinkkfinishcheck;
    public int blockcheck;
    public int releasecheck;

    private int machine_state;
    private int state_size;
    private List<DeviceStateItem> state_list =new ArrayList<>(8);
    private int error_size;
    private List<DeviceErrorItem> deviceErrorItems=new ArrayList<>(5);

    public AckQuery() {
    }

    @Override
    public void Encodeing2class(String[] ack) {
        DeviceErrorItem deviceErrorItem;
        DeviceStateItem deviceStateItem;
        int dataindex = 7;
        machine_state = Integer.valueOf(ack[dataindex++], 16);
        state_size = Integer.valueOf(ack[dataindex++], 16);
        state_list.clear();
        for (int i = 0; i < state_size; i++) {
            int id = Integer.valueOf(ack[dataindex] + ack[dataindex + 1] + ack[dataindex + 2] + ack[dataindex + 3], 16);
            dataindex+=4;
            byte value =Byte.valueOf(ack[dataindex++],16);
            deviceStateItem = new DeviceStateItem(id,value);
            state_list.add(deviceStateItem);
        }
        error_size = Integer.valueOf(ack[dataindex++], 16);
        deviceErrorItems.clear();
        for (int i = 0; i < error_size; i++) {
            deviceErrorItem = new DeviceErrorItem();
            try {
                deviceErrorItem.setId(Integer.valueOf(ack[dataindex] + ack[dataindex + 1] + ack[dataindex + 2] + ack[dataindex + 3], 16));
                dataindex += 4;
                deviceErrorItem.setError_size(Integer.valueOf(ack[dataindex++], 16));
                deviceErrorItem.InitErrorList(ack, dataindex, dataindex + deviceErrorItem.length());
                dataindex += deviceErrorItem.getError_size();
            } catch (Exception e) {
                deviceErrorItem =null;
            }
            if(deviceErrorItem!=null)
            deviceErrorItems.add(deviceErrorItem);
        }
    }

    @Override
    public int getlength() {
        return 0;
    }

    public List<DeviceStateItem> getState_list() {
        return state_list;
    }

    public void setState_list(List<DeviceStateItem> state_list) {
        this.state_list = state_list;
    }

    public int getNtcHighTemperature()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_NTC_HIGH_TEMPERATURE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }
    public int getNtcLowTemperature()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_NTC_LOW_TEMPERATURE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }

    public int getWaterHighState()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_WATER_HIGH_STATE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }

    public int getWaterLowState()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_WATER_LOW_STATE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }

    public int getIndexDoorState()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_DOOR_STATE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }

    public int getIndexDriptrayState()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_DRIPTRAY_STATE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }

    public int getIndexHeaterState()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_HEATER_STATE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }

    public int getIndexWasterBinState()
    {
        int index = state_list.indexOf(new DeviceStateItem(INDEX_WASTER_BIN_STATE,(byte)0));
        if(index ==-1)
            return 0xffffffff;
        else
            return state_list.get(index).getValue();
    }

    public int getMachine_state() {
        return machine_state;
    }

    public void setMachine_state(int machine_state) {
        this.machine_state = machine_state;
    }

    public int getState_size() {
        return state_size;
    }

    public void setState_size(int state_size) {
        this.state_size = state_size;
    }

    public int getError_size() {
        return error_size;
    }

    public void setError_size(int error_size) {
        this.error_size = error_size;
    }

    public List<DeviceErrorItem> getDeviceErrorItems() {
        return deviceErrorItems;
    }

    public void setDeviceErrorItems(List<DeviceErrorItem> deviceErrorItems) {
        this.deviceErrorItems = deviceErrorItems;
    }

    /*public List<DeviceErrorItem> getBlockItem()
    {
        if(this.deviceErrorItems!=null && this.deviceErrorItems.size()>0)
        {
            for (DeviceErrorItem)
        }
    }*/
    @Override
    public String toString() {
        return "AckQuery{" +
                "machine_state=" + machine_state +
                ", state_size=" + state_size +
                ", state_list=" + state_list +
                ", error_size=" + error_size +
                ", deviceErrorItems=" + deviceErrorItems +
                '}';
    }
}
