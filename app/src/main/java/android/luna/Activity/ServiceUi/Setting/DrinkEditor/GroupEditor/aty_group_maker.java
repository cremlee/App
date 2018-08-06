package android.luna.Activity.ServiceUi.Setting.DrinkEditor.GroupEditor;

import android.database.DataSetObserver;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.GroupAdapter;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.BeverageGroup;
import android.luna.ViewUi.widget.*;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/10.
 */

public class aty_group_maker extends BaseActivity implements View.OnClickListener {

    private TextView btn_back;
    private Spinner group_select,group_item_filter_1,group_item_filter_2;
    private Button btn_del,btn_new,btn_save,group_item_add,group_item_remove,btn_search;
    private SettingItemTextView2 group_item_name,group_item_icon;
    private SettingItemCheckBox group_item_size,group_item_show;
    private List<BeverageGroup> beverageGroupList =null;
    private BeverageFactoryDao beverageFactoryDao =null;
    private HashMap<Integer,String> groupadapter = new HashMap<>();
    private GroupAdapter groupAdapter;

    private int _groupid =-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_groupsetting_main);
        btn_back = findViewById(R.id.btn_back);
        group_select= findViewById(R.id.group_select);
        group_item_filter_1= findViewById(R.id.group_item_filter_1);
        group_item_filter_2= findViewById(R.id.group_item_filter_2);
        group_item_name= findViewById(R.id.group_item_name);
        group_item_icon= findViewById(R.id.group_item_icon);
        group_item_size= findViewById(R.id.group_item_size);
        group_item_show= findViewById(R.id.group_item_show);

        btn_del= findViewById(R.id.btn_del);
        btn_new= findViewById(R.id.btn_new);
        btn_save= findViewById(R.id.btn_save);
        group_item_add= findViewById(R.id.group_item_add);
        group_item_remove= findViewById(R.id.group_item_remove);
        btn_search = findViewById(R.id.btn_search);

        group_select.setAdapter(groupAdapter);

    }

    @Override
    public void InitData() {
        super.InitData();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        beverageGroupList = beverageFactoryDao.getBeverageGroup().queryall();
        Collections.sort(beverageGroupList);
        int pid =-1;
        String name ="";
        for (int i=0;i<beverageGroupList.size();i++)
        {
            if(pid == -1 && name.equals(""))
            {
                pid = beverageGroupList.get(i).getGroupid();
                name = beverageGroupList.get(i).getName();
                groupadapter.put(pid,name);
            }
            else
            {
                if(pid != beverageGroupList.get(i).getGroupid())
                {
                    pid = beverageGroupList.get(i).getGroupid();
                    name = beverageGroupList.get(i).getName();
                    groupadapter.put(pid,name);
                }
            }
        }
        groupAdapter = new GroupAdapter(this,groupadapter);
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        btn_back.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_new.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        group_item_add.setOnClickListener(this);
        group_item_remove.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        group_item_name.setOnClickListener(this);
        group_item_icon.setOnClickListener(this);
        group_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _groupid = groupAdapter.getgroupid(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_group_maker.this);
                break;
        }
    }
}
