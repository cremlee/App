package android.luna.ViewUi.NutritionUi.data;

import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/5/24.
 */

public class PowderBean {

    public int MaskCode;



    private int[] resIds = new int[]{
            R.mipmap.con_cellery,R.mipmap.con_crustaceans,R.mipmap.con_eggs,R.mipmap.con_fish,
            R.mipmap.con_gluten,R.mipmap.con_lupin,R.mipmap.con_milk,R.mipmap.con_mollucs,
            R.mipmap.con_mustard,R.mipmap.con_nuts,R.mipmap.con_peanuts,R.mipmap.con_seasame,
            R.mipmap.con_sulphite
    };
    private String[] resname = new String[]{
            "cellery","crustaceans","eggs","fish","gluten","lupin","milk",
            "mollucs","mustard","nuts","peanuts","seasame","sulphite"};
    public List<itembean> getdata()
    {
        List<itembean> ret = new ArrayList<>(13);
        for (int i = 0; i < 13; i++) {
            if (((MaskCode >> i) & 0x01) == 0x01) {
                ret.add(new itembean(resname[i],resIds[i]));
            }
        }
        return ret;
    }

    public class itembean
    {
        public String name;
        public int resid;

        public itembean(String name, int resid) {
            this.name = name;
            this.resid = resid;
        }
    }
}
