package android.luna.Activity.CustomerUI.Shopping.model;

import android.luna.Activity.CustomerUI.Shopping.Adapter.CartItem;
import android.luna.Data.CustomerUI.DrinkMenuButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lee.li on 2018/3/20.
 */

public class OrderBean {
    private int cups;
    private Map<Integer,Integer> ordermap;
    private List<DrinkMenuButton> drinkMenuButtonList=null;

    public OrderBean(List<DrinkMenuButton> data) {
        this.cups = 0;
        ordermap = new LinkedHashMap <>(10);
        drinkMenuButtonList =data;
    }

   public List<CartItem> getcartlistdata()
   {
       if(drinkMenuButtonList ==null || drinkMenuButtonList.size() ==0)
           return null;
       if(ordermap.size()==0)
           return null;
       List<CartItem> ret =new ArrayList<>(20);
       CartItem tmp;
       for (Map.Entry<Integer,Integer> key:ordermap.entrySet())
       {
           for (DrinkMenuButton item:drinkMenuButtonList)
           {
               if(item.getPid() == key.getKey())
               {
                   tmp =new CartItem(item,ordermap.get(item.getPid()));
                   ret.add(tmp);
                   break;
               }
           }
       }
       return ret;
   }

    public void resetdata()
    {
        this.cups = 0;
        ordermap.clear();
    }

    public void Order_Add()
    {
        cups++;
    }

    public void Order_Del()
    {
        cups--;
    }

    public void setordermap(int pid,int num)
    {
        ordermap.put(pid, num);
        //shuaxin cups
        cups = getCupsfromMap();
    }

    public  int getCupsfromMap()
    {
        int ret =0;
        for(Integer key:ordermap.keySet())
        {
            ret+=ordermap.get(key);
        }
        return ret;
    }

    public int getCups() {
        return cups;
    }

    public int getitemCups(int pid)
    {
        return ordermap.get(pid) == null?0:ordermap.get(pid);
    }
    public Map<Integer, Integer> getOrdermap() {
        return ordermap;
    }
}
