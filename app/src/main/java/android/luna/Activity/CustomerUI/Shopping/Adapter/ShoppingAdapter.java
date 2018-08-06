package android.luna.Activity.CustomerUI.Shopping.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.luna.Activity.CustomerUI.Gallery.Adapter.MachineWarningAdapter;
import android.luna.Activity.CustomerUI.Shopping.model.OrderBean;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Utils.Logger.EvoTrace;
import android.luna.Utils.PictureManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lid.lib.LabelImageView;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import java.util.HashMap;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/3/20.
 */

public class ShoppingAdapter extends BaseAdapter{

    public  interface OnCartChanged
    {
        void Add(ImageView view,int cups);
        void Del(int cups);
    }

    public OnCartChanged monCartChanged;

    public void  SetOnCartChanged(OnCartChanged a)
    {
        monCartChanged =a;
    }
    public ShoppingAdapter(Context context,List<DrinkMenuButton> data)
    {
        mcontext = context;
        drinkMenuButtonList =data;
        orderBean = new OrderBean(data);
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    private OrderBean orderBean;
    private Context mcontext;
    private List<DrinkMenuButton> drinkMenuButtonList=null;
    @Override
    public int getCount() {
        return drinkMenuButtonList==null?0:drinkMenuButtonList.size();
    }

    @Override
    public DrinkMenuButton getItem(int position) {
        return drinkMenuButtonList==null?null:drinkMenuButtonList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private HashMap<Integer, View> viewMap = new HashMap<>();
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingAdapter.ViewHolder holder = null;
        if(!viewMap.containsKey(position) || viewMap.get(position) == null)
        {
            holder = new ShoppingAdapter.ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.myviewpagershop, null);
            holder.drinkicon =  convertView.findViewById(R.id.drinkicon);
            holder.drinkname = convertView.findViewById(R.id.drinkname);
            holder.cart_btn = convertView.findViewById(R.id.cart_btn);
            holder.drinkstate = convertView.findViewById(R.id.drinkstate);
            convertView.setTag(holder);
            viewMap.put(position, convertView);
        } else {
            convertView = viewMap.get(position);
            holder = (ShoppingAdapter.ViewHolder) convertView.getTag();
        }
        final DrinkMenuButton item = drinkMenuButtonList.get(position);
        if(item!=null)
        {
            if(item.getDrinkstate()==0) {
                holder.drinkstate.setVisibility(View.GONE);
            }
            else if(item.getDrinkstate()==1){
                holder.drinkstate.setText("Out of Stock");
            }
            else if(item.getDrinkstate()==2){
                holder.drinkstate.setText("Not Available");
            }
            else if(item.getDrinkstate()==3){
                holder.drinkstate.setText("Empty waster bin");
            }
            holder.cart_btn.setCount(orderBean.getitemCups(item.getPid()));
            final ViewHolder finalHolder = holder;
            holder.cart_btn.setOnAddDelListener(new IOnAddDelListener() {
                @Override
                public void onAddSuccess(int i) {
                    orderBean.Order_Add();
                    orderBean.setordermap(item.getPid(),i);
                    if(monCartChanged!=null)
                    {
                        monCartChanged.Add(finalHolder.drinkicon,orderBean.getCups());
                    }
                }

                @Override
                public void onAddFailed(int i, FailType failType) {
                    EvoTrace.e("shop","onAddFailed"+i);
                }

                @Override
                public void onDelSuccess(int i) {
                    EvoTrace.e("shop","onDelSuccess"+i);
                    orderBean.Order_Del();
                    orderBean.setordermap(item.getPid(),i);
                    if(monCartChanged!=null)
                    {
                        monCartChanged.Del(orderBean.getCups());
                    }
                }

                @Override
                public void onDelFaild(int i, FailType failType) {
                    EvoTrace.e("shop","onDelFaild"+i);
                }
            });
            holder.drinkname.setText(item.getName());
            holder.drinkicon.setLabelDistance(50);
            holder.drinkicon.setLabelHeight(30);
            holder.drinkicon.setLabelTextSize(18);
            if(item.getPrice()!=0 )
                holder.drinkicon.setLabelText("$ "+Float.toString(item.getPrice()));
            else
                holder.drinkicon.setLabelText("free");
            String path = item.getIconpath();
            if(path!=null) {
                Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                if (bitmap == null) {
                    final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(path, 240, 240);
                    if (tmpbitmap != null) {
                        PictureManager.getInstance().addBitmapToMemoryCache(path, tmpbitmap);
                        bitmap = PictureManager.getInstance().getBitmapFromMemCache(path);
                    }
                }
                if (bitmap != null)
                    holder.drinkicon.setImageBitmap(bitmap);
                else
                    holder.drinkicon.setImageResource(R.mipmap.coffee);
            }
        }
        return convertView;
    }

    class ViewHolder {
        LabelImageView drinkicon;
        TextView drinkname;
        AnimShopButton cart_btn;
        TextView drinkstate;
    }
}
