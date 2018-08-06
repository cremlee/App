package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class MakeDrinkResData implements IEncode {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void Encodeing2class(String[] ack) {
        int pos =0;
        id = (Integer.valueOf(ack[pos], 16)<<8) +Integer.valueOf(ack[pos+1], 16);
    }

    @Override
    public int getlength() {
        return 2;
    }
}
