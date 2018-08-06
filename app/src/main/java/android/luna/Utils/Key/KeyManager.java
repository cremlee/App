package android.luna.Utils.Key;

import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Logger.EvoTrace;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Lee.li on 2018/7/17.
 */

public class KeyManager {

    private static final int KEY_APPID =1;
    private static final int KEY_PV=2;
    private static final int KEY_PB =3;
    private static final int KEY_AU =5;
    private static final int KEY_MCHID =4;

    private static final String PAY_AUTH_FILE_ENCRYPTKEY = "Crem1234";
    private static final String W_PATH = FileHelper.getSDCardDirPath()+"crem/machine/config/pay.wuk";
    private static final String A_PATH = FileHelper.getSDCardDirPath()+"crem/machine/config/pay.auk";
    public static Wechatkey getWechatkey()
    {
        String data =null;
        Wechatkey wechatkey =new Wechatkey();
        try {
            String data1 = AndroidUtils_Ext.bufferedReader(W_PATH);
            data = AndroidUtils_Ext.decrypt(data1, "Crem1234");
            String[] strings = data.split(" ");
            int[] bufs = new int[strings.length];
            for (int i=0;i<strings.length;i++)
            {
                bufs[i] = Integer.valueOf(strings[i],16);
            }
            int index=0;
            wechatkey.info = (new String(bufs,index,20)).trim();
            index+=20;
            for(;index<strings.length;)
            {
                int t = bufs[index++];
                int l = (bufs[index]<<8)+bufs[index+1];
                index+=2;
                String v = (new String(bufs,index,l)).trim();
                index+=l;
                switch (t)
                {
                    case KEY_APPID:
                        wechatkey.appid = v;
                        break;
                    case KEY_PV:
                        wechatkey.pvkey =v;
                        break;
                    case KEY_MCHID:
                        wechatkey.mchid =v;
                        break;
                }
            }
            EvoTrace.e("key","wechatkey = "+wechatkey.toString());
        } catch (Exception e) {
            return  null;
        }
        return  wechatkey;
    }

    public static Alipaykey getAlipaykey()
    {
        String data =null;
        Alipaykey alipaykey =new Alipaykey();
        try {
            String data1 = AndroidUtils_Ext.bufferedReader(W_PATH);
            data = AndroidUtils_Ext.decrypt(data1, "Crem1234");
            String[] strings = data.split(" ");
            int[] bufs = new int[strings.length];
            for (int i=0;i<strings.length;i++)
            {
                bufs[i] = Integer.valueOf(strings[i],16);
            }
            int index=0;
            alipaykey.info = (new String(bufs,index,20)).trim();
            index+=20;
            for(;index<strings.length;)
            {
                int t = bufs[index++];
                int l = (bufs[index]<<8)+bufs[index+1];
                index+=2;
                String v = (new String(bufs,index,l)).trim();
                index+=l;
                switch (t)
                {
                    case KEY_APPID:
                        alipaykey.appid = v;
                        break;
                    case KEY_PV:
                        alipaykey.pvkey =v;
                        break;
                    case KEY_AU:
                        alipaykey.authtype =v;
                        break;
                    case KEY_PB:
                        alipaykey.pbkey =v;
                         break;

                }
            }
            EvoTrace.e("key","alipaykey = "+alipaykey.toString());
        } catch (Exception e) {
            return  null;
        }
        return  alipaykey;
    }
}
