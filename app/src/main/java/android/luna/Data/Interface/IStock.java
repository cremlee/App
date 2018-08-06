package android.luna.Data.Interface;

import java.util.List;

/**
 * Created by Lee.li on 2018/6/6.
 */

public interface IStock<T> {
    void create(T t);
    void update(T t);
    T queryByPid(int pid);

}
