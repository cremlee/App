package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/8/7.
 */

public interface ISystemParam<T>{
    void loaddefault();
    void modify(T t);
    void delete();
    void create(T t);
    T query();
}
