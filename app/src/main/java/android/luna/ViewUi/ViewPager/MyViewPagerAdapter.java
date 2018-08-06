package android.luna.ViewUi.ViewPager;

import android.content.Context;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import evo.luna.android.R;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Lee.li on 2017/12/26.
 */

public class MyViewPagerAdapter extends PagerAdapter {
    private List<DrinkMenuButton> drinkMenuButtonList;
    private Context context;
    public  MyViewPagerAdapter(List<DrinkMenuButton> drinkMenuButtonList,Context context)
    {
        this.drinkMenuButtonList = drinkMenuButtonList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return this.drinkMenuButtonList==null?0:this.drinkMenuButtonList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = LayoutInflater.from(context).inflate(R.layout.myviewpager,null);
        container.addView(v);
        return  v;
    }

    @Override
    public float getPageWidth(int position) {
        return 0.4f;
    }
}
