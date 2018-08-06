package android.luna.Data.module.Powder;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/17.
 */
@DatabaseTable(tableName = "tb_powder_item")
public class PowderItem implements Serializable{
    private static final long serialVersionUID = 1L;
    public static final int ITEM_1 =0x01;
    public static final int ITEM_2 =0x02;
    public static final int ITEM_3 =0x04;
    public static final int ITEM_4 =0x08;
    public static final int ITEM_5 =0x10;
    public static final int ITEM_6 =0x20;
    public static final int ITEM_7 =0x40;
    public static final int ITEM_8 =0x80;
    public static final int ITEM_9 =0x0100;
    public static final int ITEM_10 =0x0200;
    public static final int ITEM_11 =0x0400;
    public static final int ITEM_12 =0x0800;
    public static final int ITEM_13 =0x1000;

    public static final int GROUP_GRINDER =2;
    public static final int GROUP_INSTANT=3;


    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int pid;
    @DatabaseField(defaultValue = "new powder")
    private String name="new powder";
    @DatabaseField
    private int isdefault;
    @DatabaseField(defaultValue = "0")
    private int containmask=0;
    @DatabaseField(defaultValue = "0")
    private int maycontainmask=0;
    @DatabaseField(defaultValue = "3")
    private int group =3;
    @DatabaseField(defaultValue = "0")
    private int selected =0;
    @DatabaseField(defaultValue = "1") //密度 g/ml
    private int density = 1;



    public PowderItem() {
    }

    public PowderItem(int pid, String name, int isdefault, int containmask, int maycontainmask) {
        this.pid = pid;
        this.name = name;
        this.isdefault = isdefault;
        this.containmask = containmask;
        this.maycontainmask = maycontainmask;
    }

    public PowderItem(int pid, String name, int isdefault, int containmask, int maycontainmask, int group, int selected, int density) {
        this.pid = pid;
        this.name = name;
        this.isdefault = isdefault;
        this.containmask = containmask;
        this.maycontainmask = maycontainmask;
        this.group = group;
        this.selected = selected;
        this.density = density;
    }

    public int getDensity() {
        return density;
    }

    public void setDensity(int density) {
        this.density = density;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
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

    public int getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public int getContainmask() {
        return containmask;
    }

    public void setContainmask(int containmask) {
        this.containmask = containmask;
    }

    public int getMaycontainmask() {
        return maycontainmask;
    }

    public void setMaycontainmask(int maycontainmask) {
        this.maycontainmask = maycontainmask;
    }

    private int[] resIds = new int[]{
            R.mipmap.con_cellery,R.mipmap.con_crustaceans,R.mipmap.con_eggs,R.mipmap.con_fish,
            R.mipmap.con_gluten,R.mipmap.con_lupin,R.mipmap.con_milk,R.mipmap.con_mollucs,
            R.mipmap.con_mustard,R.mipmap.con_nuts,R.mipmap.con_peanuts,R.mipmap.con_seasame,
            R.mipmap.con_sulphite
    };
    private String[] resname = new String[]{
            "cellery","crustaceans","eggs","fish","gluten","lupin","milk",
            "mollucs","mustard","nuts","peanuts","seasame","sulphite"};
    private ContainItem getContainItem(int i) {
        ContainItem tmp = new ContainItem();
        tmp.itemcode = i;
        tmp.iconresid = resIds[i];
        tmp.itemname =resname[i];
        if (((getContainmask() >> i) & 0x01) == 0x01) {
            tmp.selectcode = 2;
        } else if (((getMaycontainmask() >> i) & 0x01) == 0x01) {
            tmp.selectcode = 1;
        } else {
            tmp.selectcode = 0;
        }
        return tmp;
    }

    public List<ContainItem> getallItem() {
        List<ContainItem> tmp = new ArrayList<>(14);
        for (int i = 0; i < 13; i++) {
            tmp.add(getContainItem(i));
        }
        return tmp;
    }

    public void setContainMask(List<ContainItem> d)
    {
        containmask = 0;
        maycontainmask =0;
        for (ContainItem item :d)
        {
            if(item.selectcode ==1)  //may
            {
                maycontainmask += (1 << item.itemcode);
            }
            else if(item.selectcode ==2) //contain
            {
                containmask += (1 << item.itemcode);
            }
        }
    }
}
