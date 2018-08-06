package android.luna.Utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Lee.li on 2018/2/5.
 */

public class InstallPkg {
    public static int installBySlient(Context context, String filePath) {
        int result = 0;
        try {
            File file = new File(filePath);
            if (filePath == null || filePath.length() == 0
                    || (file = new File(filePath)) == null
                    || file.length() <= 0 || !file.exists() || !file.isFile()) {
                return 1;
            }
            String[] args = {"pm","install","-r", filePath};
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = null;
            BufferedReader successResult = null;
            BufferedReader errorResult = null;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder errorMsg = new StringBuilder();
            try {
                process = processBuilder.start();
                successResult = new BufferedReader(new InputStreamReader(
                        process.getInputStream()));
                errorResult = new BufferedReader(new InputStreamReader(
                        process.getErrorStream()));
                String s;

                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }

                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
                result = 2;
            } catch (Exception e) {
                e.printStackTrace();
                result = 2;
            } finally {
                try {
                    if (successResult != null) {
                        successResult.close();
                    }
                    if (errorResult != null) {
                        errorResult.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (process != null) {
                    process.destroy();
                }
            }

            if (successMsg.toString().contains("Success")
                    || successMsg.toString().contains("success")) {
                result = 1;
                //sendToUI("安装成功result = 1");
            } else {
                result = 2;
            }
        } catch (Exception e) {
            result = -1;
        }
        return result;
    }

}
