package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_beverage_basic")
public class BeverageBasic implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final int DISPENSETYPE_PUSH_AND_HOLD = 1; // water
	public static final int DISPENSETYPE_PORTION = 2;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	/* Additional Recipe Settings(Start) */
	@DatabaseField
	private int stoppable;
	@DatabaseField(defaultValue = "0")
	private int cupSensor = 0;
	@DatabaseField(defaultValue = "1")
	private int useCount = 1;
	@DatabaseField(defaultValue = "0.0f")
	private float drinkPrice = 0.0f;
	@DatabaseField(defaultValue = "1")
	private int ledColor = 1; //
	@DatabaseField(defaultValue = "1")
	private int ledMode = 1;
	@DatabaseField(defaultValue = "1")
	private int ledIntensity = 1;
	@DatabaseField(defaultValue = "2")
	private int dispenseType = 2;
	/* Additional Recipe Settings(end) */

	/* Pre Selections(Start) */
	@DatabaseField(defaultValue = "0")
	private int chocolate;
	@DatabaseField(defaultValue = "0")
	private int milk;
	@DatabaseField(defaultValue = "0")
	private int strength ;
	@DatabaseField(defaultValue = "0")
	private int sugar;
	@DatabaseField(defaultValue = "0")
	private int topping;
	@DatabaseField(defaultValue = "1")
	private int volume = 1;
	@DatabaseField(defaultValue = "0")
	private int jug;

	public int getQuickDrink() {
		return quickDrink;
	}

	public void setQuickDrink(int quickDrink) {
		this.quickDrink = quickDrink;
	}

	@DatabaseField(defaultValue = "0")
	private int quickDrink;
	/* Pre Selections(end) */
	//1:new 2:ok 3:update
	@DatabaseField(defaultValue = "1")
	private int createstatus=1;

	public BeverageBasic(){}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@DatabaseField
	private String name="";

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

	public int getStoppable() {
		return stoppable;
	}

	public void setStoppable(int stoppable) {
		this.stoppable = stoppable;
	}

	public int getCupSensor() {
		return cupSensor;
	}

	public void setCupSensor(int cupSensor) {
		this.cupSensor = cupSensor;
	}

	public int getUseCount() {
		return useCount;
	}

	public void setUseCount(int useCount) {
		this.useCount = useCount;
	}

	public float getDrinkPrice() {
		return drinkPrice;
	}

	public void setDrinkPrice(float drinkPrice) {
		this.drinkPrice = drinkPrice;
	}

	public int getLedColor() {
		return ledColor;
	}

	public void setLedColor(int ledColor) {
		this.ledColor = ledColor;
	}

	public int getLedMode() {
		return ledMode;
	}

	public void setLedMode(int ledMode) {
		this.ledMode = ledMode;
	}

	public int getLedIntensity() {
		return ledIntensity;
	}

	public void setLedIntensity(int ledIntensity) {
		this.ledIntensity = ledIntensity;
	}

	public int getDispenseType() {
		return dispenseType;
	}

	public void setDispenseType(int dispenseType) {
		this.dispenseType = dispenseType;
	}

	public int getChocolate() {
		return chocolate;
	}

	public void setChocolate(int chocolate) {
		this.chocolate = chocolate;
	}

	public int getMilk() {
		return milk;
	}

	public void setMilk(int milk) {
		this.milk = milk;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getSugar() {
		return sugar;
	}

	public void setSugar(int sugar) {
		this.sugar = sugar;
	}

	public int getTopping() {
		return topping;
	}

	public void setTopping(int topping) {
		this.topping = topping;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getJug() {
		return jug;
	}

	public void setJug(int jug) {
		this.jug = jug;
	}

	public int getCreatestatus() {
		return createstatus;
	}

	public void setCreatestatus(int createstatus) {
		this.createstatus = createstatus;
	}

	private boolean IsChanged=false;
	public void setChanged(boolean changed) {
		IsChanged = changed;
		if(createstatus==2)
		{
			createstatus =3;
		}
	}
	public int getPreselectvalue()
	{
		return this.volume + (this.strength<<1)+(this.milk<<1)+(this.topping<<1)+(this.chocolate<<1);
	}
	@Override
	public String toString() {
		return "BeverageBasic{" +
				"id=" + id +
				", pid=" + pid +
				", stoppable=" + stoppable +
				", cupSensor=" + cupSensor +
				", useCount=" + useCount +
				", drinkPrice=" + drinkPrice +
				", ledColor=" + ledColor +
				", ledMode=" + ledMode +
				", ledIntensity=" + ledIntensity +
				", dispenseType=" + dispenseType +
				", chocolate=" + chocolate +
				", milk=" + milk +
				", strength=" + strength +
				", sugar=" + sugar +
				", topping=" + topping +
				", volume=" + volume +
				", jug=" + jug +
				", quickDrink=" + quickDrink +
				", createstatus=" + createstatus +
				", name='" + name + '\'' +
				'}';
	}
}
