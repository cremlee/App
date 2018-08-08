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

}
