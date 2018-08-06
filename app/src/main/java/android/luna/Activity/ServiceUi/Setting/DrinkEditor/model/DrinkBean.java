package android.luna.Activity.ServiceUi.Setting.DrinkEditor.model;

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;

/**
 * Created by zhangxutong .
 * Date: 16/08/28
 */

public class DrinkBean extends BaseIndexPinyinBean {

    private String drinkname;
    private String iconpath="";
    private int pid;
    private boolean isTop;//是否是最上面的 不需要被转化成拼音的
    private boolean isNew;

    public boolean isCandelete() {
        return candelete;
    }

    public void setCandelete(boolean candelete) {
        this.candelete = candelete;
    }

    private boolean candelete=false;

    public DrinkBean() {}
    public DrinkBean(String drinkname) {
        this.drinkname = drinkname;
    }
    public DrinkBean(int pid,String drinkname, String path,boolean candelete,boolean isnew) {
        this.pid =pid;
        this.drinkname = drinkname;
        this.iconpath=path;
        this.candelete =candelete;
        this.isNew =isnew;
    }


    public DrinkBean setdrinkname(String drinkname) {
        this.drinkname = drinkname;
        return this;
    }

    public boolean isTop() {
        return isTop;
    }

    public DrinkBean setTop(boolean top) {
        isTop = top;
        return this;
    }

    @Override
    public String getTarget() {
        return drinkname;
    }

    @Override
    public boolean isNeedToPinyin() {
        return !isTop;
    }


    @Override
    public boolean isShowSuspension() {
        return !isTop;
    }

    public String getDrinkname() {
        return drinkname;
    }

    public String getIconpath() {
        return iconpath;
    }

    public int getPid() {
        return pid;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}
