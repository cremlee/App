package android.luna.Activity.ServiceUi.Setting.DrinkEditor.NameEditor.fragment;

import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.CleanFragment;
import android.luna.Activity.UpdateUi.fragment.ExportFragment;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.DrinkName;
import android.luna.Utils.Lang.LangLocalHelper;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.AsyncTask;
import android.view.View;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/26.
 */

public class NameExportFragment extends CleanFragment implements View.OnClickListener{
    private MaterialDialog progressDialog;
    private boolean isUsbReady =false;
    private String usbpath="";
    private BeverageFactoryDao beverageFactoryDao;
    @Override
    public void InitView(View view) {
        super.InitView(view);
        beverageFactoryDao = new BeverageFactoryDao(getActivity(),(CremApp) getActivity().getApplication());
        drinkNames =beverageFactoryDao.getBeverageNameDao().queryall();
        setTitle("Export Name");
        setIcon(0);
        setContent("");
        getStart().setOnClickListener(this);
        if(!isUsbReady)
            getStart().setEnabled(false);
    }

    public void setUsbReady(boolean a)
    {

        isUsbReady =a;
    }
    public void  enableUSB(String usbpath)
    {
        enableStart();
        isUsbReady =true;
        this.usbpath = usbpath;
    }

    public void  disableUSB()
    {
        disableStart();
        isUsbReady =false;
        this.usbpath = "";
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(progressDialog!=null)
            progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.start:
                // TODO: 2018/6/1  export the name button
                progressDialog = new MaterialDialog.Builder(getActivity())
                        .title("Expor Name")
                        .canceledOnTouchOutside(true)
                        .content("waiting...")
                        .progress(true, 0)
                        .show();
                new ExportNameAsyncTask().execute();
                break;
        }
    }
    private List<DrinkName> drinkNames;
    class  ExportNameAsyncTask extends AsyncTask<String,Integer,String>
    {

        private  int m_totalecount=drinkNames.size();
        @Override
        protected String doInBackground(String... params) {
            publishProgress((m_totalecount-1));
            if (LangLocalHelper.ExportLangfile(drinkNames,usbpath))
            {
                publishProgress(m_totalecount);
                return "ok";
            }
            return "failed";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("ok"))
            {
                if(progressDialog!=null)
                    progressDialog.dismiss();
            }
        }
    }
}
