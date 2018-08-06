package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/4/25.
 */
@DatabaseTable(tableName = "tb_person_drink")
public class PersonDrink implements Serializable{
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int uid;
    @DatabaseField
    private int pid;
    @DatabaseField
    private String name="new one";
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
    @DatabaseField(defaultValue = "1")
    private int position = 1;
    @DatabaseField(defaultValue = "0")
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public PersonDrink() {
    }

    public PersonDrink(int uid, int pid, String name) {
        this.uid = uid;
        this.pid = pid;
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
}
