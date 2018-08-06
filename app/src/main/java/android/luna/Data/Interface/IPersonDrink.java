package android.luna.Data.Interface;

import android.luna.Data.module.PersonDrink;

import java.util.List;

/**
 * Created by Lee.li on 2018/4/25.
 */

public interface IPersonDrink extends IPerson<PersonDrink> {

    List<PersonDrink> quryallRecord(int id);
    void updateorNew(PersonDrink a);
}
