package android.luna.Data.module.Clean;

/**
 * Created by Lee.li on 2018/8/10.
 */

public class CleanStep implements ICleanStep {
    private boolean isaction;
    private int actionid;
    private int actionindex;

    public boolean isaction() {
        return isaction;
    }

    public void setIsaction(boolean isaction) {
        this.isaction = isaction;
    }

    public int getActionid() {
        return actionid;
    }

    public void setActionid(int actionid) {
        this.actionid = actionid;
    }

    public int getActionindex() {
        return actionindex;
    }

    public void setActionindex(int actionindex) {
        this.actionindex = actionindex;
    }

    @Override
    public String gethelp() {
        return null;
    }
}
