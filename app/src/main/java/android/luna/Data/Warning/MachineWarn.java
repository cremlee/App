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
                return MachineError.ECODE1;
            else if(warningcode ==2)
                return MachineError.ECODE2;
        }
        else if(strid.startsWith("0002"))
        {
            if(warningcode == 1)
                return MachineError.ECODE3;
            else if(warningcode ==2)
                return MachineError.ECODE4;
        }
        else if(strid.startsWith("0003"))
        {
            if(warningcode == 1)
                return MachineError.ECODE5;
            else if(warningcode ==2)
                return MachineError.ECODE6;
            else if(warningcode ==3)
                return MachineError.ECODE7;
        }
        else if(strid.startsWith("0004"))
        {
            if(warningcode == 1)
                return MachineError.ECODE8;
            else if(warningcode ==2)
                return MachineError.ECODE9;
        }
        else if(strid.startsWith("0005"))
        {
            if(warningcode == 1)
                return MachineError.ECODE10;
            else if(warningcode ==2)
                return MachineError.ECODE11;
        }
        else if(strid.startsWith("0006"))
        {
            if(warningcode == 1)
                return MachineError.ECODE12;
            else if(warningcode ==2)
                return MachineError.ECODE13;
            else if(warningcode ==3)
                return MachineError.ECODE14;
        }
        else if(strid.startsWith("0007"))
        {
            if(warningcode == 1)
                return MachineError.ECODE15;
            else if(warningcode ==2)
                return MachineError.ECODE16;
            else if(warningcode ==3)
                return MachineError.ECODE17;
            else if(warningcode ==4)
                return MachineError.ECODE18;
        }
        else if(strid.startsWith("0008"))
        {
            if(warningcode == 1)
                return MachineError.ECODE19;
            else if(warningcode ==2)
                return MachineError.ECODE20;
        }
        else if(strid.startsWith("0009"))
        {
            if(warningcode == 1)
                return MachineError.ECODE21;
        }
        else if(strid.startsWith("000A"))
        {
            if(warningcode == 1)
                return MachineError.ECODE22;
            else if(warningcode ==2)
                return MachineError.ECODE23;
        }
        else if(strid.startsWith("000B"))
        {
            if(warningcode == 1)
                return MachineError.ECODE24;
            else if(warningcode ==2)
                return MachineError.ECODE25;
        }
        else if(strid.startsWith("000C"))
        {
            if(warningcode == 1)
                return MachineError.ECODE26;
        }
        else if(strid.startsWith("000D"))
        {
            if(warningcode == 1)
                return MachineError.ECODE27;
            else if(warningcode ==2)
                return MachineError.ECODE28;
        }
        else if(strid.startsWith("0012"))
        {
            if(warningcode == 1)
                return MachineError.ECODE29;
            else if(warningcode ==2)
                return MachineError.ECODE30;
        }
        else if(strid.startsWith("0013"))
        {
            if(warningcode == 1)
                return MachineError.ECODE31;
        }
        else if(strid.startsWith("0014"))
        {
            if(warningcode == 1)
                return MachineError.ECODE32;
            else if(warningcode ==2)
                return MachineError.ECODE33;
        }
        else if(strid.startsWith("0015"))
        {
            if(warningcode == 1)
                return MachineError.ECODE34;
            else if(warningcode ==2)
                return MachineError.ECODE35;
            else if(warningcode ==3)
                return MachineError.ECODE36;
        }
        else if(strid.startsWith("0016"))
        {
            if(warningcode == 1)
                return MachineError.ECODE37;
            else if(warningcode ==2)
                return MachineError.ECODE38;
        }
        else if(strid.startsWith("0017"))
        {
            if(warningcode == 1)
                return MachineError.ECODE39;
            else if(warningcode ==2)
                return MachineError.ECODE40;
        }
        else if(strid.startsWith("0018"))
        {
            if(warningcode == 1)
                return MachineError.ECODE41;
        }
        else if(strid.startsWith("0019"))
        {
            if(warningcode == 1)
                return MachineError.ECODE42;
            else if(warningcode ==2)
                return MachineError.ECODE43;
        }
        else if(strid.startsWith("001A"))
        {
            if(warningcode == 1)
                return MachineError.ECODE44;
        }
        else if(strid.startsWith("00FF"))
        {
            if(warningcode == 1)
                return MachineError.ECODE45;
            else if(warningcode ==2)
                return MachineError.ECODE46;
            else if(warningcode ==3)
                return MachineError.ECODE47;
            else if(warningcode ==4)
                return MachineError.ECODE48;
            else if(warningcode ==5)
                return MachineError.ECODE49;
            else if(warningcode ==6)
                return MachineError.ECODE50;
            else if(warningcode ==7)
                return MachineError.ECODE51;
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
