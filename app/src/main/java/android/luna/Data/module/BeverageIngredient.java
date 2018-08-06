package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Beverage与Ingredient中间表
 * 
 * @author Gil.tang
 * 
 */
@DatabaseTable(tableName = "tb_beverage_ingredient")
public class BeverageIngredient implements Serializable {
	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int beveragePid;
	@DatabaseField
	private int ingredientPid;
	@DatabaseField(defaultValue="1")
	private int ingredientType=1;
	@DatabaseField
	private int isIngredientDefault; // 0是从Edit
										// Recipe中添加的，1是系统默认的，2是通过Ingredient
										// Maker 添加的
	@DatabaseField
	private int scaleUp = 100;
	@DatabaseField
	private int startTime;
	// 只在Edit recipe中使用
	private float totalTime;

	public BeverageIngredient() {
	}

	public BeverageIngredient(int beveragePid, int ingredientPid, int ingredientType, int scaleUp, int startTime, int isIngredientDefault) {
		super();
		this.beveragePid = beveragePid;
		this.ingredientPid = ingredientPid;
		this.ingredientType = ingredientType;
		this.scaleUp = scaleUp;
		this.startTime = startTime;
		this.isIngredientDefault = isIngredientDefault;
	}

	public BeverageIngredient(int ingredientPid, int ingredientType, int scaleUp, int startTime, int isIngredientDefault) {
		super();
		this.ingredientPid = ingredientPid;
		this.ingredientType = ingredientType;
		this.scaleUp = scaleUp;
		this.startTime = startTime;
		this.isIngredientDefault = isIngredientDefault;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBeveragePid() {
		return beveragePid;
	}

	public void setBeveragePid(int beveragePid) {
		this.beveragePid = beveragePid;
	}

	public int getIngredientPid() {
		return ingredientPid;
	}

	public void setIngredientPid(int ingredientPid) {
		this.ingredientPid = ingredientPid;
	}

	public int getScaleUp() {
		return scaleUp;
	}

	public void setScaleUp(int scaleUp) {
		this.scaleUp = scaleUp;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(int ingredientType) {
		this.ingredientType = ingredientType;
	}

	public int getIsIngredientDefault() {
		return isIngredientDefault;
	}

	public void setIsIngredientDefault(int isIngredientDefault) {
		this.isIngredientDefault = isIngredientDefault;
	}

	public float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}

	
	
	@Override
	public String toString() {
		return "BeverageIngredient [id=" + id + ", beveragePid=" + beveragePid + ", ingredientPid=" + ingredientPid + ", ingredientType=" + ingredientType + ", isIngredientDefault=" + isIngredientDefault + ", scaleUp=" + scaleUp + ", totalTime=" + totalTime+ ", startTime=" + startTime + "]";
	}

}
