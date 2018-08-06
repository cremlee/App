package android.luna.Data.module;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_count")
public class Count {
	
	public static int TYPE_STILL =198;
	public static int TYPE_HOT =199;
	public static int TYPE_SPARKING =200;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int masterTotalBeverageCount;
	@DatabaseField
	private long masterTotalWaterCount;
	@DatabaseField
	private int watertype;
	
	public Count()
	{
		
	}
	public Count(int masterTotalBeverageCount,long masterTotalWaterCount,int watertype)
	{
		this.masterTotalBeverageCount = masterTotalBeverageCount;
		this.masterTotalWaterCount =masterTotalWaterCount;
		this.watertype =watertype;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMasterTotalBeverageCount() {
		return masterTotalBeverageCount;
	}

	public void setMasterTotalBeverageCount(int masterTotalBeverageCount) {
		this.masterTotalBeverageCount = masterTotalBeverageCount;
	}

	public long getMasterTotalWaterCount() {
		return masterTotalWaterCount;
	}

	public void setMasterTotalWaterCount(long masterTotalWaterCount) {
		this.masterTotalWaterCount = masterTotalWaterCount;
	}

}
