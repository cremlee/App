package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip;

import android.content.Intent;
import android.luna.Activity.Base.Constant;
import android.luna.ViewUi.Tip.TipBaseEditText;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Lee.li on 2018/2/11.
 */

public class IngredientnameTip extends TipBaseEditText {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int reqCode() {
        return Constant.REQ_INPUT;
    }

    @Override
    public String title() {
        return "Set Ingredient Name";
    }

    @Override
    public String hint() {
        return "Input Ingredient Name";
    }

    @Override
    public Intent intent() {
        return new Intent(this, IngredientnameTip.class);
    }

    @Override
    public boolean isReq() {
        return true;
    }

    @Override
    public int editTextInputType() {
        return 0;
    }
}
