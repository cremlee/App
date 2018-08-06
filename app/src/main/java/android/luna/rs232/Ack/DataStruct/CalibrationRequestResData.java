package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/27.
 */

public class CalibrationRequestResData implements IEncode {
    private int id;
    private int value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void Encodeing2class(String[] ack) {
        int pos =0;
        id = (Integer.valueOf(ack[pos], 16)<<24) + (Integer.valueOf(ack[pos+1], 16)<<16) +
                (Integer.valueOf(ack[pos+2], 16)<<8) +Integer.valueOf(ack[pos+3], 16);
        pos+=4;
        value = (Integer.valueOf(ack[pos], 16)<<24) + (Integer.valueOf(ack[pos+1], 16)<<16) +
                (Integer.valueOf(ack[pos+2], 16)<<8) +Integer.valueOf(ack[pos+3], 16);
    }

    @Override
    public int getlength() {
        return 8;
    }
}
