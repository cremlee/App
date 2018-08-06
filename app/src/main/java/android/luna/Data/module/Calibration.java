package android.luna.Data.module;

import java.io.Serializable;

import android.content.Context;
import evo.luna.android.R;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_calibration")
public class Calibration implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int TYPE_WATER_TANK = 0; // 70s
	public static final int TYPE_CANISTERS = 1; // 7s
	public static final int TYPE_BREWER = 2; // 12s
	public static final int TYPE_COLD_WATER_SYSTEM = 3; // 6s

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private float dosageValve;
	@DatabaseField
	private int type;
	@DatabaseField
	private String partNo;
	@DatabaseField
	private int machineType; // 0 通用，1 MF04，2 MF13
	@DatabaseField
	private String unint;
	@DatabaseField
	private int runtime;

	public Calibration() {
	}

	public Calibration(String name, int type, String partNo, int machineType, String unint,int runtime) {
		this.name = name;
		this.type = type;
		this.partNo = partNo;
		this.machineType = machineType;
		this.unint = unint;
		this.runtime = runtime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName(Context context) {
		
		if(this.partNo.equals("80"))
		{
			name=context.getString(R.string.cal_watertank_txt80);
		}
		else if(this.partNo.equals("81"))
		{
			name=context.getString(R.string.cal_watertank_txt81);
		}
		else if(this.partNo.equals("82"))
		{
			name=context.getString(R.string.cal_watertank_txt82);
		}
		else if(this.partNo.equals("83"))
		{
			name=context.getString(R.string.cal_watertank_txt83);
		}
		else if(this.partNo.equals("84"))
		{
			name=context.getString(R.string.cal_watertank_txt84);
		}
		else if(this.partNo.equals("85"))
		{
			name=context.getString(R.string.cal_watertank_txt85);
		}
		else if(this.partNo.equals("86"))
		{
			name=context.getString(R.string.cal_watertank_txt86);
		}
		else if(this.partNo.equals("87") )
		{
			name=context.getString(R.string.cal_watertank_txt87);
		}
		else if(this.partNo.equals("8a"))
		{
			name=context.getString(R.string.cal_watertank_txt8a);
		}
		else if(this.partNo.equals("8b"))
		{
			name=context.getString(R.string.cal_watertank_txt8b);
		}
		else if(this.partNo.equals("8c"))
		{
			name=context.getString(R.string.cal_watertank_txt8c);
		}
		else if(this.partNo.equals("8d"))
		{
			name=context.getString(R.string.cal_watertank_txt8d);
		}
		else if(this.partNo.equals("8e"))
		{
			name=context.getString(R.string.cal_watertank_txt8e);
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getDosageValve() {
		return dosageValve;
	}

	public void setDosageValve(float dosageValve) {
		this.dosageValve = dosageValve;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public int getMachineType() {
		return machineType;
	}

	public void setMachineType(int machineType) {
		this.machineType = machineType;
	}

	public String getUnint() {
		return unint;
	}

	public void setUnint(String unint) {
		this.unint = unint;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

}
