package android.luna.ViewUi.DragUi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lee on 2016/8/26.
 */
public class FreeSizeDraggableLayout extends ViewGroup {
    final String TAG = "FreeSizeDraggableLayout";
    final int INVALID_POSITION = -1;
    private int mUnitWidthNum = 5;
    private int mUnitHeightNum = 2;
    private int mUnitWidth;
    private int mUnitHeight;
    private List<DetailView> listViews;
    public List<DetailView> getListViews() {
		return listViews;
	}
	private int mClickX;
    private int mClickY;    
    private int m_IconSize;
    private ImageView mDragImageView;
    private int mPoint2ItemLeft;
    private int mPoint2ItemTop;
    private int mOffset2Left;
    private int mOffset2Top;
    private int mStatusHeight;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowLayoutParams;
    private int mViewPadding = 1;
    //private int mPressedItem = INVALID_POSITION;
    //private int moldItem = INVALID_POSITION;
    //private int mnewItem = INVALID_POSITION;
    private boolean mGroupChangeEnable = true;
    private long mResponseTime = 1200;
    private Handler mHandler = new Handler();
    private boolean mPress = false;
    private Vibrator mVibrator;
    
    
    
    private PressItem mPressedItem =new PressItem();
    private PressItem moldItem = null;
    private PressItem mnewItem = null;
    
    private int listnumber=0;
    
    private ArrayList<List<DetailView>> _listManager = new ArrayList<List<DetailView>>();
    HashMap<Integer,Integer> reftable1 =new HashMap<Integer,Integer>();
    public void clearlistManager(List<DetailView> a)
    {
   	 _listManager.clear();
   	 
    }
     public void addtolistManager(List<DetailView> a)
     {
    	 _listManager.add(a);
    	 
     }
      
     public void changelistsource(int i)
     {
    	 if(i<0)
    		 i=0;
    	if(i>_listManager.size()-1)
    		i=_listManager.size()-1;	 
    	 listnumber =i;
    	 this.setList(_listManager.get(i));
     }
    MyDragListener dragCallBackListener=null;    
    public interface MyDragListener {
		public void onDragFinishedListener(HashMap<Integer, Integer> a);
	}       
    public void setonDragFinishedListener(MyDragListener i)
    {
    	dragCallBackListener =i;
    }
    
    MyDragMoveLister dragmovelistercallback=null;
    public interface MyDragMoveLister
    {
    	public void onDragMoveListener(int a);
    }
    public void setonDragMoveListener(MyDragMoveLister i)
    {
    	dragmovelistercallback =i;
    }
    
    private Runnable mPressHandler = new Runnable() {
        @Override
        public void run() {
            mPress = true;
            mVibrator.vibrate(50);  
            if(mPressedItem.getIconindex()!=INVALID_POSITION)
            {
	            createPressImageView(listViews.get(mPressedItem.getIconindex()).getView(), mClickX, mClickY);
	            View viewPress = getChildAt(mPressedItem.getIconindex());
	            viewPress.setVisibility(View.INVISIBLE);
            }
            
        }
    };

    public FreeSizeDraggableLayout(Context context) {
        this(context, null);
    }

    public FreeSizeDraggableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FreeSizeDraggableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(getContext());
    }

    private int getitembybeginpoint(Point p)
    {
    	
    	int i = INVALID_POSITION;
        for (DetailView view : listViews) {
            
            if (view.getPoint().x==p.x && view.getPoint().y==p.y ) {
                i = listViews.indexOf(view);
                break;
            }
        }
        return i;
    	
    }
    @SuppressLint("UseSparseArrays")
    private HashMap<Integer,Integer> reftable =new HashMap<Integer,Integer>();
    public HashMap<Integer,Integer> getreftable()
    {
    	reftable.clear();
    	
    	int minid = getminsortid();
    	int pidvalue =0;
    	int sidvalue =minid;
    	int itemindex =INVALID_POSITION;
    	for(int i = 0;i < mUnitHeightNum;i++)
    	{
			for(int j = 0;j < mUnitWidthNum;j++)
			{
				itemindex = getitembybeginpoint(new Point(j,i));
				if(itemindex!=INVALID_POSITION)
				{
					pidvalue =listViews.get(itemindex).getPid();
					if(reftable.containsKey(pidvalue))
					continue;
					else
					{
						reftable.put(pidvalue,sidvalue++);
					}
				}
			}
    	}
    	return  reftable;
    }
    private int getminsortid()
    {
    	int ret =0xff;
    	for(DetailView item :listViews)
    	{
    		if(item.getIdSort()!=0 && ret>item.getIdSort())
    			ret = item.getIdSort();
    	}
    	return ret;
    }
    private boolean isborder=false;
    private boolean needchange =false;
    private long lasttm=0;
    private boolean canchange =false;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            	mPressedItem.setListindex(listnumber);
                mPressedItem.setIconindex(getClickedItem(new Point((int) ev.getX(), (int) ev.getY())));
                if (mPressedItem.getIconindex() == INVALID_POSITION || listViews.get(mPressedItem.getIconindex()).getPid()==0)
                    return super.dispatchTouchEvent(ev);
                canchange = listViews.get(mPressedItem.getIconindex()).getWidthNum() ==1?true:false;
                mHandler.postDelayed(mPressHandler, mResponseTime);
                mClickX = (int) ev.getX();
                mClickY = (int) ev.getY();
                mPoint2ItemTop = mClickY - listViews.get(mPressedItem.getIconindex() ).getView().getTop();
                mPoint2ItemLeft = mClickX - listViews.get(mPressedItem.getIconindex() ).getView().getLeft();
                m_IconSize=listViews.get(mPressedItem.getIconindex() ).getView().getWidth();
                mOffset2Top = (int) (ev.getRawY() - mClickY);
                mOffset2Left = (int) (ev.getRawX() - mClickX);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mPress) {
                    mWindowLayoutParams.x = (int) ev.getX() - mPoint2ItemLeft + mOffset2Left;
                    mWindowLayoutParams.y = (int) ev.getY() - mPoint2ItemTop + mOffset2Top - mStatusHeight;
                    if(canchange)
                    {
	                    if(ev.getX()<50 || ev.getX()>700)
	                    {
	                    	long time = System.currentTimeMillis();
	                    	if(!isborder)
	                    	{
	                    		lasttm = System.currentTimeMillis();
	                    		isborder =true;
	                    	}
	                    	if((time - lasttm>1500) )
	                    	{
	                    		lasttm = System.currentTimeMillis();
	                    		if(ev.getX()<50)
	                    			changelistsource(listnumber-1);
	                    		else
	                    			changelistsource(listnumber+1);	
	                    		needchange =true; 
	                    		
	                    	}
	                    }else
	                    {
	                    	isborder =false;
	                    }
                    }
                    mWindowManager.updateViewLayout(mDragImageView, mWindowLayoutParams);
                    PressItem iPrepareChangePosition = new PressItem();
                    iPrepareChangePosition.setListindex(listnumber);
                    iPrepareChangePosition.setIconindex(getClickedItem(new Point((int) ev.getX(), (int) ev.getY())));

                    if (iPrepareChangePosition.getIconindex() != INVALID_POSITION &&_listManager.get(iPrepareChangePosition.getListindex()).get(iPrepareChangePosition.getIconindex()).getPid()!=0 && (iPrepareChangePosition.getIconindex()!=mPressedItem.getIconindex() || iPrepareChangePosition.getListindex()!=mPressedItem.getListindex())){
                    	if(_listManager.get(iPrepareChangePosition.getListindex()).get(iPrepareChangePosition.getIconindex()).bSameSize(_listManager.get(mPressedItem.getListindex()).get(mPressedItem.getIconindex())))
                    	{
                    		moldItem = mPressedItem;
                    		mnewItem = iPrepareChangePosition;
                    		changePositionInList(iPrepareChangePosition, mPressedItem);
                           if(dragCallBackListener!=null)
                    		dragCallBackListener.onDragFinishedListener(getreftable());          
                        }
                    	else if (mGroupChangeEnable) {
                            List<Integer> ilistChange = ChangeableViewGroupExist(iPrepareChangePosition.getIconindex(), mPressedItem.getIconindex());
                            if (ilistChange.size() != 0) {
                            	moldItem = mPressedItem;
                            	mnewItem = iPrepareChangePosition;
                                changeGroupPos(mPressedItem.getIconindex(), iPrepareChangePosition.getIconindex(), ilistChange);            
                            }                                             
                    }     
                    	DrawViewsAtList();               
                    }
                    return true;
                   }
                else
                {
                	if(mClickX==0)
                		return false;
                	if (mClickX - ev.getX() > 120)  
			        {  
			           // 添加动画  
                		mClickX =0;
                		mHandler.removeCallbacks(mPressHandler);
                		mPress =false;
                        if(dragCallBackListener!=null)
                		dragmovelistercallback.onDragMoveListener(-1);
			           
			        }// 从右向左滑动  
			        else if (mClickX - ev.getX() < -120)  
			        {  
			        	mClickX =0;
			        	mHandler.removeCallbacks(mPressHandler);
			        	mPress =false;
                        if(dragCallBackListener!=null)
			        	dragmovelistercallback.onDragMoveListener(1);
			             
			        } 
                	return false;
                }
		case MotionEvent.ACTION_UP:
                mHandler.removeCallbacks(mPressHandler);
                if (mPress) {              	
                    mWindowManager.removeView(mDragImageView);                    
                    View viewPress =_listManager.get(mPressedItem.getListindex()).get(mPressedItem.getIconindex()).getView();//getChildAt(mPressedItem.getIconindex()); //_listManager.get(mPressedItem.getListindex()).get(mPressedItem.getIconindex()).getView();
                    if(viewPress!=null)
                    {
	                    viewPress.setVisibility(View.VISIBLE);
	                    viewPress.invalidate();
                    }
                    mPress = false;
                    if(moldItem!=mnewItem)
                    {
                        if(dragCallBackListener!=null)
                    	dragCallBackListener.onDragFinishedListener(getreftable());                   
                    }
                    return false;
                    
                }
                break;
        }
        return  super.dispatchTouchEvent(ev);
    }
    
    private void changeGroupPos(int iPress, int iPrepare, List<Integer> iTotal) {
        Point pPress = listViews.get(iPress).getPoint();
        Point pPrepare = listViews.get(iPrepare).getPoint();

        int ixPress = pPress.x;
        int iyPress = pPress.y;

        int iWidth = listViews.get(iPress).getWidthNum();
        int iHeight = listViews.get(iPress).getHeightNum();

        int ixPrepare = pPrepare.x;
        int iyPrepare = pPrepare.y;

        DetailView detailView = listViews.get(iPress);
        if(ixPrepare>mUnitWidthNum-2 )
        	ixPrepare=mUnitWidthNum-2;
        if(iyPrepare>mUnitWidthNum-2 )
        	iyPrepare=mUnitWidthNum-2;
        if(ixPrepare<0 )
        	ixPrepare=0;
        if(iyPrepare<0 )
        	iyPrepare=0;
        detailView.setPoint(new Point(ixPrepare, iyPrepare));
        listViews.set(iPress, detailView);
        for (int i = 0; i < iTotal.size(); ++i) {
            int iViewWaitChange = iTotal.get(i);
            detailView = listViews.get(iViewWaitChange);
            Point pTemp = detailView.getPoint();
            if (pTemp.x < ixPress || pTemp.x >= ixPress + iWidth) {
                if (pTemp.x < ixPress) {
                    pTemp.x += iWidth;
                } else
                    pTemp.x -= iWidth;
            }
            if (pTemp.y < iyPress || pTemp.y >= iyPress + iHeight) {
                if (pTemp.y < iyPress)
                    pTemp.y += iHeight;
                else
                    pTemp.y -= iHeight;
            }
            if(pTemp.x>mUnitWidthNum-1 )
            	pTemp.x=mUnitWidthNum-1;
            if(pTemp.y>mUnitWidthNum-1 )
            	pTemp.y=mUnitWidthNum-1;
            if(pTemp.x<0 )
            	pTemp.x=0;
            if(pTemp.y<0 )
            	pTemp.y=0;
            detailView.setPoint(pTemp);
        }
    }
    private void DrawViewsAtList() {
        for (int i = 0; i < listViews.size(); ++i) {
            DetailView dvView = listViews.get(i);
            View vChild = getChildAt(i);
            int iL = dvView.getPoint().x * mUnitWidth;
            int iT = dvView.getPoint().y * mUnitHeight;
            int iR = iL + dvView.getWidthNum() * mUnitWidth;
            int iB = iT + dvView.getHeightNum() * mUnitHeight;

            vChild.setLeft(iL + mViewPadding);
            vChild.setTop(iT + mViewPadding);
            vChild.setRight(iR - mViewPadding);
            vChild.setBottom(iB - mViewPadding);
        }
    }
    private List<Integer> ChangeableViewGroupExist(int iCheckingItem, int iDraggingItem) {
        List<Integer> list = new ArrayList<>();
        int xOri = listViews.get(iCheckingItem).getPoint().x; 
        int yOri = listViews.get(iCheckingItem).getPoint().y;
        if(listViews.get(iDraggingItem).getWidthNum()>1)  
        {
        	xOri = (xOri>2?xOri-1:xOri);
        	yOri = (yOri>2?yOri-1:yOri); 
        }
        int xDes = xOri + listViews.get(iDraggingItem).getWidthNum();
        int yDes = yOri + listViews.get(iDraggingItem).getHeightNum();

        int iArea = 0;
        for (int x = 0; x < listViews.size(); ++x) {
            DetailView detailView = listViews.get(x);
            Point pBegin = detailView.getPoint();
            Point pEnd;
             pEnd = new Point(pBegin.x + detailView.getWidthNum(), pBegin.y + detailView.getHeightNum());
            if (pBegin.x >= xOri && pBegin.y >= yOri && pEnd.x <= xDes && pEnd.y <= yDes) {
                list.add(x);
                iArea += detailView.getHeightNum() * detailView.getWidthNum();
            }
        }
        if (iArea != listViews.get(iDraggingItem).getHeightNum() * listViews.get(iDraggingItem).getWidthNum()) {
            	list.clear();
        }
        return list;
    }
    private void createPressImageView(View v, int x, int y) {
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;

        mWindowLayoutParams.x = x - mPoint2ItemLeft + mOffset2Left;
        mWindowLayoutParams.y = y - mPoint2ItemTop + mOffset2Top - mStatusHeight;
        mWindowLayoutParams.alpha = 0.5f;
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mDragImageView = new ImageView(getContext());
        v.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        mDragImageView.setImageBitmap(bitmap);
        v.destroyDrawingCache();
        if(listViews.get(mPressedItem.getIconindex()).getIdSort()==0 )
        {
        	mDragImageView.setVisibility(View.GONE);
        }
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
    }
    public void setList(List<DetailView> list) {
        listViews = list;
        removeAllViews();
        for (DetailView v : listViews) {
            addView(v.getView());
        }
        postInvalidate();
    }
    public void setUnitWidthNum(int i) {
        mUnitWidthNum = i;
    }
    public void setUnitHeightNum(int i) {
        mUnitHeightNum = i;
    }
    private void changePositionInList(int i, int j) {
        Point p = listViews.get(i).getPoint();
        listViews.get(i).setPoint(listViews.get(j).getPoint());
        listViews.get(j).setPoint(new Point(p.x, p.y));
    }
    
    
    
    private void changePositionInList(PressItem i, PressItem j) {      
    	 Point p = listViews.get(i.getIconindex()).getPoint();
    	 Point p1 = _listManager.get(j.getListindex()).get(j.getIconindex()).getPoint();
    	 int sortid =  listViews.get(i.getIconindex()).getIdSort();
    	 int sortid1 =_listManager.get(j.getListindex()).get(j.getIconindex()).getIdSort();
        if((i.getListindex()!=j.getListindex())  && needchange)
        {
        	reftable1.clear();	
        	reftable1.put(listViews.get(i.getIconindex()).getPid(),sortid1);
            if(dragCallBackListener!=null)
        	dragCallBackListener.onDragFinishedListener(reftable1);  
        	//jiaohuan page        	
        	DetailView temp = new DetailView();
        	temp.copy(listViews.get(i.getIconindex()));
        	listViews.get(i.getIconindex()).copy(_listManager.get(j.getListindex()).get(j.getIconindex()));
        	listViews.get(i.getIconindex()).setIdSort(sortid);
        	listViews.get(i.getIconindex()).setPoint(new Point(p.x,p.y));
        	
        	_listManager.get(i.getListindex()).get(i.getIconindex()).copy(_listManager.get(j.getListindex()).get(j.getIconindex()));
        	_listManager.get(i.getListindex()).get(i.getIconindex()).setIdSort(sortid);
        	_listManager.get(i.getListindex()).get(i.getIconindex()).setPoint(new Point(p.x,p.y));
        	
        	_listManager.get(j.getListindex()).get(j.getIconindex()).copy(temp);
        	_listManager.get(j.getListindex()).get(j.getIconindex()).setIdSort(sortid1);
        	_listManager.get(j.getListindex()).get(j.getIconindex()).setPoint(new Point(p1.x,p1.y));
        	mPressedItem.setListindex(listnumber);
        	mPressedItem.setIconindex(i.getIconindex());
        	
        	changelistsource(listnumber);
        	View viewPress = getChildAt(mPressedItem.getIconindex());
            viewPress.setVisibility(View.INVISIBLE);
        	needchange=false;
        }
        else
        {
         _listManager.get(i.getListindex()).get(i.getIconindex()).setPoint(new Point(p1.x,p1.y));
	     _listManager.get(j.getListindex()).get(j.getIconindex()).setPoint(new Point(p.x,p.y));
        }
        

    }
    
    private int getClickedItem(Point p) {
        int i = INVALID_POSITION;
        for (DetailView view : listViews) {
            View v = view.getView();
            Rect rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            if (rect.contains(p.x, p.y)) {
                i = listViews.indexOf(view);
                break;
            }
        }
        return i;
    }
    public void setsubViewPadding(int i) {
        mViewPadding = i;
    }
    /*private int mLeftWidth;
    private int mRightWidth;
    private final Rect mTmpContainerRect = new Rect();
    private final Rect mTmpChildRect = new Rect();*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //set the unit size
        mUnitWidth = MeasureSpec.getSize(widthMeasureSpec) / mUnitWidthNum;
        mUnitHeight = MeasureSpec.getSize(heightMeasureSpec) / mUnitHeightNum;
        measureChildren(widthMeasureSpec, heightMeasureSpec);   	
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int iChildCount = getChildCount();
        for (int i = 0; i < iChildCount; ++i) {
            //set child view's layout with padding
            DetailView dvView = listViews.get(i);
            View vChild = getChildAt(i);
            int iL = dvView.getPoint().x * mUnitWidth;
            int iT = dvView.getPoint().y * mUnitHeight;
            int iR = iL + dvView.getWidthNum() * mUnitWidth;
            int iB = iT + dvView.getHeightNum() * mUnitHeight;
            
            vChild.layout(iL + mViewPadding, iT + mViewPadding, iR - mViewPadding, iB - mViewPadding);
        }
    }
    private static int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
    public void setGroupChangeEnable(Boolean b) {
        mGroupChangeEnable = b;
    }
    
    public class PressItem
    {
    	private int listindex;
    	@Override
		public String toString() {
			return "PressItem [listindex=" + listindex + ", iconindex="
					+ iconindex + "]";
		}
		public int getListindex() {
			return listindex;
		}
		public void setListindex(int listindex) {
			this.listindex = listindex;
		}
		private int iconindex;
		public int getIconindex() {
			return iconindex;
		}
		public void setIconindex(int iconindex) {
			this.iconindex = iconindex;
		}
    }
}
