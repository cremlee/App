package android.luna.Data.Interface;
import android.luna.Data.module.BeverageUi;

import java.util.List;

/**
 * Created by Lee.li on 2018/2/12.
 */
public interface IBeverageUi extends IBeverage<BeverageUi> {
    List<BeverageUi> getShowSortbyIndex();
    List<BeverageUi> getHide();
}
