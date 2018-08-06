package android.luna.Data.module.Key;

/**
 * Created by Lee.li on 2018/6/4.
 */

public class AliAuthKey {
    private String appid;
    private String pvkey;
    private String pbkey;
    private String keytype;

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

    public String getPbkey() {
        return pbkey;
    }

    public void setPbkey(String pbkey) {
        this.pbkey = pbkey;
    }

    public String getKeytype() {
        return keytype;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }

    @Override
    public String toString() {
        return "AliAuthKey{" +
                "appid='" + appid + '\'' +
                ", pvkey='" + pvkey + '\'' +
                ", pbkey='" + pbkey + '\'' +
                ", keytype='" + keytype + '\'' +
                '}';
    }
}
