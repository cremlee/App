package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/31.
 */

public class DeviceDBSetResData implements IEncode {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void Encodeing2class(String[] ack) {
        int pos=0;
        count = Integer.valueOf(ack[pos], 16);
    }

    @Override
    public int getlength() {
        return 1;
    }
}
