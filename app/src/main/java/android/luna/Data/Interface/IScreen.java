package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/3/1.
 */

public interface IScreen<T> {
     T query();
     int update(T t);

}
