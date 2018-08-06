package android.luna.SDK.Wechat;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Key.KeyManager;
import android.luna.Utils.Key.Wechatkey;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class WXPayConfigImpl extends WXPayConfig{

    private byte[] certData;
    private static WXPayConfigImpl INSTANCE;
    private static Wechatkey KeyInstance;
    private String appid;
    private String mchid;
    private String key;

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    private String prepay_id;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    private String out_trade_no;

    /**
     * zhengshu jiazai
     * @throws Exception
     */
    private WXPayConfigImpl() throws Exception{
        /*String certPath = FileHelper.PATH_CERT_FILE;
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();*/
    }

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                    if(KeyInstance ==null)
                        KeyInstance = KeyManager.getWechatkey();
                }
            }
        }
        return INSTANCE;
    }

    public String getKeyInfo()
    {
        return KeyInstance==null?"not in use":KeyInstance.info;
    }
    public String getAppID() {
        return KeyInstance==null?"":KeyInstance.appid;
    }

    public String getMchID() {
        return  KeyInstance==null?"":KeyInstance.mchid;
    }

    public String getKey() {
        return  KeyInstance==null?"":KeyInstance.pvkey;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }


    public int getHttpConnectTimeoutMs() {
        return 2000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public String getPrimaryDomain() {
        return "api.mch.weixin.qq.com";
    }

    public String getAlternateDomain() {
        return "api2.mch.weixin.qq.com";
    }

    @Override
    public int getReportWorkerNum() {
        return 1;
    }

    @Override
    public int getReportBatchSize() {
        return 2;
    }
}
