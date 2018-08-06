package android.luna.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lee.li on 2017/8/1.
 */

public class LogDataBaseHelper extends SQLiteOpenHelper
{

    //version 1: CREATE_LOG,CREATE_TRADE
    //version 2: add CREATE_COUNTER
    private Context mContext;
    public static final String CREATE_LOG = "create table tb_log(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "event_type integer," +
            "event_time TimeStamp DEFAULT(datetime('now', 'localtime'))," +
            "event_info text," +
            "event_help text)";
    public static final String CREATE_TRADE = "create table tb_trade(" +
        //primary key 将id列设为主键    autoincrement表示id列是自增长的
        "id integer primary key autoincrement," +
        "trade_type integer," +
        "trade_time TimeStamp DEFAULT(datetime('now', 'localtime'))," +
        "trade_price integer," +
        "trade_flag integer)";

    public static final String CREATE_COUNTER = "create table tb_counter(" +
            //primary key 将id列设为主键    autoincrement表示id列是自增长的
            "id integer primary key autoincrement," +
            "beverage_pid integer," +
            "beverage_name varchar," +
            "beverage_counter integer," +
            "import_time TimeStamp DEFAULT(datetime('now', 'localtime')))";

    public LogDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOG);
        db.execSQL(CREATE_TRADE);
        db.execSQL(CREATE_COUNTER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
