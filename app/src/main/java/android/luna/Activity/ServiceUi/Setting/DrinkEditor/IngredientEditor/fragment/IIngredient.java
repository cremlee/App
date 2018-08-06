package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;

/**
 * Created by Lee.li on 2018/2/11.
 */

public interface IIngredient<T> {
    void InitView(T a);
    void InitEvent();
    void save();
    void NotifyChange();
    void NotifyNameChange(String name);
}
