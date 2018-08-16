package android.luna.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.luna.Data.module.Key.AliAuthKey;
import android.luna.Data.module.Key.WechatAuthKey;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Lee.li on 2018/1/17.
 */

public class FileHelper {
    private static final String TAG = "FileHelper";
    public static final String PATH_USB = "/update/";
    public static final String PATH_RES= getSDCardDirPath()+"/crem/";
    public static final String PATH_ICON = getSDCardDirPath()+"/crem/icon/";
    public static final String PATH_MAIN_BKG = getSDCardDirPath()+"/crem/background/";
    public static final String PATH_STORY= getSDCardDirPath()+"/crem/story/";
    public static final String PATH_DRINK_AM= getSDCardDirPath()+"/crem/drinkam/";
    public static final String PATH_DRINK_BKG= getSDCardDirPath()+"/crem/drinkbg/";
    public static final String PATH_BRAND= getSDCardDirPath()+"/crem/banner/";
    public static final String PATH_SCREEN_SAVER= getSDCardDirPath()+"/crem/screensave/";
    public static final String PATH_SCREEN_LOGO= getSDCardDirPath()+"/crem/cup/";
    public static final String PATH_HELP_USER= getSDCardDirPath()+"/crem/help/user/";
    public static final String PATH_HELP_SERVICE= getSDCardDirPath()+"/crem/help/service/";
    public static final String PATH_PROFILE= getSDCardDirPath()+"/crem/profile/";
    public static final String PATH_CONFIG= getSDCardDirPath()+"/crem/config/";
    public static final String FILE_KEY_ALI= getSDCardDirPath()+"/crem/key/pay.auk";
    public static final String FILE_KEY_WECHAT= getSDCardDirPath()+"/crem/key/pay.wuk";
    public static final String FILE_PRODUCTION= getSDCardDirPath()+"/crem/machine/config/";
    public static final String PATH_CLEAN= getSDCardDirPath()+"/crem/machine/clean/";
    public static final String FILE_DEVICE_CONFIG= PATH_CONFIG+"config.xmld";
    public static final String PATH_LANG= "/update/language/";
    public static final String PATH_KEY= getSDCardDirPath()+"/crem/key/";
    public static final String PATH_CERT_FILE= getSDCardDirPath()+"/crem/machine/cert/apiclient_cert.p12";

    public static final String PATH_DB_MAIN= "/data/data/evo.luna.android/databases/";

    public static String getSDCardDirPath() {
        return "mnt/sdcard/";
        //return android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public static boolean isSDCardExist() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    public static boolean isDirExist(String path)
    {

        File file_ = new File(path);
        if (!file_.exists())
        {
            return  false;
        }
        return true;
    }

    public static void copyDbFile(Context context, String tab_name) {
        InputStream in = null;
        FileOutputStream out = null;
        /**data/data/路径*/
        String path = "/data/data/" + context.getPackageName() + "/databases";
        File file = new File(path + "/" + tab_name);
        try {
            //创建文件夹
            File file_ = new File(path);
            if (!file_.exists())
                file_.mkdirs();

            if (file.exists())//删除已经存在的
                file.deleteOnExit();
            //创建新的文件
            if (!file.exists())
                file.createNewFile();

            in = context.getAssets().open(tab_name); // 从assets目录下复制
            out = new FileOutputStream(file);
            int length = -1;
            byte[] buf = new byte[1024];
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 递归删除文件和文件夹
     * @param file    要删除的根目录
     */
    public static void RecursionDeleteFile(File file){
        if(file==null)
        {
            return;
        }
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }
    public static boolean IsValidDB(String dbname)
    {
        File file = new File(dbname);
        if(!file.exists())
            return false;
        return true;
    }

    /**
     * 复制单个文件
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static boolean copyFile(String oldPath, String newPath) {
        boolean isok = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            (new File(newPath.substring(0,newPath.lastIndexOf("/")))).mkdirs();
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                fs.flush();
                fs.getFD().sync();
                fs.close();
                inStream.close();
            }
            else
            {
                isok = false;
            }
        }
        catch (Exception e) {
            isok = false;
        }
        return isok;

    }

    /**
     * 复制整个文件夹内容
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        boolean isok = true;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else
                {
                    temp=new File(oldPath+File.separator+file[i]);
                }

                if(temp.isFile()){
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 10];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.getFD().sync();
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){//如果是子文件夹
                    if (!copyFolder(oldPath+"/"+file[i]+"/",newPath+"/"+file[i]+"/"))
                        return false;
                }
            }
        }
        catch (Exception e) {
            isok = false;
        }
        return isok;
    }



    public interface myCallBack {
        void postprogress(int value);
    }
    public static int mfilescount=0;
    public static boolean copyFolder(String oldPath, String newPath,myCallBack mycallback) {
        boolean isok = true;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a=new File(oldPath);
            String[] file=a.list();
            File temp=null;
            for (int i = 0; i < file.length; i++) {
                if(oldPath.endsWith(File.separator)){
                    temp=new File(oldPath+file[i]);
                }
                else
                {
                    temp=new File(oldPath+File.separator+file[i]);
                }
                if(temp.isFile()){
                    mfilescount++;
                    mycallback.postprogress(mfilescount);
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 10];
                    int len;
                    while ( (len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.getFD().sync();
                    output.flush();
                    output.close();
                    input.close();
                }
                if(temp.isDirectory()){
                    if (!copyFolder(oldPath+"/"+file[i]+"/",newPath+"/"+file[i]+"/",mycallback))
                        return false;
                }
            }
        }
        catch (Exception e) {
            isok = false;
        }
        return isok;
    }
    /*
     * modify by lee
     * fix bug when f ==null
     */
    public static int CountFile(File f)
    {
        int size = 0;
        if(f== null)
        {
            return size;
        }
        File flist[] = f.listFiles();
        if(flist!=null)
        {
            size = flist.length;
            for (int i = 0; i < flist.length; i++)
            {
                if (flist[i].isDirectory())
                {
                    size = size + CountFile(flist[i]);
                    size--;
                }
            }}

        return size;
    }

    public static List<String> getAllFile(File f)
    {
        List<String> retlst = new ArrayList<>();
        if(f == null)
        {
            return null;
        }
        File flist[] = f.listFiles();
        if(flist!=null)
        {
            for (int i = 0; i < flist.length; i++)
            {
                if (flist[i].isFile())
                {
                    if(flist[i].getName().endsWith("mp4")||(flist[i].getName().endsWith("png") || flist[i].getName().endsWith("jpg") ||flist[i].getName().endsWith("gif")) && !flist[i].getName().startsWith("."))
                    {
                        retlst.add(flist[i].getPath());
                    }
                }else
                {
                    retlst.addAll(getAllFile(flist[i]));
                }
            }}

        return retlst;
    }

    public static List<String> getPDFFile(File f)
    {
        List<String> retlst = new ArrayList<>();
        if(f == null)
        {
            return null;
        }
        File flist[] = f.listFiles();
        if(flist!=null)
        {
            for (int i = 0; i < flist.length; i++)
            {
                if (flist[i].isFile())
                {
                    if(flist[i].getName().endsWith("pdf"))
                    {
                        retlst.add(flist[i].getPath());
                    }
                }else
                {
                    retlst.addAll(getPDFFile(flist[i]));
                }
            }}

        return retlst;
    }

    public static List<String> getIconFile(File f)
    {
        List<String> retlst = new ArrayList<>();
        if(f == null)
        {
            return null;
        }
        File flist[] = f.listFiles();
        if(flist!=null)
        {
            for (int i = 0; i < flist.length; i++)
            {
                if (flist[i].isFile())
                {
                    if(flist[i].getName().endsWith("png") || flist[i].getName().endsWith("jpg") && !flist[i].getName().startsWith("."))
                    {
                        retlst.add(flist[i].getPath());
                    }
                }else
                {
                    retlst.addAll(getIconFile(flist[i]));
                }
            }}

        return retlst;
    }

    public static List<File> orderByName(String fliePath) {
        List<File> files = Arrays.asList(new File(fliePath).listFiles());
        Collections.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        return files;
    }

    public static AliAuthKey initAliAuth()
    {
        AliAuthKey ret =new AliAuthKey() ;
        String data ="";
        try {
            data =AndroidUtils_Ext.bufferedReader(FileHelper.FILE_KEY_ALI);

        } catch (IOException e) {
            return null;
        }
        byte[] buf =null;
        if(!data.equals(""))
        {
            try {
                buf = AndroidUtils_Ext.decrypt(new BASE64Decoder()
                                .decodeBuffer(data),
                        "Crem1234".getBytes("ASCII"));
            } catch (Exception e) {
                return null;
            }
        }


        if(buf!=null && buf.length>20)
        {
            int pos=0;
            byte[] tmpBuf = new byte[20];
            System.arraycopy(buf,0,tmpBuf,0,20);
            //20 header
            String header = new String(tmpBuf);
            pos+=20;
            int tlv_type =0;
            int lengthlv=0;
            for (;pos<buf.length;)
            {
                try {
                    tlv_type = buf[pos];
                    lengthlv = (buf[pos+2]+(buf[pos+1]<<8));
                    tmpBuf = new byte[lengthlv];
                    pos+=3;
                    System.arraycopy(buf,pos,tmpBuf,0,lengthlv);
                    String tmp = new String(tmpBuf);
                    pos+=lengthlv;
                    if(tlv_type == 1) {
                        ret.setAppid(tmp);
                    }
                    else if(tlv_type == 2) {
                        ret.setPvkey(tmp);
                    }
                    else if(tlv_type == 3) {
                        ret.setPbkey(tmp);
                    }
                    else if(tlv_type == 5) {
                        ret.setKeytype(tmp);
                    }
                }
                    catch(Exception e)
                    {
                        return null;
                    }
            }
        }

        return ret;
    }

    public static WechatAuthKey initWechatAuth()
    {
        WechatAuthKey ret =new WechatAuthKey() ;
        String data ="";
        try {
            data =AndroidUtils_Ext.bufferedReader(FileHelper.FILE_KEY_WECHAT);

        } catch (IOException e) {
            return null;
        }
        byte[] buf =null;
        if(!data.equals(""))
        {
            try {
                buf = AndroidUtils_Ext.decrypt(new BASE64Decoder()
                                .decodeBuffer(data),
                        "Crem1234".getBytes("ASCII"));
            } catch (Exception e) {
                return null;
            }
        }


        if(buf!=null && buf.length>20)
        {
            int pos=0;
            byte[] tmpBuf = new byte[20];
            System.arraycopy(buf,0,tmpBuf,0,20);
            //20 header
            String header = new String(tmpBuf);
            pos+=20;
            int tlv_type =0;
            int lengthlv=0;
            for (;pos<buf.length;)
            {
                try {
                    tlv_type = buf[pos];
                    lengthlv = (buf[pos+2]+(buf[pos+1]<<8));
                    tmpBuf = new byte[lengthlv];
                    pos+=3;
                    System.arraycopy(buf,pos,tmpBuf,0,lengthlv);
                    String tmp = new String(tmpBuf);
                    pos+=lengthlv;
                    if(tlv_type == 1) {
                        ret.setAppid(tmp);
                    }
                    else if(tlv_type == 2) {
                        ret.setPvkey(tmp);
                    }
                    else if(tlv_type == 4) {
                        ret.setMchid(tmp);
                    }
                }
                catch(Exception e)
                {
                    return null;
                }
            }
        }

        return ret;
    }

    /**
     *
     * @param src
     * @param destPath
     * @return
     */
    public static boolean copyFile(File src, String destPath,String FileName) {
        boolean result = false;
        if ((src == null) || (destPath== null)) {
            return result;
        }
        new File(destPath).mkdirs();
        File dest= new File(destPath + FileName);
        if (dest.exists()) {
            dest.delete(); // delete file
        }
        try {
            dest.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileChannel srcChannel = null;
        FileChannel dstChannel = null;

        try {
            srcChannel = new FileInputStream(src).getChannel();
            dstChannel = new FileOutputStream(dest).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), dstChannel);
            result = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
        try {
            srcChannel.close();
            dstChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
