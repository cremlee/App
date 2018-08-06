package android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.fragment;

import android.app.Activity;
import android.content.Intent;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.PicSelector.aty_uiRes_selector;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverage_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.BaseFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.IIngredient;
import android.luna.Data.module.BeverageUi;
import android.luna.Utils.FileHelper;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/14.
 */

public class BeverageUiFragment extends BaseFragment implements IIngredient<BeverageUi> ,View.OnClickListener{
    private aty_beverage_maker aty;
    private SettingItemTextView2 drinkIconPictureItem;
    private SettingItemTextView2 drinkStroytelling;
    private SettingItemTextView2 drinkDispenseTelling;
    private SettingItemTextView2 drinkGallery;
    private BeverageUi getUi()
    {
        return aty.getMbeverageUi();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beverage_ui, container, false);
        drinkIconPictureItem = view.findViewById(R.id.drinkIconPictureItem);
        drinkStroytelling = view.findViewById(R.id.drinkStroytelling);
        drinkDispenseTelling = view.findViewById(R.id.drinkDispenseTelling);
        drinkGallery = view.findViewById(R.id.drinkGallery);
        InitEvent();

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        InitView(null);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void InitView(BeverageUi a) {

    }

    @Override
    public void InitEvent() {
        drinkIconPictureItem.setOnClickListener(this);
        drinkStroytelling.setOnClickListener(this);
        drinkDispenseTelling.setOnClickListener(this);
        drinkGallery.setOnClickListener(this);
    }

    @Override
    public void save() {

    }
    @Override
    public void NotifyChange() {
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(11);
    }

    @Override
    public void NotifyNameChange(String name) {
        if(oningredientChanged!=null)
            oningredientChanged.itemchanged(99);
    }
    @Override
    public void onAttach(Activity activity) {
        aty = (aty_beverage_maker) activity;
        //app = aty.getApp();
        super.onAttach(activity);
    }

    @Override
    public void onClick(View view) {
        int id =view.getId();
        Intent intent;
        switch (id)
        {
            case R.id.drinkIconPictureItem:
                intent = new Intent(getActivity(),aty_uiRes_selector.class);
                intent.putExtra("path", getUi().getIconPath());
                intent.putExtra("folder", FileHelper.PATH_ICON);
                intent.putExtra("reqCode", Constant.REQ_DRINKICONPICUTE);
                startActivityForResult(intent, Constant.REQ_DRINKICONPICUTE);
                break;
            case R.id.drinkStroytelling:
                intent = new Intent(getActivity(),aty_uiRes_selector.class);
                intent.putExtra("path", getUi().getStoryTellingPath());
                intent.putExtra("folder", FileHelper.PATH_STORY);
                intent.putExtra("reqCode", Constant.REQ_DRINKSTORY);
                startActivityForResult(intent, Constant.REQ_DRINKSTORY);
                break;
            case R.id.drinkDispenseTelling:
                intent = new Intent(getActivity(),aty_uiRes_selector.class);
                intent.putExtra("path", getUi().getDispenseTellingPath());
                intent.putExtra("folder", FileHelper.PATH_DRINK_AM);
                intent.putExtra("reqCode", Constant.REQ_DRINKDISPENSE);
                startActivityForResult(intent, Constant.REQ_DRINKDISPENSE);
                break;
            case R.id.drinkGallery:
                intent = new Intent(getActivity(),aty_uiRes_selector.class);
                intent.putExtra("path", getUi().getGalleryBkgPath());
                intent.putExtra("folder", FileHelper.PATH_DRINK_BKG);
                intent.putExtra("reqCode", Constant.REQ_DRINKGALLERY);
                startActivityForResult(intent, Constant.REQ_DRINKGALLERY);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (requestCode == Constant.REQ_DRINKICONPICUTE) {
            getUi().setIconPath(data.getStringExtra("newpath"));
            aty.saveUiData();
        }
        else if(requestCode == Constant.REQ_DRINKSTORY)
        {
            getUi().setStoryTellingPath(data.getStringExtra("newpath"));
            aty.saveUiData();
        }
        else if(requestCode == Constant.REQ_DRINKDISPENSE)
        {
            getUi().setDispenseTellingPath(data.getStringExtra("newpath"));
            aty.saveUiData();
        }
        else if(requestCode == Constant.REQ_DRINKGALLERY)
        {
            getUi().setGalleryBkgPath(data.getStringExtra("newpath"));
            aty.saveUiData();
        }

    }

}
