package android.luna.Data.module.MachineDevice;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Dev_ES extends Brewer {
    private float vol_max_up = 3.3f;
    private float vol_touch = 3.3f;
    private float vol_press = 3.3f;
    private float vol_max_down = 3.3f;
    public Dev_ES(int position) {
        super(0x01, position);
    }

    public float getVol_max_up() {
        return vol_max_up;
    }

    public void setVol_max_up(float vol_max_up) {
        this.vol_max_up = vol_max_up;
    }

    public float getVol_touch() {
        return vol_touch;
    }

    public void setVol_touch(float vol_touch) {
        this.vol_touch = vol_touch;
    }

    public float getVol_press() {
        return vol_press;
    }

    public void setVol_press(float vol_press) {
        this.vol_press = vol_press;
    }

    public float getVol_max_down() {
        return vol_max_down;
    }

    public void setVol_max_down(float vol_max_down) {
        this.vol_max_down = vol_max_down;
    }
}
