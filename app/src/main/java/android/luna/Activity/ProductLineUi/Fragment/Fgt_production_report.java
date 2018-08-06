package android.luna.Activity.ProductLineUi.Fragment;

import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ProductLineUi.adapter.adapter_production_test;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class Fgt_production_report extends productionFragment implements View.OnClickListener {
    private Button next,upload;
    private MaterialDialog progressDialog;
    private ListView test_item;
    private CremApp app;
    private BaseActivity activity;
    public  interface nextListerner
    {
        void Clicked(int a);
    }
    private nextListerner _nextListerner;
    public void setOnnextListerner(nextListerner a)
    {
        _nextListerner =a;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_production_report, container, false);
        InitView(view);
        return view;
    }

    private adapter_production_test adapter_test;
    @Override
    public void InitView(View view) {
        activity = (BaseActivity)getActivity();
        app = activity.getApp();
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);
        upload = view.findViewById(R.id.upload);
        upload.setOnClickListener(this);
        test_item = view.findViewById(R.id.test_item);
        adapter_test = new adapter_production_test(activity,app.getTestItemsReport());
        test_item.setAdapter(adapter_test);
        ((TextView)view.findViewById(R.id.date)).setText(getdate());
        ((TextView)view.findViewById(R.id.id)).setText("888888");
    }

    private String getdate()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return  format.format(new Date());
    }
    private void showuploadwindow()
    {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Upload report")
                .canceledOnTouchOutside(false)
                .content("uploading...")
                .progress(true, 0)
                .show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.next:
                if(_nextListerner!=null)
                    _nextListerner.Clicked(4);
                break;
            case R.id.upload:
                showuploadwindow();
                mHandler.sendEmptyMessageDelayed(1000,3000);
                break;
        }
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    if(progressDialog!=null)
                    {
                        progressDialog.dismiss();
                    }
                    next.setVisibility(View.VISIBLE);
                default:
                    break;
            }
        }
    };


}
