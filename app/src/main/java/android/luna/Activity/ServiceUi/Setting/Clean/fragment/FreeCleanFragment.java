package android.luna.Activity.ServiceUi.Setting.Clean.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Utils.Device.DeviceXmlFactory;
import android.luna.Utils.FileHelper;
import android.luna.Utils.ImageConvertFactory;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.rs232.Cmd.CmdCleanMachine;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class FreeCleanFragment extends Fragment implements View.OnClickListener {
    private TextView title;
    private Button btnaction;
    private ImageView icon;
    private TextView content;
    private MaterialDialog progressDialog;
    private IntentFilter filter;
    private CremApp app;
    private  int stepcount =4;
    private int currentcleanstep;
    private ImageView index1,index2,index3,index4;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clean_step_base, container, false);
        InitView(view);
        app = ((BaseActivity)getActivity()).getApp();
        return view;
    }
    public void InitView(View view)
    {
        index1 = view.findViewById(R.id.index1);
        index2 = view.findViewById(R.id.index2);
        index3 = view.findViewById(R.id.index3);
        index4 = view.findViewById(R.id.index4);
        title = view.findViewById(R.id.title);
        icon = view.findViewById(R.id.icon);
        content = view.findViewById(R.id.content);
        btnaction = view.findViewById(R.id.action);
        btnaction.setOnClickListener(this);
        setTitle("Machine cleanning");
        setContent("Please follow the guide to finish the cleanning \n 1.Press the Start button.");
        setIcon(0);
        setactiontitle("Start");
        currentcleanstep =0;
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
                    setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB\n3.CCCCCCCCCCCCC");
                    setactiontitle("Start");
                    index1.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                    index2.setBackgroundColor(getActivity().getResources().getColor(R.color.yellow));
                    setIcon(ImageConvertFactory.getfrompath(FileHelper.PATH_CLEAN+"2.png"));
                    currentcleanstep =2;
                    btnaction.setVisibility(View.VISIBLE);
                }
                ((BaseActivity)getActivity()).showToastLong("Clean Cmd Timeout!");
            }
            else if(action.equals(Constant.ACTION_CLEAN_MACHINE_FINISH)) {
               // TODO: 2018/7/13  release ui
                if(progressDialog!=null && progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                    index2.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                    index3.setBackgroundColor(getActivity().getResources().getColor(R.color.yellow));
                    setIcon(ImageConvertFactory.getfrompath(FileHelper.PATH_CLEAN+"3.png"));
                    currentcleanstep =3;
                    setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB");
                    btnaction.setVisibility(View.VISIBLE);
                    setactiontitle("Next");
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

    public void setIcon(Bitmap rsid)
    {
        icon.setImageBitmap(rsid);
    }

    public void setactiontitle(String a)
    {
        btnaction.setText(a);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    private void dealwithCleanUi(int step)
    {
        switch (step)
        {
            case 0:
                setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB");
                setactiontitle("Next");
                index1.setBackgroundColor(getActivity().getResources().getColor(R.color.yellow));
                setIcon(ImageConvertFactory.getfrompath(FileHelper.PATH_CLEAN+"1.png"));
                currentcleanstep =1;
                break;
            case 1:
                setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB\n3.CCCCCCCCCCCCC");
                setactiontitle("Start");
                index1.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                index2.setBackgroundColor(getActivity().getResources().getColor(R.color.yellow));
                setIcon(ImageConvertFactory.getfrompath(FileHelper.PATH_CLEAN+"2.png"));
                currentcleanstep =2;
                break;
            case 2:
                String  cmd="";
                try {cmd = new CmdCleanMachine().buildCmdStart(DeviceXmlFactory.getCleanComponent(FileHelper.FILE_DEVICE_CONFIG,DeviceXmlFactory.CLEAN_TYPE_DAILY));
                }
                catch (Exception e){}
                if(!cmd.equals(""))
                    ((BaseActivity)getActivity()).getApp().addCmdQueue(cmd);
                setContent("Waiting....");
                btnaction.setVisibility(View.GONE);
                currentcleanstep =21;
                break;
            case 3:
                setContent("1. AAAAAAAAAAAAAAAAAA \n2. BBBBBBBBBBBBBBB");
                setactiontitle("Next");
                index3.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                index4.setBackgroundColor(getActivity().getResources().getColor(R.color.yellow));
                setIcon(ImageConvertFactory.getfrompath(FileHelper.PATH_CLEAN+"4.png"));
                currentcleanstep =4;
                break;
            case 4:
                setContent("Clean finished");
                index4.setBackgroundColor(getActivity().getResources().getColor(R.color.green));
                setIcon(0);
                setactiontitle("Done");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.action:
                dealwithCleanUi(currentcleanstep);
                break;
        }
    }
}
