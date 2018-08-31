package android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.PicSelector;

import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.DrinkResAdapter;
import android.luna.Utils.FileHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/24.
 */

public class aty_uiRes_selector extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {
    private GridView gv_res;
    private TextView title;
    private String path="";
    private String folder ="";
    private DrinkResAdapter drinkResAdapter=null;
    private int reqCode;
    private List<DrinkRes> drinkResList =new ArrayList<>(30);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getIntent().getStringExtra("path");
        folder = getIntent().getStringExtra("folder");
        reqCode = getIntent().getIntExtra("reqCode",0);
        InitAdpterRes();
    }
    private void InitAdpterRes()
    {
        drinkResList.clear();
        DrinkRes tmp;
        if(folder!=null && folder!="") {
         List<String> files= FileHelper.getAllFile(new File(folder));
            if(files!=null && files.size()>0)
            {
                for (String item :files)
                {
                    tmp = new DrinkRes(item);
                    if(item.equalsIgnoreCase(path))
                    {
                        tmp.setSelected(true);
                    }
                    drinkResList.add(tmp);
                }
            }
        }
        drinkResAdapter =new DrinkResAdapter(this,drinkResList);
        gv_res.setAdapter(drinkResAdapter);
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
        setContentView(R.layout.aty_res_selector);
        gv_res = findViewById(R.id.gv_res);
        title = findViewById(R.id.title);
    }

    @Override
    public void InitData() {
        super.InitData();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
        title.setOnClickListener(this);
        gv_res.setClickable(true);
        gv_res.setOnItemClickListener(this);
        gv_res.setOnItemLongClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.title)
        {
          Intent intent = new Intent();
          intent.putExtra("newpath", drinkResAdapter.getSelectPath());
           if(reqCode!=0)
               setResult(reqCode, intent);
          AppManager.getAppManager().finishActivity(aty_uiRes_selector.this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        drinkResAdapter.setpicSelectStatus(i);
        drinkResAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        drinkResAdapter.setIshowdelete();
        drinkResAdapter.notifyDataSetChanged();
        return true;
    }
}
