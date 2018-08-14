package android.luna.Activity.ServiceUi.Setting.DrinkEditor.NameEditor.fragment;

import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ServiceUi.Setting.Clean.fragment.CleanFragment;
import android.luna.Activity.UpdateUi.fragment.ImportFragment;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.module.DrinkName;
import android.luna.Utils.FileHelper;
import android.luna.Utils.Lang.LangLocalHelper;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.os.AsyncTask;
import android.view.View;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/6/26.
 */

public class NameImportFragment extends CleanFragment implements View.OnClickListener{
    private MaterialDialog progressDialog;
    private boolean isUsbReady =false;
    private String usbpath="";
    private BeverageFactoryDao beverageFactoryDao;
    @Override
    public void InitView(View view) {
        super.InitView(view);
        beverageFactoryDao = new BeverageFactoryDao(getActivity(),(CremApp) getActivity().getApplication());
        setTitle("Import Name");
        setIcon(0);
        setContent("Please insert the USB stick,then press the start button");
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
                        .title("Import Name")
                        .canceledOnTouchOutside(true)
                        .content("waiting...")
                        .progress(true, 0)
                        .show();
                String srcPath =  this.usbpath+ FileHelper.PATH_LANG + "/drinkname.lang";
                if(!FileHelper.IsValidDB(srcPath))
                {
                    ((BaseActivity)getActivity()).showToast("No Language file exist!");
                    break;
                }
                String[] exportParams = new String[] { srcPath};
                new updateLanguageAsyncTask().execute(exportParams);
                break;
        }
    }
    class updateLanguageAsyncTask extends AsyncTask<String,Integer,String>
    {
        private List<DrinkName> drinkNames =null;
        private int totalsize =0;
        @Override
        protected String doInBackground(String... params) {
            String respath = params[0];
            if(!FileHelper.IsValidDB(respath))
            {
                return "failed";
            }
            drinkNames = LangLocalHelper.getAllDrinkfromjson(respath);
            if(drinkNames!= null && drinkNames.size()>0)
            {
                totalsize = drinkNames.size();
                syncDrinkName();
                return "ok";
            }
            return "failed";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progressDialog!=null)
                progressDialog.dismiss();
            if(s.equalsIgnoreCase("ok"))
            {
                ((BaseActivity)getActivity()).showToast("Import ok");
                ((BaseActivity)getActivity()).getApp().setIsmainpagereload(true);
            }
            else
            {
                ((BaseActivity)getActivity()).showToast("Import failed!");
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //elastic_download_view.setProgress(values[0].floatValue()/totalsize);
        }
        private void syncDrinkName()
        {
            int i =0;
            beverageFactoryDao.getBeverageNameDao().clear();
            for (DrinkName item:drinkNames)
            {
                i++;
                beverageFactoryDao.getBeverageNameDao().create(item);
                publishProgress(i);
            }
        }
    }
}
