package android.luna.Data.DAO;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IIngredientDao;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientEspresso;

import com.j256.ormlite.stmt.QueryBuilder;


public class EspressoDao extends IngredientDao implements IIngredientDao<IngredientEspresso> {
	
	public EspressoDao(Context context,CremApp app) {
		super(context,app);
	}

	@Override
	public int create(IngredientEspresso espresso, int oldIngredientPid, int beveragePid) {
		try {
			int ingredientPid = createIngredient(espresso.getName(), Ingredient.TYPE_ESPRESSO, espresso.getIsDefault());
			espresso.setPid(ingredientPid);
			getHelper().getIngredientEspressoDao().create(espresso);

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
	public void modify(IngredientEspresso espresso) {
		// 修改保存即可
		try {
			getHelper().getIngredientEspressoDao().update(espresso);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int ingredientPid = espresso.getPid();
		// 修改Ingredient表中的名字
		try {
			List<Ingredient> ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientPid);
			Ingredient intredient = ingredients.get(0);
			if (intredient != null) {
				intredient.setName(espresso.getName());
				getHelper().getIngredientDao().update(intredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, int ingredientPid) {
		try {
			getHelper().getIngredientEspressoDao().deleteById(id);
			deleteBeverageIngredient(ingredientPid);
			deleteIngredient(ingredientPid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IngredientEspresso findByT(int ingredientPid) {
		try {
			QueryBuilder<IngredientEspresso, Integer> builder = getHelper().getIngredientEspressoDao().queryBuilder();
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
			return getHelper().getIngredientEspressoDao().queryForAll().size();
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
		List<IngredientEspresso> espresso = null;
		String ingredientName = "Copy of "+oldname+"(1)";
		QueryBuilder<IngredientEspresso, Integer> queryBuilder = getHelper().getIngredientEspressoDao().queryBuilder();
		try {
			queryBuilder.where().like("name", "Copy of "+oldname+"(%)");
			espresso = queryBuilder.query();
			if (espresso == null || espresso.size() == 0) {
				return ingredientName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ingredientName;
		}
		int currentNum = 0;
		for (int i = 0; i < espresso.size(); i++) {
			IngredientEspresso water1 = espresso.get(i);
			String name = water1.getName();
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

	public IngredientEspresso copyEspresso(int ingredientPid, int ingredientNewPid, String newName) {
		try {
			List<IngredientEspresso> waterList = getHelper().getIngredientEspressoDao().queryForEq("pid", ingredientPid);
			if (waterList != null && waterList.size() > 0) {
				IngredientEspresso water = waterList.get(0);
				water.setId(0);
				water.setIsDefault(0);
				water.setPid(ingredientNewPid);
				water.setName(newName);
				water.setCreatestatus(1);
				getHelper().getIngredientEspressoDao().create(water);
				return water;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


}
