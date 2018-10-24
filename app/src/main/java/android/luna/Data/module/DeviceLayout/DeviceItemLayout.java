package android.luna.Data.module.DeviceLayout;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/17.
 */

public class DeviceItemLayout {
    private String Uid;
    private float Left;
    private float Top;
    private float Width;
    private float Height;

    public DeviceItemLayout() {
    }

    public DeviceItemLayout(String uid, float left, float top, float width, float height) {
        Uid = uid;
        Left = left;
        Top = top;
        Width = width;
        Height = height;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public float getLeft() {
        return Left;
    }

    public void setLeft(float left) {
        Left = left;
    }

    public float getTop() {
        return Top;
    }

    public void setTop(float top) {
        Top = top;
    }

    public float getWidth() {
        return Width;
    }

    public void setWidth(float width) {
        Width = width;
    }

    public float getHeight() {
        return Height;
    }

    public void setHeight(float height) {
        Height = height;
    }

    public int geticonres()
    {
        if(getUid().startsWith("000101"))
            return R.mipmap.esbrewer;
        else if(getUid().startsWith("000102"))
            return R.mipmap.monobrewer;
        else if(getUid().startsWith("0002"))
            return R.mipmap.grinder;
        else if(getUid().startsWith("0003"))
            return R.mipmap.canister;
        else if(getUid().startsWith("0004"))
            return R.mipmap.mixer;
        else if(getUid().startsWith("000F"))
            return R.mipmap.boiler;
        else if(getUid().startsWith("000002"))
            return R.mipmap.machine;
        else if(getUid().startsWith("000000"))
            return R.mipmap.valvearray;
        else if(getUid().startsWith("000A"))
            return R.mipmap.water;
        return R.mipmap.canister;
    }

    public String getname()
    {
        if(getUid().startsWith("0002"))
            return "Grinder";
        else if(getUid().startsWith("000101"))
            return "ES-Brewer";
        else if(getUid().startsWith("000102"))
            return "Mono-Brewer";
        else if(getUid().startsWith("0003"))
            return "Canister";
        else if(getUid().startsWith("0004"))
            return "Mixer";
        else if(getUid().startsWith("000F02"))
            return "Gravity Boiler";
        else if(getUid().startsWith("000F03"))
            return "ES Boiler";
        else if(getUid().startsWith("000002"))
            return "Peripheral";
        else if(getUid().startsWith("000000"))
            return "Valve";
        else if(getUid().startsWith("000A"))
            return "Water System";
        return "Unknown";
    }
}
