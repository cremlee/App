package android.luna.Data.DAO;

import java.sql.SQLException;
import java.util.List;
import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.module.FiterBrewStep;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;

public class FilterBrewStepDao extends BaseDaobak {
	public FilterBrewStepDao(Context context,CremApp app) {
		super(context,app);
	}

	public List<FiterBrewStep> getFilterBrewStep(int pid)
	{
		List<FiterBrewStep> tmp =null;
		try {
			QueryBuilder<FiterBrewStep, Integer> builder = getHelper().getFiterBrewStep().queryBuilder();
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
		List<FiterBrewStep> tmp = getFilterBrewStep(pid);
		if(tmp!=null)
		{
			for(FiterBrewStep item:tmp)
			{
				ret+=item.getWait()*100;
			}
		}
		return ret;
	}
	
	private void UpdateFiterBrewStep(FiterBrewStep a)
	{
		try {
			getHelper().getFiterBrewStep().update(a);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void DeleteFiterBrewStep(FiterBrewStep a)
	{
		try {
			DeleteBuilder<FiterBrewStep, Integer> builder = getHelper().getFiterBrewStep().deleteBuilder();
			builder.where().eq("id", a.getId());
			builder.delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void ClearTableforPid(int pid)
	{
		try {
			DeleteBuilder<FiterBrewStep, Integer> builder = getHelper().getFiterBrewStep().deleteBuilder();
			builder.where().eq("pid", pid);
			builder.delete();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void UpdateFiterBrewStepList(int pid,List<FiterBrewStep> a)
	{
		ClearTableforPid(pid);
		try {
			for(FiterBrewStep item:a)
			{
				item.setPid(pid);
				getHelper().getFiterBrewStep().create(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
