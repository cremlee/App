package android.luna.Data.module.System;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/8/6.
 */
@DatabaseTable(tableName = "tb_secret_setting")
public class SecretSettings implements Serializable{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "1")  //off
    private int qrmode;
    @DatabaseField(defaultValue = "evopsw20180502ll")
    private String qrpin;
    @DatabaseField(defaultValue = "0")  //off
    private int servicemode;
    @DatabaseField(defaultValue = "000000")  //off
    private String servicepin;
    @DatabaseField(defaultValue = "0")  //off
    private int usermode;
    @DatabaseField(defaultValue = "000000")  //off
    private String userpin;

    public SecretSettings() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQrmode() {
        return qrmode;
    }

    public void setQrmode(int qrmode) {
        this.qrmode = qrmode;
    }

    public String getQrpin() {
        return qrpin;
    }

    public void setQrpin(String qrpin) {
        this.qrpin = qrpin;
    }

    public int getServicemode() {
        return servicemode;
    }

    public void setServicemode(int servicemode) {
        this.servicemode = servicemode;
    }

    public String getServicepin() {
        return servicepin;
    }

    public void setServicepin(String servicepin) {
        this.servicepin = servicepin;
    }

    public int getUsermode() {
        return usermode;
    }

    public void setUsermode(int usermode) {
        this.usermode = usermode;
    }

    public String getUserpin() {
        return userpin;
    }

    public void setUserpin(String userpin) {
        this.userpin = userpin;
    }
}
