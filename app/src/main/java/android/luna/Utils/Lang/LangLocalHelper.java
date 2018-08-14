package android.luna.Utils.Lang;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.luna.Data.module.DrinkName;
import android.luna.Data.module.LangItem;
import android.luna.Utils.FileHelper;
import android.os.Build;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lee.li on 2018/3/8.
 */

public class LangLocalHelper {
    public static boolean ExportLangfile(List<DrinkName> langItemList, String exportpath) {
        Gson gson = new Gson();
        String str = gson.toJson(langItemList);
        String filepath = exportpath + FileHelper.PATH_LANG + "drinkname.lang";
        File file = new File(filepath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            //file.delete();

            FileOutputStream out = new FileOutputStream(file);
            out.write(str.getBytes());
            out.getFD().sync();
            out.flush();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<LangItem> getlanglistfromfile(int localinfo) {
        List<LangItem> ret;
        File file = new File(FileHelper.PATH_LANG + "en.lang");
        if (!file.exists())
            return null;
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Gson gson = new Gson();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ret = gson.fromJson(sb.toString(), new TypeToken<List<LangItem>>() {
            }.getType());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<DrinkName> getAllDrinkfromjson(String jsonpath) {
        List<DrinkName> ret;
        File file = new File(jsonpath);
        if (!file.exists())
            return null;
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Gson gson = new Gson();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ret = gson.fromJson(sb.toString(), new TypeToken<List<DrinkName>>() {
            }.getType());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getlocalinfo() {
        int ret = 0;
        switch (Locale.getDefault().getLanguage()) {
            case "de":
                ret = 6;
                break;
            case "en":
                ret = 4;
                break;
            case "sv":
                ret = 8;
                break;
            case "nb":
                ret = 3;
                break;
            case "fi":
                ret = 5;
                break;
            case "no":
                ret = 7;
                break;
            case "zh":
                ret = 1;
                break;
            case "nl":
                ret = 2;
                break;
        }
        return ret;
    }

    public static Locale getlocalinfo(int lang) {
        Locale ret = Locale.ENGLISH;
        switch (lang) {
            case 6:
                ret = Locale.ENGLISH;
                break;
            case 4:
                ret = Locale.ENGLISH;
                break;
            case 8:
                ret = Locale.ENGLISH;
                break;
            case 3:
                ret = Locale.ENGLISH;
                break;
            case 5:
                ret = Locale.ENGLISH;
                break;
            case 7:
                ret = Locale.ENGLISH;
                break;
            case 1:
                ret = Locale.SIMPLIFIED_CHINESE;
                break;
            case 2:
                ret = Locale.ENGLISH;
                break;
        }
        return ret;
    }

    public static void updateLocale(Context pContext, Locale pNewUserLocale) {
        Configuration _Configuration = pContext.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            _Configuration.setLocale(pNewUserLocale);
        } else {
            _Configuration.locale = pNewUserLocale;
        }
        DisplayMetrics _DisplayMetrics = pContext.getResources().getDisplayMetrics();
        pContext.getResources().updateConfiguration(_Configuration, _DisplayMetrics);
    }

}
