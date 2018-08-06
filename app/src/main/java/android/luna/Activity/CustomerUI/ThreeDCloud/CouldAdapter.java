package android.luna.Activity.CustomerUI.ThreeDCloud;

import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.threeDCloudUi.TagsAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/12.
 */

public class CouldAdapter extends TagsAdapter {
    private List<DrinkMenuButton> drinkMenuButtonList;
    private Context context;

    public  CouldAdapter(Context context,List<DrinkMenuButton> data)
    {
        this.context =context;
        this.drinkMenuButtonList= data;
    }
    @Override
    public int getCount() {
        return this.drinkMenuButtonList==null?0:this.drinkMenuButtonList.size();
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.myviewpager2,null);
        ImageView imageView = view.findViewById(R.id.drinkicon);
        TextView textView =view.findViewById(R.id.drinkname);
        textView.setText(drinkMenuButtonList.get(position).getName());
        String path = drinkMenuButtonList.get(position).getIconpath();//== null?"":drinkMenuButtonList.get(position).getIconpath();
        if(path!=null) {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 400, 400);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                }
            }
            if (bitmap != null)
                imageView.setImageBitmap(bitmap);
            else
                imageView.setImageBitmap(null);
        }
        return view;

    }

    @Override
    public Object getItem(int position) {
        return this.drinkMenuButtonList.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position%5;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {

    }

}
