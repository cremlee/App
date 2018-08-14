package android.luna.Data.Interface;

import android.luna.Data.module.BeverageBasic;
import android.luna.Data.module.BeverageCount;

import java.util.List;

/**
 * Created by Lee.li on 2018/2/12.
 */

public interface IBeverageCount extends IBeverage<BeverageCount> {
    List<BeverageBasic> getTopFive();
}
