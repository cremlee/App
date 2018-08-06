package android.luna.Data.DAO;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IIngredientDao;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientWater;

import com.j256.ormlite.stmt.QueryBuilder;


public class WaterDao extends IngredientDao implements IIngredientDao<IngredientWater> {

	public static final int TYPE_HOT = 0;
	public static final int TYPE_STILL = 1;
	public static final int TYPE_SPARKING = 2;
	
	public WaterDao(Context context,CremApp app) {
		super(context,app);
	}

	@Override
	public int create(IngredientWater water, int oldIngredientPid, int beveragePid) {
		try {
			int ingredientPid = createIngredient(water.getName(), Ingredient.TYPE_WATER, water.getIsDefault());
			water.setPid(ingredientPid);
			getHelper().getIngredientWaterDao().create(water);

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
	public void modify(IngredientWater water) {
		// 修改保存即可
		try {
			getHelper().getIngredientWaterDao().update(water);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int ingredientPid = water.getPid();

		// 修改Ingredient表中的名字
		try {
			List<Ingredient> ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientPid);
			Ingredient intredient = ingredients.get(0);
			if (intredient != null) {
				intredient.setName(water.getName());
				getHelper().getIngredientDao().update(intredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, int ingredientPid) {
		try {
			getHelper().getIngredientWaterDao().deleteById(id);
			deleteBeverageIngredient(ingredientPid);
			deleteIngredient(ingredientPid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IngredientWater findByT(int ingredientPid) {
		try {
			QueryBuilder<IngredientWater, Integer> builder = getHelper().getIngredientWaterDao().queryBuilder();
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
			return getHelper().getIngredientWaterDao().queryForAll().size();
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
			List<IngredientWater> water = getHelper().getIngredientWaterDao().queryForEq("name", name);
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
		List<IngredientWater> water = null;
		String ingredientName = "Copy of "+oldname+"(1)";
		QueryBuilder<IngredientWater, Integer> queryBuilder = getHelper().getIngredientWaterDao().queryBuilder();
		try {
			queryBuilder.where().like("name", "Copy of "+oldname+"(%)");
			water = queryBuilder.query();
			if (water == null || water.size() == 0) {
				return ingredientName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ingredientName;
		}
		int currentNum = 0;
		for (int i = 0; i < water.size(); i++) {
			IngredientWater water1 = water.get(i);
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

	public IngredientWater copyWater(int ingredientPid, int ingredientNewPid, String newName) {
		try {
			List<IngredientWater> waterList = getHelper().getIngredientWaterDao().queryForEq("pid", ingredientPid);
			if (waterList != null && waterList.size() > 0) {
				IngredientWater water = waterList.get(0);
				water.setId(0);
				water.setIsDefault(0);
				water.setPid(ingredientNewPid);
				water.setName(newName);
				water.setCreatestatus(1);
				getHelper().getIngredientWaterDao().create(water);
				return water;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
