package android.luna.ViewUi.CleanProcessView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/8/10.
 */

public class CleanProcessView extends LinearLayout {
    private  Context mContext;
    private  int totalcount=3;
    private LinearLayout lytview;
    private Vector<ImageView> cleanindexs=new Vector<>(3,1);
    public CleanProcessView(Context context) {
        super(context);
    }

    public CleanProcessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext =context;
        setupview();
    }

    public CleanProcessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setupview()
    {
        ImageView site;
        TextView line;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_cleanprocessview, this, true);
        lytview = view.findViewById(R.id.lytview);
        for(int i=0;i<totalcount;i++)
        {
            site = new ImageView(mContext);
            LayoutParams lp = new LayoutParams(30,30);
            lp.gravity =Gravity.CENTER;
            site.setLayoutParams(lp);
            site.setBackgroundColor(mContext.getResources().getColor(R.color.suva_gray));
            lytview.addView(site);

            if(i<totalcount-1) {
                line = new TextView(mContext);
                lp = new LayoutParams(80, 2);
                lp.gravity =Gravity.CENTER;
                lp.setMarginStart(5);
                lp.setMarginEnd(5);
                line.setLayoutParams(lp);
                line.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                lytview.addView(line);
            }
        }
    }

    public void setTotalcount(int count)
    {
        ImageView site;
        TextView line;
        totalcount = count;
        lytview.removeAllViews();
        cleanindexs.clear();
        for(int i=0;i<totalcount;i++)
        {
            site = new ImageView(mContext);
            LayoutParams lp = new LayoutParams(30,30);
            lp.gravity =Gravity.CENTER;
            site.setLayoutParams(lp);
            site.setBackgroundColor(mContext.getResources().getColor(R.color.suva_gray));
            lytview.addView(site);
            cleanindexs.add(site);
            if(i<totalcount-1) {
                line = new TextView(mContext);
                lp = new LayoutParams(80, 2);
                lp.gravity =Gravity.CENTER;
                lp.setMarginStart(5);
                lp.setMarginEnd(5);
                line.setLayoutParams(lp);
                line.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                lytview.addView(line);
            }
        }
    }

    public void activeSite(int position,int state)
    {
        if(position<totalcount)
        {
            cleanindexs.get(position).setBackgroundColor(state==1?mContext.getResources().getColor(R.color.green):mContext.getResources().getColor(R.color.yellow));
        }
    }
}
