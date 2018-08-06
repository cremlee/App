package android.luna.Activity.ServiceUi.Setting.Schedule.fragment;

import android.app.Fragment;
import android.luna.Activity.ServiceUi.Setting.Schedule.adapter.SchedulerAdapter;
import android.luna.Data.DAO.ScheduleDaoFactory;
import android.luna.Data.module.SchedulerDetail;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/30.
 */

public class SchedulerOverviewFragment extends Fragment {
    private ListView lstSchedulerOverview;
    private SchedulerAdapter adapter;
    private int schedule_type;
    private ScheduleDaoFactory scheduleDaoFactory =null;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_scheduler_overview, container, false);
        InitView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshoverview();
    }

    public void setdatas(ScheduleDaoFactory scheduleDaoFactory, int a)
    {
        this.schedule_type = a;
        this.scheduleDaoFactory = scheduleDaoFactory;

    }
    public void refreshoverview()
    {
        Map<Integer,List<SchedulerDetail>> detailMaps = scheduleDaoFactory.getScheduleDetailDao().findSchedulerDetails();
        adapter = new SchedulerAdapter(getActivity(),detailMaps,scheduleDaoFactory);
        lstSchedulerOverview.setAdapter(adapter);
    }
    private void InitView(View view)
    {
        lstSchedulerOverview = view.findViewById(R.id.list_scheduler_overview);
        lstSchedulerOverview.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.setting_scheduler_overview_header, null));
        //refreshoverview();
    }
}
