package android.luna.Data.module.Payment;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Lee.li on 2018/7/9.
 */

public class XmlDecode<T> implements IXmlDecode<T>{

    @Override
    public HashMap<String, String> TtoMap() {
        Class<? extends XmlDecode<T>> clazz = (Class<? extends XmlDecode<T>>) getClass();
        Field[] fields = clazz.getDeclaredFields();
        HashMap<String, String> params = new HashMap<>();
        try
        {
            for (Field field : fields) {
                field.setAccessible(true);
                if(field.getType() == String.class || field.getType() == int.class)
                    params.put(field.getName(), String.valueOf(field.get(this)));
            }
        }catch (Exception e){
            params =null;
        }
        return  params;
    }
}
