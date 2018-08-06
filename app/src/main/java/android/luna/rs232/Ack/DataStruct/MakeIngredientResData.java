package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/3.
 */

public class MakeIngredientResData implements IEncode {
    private int opcmd;
    private int ingredientid;

    public int getOpcmd() {
        return opcmd;
    }

    public void setOpcmd(int opcmd) {
        this.opcmd = opcmd;
    }

    public int getIngredientid() {
        return ingredientid;
    }

    public void setIngredientid(int ingredientid) {
        this.ingredientid = ingredientid;
    }

    @Override
    public void Encodeing2class(String[] ack) {
        int pos =0;
        opcmd = Integer.valueOf(ack[pos++], 16);
        ingredientid = (Integer.valueOf(ack[pos], 16)<<8) +Integer.valueOf(ack[pos+1], 16);
    }

    @Override
    public int getlength() {
        return 3;
    }
}
