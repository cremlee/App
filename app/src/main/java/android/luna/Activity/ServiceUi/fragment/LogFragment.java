package android.luna.Activity.ServiceUi.fragment;


import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.LogRecordAdapter;
import android.luna.ViewUi.pickerview.TimePickerDialog;
import android.luna.ViewUi.pickerview.data.Type;
import android.luna.ViewUi.pickerview.listener.OnDateSetListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.yinglan.keyboard.HideUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class LogFragment extends Fragment implements View.OnClickListener,OnDateSetListener {
    private CremApp app;
    private ListView lsvlog;
    private Button btn_search;
    private EditText stoptime,starttime;
    private LogRecordAdapter mLogRecordAdapter;
    TimePickerDialog mDialogYearMonthDay;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        InitView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CremApp)getActivity().getApplication();

    }

    private void InitView(View view)
    {
        mLogRecordAdapter = new LogRecordAdapter(null,getActivity());

        lsvlog = view.findViewById(R.id.lsvlog);
        btn_search= view.findViewById(R.id.btn_search);
        stoptime= view.findViewById(R.id.stoptime);
        starttime= view.findViewById(R.id.starttime);
        btn_search.setOnClickListener(this);
        starttime.setOnClickListener(this);
        stoptime.setOnClickListener(this);
        mDialogYearMonthDay = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)

                .setCallBack(this)
                .build();
        lsvlog.setAdapter(mLogRecordAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_search:
                HideUtil.hideSoftKeyboard(getActivity());
                mLogRecordAdapter.setGridItems(app.QueryByDateTypeKeyWord(starttime.getText().toString(),stoptime.getText().toString(),null,null));
                break;
            case R.id.stoptime:
                mDialogYearMonthDay.show(getFragmentManager(), "year_month_day");
                currentdateselect =2;
                break;
            case R.id.starttime:
                mDialogYearMonthDay.show(getFragmentManager(), "year_month_day");
                currentdateselect =1;
                break;
        }
    }
    private int currentdateselect =0;   //0: init 1:start 2:stop
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        //EvoTrace.e("123",getDateToString(millseconds));
        if(currentdateselect == 1)
        {
            starttime.setText(getDateToString(millseconds));
        }
        else if(currentdateselect == 2)
        {
            stoptime.setText(getDateToString(millseconds));
        }
    }
    public String getDateToString(long time) {
        Date d = new Date(time);
        return sf.format(d);
    }
}
