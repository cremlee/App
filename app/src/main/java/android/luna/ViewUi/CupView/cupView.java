package android.luna.ViewUi.CupView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.Logger.EvoTrace;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/1/23.
 */

public class cupView  extends View{

    private Bitmap m_bitmapbg,m_bitmapmilk; //背景图和覆盖在上面的牛奶
    private float mLeftPadding,mRightPadding,mTopPadding,mBottomPadding;  //上下左右边距
    private float mRectFWidth,mRectFHeight;
    private Rect mSrcRect,mDstRect;
    private Rect mSrccupRect,mDstcupRect;
    private Paint mpaint;
    private int MAX_VALUE =12;
    Shader mShader;
    public int getMilkPercent() {
        return milkPercent;
    }

    private int milkPercent=4;
    private float mScale = 0.8f;

    private Context mContext;

    public cupView(Context context) {
        super(context);
        mContext =context;
        Init();
    }

    public cupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext =context;
        Init();
    }

    public cupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext =context;
        Init();
    }

    public void setViewBackGround(Bitmap viewId) {
        m_bitmapbg = viewId;
    }
    public void SetScale(float value)
    {
        if(value>=1.2f)
        {
            value =1.2f;
        }
        if(value<=0.8f)
        {
            value =0.8f;
        }
        mScale = value;
        adjustMilk(milkPercent);
    }

    boolean iscupready =false;
    public void adjustMilk(int percent)
    {
        if(percent>=7)
        {
            percent =7;
        }
        if(percent<=4)
        {
            percent=4;
        }
        milkPercent = percent;
        if(m_bitmapmilk!=null)
        {
            if (m_bitmapmilk != null) {
                iscupready =true;
                m_bitmapmilk = Bitmap.createScaledBitmap(m_bitmapmilk, (int) (mRectFWidth*mScale), (int) (mRectFHeight*mScale), true);
            }
        }
        postInvalidate();
    }
    private void Init()
    {
        mpaint =new Paint();
        mpaint.setAntiAlias(true);
        mLeftPadding = AndroidUtils_Ext.dp2px(10, getContext());
        mTopPadding = AndroidUtils_Ext.dp2px(10, getContext());
        mRightPadding = AndroidUtils_Ext.dp2px(10, getContext());
        mBottomPadding = AndroidUtils_Ext.dp2px(10, getContext());
        //m_bitmapbg = BitmapFactory.decodeResource(getResources(), R.mipmap.coffee);
        m_bitmapmilk= BitmapFactory.decodeResource(getResources(), R.mipmap.milk);
        if (m_bitmapbg != null) {
            mSrcRect = new Rect(0, 0, m_bitmapbg.getWidth(), m_bitmapbg.getHeight());
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitMapBackGround(canvas);
        drawBitMapcup(canvas);
    }

    private void drawBitMapBackGround(Canvas canvas) {
        if (m_bitmapbg != null) {
            mDstRect = new Rect(0,0, (int) (mRectFWidth *mScale), (int) (mRectFHeight*mScale));
            canvas.drawBitmap(m_bitmapbg, mSrcRect, mDstRect, null);
        }
    }

    private void drawBitMapcup(Canvas canvas) {
        if (m_bitmapmilk != null &&iscupready) {
            BitmapShader mBitmapShader = new BitmapShader(m_bitmapmilk, Shader.TileMode.REPEAT,
                    Shader.TileMode.CLAMP);
            mpaint.setShader(mBitmapShader);
            canvas.drawOval(new RectF(0, 0, mRectFWidth*mScale,
                    mRectFHeight*milkPercent*mScale/MAX_VALUE), mpaint);
        }
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mRectFWidth = w;
        mRectFHeight = h;
        if (m_bitmapbg != null) {
            mSrcRect = new Rect(0, 0, m_bitmapbg.getWidth(), m_bitmapbg.getHeight());
        }
    }
}
