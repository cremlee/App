package android.luna.Data.module.MachineDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lee.li on 2018/4/16.
 */

public class Device implements IDevice {
    private String ID ;
    private int group_id;
    private int compent_type;
    private int position_id =1;
    public List<Integer> son_id_list ;
    public List<Integer> parent_id_list;

    public Device() {
        son_id_list =new ArrayList<>(1);
        parent_id_list =new ArrayList<>();
    }

    public Device(int group_id, int compent_type, int position_id) {
        super();
        this.group_id = group_id;
        this.compent_type = compent_type;
        this.position_id = position_id;
    }

    public Device(int group_id, int compent_type) {
        super();
        this.group_id = group_id;
        this.compent_type = compent_type;
    }

    public Device(int position_id) {
        super();
        this.position_id = position_id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getCompent_type() {
        return compent_type;
    }

    public void setCompent_type(int compent_type) {
        this.compent_type = compent_type;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public List<Integer> getSon_id_list() {
        return son_id_list;
    }

    public void setSon_id_list(List<Integer> son_id_list) {
        this.son_id_list = son_id_list;
    }

    public List<Integer> getParent_id_list() {
        return parent_id_list;
    }

    public void setParent_id_list(List<Integer> parent_id_list) {
        this.parent_id_list = parent_id_list;
    }

    public int GetDeviceId()
    {
        return (group_id << 16) + (compent_type << 8) + (position_id);
    }

    public int GetSonCount()
    {
        return this.son_id_list ==null? 0:this.son_id_list.size();
    }

    public int GetParentCount()
    {
        return this.parent_id_list ==null? 0:this.parent_id_list.size();
    }

    @Override
    public boolean IsNeedDaily() {
        if(     this.group_id ==0x0001 || this.getGroup_id()==0x0004 ||
                this.group_id ==0x000d || this.getGroup_id()==0x0010 ||
                this.group_id ==0x0013 || this.getGroup_id()==0x0017)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean IsNeedWeekly() {
        if(     this.group_id ==0x0001 || this.getGroup_id()==0x0004 ||
                this.group_id ==0x000d || this.getGroup_id()==0x0010 ||
                this.group_id ==0x0013 || this.getGroup_id()==0x0017)
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean IsDryClean() {
        if(     this.group_id ==0x0001 || this.getGroup_id()==0x0004 ||
                this.group_id ==0x000d || this.getGroup_id() ==0x0013 ||
                this.getGroup_id()==0x0017)
        {
            return true;
        }
        return false;
    }
}
