package android.luna.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Activity.ProductLineUi.aty_production_main;
import android.luna.Activity.ServiceUi.aty_service_main;
import android.luna.Activity.UpdateUi.aty_update_main;
import android.luna.BlueCom.BlueActionDefine;
import android.luna.Data.Warning.MachineWarn;
import android.luna.Utils.AndroidUtils_Ext;

import android.luna.Utils.Logger.EvoTrace;
import android.luna.rs232.Ack.AckCalibrationFinish;
import android.luna.rs232.Ack.AckCalibrationRequest;
import android.luna.rs232.Ack.AckCleanFinish;
import android.luna.rs232.Ack.AckCleanRequest;
import android.luna.rs232.Ack.AckConfigFinish;
import android.luna.rs232.Ack.AckDeviceDB;
import android.luna.rs232.Ack.AckDeviceDBSet;
import android.luna.rs232.Ack.AckDrinkFinish;
import android.luna.rs232.Ack.AckMachineConfig;
import android.luna.rs232.Ack.AckMakeBeverage;
import android.luna.rs232.Ack.AckMakeDrink;
import android.luna.rs232.Ack.AckMakeIngredient;
import android.luna.rs232.Ack.AckQuery;
import android.luna.rs232.Ack.AckTest;
import android.luna.rs232.Ack.DataStruct.DeviceDBItem;
import android.luna.rs232.Ack.DataStruct.DeviceErrorItem;
import android.luna.rs232.Ack.DataStruct.DeviceParamItem;
import android.luna.rs232.Ack.DataStruct.ErrorItem;
import android.luna.rs232.Cmd.CmdCalibration;
import android.luna.rs232.Cmd.CmdCleanFinish;
import android.luna.rs232.Cmd.CmdConfigFinish;
import android.luna.rs232.Cmd.CmdDrinkFinish;
import android.luna.rs232.Cmd.CmdStateQuery;
import android.luna.rs232.Cmd.CmdTestFinish;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.cqube.communication.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.luna.Activity.Base.Constant.*;

public class ComService extends Service implements ICommunication {

    private StringBuffer stringBuffer = new StringBuffer();
    private int ackDataLength = 0; // 应答数据长度
    private int headPos;
    private int curLength; // 当前接收数据长度
    private int WAITCOUNT = 300; // 重发次数
    private int waitCount; // 等待次数
    private int loopCount;
    private CremApp app;
    private final static int port_idel = 0;
    private final static int port_sendst = 1;
    private final static int port_sendcmd = 2;
    private final static int port_recivedst = 3;
    private final static int port_recivedcmd = 4;
    private final static int port_reset = 5;
    private int comunicationcnt=0;
    private int portStatus = 0;

    private int heartpkgcount =0;
    private String[] cmdlst;
    private String curCmd = "";
    private CmdStateQuery Quercmd;

    private boolean isstop =false;
    private WarningProcess warningProcess=null;

    public ComService() {
    }
    public class MyBinder extends Binder {

        public ComService getService() {
            return ComService.this;
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
        /********************test start********************/
        /********************test end********************/
        app = (CremApp) getApplication();
        Quercmd = new CmdStateQuery();
        ackQuery = app.getAckQueryInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_EJECT);
        filter.addDataScheme("file");
        registerReceiver(receiver, filter);
        SerialPortManager.getInstance(ComService.this);
        new Thread(new CmdThread()).start();
        warningProcess = new WarningProcess(this);
        warningProcess.start();
    }

    private int devicePid() {
        int pid = 0;

        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        if (manager != null) {
            Map<String, UsbDevice> usbDevices = manager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = usbDevices.values().iterator();
            while (deviceIterator.hasNext()) {
                UsbDevice device = deviceIterator.next();
                pid = device.getProductId();
            }
        }
        return pid;
    }

    /**
     * 解析USB文件
     *
     * @param file
     * @throws Exception
     */
    private void usbKeyFile(String path, File file, int devicePid) throws Exception {
        Intent intent1;
        // 读取原始数据为
        String data = AndroidUtils_Ext.bufferedReader(file.getPath());
        EvoTrace.e("usb", "data:" + data);
        // 前20个字节不用解密
        String data1 = data.substring(20, data.length());
        EvoTrace.e("usb", "data1:" + data1);
        // 解密后的数据为
        String decrypt = AndroidUtils_Ext.decrypt(data1, "Crem1234");
        EvoTrace.e("usb", "decrypt:" + decrypt);
        String[] strings = decrypt.split(" ");
        String strPid = decrypt.substring(6, 17).replace(" ", "");
        int filePid = Integer.valueOf(strPid, 16);
        if (filePid != devicePid) {
            Toast.makeText(this, "Not authorized key.", Toast.LENGTH_LONG).show();
            return;
        }
        int userMode = Integer.valueOf(strings[10],16);
        /*app.setUserMode(userMode);
        // operator id
        String strOperatorId = decrypt.substring(34, 45).replace(" ", "");
        int iOperatorId = Integer.valueOf(strOperatorId, 16);
        app.setUserid(iOperatorId);
        EvoTrace.e(TAG, "strOperatorId:" + iOperatorId);
        LogHelper.WriteLogInfo(LogHelper.LOG_TYPE_SERVICE, String.format("ID:%d Login", iOperatorId));
        _LoghelperDao.SetLog(new LogRecord(LoghelperDao.COLOR_SERVICE,String.format("ID:%d Login", iOperatorId),null));
        PinSetting pinSetting = factoryDao.createPinSetting().query();
        if(pinSetting ==null)
        {
            pinSetting = new PinSetting();
        }
        LogHelper.WriteLogInfo(LogHelper.LOG_TYPE_SERVICE, String.format("Insert Key:Id=[%d];mode=[%d]", iOperatorId,userMode));
        _LoghelperDao.SetLog(new LogRecord(LoghelperDao.COLOR_SERVICE,String.format("Insert Key:Id=[%d];mode=[%d]", iOperatorId,userMode),null));
       */ switch (userMode) {
            case Constant.MODE_SERVICE:
                 intent1 = new Intent(ComService.this, aty_service_main.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("path", path);
                startActivity(intent1);
                break;
            case Constant.MODE_OPERATE:
                break;
            case Constant.MODE_UPDATE:
                intent1 = new Intent(ComService.this, aty_update_main.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("path", path);
                startActivity(intent1);
                break;
            case Constant.MODE_MANUFACTORY:
                intent1 = new Intent(ComService.this, aty_production_main.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent1.putExtra("path", path);
                startActivity(intent1);
                break;
            default:
                break;
        }

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_MEDIA_MOUNTED.equals(action)) {
                Intent mIntent = new Intent(BlueActionDefine.ACTION_USB_INSERT);
                String path = intent.getData().getPath();
                mIntent.putExtra("PATH",path);
                app.setUsbpath(path);
                sendBroadcast(mIntent);
                // 查找SCR.TOK文件
                int pid = devicePid();
                File file = new File(path + Constant.TOK);
                if (file.exists()) {
                    try {
                        usbKeyFile(path, file, pid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (Intent.ACTION_MEDIA_EJECT.equals(action)) {

                Intent mIntent = new Intent(BlueActionDefine.ACTION_USB_REMOVE);
                sendBroadcast(mIntent);
                app.setUsbpath("");
               /* PinSetting pinSetting = factoryDao.createPinSetting().query();
                if(pinSetting ==null)
                {
                    pinSetting = new PinSetting();
                }
                if(app.isIsServiceKeyIn())
                {
                    app.setIsServiceKeyIn(false);

                    if(pinSetting.getUseServicePin() != 1)
                    {
                        BaseActivity baseActivity =AppManager.getAppManager().currentActivity();
                        if(baseActivity!=null)
                        {
                            if(baseActivity.getClass().getSimpleName().startsWith("TipusbinsertActivity"))
                            {
                                AppManager.getAppManager().finishActivity(TipusbinsertActivity.class);
                            }
                        }
                        Intent intent1 = new Intent(EvoService.this, SettingsActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        app.setIsServiceKeyIn(false);
                    }
                }*/
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
            int resendCount = 0; // 重发次数
            final int RESEND_COUNT = 3;
            while (!isstop) {
                sleep(100);
                switch (portStatus) {
                    case port_idel:
                        if (app.isStopHandleWithBoard())
                            continue;
                        synchronized (stringBuffer) {
                            stringBuffer.setLength(0);
                            ackDataLength = 0;
                            headPos = -1;
                        }
                        if (app.getCmdQueue().size() > 0) {
                            cmdlst=app.getCmdQueue().peek().split(":");
                            if(cmdlst.length ==2 )
                            {
                                curCmd = cmdlst[0];
                                WAITCOUNT = Integer.valueOf(cmdlst[1]);
                            }
                            portStatus = port_sendcmd;
                            resendCount = 0;
                            waitCount=0;
                            EvoTrace.e("ack","send cmd >>"+curCmd);
                            SerialPortManager.getInstance(ComService.this).sendCmd(curCmd);
                        } else {
                            loopCount++;
                            if (loopCount >= 5) {
                                loopCount = 0;
                                portStatus = port_sendst;
                                curCmd =Quercmd.buildCmd();
                                curCmd = curCmd.split(":")[0];
                                SerialPortManager.getInstance(ComService.this).sendCmd(curCmd);
                                WAITCOUNT =50;
                            }
                        }
                        waitCount = 0;
                        break;
                    case port_sendst:
                        waitCount++;
                        if (waitCount >= WAITCOUNT) {
                            portStatus = port_idel;
                            heartpkgcount =0;
                            waitCount=0;
                        }
                        break;
                    case port_sendcmd:
                        waitCount++;
                        if (waitCount >= WAITCOUNT) {
                            if (resendCount < RESEND_COUNT) {
                                EvoTrace.e("ack","resend cmd >>"+curCmd);
                                SerialPortManager.getInstance(ComService.this).sendCmd(curCmd);
                                resendCount++;
                                waitCount=0;
                            } else {
                                if (app.getCmdQueue().size() > 0) {
                                    EvoTrace.e("ack","ACTION_CMD_RSP_TIME_OUT cmd >>"+curCmd);
                                    //modify by lee:连续3次如果都超时的话就抱通讯故障,直接block 界面.
                                    waitCount=0;
                                    app.getCmdQueue().clear();
                                    Intent intent = new Intent(Constant.ACTION_CMD_RSP_TIME_OUT);
                                    sendBroadcast(intent);
                                }

                                portStatus = port_idel;
                            }
                        }
                        break;
                    case port_recivedst:
                        portStatus = port_idel;
                        heartpkgcount++;
                        break;
                    case port_recivedcmd:
                        if (app.getCmdQueue().size() > 0) {
                            EvoTrace.e("ack","port_recivedcmd cmd >>"+curCmd);
                            app.getCmdQueue().remove();
                        }
                        portStatus = port_idel;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isstop =true;
        if(warningProcess!=null)
        {
            warningProcess.free();
        }
        unregisterReceiver(receiver);
    }

    @Override
    public void onDataReceived(byte[] buffer, int size) {
        String parsing = AndroidUtils_Ext.parsing(buffer, size);
        stringBuffer.append(parsing);
        curLength = stringBuffer.length();
        // 确定数据长度
        if (ackDataLength == 0) {
            headPos = stringBuffer.indexOf("fefc");
            if (headPos >= 0 && curLength >= headPos + 8) {
                String strLength = stringBuffer.substring(headPos + 4, headPos + 8);
                ackDataLength = Integer.valueOf(strLength, 16) * 2 + 8;
            }
        }
        // 确定当前的数据长度是否和应答数据长度一致
        if ((curLength == ackDataLength + headPos)) {
            if (checkdatesum() && checksn()) {
                if (!app.isStopHandleWithBoard()) {
                    if(comunicationcnt++==10 )
                    {
                       app.setComWithBoard(true);
                    }
                    int type = ackOperate(stringBuffer.substring(headPos));
                    stringBuffer.setLength(0);
                    curLength = 0;
                    ackDataLength = 0;
                    headPos = -1;
                    //// TODO: 2017/12/14  panduan shi fou bao po sun ,
                    ////yes & cmd : then resend it
                    //// !cmd: then do nothing
                    // 数据接收完成，更改状态
                    if (type != Constant.ACK_STATE_QUERY && type != ACK_PAY_HANDLE) {
                        EvoTrace.e("ack","receive cmd type >> "+type);
                        portStatus = port_recivedcmd;
                    } else if(type == Constant.ACK_STATE_QUERY) {
                        portStatus = port_recivedst;
                    }
                } else {
                    stringBuffer.setLength(0);
                    curLength = 0;
                    ackDataLength = 0;
                    headPos = -1;
                    portStatus = port_idel;
                    // 数据接收完成，更改状态
                }
            }
        }
    }
    private int m_typeIndex =5;
    private  String[] ackCmd(String ack) {
        StringBuffer buffer = new StringBuffer();
        int length = ack.length() / 2;
        for (int i = 0; i < length; i++) {
            buffer.append(ack.substring(i * 2, i * 2 + 2)).append(" ");
        }
        // 获取应答类型
        return buffer.toString().split(" ");
    }
    private String TAG_ACK ="ack";
    private AckQuery ackQuery;
    private int ackOperate(String ack) {
        EvoTrace.e("ack","[response]:  "+ack);
        Intent boadcast=null;
        //sendBroadcast(mIntent);
        String[] acks = ackCmd(ack);
        int ackType = (Integer.valueOf(acks[m_typeIndex], 16)<<8) +Integer.valueOf(acks[m_typeIndex+1], 16);
        switch (ackType)
        {
            case ACK_HARDWARE_TEST:
                dealwithtest(acks,boadcast);
                break;
            //// TODO: 2018/2/5 zhuangtai chaxun xiangguan
            case ACK_STATE_QUERY:
                try {
                    dealwithAckquery(acks);
                }catch (Exception e)
                {
                    EvoTrace.e("Exception","dealwithAckquery:"+e.toString());
                }
                break;
            case ACK_MACHINE_CONFIG_REQUEST:
               dealwithAckmachineconfig(acks,boadcast);
                break;
            //// TODO: 2018/2/5 payment xiang guan
            case ACK_PAY_QUERY:
                Log.i(TAG_ACK, "get ACK_PAY_QUERY");
                break;
            case ACK_PAY_CHARGE:
                Log.i(TAG_ACK, "get ACK_PAY_CHARGE");
                break;
            case ACK_PAY_HANDLE:
                Log.i(TAG_ACK, "get ACK_PAY_HANDLE");
                break;
            case ACK_PAY_REQUEST:
                Log.i(TAG_ACK, "get ACK_PAY_REQUEST");
                break;
            //// TODO: 2018/2/5  Clean xiangguan
            case ACK_CLEAN_MACHINE_REQUEST:
                dealwithAckCleanRequest(acks,boadcast);
                break;
            case ACK_STOP_CLEAN_MACHINE: // 机器清洗停止操作应答

                break;
            case ACK_CLEAN_MACHINE_FINISH: // 机器清洗操作应答
                dealwithAckCleanFinish(acks,boadcast);
                break;
            //// TODO: 2018/2/5  Make drink xiangguan
            case ACK_MAKE_DRINK_REQUEST:
                EvoTrace.e(TAG_ACK, "ACK_MAKE_DRINK_REQUEST");
                break;
            case ACK_MAKE_DRINK:
                dealwithmakeDrink(acks,boadcast);
                break;
            case ACK_MAKE_DRINK_FINISH:
                dealwithAckDrinkFinish(acks,boadcast);
                break;
            //// TODO: 2018/2/5 make beverage xiangguan
            case ACK_MAKE_INGREDIENT:
                dealwithmakeIngredient(acks,boadcast);
                break;
            case ACK_MAKE_BEVERAGE:
                dealwithmakeBeverage(acks,boadcast);
                break;
            case ACK_MACHINE_DB_SETTING:
                dealwithAckDeviceDBSet(acks,boadcast);
                break;
            //// TODO: 2018/2/5 jiqi shezhi
            case ACK_DB_GETTING:
                dealwithAckDeviceDBGet(acks,boadcast);
                break;
            //// TODO: 2018/2/5 shuju chuanshu xiangguan
            case ACK_DATA_TRANSFER:
                EvoTrace.e(TAG_ACK, "ACK_DATA_TRANSFER");
                break;
            // TODO: 2018/7/27 jiaoyan
            case  ACK_CALIBRATION_REQUEST:
                dealwithcalibration(acks,boadcast);
                break;
            //// TODO: 2018/2/5 jiaoyan xiangguan
            case ACK_CALIBRATION_FINISH:
                dealwithAckCalibrationFinish(acks,boadcast);
                break;
            case ACK_CONFIG_FINISH:
                dealwithAckConfigFinish(acks,boadcast);
                break;
            //// TODO: 2018/2/5 chuwubao xiangguan
            case ACK_PACKAGE_CORRUPT:
                EvoTrace.e(TAG_ACK, "ACK_PACKAGE_CORRUPT");
                break;
            default:
                break;
        }
        return ackType;
    }

    private void blockprocess()
    {
        Class<?> clazz = AppManager.getAppManager().currentActivity().getClass().getSuperclass();
        if(clazz!=null)
        {
            EvoTrace.e("ack","clazz ="+clazz.getSimpleName());
            if("BaseUi".equalsIgnoreCase(clazz.getSimpleName()))
            {
                // TODO: 2018/7/25 release the block ui
                Intent boadcast = new Intent(Constant.ACTION_ERROR_CHECK_BLOCK_UI);
                EvoTrace.e("ack","send Constant.ACTION_ERROR_CHECK_BLOCK_UI");
                sendBroadcast(boadcast);

            }
        }
    }
    private void releaseprocess()
    {
        Class<?> clazz = AppManager.getAppManager().currentActivity().getClass();
        if(clazz!=null)
        {
            EvoTrace.e("ack","clazz ="+clazz.getSimpleName());
            if("ErrorBlockUi".equalsIgnoreCase(clazz.getSimpleName()))
            {
                // TODO: 2018/7/25 release the block ui
                Intent boadcast = new Intent(Constant.ACTION_ERROR_CHECK_RELEASE_UI);
                sendBroadcast(boadcast);
            }
        }
    }
    private int drinkpid;
    private int lastdoorstate =0xffffffff;
    List<MachineWarn> _machinewarnlist = new ArrayList<>(6);
    /**
     * deal with the machine state
     */
    private void dealwithAckquery(String[] acks)
    {
        EvoTrace.e(TAG_ACK, "get ACK_STATE_QUERY");
        Intent boadcast;
        _machinewarnlist.clear();
        int doorstate;
        int driptray;
        int heater;
        int wasterbin;
        ackQuery.Encodeing2class(acks);
        switch (ackQuery.getMachine_state())
        {
            case AckQuery.MS_NORMAL_IDEL:
                break;
            case AckQuery.MS_NORMAL_FILLING:
                break;
            case AckQuery.MS_NORMAL_HEATING:
                break;
            case AckQuery.MS_BACKUP:
                break;
            case AckQuery.MS_BLOCK_MODE:
                // TODO: 2018/6/29 block ui
                if(ackQuery.blockcheck++>=AckQuery.MS_ERROR_CHECK_FREQUENCY) {
                    ackQuery.blockcheck = 0;
                    blockprocess();
                }
                break;
            case AckQuery.MS_CAL_ING:
                break;
            case AckQuery.MS_CAL_FINISH:
                if(ackQuery.cleanfinishcheck++>=AckQuery.MS_CLEAN_FINISH_CHECK_FREQUENCY)
                {
                    ackQuery.cleanfinishcheck =0;
                    // TODO: 2018/7/27  send calibration finish
                    app.addCmdQueue((new CmdCalibration()).buildCalibrationFinishedCmd());
                }
                break;
            case AckQuery.MS_CLEAN_CLEANING:
                break;
            case AckQuery.MS_CLEAN_FINISH:
                // TODO: 2018/7/20 send cmd clean finished. check every 5 secs
                if(ackQuery.cleanfinishcheck++>=AckQuery.MS_CLEAN_FINISH_CHECK_FREQUENCY)
                {
                    ackQuery.cleanfinishcheck =0;
                    app.addCmdQueue((new CmdCleanFinish()).buildCmd());
                }
                break;
            case AckQuery.MS_CLEAN_MILK:
                break;
            case AckQuery.MS_DISPEN_ING:
                break;
            case AckQuery.MS_DISPEN_FINISH:
                if(ackQuery.dinkkfinishcheck++>=AckQuery.MS_DRINK_FINISH_CHECK_FREQUENCY)
                {
                    ackQuery.dinkkfinishcheck =0;
                    app.addCmdQueue((new CmdDrinkFinish()).buildCmd(drinkpid));
                }
                break;
            case AckQuery.MS_FRESET_ING:
                break;
            case AckQuery.MS_FRESET_FINISH:
                break;
            case AckQuery.MS_TEST_FINISH:
                if(ackQuery.cleanfinishcheck++>=AckQuery.MS_CLEAN_FINISH_CHECK_FREQUENCY)
                {
                    ackQuery.cleanfinishcheck =0;
                    app.addCmdQueue((new CmdTestFinish()).buildCmd());
                }
                break;
            case AckQuery.MS_CFG_DOWNLOAD:
                break;
            case AckQuery.MS_CFG_FINISH:
                if(ackQuery.cleanfinishcheck++>=AckQuery.MS_CLEAN_FINISH_CHECK_FREQUENCY)
                {
                    ackQuery.cleanfinishcheck =0;
                    app.addCmdQueue((new CmdConfigFinish()).buildCmd());
                }
                break;
        }
        if(ackQuery.getMachine_state()!=AckQuery.MS_BLOCK_MODE)
        {
            // TODO: 2018/7/24 release ui
            if(ackQuery.releasecheck++>=AckQuery.MS_ERROR_CHECK_FREQUENCY) {
                ackQuery.releasecheck =0;
                releaseprocess();
            }
        }
        doorstate = ackQuery.getIndexDoorState();
        driptray = ackQuery.getIndexDriptrayState();
        heater = ackQuery.getIndexHeaterState();
        wasterbin = ackQuery.getIndexWasterBinState();
        if(doorstate!=0xffffffff)
        {
            boadcast = new Intent(Constant.ACTION_DOOR_STATE);
            boadcast.putExtra("STATE", doorstate);
            sendBroadcast(boadcast);
        }
        if(driptray!=0xffffffff)
        {
            // TODO: 2018/6/29
            boadcast = new Intent(Constant.ACTION_DRIPTRAY_STATE);
            boadcast.putExtra("STATE",driptray);
            sendBroadcast(boadcast);
        }
        if(wasterbin!=0xffffffff)
        {
            // TODO: 2018/6/29
            boadcast = new Intent(Constant.ACTION_WASTERBIN_STATE);
            boadcast.putExtra("STATE",wasterbin);
            sendBroadcast(boadcast);
        }
        if(heater!=0xffffffff)
        {
            // TODO: 2018/6/29
            boadcast = new Intent(Constant.ACTION_HEATER_STATE);
            boadcast.putExtra("STATE",heater);
            sendBroadcast(boadcast);
        }
        if(ackQuery.getError_size()>0)
        {
            // TODO: 2018/6/29 chuli jiqi de xinxi ,jueding shifou xuyao block the machine
            for (DeviceErrorItem erroritem:ackQuery.getDeviceErrorItems()) {
                if(erroritem.getError_size()>0)
                {
                    for (ErrorItem code:erroritem.getError_list()) {
                        _machinewarnlist.add(new MachineWarn(erroritem.getId(),code.getCode(),code.getLevel()));
                    }
                }
            }
        }
        app.resettheWarningList(_machinewarnlist);
    }

    /**
     *
     * @param acks
     * @param boadcast
     */
    private void dealwithmakeIngredient(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithmakeIngredient");
        AckMakeIngredient ackMakeIngredient = new AckMakeIngredient();
        ackMakeIngredient.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_MAKE_INGREDIENT_ACK);
        boadcast.putExtra("ACK",ackMakeIngredient.getAckresult());
        boadcast.putExtra("OP",ackMakeIngredient.getResdata().getOpcmd());
        sendBroadcast(boadcast);

    }

    /**
     *
     * @param acks
     * @param boadcast
     */
    private void dealwithmakeBeverage(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithmakeBeverage");
        AckMakeBeverage ackMakeIngredient = new AckMakeBeverage();
        ackMakeIngredient.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_MAKE_BEVERAGE_ACK);
        boadcast.putExtra("ACK",ackMakeIngredient.getAckresult());
        boadcast.putExtra("OP",ackMakeIngredient.getResdata().getOpcmd());
        sendBroadcast(boadcast);

    }

    private void dealwithmakeDrink(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithmakeDrink");
        AckMakeDrink ackMakeDrink = new AckMakeDrink();
        ackMakeDrink.Encodeing2class(acks);
        drinkpid = ackMakeDrink.getResdata().getId();
        boadcast = new Intent(Constant.ACTION_MAKE_DRINK_ACK);
        boadcast.putExtra("ACK",ackMakeDrink.getAckresult());
        sendBroadcast(boadcast);

    }
    private void dealwithtest(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithtest");
        AckTest ackTest = new AckTest();
        ackTest.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_TEST_ACK);
        boadcast.putExtra("OP",ackTest.getResdata().getOp());
        boadcast.putExtra("ID",ackTest.getResdata().getId());
        boadcast.putExtra("ACK",ackTest.getAckresult());
        sendBroadcast(boadcast);

    }

    private void dealwithAckmachineconfig(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckmachineconfig");
        AckMachineConfig ackMachineConfig = new AckMachineConfig();
        ackMachineConfig.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_TEST_ACK);
        boadcast.putExtra("ACK",ackMachineConfig.getAckresult());
        sendBroadcast(boadcast);
    }

    private void dealwithAckCleanRequest(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckCleanRequest");
        AckCleanRequest ackCleanRequest = new AckCleanRequest();
        ackCleanRequest.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_ACK_CLEAN_REQUEST);
        boadcast.putExtra("ACK",ackCleanRequest.getAckresult());
        sendBroadcast(boadcast);
    }

    private void dealwithAckCleanFinish(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckCleanFinish");
        AckCleanFinish ackCleanFinish = new AckCleanFinish();
        ackCleanFinish.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_CLEAN_MACHINE_FINISH);
        boadcast.putExtra("ACK",ackCleanFinish.getAckresult());
        sendBroadcast(boadcast);
    }

    private void dealwithAckDrinkFinish(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckDrinkFinish");
        AckDrinkFinish ackDrinkFinish = new AckDrinkFinish();
        ackDrinkFinish.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_MAKE_DRINK_FINISH_ACK);
        boadcast.putExtra("ACK",ackDrinkFinish.getAckresult());
        sendBroadcast(boadcast);
    }
    private void dealwithcalibration(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithcalibration");
        AckCalibrationRequest ack = new AckCalibrationRequest();
        ack.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_ACK_CALIBRATION_STATE);
        boadcast.putExtra("ACK",ack.getAckresult());
        sendBroadcast(boadcast);
    }
    private void dealwithAckCalibrationFinish(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckCalibrationFinish");
        AckCalibrationFinish ack = new AckCalibrationFinish();
        ack.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_CALIBRATION_FINISH);
        boadcast.putExtra("ACK",ack.getAckresult());
        sendBroadcast(boadcast);
    }

    private void dealwithAckDeviceDBGet(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckDeviceDBGet");
        AckDeviceDB ack = new AckDeviceDB();
        ack.Encodeing2class(acks);
       for(DeviceDBItem dbItem:ack.getResdata().getDeviceDBItems())
       {
           if(dbItem!=null)
           {
               for (DeviceParamItem paramItem:dbItem.getDeviceParamItems())
               {
                   switch ((dbItem.getDeviceid()&0xffff0000))
                   {
                       case 0x00ff0000:   //machine-setting
                           switch (paramItem.getParamid())
                           {
                               case 1:
                                   break;
                               case 2:
                                   break;
                               case 3:
                                   break;
                               case 4:
                                   break;
                               case 5:   //Date-year
                                   break;
                               case 6:  //Date-month & day
                                   break;
                               case 7: //Date -  hour & minute
                                   break;
                               case 8:  //Date – second
                                   break;
                               case 0x0a:       //Firmware Version
                                   boadcast = new Intent(Constant.ACTION_DB_GET_FIRMWARE_VERSION);
                                   boadcast.putExtra("VERSION",paramItem.getParamvalue());
                                   sendBroadcast(boadcast);
                                   break;
                               case 0x0b:       //External board Version
                                   boadcast = new Intent(Constant.ACTION_DB_GET_EXTERNAL_VERSION);
                                   boadcast.putExtra("VERSION",paramItem.getParamvalue());
                                   sendBroadcast(boadcast);
                                   break;
                               case 0x0c:       //Machine type
                                   boadcast = new Intent(Constant.ACTION_DB_GET_MACHINE_TYPE);
                                   boadcast.putExtra("MACHINETYPE",paramItem.getParamvalue());
                                   sendBroadcast(boadcast);
                                   break;
                           }
                           break;
                       case 0x00020000:     //grinder-device
                           switch (paramItem.getParamid())
                           {
                               case 1:       //dosgae
                                   boadcast = new Intent(Constant.ACTION_DB_GET_GRINDER_CALIBRATION_VALUE);
                                   boadcast.putExtra("DOSAGE",paramItem.getParamvalue());
                                   sendBroadcast(boadcast);
                                   break;
                               case 2:       //motor life
                                   break;
                               case 3:       //runningtm
                                   break;
                           }
                           break;
                       case 0x00030000:     //canister-device
                           switch (paramItem.getParamid())
                           {
                               case 1:       //Canister package
                                   break;
                               case 2:       //Canister calibration amount
                                   boadcast = new Intent(Constant.ACTION_DB_GET_GRINDER_CALIBRATION_VALUE);
                                   boadcast.putExtra("DOSAGE",paramItem.getParamvalue());
                                   sendBroadcast(boadcast);
                                   break;
                               case 3:       //Canister motor useful life
                                   break;
                               case 4:       //Canister running time (s)
                                   break;
                           }
                           break;
                   }
               }
           }
       }
        //boadcast = new Intent(Constant.ACTION_CALIBRATION_FINISH);
        //sendBroadcast(boadcast);
    }
    private void dealwithAckDeviceDBSet(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckDeviceDBSet");
        AckDeviceDBSet ack = new AckDeviceDBSet();
        ack.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_DB_SET_ACK);
        boadcast.putExtra("ACK",ack.getAckresult());
        sendBroadcast(boadcast);
    }

    private void dealwithAckConfigFinish(String[] acks,Intent boadcast)
    {
        EvoTrace.e(TAG_ACK, "dealwithAckConfigFinish");
        AckConfigFinish ack = new AckConfigFinish();
        ack.Encodeing2class(acks);
        boadcast = new Intent(Constant.ACTION_CONFIG_MACHINE_FINISH);
        boadcast.putExtra("ACK",ack.getAckresult());
        sendBroadcast(boadcast);
    }
    private int getWarnLevel(int id ,int code)
    {
        return 1;
    }
    /**
     * check the ack checksum
     * @return
     */
    private boolean checkdatesum() {
        int checksum = 0;
        int cpsum = Integer.valueOf(stringBuffer.substring(stringBuffer.length() - 2, stringBuffer.length()), 16);
        int count = (stringBuffer.length() - 10) / 2;
        //判断长度合法性
        for (int i = 0; i < count; i++) {
            checksum ^= Integer.valueOf(stringBuffer.substring(8 + i * 2, 10 + i * 2), 16);
        }
        if (checksum == cpsum) {
            return true;
        }
        EvoTrace.e("ack","checkdatesum >> error");
        stringBuffer.setLength(0);
        curLength = 0;
        ackDataLength = 0;
        headPos = -1;
        return false;
    }
    /**
    check the cmd sn == ack sn ?
     */
    private boolean checksn()
    {
        int respsn = Integer.valueOf(stringBuffer.substring(8 , 10), 16);
        int cmdsn = Integer.valueOf(curCmd.replace(" ","").substring(8 , 10), 16);
        if(respsn == cmdsn)
        {
            //he fa bao
            return true;
        }
        else
        {
            EvoTrace.e("ack","checksn >> error");
            //cuowei bao
            app.cleanQueue();
            stringBuffer.setLength(0);
            curLength = 0;
            ackDataLength = 0;
            headPos = -1;
            return false;
        }
    }
}
