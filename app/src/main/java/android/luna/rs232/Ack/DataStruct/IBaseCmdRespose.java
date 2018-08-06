package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/3.
 */

public class IBaseCmdRespose<T> {

    private int cmd;
    private int ackresult;
    private T resdata;

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getAckresult() {
        return ackresult;
    }

    public void setAckresult(int ackresult) {
        this.ackresult = ackresult;
    }

    public T getResdata() {
        return resdata;
    }

    public void setResdata(T resdata) {
        this.resdata = resdata;
    }
}
