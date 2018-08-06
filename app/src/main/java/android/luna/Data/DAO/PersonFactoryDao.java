package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IPersonDao;
import android.luna.Data.Interface.IPersonDrink;
import android.luna.Data.Interface.IPersonItem;
import android.luna.Data.module.PersonDrink;
import android.luna.Data.module.PersonItem;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lee.li on 2018/4/25.
 */

public class PersonFactoryDao extends BaseDaobak implements IPersonDao {

    private IPersonItem _personItemDao = null;
    private IPersonDrink _personDrinkDao = null;

    public PersonFactoryDao(Context context, CremApp app) {
        super(context, app);
    }



   /* public void RemovePersonFromList(PersonItem p)
    {
        int user = p.getId();
        getPersonItemDao().delete(user);
    }*/


    @Override
    public IPersonItem getPersonItemDao() {
        if(_personItemDao == null)
            _personItemDao = new IPersonItem() {
                @Override
                public List<PersonItem> quryallRecord() {
                    try {
                        return getHelper().get_PersonItemDao().queryForAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public PersonItem qury(int id) {
                    try {
                        return getHelper().get_PersonItemDao().queryForId(id);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void update(PersonItem personItem) {
                    try {
                         getHelper().get_PersonItemDao().update(personItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void create(PersonItem personItem) {
                    try {
                        getHelper().get_PersonItemDao().create(personItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void delete(int id) {
                    try {
                        getHelper().get_PersonItemDao().deleteById(id);
                        DeleteBuilder<PersonDrink, Integer> builder =getHelper().get_PersonDrinkDao().deleteBuilder();
                        builder.where().eq("uid", id);
                        builder.delete();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };
        return _personItemDao;
    }

    @Override
    public IPersonDrink getPersonDrinkDao() {
        if(_personDrinkDao == null)
            _personDrinkDao =new IPersonDrink() {
                @Override
                public List<PersonDrink> quryallRecord(int id) {
                    try {
                        QueryBuilder<PersonDrink, Integer> builder = getHelper().get_PersonDrinkDao().queryBuilder();
                        builder.where().eq("uid",id);
                        return builder.query();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public void updateorNew(PersonDrink a) {
                    try {
                        getHelper().get_PersonDrinkDao().createOrUpdate(a);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public List<PersonDrink> quryallRecord() {
                    return null;
                }

                @Override
                public PersonDrink qury(int id) {
                    return null;
                }

                @Override
                public void update(PersonDrink personDrink) {
                    try {
                        getHelper().get_PersonDrinkDao().update(personDrink);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void create(PersonDrink personDrink) {
                    try {
                        getHelper().get_PersonDrinkDao().create(personDrink);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void delete(int id) {
                    DeleteBuilder<PersonDrink, Integer> builder =getHelper().get_PersonDrinkDao().deleteBuilder();
                    try {
                        builder.where().eq("uid", id);
                        builder.delete();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            };
       return _personDrinkDao;
    }
}
