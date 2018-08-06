package android.luna.ViewUi.FloatButton.floateutil;
import android.content.Context;
import android.luna.ViewUi.FloatButton.DragView;
import android.luna.ViewUi.FloatButton.ParamReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import evo.luna.android.R;

public class FloatBallView extends DragView implements ParamReceiver {

	public RelativeLayout getBall() {
		return ball;
	}

	private RelativeLayout ball;
	public FloatBallView(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.float_small_drag_ball, this);
		ball = findViewById(R.id.ball);
	}



	@Override
	public void onParamReceive(Bundle bundle) {

	}
}
