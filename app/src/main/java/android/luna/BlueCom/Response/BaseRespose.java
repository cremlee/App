package android.luna.BlueCom.Response;

/**
 * Created by Lee.li on 2018/6/8.
 */

public class BaseRespose {
    private String cmd;
    private String data;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public BaseRespose()
    {
    }
    public BaseRespose built(String value)
    {

        this.cmd = value.substring(8,10);
        this.data = value.substring(10,value.length()-5);
        return this;
    }
    public String[] getdatas()
    {
        return data.split(":");
    }
}
