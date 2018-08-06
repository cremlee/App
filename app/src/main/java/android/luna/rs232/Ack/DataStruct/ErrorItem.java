package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/8/1.
 */

public class ErrorItem {
    private int code;
    private int level;

    public ErrorItem(int code, int level) {
        this.code = code;
        this.level = level;
    }

    public ErrorItem() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ErrorItem{" +
                "code=" + code +
                ", level=" + level +
                '}';
    }
}
