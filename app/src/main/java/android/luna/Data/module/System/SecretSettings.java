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
    private int qrmode;
    private String qrpin;
    private int servicemode;
    private String servicepin;
    private int usermode;
    private String userpin;

}
