package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.ICanisterStock;
import android.luna.Data.Interface.IMachineStock;
import android.luna.Data.Interface.IWasterbinStock;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Data.module.CanisterItemStock;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.WasterBinStock;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lee.li on 2018/6/6.
 */

public class StockFactoryDao extends BaseDaobak implements IMachineStock {
    public StockFactoryDao(Context context, CremApp app) {
        super(context, app);
        beverageFactoryDao = new BeverageFactoryDao(context,app);
    }
    private ICanisterStock _canisterStock;
    private IWasterbinStock _wasterbinStock;

    private BeverageFactoryDao beverageFactoryDao;

    public void updateCanisterStockByPid(int pid)
    {
        List<BeverageIngredient> beverageIngredientList = beverageFactoryDao.getBeverageIngerdient().queryforbeveragepid(pid);
        if(beverageIngredientList!=null && beverageIngredientList.size()>0)
        {
            int powerType;
            int powderAmount;
            for (BeverageIngredient ingredint:beverageIngredientList ) {
                switch (ingredint.getIngredientType())
                {
                    case Ingredient.TYPE_ESPRESSO:
                        IngredientEspresso espresso = beverageFactoryDao.getingredientFactoryDao().getEspressoDao().findByT(ingredint.getIngredientPid());
                        if(espresso!=null)
                        {
                            powerType = espresso.getPowdertype();
                            powderAmount = (int)(espresso.getPowderamount()*ingredint.getScaleUp()/10);
                           CanisterItemStock item = getCanisterStockDao().queryByPid(powerType);
                            if(item!=null)
                            {
                                int value = item.getStock()-powderAmount>0?(int)((item.getStock()-powderAmount)):0;
                                item.setStock(value);
                                getCanisterStockDao().update(item);
                            }
                        }
                        break;
                    case Ingredient.TYPE_FILTER_BREW:
                        break;
                    case  Ingredient.TYPE_INSTANT:
                        IngredientInstant instant =  beverageFactoryDao.getingredientFactoryDao().getInstantDao().findByT(ingredint.getIngredientPid());
                        if(instant!=null)
                        {
                            powerType = instant.getPacket1Type();
                            powderAmount = (int)(instant.getPacket1Amount()*ingredint.getScaleUp()/10);
                            CanisterItemStock item = getCanisterStockDao().queryByPid(powerType);
                            if(item!=null)
                            {
                                int value = item.getStock()-powderAmount>0?((item.getStock()-powderAmount)):0;
                                item.setStock(value);
                                getCanisterStockDao().update(item);
                            }
                        }
                        break;
                    case Ingredient.TYPE_FILTER_BREW_ADVANCE:
                        break;
                    case Ingredient.TYPE_MILK:
                        break;
                    case Ingredient.TYPE_WATER:
                        break;
                }
            }
        }
    }
    @Override
    public ICanisterStock getCanisterStockDao() {
        if (_canisterStock ==null)
        {
            _canisterStock = new ICanisterStock() {
                @Override
                public List<CanisterItemStock> queryall() {
                    try {
                        return getHelper().get_canisterItemStocksDao().queryForAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void cleartable() {
                    try {
                        getHelper().get_canisterItemStocksDao().delete(getHelper().get_canisterItemStocksDao().queryForAll());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void create(CanisterItemStock canisterItemStock) {
                    try {
                        getHelper().get_canisterItemStocksDao().create(canisterItemStock);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void update(CanisterItemStock canisterItemStock) {
                    try {
                        getHelper().get_canisterItemStocksDao().update(canisterItemStock);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public CanisterItemStock queryByPid(int pid) {
                    QueryBuilder<CanisterItemStock, Integer> builder = getHelper().get_canisterItemStocksDao().queryBuilder();
                    try {
                        builder.where().eq("pid", pid);
                        return builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }
        return _canisterStock;
    }

    @Override
    public IWasterbinStock getWasterbinStockDao() {
        if(_wasterbinStock == null)
        {
            _wasterbinStock = new IWasterbinStock() {
                @Override
                public void create(WasterBinStock wasterBinStock) {
                    if(wasterBinStock.getId()!=1)
                    {
                        return;
                    }
                    try {
                        getHelper().get_wasterBinStocksDao().create(wasterBinStock);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void update(WasterBinStock wasterBinStock) {
                    try {
                        getHelper().get_wasterBinStocksDao().update(wasterBinStock);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public WasterBinStock queryByPid(int pid) {
                    WasterBinStock ret =null;
                    try {
                         ret = getHelper().get_wasterBinStocksDao().queryForId(1);
                        if(ret==null || (ret.getPid()!=99 && ret.getGroup()!=99))
                        {
                            ret =null;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return ret;
                }
            };
        }
        return _wasterbinStock;
    }
}
