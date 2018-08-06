package android.luna.Data.Interface;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.IngredientItems;
import android.luna.Data.module.BeverageIngredient;
import android.luna.Data.module.Ingredient;

import java.util.List;

/**
 * Created by Lee.li on 2018/2/13.
 */

public interface IBeverageIngredient extends IBeverage<BeverageIngredient> {
      List<BeverageIngredient> queryforbeveragepid(int pid);
      List<IngredientItems> GetAllIngredientName();
      Ingredient getIngredientByPid(int pid);
}
