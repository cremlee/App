package android.luna.Activity.ServiceUi.Setting.Clean.fragment;

import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.view.View;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/1.
 */

public class BrewerCleanFragment extends  CleanFragment implements View.OnClickListener {
    @Override
    public void InitView(View view) {
        super.InitView(view);
        setTitle(getActivity().getString(R.string.clean_brew_title));
        setIcon(R.mipmap.clean_brewer);
        setContent(getActivity().getString(R.string.clean_brew_cxt));
        getStart().setOnClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start:
                // TODO: 2018/6/1  1.blocl the ui 2.send clean cmd 3.release ui until finished
                break;
        }
    }
}
