package android.luna.Data.module.Production;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Comparator;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class AutoTestItem implements Comparator<AutoTestItem>,Comparable<AutoTestItem> ,Cloneable{
    public static final int OP_AUTO =1;
    public static final int OP_VALVE =2;
    public static final int OP_SENSOR =3;

    public static final int RESULT_IDEL =0;
    public static final int RESULT_OK =1;
    public static final int RESULT_FAILED=2;
    public static final int RESULT_TESTING=3;

    private int deviceid;
    private int result =0;
    private int optype=1;

    public AutoTestItem() {
    }

    public AutoTestItem(int deviceid, int result, int optype) {
        this.deviceid = deviceid;
        this.result = result;
        this.optype = optype;
    }

    public int getDeviceid() {
        return deviceid;
    }

    public String getTestID()
    {
        return String.format("%08X",deviceid);
    }
    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getOptype() {
        return optype;
    }

    public void setOptype(int optype) {
        this.optype = optype;
    }

    public String getName(Context context)
    {
        String strid = String.format("%08X",deviceid);
        if(strid.startsWith("000101") && optype == OP_AUTO)
            return context.getString(R.string.PL_ES_BREWER)+(deviceid&0xff);
        if(strid.startsWith("0004")  && optype == OP_AUTO)
            return context.getString(R.string.PL_MIXER)+(deviceid&0xff);
        if(strid.startsWith("0002")  && optype == OP_AUTO)
            return context.getString(R.string.PL_GRINDER)+(deviceid&0xff);
        if(strid.startsWith("0003")  && optype == OP_AUTO)
            return context.getString(R.string.PL_CANISTER)+(deviceid&0xff);
        if(strid.startsWith("0015")  && optype == OP_AUTO)
            return context.getString(R.string.PL_HOPPER)+(deviceid&0xff);
        if(strid.startsWith("000101") && optype == OP_VALVE)
            return context.getString(R.string.PL_ES_BREWER)+(deviceid&0xff)+ context.getString(R.string.PL_VALVE);
        if(strid.startsWith("0004") && optype == OP_VALVE)
            return context.getString(R.string.PL_MIXER)+(deviceid&0xff)+ context.getString(R.string.PL_VALVE);
        if(strid.startsWith("000A") && optype == OP_VALVE)
            return context.getString(R.string.PL_PUMP);
        if(strid.startsWith("0006") && optype == OP_SENSOR)
            return context.getString(R.string.PL_NTC)+(deviceid&0xff);
        if(strid.startsWith("0007") && optype == OP_SENSOR)
            return "Water level "+(deviceid&0xff);
        if(strid.startsWith("0008") && optype == OP_SENSOR)
            return context.getString(R.string.PL_CUP_SEN)+(deviceid&0xff);
        if(strid.startsWith("0009") && optype == OP_SENSOR)
            return "Pressure "+(deviceid&0xff);
        if(strid.startsWith("0018") && optype == OP_SENSOR)
            return context.getString(R.string.PL_DRIPTRAY)+(deviceid&0xff);
        if(strid.startsWith("0018") && optype == OP_SENSOR)
            return "Door switch "+(deviceid&0xff);
        return "other";
    }

    public String getResultString(Context context)
    {
        String ret = context.getString(R.string.PL_WAIT);
        switch (result)
        {
            case RESULT_IDEL:
                ret = context.getString(R.string.PL_WAIT);
                break;
            case RESULT_OK:
                ret = context.getString(R.string.PL_PASS);
                break;
            case RESULT_TESTING:
                ret = context.getString(R.string.PL_IN_TEST);
                break;
            case RESULT_FAILED:
                ret = context.getString(R.string.PL_FAIL);
                break;
        }
        return  ret;
    }

    @Override
    public int compareTo(@NonNull AutoTestItem o) {
        return o.compare(o,this);
    }

    @Override
    public int compare(AutoTestItem o1, AutoTestItem o2) {
        if(o1.getOptype()<o2.getOptype())
            return 1;
        else if(o1.getOptype()==o2.getOptype())
            return 0;
        else
            return -1;
    }

    @Override
    public AutoTestItem clone() throws CloneNotSupportedException {
        AutoTestItem sc =null;
        try
        {
            sc = (AutoTestItem)super.clone();
        }catch (Exception e){}
        return  sc;
    }
}
