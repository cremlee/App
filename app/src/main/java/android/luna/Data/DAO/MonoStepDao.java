package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.module.FiterBrewStep;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientMonoProcess;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

public class MonoStepDao extends BaseDaobak {
    public MonoStepDao(Context context, CremApp app) {
        super(context, app);
    }
    public List<IngredientMonoProcess> getmonosteps(int pid)
    {
        List<IngredientMonoProcess> tmp =null;
        try {
            QueryBuilder<IngredientMonoProcess, Integer> builder = getHelper().getingredientMonoProcessesDao().queryBuilder();
            builder.where().eq("pid", pid);
            builder.orderBy("stepindex", true);
            tmp = builder.query();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tmp;
    }
    public int getsteptime(int pid)
    {
        int ret =0;
        List<IngredientMonoProcess> tmp = getmonosteps(pid);
        if(tmp!=null)
        {
            for(IngredientMonoProcess item:tmp)
            {
                ret+=(item.getBubpump_tm()+item.getInfusion_tm()+item.getPress_tm()+item.getOutlet_tm());
            }
        }
        return ret;
    }

    private void updatemonostep(IngredientMonoProcess a)
    {
        try {
            getHelper().getingredientMonoProcessesDao().update(a);} catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void deletemonostep(IngredientMonoProcess a)
    {
        try {
            DeleteBuilder<IngredientMonoProcess, Integer> builder = getHelper().getingredientMonoProcessesDao().deleteBuilder();
            builder.where().eq("id", a.getId());
            builder.delete();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void clearmonostepbypid(int ingredientpid)
    {
        try {
            DeleteBuilder<IngredientMonoProcess, Integer> builder = getHelper().getingredientMonoProcessesDao().deleteBuilder();
            builder.where().eq("pid", ingredientpid);
            builder.delete();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void updatemonosteplist(int pid,List<IngredientMonoProcess> a)
    {
        clearmonostepbypid(pid);
        if(a!=null && a.size()>0) {
            try {
                for (IngredientMonoProcess item : a) {
                    item.setPid(pid);
                    getHelper().getingredientMonoProcessesDao().create(item);
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
