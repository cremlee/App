package android.luna.ViewUi;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class myViewFlipper extends ViewFlipper
{
	public myViewFlipper(Context context) {
		super(context);
	}

	public myViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private GestureDetector gestureDetector =null;
	public void setmy(GestureDetector a)
	{
		gestureDetector =a;
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev)
	{
		if(gestureDetector!=null)
		  return this.gestureDetector.onTouchEvent(ev);
		return false;
	}
}
