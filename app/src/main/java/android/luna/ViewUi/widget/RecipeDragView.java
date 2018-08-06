package android.luna.ViewUi.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import evo.luna.android.R;

/**
 * 拖动距离与时间之间的关系
 * 
 * 总长度：420 px 时间为：90 second 那么每420/90(s)为1秒钟的长度
 * 
 * @author wujiayi
 *
 */

public class RecipeDragView extends TextView {

	// private final static String TAG = "ChangeBaseRecipeView";

	/**
	 * x轴可滑动的最大坐标值
	 */
	public static final int XAXIS_MAX = 420;
	/**
	 * 设置默认时间60s，计算坐标与时间的阀值是420/120
	 */
	public static final float VALVE = XAXIS_MAX / 65.0f;
	private int mAction; // 定义一个记录当前动作的变量
	private int currentX = 0; // 初始化当前控件的位置
	private int previousX = 0; // 记录上一次控件的位置
	private int mTop = 0, mLeft = 0;
	private int previousLeft; // 记录上一次控件的位置，判断是滑动还是双击
	private final long clickSpace = 1000;// 处理双击事件
	private long firstClickTime;

	private Handler longHandler = new Handler();
	private boolean longFlag = false;
	private boolean dragFlag = false;

	public RecipeDragView(Context context) {
		super(context, null);
	}

	public RecipeDragView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public interface DragCallBackListener {
		public void onDragChangeListener(float value, boolean isDrag);
	}

	public interface DoubleClickListener {
		public void onDoubleClickListener(View v);
	}

	public interface CloseClickListener {
		public void onCloseClickListener(View v);
	}

	DragCallBackListener dragCallBackListener;
	DoubleClickListener doubleClickListener;
	CloseClickListener closeClickListener;

	public void setOnDragChangeListener(DragCallBackListener dragCallBack) {
		this.dragCallBackListener = dragCallBack;
	}

	public void setOnDoubleClickListener(DoubleClickListener doubleClick) {
		this.doubleClickListener = doubleClick;
	}

	public void setOnCloseClickListener(CloseClickListener closeClick) {
		this.closeClickListener = closeClick;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mAction = event.getAction(); // 获取当前动作
		currentX = (int) event.getX(); // 获取当前X坐标
		// currentY = (int) event.getY(); // 获取当前Y坐标
		switch (mAction) {
		case MotionEvent.ACTION_DOWN: // 按下控件时
			previousX = currentX; // 记录上次X,Y坐标
			previousLeft = getLeft();
			longFlag = true;
			// 1秒后来判断
			if (longHandler != null) {
				longHandler.removeCallbacks(runnable);
			}
			longHandler.postDelayed(runnable, 1000);
			break;
		case MotionEvent.ACTION_MOVE: // 开始移动控件
			int detelX = currentX - previousX;
			if (dragFlag) {
				mTop = getTop(); // 控件未移动前距离父窗口顶部的距离
				mLeft = getLeft(); // 控件未移动前距离父窗口左边的距离
				 if (mLeft < 0 || (detelX + mLeft)<0) {
						detelX = 0;
						mLeft = 0;
						this.layout(0, mTop, getWidth(), mTop + getHeight());
				 }
				if (detelX != 0) {
					// 重新指定控件的位置和大小
					this.layout(detelX + mLeft, mTop, detelX + mLeft + getWidth(), mTop + getHeight());
				}
				previousX = currentX - detelX; // 记录上一次的X，Y坐标
				// 通知界面开始更改时间
				if (mLeft >= 0) {
					dragCallBackListener.onDragChangeListener(mLeft / VALVE, true);
				}
			}
			break;

		case MotionEvent.ACTION_UP:
			longFlag = false;
			dragFlag = false;
			setBackgroundColor(getResources().getColor(R.color.title_text_color));
			long clickTime = System.currentTimeMillis() - firstClickTime;
			if (clickTime > clickSpace) {
				firstClickTime = System.currentTimeMillis();
			} else if (clickTime < clickSpace && !isMove()) {
				doubleClickListener.onDoubleClickListener(this);
				return true;
			}

			// 限制拖动边界
			if (mLeft <= 0) {
				detelX = 0;
				mLeft = 0;
				this.layout(0, mTop, getWidth(), mTop + getHeight());
			} else if (getLeft() > XAXIS_MAX) {
				mLeft = XAXIS_MAX;
				this.layout(mLeft, getTop(), mLeft + getWidth(), getTop() + getHeight());
			}
			if (isMove()) {
				dragCallBackListener.onDragChangeListener(mLeft / VALVE, false);
				mTop = getTop(); // 控件未移动前距离父窗口顶部的距离
				mLeft = getLeft(); // 控件未移动前距离父窗口左边的距离
			}
			break;
		}
		return true;
	}

	private Runnable runnable = new Runnable() {
		public void run() {
			if (longFlag) {
				dragFlag = true;
				setBackgroundColor(getResources().getColor(R.color.edit_recipe_down_color));
			}
		}
	};

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	public void setRecipeWidth(double width) {
		int iWidth = 100;
		//// TODO: 2018/2/26 set the min width
		if (width <= 20) {

		}else{
			iWidth = (int) (width * VALVE);
		}
		
		ViewGroup.LayoutParams params = this.getLayoutParams();
		params.width = iWidth;
		this.setLayoutParams(params);
	}

	/**
	 * 判断是否移动
	 * 
	 * @return
	 */
	private boolean isMove() {
		if (Math.abs(previousLeft - mLeft) < 3) {
			return false;
		}
		return true;
	}
}
