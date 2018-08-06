package android.luna.ViewUi.OfficeUi.Fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverage_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Data.module.PersonItem;
import android.luna.Utils.PictureManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/29.
 */

public class PersonFragment extends Fragment implements View.OnClickListener ,IPersonFragment<PersonItem>{

    private ImageView icon;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_drink, container, false);
        icon = view.findViewById(R.id.icon);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
        }
    }


    @Override
    public void initView(PersonItem data) {


        if(data.getIconpath()!=null && !data.getIconpath().equals(""))
        {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(data.getIconpath());
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(data.getIconpath(), 120, 120);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(data.getIconpath(), tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(data.getIconpath());
                }
            }
            if (bitmap != null)
                icon.setImageBitmap(bitmap);
            else
                icon.setImageResource(R.mipmap.ic_person_default);
        }
        else
        {
           icon.setImageResource(R.mipmap.ic_person_default);
        }
    }
}
