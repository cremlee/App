package android.luna.Data.Warning;

import android.content.Context;
import android.luna.Data.module.Error.MachineError;
import android.support.annotation.NonNull;

import java.io.Serializable;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/6.
 */

public class MachineWarn implements Serializable ,Comparable<MachineWarn>{
    private int warningdevice;
    private int warningcode;
    private int warninglevel;

    public MachineWarn(int id,int code,int level)
    {
        this.warningdevice =id;
        this.warningcode =code;
        this.warninglevel = level;
    }

    public int getWarningdevice() {
        return warningdevice;
    }

    public void setWarningdevice(int warningdevice) {
        this.warningdevice = warningdevice;
    }

    public int getWarningcode() {
        return warningcode;
    }

    public void setWarningcode(int warningcode) {
        this.warningcode = warningcode;
    }

    public int getWarninglevel() {
        return warninglevel;
    }

    public void setWarninglevel(int warninglevel) {
        this.warninglevel = warninglevel;
    }


    public String getWarninghelp()
    {
        return "";
    }


    public String getErrorContent()
    {
        String strid = String.format("%08X",warningdevice);
        if(strid.startsWith("0001"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0101;
            else if(warningcode ==2)
                return MachineError.ECODE0102;
        }
        else if(strid.startsWith("0002"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0201;
            else if(warningcode ==2)
                return MachineError.ECODE0202;
        }
        else if(strid.startsWith("0003"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0301;
            else if(warningcode ==2)
                return MachineError.ECODE0302;
            else if(warningcode ==3)
                return MachineError.ECODE0303;
        }
        else if(strid.startsWith("0004"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0401;
            else if(warningcode ==2)
                return MachineError.ECODE0402;
        }
        else if(strid.startsWith("0005"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0501;
            else if(warningcode ==2)
                return MachineError.ECODE0502;
        }
        else if(strid.startsWith("0006"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0601;
            else if(warningcode ==2)
                return MachineError.ECODE0602;
            else if(warningcode ==3)
                return MachineError.ECODE0603;
            else if(warningcode ==4)
                return MachineError.ECODE0604;
        }
        else if(strid.startsWith("0007"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0701;
            else if(warningcode ==2)
                return MachineError.ECODE0702;
            else if(warningcode ==3)
                return MachineError.ECODE0703;
            else if(warningcode ==4)
                return MachineError.ECODE0704;
        }
        else if(strid.startsWith("0008"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0801;
            else if(warningcode ==2)
                return MachineError.ECODE0802;
        }
        else if(strid.startsWith("0009"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0901;
        }
        else if(strid.startsWith("000A"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0A01;
            else if(warningcode ==2)
                return MachineError.ECODE0A02;
        }
        else if(strid.startsWith("000B"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0B01;
            else if(warningcode ==2)
                return MachineError.ECODE0B02;
        }
        else if(strid.startsWith("000C"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0C01;
        }
        else if(strid.startsWith("000D"))
        {
            if(warningcode == 1)
                return MachineError.ECODE0D01;
            else if(warningcode ==2)
                return MachineError.ECODE0D02;
        }
        else if(strid.startsWith("0012"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1201;
            else if(warningcode ==2)
                return MachineError.ECODE1202;
        }
        else if(strid.startsWith("0013"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1301;
        }
        else if(strid.startsWith("0014"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1401;
            else if(warningcode ==2)
                return MachineError.ECODE1402;
        }
        else if(strid.startsWith("0015"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1501;
            else if(warningcode ==2)
                return MachineError.ECODE1502;
            else if(warningcode ==3)
                return MachineError.ECODE1503;
        }
        else if(strid.startsWith("0016"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1601;
            else if(warningcode ==2)
                return MachineError.ECODE1602;
        }
        else if(strid.startsWith("0017"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1701;
            else if(warningcode ==2)
                return MachineError.ECODE1702;
        }
        else if(strid.startsWith("0018"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1801;
        }
        else if(strid.startsWith("0019"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1901;
            else if(warningcode ==2)
                return MachineError.ECODE1902;
        }
        else if(strid.startsWith("001A"))
        {
            if(warningcode == 1)
                return MachineError.ECODE1A01;
        }
        else if(strid.startsWith("00FF"))
        {
            if(warningcode == 1)
                return MachineError.ECODEFF01;
            else if(warningcode ==2)
                return MachineError.ECODEFF02;
            else if(warningcode ==3)
                return MachineError.ECODEFF03;
            else if(warningcode ==4)
                return MachineError.ECODEFF04;
            else if(warningcode ==5)
                return MachineError.ECODEFF05;
            else if(warningcode ==6)
                return MachineError.ECODEFF06;
            else if(warningcode ==7)
                return MachineError.ECODEFF07;
        }
        return "not defined!";
    }

    public String getErrorDeviceName(Context context)
    {
        String strid = String.format("%08X",warningdevice);
        if(strid.startsWith("000101"))
            return context.getString(R.string.PL_ES_BREWER)+(warningdevice&0xff);
        else if(strid.startsWith("0004") )
            return context.getString(R.string.PL_MIXER)+(warningdevice&0xff);
        else if(strid.startsWith("0002") )
            return context.getString(R.string.PL_GRINDER)+(warningdevice&0xff);
        else if(strid.startsWith("0003") )
            return context.getString(R.string.PL_CANISTER)+(warningdevice&0xff);
        else if(strid.startsWith("0015"))
            return context.getString(R.string.PL_HOPPER)+(warningdevice&0xff);
        else if(strid.startsWith("000101"))
            return context.getString(R.string.PL_ES_BREWER)+(warningdevice&0xff)+ context.getString(R.string.PL_VALVE);
        else if(strid.startsWith("000A"))
            return context.getString(R.string.PL_PUMP);
        else if(strid.startsWith("0006"))
            return context.getString(R.string.PL_NTC)+(warningdevice&0xff);
        else if(strid.startsWith("0007"))
            return "Water level "+(warningdevice&0xff);
        else if(strid.startsWith("0008"))
            return context.getString(R.string.PL_CUP_SEN)+(warningdevice&0xff);
        else if(strid.startsWith("0009") )
            return "Pressure "+(warningdevice&0xff);
        else if(strid.startsWith("0018"))
            return context.getString(R.string.PL_DRIPTRAY)+(warningdevice&0xff);
        else if(strid.startsWith("0018"))
            return "Door switch "+(warningdevice&0xff);
        return "other";
    }






    @Override
    public boolean equals(Object obj) {
        MachineWarn c = (MachineWarn)obj;
        if(c==null) return false;
        return this.warningdevice == c.warningdevice &&
                this.warningcode == c.warningcode &&
                this.warninglevel == c.warninglevel;
    }

    @Override
    public int compareTo(@NonNull MachineWarn machineWarn) {
        int num = this.getWarningdevice()-machineWarn.getWarningdevice();
        if(num ==0)
            num = this.getWarningcode()-machineWarn.getWarningcode();
        if(num ==0)
            num = this.getWarninglevel()-machineWarn.getWarninglevel();
        return num;
    }

    @Override
    public String toString() {
        return "MachineWarn{" +
                "warningdevice=" + warningdevice +
                ", warningcode=" + warningcode +
                ", warninglevel=" + warninglevel +
                '}';
    }
}
