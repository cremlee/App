package android.luna.Data.Interface;

import android.luna.Data.module.Powder.PowderItem;

import java.util.List;

/**
 * Created by Lee.li on 2018/5/17.
 */

public interface IPowerItem extends IPowder<PowderItem> {
    List<PowderItem> queryallbygroup(int group);
    void updatePowderSelectedSt(int a,int b);
    String getNamebyPid(int pid);
    void InitPowderItems();

}
