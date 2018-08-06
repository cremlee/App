package android.luna.ViewUi.widget;

import java.sql.SQLException;

import android.content.Context;
import android.content.Intent;
import android.luna.Activity.Base.Constant;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.FilterBrewStepDao;
import android.luna.Data.module.*;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import evo.luna.android.R;

public class RecipeEditWidget extends LinearLayout {

	private static final String TAG = "RecipeEditWidget";
	private Context mContext;
	private View view;
	private LinearLayout rightLayout;
	private TextView recipeName;
	private TextView recipeParam;
	private ImageView recipeClose;
	private TextView recipeStartTime;
	private RecipeDragView dragView;

	private String name;
	private float totalVolume;
	private float totalTime;
	private float startTime;
	
	private BeverageIngredient beverageIngredient;
	private IngredientFilterBrew filterBrew;
	private IngredientFilterBrewAdvance filterBrewAdvance;
	private IngredientInstant instant;
	private IngredientWater water;
	private IngredientMilk milk;
	private IngredientEspresso _espresso;
//	private int machineType = App.MACHINE_TYPE_MF13;
	//private DataHelper dataHelper;
	private BeverageFactoryDao mbeverageFactoryDao=null;

	public BeverageIngredient getBeverageIngredient() {
		return beverageIngredient;
	}

	public RecipeEditWidget(Context context) {
		super(context, null);
		this.mContext = context;

	}

	public RecipeEditWidget(Context context, int id) {
		super(context);
		this.mContext = context;
		initViews();
		setId(id);
		mbeverageFactoryDao = new BeverageFactoryDao(mContext ,(CremApp)mContext.getApplicationContext());
	}

	public RecipeEditWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		initViews();
	}

	public TextView getRecipeParam() {
		return recipeParam;
	}

	private float startpos =0;
	private FilterBrewStepDao filterBrewStepDao;
	
	private void initViews() {
		filterBrewStepDao = new FilterBrewStepDao(this.mContext,(CremApp) this.mContext.getApplicationContext());
		view = LayoutInflater.from(mContext).inflate(R.layout.layout_change_base_recipe_view, this, true);
		rightLayout =  view.findViewById(R.id.rightLayout);

		recipeName =  view.findViewById(R.id.changeBaseRecipeName);
		recipeParam = view.findViewById(R.id.changeBaseRecipeParam);
		recipeClose = view.findViewById(R.id.changeBaseRecipeClose);
		recipeStartTime = view.findViewById(R.id.changeBaseRecipeStartTime);
		dragView =  view.findViewById(R.id.recipeDragView);
		setDragView(dragView);
		// 设置宽度
		ViewGroup.LayoutParams layoutParams = dragView.getLayoutParams();
		layoutParams.width = 150;
		dragView.setLayoutParams(layoutParams);

		// 设置参数
		recipeName.setText(name);
		recipeStartTime.setText(mContext.getResources().getString(R.string.start_time, startTime));
		rightLayout.setClickable(true);
		rightLayout.setOnTouchListener(new OnTouchListener(){

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int moveaction =event.getAction();
				switch(moveaction)
				{
					case MotionEvent.ACTION_DOWN:
						startpos = event.getX();
						break;
					case MotionEvent.ACTION_UP:
						if((event.getX() - startpos)>50)
						{
							beverageIngredient.setStartTime(beverageIngredient.getStartTime()+100);
							recipeStartTime.setText(mContext.getResources().getString(R.string.start_time, beverageIngredient.getStartTime()/1000f));
							mbeverageFactoryDao.getBeverageIngerdient().update(beverageIngredient);
							sendMyBroadcast(beverageIngredient,true);
						}
						if((event.getX() - startpos)<-50)
						{
							beverageIngredient.setStartTime(beverageIngredient.getStartTime()-100);
							recipeStartTime.setText(mContext.getResources().getString(R.string.start_time, beverageIngredient.getStartTime()/1000f));
							mbeverageFactoryDao.getBeverageIngerdient().update(beverageIngredient);
							sendMyBroadcast(beverageIngredient,true);
						}
						break;
				}
				return false;
			}} );
		
		dragView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction() == MotionEvent.ACTION_CANCEL)
				{
					//return true;
				}
				return false;
			}
		});
		dragView.setOnDragChangeListener(new RecipeDragView.DragCallBackListener() {

			@Override
			public void onDragChangeListener(float value, boolean isDrag) {
				rightLayout.scrollTo(0, 0);
				recipeStartTime.setText(mContext.getResources().getString(R.string.start_time, value));
				beverageIngredient.setStartTime((int) (value * 1000));
				if (!isDrag) {
					mbeverageFactoryDao.getBeverageIngerdient().update(beverageIngredient);
				}
				sendMyBroadcast(beverageIngredient,false);
			}
		});
		
	}
	
	private void sendMyBroadcast(BeverageIngredient bi,boolean isrefresh){
		if(isrefresh)
		{
			setStartTime(beverageIngredient.getStartTime()/1000.0f);
			new Handler().post(new Runnable() {

				@Override
				public void run() {
					int x = -(int) (getStartTime() * RecipeDragView.VALVE);
					rightLayout.scrollTo(x, 0);
				}
			});
		}
		final Intent intent = new Intent(Constant.ACTION_CHANGE_POSITION);
		intent.putExtra("extra", bi);
		mContext.sendBroadcast(intent);
	}

	public float getTotalVolume() {
		return totalVolume;
	}

	public void setTotalVolume(float totalVolume) {
		this.totalVolume = totalVolume;
	}

	public ImageView getRecipeClose() {
		return recipeClose;
	}

	public RecipeDragView getDragView() {
		return dragView;
	}

	public void setDragView(RecipeDragView dragView) {
		this.dragView = dragView;
	}

	public float getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(float totalTime) {
		this.totalTime = totalTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		if (recipeName != null) {
			recipeName.setText(name);
		}
	}

	public float getStartTime() {
		return startTime;
	}

	public void setStartTime(float startTime) {
		this.startTime = startTime;
		if (recipeStartTime != null) {
			recipeStartTime.setText(mContext.getResources().getString(R.string.start_time, startTime));
		}
	}

	public void setBaseValue(BeverageIngredient pub, Object ingredient) {

		beverageIngredient = pub;
		int ingredientType = pub.getIngredientType();
		float scaleUp = pub.getScaleUp()/100.0f;
		switch (ingredientType) {
		case Ingredient.TYPE_FILTER_BREW_ADVANCE:
			filterBrewAdvance = (IngredientFilterBrewAdvance)ingredient;
			name = filterBrewAdvance.getName();
			float[] filteradvValues = CremApp.calcFilterBrewAdvanceValue(filterBrewAdvance, filterBrewStepDao.getFilterBrewStep(filterBrewAdvance.getPid()));
			totalTime = filteradvValues[0];
			totalVolume = filteradvValues[1]*scaleUp;
			break;
		case Ingredient.TYPE_FILTER_BREW:
			filterBrew = (IngredientFilterBrew) ingredient;
			name = filterBrew.getName();			
//			float[] filterValues = CalcRecipeValueUtils.calcFilterBrewValue(filterBrew,App.getMachineType());
			float[] filterValues = CremApp.calcFilterBrewValue(filterBrew);
			totalTime = filterValues[0];
			totalVolume = filterValues[1]*scaleUp;
			break;
		case Ingredient.TYPE_INSTANT:
			instant = (IngredientInstant) ingredient;
			name = instant.getName();
			float[] instantValues = CremApp.calcInstantValue(instant);
			totalTime = instantValues[0]*scaleUp+(instant.getPauseTimeAfterDispense()+instant.getPreflushPauseTime()+Constant.MIX_DELAY_TM)/1000.0f;
			totalVolume = instantValues[1]*scaleUp;
			break;
		case Ingredient.TYPE_WATER:
			water = (IngredientWater) ingredient;
			name = water.getName();
			float[] waterValues = CremApp.calcWaterValue(water);
			totalTime = waterValues[0]*scaleUp;
			totalVolume = waterValues[1]*scaleUp;
			break;
		case Ingredient.TYPE_MILK:
			milk = (IngredientMilk) ingredient;
			name = milk.getName();
			float[] milkValues = CremApp.calcMilkValue(milk);
			totalTime = milkValues[0]*scaleUp;
			totalVolume = milkValues[1]*scaleUp;
			break;
		case Ingredient.TYPE_ESPRESSO:
			_espresso = (IngredientEspresso)ingredient;
			name = _espresso.getName();
			totalTime = (_espresso.getBrewtime()+_espresso.getPreinfusiontime()+_espresso.getPrebrewtime())/10;
			totalVolume = _espresso.getWatervolume()*scaleUp;
			break;
		default:
			break;
		}
		setName(name);
		setTotalVolume(totalVolume);
		setTotalTime(totalTime);
		recipeParam.setText(getResources().getString(R.string.change_base_recipe_param, totalTime, totalVolume));
		setStartTime(pub.getStartTime() / 1000.0f);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				int x = -(int) (getStartTime() * RecipeDragView.VALVE);
				rightLayout.scrollTo(x, 0);
			}
		});
	}

}
