package android.luna.Data.Interface;
import android.luna.Data.module.BeverageGroup;

import java.util.List;

/**
 * Created by Lee.li on 2018/2/12.
 */
public interface IBeverageGroup extends IBeverage<BeverageGroup> {
   List<BeverageGroup> querylistbyPid(int pid);
   int creategroupid();
   void deletegroup(int id);
   BeverageGroup queryGroupItem(int id);
}
