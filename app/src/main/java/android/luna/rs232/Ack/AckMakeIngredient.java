package android.luna.rs232.Ack;

import android.luna.rs232.Ack.DataStruct.IBaseCmdRespose;
import android.luna.rs232.Ack.DataStruct.IEncode;
import android.luna.rs232.Ack.DataStruct.MakeIngredientResData;

/**
 * Created by Lee.li on 2018/7/3.
 */

public class AckMakeIngredient extends IBaseCmdRespose<MakeIngredientResData> implements IEncode {
    public  int dataindex =5;
    @Override
    public void Encodeing2class(String[] ack) {

        int value = (Integer.valueOf(ack[dataindex], 16)<<8) +Integer.valueOf(ack[dataindex+1], 16);
        dataindex+=2;
        setCmd(value);
        setResdata(new MakeIngredientResData());
        String[] data = new String[getResdata().getlength()];
        System.arraycopy(ack,dataindex,data,0,getResdata().getlength());
        getResdata().Encodeing2class(data);
        dataindex+= getResdata().getlength();
        value = Integer.valueOf(ack[dataindex], 16);
        setAckresult(value);
    }

    @Override
    public int getlength() {
        return 0;
    }
}
