package android.luna.Data.DAO;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IIngredientDao;
import android.luna.Data.module.Ingredient;
import android.luna.Data.module.IngredientInstant;

import com.j256.ormlite.stmt.QueryBuilder;

public class InstantDao extends IngredientDao implements IIngredientDao<IngredientInstant> {

	public InstantDao(Context context,CremApp app) {
		super(context,app);
	}

	@Override
	public int create(IngredientInstant instant, int oldIngredientPid, int beveragePid) {
		try {
			int ingredientPid = createIngredient(instant.getName(), Ingredient.TYPE_INSTANT,instant.getIsDefault());
			instant.setPid(ingredientPid);
			getHelper().getIngredientInstantDao().create(instant);
			if (beveragePid!=0) {
				updateBeverageIngredient(ingredientPid, beveragePid, oldIngredientPid);
//				deleteBeverageIngredient(ingredientPid);
			}
			return ingredientPid;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void modify(IngredientInstant instant) {
		// 修改保存即可
		try {
			getHelper().getIngredientInstantDao().update(instant);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int	ingredientPid = instant.getPid();
		// 修改Ingredient表中的名字
		try {
			List<Ingredient> ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientPid);
			Ingredient intredient = ingredients.get(0);
			if(intredient!=null){
				intredient.setName(instant.getName());
				getHelper().getIngredientDao().update(intredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id, int ingredientPid) {
		try {
			getHelper().getIngredientInstantDao().deleteById(id);
			deleteBeverageIngredient(ingredientPid);
			deleteIngredient(ingredientPid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public IngredientInstant findByT(int ingredientPid) {
		try {
			QueryBuilder<IngredientInstant, Integer> builder = getHelper().getIngredientInstantDao().queryBuilder();
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
			return getHelper().getIngredientInstantDao().queryForAll().size();
		} catch (SQLException e) {
			return 0;
		}
	}

	/**
	 * 检测名字是否存在
	 * @param name
	 * @return
	 */
	public boolean checkName(String name){
		try {
			List<IngredientInstant> instants = getHelper().getIngredientInstantDao().queryForEq("name", name);
			if(instants == null || instants.size()==0){
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 自动生成名字
	 * @return
	 */
	public String autoCreateName(String oldname) {
		List<IngredientInstant> instants = null;
		String ingredientName = "Copy of "+oldname+"(1)";
		QueryBuilder<IngredientInstant,Integer> queryBuilder = getHelper().getIngredientInstantDao().queryBuilder();
		try {
			queryBuilder.where().like("name", "Copy of "+oldname+"(%)");
			instants = queryBuilder.query();
			if (instants == null || instants.size() == 0) {
				return ingredientName;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ingredientName;
		}
		int currentNum = 0;
		for (int i = 0; i < instants.size(); i++) {
			IngredientInstant brew = instants.get(i);
			String name = brew.getName();
			if(name.startsWith("Copy of "+oldname+"(")){
				int num = Integer.valueOf(name.substring(9+oldname.length(), name.length()-1));
				if(num>currentNum){
					currentNum = num;
				}
			}
		}
		ingredientName = "Copy of "+oldname+"("+(currentNum + 1)+")";
		return ingredientName;
	}
	
	public IngredientInstant copyInstant(int ingredientPid,int ingredientNewPid, String newName) {
		try {
			List<IngredientInstant> instants = getHelper().getIngredientInstantDao().queryForEq("pid", ingredientPid);
			if (instants != null && instants.size() > 0) {
				IngredientInstant instant = instants.get(0);
				instant.setPid(ingredientNewPid);
				instant.setId(0);
				instant.setIsDefault(0);
				instant.setCreatestatus(1);
				instant.setName(newName);
				getHelper().getIngredientInstantDao().create(instant);
				return instant;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
