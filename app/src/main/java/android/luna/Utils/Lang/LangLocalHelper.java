package android.luna.Utils.Lang;

import android.luna.Data.module.DrinkName;
import android.luna.Data.module.LangItem;
import android.luna.Utils.FileHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by Lee.li on 2018/3/8.
 */

public class LangLocalHelper {
    public static boolean ExportLangfile(List<DrinkName> langItemList,String exportpath)
    {
        Gson gson = new Gson();
        String str = gson.toJson(langItemList);
        String filepath = exportpath+FileHelper.PATH_LANG+"drinkname.lang";
        File file = new File(filepath);
        if(!file.exists())
        {
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
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        return true;
    }

    public static  List<LangItem> getlanglistfromfile(int localinfo)
    {
        List<LangItem> ret;
        File file = new File(FileHelper.PATH_LANG+"en.lang");
        if(!file.exists())
            return null;
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Gson gson = new Gson();
            String line = "";
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            ret = gson.fromJson(sb.toString(),new TypeToken<List<LangItem>>() {}.getType());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public  static List<DrinkName> getAllDrinkfromjson(String jsonpath)
    {
        List<DrinkName> ret;
        File file = new File(jsonpath);
        if(!file.exists())
            return null;
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            Gson gson = new Gson();
            String line = "";
            while((line = br.readLine())!=null){
                sb.append(line);
            }
            ret = gson.fromJson(sb.toString(),new TypeToken<List<DrinkName>>() {}.getType());
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
