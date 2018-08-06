package android.luna.ViewUi.OfficeUi.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class PasswordFragment extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_psw, container, false);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
        }
    }
}
