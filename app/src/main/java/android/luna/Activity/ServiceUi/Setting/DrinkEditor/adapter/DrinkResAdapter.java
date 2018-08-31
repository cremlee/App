package android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.PicSelector.DrinkRes;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.aty_ingrendient_maker;
import android.luna.Utils.FileHelper;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.lid.lib.LabelImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/2/1.
 */

public class DrinkResAdapter extends BaseAdapter {
    public void setMlistdata(List<DrinkRes> mlistdata) {
        viewMap.clear();
        this.mlistdata = mlistdata;
        notifyDataSetChanged();
    }

    private List<DrinkRes> mlistdata =new ArrayList<>();
    private Context mcontext;
    private MaterialDialog progressDialog;
    private boolean ismulti =false;
    private boolean ishowdelete =false;

    public void setIshowdelete()
    {
        this.ishowdelete =!this.ishowdelete;
    }
    @Override
    public int getCount() {
        return mlistdata==null?0:mlistdata.size();
    }

    public void setpicSelectStatus(int position)
    {
        if(!ismulti) {
            boolean b = !mlistdata.get(position).isSelected();
            for (DrinkRes item : mlistdata) {
                item.setSelected(false);
            }
            mlistdata.get(position).setSelected(b);
        }
        else {
            mlistdata.get(position).setSelected(!mlistdata.get(position).isSelected());
        }
    }
    public DrinkResAdapter(Context context , List<DrinkRes> data)
    {
        super();
        mcontext = context;
        mlistdata=data;
    }
    public DrinkResAdapter(Context context , List<DrinkRes> data,boolean ismulti)
    {
        super();
        mcontext = context;
        mlistdata=data;
        this.ismulti =ismulti;
    }
    @Override
    public DrinkRes getItem(int i) {
        return mlistdata==null?null:mlistdata.get(i);
    }

    public  String getSelectPath()
    {
        for(DrinkRes item:mlistdata)
        {
            if(item.isSelected())
                return item.getResPath();
        }
        return "";
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    private HashMap<Integer, View> viewMap = new HashMap<Integer, View>();
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
         ViewHolder holder =null;
        if(!viewMap.containsKey(i) || viewMap.get(i) == null)
        {
            holder = new ViewHolder();
            view = LayoutInflater.from(mcontext).inflate(R.layout.adp_drink_res, null);
            GridView.LayoutParams lp = new GridView.LayoutParams(150, 120);
            view.setLayoutParams(lp);

            holder.selectPicture = view.findViewById(R.id.selectPicture);
            holder.picicon =view.findViewById(R.id.pic_icon);
            holder.remove = view.findViewById(R.id.remove);
            holder.picicon.setTag(getItem(i));
            final int pos = i;
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new MaterialDialog.Builder(mcontext)
                            .title("Delete the picture")
                            .content("Do you want to delete it?")
                            .positiveText("YES")
                            .positiveColor(mcontext.getResources().getColor(R.color.green_grass))
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    // TODO: 2018/8/31 delete the pic select ,notify the ui.
                                    FileHelper.RecursionDeleteFile(new File(mlistdata.get(pos).getResPath()));
                                    mlistdata.remove(pos);
                                    notifyDataSetChanged();
                                    progressDialog.dismiss();
                            }
                            })
                            .negativeText("NO")
                            .negativeColor(mcontext.getResources().getColor(R.color.red_wine))
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    progressDialog.dismiss();
                                }
                            })
                            .canceledOnTouchOutside(false)
                            .show();
                }
            });
            view.setTag(holder);
            viewMap.put(i, view);
        }
        else {
            view = viewMap.get(i);
            holder = (ViewHolder)view.getTag();

        }
        if(!this.ishowdelete)
            holder.remove.setVisibility(View.GONE);
        else
            holder.remove.setVisibility(View.VISIBLE);
        String path = getItem(i).getResPath();
        if(getItem(i).isSelected())
        {
            holder.selectPicture.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.selectPicture.setVisibility(View.GONE);
        }
        if(path!=null)
        {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
            if(bitmap == null)
            {
                Bitmap tmpbitmap =null;
                if(path.endsWith("mp4"))
                {
                    tmpbitmap = PictureManager.getInstance().getVidioBitmap(path,100,100, MediaStore.Images.Thumbnails.MICRO_KIND);
                }
                else
                {
                     tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 800, 600,false);
                }
                    if (tmpbitmap != null) {
                        PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                        bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                    }
                }
                if (bitmap != null) {
                    holder.picicon.setImageBitmap(bitmap);
                    holder.picicon.setLabelText(path.substring(path.lastIndexOf(".") + 1));
                }
            }
        return  view;
    }
    class ViewHolder
    {
        LabelImageView picicon;
        ImageView selectPicture;
        ImageButton remove;
    }
}
