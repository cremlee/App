package android.luna.Activity.ServiceUi.Setting.Clean.fragment;

import android.luna.Activity.Base.BaseActivity;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.rs232.Ack.AckQuery;
import android.luna.rs232.Cmd.CmdCleanMachine;
import android.view.View;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/1.
 */

public class WeeklyCleanFragment extends  CleanFragment implements View.OnClickListener {
    private String cmd="";
    @Override
    public void InitView(View view) {
        super.InitView(view);
        setTitle("Weekly Clean");
        setIcon(R.mipmap.clean_weekly_care);
        setContent(getActivity().getString(R.string.clean_weekly_cxt));
        getStart().setOnClickListener(this);
        try {
            cmd = new CmdCleanMachine().buildCmdStart(DeviceXmlFactory.getCleanComponent(FileHelper.FILE_DEVICE_CONFIG,DeviceXmlFactory.CLEAN_TYPE_WEEKLY));
        }
        catch (Exception e){}
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
                if(getApp().getAckQueryInstance().getMachine_state()!= AckQuery.MS_NORMAL_IDEL)
                {
                    ((BaseActivity)getActivity()).showToast("Machine state error!");
                    break;
                }
                if(!cmd.equals(""))
                    ((BaseActivity)getActivity()).getApp().addCmdQueue(cmd);
                break;
        }
    }
}
