package android.luna.Data.DAO;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IIngredientDao;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientFilterBrew;
import com.j256.ormlite.stmt.QueryBuilder;

public class FilterBrewDao extends IngredientDao implements IIngredientDao<IngredientFilterBrew> {

	public FilterBrewDao(Context context,CremApp app) {
		super(context,app);
	}

	@Override
	public int create(IngredientFilterBrew filterBrew, int oldIngredientPid, int beveragePid) {
		try {
			int ingredientPid = createIngredient(filterBrew.getName(), Ingredient.TYPE_FILTER_BREW, filterBrew.getIsDefault());
			filterBrew.setPid(ingredientPid);
			getHelper().getIngredientFilterBrewDao().create(filterBrew);

			if (beveragePid != 0) {
				updateBeverageIngredient(ingredientPid, beveragePid, oldIngredientPid);
				// deleteBeverageIngredient(ingredientPid);
			}
			return ingredientPid;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void modify(IngredientFilterBrew filterBrew) {
		// 修改保存即可
		try {
			getHelper().getIngredientFilterBrewDao().update(filterBrew);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int ingredientPid = filterBrew.getPid();
		// 修改Ingredient表中的名字
		try {
			List<Ingredient> ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientPid);
			if (ingredients == null || ingredients.size() == 0) {
				return;
			}
			Ingredient intredient = ingredients.get(0);
			if (intredient != null) {
				intredient.setName(filterBrew.getName());
				getHelper().getIngredientDao().update(intredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, int ingredientPid) {
		try {
			getHelper().getIngredientFilterBrewDao().deleteById(id);
			deleteBeverageIngredient(ingredientPid);
			deleteIngredient(ingredientPid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IngredientFilterBrew findByT(int ingredientPid) {
		try {
			QueryBuilder<IngredientFilterBrew, Integer> builder = getHelper().getIngredientFilterBrewDao().queryBuilder();
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
			return getHelper().getIngredientFilterBrewDao().queryForAll().size();
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
			List<IngredientFilterBrew> brews = getHelper().getIngredientFilterBrewDao().queryForEq("name", name);
			if (brews == null || brews.size() == 0) {
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
		String ingredientName = "Copy of "+oldname+"(1)";
		List<IngredientFilterBrew> filterBrews = null;
		QueryBuilder<IngredientFilterBrew, Integer> queryBuilder = getHelper().getIngredientFilterBrewDao().queryBuilder();
		try {
			queryBuilder.where().like("name", "Copy of "+oldname+"(%)");
			filterBrews = queryBuilder.query();
			if (filterBrews == null || filterBrews.size() == 0) {
				return ingredientName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ingredientName;
		}
		int currentNum = 0;
		for (int i = 0; i < filterBrews.size(); i++) {
			IngredientFilterBrew brew = filterBrews.get(i);
			String name = brew.getName();
			if(name.startsWith("Copy of "+oldname+"(")) {
				int num = Integer.valueOf(name.substring(9+oldname.length(), name.length()-1));
				if (num > currentNum) {
					currentNum = num;
				}
			}
		}
		ingredientName = "Copy of "+oldname+"("+(currentNum + 1)+")";
		return ingredientName;
	}

	public IngredientFilterBrew copyFilterBrew(int ingredientPid,int ingredientNewPid, String newName) {
		try {
			List<IngredientFilterBrew> brews = getHelper().getIngredientFilterBrewDao().queryForEq("pid", ingredientPid);
			if (brews != null && brews.size() > 0) {
				IngredientFilterBrew brew = brews.get(0);
				brew.setId(0);
				brew.setPid(ingredientNewPid);
				brew.setIsDefault(0);
				brew.setCreatestatus(1);
				brew.setName(newName);
				getHelper().getIngredientFilterBrewDao().create(brew);
				return brew;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
