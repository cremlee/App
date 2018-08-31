package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IingredientFactory;

/**
 * Created by Lee.li on 2018/2/26.
 */

public class IngredientFactoryDao extends BaseDaobak implements IingredientFactory {
    public IngredientFactoryDao(Context context, CremApp app) {
        super(context, app);
    }
    private FilterBrewDao _filterBrewDao=null;
    private InstantDao _instantDao=null;
    private WaterDao _waterDao=null;
    private IngredientDao _ingredientDao=null;
    private EspressoDao _EspressoDao =null;
    private MonoDao _monoDao=null;
    @Override
    public FilterBrewDao getFilterBrewDao() {
        if(_filterBrewDao == null)
            _filterBrewDao = new FilterBrewDao(getmContext(),getApp());
        return  _filterBrewDao;
    }

    @Override
    public InstantDao getInstantDao() {
        if(_instantDao == null)
            _instantDao = new InstantDao(getmContext(),getApp());
        return  _instantDao;
    }

    @Override
    public WaterDao getWaterDao() {
        if(_waterDao ==null)
            _waterDao =new WaterDao(getmContext(),getApp());
        return _waterDao;
    }

    @Override
    public IngredientDao getIngredientDao() {
        if(_ingredientDao ==null)
            _ingredientDao=new IngredientDao(getmContext(),getApp());
        return _ingredientDao;
    }

    @Override
    public EspressoDao getEspressoDao() {
        if(_EspressoDao ==null)
            _EspressoDao=new EspressoDao(getmContext(),getApp());
        return _EspressoDao;
    }

    @Override
    public MonoDao getMonoDao() {
        if(_monoDao==null)
            _monoDao = new MonoDao(getmContext(),getApp());
        return  _monoDao;
    }


}
