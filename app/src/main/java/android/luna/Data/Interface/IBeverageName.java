package android.luna.Data.Interface;

import android.luna.Data.module.DrinkName;

import java.util.List;

/**
 * Created by Lee.li on 2018/2/12.
 */

public interface IBeverageName {
    List<DrinkName> queryallByPid(int pid);
    List<DrinkName> queryallByLocale();
    List<DrinkName> queryall();
    DrinkName queryByPid(int pid);
    void modify(DrinkName a);
    void create(DrinkName a);
    int deleteByPid(int pid);
    int getlocalinfo();
    String getDrinkname(int pid);
    String getDrinkname(int pid,int lang);
    void clear();

}
