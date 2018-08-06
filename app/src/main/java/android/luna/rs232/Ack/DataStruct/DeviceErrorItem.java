package android.luna.rs232.Ack.DataStruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee.li on 2018/6/29.
 */

public class DeviceErrorItem {
    private int id;
    private int error_size;
    private List<ErrorItem> error_list = new ArrayList<>();

    public DeviceErrorItem(int id, int error_size) {
        this.id = id;
        this.error_size = error_size;
    }

    public DeviceErrorItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getError_size() {
        return error_size;
    }

    public int length()
    {
        return error_size*2;
    }
    public void setError_size(int error_size) {
        this.error_size = error_size;
    }

    public List<ErrorItem> getError_list() {
        return error_list;
    }

    public void setError_list(List<ErrorItem> error_list) {
        this.error_list = error_list;
    }

    public void InitErrorList(String[] cmd, int start, int end)
    {
        int count = (end-start)/2;
        if(count>0)
        {
            ErrorItem errorItem;
            for (int i=0;i<count;i++)
            {

                errorItem = new ErrorItem(Integer.valueOf(cmd[start], 16),Integer.valueOf(cmd[start+1], 16));
                start+=2;
                error_list.add(errorItem);
            }
        }
    }

    @Override
    public String toString() {
        return "DeviceErrorItem{" +
                "id=" + id +
                ", error_size=" + error_size +
                ", error_list=" + error_list +
                '}';
    }
}
