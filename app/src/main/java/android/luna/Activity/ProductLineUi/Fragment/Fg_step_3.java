package android.luna.Activity.ProductLineUi.Fragment;

import android.luna.Activity.Base.BaseActivity;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.rs232.Cmd.CmdCleanMachine;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/6.
 */

public class Fg_step_3 extends  productionFragment implements View.OnClickListener{
    private TextView info;
    private String cmd="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_step_3, container, false);
        InitView(view);

        return view;
    }

    @Override
    public void InitView(View view) {
        view.findViewById(R.id.open).setOnClickListener(this);
        info=view.findViewById(R.id.info);
    }
    @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.open:
                    // TODO: 2018/7/12 da kai jiqi de shui fa
                    try {
                        cmd = new CmdCleanMachine().buildCmdStart(DeviceXmlFactory.getCleanComponent(FileHelper.FILE_DEVICE_CONFIG,DeviceXmlFactory.CLEAN_TYPE_DRY_CLOSE));
                    }
                    catch (Exception e){}
                    if(!cmd.equals(""))
                        ((BaseActivity)getActivity()).getApp().addCmdQueue(cmd);
                    break;
            }
    }
}
