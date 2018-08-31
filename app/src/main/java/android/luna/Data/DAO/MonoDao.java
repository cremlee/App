package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IIngredientDao;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientMono;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;


public class MonoDao extends IngredientDao implements IIngredientDao<IngredientMono> {

	public MonoDao(Context context, CremApp app) {
		super(context,app);
	}

	@Override
	public int create(IngredientMono mono, int oldIngredientPid, int beveragePid) {
		try {
			int ingredientPid = createIngredient(mono.getName(), Ingredient.TYPE_MONO, mono.getIsDefault());
			mono.setPid(ingredientPid);
			getHelper().getIngredientMonoDao().create(mono);

			if (beveragePid != 0) {
				updateBeverageIngredient(ingredientPid, beveragePid, oldIngredientPid);
			}
			return ingredientPid;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void modify(IngredientMono mono) {
		// 修改保存即可
		try {
			getHelper().getIngredientMonoDao().update(mono);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int ingredientPid = mono.getPid();
		// 修改Ingredient表中的名字
		try {
			List<Ingredient> ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientPid);
			Ingredient intredient = ingredients.get(0);
			if (intredient != null) {
				intredient.setName(mono.getName());
				getHelper().getIngredientDao().update(intredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, int ingredientPid) {
		try {
			getHelper().getIngredientMonoDao().deleteById(id);
			deleteBeverageIngredient(ingredientPid);
			deleteIngredient(ingredientPid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IngredientMono findByT(int ingredientPid) {
		try {
			QueryBuilder<IngredientMono, Integer> builder = getHelper().getIngredientMonoDao().queryBuilder();
			builder.where().eq("pid", ingredientPid);
			return builder.queryForFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getItemcount() {
		try {
			return getHelper().getIngredientMonoDao().queryForAll().size();
		} catch (SQLException e) {
			return 0;
		}
	}

	/**
	 * 检测名字是否存在
	 * 
	 * @param name
	 * @return
	 */
	public boolean checkName(String name) {
		try {
			List<IngredientEspresso> water = getHelper().getIngredientEspressoDao().queryForEq("name", name);
			if (water == null || water.size() == 0) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 自动生成名字
	 * 
	 * @return
	 */
	public String autoCreateName(String oldname) {
		List<IngredientMono> monos = null;
		String ingredientName = "Copy of "+oldname+"(1)";
		QueryBuilder<IngredientMono, Integer> queryBuilder = getHelper().getIngredientMonoDao().queryBuilder();
		try {
			queryBuilder.where().like("name", "Copy of "+oldname+"(%)");
			monos = queryBuilder.query();
			if (monos == null || monos.size() == 0) {
				return ingredientName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ingredientName;
		}
		int currentNum = 0;
		for (int i = 0; i < monos.size(); i++) {
			IngredientMono mono = monos.get(i);
			String name = mono.getName();
			if(name.startsWith("Copy of "+oldname+"(")){
				int num = Integer.valueOf(name.substring(9+oldname.length(), name.length()-1));
				if (num > currentNum) {
					currentNum = num;
				}
			}
		}
		ingredientName = "Copy of "+oldname+"("+(currentNum + 1)+")";
		return ingredientName;
	}

	public IngredientMono copymono(int ingredientPid, int ingredientNewPid, String newName) {
		try {
			List<IngredientMono> monos = getHelper().getIngredientMonoDao().queryForEq("pid", ingredientPid);
			if (monos != null && monos.size() > 0) {
				IngredientMono mono = monos.get(0);
				mono.setId(0);
				mono.setIsDefault(0);
				mono.setPid(ingredientNewPid);
				mono.setName(newName);
				mono.setCreatestatus(1);
				getHelper().getIngredientMonoDao().create(mono);
				return mono;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}
