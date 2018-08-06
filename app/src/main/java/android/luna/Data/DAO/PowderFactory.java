package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IPowderDao;
import android.luna.Data.Interface.IPowderNutrition;
import android.luna.Data.Interface.IPowerItem;
import android.luna.Data.module.Powder.PowderItem;
import android.luna.Data.module.Powder.PowderNutrition;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lee.li on 2018/5/17.
 */

public class PowderFactory extends BaseDaobak implements IPowderDao {
    public PowderFactory(Context context, CremApp app) {
        super(context, app);
    }
    private IPowerItem _powerItem=null;
    private IPowderNutrition _powderNutrition=null;
    @Override
    public IPowerItem getPowerItemDao() {
        if(_powerItem ==null) {
            _powerItem = new IPowerItem(){
                @Override
                public  void updatePowderSelectedSt(int pid ,int st)
                {
                    try {
                        UpdateBuilder<PowderItem, Integer> builder = getHelper().get_powderItemDao().updateBuilder();
                        builder.updateColumnValue("selected",st).where().eq("pid",pid);
                        builder.update();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public String getNamebyPid(int pid) {
                    PowderItem ret =query(pid);
                    if(ret!=null)
                        return ret.getName();
                    return "unknown";
                }

                @Override
                public void InitPowderItems() {
                    String sql = "DELETE FROM tb_powder_item";
                    try {
                        getHelper().get_powderItemDao().executeRawNoArgs(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    PowderItem tmp =new PowderItem(1,"Espresso",1,0,0,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(2,"Chocolate",1,0,0,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(3,"Whitening",1,780,1170,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(4,"Milk powder",1,780,1170,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(5,"Milk granulate",1,780,1170,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(6,"Sugar",1,780,1170,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(7,"Instant coffee",1,780,1170,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(8,"Soup",1,780,1170,3,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                     tmp =new PowderItem(129,"bean",1,780,1170,2,0,1);
                    try {
                        getHelper().get_powderItemDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public List<PowderItem> queryallbygroup(int group) {
                    try {
                        QueryBuilder<PowderItem, Integer> builder = getHelper().get_powderItemDao().queryBuilder();
                        builder.where().eq("group",group);
                        return builder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public PowderItem query(int pid) {
                    try {
                        QueryBuilder<PowderItem, Integer> builder = getHelper().get_powderItemDao().queryBuilder();
                        builder.where().eq("pid",pid);
                        return builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void update(PowderItem powderItem) {
                    try {
                         getHelper().get_powderItemDao().update(powderItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void create(PowderItem powderItem) {
                    try {
                        getHelper().get_powderItemDao().create(powderItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void createOrupdate(PowderItem powderItem) {
                    try {
                        getHelper().get_powderItemDao().createOrUpdate(powderItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void delete(int pid) {

                }

                @Override
                public void delete(PowderItem powderItem) {
                    try {
                        getHelper().get_powderItemDao().delete(powderItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    getPowderNutritionDao().delete(powderItem.getPid());
                }

                @Override
                public List<PowderItem> queryall() {
                    try {
                        return getHelper().get_powderItemDao().queryForAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }
        return _powerItem;
    }
    @Override
    public IPowderNutrition getPowderNutritionDao() {
        if(_powderNutrition ==null)
        {
            _powderNutrition = new IPowderNutrition() {
                @Override
                public void InitPowderNutrition() {
                    String sql = "DELETE FROM tb_powder_nutrition";
                    try {
                        getHelper().get_powderNutritionDao().executeRawNoArgs(sql);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    PowderNutrition tmp =new PowderNutrition(1,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(2,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(3,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(4,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(5,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(6,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(7,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(8,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    tmp =new PowderNutrition(129,1,1,0,0,3);
                    try {
                        getHelper().get_powderNutritionDao().create(tmp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public PowderNutrition query(int pid) {
                    try {
                        QueryBuilder<PowderNutrition, Integer> builder = getHelper().get_powderNutritionDao().queryBuilder();
                        builder.where().eq("pid",pid);
                        return builder.queryForFirst();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void update(PowderNutrition powderNutrition) {
                    try {
                        getHelper().get_powderNutritionDao().createOrUpdate(powderNutrition);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void create(PowderNutrition powderNutrition) {
                    try {
                        getHelper().get_powderNutritionDao().createOrUpdate(powderNutrition);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void createOrupdate(PowderNutrition powderNutrition) {
                    try {
                        getHelper().get_powderNutritionDao().createOrUpdate(powderNutrition);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void delete(int pid) {
                    DeleteBuilder<PowderNutrition, Integer> builder =getHelper().get_powderNutritionDao().deleteBuilder();
                    try {
                        builder.where().eq("pid", pid);
                        builder.delete();
                    }
                    catch (Exception w)
                    {
                    }
                }

                @Override
                public void delete(PowderNutrition powderNutrition) {
                    try {
                        getHelper().get_powderNutritionDao().delete(powderNutrition);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public List<PowderNutrition> queryall() {
                    return null;
                }
            };
        }
        return _powderNutrition;
    }
    public void  InitPowderFactory()
    {
        getPowerItemDao().InitPowderItems();
        getPowderNutritionDao().InitPowderNutrition();
    }
}
