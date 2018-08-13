package android.luna.Activity.ServiceUi.Setting.DrinkEditor.GroupEditor;

import android.content.Intent;
import android.database.DataSetObserver;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.tip.IngredientnameTip;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.BeverageItemSelectorAdpter;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.GroupAdapter;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.BeverageBasic;
import android.luna.Data.module.BeverageGroup;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.Tip.TipEditGroupText;
import android.luna.ViewUi.widget.*;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    private MaterialDialog progressDialog;
    private ListView group_item_source;
    private int _groupid =-1;
    private List<BeverageBasic> beverageBasics;
    private BeverageItemSelectorAdpter beverageItemSelectorAdpter;
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

        group_item_source = findViewById(R.id.group_item_source);
        group_item_source.setAdapter(beverageItemSelectorAdpter);

    }

    private void getbeveragebasicdataset()
    {
        beverageBasics = beverageFactoryDao.getBeverageBasicDao().queryall();
        if(beverageBasics!=null && beverageBasics.size()>0)
        {
            // TODO: 2018/8/13 delete the exists one
        }
        beverageItemSelectorAdpter.set_data(beverageBasics);
        beverageItemSelectorAdpter.notifyDataSetChanged();
    }
    private void getgroupdataset()
    {
        groupadapter.clear();
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
    }
    @Override
    public void InitData() {
        super.InitData();
        beverageFactoryDao = new BeverageFactoryDao(this,getApp());
        getgroupdataset();
       // getbeveragebasicdataset();
        groupAdapter = new GroupAdapter(this,groupadapter);
        beverageItemSelectorAdpter = new BeverageItemSelectorAdpter(this,beverageBasics);
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
                showTestToast("groupid ="+_groupid);
                if(_groupid!=-1)
                {
                    // TODO: 2018/8/13 update all the ui
                    BeverageGroup beverageGroup= beverageFactoryDao.getBeverageGroup().queryGroupItem(_groupid);
                    if(beverageGroup!=null)
                    {
                        group_item_name.setTextValue(beverageGroup.getName());
                        group_item_icon.setTextValue(beverageGroup.getIconpath());
                        group_item_size.setChecked(beverageGroup.getBigmode()==1);
                        group_item_show.setChecked(beverageGroup.getShowinscreen()==1);
                        getbeveragebasicdataset();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void CreatenewGroup()
    {
        BeverageGroup beverageGroup =new BeverageGroup(beverageFactoryDao.getBeverageGroup().creategroupid(),"New group");
        beverageFactoryDao.getBeverageGroup().create(beverageGroup);
        getgroupdataset();
        groupAdapter.notifyDataSetChanged();
        group_select.setSelection(0);
    }
    private void DeleteGroup()
    {
        beverageFactoryDao.getBeverageGroup().deletegroup(_groupid);
        getgroupdataset();
        groupAdapter.notifyDataSetChanged();
        group_select.setSelection(0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode == Constant.REQ_INPUT)
      {
          String name = data.getStringExtra("NAME");
          group_item_name.setTextValue(name);
          // TODO: 2018/8/13 update all record in group
      }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_back:
                AppManager.getAppManager().finishActivity(aty_group_maker.this);
                break;
            case R.id.btn_new:
                progressDialog = new MaterialDialog.Builder(aty_group_maker.this)
                    .title("Create new Group")
                    .content("Do you want to create a new group?")
                    .positiveText("Yes")
                    .positiveColor(getResources().getColor(R.color.green_grass))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            CreatenewGroup();
                            progressDialog.dismiss();
                        }
                    })
                    .negativeText("Later")
                    .negativeColor(getResources().getColor(R.color.red_wine))
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        }
                    })
                    .canceledOnTouchOutside(false)
                    .show();
                break;
            case R.id.btn_del:
                if(_groupid==-1)
                    break;
                progressDialog = new MaterialDialog.Builder(aty_group_maker.this)
                        .title("Delete Group")
                        .content("Do you want to Delete the group?")
                        .positiveText("Yes")
                        .positiveColor(getResources().getColor(R.color.green_grass))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                DeleteGroup();
                                progressDialog.dismiss();
                            }
                        })
                        .negativeText("Later")
                        .negativeColor(getResources().getColor(R.color.red_wine))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            }
                        })
                        .canceledOnTouchOutside(false)
                        .show();
                break;
            case R.id.group_item_name:
                if(_groupid==-1)
                    break;
                Intent intent = new Intent(this,TipEditGroupText.class);
                intent.putExtra("NAME",group_item_name.getTextValue());
                startActivityForResult(intent, Constant.REQ_INPUT);
                break;
        }
    }
}
