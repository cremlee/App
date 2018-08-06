package android.luna.Utils.Nutrition;

import android.luna.Data.module.Powder.PowderNutrition;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee.li on 2018/5/24.
 */

public class CalcHelpper {
    public static List<NutritionListItem> getNutritionfromdrink(PowderNutrition a)
    {
        DecimalFormat fnum  =   new  DecimalFormat("##0.00");
        List<NutritionListItem> ret = new ArrayList<>(3);
        ret.add(new NutritionListItem("Fat","Sugar","Sodium","Protein","Carbohydrate","Kilo"));
        ret.add(new NutritionListItem(fnum.format(a.getFat()),fnum.format(a.getSugar()),fnum.format(a.getSodium()),fnum.format(a.getProtein()),fnum.format(a.getCarbohydrate()),fnum.format(a.getKilocalorie())));
        return ret;
    }
}
