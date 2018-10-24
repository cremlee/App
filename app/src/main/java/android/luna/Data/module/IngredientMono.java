package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_ingredient_mono")
public class IngredientMono implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private String name;
	@DatabaseField(defaultValue="129")
	private int powdertype;
	@DatabaseField(defaultValue="50")
	private float powderamount;
	@DatabaseField(defaultValue="1")
	private int powdervolumetype;
	@DatabaseField(defaultValue="150")
	private int powdervolume;
	@DatabaseField(defaultValue="30")
	private int firstpart;
	@DatabaseField(defaultValue="30")
	private int secondpart;
	@DatabaseField(defaultValue="5")
	private int brewtime;
	@DatabaseField(defaultValue="5")
	private int infusiontime;
	@DatabaseField(defaultValue="8")
	private int waterpressure;
	@DatabaseField(defaultValue="80")
	private int airspeed;
	@DatabaseField(defaultValue="8")
	private int airruntime;
	@DatabaseField(defaultValue="50")
	private int bubblerspeed;
	@DatabaseField(defaultValue="5")
	private int bubblerruntime;
	@DatabaseField(defaultValue="0")
	private int bubblerrundelay;
	@DatabaseField(defaultValue="0")
	private int isDefault;
	@DatabaseField(defaultValue ="1")
	private int createstatus=1;
	public IngredientMono() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IngredientMono(int pid, String name, int powdertype, float powderamount, int powdervolumetype, int powdervolume, int firstpart, int secondpart, int brewtime, int infusiontime, int waterpressure, int airspeed, int airruntime, int bubblerspeed, int bubblerruntime, int bubblerrundelay, int isDefault, int createstatus) {
		this.pid = pid;
		this.name = name;
		this.powdertype = powdertype;
		this.powderamount = powderamount;
		this.powdervolumetype = powdervolumetype;
		this.powdervolume = powdervolume;
		this.firstpart = firstpart;
		this.secondpart = secondpart;
		this.brewtime = brewtime;
		this.infusiontime = infusiontime;
		this.waterpressure = waterpressure;
		this.airspeed = airspeed;
		this.airruntime = airruntime;
		this.bubblerspeed = bubblerspeed;
		this.bubblerruntime = bubblerruntime;
		this.bubblerrundelay = bubblerrundelay;
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

	public int getPowdertype() {
		return powdertype;
	}

	public void setPowdertype(int powdertype) {
		this.powdertype = powdertype;
	}

	public float getPowderamount() {
		return powderamount;
	}

	public void setPowderamount(float powderamount) {
		this.powderamount = powderamount;
	}

	public int getPowdervolumetype() {
		return powdervolumetype;
	}

	public void setPowdervolumetype(int powdervolumetype) {
		this.powdervolumetype = powdervolumetype;
	}

	public int getPowdervolume() {
		return powdervolume;
	}

	public void setPowdervolume(int powdervolume) {
		this.powdervolume = powdervolume;
	}

	public int getFirstpart() {
		return firstpart;
	}

	public void setFirstpart(int firstpart) {
		this.firstpart = firstpart;
	}

	public int getSecondpart() {
		return secondpart;
	}

	public void setSecondpart(int secondpart) {
		this.secondpart = secondpart;
	}

	public int getBrewtime() {
		return brewtime;
	}

	public void setBrewtime(int brewtime) {
		this.brewtime = brewtime;
	}

	public int getInfusiontime() {
		return infusiontime;
	}

	public void setInfusiontime(int infusiontime) {
		this.infusiontime = infusiontime;
	}

	public int getWaterpressure() {
		return waterpressure;
	}

	public void setWaterpressure(int waterpressure) {
		this.waterpressure = waterpressure;
	}

	public int getAirspeed() {
		return airspeed;
	}

	public void setAirspeed(int airspeed) {
		this.airspeed = airspeed;
	}

	public int getAirruntime() {
		return airruntime;
	}

	public void setAirruntime(int airruntime) {
		this.airruntime = airruntime;
	}

	public int getBubblerspeed() {
		return bubblerspeed;
	}

	public void setBubblerspeed(int bubblerspeed) {
		this.bubblerspeed = bubblerspeed;
	}

	public int getBubblerruntime() {
		return bubblerruntime;
	}

	public void setBubblerruntime(int bubblerruntime) {
		this.bubblerruntime = bubblerruntime;
	}

	public int getBubblerrundelay() {
		return bubblerrundelay;
	}

	public void setBubblerrundelay(int bubblerrundelay) {
		this.bubblerrundelay = bubblerrundelay;
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

	@Override
	public String toString() {
		return "IngredientMono{" +
				"id=" + id +
				", pid=" + pid +
				", name='" + name + '\'' +
				", powdertype=" + powdertype +
				", powderamount=" + powderamount +
				", powdervolumetype=" + powdervolumetype +
				", powdervolume=" + powdervolume +
				", firstpart=" + firstpart +
				", secondpart=" + secondpart +
				", brewtime=" + brewtime +
				", infusiontime=" + infusiontime +
				", waterpressure=" + waterpressure +
				", airspeed=" + airspeed +
				", airruntime=" + airruntime +
				", bubblerspeed=" + bubblerspeed +
				", bubblerruntime=" + bubblerruntime +
				", bubblerrundelay=" + bubblerrundelay +
				", isDefault=" + isDefault +
				", createstatus=" + createstatus +
				'}';
	}
}
