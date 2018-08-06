package android.luna.Data.Interface;

import java.util.List;

/**
 * Created by Lee.li on 2018/2/12.
 */

public interface IBeverage<T> {
     T query(int pid);
     int update(T t);
     int create(T t);
     int delete(int pid);
     List<T> queryall();
}
