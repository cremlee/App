package android.luna.Data.Interface;

import android.luna.Data.DAO.EspressoDao;
import android.luna.Data.DAO.FilterBrewDao;
import android.luna.Data.DAO.IngredientDao;
import android.luna.Data.DAO.InstantDao;
import android.luna.Data.DAO.MonoDao;
import android.luna.Data.DAO.WaterDao;

/**
 * Created by Lee.li on 2018/2/26.
 */

public interface IingredientFactory {
    FilterBrewDao getFilterBrewDao();
    InstantDao getInstantDao();
    WaterDao getWaterDao();
    IngredientDao getIngredientDao();
    EspressoDao getEspressoDao();
    MonoDao getMonoDao();
}
