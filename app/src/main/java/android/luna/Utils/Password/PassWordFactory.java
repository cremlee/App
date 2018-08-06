package android.luna.Utils.Password;

import android.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Lee.li on 2018/5/2.
 */

public class PassWordFactory {
   private static final int min = 100000;
   private static final int max = 999999;
    private static String fact ="evopsw20180502ll";
    private static final String ENCRYPTION_MANNER = "DESede";

   public static String generatePsw()
   {
       Random random =new Random();
       return (random.nextInt(max)%(max-min+1) + min)+"";
   }
   public static String getEncryptPassword(String psw)
   {
       SecretKey secretKey = new SecretKeySpec(fact.getBytes(), ENCRYPTION_MANNER);
       Cipher cipher = null;
       try {
           cipher = Cipher.getInstance(ENCRYPTION_MANNER);
           cipher.init(Cipher.ENCRYPT_MODE, secretKey);
           byte[] encrypt = cipher.doFinal(psw.getBytes());
           return new String(Base64.encode(encrypt, Base64.DEFAULT), "UTF-8");
       } catch (Exception e) {
           e.printStackTrace();
       }
       return null;
   }

    public static String getdecryptPassword(String miwen) throws Exception
    {
        SecretKey secretKey = new SecretKeySpec(fact.getBytes(), ENCRYPTION_MANNER);
        Cipher cipher = Cipher.getInstance(ENCRYPTION_MANNER);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] bytes = Base64.decode(miwen.getBytes("UTF-8"), Base64.DEFAULT);
        byte[] plain = cipher.doFinal(bytes);
        //解密结果转码
        return  new String(plain, "UTF-8");
    }

}

