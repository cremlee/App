package android.luna.Data.CustomerUI;

/**
 * Created by Lee.li on 2017/12/26.
 */

public class DrinkMenuButton {
    private String name;
    private float price;
    private String iconpath;
    private String bgPath;
    private String storypath;
    private boolean isgroup;
    private int drinkstate;   //0-noraml  1-out of stock 2 not available 3 wasterbin is full
    private int preselect;      //bit0~bit7
                                //cup-strength-milk-top
    private int jugmode;

    public int getJugmode() {
        return jugmode;
    }

    public void setJugmode(int jugmode) {
        this.jugmode = jugmode;
    }

    public int getPreselect() {
        return preselect;
    }

    public void setPreselect(int preselect) {
        this.preselect = preselect;
    }

    public int getDrinkstate() {
        return drinkstate;
    }

    public void setDrinkstate(int drinkstate) {
        this.drinkstate = drinkstate;
    }

    public String getStorypath() {
        return storypath;
    }

    public void setStorypath(String storypath) {
        this.storypath = storypath;
    }

    public String getDispensepath() {
        return dispensepath;
    }

    public void setDispensepath(String dispensepath) {
        this.dispensepath = dispensepath;
    }

    private String dispensepath;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    private int pid=-1;

    public DrinkMenuButton(String name) {
        this.name = name;
        this.price =0.0f;
    }

    public DrinkMenuButton(String name,String iconpath) {
        this.name = name;
        this.iconpath =iconpath;
        this.price =0.0f;
    }

    public DrinkMenuButton(String name, float price, String iconpath, String bgPath,String storypath,String dispensepath) {
        this.name = name;
        this.price = price;
        this.iconpath = iconpath;
        this.bgPath = bgPath;
        this.storypath =storypath;
        this.dispensepath =dispensepath;
    }

    public DrinkMenuButton(String name, float price, String iconpath, String bgPath, String storypath, boolean isgroup, String dispensepath, int pid) {
        this.name = name;
        this.price = price;
        this.iconpath = iconpath;
        this.bgPath = bgPath;
        this.storypath = storypath;
        this.isgroup = isgroup;
        this.dispensepath = dispensepath;
        this.pid = pid;
    }

    public boolean isgroup() {
        return isgroup;
    }

    public void setIsgroup(boolean isgroup) {
        this.isgroup = isgroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIconpath() {
        return iconpath;
    }

    public void setIconpath(String iconpath) {
        this.iconpath = iconpath;
    }

    public String getBgPath() {
        return bgPath;
    }

    public void setBgPath(String bgPath) {
        this.bgPath = bgPath;
    }

    public boolean isVolume()
    {
        return (preselect&0x01)==0x01;
    }
    public boolean isStrength()
    {
        return (preselect&0x02)==0x02;
    }
    public boolean isMilk()
    {
        return (preselect&0x04)==0x04;
    }
    public boolean isTopping()
    {
        return (preselect&0x08)==0x08;
    }
}
