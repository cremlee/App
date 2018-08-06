package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_SenNtc extends Sensor {
    private int temperature_block =70;
    private int temperature_eco=60;
    private int temperature_warning=85;
    private int temperature_normal=95;
    private int temperature_offset=0;
    public Dev_SenNtc( int type) {
        super(0x0006, type, 0x01);
    }

    public int getTemperature_block() {
        return temperature_block;
    }

    public void setTemperature_block(int temperature_block) {
        this.temperature_block = temperature_block;
    }

    public int getTemperature_eco() {
        return temperature_eco;
    }

    public void setTemperature_eco(int temperature_eco) {
        this.temperature_eco = temperature_eco;
    }

    public int getTemperature_warning() {
        return temperature_warning;
    }

    public void setTemperature_warning(int temperature_warning) {
        this.temperature_warning = temperature_warning;
    }

    public int getTemperature_normal() {
        return temperature_normal;
    }

    public void setTemperature_normal(int temperature_normal) {
        this.temperature_normal = temperature_normal;
    }

    public int getTemperature_offset() {
        return temperature_offset;
    }

    public void setTemperature_offset(int temperature_offset) {
        this.temperature_offset = temperature_offset;
    }
}
