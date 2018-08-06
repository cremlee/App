package android.luna.Data.Interface;

import android.luna.Data.module.CanisterItemStock;

import java.util.List;

/**
 * Created by Lee.li on 2018/6/6.
 */

public interface ICanisterStock extends IStock<CanisterItemStock> {
    List<CanisterItemStock> queryall();
    void cleartable();
}
