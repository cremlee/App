package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 记录Counters中得Master Total Beverages 是所有Beverages的总和，在counters中点击Reset
 * counters不清空
 * 
 * 注：这张表中只有一条ID为1的数据
 * 
 * @author tangcheng
 *
 */
@DatabaseTable(tableName = "tb_master_total_beverages")
public class MasterTotalBeverage {

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int total;

	public MasterTotalBeverage() {
		super();
	}

	public MasterTotalBeverage(int id, int total) {
		super();
		this.id = id;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
