package android.luna.Data.module.Key;

/**
 * Created by Lee.li on 2018/6/4.
 */

public class WechatAuthKey {
    private String appid;
    private String pvkey;
    private String mchid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPvkey() {
        return pvkey;
    }

    public void setPvkey(String pvkey) {
        this.pvkey = pvkey;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    @Override
    public String toString() {
        return "WechatAuthKey{" +
                "appid='" + appid + '\'' +
                ", pvkey='" + pvkey + '\'' +
                ", mchid='" + mchid + '\'' +
                '}';
    }
}
