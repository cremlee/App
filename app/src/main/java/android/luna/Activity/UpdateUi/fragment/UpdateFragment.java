package android.luna.Activity.UpdateUi.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.CustomerUI.Normal.aty_customer_ui_3;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.FileHelper;
import android.luna.Utils.InstallPkg;
import android.luna.rs232.Cmd.CmdUpdate;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import evo.luna.android.R;
import is.arontibo.library.ElasticDownloadView;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class UpdateFragment extends Fragment implements View.OnClickListener {
    private PopupWindow mPopWindow;
    private boolean showmPopWindow;
    private Button sf_update1,btn_retry,sf_update;
    private ElasticDownloadView elastic_download_view;
    private TextView tv_update_content;
    private TextView ver_old ,ver_new;
    private CremApp app;
    private String apkPath ="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        app = (CremApp)getActivity().getApplication();
        InitView(view);
        return view;
    }
    private void InitView(View view)
    {
        sf_update = view.findViewById(R.id.sf_update);
        sf_update.setOnClickListener(this);
        sf_update1 = view.findViewById(R.id.sf_update);
        sf_update1.setOnClickListener(this);
        ver_old =view.findViewById(R.id.ver_old);
        ver_new =view.findViewById(R.id.ver_new);
        try {
            ver_old.setText(getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(),0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        apkPath = app.getUsbpath()+ FileHelper.PATH_USB+"update.apk";
        ver_new.setText(getApkInfo(apkPath));
    }
    private void showPopupWindow(View parent) {
        if (showmPopWindow) {
            DismissPopWindow();
        } else {
            View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_win_update, null);
            elastic_download_view = contentView.findViewById(R.id.elastic_download_view);
            tv_update_content = contentView.findViewById(R.id.tv_update_content);
            btn_retry= contentView.findViewById(R.id.btn_retry);
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
            if(id == R.id.sf_update1)
            {
                showPopupWindow(view);
                new UpdateFirmwareAsyncTask().execute();
            }
            else if(id==R.id.btn_retry)
            {
                btn_retry.setVisibility(View.INVISIBLE);
                showPopupWindow(view);
            }
            else if(id == R.id.sf_update)
            {
                /*Uri uri = Uri.fromFile(new File(apkPath));
                Intent localIntent = new Intent(Intent.ACTION_VIEW);
                localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
                startActivity(localIntent);*/
                new Thread(new updatesw()).start();
            }

    }




    private int TotalPkgAmount =1;
    private int crtpkgno =0;
    private CmdUpdate _CmdUpdate;
    private Map<Integer, String> cmdmap = new HashMap<>();
    private boolean InitCmdmap(String Path)
    {
       // tv_update_content.setText("start load firmware");
        File fs = new File(Path);
        if (!fs.exists()) {
            return false;
        }
        cmdmap.clear();
        int packagenum = 0;
        boolean isLastPkg = false;
        if (fs.length()>= 1024)
        {
            packagenum = (int)(fs.length() / 1024);
        }
        if (fs.length() % 1024 != 0)
        {
            isLastPkg = true;
        }
        if (isLastPkg)
        {
            TotalPkgAmount = packagenum+1;
        }
        else
        {
            TotalPkgAmount = packagenum;
        }
        InputStream inStream =null;
        try {
            inStream = new FileInputStream(fs);
            int readlen =-1;
            byte[] buffer = new byte[1024];
            for (int cnt = 0; cnt < packagenum; cnt++)
            {
                readlen = inStream.read(buffer, 0, 1024);
                if(readlen == 1024)
                {
                    cmdmap.put(cnt, AndroidUtils_Ext.bytes2String(buffer));
                   // publishProgress(new Integer[]{2,cnt+1,TotalPkgAmount});
                }else
                {
                    return false;
                }
            }
            if(isLastPkg)
            {
                int lastlen = (int)(fs.length()-packagenum*1024);
                byte[] lastbuffer = new byte[lastlen];
                readlen = inStream.read(lastbuffer, 0, lastlen);
                if(readlen == lastlen)
                {
                    cmdmap.put(packagenum, AndroidUtils_Ext.bytes2String(lastbuffer));
                   // publishProgress(new Integer[]{2,TotalPkgAmount,TotalPkgAmount});
                }else
                {
                    return false;
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            if(inStream!=null)
            {
                try {
                    inStream.close();
                    inStream =null;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
       // tv_update_content.setText("start update firmware");
        return true;

    }


    class  UpdateFirmwareAsyncTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            if(!InitCmdmap("/mnt/sdcard/md/firmware.bin"))
            {
                return "failed";
            }
            else {
                int i = 0;
                while (i++ <= 99) {
                    publishProgress(new Integer[]{i});
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return "ok";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(elastic_download_view!=null)
            {
                elastic_download_view.setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("failed"))
            {
                elastic_download_view.fail();
                btn_retry.setVisibility(View.VISIBLE);
            }
            else
            {
                elastic_download_view.success();
                tv_update_content.setText("Restart the machine!");
            }
        }
    }

    public  String getApkInfo(String filePath)
    {
        PackageManager packageManager = getActivity().getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if(packageInfo!=null)
        {
            return  packageInfo.versionName;
        }
        return "unknown";
    }

    private class updatesw implements Runnable {


        @Override
        public void run() {
            Uri uri = Uri.fromFile(new File(apkPath));
            Intent localIntent = new Intent(Intent.ACTION_VIEW);
            localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.setDataAndType(uri, "application/vnd.android.package-archive");
            startActivity(localIntent);
        }
    }
}
