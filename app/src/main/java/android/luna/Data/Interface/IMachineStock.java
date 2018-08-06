package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/6/6.
 */

public interface IMachineStock {
    ICanisterStock getCanisterStockDao();
    IWasterbinStock getWasterbinStockDao();
}
