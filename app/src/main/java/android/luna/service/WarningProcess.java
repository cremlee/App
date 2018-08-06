package android.luna.service;

import android.content.Intent;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Warning.MachineWarn;
import android.luna.Utils.Logger.EvoTrace;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lee.li on 2018/2/6.
 */

public class WarningProcess extends Thread implements Runnable {
    private final static String TAG = "WarningProcess";
    private CremApp app;
    private boolean IsStop =false;
    private List<MachineWarn> _currentwarningList =new ArrayList<>(20);
    public WarningProcess(ComService service)
    {
        app = (CremApp)service.getApplication();
    }

    public void free()
    {
        IsStop =true;
    }

    @Override
    public void run() {
        super.run();
        while(!IsStop)
        {
            mySleep(1000);
            HandleWarningMsg();
        }
    }
    private void mySleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void warningList()
    {
        _currentwarningList.clear();
        for (MachineWarn item:app.getallMachineWarnList())
        {
            _currentwarningList.add(item);
        }
    }
    private void HandleWarningMsg()
    {
        if(CompareWarning())
        {
            //// TODO: 2018/2/6 tongzhi warning fasheng gaibian
            EvoTrace.e(TAG,"warning change!");
            warningList();
            Intent waningmsg = new Intent(Constant.ACTION_WARNING_NOTIFICATION);
            app.getApplicationContext().sendBroadcast(waningmsg);
        }
    }
    private boolean CompareWarning()
    {
        if(_currentwarningList.size()!=app.getallMachineWarnList().size())
        {
            EvoTrace.e(TAG,"CompareWarning !size");
            return true;
        }
        else
        {
            Collections.sort(_currentwarningList);
            Collections.sort(app.getallMachineWarnList());
            for (int i=0;i<_currentwarningList.size();i++)
            {
                if(!_currentwarningList.get(i).equals(app.getallMachineWarnList().get(i)))
                {
                    EvoTrace.e(TAG,"CompareWarning !value");
                    return true;
                }
            }
        }
        return false;
    }
}
