package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/4/25.
 */
@DatabaseTable(tableName = "tb_person_item")
public class PersonItem extends BaseIndexPinyinBean implements Serializable{
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "")
    private String name;
    @DatabaseField(defaultValue = "")
    private String iconpath;
    @DatabaseField(defaultValue = "0")
    private int islocked;
    @DatabaseField(defaultValue = "")
    private String psw;

    private boolean isTop =false;

    public PersonItem() {
    }

    public PersonItem(String name, String iconpath, int islocked, String psw) {
        this.name = name;
        this.iconpath = iconpath;
        this.islocked = islocked;
        this.psw = psw;
    }

    public PersonItem(String name, String iconpath, int islocked, String psw, boolean isTop) {
        this.name = name;
        this.iconpath = iconpath;
        this.islocked = islocked;
        this.psw = psw;
        this.isTop = isTop;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public int getIslocked() {
        return islocked;
    }

    public void setIslocked(int islocked) {
        this.islocked = islocked;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public boolean isTop() {
        return isTop;
    }

    public PersonItem setTop(boolean top) {
        isTop = top;
        return this;
    }

    @Override
    public String getTarget() {
        return name;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }
}
