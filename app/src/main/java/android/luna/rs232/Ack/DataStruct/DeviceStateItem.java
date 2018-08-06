package android.luna.rs232.Ack.DataStruct;

/**
 * Created by Lee.li on 2018/7/24.
 */

public class DeviceStateItem  {
    private int id;
    private byte value;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    public DeviceStateItem(int id, byte value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceStateItem that = (DeviceStateItem) o;

        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }
}
