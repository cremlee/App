package android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter;

/**
 * Created by Lee.li on 2018/2/24.
 */

public class IngredientItems {
    private String name;
    private int pid;
    public IngredientItems(String name,int pid)
    {
        this.name =name;
        this.pid =pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
