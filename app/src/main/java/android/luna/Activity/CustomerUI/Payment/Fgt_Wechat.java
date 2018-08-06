package android.luna.Activity.CustomerUI.Payment;

import android.graphics.BitmapFactory;
import android.luna.Utils.FileHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/8/1.
 */

public class Fgt_Wechat extends PayFragment implements View.OnClickListener {
    private ImageView img_qr;
    private String filePath = FileHelper.PATH_CONFIG+"qr_tmp.jpg";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lyt_payment_wechat, container, false);
        InitView(view);
        return view;
    }
    @Override
    public void InitView(View view) {
        img_qr = view.findViewById(R.id.img_qr);
    }

    public void showqrcode()
    {
        img_qr.setImageBitmap(BitmapFactory.decodeFile(filePath));
    }
    @Override
    public void InitData() {

    }

    @Override
    public void InitEvent() {

    }

    @Override
    public void onClick(View v) {

    }
}
