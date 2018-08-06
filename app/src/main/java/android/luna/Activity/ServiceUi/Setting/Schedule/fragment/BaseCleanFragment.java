package android.luna.Activity.ServiceUi.Setting.Schedule.fragment;

import android.app.Fragment;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.Schedule.adapter.CleanSchedulerAdapter;
import android.luna.Data.DAO.ScheduleDaoFactory;
import android.luna.Data.module.Scheduler;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class BaseCleanFragment extends Fragment {
    private TextView title;
    private ListView lv_schedule;
    private ScheduleDaoFactory scheduleDaoFactory =null;
    private int schedule_type;
    private List<Scheduler> _data;
    public BaseCleanFragment(){}
    private CleanSchedulerAdapter cleanSchedulerAdapter;
    public void setdatas(ScheduleDaoFactory scheduleDaoFactory,int a)
    {
        this.schedule_type = a;
        this.scheduleDaoFactory = scheduleDaoFactory;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dailyclean_setting, container, false);
        _data = this.scheduleDaoFactory.getScheduleEventDao().queryBytype(this.schedule_type);
        cleanSchedulerAdapter = new CleanSchedulerAdapter(_data,getActivity());
        cleanSchedulerAdapter.setOnSchedulerChanged(new CleanSchedulerAdapter.OnSchedulerChanged() {
            @Override
            public void delete(int position) {
                scheduleDaoFactory.getScheduleEventDao().delete(_data.get(position));
                scheduleDaoFactory.getScheduleDetailDao().deleteBySchedulerId(_data.get(position).getId());
                _data.remove(position);
                cleanSchedulerAdapter.notifyDataSetChanged();
            }

            @Override
            public void edit(int position) {
                ((BaseActivity)getActivity()).showToast(_data.get(position).toString());
            }
        });
        InitView(view);

        return view;
    }
    public void InitView(View view)
    {
        title = view.findViewById(R.id.title);
        lv_schedule =view.findViewById(R.id.lv_schedule);
        lv_schedule.setAdapter(cleanSchedulerAdapter);
        switch (this.schedule_type)
        {
            case Scheduler.TYPE_DAILY_CLEAN:
                title.setText("Daily clean Scheduler");
                break;
            case Scheduler.TYPE_WEEKLY_CLEAN:
                title.setText("Weekly clean Scheduler");
                break;
        }
    }
}
