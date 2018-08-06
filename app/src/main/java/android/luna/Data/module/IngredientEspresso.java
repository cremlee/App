package android.luna.Data.module;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_ingredient_espresso")
public class IngredientEspresso implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private String name;
	@DatabaseField(defaultValue="129")
	private int powdertype=129;
	@DatabaseField(defaultValue="0")
	private float powderamount=0;
	@DatabaseField(defaultValue="5")
	private int preinfusiontime=5;
	@DatabaseField(defaultValue="8")
	private int prebrewtime=8;
	@DatabaseField(defaultValue="8")
	private int brewtime=8;
	@DatabaseField(defaultValue="40")
	private int watervolume=40; 
	@DatabaseField(defaultValue="5")
	private int pressure=5;
	@DatabaseField(defaultValue="0")
	private int watertype=0;
	@DatabaseField(defaultValue="0")
	private int isDefault;
	@DatabaseField(defaultValue ="1")
	private int createstatus=1;
	
		
	public IngredientEspresso() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IngredientEspresso(int pid, String name, int powderamount,
			int preinfusiontime, int prebrewtime, int brewtime,
			int watervolume, int pressure, int isDefault, int createstatus) {
		super();
		this.pid = pid;
		this.name = name;
		this.powderamount = powderamount;
		this.preinfusiontime = preinfusiontime;
		this.prebrewtime = prebrewtime;
		this.brewtime = brewtime;
		this.watervolume = watervolume;
		this.pressure = pressure;
		this.isDefault = isDefault;
		this.createstatus = createstatus;
	}
	public boolean isChanged() {
		return IsChanged;
	}

	private boolean IsChanged=false;
	public void setChanged(boolean changed) {
		IsChanged = changed;
		if(createstatus==2)
		{
			createstatus =3;
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPowderamount() {
		return powderamount;
	}
	public void setPowderamount(float powderamount) {
		this.powderamount = powderamount;
	}
	public int getPreinfusiontime() {
		return preinfusiontime;
	}
	public void setPreinfusiontime(int preinfusiontime) {
		this.preinfusiontime = preinfusiontime;
	}
	public int getPrebrewtime() {
		return prebrewtime;
	}
	public void setPrebrewtime(int prebrewtime) {
		this.prebrewtime = prebrewtime;
	}
	public int getBrewtime() {
		return brewtime;
	}
	public void setBrewtime(int brewtime) {
		this.brewtime = brewtime;
	}
	public int getWatervolume() {
		return watervolume;
	}
	public void setWatervolume(int watervolume) {
		this.watervolume = watervolume;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}
	public int getCreatestatus() {
		return createstatus;
	}
	public void setCreatestatus(int createstatus) {
		this.createstatus = createstatus;
	}

	public int getPowdertype() {
		return powdertype;
	}

	public void setPowdertype(int powdertype) {
		this.powdertype = powdertype;
	}

	public int getWatertype() {
		return watertype;
	}

	public void setWatertype(int watertype) {
		this.watertype = watertype;
	}
}
