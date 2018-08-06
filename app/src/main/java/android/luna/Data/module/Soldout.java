package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 当type为fm04时grinder将设置为料包的值，并且与其他3个互斥<br/>
 * volume 分别为Full,1/4,2/4,3/4
 * 
 * @author Administrator
 * 
 */
@DatabaseTable(tableName = "tb_soldout")
public class Soldout implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(defaultValue = "500.00f")
	private float grinderPct= 500.00f;
	@DatabaseField
	private String grinder;
	@DatabaseField(defaultValue = "500.00f")
	private float grinderCount = 500.00f;
	@DatabaseField
	private String canister2;
	@DatabaseField(defaultValue = "500.00f")
	private float canister2Pct= 500.00f;
	@DatabaseField(defaultValue = "500.00f")
	private float canister2Count = 500.00f;
	@DatabaseField
	private String canister3;
	@DatabaseField(defaultValue = "500.00f")
	private float canister3Pct= 500.00f;
	@DatabaseField(defaultValue = "500.00f")
	private float canister3Count = 500.00f;
	@DatabaseField
	private String canister4;
	@DatabaseField(defaultValue = "500.00f")
	private float canister4Pct= 500.00f;
	@DatabaseField(defaultValue = "500.00f")
	private float canister4Count = 500.00f;
	@DatabaseField(defaultValue = "150")
	private int wasteBinCount = 150; // 做一杯含豆子的咖啡就-1
	@DatabaseField
	private int waterPassCount;// 做一杯饮料就加上饮料的Volume
	@DatabaseField
	private int grinderCoffeeCount; // 做一杯含豆子的咖啡就加咖啡豆的克数
	@DatabaseField
	private int filterBrewerCount;

	public Soldout() {
	}

	public Soldout(String grinder, float grinderPct, String canister2Value, float canister2Pct, String canister3Value, float canister3Pct, String canister4Value, float canister4Pct) {
		super();
		this.grinder = grinder;
		this.grinderPct = grinderPct;
		this.canister2 = canister2Value;
		this.canister2Pct = canister2Pct;
		this.canister3 = canister3Value;
		this.canister3Pct = canister3Pct;
		this.canister4 = canister4Value;
		this.canister4Pct = canister4Pct;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getGrinderPct() {
		return grinderPct;
	}

	public void setGrinderPct(float grinderPct) {
		this.grinderPct = grinderPct;
	}

	public String getGrinder() {
		return grinder;
	}

	public void setGrinder(String grinder) {
		this.grinder = grinder;
	}

	public float getGrinderCount() {
		return grinderCount;
	}

	public void setGrinderCount(float grinderCount) {
		this.grinderCount = grinderCount;
	}

	public String getCanister2() {
		return canister2;
	}

	public void setCanister2(String canister2) {
		this.canister2 = canister2;
	}

	public float getCanister2Pct() {
		return canister2Pct;
	}

	public void setCanister2Pct(float canister2Pct) {
		this.canister2Pct = canister2Pct;
	}

	public float getCanister2Count() {
		return canister2Count;
	}

	public void setCanister2Count(float canister2Count) {
		this.canister2Count = canister2Count;
	}

	public String getCanister3() {
		return canister3;
	}

	public void setCanister3(String canister3) {
		this.canister3 = canister3;
	}

	public float getCanister3Pct() {
		return canister3Pct;
	}

	public void setCanister3Pct(float canister3Pct) {
		this.canister3Pct = canister3Pct;
	}

	public float getCanister3Count() {
		return canister3Count;
	}

	public void setCanister3Count(float canister3Count) {
		this.canister3Count = canister3Count;
	}

	public String getCanister4() {
		return canister4;
	}

	public void setCanister4(String canister4) {
		this.canister4 = canister4;
	}

	public float getCanister4Pct() {
		return canister4Pct;
	}

	public void setCanister4Pct(float canister4Pct) {
		this.canister4Pct = canister4Pct;
	}

	public float getCanister4Count() {
		return canister4Count;
	}

	public void setCanister4Count(float canister4Count) {
		this.canister4Count = canister4Count;
	}

	public int getWasteBinCount() {
		return wasteBinCount;
	}

	public void setWasteBinCount(int wasteBinCount) {
		this.wasteBinCount = wasteBinCount;
	}

	public int getWaterPassCount() {
		return waterPassCount;
	}

	public void setWaterPassCount(int waterPassCount) {
		this.waterPassCount = waterPassCount;
	}

	public int getGrinderCoffeeCount() {
		return grinderCoffeeCount;
	}

	public void setGrinderCoffeeCount(int grinderCoffeeCount) {
		this.grinderCoffeeCount = grinderCoffeeCount;
	}

	public int getFilterBrewerCount() {
		return filterBrewerCount;
	}

	public void setFilterBrewerCount(int filterBrewerCount) {
		this.filterBrewerCount = filterBrewerCount;
	}

//	public int getType() {
//		return type;
//	}
//
//	public void setType(int type) {
//		this.type = type;
//	}
//
	@Override
	public String toString() {
		return "Soldout [id=" + id + ", grinderPct=" + grinderPct + ", grinder=" + grinder + ", grinderCount=" + grinderCount + ", canister2=" + canister2 + ", canister2Pct=" + canister2Pct + ", canister2Count=" + canister2Count + ", canister3=" + canister3 + ", canister3Pct=" + canister3Pct + ", canister3Count=" + canister3Count + ", canister4=" + canister4 + ", canister4Pct=" + canister4Pct + ", canister4Count=" + canister4Count + ", wasteBinCount=" + wasteBinCount + ", waterPassCount=" + waterPassCount + ", grinderCoffeeCount=" + grinderCoffeeCount + ", filterBrewerCount=" + filterBrewerCount +"]";
	}

}
