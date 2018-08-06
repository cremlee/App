package android.luna.Data.module.Payment.Wechat;

import android.luna.SDK.Wechat.WXPayConfig;
import android.luna.SDK.Wechat.WXPayConfigImpl;
import android.luna.SDK.Wechat.WXPayUtil;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * Created by Lee.li on 2018/7/10.
 */

public class RefundTest extends Thread {
    private WXPayConfig config ;
    public  RefundTest(WXPayConfig config)
    {
        this.config =config;
    }
    @Override
    public void run() {
        refund();
    }
    private void refund()
    {
        KeyStore keyStore = null;

        try {
            keyStore = KeyStore.getInstance("PKCS12");
            try {
                keyStore.load(config.getCertStream(), config.getMchID().toCharArray());  // inStream :证书流，mch_id :密码，注意当证书和密码不对应的时候会出现异常
                KeyManagerFactory kmf = KeyManagerFactory.getInstance("X509");
                kmf.init(keyStore, config.getMchID().toCharArray());
                KeyManager[] keyManagers = kmf.getKeyManagers();
                TrustManager[] tm ={new MyX509TrustManager()};
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(keyManagers, tm, new java.security.SecureRandom());
                String urladdress = "https://api.mch.weixin.qq.com/secapi/pay/refund";
                URL url = new URL(urladdress);

                ProductRefund productRefund = new ProductRefund("4200000136201807091160672239", "20180709132620crem", "20180709132620crem", 1, 1);
                Map<String, String> data = productRefund.TtoMap();
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn instanceof HttpURLConnection) {
                    ((HttpsURLConnection) conn).setSSLSocketFactory(sslContext.getSocketFactory());
                    ((HttpsURLConnection) conn).setHostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String arg0, SSLSession arg1) {
                            return false;
                        }
                    });

                }
                String wx_xml = WXPayUtil.mapToXml(data);
                byte[] xml = wx_xml.getBytes();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                // 设置HTTP请求的头字段
                conn.setRequestProperty("Accept", "text/xml,text/javascript,text/html");
                conn.setRequestProperty("User-Agent", "wxpay sdk java v1.0 " + config.getMchID());
                conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8"); // 内容类型
                //conn.setRequestProperty("Content-Length", String.valueOf(xml.length)); // 实体内容的长度
                conn.getOutputStream().write(xml);
                conn.getOutputStream().flush();
                conn.getOutputStream().close();

                if (conn.getResponseCode() != 200)
                    throw new RuntimeException("请求url失败");

                InputStream is = conn.getInputStream(); // 获取返回的数据
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int len;
                while ((len = is.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                String string = out.toString("UTF-8");
                //result = string;
                out.close();
            } finally {
               // inStream.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private class MyX509TrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {


        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {


        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {

            return null;
        }



    }
}
