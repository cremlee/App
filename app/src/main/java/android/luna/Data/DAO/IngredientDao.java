package android.luna.Data.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.module.*;
import android.luna.rs232.Cmd.CmdMakeIngredient;
import android.util.Log;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;


public class IngredientDao extends BaseDaobak {

	public IngredientDao(Context context,CremApp app) {
		super(context,app);
	}

	/**
	 * 获取Ingredient 新的 Pid
	 * @return
	 * @throws SQLException
	 */
	public int newIngredientPid() throws SQLException {
		QueryBuilder<Ingredient, Integer> builder = getHelper().getIngredientDao().queryBuilder();
		builder.orderBy("pid", true);
		
		int ingredientPid = 1;
		List<Ingredient> intgredients = builder.query();
		for (int i = 0; i < intgredients.size(); i++) {
			int pid = intgredients.get(i).getPid();
			if(pid != i+1){
				ingredientPid = i+1;
				return ingredientPid;
			}
		}
		ingredientPid = intgredients.size() + 1;
		return ingredientPid;
	}
	
	/**
	 * 创建一个Ingredient（FilterBrew/Instant/Water的管理表）
	 * 
	 * @param name
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public int createIngredient(String name, int type,int isDefault) throws SQLException {
		Ingredient ingredient = new Ingredient();
		ingredient.setPid(newIngredientPid());
		ingredient.setName(name);
		ingredient.setType(type);
		ingredient.setIsDefault(isDefault);
		getHelper().getIngredientDao().create(ingredient);
		return ingredient.getPid();
	}

	/**
	 * 更新Beverage 与Ingredient的中间表
	 * 
	 * @param ingredientPid
	 * @param beveragePid
	 * @param oldIngredientPid
	 * @return
	 * @throws SQLException
	 */
	public int updateBeverageIngredient(int ingredientPid, int beveragePid, int oldIngredientPid)
			throws SQLException {
		QueryBuilder<BeverageIngredient, Integer> builder = getHelper().getBeverageIngredientDao().queryBuilder();
		builder.where().eq("ingredientPid", oldIngredientPid).and().eq("beveragePid", beveragePid);
		BeverageIngredient bi = builder.queryForFirst();
		if (bi != null) {
			bi.setIngredientPid(ingredientPid);
			return getHelper().getBeverageIngredientDao().update(bi);
		}
		return 0;
	}

	/**
	 * 删除Beverage Ingredient的中间表数据
	 * 
	 * @param ingredientPid
	 * @return
	 * @throws SQLException
	 */
	public int deleteBeverageIngredient(int ingredientPid) throws SQLException {
		DeleteBuilder<BeverageIngredient, Integer> builder = getHelper().getBeverageIngredientDao().deleteBuilder();
		builder.where().eq("ingredientPid", ingredientPid);
		return builder.delete();
	}

	/**
	 * 删除Ingredient表中的数据
	 * 
	 * @param ingredientPid
	 * @return
	 * @throws SQLException
	 */
	public int deleteIngredient(int ingredientPid) throws SQLException {
		DeleteBuilder<Ingredient, Integer> builder = getHelper().getIngredientDao().deleteBuilder();
		builder.where().eq("ingredientPid", ingredientPid);
		return builder.delete();
	}
	
	
	/**
	 * 加载默认的Ingredient和通过Ingredient Maker加上去的。即显示isDefault不为0的所有Ingredient
	 * @return
	 */
	public List<Ingredient> defaultIngredientList(){
		try {
//			return getHelper().getIngredientDao().queryForEq("isDefault", 1);
			QueryBuilder<Ingredient, Integer> builder =  getHelper().getIngredientDao().queryBuilder();
			builder.where().ne("isDefault", 0);
			return builder.query();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Ingredient getIngredient(int ingredientPid){
		try {
			List<Ingredient> ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientPid);
			if(ingredients!=null && ingredients.size()>0){
				return ingredients.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param ingredient
	 * @param soldoutType
	 * @return 删除Ingredient数据结构，给下位机使用
	 */
	public String deleteIngredientById(Ingredient ingredient,int soldoutType){
		String buildStructure = "";
		// 删除Ingredient中
		int ingredientId = ingredient.getPid();
		int ingredientType = ingredient.getType();
		try {
			if (ingredient.getIsDefault() == 1) {
				// 删除中间表（IngredientBeverage）表中IngredientPid为Ingredient Pid的数据
				deleteBeverageIngredient(ingredientId);
				return "";
			}
			// 删除IngredientPub 同时需要删除pid为当前IngredientPub pid的Ingredient
			getHelper().getIngredientDao().deleteById(ingredient.getId());
			// 删除中间表（IngredientBeverage）表中IngredientPid为Ingredient Pid的数据
			deleteBeverageIngredient(ingredientId);
			CmdMakeIngredient cmdMakeIngredient = new CmdMakeIngredient();
			switch (ingredientType) {
				case Ingredient.TYPE_FILTER_BREW:
					IngredientFilterBrew filterBrew = getHelper().getIngredientFilterBrewDao()
							.queryForEq("pid", ingredientId).get(0);
					buildStructure = cmdMakeIngredient.buildFilterBrewStructure(filterBrew);
					getHelper().getIngredientFilterBrewDao().deleteById(filterBrew.getId());
					break;
				case Ingredient.TYPE_INSTANT:
					IngredientInstant instant = getHelper().getIngredientInstantDao().queryForEq("pid", ingredientId)
							.get(0);
					buildStructure = cmdMakeIngredient.buildInstantStructure(instant);
					getHelper().getIngredientInstantDao().deleteById(instant.getId());
					break;
				case Ingredient.TYPE_WATER:
					IngredientWater water = getHelper().getIngredientWaterDao().queryForEq("pid", ingredientId).get(0);
					buildStructure = cmdMakeIngredient.buildWaterStructure(water);
					getHelper().getIngredientWaterDao().deleteById(water.getId());
					break;
				case Ingredient.TYPE_FILTER_BREW_ADVANCE:
					IngredientFilterBrewAdvance fadv = getHelper().getIngredientFilterBrewAdvanceDao().queryForEq("pid", ingredientId).get(0);
					FilterBrewStepDao aa = new FilterBrewStepDao(getmContext(), ((CremApp) getmContext().getApplicationContext()));
					getHelper().getIngredientFilterBrewAdvanceDao().deleteById(fadv.getId());
					aa.ClearTableforPid(ingredientId);
					//todo : send cmd
					break;
				case Ingredient.TYPE_ESPRESSO:
					IngredientEspresso espresso = getHelper().getIngredientEspressoDao().queryForEq("pid", ingredientId).get(0);
					if (espresso != null) {
						getHelper().getIngredientEspressoDao().deleteById(espresso.getId());
						buildStructure = cmdMakeIngredient.buildEspressoStructure(espresso);
					}
					break;
				case Ingredient.TYPE_MONO:
					IngredientMono mono = getHelper().getIngredientMonoDao().queryForEq("pid", ingredientId).get(0);
					if(mono!=null)
					{
						getHelper().getIngredientMonoDao().deleteById(mono.getId());
					}
					MonoStepDao monostep = new MonoStepDao(getmContext(), ((CremApp) getmContext().getApplicationContext()));
					monostep.clearmonostepbypid(ingredientId);
					break;
				default:
					break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buildStructure;
	}
	
	/**
	 * 查找Beverage中包含了那些Ingredient
	 * @param beveragePid
	 * @return
	 */
	public ArrayList<Integer> getIngredientTypes(int beveragePid){
		
		List<BeverageIngredient> beverageIngredients = null;
		ArrayList<Integer> types = new ArrayList<Integer>();
		try {
			beverageIngredients = getHelper().getBeverageIngredientDao().queryForEq("beveragePid", beveragePid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(beverageIngredients!=null && beverageIngredients.size()>0){
			for (int i = 0; i < beverageIngredients.size(); i++) {
				types.add(beverageIngredients.get(i).getIngredientType());
			}
		}
		return types;
	}
	
	public boolean getIngredientidForBeverage(int pid)
	{
		try {
			QueryBuilder<BeverageIngredient, Integer> builder = getHelper().getBeverageIngredientDao().queryBuilder();
			builder.where().eq("beveragePid", pid).and().eq("ingredientType", Ingredient.TYPE_FILTER_BREW);
			List<BeverageIngredient> beverageIngredients = builder.query();
			if( beverageIngredients.size()==1){
				int ingredientpid= beverageIngredients.get(0).getIngredientPid();
				List<Ingredient> ingredients = getHelper().getIngredientDao().queryForEq("pid", ingredientpid);
				if(ingredients.size()==1){
					return (ingredients.get(0).getIsDefault() ==1 ? true:false);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
