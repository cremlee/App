package android.luna.Data.module;

import java.io.Serializable;

import android.content.Context;
import evo.luna.android.R;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 此表只能修改数据，不能进行添加和删除操作
 * 
 * @author Administrator
 * 
 */
@DatabaseTable(tableName = "tb_maintenance")
public class Maintenance implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static int BOILER_VALVE_MEMBRANE = 1;
	public final static int BOILER_VALVE_SEALING = 2;
	public final static int BOILER_VALVE_ORING = 3;
	public final static int BOILER_PIN_SHORT = 4;
	public final static int BOILER_PIN_LONG = 5;
	public final static int BOILER_PIN_SEALING = 6;
	public final static int BOILER_NTC_SENSOR = 7;
	public final static int BOILER_NTC_SEALING = 8;

	public final static int BREWING_UNIT_BREW_FILTER = 9;
	public final static int BREWING_UNIT_BREW_CHAMBER = 10;
	public final static int BREWING_UNIT_BREW_CYLINDER = 11;
	public final static int BREWING_UNIT_COFFEE_GROUNDS_SCRAPER = 12;

	public final static int INGREDIENTS_GRINDER_DISCS = 13;
	public final static int INGREDIENTS_MIXER_MOUNTING_PLATE = 14;
	public final static int INGREDIENTS_MIXER_SEALING = 15;
	public final static int INGREDIENTS_MIXER_ORING = 16;
	public final static int INGREDIENTS_MIXER_WHIPPER = 17;
	public final static int VENTILATION_TUBE = 18;
	public final static int VENTILATION_FAN = 20;
	public final static int WATER_FILTER = 19;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String startTime;
	@DatabaseField
	private int lifeCycle; // 生命周期
	@DatabaseField
	private int type; // 1,BOILER 2,BREWING UNIT 3,INGREDIENTS 4,VENTILATION
	@DatabaseField
	private int subId;
	@DatabaseField
	private int actiontype;              //1:check 2:clean 3 :clean-repalce 4:check-replace
	public int getActiontype() {
		return actiontype;
	}

	public void setActiontype(int actiontype) {
		this.actiontype = actiontype;
	}

	public int getNeedcheck() {
		return needcheck;
	}

	public void setNeedcheck(int needcheck) {
		this.needcheck = needcheck;
	}

	@DatabaseField(defaultValue="1")     //1:no 2:need
	private int needcheck;
	
	public Maintenance() {
	}

	public Maintenance(String name, String startTime, int lifeCycle, int type,
			int subId, int actiontype, int needcheck) {
		super();
		this.name = name;
		this.startTime = startTime;
		this.lifeCycle = lifeCycle;
		this.type = type;
		this.subId = subId;
		this.actiontype = actiontype;
		this.needcheck = needcheck;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName(Context context) {
		if(this.subId == Maintenance.BOILER_VALVE_MEMBRANE)
		  return context.getString(R.string.mte_boiler_ovm);
		else if(this.subId == Maintenance.BOILER_VALVE_SEALING)
			  return context.getString(R.string.mte_boiler_ovs);
		else if(this.subId == Maintenance.BOILER_VALVE_ORING)
			  return context.getString(R.string.mte_boiler_ovo);
		else if(this.subId == Maintenance.BOILER_PIN_SHORT)
			  return context.getString(R.string.mte_boiler_lspst);
		else if(this.subId == Maintenance.BOILER_PIN_LONG)
			  return context.getString(R.string.mte_boiler_lspl);
		else if(this.subId == Maintenance.BOILER_PIN_SEALING)
			  return context.getString(R.string.mte_boiler_lspsg);
		else if(this.subId == Maintenance.BOILER_NTC_SENSOR)
			  return context.getString(R.string.mte_boiler_nsr);
		else if(this.subId == Maintenance.BOILER_NTC_SEALING)
			  return context.getString(R.string.mte_boiler_nsg);
		else if(this.subId == Maintenance.BREWING_UNIT_BREW_FILTER)
			  return context.getString(R.string.mte_brewingunit_bf);
		else if(this.subId == Maintenance.BREWING_UNIT_BREW_CHAMBER)
			  return context.getString(R.string.mte_brewingunit_bcbr);
		else if(this.subId == Maintenance.BREWING_UNIT_BREW_CYLINDER)
			  return context.getString(R.string.mte_brewingunit_bcdr);
		else if(this.subId == Maintenance.BREWING_UNIT_COFFEE_GROUNDS_SCRAPER)
			  return context.getString(R.string.mte_brewingunit_cgs);
		else if(this.subId == Maintenance.INGREDIENTS_GRINDER_DISCS)
			  return context.getString(R.string.mte_ingredients_gd);
		else if(this.subId == Maintenance.INGREDIENTS_MIXER_MOUNTING_PLATE)
			  return context.getString(R.string.mte_ingredients_mmp);
		else if(this.subId == Maintenance.INGREDIENTS_MIXER_SEALING)
			  return context.getString(R.string.mte_ingredients_ms);
		else if(this.subId == Maintenance.INGREDIENTS_MIXER_ORING)
			  return context.getString(R.string.mte_ingredients_mo);
		else if(this.subId == Maintenance.INGREDIENTS_MIXER_WHIPPER)
			  return context.getString(R.string.mte_ingredients_mw);
		else if(this.subId == Maintenance.VENTILATION_TUBE)
			  return context.getString(R.string.mte_ventilation_vt);
		else if(this.subId == Maintenance.VENTILATION_FAN)
			  return context.getString(R.string.mte_ventilation_fb);
		else if(this.subId == Maintenance.WATER_FILTER)
			  return context.getString(R.string.mte_waterfilter);
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLifeCycle() {
		return lifeCycle;
	}

	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	@Override
	public String toString() {
		return "Maintenance [id=" + id + ", name=" + name + ", startTime=" + startTime + ", type=" + type + "]";
	}

}
