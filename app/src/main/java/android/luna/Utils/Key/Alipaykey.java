package android.luna.Utils.Key;

/**
 * Created by Lee.li on 2018/8/6.
 */

public class Alipaykey {
    public String info ;
    public String appid;
    public String pvkey;
    public String pbkey;
    public String authtype;

    @Override
    public String toString() {
        return "Alipaykey{" +
                "info='" + info + '\'' +
                ", appid='" + appid + '\'' +
                ", pvkey='" + pvkey + '\'' +
                ", pbkey='" + pbkey + '\'' +
                ", authtype='" + authtype + '\'' +
                '}';
    }
}
