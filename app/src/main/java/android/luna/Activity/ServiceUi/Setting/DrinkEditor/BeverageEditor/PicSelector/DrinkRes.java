package android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.PicSelector;

/**
 * Created by Lee.li on 2018/2/24.
 */

public class DrinkRes {
    private boolean isSelected =false;
    private String resPath="";
    public  DrinkRes(String resPath)
    { this.resPath = resPath; }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }
}
