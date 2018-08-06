package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/23.
 */

public class MakeDrinkFinishResData implements IEncode {
    private int pid;
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    @Override
    public void Encodeing2class(String[] ack) {
        int pos =0;
        pid = (Integer.valueOf(ack[pos], 16)<<8) +Integer.valueOf(ack[pos+1], 16);
    }

    @Override
    public int getlength() {
        return 2;
    }
}
