package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/7/31.
 */

public interface IPayment <T>{
    T Query();
    void Update(T t);
}
