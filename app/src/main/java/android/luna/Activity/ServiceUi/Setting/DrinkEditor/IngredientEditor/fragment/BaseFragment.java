package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;

import android.app.Fragment;

/**
 * Created by Lee.li on 2018/2/11.
 */

public class BaseFragment extends Fragment {
    public OningredientChanged oningredientChanged=null;
    public interface OningredientChanged
    {
        void itemchanged(int type);
    }
    public void SetOningredientChanged(OningredientChanged a)
    {
        oningredientChanged =a;
    }
}
