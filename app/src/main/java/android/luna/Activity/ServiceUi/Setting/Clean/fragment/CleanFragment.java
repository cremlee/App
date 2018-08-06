package android.luna.Activity.ServiceUi.Setting.Clean.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.Schedule.adapter.CleanSchedulerAdapter;
import android.luna.Data.DAO.ScheduleDaoFactory;
import android.luna.Data.module.Scheduler;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class CleanFragment extends Fragment  {
    private TextView title;
    public Button getStart() {
        return start;
    }
    private Button start;
    private ImageView icon;
    private TextView content;
    private MaterialDialog progressDialog;
    private IntentFilter filter;
    private CremApp app;

    public CremApp getApp(){return app;}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean_base, container, false);
        InitView(view);
        app = ((BaseActivity)getActivity()).getApp();
        return view;
    }
    public void InitView(View view)
    {
        title = view.findViewById(R.id.title);
        icon = view.findViewById(R.id.icon);
        content = view.findViewById(R.id.content);
        start = view.findViewById(R.id.start);
    }

    @Override
    public void onResume() {
        super.onResume();
        filter = new IntentFilter();
        filter.addAction(Constant.ACTION_ACK_CLEAN_REQUEST);
        filter.addAction(Constant.ACTION_CLEAN_MACHINE_FINISH);
        filter.addAction(Constant.ACTION_CMD_RSP_TIME_OUT);
        getActivity().registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int ackresult;
            if(action.equals(Constant.ACTION_ACK_CLEAN_REQUEST))
            {
                // TODO: 2018/7/13 diaoqi dialog ,block ui
                 ackresult = intent.getIntExtra("ACK",2);
                if (ackresult ==1)
                {
                    showCleanDialog();
                }else
                {
                    ((BaseActivity)getActivity()).showToastLong("Clean failed");
                }
            }
            else if(action.equals(Constant.ACTION_CMD_RSP_TIME_OUT))
            {
                // TODO: 2018/7/13 if ui block ,then release it otherwise show failed
                if(progressDialog!=null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
                ((BaseActivity)getActivity()).showToastLong("Clean Cmd Timeout!");
            }
            else if(action.equals(Constant.ACTION_CLEAN_MACHINE_FINISH)) {
               // TODO: 2018/7/13  release ui
                if(progressDialog!=null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }


    private void showCleanDialog()
    {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Machine clean in process")
                .canceledOnTouchOutside(true)
                .content("waiting...")
                .progress(true, 0)
                .show();
    }

    public void disableStart(){start.setEnabled(false);}
    public void enableStart(){start.setEnabled(true);}
    public void setTitle(String a)
    {
        title.setText(a);
    }

    public void setContent(String a)
    {
        content.setText(a);
    }

    public void setIcon(int rsid)
    {
        if(rsid ==0)
            icon.setImageBitmap(null);
        else
            icon.setImageResource(rsid);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }
}
