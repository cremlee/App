package android.luna.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.luna.Activity.Base.CremApp;

import android.luna.BlueCom.BlueActionDefine;
import android.luna.BlueCom.BlueCmdDefine;
import android.luna.BlueCom.Response.BaseRespose;
import android.luna.BlueCom.cmd.CmdOffLine;
import android.luna.Utils.AndroidUtils_Ext;

import android.luna.Utils.Logger.EvoTrace;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.cqube.communication.BluePortManager;
import com.cqube.communication.ICommunication;

public class BlueComService extends Service implements ICommunication {
    private StringBuffer stringBuffer = new StringBuffer();
    private int ackDataLength = 0; // 应答数据长度
    private int headPos;
    private int endPos;
    private int curLength; // 当前接收数据长度
    private CremApp app;
    private boolean isgetMac =false;
    public BlueComService() {
    }

    public class MyBinder extends Binder {

        public BlueComService getService() {
            return BlueComService.this;
        }
    }

    private MyBinder myBinder = new MyBinder();

    @Override
    public IBinder onBind(Intent arg0) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = (CremApp) getApplication();
        IntentFilter filter = new IntentFilter();
        filter.addAction(BlueActionDefine.ACTION_DATA_TO_GAME);
        registerReceiver(receiver, filter);
        BluePortManager.getInstance(BlueComService.this);
        new Thread(new CmdThread()).start();
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BlueActionDefine.ACTION_DATA_TO_GAME.equals(action)) {
                try {
                    String editData = (String)intent.getExtras().get("cmddata");
                    byte[] bytes = editData.getBytes();
                    BluePortManager.getInstance(BlueComService.this).write(bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void sleep(long tm) {
        try {
            Thread.sleep(tm);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class CmdThread implements Runnable {
        @Override
        public void run() {
            isgetMac = true;
            BluePortManager.getInstance(BlueComService.this).write("AT#M".getBytes());
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        EvoTrace.e("blue","BlueComService onTaskRemoved");
    }

    @Override
    public void onDestroy() {
        //EvoTrace.e("blue","BlueComService onDestroy");
        //BluePortManager.getInstance(BlueComService.this).write((new CmdOffLine()).toCmd().getBytes());
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    private boolean checkdatesum(StringBuffer a) {
        return true;
    }

    private String str2mac(String a)
    {
        String ret ="";
        if(a.length()==12)
        {
            ret = a.substring(0,2)+":"+a.substring(2,4)+":"
                    +a.substring(4,6)+":"+a.substring(6,8)+":"
                    +a.substring(8,10)+":"+a.substring(10,12);
        }
        return  ret;
    }
    @Override
    public void onDataReceived(byte[] buffer, int size) {
        String parsing;
        if(isgetMac) {
            parsing = AndroidUtils_Ext.parsing(buffer, size);
            stringBuffer.append(parsing);
            EvoTrace.e("blue","onDataReceived="+parsing);
            if(stringBuffer.toString().startsWith("415423234d") && stringBuffer.toString().length()>=22) //AT##M
            {
                String mac = stringBuffer.toString();
                String macadress =  str2mac(mac.substring(10,mac.length()));
                EvoTrace.e("blue","MAC2="+macadress);
                app.setBlue_mac(macadress);
                isgetMac =false;
                stringBuffer.setLength(0);
            }
        }
        else {
             parsing = new String(buffer);
            stringBuffer.append(parsing);
            curLength = stringBuffer.length();
            if (ackDataLength == 0) {
                headPos = stringBuffer.indexOf("stx#");
                if (headPos >= 0 && curLength >= headPos + 8) {
                    String strLength = stringBuffer.substring(headPos + 4, headPos + 8);
                    ackDataLength = Integer.valueOf(strLength) + 8;
                }
                if (endPos > 0 && headPos > 0 && endPos > headPos) {

                }
            }
            endPos = stringBuffer.indexOf("endl;");
            EvoTrace.e("blue", "curLength=>" + curLength);
            EvoTrace.e("blue", "ackDataLength=>" + ackDataLength);
            EvoTrace.e("blue", "headPos=>" + headPos);
            if ((curLength == ackDataLength + headPos)) {
                if (checkdatesum(stringBuffer)) {
                    int type = ackOperate(stringBuffer.substring(headPos));
                    stringBuffer.setLength(0);
                    curLength = 0;
                    ackDataLength = 0;
                    headPos = -1;
                    endPos = -1;
                }
            } else if ((curLength > (ackDataLength + headPos)) && headPos >= 0 && endPos > 0) {
                stringBuffer.setLength(0);
                curLength = 0;
                ackDataLength = 0;
                headPos = -1;
                endPos = -1;
            }
        }
    }
    private BaseRespose baseresponse;
    private int ackOperate(String cmd)
    {
        Intent actIntent;
        EvoTrace.e("blue","ackOperate=>"+cmd);
        baseresponse = new  BaseRespose().built(cmd);
        switch (baseresponse.getCmd())
        {
            case BlueCmdDefine.CMD_ECO:  //eco cmd
                 actIntent = new Intent(BlueActionDefine.ACTION_TELNET_CMD_ECO);
                actIntent.putExtra("bluedata",baseresponse.getdatas()[0]);
                sendBroadcast(actIntent);
                break;
            case BlueCmdDefine.CMD_QUERY_BEVERAGE:
                 actIntent = new Intent(BlueActionDefine.ACTION_TELNET_CMD_QUERY_BEVERAGE);
                 sendBroadcast(actIntent);
                break;
            case BlueCmdDefine.CMD_SET_THEME:
                actIntent = new Intent(BlueActionDefine.ACTION_TELNET_CMD_SET_THEME);
                actIntent.putExtra("bluedata",baseresponse.getdatas()[0]);
                sendBroadcast(actIntent);
                break;
            case BlueCmdDefine.CMD_MAKE_BEVERAGE:
                actIntent = new Intent(BlueActionDefine.ACTION_TELNET_CMD_MAKE_DRINK);
                actIntent.putExtra("bluedata",baseresponse.getdatas()[0]);
                sendBroadcast(actIntent);
                break;
        }
        return 0;
    }
}
