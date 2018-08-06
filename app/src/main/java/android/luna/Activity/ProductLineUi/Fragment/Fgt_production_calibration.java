package android.luna.Activity.ProductLineUi.Fragment;

import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class Fgt_production_calibration extends productionFragment implements View.OnClickListener {
    private Button next,upload;
    private MaterialDialog progressDialog;
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
        View view = inflater.inflate(R.layout.fg_production_calibration, container, false);
        InitView(view);
        return view;
    }

    @Override
    public void InitView(View view) {
        next = view.findViewById(R.id.next);
        next.setOnClickListener(this);
    }

    private void showuploadwindow()
    {
        progressDialog = new MaterialDialog.Builder(getActivity())
                .title("Upload config")
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
                    _nextListerner.Clicked(3);
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
