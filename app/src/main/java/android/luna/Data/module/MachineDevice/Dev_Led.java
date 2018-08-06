package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_Led extends Device {
    private int led_idel_mode=1;
    private int led_idel_color=3;
    private int led_idel_intensity=1;
    private int led_warn_mode=2;
    private int led_warn_color=1;
    private int led_warn_intensity=1;
    private int life_led=5000;
    public Dev_Led(int type,int position) { super(0x000c, type, position);}

    public int getLed_idel_mode() {
        return led_idel_mode;
    }

    public void setLed_idel_mode(int led_idel_mode) {
        this.led_idel_mode = led_idel_mode;
    }

    public int getLed_idel_color() {
        return led_idel_color;
    }

    public void setLed_idel_color(int led_idel_color) {
        this.led_idel_color = led_idel_color;
    }

    public int getLed_idel_intensity() {
        return led_idel_intensity;
    }

    public void setLed_idel_intensity(int led_idel_intensity) {
        this.led_idel_intensity = led_idel_intensity;
    }

    public int getLed_warn_mode() {
        return led_warn_mode;
    }

    public void setLed_warn_mode(int led_warn_mode) {
        this.led_warn_mode = led_warn_mode;
    }

    public int getLed_warn_color() {
        return led_warn_color;
    }

    public void setLed_warn_color(int led_warn_color) {
        this.led_warn_color = led_warn_color;
    }

    public int getLed_warn_intensity() {
        return led_warn_intensity;
    }

    public void setLed_warn_intensity(int led_warn_intensity) {
        this.led_warn_intensity = led_warn_intensity;
    }

    public int getLife_led() {
        return life_led;
    }

    public void setLife_led(int life_led) {
        this.life_led = life_led;
    }
}
