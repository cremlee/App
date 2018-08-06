package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class TestResData implements IEncode {
    private int op;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    @Override
    public void Encodeing2class(String[] ack) {
        int pos =0;
        op = Integer.valueOf(ack[pos++], 16);
        id = (Integer.valueOf(ack[pos], 16)<<24) + (Integer.valueOf(ack[pos+1], 16)<<16) +
                (Integer.valueOf(ack[pos+2], 16)<<8) +Integer.valueOf(ack[pos+3], 16);
    }

    @Override
    public int getlength() {
        return 5;
    }
}
