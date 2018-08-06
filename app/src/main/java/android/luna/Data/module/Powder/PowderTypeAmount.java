package android.luna.Data.module.Powder;

/**
 * Created by Lee.li on 2018/5/24.
 */

public class PowderTypeAmount {
    private int type;
    private float amount;

    public PowderTypeAmount(int type, float amount) {
        this.type = type;
        this.amount = amount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
