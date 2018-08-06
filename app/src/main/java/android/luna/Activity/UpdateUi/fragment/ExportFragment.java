package android.luna.Activity.UpdateUi.fragment;


import android.app.Fragment;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.DrinkName;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Lang.LangLocalHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import evo.luna.android.R;
import is.arontibo.library.ElasticDownloadView;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class ExportFragment extends Fragment  implements OnClickListener{
    private PopupWindow mPopWindow;
    private boolean showmPopWindow;
    private Button btn_export_setting,btn_export_log,btn_export_cancel,btn_export_start,btn_retry;
    private  ElasticDownloadView elastic_download_view;
    private TextView tv_update_content;
    private Spinner export_sp;
    private LinearLayout lyt_export;
    private BeverageFactoryDao beverageFactoryDao;
    private List<DrinkName> drinkNames;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);
        InitView(view);
        beverageFactoryDao = new BeverageFactoryDao(getActivity(),(CremApp) getActivity().getApplication());
        drinkNames =beverageFactoryDao.getBeverageNameDao().queryall();
        //LangLocalHelper.ExportLangfile(drinkNames);
        return view;
    }
    private void InitView(View view)
    {
        btn_export_setting = view.findViewById(R.id.btn_export_setting);
        btn_export_log = view.findViewById(R.id.btn_export_log);
        btn_export_setting.setOnClickListener(this);
        btn_export_log.setOnClickListener(this);
    }
    private void showPopupWindow(View parent) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_win_export, null);
            elastic_download_view = contentView.findViewById(R.id.elastic_download_view);
            tv_update_content = contentView.findViewById(R.id.tv_update_content);
            btn_retry= contentView.findViewById(R.id.btn_retry);
            btn_export_cancel = contentView.findViewById(R.id.btn_export_cancel);
            btn_export_start = contentView.findViewById(R.id.btn_export_start);
            lyt_export = contentView.findViewById(R.id.lyt_export);

            export_sp = contentView.findViewById(R.id.export_sp);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.export_folder));
            export_sp.setAdapter(adapter);
            btn_export_cancel.setOnClickListener(this);
            btn_export_start.setOnClickListener(this);
            btn_retry.setOnClickListener(this);

            elastic_download_view.startIntro();
            mPopWindow = new PopupWindow(contentView);
            mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopWindow.showAtLocation(parent, Gravity.CENTER,0,0);


            showmPopWindow = true;
        }
    }
    private void DismissPopWindow()
    {
        mPopWindow.dismiss();
        showmPopWindow =false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_export_setting:
                showPopupWindow(view);
                break;
            case R.id.btn_export_log:
                break;
            case R.id.btn_export_cancel:
                DismissPopWindow();
                break;
            case R.id.btn_export_start:
                lyt_export.setVisibility(View.INVISIBLE);
                elastic_download_view.setVisibility(View.VISIBLE);
                tv_update_content.setText("begin to export");
                if(export_sp.getSelectedItemPosition()<=4)
                    new ExportAsyncTask(export_sp.getSelectedItemPosition()).execute();
                else
                    new ExportNameAsyncTask().execute();
                break;
            case R.id.btn_retry:
                DismissPopWindow();
                break;
        }
    }
     class  ExportAsyncTask extends AsyncTask<String,Integer,String>
    {
        private FileHelper.myCallBack myCallback =new FileHelper.myCallBack()
        {
            @Override
            public void postprogress(int value) {
                publishProgress(value);
            }
        };
        private int ConfigIndex=0;
        private  float m_totalecount;
        public ExportAsyncTask(int i) {
            ConfigIndex =i;
        }
        @Override
        protected String doInBackground(String... strings) {
            FileHelper.mfilescount =0;
            FileHelper.copyFile("/data/data/evo.luna.android/databases/cqube.db","/storage/udisk/mytest/cqube.db");
            m_totalecount = FileHelper.CountFile(new File("mnt/sdcard/crem/"));
            if(FileHelper.copyFolder("mnt/sdcard/crem/","/storage/udisk/mytest/",myCallback)) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "ok";
            }
            else
                return "failed";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            elastic_download_view.setProgress(((float)values[0]/m_totalecount)*100);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("ok"))
            {
                elastic_download_view.success();
                btn_retry.setText("Close");
                btn_retry.setVisibility(View.VISIBLE);
                tv_update_content.setText(((int)m_totalecount)+" files exported!");
            }
            else
            {
                elastic_download_view.fail();
                btn_retry.setText("Close");
                btn_retry.setVisibility(View.VISIBLE);
            }
        }
    }

     class  ExportNameAsyncTask extends AsyncTask<String,Integer,String>
     {

         private  int m_totalecount=drinkNames.size();
         @Override
         protected String doInBackground(String... params) {
             publishProgress((m_totalecount-1));
             if (LangLocalHelper.ExportLangfile(drinkNames,((CremApp)getActivity().getApplication()).getUsbpath()))
             {
                 publishProgress(m_totalecount);
                 return "ok";
             }
             return "failed";
         }
         @Override
         protected void onProgressUpdate(Integer... values) {
             super.onProgressUpdate(values);
             elastic_download_view.setProgress(((float)values[0]/m_totalecount)*100);
         }
         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             if(s.equalsIgnoreCase("ok"))
             {
                 elastic_download_view.success();
                 btn_retry.setText("Close");
                 btn_retry.setVisibility(View.VISIBLE);
                 tv_update_content.setText(((int)m_totalecount)+" names exported!");
             }
             else
             {
                 elastic_download_view.fail();
                 btn_retry.setText("Close");
                 btn_retry.setVisibility(View.VISIBLE);
             }
         }
     }
}
