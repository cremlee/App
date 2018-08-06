package android.luna.Data.module.Payment.Wechat;

import android.luna.SDK.Wechat.WXPayConfig;
import android.luna.SDK.Wechat.WXPayUtil;
import android.luna.Utils.Logger.EvoTrace;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

/**
 * Created by Lee.li on 2018/7/10.
 */

public class RefundTesthttpok extends Thread {
    private WXPayConfig config ;
    public RefundTesthttpok(WXPayConfig config)
    {
        this.config =config;
    }
    private OkHttpClient client;
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";//证书类型
    private static final String KEY_STORE_PASSWORD = "1475691702";//证书密码（应该是客户端证书密码）
    private TLSSocketFactory tlsSocketFactory;
        @Override
    public void run() {
        test();
    }


      class TLSSocketFactory extends SSLSocketFactory {
        private SSLSocketFactory delegate;
          public SSLSocketFactory getDelegate(){return delegate;}
        public TLSSocketFactory() throws Exception {
            InputStream client_input =config.getCertStream();
            SSLContext sslContext = SSLContext.getInstance("TLS");
            KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE_P12);
            keyStore.load(client_input, KEY_STORE_PASSWORD.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, KEY_STORE_PASSWORD.toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(),null , new SecureRandom());
            delegate = sslContext.getSocketFactory();
        }

        @Override
        public String[] getDefaultCipherSuites() {
            return delegate.getDefaultCipherSuites();
        }

        @Override
        public String[] getSupportedCipherSuites() {
            return delegate.getSupportedCipherSuites();
        }

        @Override
        public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(s, host, port, autoClose));
        }

        @Override
        public Socket createSocket(String host, int port) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(host, port, localHost,
                    localPort));
        }

        @Override
        public Socket createSocket(InetAddress host, int port) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(host, port));
        }

        @Override
        public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
            return enableTLSOnSocket(delegate.createSocket(address, port, localAddress, localPort));
        }

        private Socket enableTLSOnSocket(Socket socket) {
            if (socket != null && (socket instanceof SSLSocket)) {

                String[] protocols = ((SSLSocket) socket).getEnabledProtocols();
                List<String> supports = new ArrayList<>();
                if (protocols != null && protocols.length > 0) {
                    supports.addAll(Arrays.asList(protocols));
                }
                Collections.addAll(supports, "TLSv1.1", "TLSv1.2");
                ((SSLSocket) socket).setEnabledProtocols(supports.toArray(new String[supports.size()]));
            }
            return socket;
        }
    }

    private void test() {

        try {
            //TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                    .tlsVersions(TlsVersion.TLS_1_1,TlsVersion.TLS_1_2)
                    .build();

            client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30,TimeUnit.SECONDS)
                    .sslSocketFactory(new TLSSocketFactory(),new MyX509TrustManager())
                    .connectionSpecs(Collections.singletonList(spec))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .build();

            String urladdress = "https://api.mch.weixin.qq.com/secapi/pay/refund";
            FormBody.Builder requestBody = new FormBody.Builder();
            ProductRefund productRefund = new ProductRefund("4200000136201807091160672239", "20180709132620crem", "20180709132620crem", 1, 1);
            Map<String, String> data = productRefund.TtoMap();
            for (String key : data.keySet()) {
                requestBody.add(key,data.get(key));
            }
            RequestBody requestBody1 = requestBody.build();
            Request request = new Request.Builder()
                    .url(urladdress)
                    .post(requestBody1)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    EvoTrace.e("pay","onFailure"+e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    EvoTrace.e("pay","onResponse"+response.toString());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private class MyX509TrustManager implements X509TrustManager {


        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
