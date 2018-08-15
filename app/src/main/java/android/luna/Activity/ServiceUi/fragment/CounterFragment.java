package android.luna.Activity.ServiceUi.fragment;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Typeface;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Adapter.CoffeeCountAdapter;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.BeverageCount;
import android.luna.Data.module.BeverageUi;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import evo.luna.android.R;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class CounterFragment extends Fragment implements View.OnClickListener ,IAuthManage {
    private PieChart mChart;
    private ColumnChartView helloChart;
    private Typeface tf;
    private BeverageFactoryDao beverageFactoryDao;
    private List<BeverageCount> beverageCountList;
    private List<BeverageCount> beverageCountListCopy =new ArrayList<>();
    private List<piedata> piedatas = new ArrayList<>(6);
    private CoffeeCountAdapter coffeeCountAdapter;
    private ListView list_counter;
    private ImageView chart_bar;
    private RelativeLayout lyt_chart;
    private ScrollView  sv;
    private SettingItemTextView2 resetCountBeverageItem,resetCountProgramItem;
    private CremApp app;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, container, false);
        InitData();
        InitView(view);
        AuthManage(view);
        return view;
    }
    private Typeface mTf;
    private void InitView(View view)
    {
        resetCountBeverageItem = view.findViewById(R.id.resetCountBeverageItem);
        resetCountProgramItem = view.findViewById(R.id.resetCountProgramItem);
        resetCountBeverageItem.setOnClickListener(this);
        resetCountProgramItem.setOnClickListener(this);

        list_counter = view.findViewById(R.id.list_counter);
        chart_bar = view.findViewById(R.id.chart_bar);
        chart_bar.setOnClickListener(this);
        lyt_chart = view.findViewById(R.id.lyt_chart);
        lyt_chart.setVisibility(View.GONE);
        mChart = view.findViewById(R.id.mChart);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);
        mChart.setDragDecelerationFrictionCoef(0.95f);
        tf = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mChart.setBackgroundColor(Color.TRANSPARENT);
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText(getString(R.string.VIEW_UI_TOP_5));

        sv = view.findViewById(R.id.sv);

        setData(4, 100);
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);





        list_counter.setAdapter(coffeeCountAdapter);
        if (coffeeCountAdapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < coffeeCountAdapter.getCount(); i++) {
                View listItem = coffeeCountAdapter.getView(i, null, list_counter);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = list_counter.getLayoutParams();
            params.height = totalHeight + (list_counter.getDividerHeight() * (coffeeCountAdapter.getCount() - 1));
            list_counter.setLayoutParams(params);
        }

        helloChart = view.findViewById(R.id.helloChart);
        setHelloChartData();
    }
    private ColumnChartData data;
    private void setHelloChartData() {
        List<SubcolumnValue> values;
        List<AxisValue> axisValues = new ArrayList<>();
        List<Column> columns = new ArrayList<>();


        beverageCountListCopy.add(0,new BeverageCount());
        int maxvalue =0;
        for(int i=0;i<beverageCountListCopy.size();i++)
        {
            if(maxvalue<beverageCountListCopy.get(i).getDrinkCount())
            {
                maxvalue =beverageCountListCopy.get(i).getDrinkCount()+10;
            }
            values = new ArrayList<>();
            values.add(new SubcolumnValue((float)beverageCountListCopy.get(i).getDrinkCount(), ChartUtils.pickColor()));
            axisValues.add(new AxisValue(i).setLabel(beverageCountListCopy.get(i).getName()));
            if(i==0)
                columns.add(new Column(values).setHasLabels(false));
            else
                columns.add(new Column(values).setHasLabels(true));
        }
        data = new ColumnChartData(columns);
        data.setAxisXBottom(new Axis(axisValues).setMaxLabelChars(5));
        data.setAxisYLeft(new Axis().setHasTiltedLabels(false));
        helloChart.setColumnChartData(data);
        // Set selection mode to keep selected month column highlighted.
        helloChart.setValueSelectionEnabled(true);
        helloChart.setViewportCalculationEnabled(true);
        Viewport v = new Viewport(0, maxvalue<50?50:maxvalue, beverageCountListCopy.size(), 0);
        helloChart.setMaximumViewport(v);
        helloChart.setCurrentViewport(new Viewport(0, maxvalue<50?50:maxvalue, 8, 0));
        helloChart.setZoomEnabled(false);
    }


    private void InitData()
    {
        beverageFactoryDao = new BeverageFactoryDao(getActivity(),(CremApp)getActivity().getApplication());
        beverageCountList= beverageFactoryDao.getBeverageCountDao().queryall();
        String name ="other";
        String path ="";
        int othercount =0;
        piedatas.clear();
        beverageCountListCopy.clear();
        if(beverageCountList!=null) {
            Collections.sort(beverageCountList);
            for (int i=0;i<beverageCountList.size();i++)
            {

                int pid = beverageCountList.get(i).getPid();
                BeverageUi tmp = beverageFactoryDao.getBeverageUiDao().query(pid);
                path = tmp==null?"":tmp.getIconPath();
                name =  beverageFactoryDao.getBeverageNameDao().getDrinkname(pid);
                beverageCountList.get(i).setName(name);
                beverageCountList.get(i).setIconpath(path);
                int count = beverageCountList.get(i).getDrinkCount();
                if(i<4)
                {
                    piedatas.add(new piedata(name,count));
                }else
                {
                    othercount+=count;
                    if(i== beverageCountList.size()-1)
                        piedatas.add(new piedata("ohters",othercount));
                }
                beverageCountListCopy.add(beverageCountList.get(i).clone());
            }
            coffeeCountAdapter = new CoffeeCountAdapter(getActivity(),beverageCountList);
        }

    }
    //counter
    private void setData(int count, float range) {
        float mult = range;
        ArrayList<PieEntry> entries = new ArrayList<>();
        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if(piedatas.size()<=0)
            return;

        for (int i = 0; i < piedatas.size() ; i++) {
            entries.add(new PieEntry((float) piedatas.get(i).getCount(),piedatas.get(i).getName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (CremApp)getActivity().getApplication();

    }

    @Override
    public void onClick(View v) {
       if(v.getId() == R.id.chart_bar)
       {
           if(lyt_chart.getVisibility() == View.GONE) {
               lyt_chart.setVisibility(View.VISIBLE);
               sv.setVisibility(View.GONE);
           }
           else
           {
               lyt_chart.setVisibility(View.GONE);
               sv.setVisibility(View.VISIBLE);
           }
       }
       else if(v.getId() == R.id.resetCountBeverageItem)
       {
           //// TODO: 2018/5/21 reset drink counters
       }
       else if(v.getId() == R.id.resetCountProgramItem)
       {
           //// TODO: 2018/5/21 reset clean counters
       }
    }

    @Override
    public void AuthManage(View view) {
        if(app.getAuth_level() < 3) {
            resetCountBeverageItem.setVisibility(View.GONE);
            resetCountProgramItem.setVisibility(View.GONE);
        }

    }

    class piedata
    {
       private String name;
       private int count;

        public piedata() {

        }
        public piedata(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
