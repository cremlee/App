package android.luna.Data.module;

/**
 * Created by Lee.li on 2017/8/2.
 */

public class CounterRecord {
    private int    beverage_pid;
    private String beverage_name;
    private int    beverage_counter;

    public CounterRecord(int beverage_pid, String beverage_name, int beverage_counter) {
        this.beverage_pid = beverage_pid;
        this.beverage_name = beverage_name;
        this.beverage_counter = beverage_counter;
    }

    public int getBeverage_pid() {
        return beverage_pid;
    }

    public void setBeverage_pid(int beverage_pid) {
        this.beverage_pid = beverage_pid;
    }

    public String getBeverage_name() {
        return beverage_name;
    }

    public void setBeverage_name(String beverage_name) {
        this.beverage_name = beverage_name;
    }

    public int getBeverage_counter() {
        return beverage_counter;
    }

    public void setBeverage_counter(int beverage_counter) {
        this.beverage_counter = beverage_counter;
    }
}
