package android.luna.Data.Interface;

import java.util.List;

/**
 * Created by Lee.li on 2018/4/25.
 */

public interface IPerson<T> {
    List<T> quryallRecord();
    T qury(int id);
    void update(T t);
    void create(T t);
    void delete(int id);
}
