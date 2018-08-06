package android.luna.Data.module;

import java.io.Serializable;

import android.content.Context;
import evo.luna.android.R;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 即Schedule下面的操作次数
 * 
 * @author Administrator
 * 
 */
@DatabaseTable(tableName = "tb_clean_program_count")
public class CleanProgramCount implements Serializable {
	private Context context;
	private static final long serialVersionUID = -2937130432817553812L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private int count;
	@DatabaseField
	private int type;

	public CleanProgramCount() {
		super();
		//this.context = context;
	}

	public CleanProgramCount(String name, int count,int type ) {
		super();
		//this.context = context;
		this.name = name;
		this.count = count;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName(Context context) {
		if(this.type == CleanMachine.CLEAN_DAILY)
		{
			return context.getString(R.string.warning_daily_clean);
		}
		if(this.type == CleanMachine.CLEAN_WEEKLY)
		{
			return context.getString(R.string.weekly_clean);
		}
		if(this.type == CleanMachine.CLEAN_BREWER)
		{
			return context.getString(R.string.counter_cleanbrew);
		}
		if(this.type == CleanMachine.CLEAN_MIXER)
		{
			return context.getString(R.string.counter_cleanmix);
		}
		if(this.type == CleanMachine.CLEAN_SHOCK_VALVE)
		{
			return context.getString(R.string.counter_cleanvalve);
		}
		if(this.type == CleanMachine.CLEAN_GRINDER)
		{
			return context.getString(R.string.counter_cleangrd);
		}
		return "";
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
