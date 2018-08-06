package android.luna.Data.module;

import java.sql.Date;

/**
 * Created by Lee.li on 2017/8/2.
 */

public class LogRecord {

    public static  int COLOR_WARNING =2;
    public static  int COLOR_ERROR   =3;
    public static  int COLOR_CLEAN   =4;
    public static  int COLOR_NOTE    =5;
    public static  int COLOR_SERVICE =7;
    public static  int COLOR_COUNTER =6;

    private int event_type;
    private String event_time;
    private String event_info;
    private String event_help;

    public LogRecord(){}

    public LogRecord(int event_type, String event_info, String event_help) {
        this.event_type = event_type;
        this.event_info = event_info;
        this.event_help = event_help;
    }

    public int getEvent_type() {
        return event_type;
    }

    public void setEvent_type(int event_type) {
        this.event_type = event_type;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_info() {
        return event_info;
    }

    public void setEvent_info(String event_info) {
        this.event_info = event_info;
    }

    public String getEvent_help() {
        return event_help;
    }

    public void setEvent_help(String event_help) {
        this.event_help = event_help;
    }

}
