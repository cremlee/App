package android.luna.Data.Interface;

import java.util.List;

/**
 * Created by Lee.li on 2018/5/17.
 */

public interface IPowder<T> {
    T query(int pid);
    void update(T t);
    void create(T t);
    void createOrupdate(T t);
    void delete(int pid);
    void delete(T t);
    List<T> queryall();
}
