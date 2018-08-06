package android.luna.Activity.ServiceUi.fragment;
import android.app.Fragment;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Utils.SystemUtil;
import android.luna.ViewUi.widget.SettingItemTextViewVer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class AboutFragment extends Fragment {
    private SettingItemTextViewVer ver_android,ver_kernal,ver_database,ver_app,ver_mode,blue_mac;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        InitView(view);
        return view;
    }

    private  void InitView(View view)
    {
        blue_mac = view.findViewById(R.id.blue_mac);
        ver_mode= view.findViewById(R.id.ver_mode);
        ver_app= view.findViewById(R.id.ver_app);
        ver_database= view.findViewById(R.id.ver_database);
        ver_android = view.findViewById(R.id.ver_android);
        ver_kernal = view.findViewById(R.id.ver_kernal);
        ver_android.setTextValue(SystemUtil.getSystemVersion());
        ver_kernal.setTextValue(SystemUtil.getKernelVersion());
        ver_app.setTextValue(SystemUtil.packageName(getActivity()));
        ver_database.setTextValue(SystemUtil.DatabaseVersion());
        ver_mode.setTextValue(SystemUtil.getSystemModel());
        String mac = ((BaseActivity)getActivity()).getApp().getBlue_mac();
        if(mac.equals(""))
            blue_mac.setTextValue("not in use");
        else
            blue_mac.setTextValue(mac);
    }
}
