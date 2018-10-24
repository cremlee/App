package android.luna.Utils.Device;

import android.luna.Data.module.CleanActionItem;
import android.luna.Data.module.DeviceLayout.DeviceItemLayout;
import android.luna.Data.module.MachineDevice.DEV_Waterpump;
import android.luna.Data.module.MachineDevice.DEV_virMachine;
import android.luna.Data.module.MachineDevice.Dev_AirPump;
import android.luna.Data.module.MachineDevice.Dev_Airbreak;
import android.luna.Data.module.MachineDevice.Dev_Boiler_ES;
import android.luna.Data.module.MachineDevice.Dev_Boiler_G;
import android.luna.Data.module.MachineDevice.Dev_BubPump;
import android.luna.Data.module.MachineDevice.Dev_Canister;
import android.luna.Data.module.MachineDevice.Dev_ES;
import android.luna.Data.module.MachineDevice.Dev_Fan;
import android.luna.Data.module.MachineDevice.Dev_Grinder;
import android.luna.Data.module.MachineDevice.Dev_Heater;
import android.luna.Data.module.MachineDevice.Dev_Hopper;
import android.luna.Data.module.MachineDevice.Dev_Led;
import android.luna.Data.module.MachineDevice.Dev_Mixer_L;
import android.luna.Data.module.MachineDevice.Dev_Mono;
import android.luna.Data.module.MachineDevice.Dev_SenCup;
import android.luna.Data.module.MachineDevice.Dev_SenDoor;
import android.luna.Data.module.MachineDevice.Dev_SenDriptray;
import android.luna.Data.module.MachineDevice.Dev_SenFlowmeter;
import android.luna.Data.module.MachineDevice.Dev_SenNtc;
import android.luna.Data.module.MachineDevice.Dev_SenPressuer;
import android.luna.Data.module.MachineDevice.Dev_SenWaster;
import android.luna.Data.module.MachineDevice.Dev_SenWater;
import android.luna.Data.module.MachineDevice.Device;
import android.luna.Utils.FileHelper;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class DeviceXmlFactory {
   public  static final int   CLEAN_TYPE_DAILY= 1;
    public  static final int  CLEAN_TYPE_WEEKLY= 2;
    public  static final int  CLEAN_TYPE_DRY_OPEN= 3;
    public  static final int  CLEAN_TYPE_DRY_CLOSE= 4;
    //// TODO: 2018/4/17 getall device info from the config xml; 
    public static List<Device> getXmlDevice(String xmlpath)  {
        List<Device> devices =null;
        Device device =null;
        List<Integer> parents =null;
        List<Integer> sons =null;
        int pcnt=0,scnt =0;
        try {
            InputStream xml = new FileInputStream(xmlpath);
            XmlPullParser pullParser = Xml.newPullParser();
            pullParser.setInput(xml, "UTF-8");
            int event = pullParser.getEventType();
            while ( event!= XmlPullParser.END_DOCUMENT)
            {
                switch (event){
                    case XmlPullParser.START_DOCUMENT:
                        devices =new ArrayList<>(20);
                        break;
                    case XmlPullParser.START_TAG:
                        if("Device".equals(pullParser.getName()))
                        {
                            String id = pullParser.getAttributeValue(null,"ID");

                            if(id.startsWith("000101")) //ES-brewer
                            {
                                device = new Dev_ES(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_ES)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                                ((Dev_ES)device).setInlet_flow(Integer.parseInt(pullParser.getAttributeValue(null,"Flow_Meter")));
                                ((Dev_ES)device).setLife_brewer_motor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Motor")));
                                ((Dev_ES)device).setLife_inlet_valve(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Valve")));
                            }
                            else if(id.startsWith("000102")) //Mono-brewer
                            {
                                device = new Dev_Mono(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Mono)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                                ((Dev_Mono)device).setInlet_flow(Integer.parseInt(pullParser.getAttributeValue(null,"Flow_Meter")));
                                ((Dev_Mono)device).setLife_brewer_motor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Motor")));
                                ((Dev_Mono)device).setLife_inlet_valve(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Valve")));
                            }
                            else if(id.startsWith("0002")) //grinder
                            {
                                device = new Dev_Grinder(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Grinder)device).setDosage_value(Integer.parseInt(pullParser.getAttributeValue(null,"Dosage_Value")));
                                ((Dev_Grinder)device).setMotor_life(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Motor")));
                            }
                            else if(id.startsWith("0003")) //canister
                            {
                                device =new Dev_Canister(0x02,0x01);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Canister)device).setMotor_life(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Motor")));
                                (device).setCompent_type(Integer.parseInt(pullParser.getAttributeValue(null,"Type")));
                                ((Dev_Canister)device).setDosage_value(Integer.parseInt(pullParser.getAttributeValue(null,"Dosage_Value")));
                                ((Dev_Canister)device).setPowder_type(Integer.parseInt(pullParser.getAttributeValue(null,"Powder")));
                                ((Dev_Canister)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                            }
                            else if(id.startsWith("0004")) //mixer
                            {
                                device = new Dev_Mixer_L(0x01);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Mixer_L)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                                ((Dev_Mixer_L)device).setRun_speed(Integer.parseInt(pullParser.getAttributeValue(null,"Speed")));
                                ((Dev_Mixer_L)device).setHot_valve_flow(Integer.parseInt(pullParser.getAttributeValue(null,"Hot_flow")));
                                ((Dev_Mixer_L)device).setCold_valve_flow(Integer.parseInt(pullParser.getAttributeValue(null,"Cold_flow")));
                                ((Dev_Mixer_L)device).setLife_motor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Motor")));
                                ((Dev_Mixer_L)device).setLife_cold_valve(Integer.parseInt(pullParser.getAttributeValue(null,"Life_cold_valve")));
                                ((Dev_Mixer_L)device).setLife_hot_valve(Integer.parseInt(pullParser.getAttributeValue(null,"Life_hot_valve")));
                                ((Dev_Mixer_L)device).setLife_whipper(Integer.parseInt(pullParser.getAttributeValue(null,"Life_whipper")));
                            }
                            else if(id.startsWith("0005")) //flowmeter
                            {
                                device =new Dev_SenFlowmeter(1,1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenFlowmeter)device).setPluse(Integer.parseInt(pullParser.getAttributeValue(null,"Pluse")));
                            }
                            else if(id.startsWith("0006")) //ntc
                            {
                                device = new Dev_SenNtc(Integer.parseInt(pullParser.getAttributeValue(null,"Type")));
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenNtc)device).setLife_sensor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Sensor")));
                                ((Dev_SenNtc)device).setTemperature_normal(Integer.parseInt(pullParser.getAttributeValue(null,"Normal")));
                                ((Dev_SenNtc)device).setTemperature_warning(Integer.parseInt(pullParser.getAttributeValue(null,"Warn")));
                                ((Dev_SenNtc)device).setTemperature_block(Integer.parseInt(pullParser.getAttributeValue(null,"Block")));
                                ((Dev_SenNtc)device).setTemperature_eco(Integer.parseInt(pullParser.getAttributeValue(null,"ECO")));
                                ((Dev_SenNtc)device).setTemperature_offset(Integer.parseInt(pullParser.getAttributeValue(null,"Offset")));
                            }
                            else if(id.startsWith("0007")) //water level
                            {
                                device = new Dev_SenWater(Integer.parseInt(pullParser.getAttributeValue(null,"Type")));
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenWater)device).setLife_sensor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Sensor")));
                            }
                            else if(id.startsWith("0008")) //cup sensor
                            {
                                device = new Dev_SenCup(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenCup)device).setLife_sensor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Sensor")));
                            }
                            else if(id.startsWith("0009")) //pressure sensor
                            {
                                device = new Dev_SenPressuer(1,1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setCompent_type(Integer.parseInt(id.substring(4,6),16));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenPressuer)device).setLife_sensor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Sensor")));
                            }
                            else if(id.startsWith("000A")) //pump water
                            {
                                device = new DEV_Waterpump(1,1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setCompent_type(Integer.parseInt(id.substring(4,6),16));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((DEV_Waterpump)device).setMotor_life(Integer.parseInt(pullParser.getAttributeValue(null,"Motor_life")));
                                ((DEV_Waterpump)device).setSpeed(Integer.parseInt(pullParser.getAttributeValue(null,"Speed")));
                            }
                            else if(id.startsWith("000B01")) //airpump
                            {
                                device = new Dev_AirPump();
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setCompent_type(Integer.parseInt(id.substring(4,6),16));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_AirPump)device).setMotor_life(Integer.parseInt(pullParser.getAttributeValue(null,"Motor_life")));
                                ((Dev_AirPump)device).setSpeed(Integer.parseInt(pullParser.getAttributeValue(null,"Speed")));
                            }
                            else if(id.startsWith("000B02")) //airpump
                            {
                                device = new Dev_BubPump();
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setCompent_type(Integer.parseInt(id.substring(4,6),16));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_BubPump)device).setMotor_life(Integer.parseInt(pullParser.getAttributeValue(null,"Motor_life")));
                                ((Dev_BubPump)device).setSpeed(Integer.parseInt(pullParser.getAttributeValue(null,"Speed")));
                            }
                            else if(id.startsWith("000C")) //led
                            {
                                device = new Dev_Led(Integer.parseInt(pullParser.getAttributeValue(null,"Type")),1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Led)device).setLed_idel_mode(Integer.parseInt(pullParser.getAttributeValue(null,"IdleMode")));
                                ((Dev_Led)device).setLed_idel_color(Integer.parseInt(pullParser.getAttributeValue(null,"IdleColor")));
                                ((Dev_Led)device).setLed_idel_intensity(Integer.parseInt(pullParser.getAttributeValue(null,"IdleInt")));
                                ((Dev_Led)device).setLed_warn_mode(Integer.parseInt(pullParser.getAttributeValue(null,"WarnMode")));
                                ((Dev_Led)device).setLed_warn_color(Integer.parseInt(pullParser.getAttributeValue(null,"WarnColor")));
                                ((Dev_Led)device).setLed_warn_intensity(Integer.parseInt(pullParser.getAttributeValue(null,"WarnInt")));
                                ((Dev_Led)device).setLife_led(Integer.parseInt(pullParser.getAttributeValue(null,"LedLife")));
                            }
                            else if(id.startsWith("000e")) //air
                            {
                                device =new Dev_Airbreak(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                            }
                            else if(id.startsWith("000F02")) //boiler
                            {
                                device =new Dev_Boiler_G(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Boiler_G)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                                ((Dev_Boiler_G)device).setInlet_water_type(Integer.parseInt(pullParser.getAttributeValue(null,"Water")));
                                ((Dev_Boiler_G)device).setLife_intlet_valve(Integer.parseInt(pullParser.getAttributeValue(null,"Life_inlet_valve")));
                                ((Dev_Boiler_G)device).setCycle_boiler_clean(Integer.parseInt(pullParser.getAttributeValue(null,"Clean_cycle")));
                            }
                            else if(id.startsWith("000F03")) //boiler
                            {
                                device =new Dev_Boiler_ES(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Boiler_ES)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                                ((Dev_Boiler_ES)device).setInlet_water_type(Integer.parseInt(pullParser.getAttributeValue(null,"Water")));
                                ((Dev_Boiler_ES)device).setLife_intlet_valve(Integer.parseInt(pullParser.getAttributeValue(null,"Life_inlet_valve")));
                                ((Dev_Boiler_ES)device).setCycle_boiler_clean(Integer.parseInt(pullParser.getAttributeValue(null,"Clean_cycle")));
                            }
                            else if(id.startsWith("0014")) //fan
                            {
                                device =new Dev_Fan(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Fan)device).setLife_motor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Motor")));
                                ((Dev_Fan)device).setRun_speed(Integer.parseInt(pullParser.getAttributeValue(null,"Speed")));
                            }
                            else if(id.startsWith("0015")) //hopper
                            {
                                device = new Dev_Hopper(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Hopper)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                                ((Dev_Hopper)device).setPowder_type(Integer.parseInt(pullParser.getAttributeValue(null,"Powder")));
                                //((Dev_Hopper)device).setDosage_value(Integer.parseInt(pullParser.getAttributeValue(null,"Speed")));
                                ((Dev_Hopper)device).setMotor_life(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Motor")));
                            }
                            else if(id.startsWith("0016")) //heater
                            {
                                device = new Dev_Heater(1,1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setCompent_type(Integer.parseInt(id.substring(4,6),16));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_Heater)device).setMax_heat_time(Integer.parseInt(pullParser.getAttributeValue(null,"Max_heat_time")));
                                ((Dev_Heater)device).setLife_times(Integer.parseInt(pullParser.getAttributeValue(null,"Life_times")));
                            }
                            else if(id.startsWith("0018")) //driptray
                            {
                                device = new Dev_SenDriptray(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenDriptray)device).setLife_sensor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Sensor")));
                                ((Dev_SenDriptray)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                            }
                            else if(id.startsWith("0019")) //wasterbin
                            {
                                device = new Dev_SenWaster(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenWaster)device).setLife_sensor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Sensor")));
                                ((Dev_SenWaster)device).setMax_capability(Integer.parseInt(pullParser.getAttributeValue(null,"Max_Capability")));
                            }
                            else if(id.startsWith("001A")) //door
                            {
                                device = new Dev_SenDoor(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                                device.setPosition_id(Integer.parseInt(id.substring(6),16));
                                ((Dev_SenDoor)device).setLife_sensor(Integer.parseInt(pullParser.getAttributeValue(null,"Life_Sensor")));
                            }
                            else if(id.startsWith("000002")) //machine
                            {

                                device =new DEV_virMachine(1);
                                device.setID(pullParser.getAttributeValue(null,"GUID"));
                            }

                        }
                      else  if("Parents".equals(pullParser.getName()))
                        {
                            int count = Integer.parseInt(pullParser.getAttributeValue(null,"Count"));
                            pcnt =count;
                            if(count >0)
                                parents = new ArrayList<>(count);
                        }
                        else if("Parent".equals(pullParser.getName()))
                        {
                            if(pcnt>0)
                                parents.add(Integer.parseInt(pullParser.getAttributeValue(null,"ID"),16));
                        }
                        else if("Sons".equals(pullParser.getName()))
                        {
                            int count = Integer.parseInt(pullParser.getAttributeValue(null,"Count"));
                            scnt =count;
                            if(count >0)
                                sons = new ArrayList<>(count);
                        }
                        else  if("Son".equals(pullParser.getName()))
                        {
                            if(scnt>0)
                            sons.add(Integer.parseInt(pullParser.getAttributeValue(null,"ID"),16));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if("Device_Map".equals(pullParser.getName()))
                        {
                            break;
                        }
                       else if("Device".equals(pullParser.getName()))
                        {
                            devices.add(device);
                            device =null;
                        }
                       else if("Parent".equals(pullParser.getName()))
                        {
                            if(parents!=null && parents.size() == pcnt) {
                                device.setParent_id_list(parents);
                                parents =null;
                                pcnt=0;
                            }
                        }
                       else if("Son".equals(pullParser.getName()))
                        {
                            if(sons!=null && sons.size() == scnt) {
                                device.setSon_id_list(sons);
                                sons =null;
                                scnt= 0;
                            }
                        }
                        break;
                }
                event = pullParser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return devices;
    }
    //// TODO: 2018/4/17 getall machine layoutinfo from config xml;
    public static List<DeviceItemLayout> getXmlLayout(String xmlpath)
    {
        List<DeviceItemLayout> devices =null;
        DeviceItemLayout device =null;
        String tagName = "";
        try {
            InputStream xml = new FileInputStream(xmlpath);
            XmlPullParser pullParser = Xml.newPullParser();
            pullParser.setInput(xml, "UTF-8");
            int event = pullParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        devices = new ArrayList<>(10);
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = pullParser.getName();
                        if ("DeviceItem".equals(tagName)) {
                            device = new DeviceItemLayout();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        String text = pullParser.getText().equals("")?"0":pullParser.getText();
                        if ("Uid".equals(tagName)) {
                            device.setUid(text);
                        } else if ("Left".equals(tagName)) {
                            device.setLeft(Float.parseFloat(text));
                        } else if ("Top".equals(tagName)) {
                            device.setTop(Float.parseFloat(text));
                        } else if ("Width".equals(tagName)) {
                            device.setWidth(Float.parseFloat(text));
                        } else if ("Height".equals(tagName)) {
                            device.setHeight(Float.parseFloat(text));
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("DeviceItem".equals(pullParser.getName())) {
                            if(!device.getUid().equals("0000-00")) {
                                devices.add(device);
                            }
                            device = null;
                        }tagName = "";
                        break;
                }
                event = pullParser.next();
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return devices;
    }
    ////// TODO: 2018/4/17 getfile Md5 value
    //// TODO: 2018/4/17 calc file md5 value
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        String md5 = bigInt.toString(16);
        while (md5.length() < 32)
            md5 = "0" + md5;
        return md5;
    }

    public static void UpdateDeviceXml(Device d)
    {
        List<Device> devices = getXmlDevice(FileHelper.PATH_CONFIG+"config.xmld");
        if(devices!=null)
        {
            for (Device item : devices)
            {
                if(item.GetDeviceId() == d.GetDeviceId())
                {
                    devices.remove(item);
                    devices.add(d);
                    break;
                }
            }
            try {
                NewDeviceXml(devices);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void NewDeviceXml(List<Device> devices) throws Exception
    {
        File file = new File(FileHelper.PATH_CONFIG+"config.xmld");
        file.delete();
        File xmlFile = new File(FileHelper.PATH_CONFIG+"config.xmld");

        FileOutputStream outStream = new FileOutputStream(xmlFile);

        String DeviceIDStr ="";
        XmlSerializer serializer = Xml.newSerializer();
        serializer.setOutput(outStream, "UTF-8");
        serializer.startDocument("UTF-8", true);
        serializer.startTag(null, "Device_Map");
        for(Device device : devices){
            serializer.startTag(null, "Device");
            serializer.attribute(null, "GUID", device.getID());
            DeviceIDStr = String.format("%08X",device.GetDeviceId()).toUpperCase();
            serializer.attribute(null, "ID", DeviceIDStr);
            if(DeviceIDStr.startsWith("000101")) //es
            {
                serializer.attribute(null, "Name", "ES-Brewer");
                serializer.attribute(null, "Max_Capability", ( (Dev_ES) device).getMax_capability()+"");
                serializer.attribute(null, "Flow_Meter", ( (Dev_ES) device).getInlet_flow()+"");
                serializer.attribute(null, "Life_Motor", ( (Dev_ES) device).getLife_brewer_motor()+"");
                serializer.attribute(null, "Life_Valve", ( (Dev_ES) device).getLife_inlet_valve()+"");

            }
            else if(DeviceIDStr.startsWith("000102")) //mono
            {
                serializer.attribute(null, "Name", "Mono-Brewer");
                serializer.attribute(null, "Max_Capability", ( (Dev_Mono) device).getMax_capability()+"");
                serializer.attribute(null, "Flow_Meter", ( (Dev_Mono) device).getInlet_flow()+"");
                serializer.attribute(null, "Life_Motor", ( (Dev_Mono) device).getLife_brewer_motor()+"");
                serializer.attribute(null, "Life_Valve", ( (Dev_Mono) device).getLife_inlet_valve()+"");

            }
            else  if(DeviceIDStr.startsWith("0002")) //grinder
            {
                serializer.attribute(null, "Name", "Grinder");
                serializer.attribute(null, "Life_Motor", ( (Dev_Grinder) device).getMotor_life()+"");
                serializer.attribute(null, "Dosage_Value", ( (Dev_Grinder) device).getDosage_value()+"");
            }
            else if(DeviceIDStr.startsWith("0003")) //canister
            {
                serializer.attribute(null, "Name", "Canister");
                serializer.attribute(null, "Max_Capability", ( (Dev_Canister) device).getMax_capability()+"");
                serializer.attribute(null, "Type", ( device).getCompent_type()+"");
                serializer.attribute(null, "Dosage_Value", ( (Dev_Canister) device).getDosage_value()+"");
                serializer.attribute(null, "Powder", ( (Dev_Canister) device).getPowder_type()+"");
                serializer.attribute(null, "Life_Motor", ( (Dev_Canister) device).getMotor_life()+"");
            }
            else if(DeviceIDStr.startsWith("0004")) //mixer
            {
                serializer.attribute(null, "Name", "Mixer");
                serializer.attribute(null, "Max_Capability", ( (Dev_Mixer_L) device).getMax_capability()+"");
                serializer.attribute(null, "Speed", ((Dev_Mixer_L) device).getRun_speed()+"");
                serializer.attribute(null, "Hot_flow", ( (Dev_Mixer_L) device).getHot_valve_flow()+"");
                serializer.attribute(null, "Cold_flow", ( (Dev_Mixer_L) device).getCold_valve_flow()+"");
                serializer.attribute(null, "Life_Motor", ( (Dev_Mixer_L) device).getLife_motor()+"");
                serializer.attribute(null, "Life_hot_valve", ( (Dev_Mixer_L) device).getLife_hot_valve()+"");
                serializer.attribute(null, "Life_cold_valve", ( (Dev_Mixer_L) device).getLife_cold_valve()+"");
                serializer.attribute(null, "Life_whipper", ( (Dev_Mixer_L) device).getLife_whipper()+"");
            }
            else if(DeviceIDStr.startsWith("0005")) //flowmeter
            {
                serializer.attribute(null, "Name", "Flowmeter");
                serializer.attribute(null, "Pluse", ( (Dev_SenFlowmeter) device).getPluse()+"");
            }
            else if(DeviceIDStr.startsWith("0006")) //ntc
            {
                serializer.attribute(null, "Name", "NTC");
                serializer.attribute(null, "Type", ( (Dev_SenNtc) device).getCompent_type()+"");
                serializer.attribute(null, "Normal", ((Dev_SenNtc) device).getTemperature_normal()+"");
                serializer.attribute(null, "Warn", ( (Dev_SenNtc) device).getTemperature_warning()+"");
                serializer.attribute(null, "Block", ( (Dev_SenNtc) device).getTemperature_block()+"");
                serializer.attribute(null, "ECO", ( (Dev_SenNtc) device).getTemperature_eco()+"");
                serializer.attribute(null, "Offset", ( (Dev_SenNtc) device).getTemperature_offset()+"");
                serializer.attribute(null, "Life_Sensor", ( (Dev_SenNtc) device).getLife_sensor()+"");
           }
            else if(DeviceIDStr.startsWith("0007")) //water level
            {
                serializer.attribute(null, "Name", "Water_level");
                serializer.attribute(null, "Type", ( (Dev_SenWater) device).getCompent_type()+"");
                serializer.attribute(null, "Life_Sensor", ((Dev_SenWater) device).getLife_sensor()+"");
           }
            else if(DeviceIDStr.startsWith("0008")) //cup sensor
            {
                serializer.attribute(null, "Name", "Cup_sensor");
                serializer.attribute(null, "Type", ( (Dev_SenCup) device).getCompent_type()+"");
                serializer.attribute(null, "Life_Sensor", ((Dev_SenCup) device).getLife_sensor()+"");
            }
            else if(DeviceIDStr.startsWith("0009")) //pressure
            {
                serializer.attribute(null, "Name", "Pressure_sensor");
                serializer.attribute(null, "Type", ( (Dev_SenPressuer) device).getCompent_type()+"");
                serializer.attribute(null, "Life_Sensor", ( (Dev_SenPressuer) device).getLife_sensor()+"");
            }
            else if(DeviceIDStr.startsWith("000A")) //pump
            {
                serializer.attribute(null, "Name", "Waterpump");
                serializer.attribute(null, "Type", ( device).getCompent_type()+"");
                serializer.attribute(null, "Speed", ( (DEV_Waterpump) device).getSpeed()+"");
                serializer.attribute(null, "Motor_life", ( (DEV_Waterpump) device).getMotor_life()+"");
            }
            else if(DeviceIDStr.startsWith("000B01")) //air
            {
                serializer.attribute(null, "Name", "Waterpump");
                serializer.attribute(null, "Type", ( device).getCompent_type()+"");
                serializer.attribute(null, "Speed", ( (Dev_AirPump) device).getSpeed()+"");
                serializer.attribute(null, "Motor_life", ( (Dev_AirPump) device).getMotor_life()+"");
            }
            else if(DeviceIDStr.startsWith("000B02")) //bub
            {
                serializer.attribute(null, "Name", "Waterpump");
                serializer.attribute(null, "Type", ( device).getCompent_type()+"");
                serializer.attribute(null, "Speed", ( (Dev_BubPump) device).getSpeed()+"");
                serializer.attribute(null, "Motor_life", ( (Dev_BubPump) device).getMotor_life()+"");
            }
            else if(DeviceIDStr.startsWith("000C")) //led
            {
                serializer.attribute(null, "Name", "LED");
                serializer.attribute(null, "Type", ( (Dev_Led) device).getCompent_type()+"");
                serializer.attribute(null, "IdleMode", ((Dev_Led) device).getLed_idel_mode()+"");
                serializer.attribute(null, "IdleColor", ((Dev_Led) device).getLed_idel_color()+"");
                serializer.attribute(null, "IdleInt", ((Dev_Led) device).getLed_idel_intensity()+"");
                serializer.attribute(null, "WarnMode", ((Dev_Led) device).getLed_warn_mode()+"");
                serializer.attribute(null, "WarnColor", ((Dev_Led) device).getLed_warn_color()+"");
                serializer.attribute(null, "WarnInt", ((Dev_Led) device).getLed_warn_intensity()+"");
                serializer.attribute(null, "LedLife", ((Dev_Led) device).getLife_led()+"");
            }
            else if(DeviceIDStr.startsWith("000E")) //airbreak
            {
                serializer.attribute(null, "Name", "Airbreak");
            }
            else if(DeviceIDStr.startsWith("000F02")) //boiler
            {
                serializer.attribute(null, "Name", "Boiler");
                serializer.attribute(null, "Max_Capability", ( (Dev_Boiler_G) device).getMax_capability()+"");
                serializer.attribute(null, "Water", ((Dev_Boiler_G) device).getInlet_water_type()+"");
                serializer.attribute(null, "Life_inlet_valve", ( (Dev_Boiler_G) device).getLife_intlet_valve()+"");
                serializer.attribute(null, "Clean_cycle", ((Dev_Boiler_G) device).getCycle_boiler_clean()+"");
              }
            else if(DeviceIDStr.startsWith("000F03")) //boiler
            {
                serializer.attribute(null, "Name", "Boiler");
                serializer.attribute(null, "Max_Capability", ( (Dev_Boiler_ES) device).getMax_capability()+"");
                serializer.attribute(null, "Water", ((Dev_Boiler_ES) device).getInlet_water_type()+"");
                serializer.attribute(null, "Life_inlet_valve", ( (Dev_Boiler_ES) device).getLife_intlet_valve()+"");
                serializer.attribute(null, "Clean_cycle", ((Dev_Boiler_ES) device).getCycle_boiler_clean()+"");
            }
            else if(DeviceIDStr.startsWith("0014")) //fan
            {
                serializer.attribute(null, "Name", "Fan");
                serializer.attribute(null, "Speed", ( (Dev_Fan) device).getRun_speed()+"");
                serializer.attribute(null, "Life_Motor", ((Dev_Fan) device).getLife_motor()+"");
             }
            else if(DeviceIDStr.startsWith("0015")) //hopper
            {
                serializer.attribute(null, "Name", "Hopper");
                serializer.attribute(null, "Max_Capability", ( (Dev_Hopper) device).getMax_capability()+"");
                serializer.attribute(null, "Powder", ((Dev_Hopper) device).getPowder_type()+"");
                serializer.attribute(null, "Life_Motor", ((Dev_Hopper) device).getMotor_life()+"");
             }
            else if(DeviceIDStr.startsWith("0016")) //heater
            {
                serializer.attribute(null, "Name", "Heater");
                serializer.attribute(null, "Max_heat_time", ((Dev_Heater) device).getMax_heat_time()+"");
                serializer.attribute(null, "Life_times", ((Dev_Heater) device).getLife_times()+"");
            }
            else if(DeviceIDStr.startsWith("0018")) //driptray
            {
                serializer.attribute(null, "Name", "Drip_tray");
                serializer.attribute(null, "Max_Capability", ( (Dev_SenDriptray) device).getMax_capability()+"");
                serializer.attribute(null, "Life_Sensor", ( (Dev_SenDriptray) device).getLife_sensor()+"");
            }
            else if(DeviceIDStr.startsWith("0019")) //wasterbin
            {
                serializer.attribute(null, "Name", "Waster_bin");
                serializer.attribute(null, "Max_Capability", ( (Dev_SenWaster) device).getMax_capability()+"");
                serializer.attribute(null, "Life_Sensor", ( (Dev_SenWaster) device).getLife_sensor()+"");
             }
            else if(DeviceIDStr.startsWith("001A")) //door
            {
                serializer.attribute(null, "Name", "Door");
                serializer.attribute(null, "Life_Sensor", ( (Dev_SenDoor) device).getLife_sensor()+"");
            }
            else if(DeviceIDStr.startsWith("000002")) //machine
            {
                serializer.attribute(null, "Name", "Machine");
            }
            //parent
            serializer.startTag(null, "Parents");
            serializer.attribute(null,"Count",device.GetParentCount()+"");
            if(device.GetParentCount()>0)
            {
                for (int va:device.getParent_id_list())
                {
                    serializer.startTag(null, "Parent");
                    serializer.attribute(null,"ID",String.format("%08X",va));
                    serializer.endTag(null, "Parent");
                }

            }
            serializer.endTag(null, "Parents");
            // son part
            serializer.startTag(null, "Sons");
            serializer.attribute(null,"Count",device.GetSonCount()+"");
            if(device.GetSonCount()>0)
            {
                for (int va:device.getSon_id_list())
                {
                    serializer.startTag(null, "Son");
                    serializer.attribute(null,"ID",String.format("%08X",va));
                    serializer.endTag(null, "Son");
                }

            }
            serializer.endTag(null, "Sons");
            serializer.endTag(null, "Device");
        }
        serializer.endTag(null, "Device_Map");
        serializer.endDocument();
        outStream.flush();
        outStream.close();
    }
    public static List<CleanActionItem> getCleanComponent(String xmlpath,int cleantype)
    {
        List<CleanActionItem> ret = new ArrayList<>();
        List<Device> devices = getXmlDevice(xmlpath);
        CleanActionItem tmp;
        for (Device item:devices)
        {
            switch (cleantype) {
                case CLEAN_TYPE_DAILY:
                    if(item.IsNeedDaily())
                    {
                        tmp = new CleanActionItem(item.GetDeviceId(),CleanActionItem.CLEAN_ACTION_RINGSING);
                        ret.add(tmp);
                    }
                    break;
                case CLEAN_TYPE_WEEKLY:
                    if(item.IsNeedWeekly())
                    {
                        if(item.getGroup_id() ==0x0001 && item.getCompent_type() == 0x03)
                            tmp = new CleanActionItem(item.GetDeviceId(),CleanActionItem.CLEAN_ACTION_SORK);
                        else
                            tmp = new CleanActionItem(item.GetDeviceId(),CleanActionItem.CLEAN_ACTION_RINGSING);
                        ret.add(tmp);
                        if(item.getGroup_id() !=0x0001) {
                            tmp = new CleanActionItem(item.GetDeviceId(), CleanActionItem.CLEAN_ACTION_SHOCK);
                            ret.add(tmp);
                        }
                    }
                    break;
                case CLEAN_TYPE_DRY_OPEN:
                    if(item.IsDryClean())
                    {
                        tmp = new CleanActionItem(item.GetDeviceId(),CleanActionItem.CLEAN_ACTION_DRY_OPEN);
                        ret.add(tmp);
                    }
                    break;
                case CLEAN_TYPE_DRY_CLOSE:
                    if(item.IsDryClean())
                    {
                        tmp = new CleanActionItem(item.GetDeviceId(),CleanActionItem.CLEAN_ACTION_DRY_CLOSE);
                        ret.add(tmp);
                    }
                    break;
            }
        }

        return ret;
    }
    public static List<Device> getAttachDeviceByUid(String Uid,List<Device> devices)
    {
        List<Device> ret = new ArrayList<>(2);
        Device main = getMainDeviceByUid(Uid,devices);
        String attstr = "";
        if(main!=null && main.getParent_id_list()!=null && main.getParent_id_list().size()>0)
        {

            for (Integer item:main.getParent_id_list())
            {
                attstr = String.format("%08X",item.intValue());
                if(attstr.startsWith("0005") || attstr.startsWith("0006") || attstr.startsWith("0007") ||attstr.startsWith("0008") || attstr.startsWith("000B")
                        || attstr.startsWith("000C") || attstr.startsWith("0014") || attstr.startsWith("0015") || attstr.startsWith("0018") ||attstr.startsWith("0019") || attstr.startsWith("001A"))
                {
                    ret.add(getMainDeviceByUid(attstr,devices));
                }
            }
        }
        if(ret.size()>0)
            return ret;
        return null;
    }

    public static Device getMainDeviceByUid(String Uid,List<Device> devices)
    {
        for (Device item:devices)
        {
            if(item.GetDeviceId() == Integer.parseInt(Uid,16))
            {
                return item;
            }
        }
        return null;
    }

    // TODO: 2018/10/9 according to the mpd get the correct type
    public static int getEsBeanType()
    {
        return 0x81;
    }
    public static int getMonoBeanType()
    {
        return 0x81;
    }
}
