package android.luna.Activity.CustomerUI.Payment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/8/1.
 */

abstract public class PayFragment extends Fragment {
    public abstract void InitView(View view);
    public abstract void InitData();
    public abstract void InitEvent();


}
