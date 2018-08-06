package android.luna.Data.Interface;

public interface IIngredientDao<T>{

	/**
	 * （1）是否来自Change Base Recipe界面<br/>
	 * （2）如果是讲此次保存的IngredientPid记录下来，更新当前Beverage中引用的IngredientPid<br/>
	 * （3）如果不是直接保存即可<br/>
	 * 
	 * @param t
	 * @param
	 * @param oldIngredientPid
	 * @param beveragePid
	 * @return IngredientPid
	 */
	public int create(T t, int oldIngredientPid, int beveragePid);

	/**
	 * 修改数据
	 * 
	 * @param
	 */
	public void modify(T t);

	/**
	 * （1）删除Ingredient表中FilterBrew pid<br/>
	 * （2）删除BI表中IngredientPid为当前删除Ingredient 的Pid<br/>
	 * （3）删除当前的FilterBrew<br/>
	 * 
	 * @param id
	 * @param ingredientPid
	 */
	public void delete(int id, int ingredientPid);

	/**
	 * 根据Ingredient Pid 查找Ingredient
	 * 
	 * @param ingredientPid
	 * @return
	 */
	public T findByT(int ingredientPid);

	public int getItemcount();
}
