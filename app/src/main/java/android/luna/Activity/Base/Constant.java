package android.luna.Activity.Base;

import android.os.Environment;

public final class Constant {


	//// TODO: 2018/5/21 权限的设定
	public static final int AUTH_FIRSTLINE  = 1;
	public static final int AUTH_SECONDLINE = 2;
	public static final int AUTH_SERVICE    = 3;

	public final static float WATER_VOLUME = 20.0f;
	public static final int MIX_DELAY_TM = 3500; // 3.5 s
	public static final int BEVERAGE_DELAY_TM = 4000; // 4 s


	public static int WARN_REMOVE =0;
	public static int WARN_ADD =1;

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}

	public static class Extra {
		public static final String FRAGMENT_INDEX = "com.nostra13.example.universalimageloader.FRAGMENT_INDEX";
		public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	}


	public static class PopType
	{
		public static final int ERROR =1;
		public static final int SERVICE_PASSWORD =2;
		public static final int OPERATOR_PASSWORD =3;
		public static final int CART_ITEM =4;
	}

	public final static int MODE_MAINPAGE = 0;
	public final static int MODE_NORMAL = 1;
	public final static int MODE_OPERATE = 2;
	public final static int MODE_SERVICE = 3;
	public final static int MODE_UPDATE = 9;
	public final static int MODE_MANUFACTORY = 10;
	public final static int MODE_BOOT = 11;

	/**
	 * 加密文件
	 */
	public static final String TOK = "/scr.tok";

	private static final String BASEPATH = Environment.getExternalStorageDirectory().getPath();
	public static final String PATH_VIDEO = BASEPATH + "/evo/Video";
	public static final String PATH_LOGS = BASEPATH + "/evo/logs";
	public static final String PATH_PDF = BASEPATH + "/evo/pdf";
	public static final String PATH_BG = BASEPATH + "/evo/bg";
	public static final String PATH_INFO = BASEPATH + "/evo/info";
	public static final String PATH_SCREEN_SAVER = BASEPATH + "/evo/ScreenSaver";
	/**
	 * gif jpg png
	 */
	public static final String PATH_DRINKDISPENSINGIMAGE = BASEPATH + "/evo/DrinkDispensingImage/";
	/**
	 * Drink info picture path
	 */
	public static final String PATH_DRINKBEVERAGEIMAGE = BASEPATH + "/evo/DrinkBeverageImage/";
	/**
	 * Drink icon picture path
	 */
	public static final String PATH_ICONS = BASEPATH + "/evo/Icons/";
	/**
	 * branding image
	 */
	public static final String PATH_BRANDING = BASEPATH + "/evo/Branding/";
	/**
	 * BACKGROUND image
	 */
	public static final String PATH_BACKGROUND = BASEPATH + "/evo/Background/";
	/**
	 * Drink icon picture
	 */
	public static final int TYPE_PICTURE_ICONS = 1;
	/**
	 * Drink info picture 1
	 */
	public static final int TYPE_PICTURE_DRINK_INFO_PICTURE1 = 2;
	/**
	 * Drink info picture 2
	 */
	public static final int TYPE_PICTURE_DRINK_INFO_PICTURE2 = 3;
	/**
	 * Choose Brand
	 */
	public static final int TYPE_PICTURE_BRANDING = 4;
	/**
	 * main background Picture
	 */
	public static final int TYPE_PICTURE_MAIN_BACKGROUND = 5;
	/**
	 * sleep background picture
	 */
	public static final int TYPE_PICTURE_SLEEP_BACKGROUND = 6;
	/**
	 * screen saver
	 */
	public static final int TYPE_PICTURE_SCREENSAVER = 7;
	/**
	 * Drink Image
	 */
	public static final int TYPE_PICTURE_DRINKDISPENSINGIMAGE = 8;

	/**
	 * 更新app
	 */
	public static final int TYPE_UPDATE_APP = 0;
	/**
	 * 导出
	 */
	public static final int TYPE_UPDATE_EXPORT = 1;
	/**
	 * 导入
	 */
	public static final int TYPE_UPDATE_IMPORT = 2;
	/**
	 * 导入所有文件，包括evo目录和数据库
	 */
	public static final int TYPE_UPDATE_ALL = 1;
	/**
	 * 导入Beverage
	 */
	public static final int TYPE_UPDATE_BEVERAGE = 0;

	// **************清除操作*************//
	public static final int DAILY_CLEAN = 0;
	public static final int WEEKLY_CLEAN = 1;
	public static final int CLEAN_BREWER = 2;
	public static final int CLEAN_MIXER = 3;
	public static final int CLEAN_VALUES = 4;
	public static final int CLEAN_GRINDER = 5;

	// **************用户模式*************//
	public static final int REQ_INPUT = 0x100;
	public static final int REQ_CHOOSE_INGREDIENT = 0x102;
	public static final int REQ_DRINKICONPICUTE = 0x104;
	public static final int REQ_DRINKSTORY = 0x105;
	public static final int REQ_DRINKDISPENSE= 0x106;
	public static final int REQ_DRINKGALLERY = 0x107;



	public static final int REQ_SCR_BKG 	= 0x150;
	public static final int REQ_SCR_BRAND 	= 0x151;
	public static final int REQ_SCR_SAVER 	= 0x152;
	public static final int REQ_SCR_ECO 	= 0x153;
	public static final int REQ_SCR_CUP		= 0x154;


	//public static final int REQ_WELCOM_MESSAGE = 0x105;
	//public static final int REQ_SLEEP_MESSAGE = 0x106;
	//public static final int REQ_USE_PIN = 0x107;
	/*public static final int REQ_SERVICE_PIN = 0x108;
	public static final int REQ_BACKGROUND_PICUTE = 0x109;
	public static final int REQ_DELETE_INGREDIENT = 0x110;
	public static final int REQ_SELECT_DAY = 0x111;
	public static final int REQ_SELECT_START_TIME = 0x112;
	public static final int REQ_SELECT_END_TIME = 0x113;
	public static final int REQ_EDIT_RECIPE = 0X114;
	public static final int REQ_CLEAN_ALL = 0X115;
	public static final int REQ_BREW_STEP = 0X116;
	public static final int REQ_BREW_STEP_AD = 0X117;*/
	// public static final int REQ_MODIFY_FILTERBREW=0X114;
	// public static final int REQ_MODIFY_INSTANT=0X115;
	// public static final int REQ_MODIFY_WATER=0X116;

	/**
	 * 开门
	 */
	public final static int DOOR_OPEN = 0;
	/**
	 * 关门
	 */
	public final static int DOOR_CLOSE = 1;

	/**
	 * 转一周的固定时间为10s
	 */
	public final static int BREW_RUNNING_TIME = 10000;

	//------------------Warning ids start---------------/
	// 1 FLUSH
	public static final int WARNING_FLUSH = 0;
	// 2 BLOCK Water Heating
	public static final int WARN_WATER_HEATING = 1;
	// 3 BLOCK OverCurrent
	public static final int ERR_MOTOR_OVERCURRENT = 2;
	// 4 BLOCK Temp sensor error
	public static final int ERR_TEMP_SENSOR = 3;
	// 5 Level sensor error
	public static final int ERR_WATER_LEVEL_SENSOR = 4;
	// 6 BLOCK Low Water Level
	public static final int WARN_WATER_LOW = 5;
	// 7 BLOCK Check water supply
	public static final int ERR_WATER_SUPPLY = 6;
	// 8 BLOCK Heating time out
	public static final int ERR_TEMP_HEATING_TIMEOUT = 7;
	// 9 Door open
	// 10 Place A Cup
	public static final int WARNING_PLACE_A_CUP = 8;
	// 11 Empty drip tray
	public static final int WARN_DRIPTRAY_FULL = 9;
	// 12 Empty waste bin
	public static final int WARNING_EMPTY_WASTE_BIN = 10;
	// 13 Empty waste box
	public static final int WARNING_EMPTY_WASTE_BOX = 11;
	// 14 Daily care
	public static final int WARNING_DAILY_CARE = 12;
	// 15 Weekly clean
	public static final int WARNING_WEEKLY_CLEAN = 13;
	// 16 Check Maintenance
	public static final int WARNING_CHECK_MAINTENANCE = 14;
	// 17 Low water temp
	public static final int WARNING_LOW_WATER_TEMP = 15;

	// 18 Waste bin is full
	public static final int WARNING_WASTE_BIN_IS_FULL = 16;
	// 19 Waste bin is almost full
	public static final int WARNING_WASTE_BIN_IS_ALMOST_FULL = 17;
	// 20 Coffee is running low
	public static final int WARNING_COFFEE_IS_RUNNING_LOW = 18;
	// 21 Ingredients are running low
	public static final int WARNING_INGREDIENTS_ARE_RUNNING_LOW = 19;
	// 22 Place the cleaning container
	public static final int WARNING_PLACE_THE_CLEANING_CONTAINER = 20;
	// 23 no Drip tray
	public static final int WARN_DRIPTRAY_LOST = 21;

	public static final int WARN_WATER_TEMP_NOT_BEST = 22;// 不block

	public static final int ERR_COOL = 23;

	public static final int ERR_WATER_FILTER = 24;

	public static final int ERR_STORAGE_SAVE = 25;

	public static final int ERR_OTHER = 26;

	public static String id2String(int id) {
		switch (id) {
		case WARNING_FLUSH:
			return "Daily clean";
		case WARN_WATER_HEATING:
		case ERR_MOTOR_OVERCURRENT:
		case ERR_TEMP_SENSOR:
		case ERR_WATER_LEVEL_SENSOR:
		case WARN_WATER_LOW:
		case ERR_WATER_SUPPLY:
		case ERR_TEMP_HEATING_TIMEOUT:
		case WARN_DRIPTRAY_LOST:
			return "Machine block";
		case WARN_WATER_TEMP_NOT_BEST:
			return "Not best for coffee";
		case WARN_DRIPTRAY_FULL:
		case WARNING_EMPTY_WASTE_BIN:
		case WARNING_EMPTY_WASTE_BOX:
		case WARNING_CHECK_MAINTENANCE:
			return "Maintenance";
		case WARNING_DAILY_CARE:
			return "Daily clean";
		case WARNING_WEEKLY_CLEAN:
			return "Weekly clean";
		case WARNING_LOW_WATER_TEMP:
			return "Low water level";
		case WARNING_WASTE_BIN_IS_FULL:
			return "Maintenance";
		case WARNING_WASTE_BIN_IS_ALMOST_FULL:
			return "Maintenance";
		case WARNING_COFFEE_IS_RUNNING_LOW:
		case WARNING_INGREDIENTS_ARE_RUNNING_LOW:
			return "Canister";
		case WARNING_PLACE_THE_CLEANING_CONTAINER:
			return "Cleaning Contrainer";
		default:
			return "No Define Warning";
		}
	}
	
	//----------------actions-------------------//
	/* 应答状态 */
	public static final int ACK_STATE_CLEAN_RESERVE = 0;
	public static final int ACK_STATE_CLEAN_FINISHED = 1;
	public static final int ACK_SATTE_CLEAN_MILK_PHASE1_FINISHED = 10;
	public static final int ACK_STATE_CLEAN_CLEANING = 11;
	
	
	public static final String ACTION_UPDATE  = "evo.luna.android.UPDATE";
	
	public static final String ACTION_CHANGE_POSITION = "evo.luna.android.action.CHANGE_POSITION";

	public static final String ACTION_DOOR_STATE = "evo.luna.android.action.DOOR_STATE";
	public static final String ACTION_DRIPTRAY_STATE = "evo.luna.android.action.DRIPTRAY_STATE";
	public static final String ACTION_WASTERBIN_STATE = "evo.luna.android.action.WASTERBIN_STATE";
	public static final String ACTION_HEATER_STATE = "evo.luna.android.action.HEATER_STATE";

	public static final String ACTION_CMD_RSP_TIME_OUT = "evo.luna.android.action.CMD_RSP_TIME_OUT";

	public static final String ACTION_ERROR_CHECK_RELEASE_UI="evo.luna.android.action.ERROR_CHECK_RELEASE_UI";
	public static final String ACTION_ERROR_CHECK_BLOCK_UI="evo.luna.android.action.ERROR_CHECK_BLOCK_UI";
	/**
	 * Clean request应答
	 */
	public static final String ACTION_ACK_CLEAN_REQUEST= "evo.luna.android.action.ACK_CLEAN_ERQUEST";


	/**
	 * 清除应答
	 */
	public static final String ACTION_ACK_CLEAN_STATE = "evo.luna.android.action.ACK_CLEAN_STATE";
	/**
	 * calibration 状态
	 */
	public static final String ACTION_ACK_CALIBRATION_STATE = "evo.luna.android.action.ACK_CALIBRATION_STATE";

	public static final String ACITON_ACK_NO_DISPENSING_STATE = "evo.luna.android.action.ACK_NO_DISPENSING_STATE";
	/**
	 * 警告
	 */
//	public static final String ACTION_WARNING = "evo.luna.android.action.WARNING";
	public static final String ACTION_WARNING_LIST = "evo.luna.android.action.WARNING_LIST";
	/**
	 * 饮料制作完成
	 */
	public static final String ACTION_DISPENSE_FINISHED = "evo.luna.android.action.DISPENSE_FINISHED";
	/**
	 * Clean finished
	 */
	public static final String ACTION_CLEAN_MACHINE_FINISH = "evo.luna.android.action.CLEAN_MACHINE_FINISH";
	/**
	 * 饮料制作请求应答广播
	 */
	public static final String ACTION_MAKE_DRINK_ACK = "evo.luna.android.action.MAKE_DRINK";

	/**
	 * 饮料制作应答广播
	 */
	public static final String ACTION_MAKE_DRINK_FINISH_ACK = "evo.luna.android.action.MAKE_DRINK_FINISH";

	/**
	 * Config finished
	 */
	public static final String ACTION_CONFIG_MACHINE_FINISH = "evo.luna.android.action.CONFIG_MACHINE_FINISH";
	/**
	 *
	 */
	public static final String ACTION_TEST_ACK = "evo.luna.android.action.test";
	/**
	 * 校准完成
	 */
	public static final String ACTION_CALIBRATION_FINISH = "evo.luna.android.action.CALIBRATION_FINISH";
	
	/**
	 * 状态处理完成了
	 */
	public static final String ACTION_STATE_OP_FINISHED = "evo.luna.android.action.STATE_OP_FINISHED";
	/**
	 * 显示时间
	 */
	public static final String ACTION_TIME = "evo.luna.android.action.SHOW_TIME";
	/**
	 * MAKE DRINK
	 */
	public static final String ACTION_MAKEDRINK_ACK = "evo.luna.android.action.MAKEDRINK";
	/**
	 * Maintence
	 */
	public static final String ACTION_MAINTENCE_ACK = "evo.luna.android.action.Maintence";
	
	public static final String ACTION_MAKE_BEVERAGE_ACK = "evo.luna.android.action.Beverage";
	public static final String ACTION_MAKE_INGREDIENT_ACK = "evo.luna.android.action.Ingredient";
	
	public static final String ACTION_PAY_REQUEST_ACK = "evo.luna.android.action.pay.request";
	public static final String ACTION_PAY_HANDLE_ACK = "evo.luna.android.action.pay.handle";
	public static final String ACTION_PAY_CHARGE_QUERY_ACK = "evo.luna.android.action.pay.charge.query";
	public static final String ACTION_PAY_CHARGE_ACK = "evo.luna.android.action.pay.charge";
	public static final String ACTION_FREE_CHECK = "evo.luna.android.action.check.free";
	
	
	public static final String ACTION_RF_COFFEE_UI = "evo.luna.android.action.rf.coffee.ui";



	public static final String ACTION_WARNING_NOTIFICATION ="evo.luna.android.warningprocess";
	/**
	 * not enough water
	 */
	public static final int ACK_STATE_NO_DISPENSING_NOT_ENOUGH_WATER = 1; // 没有足够的水
	/**
	 * water temp is too low
	 */
	public static final int ACK_STATE_NO_DISPENSING_WATER_TEMP_LOW = 10; // 水温太低





	
	/* ========操作码========== */
	/**
	 * 操作码：delete
	 */
	public static final String OPCMD_DELETE = "01";
	/**
	 * 操作码：modify
	 */
	public static final String OPCMD_MODIFY = "02";
	/**
	 * 操作码：add
	 */
	public static final String OPCMD_ADD = "03";
	/**
	 * 操作码：review
	 */
	public static final String OPCMD_PREVIEW = "04";
	/**
	 * 操作码：backup
	 */
	public static final String OPCMD_BACKUP = "05";


	/* ========应答码========== */
	/**
	 * 工作模式应答
	 */
	public static final int ACK_MACHINE_CONFIG_REQUEST= 0x80;
	/**
	 * 系统匹配应答
	 */
	public static final int ACK_SYSTEM_MATCHING_REQUEST = 0x81;
	/**
	 * 机器清洗操作应答
	 */
	public static final int ACK_CLEAN_MACHINE_REQUEST = 0x82;
	/**
	 * 机器清洗停止操作应答
	 */
	public static final int ACK_STOP_CLEAN_MACHINE = 0x83;
	/**
	 * 机器清洗操作应答
	 */
	public static final int ACK_CLEAN_MACHINE_FINISH = 0x84;
	/**
	 * 饮料制作请求应答
	 */
	public static final int ACK_MAKE_DRINK_REQUEST = 0x85;
	/**
	 * 饮料制作应答
	 */
	public static final int ACK_MAKE_DRINK = 0x86;
	/**
	 * 饮料制作请求应答
	 */
	public static final int ACK_MAKE_DRINK_FINISH = 0x8F;
	/**
	 * 原料制作命令应答
	 */
	public static final int ACK_MAKE_INGREDIENT = 0x87;
	/**
	 * 菜单制作命令应答
	 */
	public static final int ACK_MAKE_BEVERAGE = 0x88;
	/**
	 * 硬件测试命令应答
	 */
	public static final int ACK_HARDWARE_TEST = 0x89;
	/**
	 * 状态查询命令应答
	 */
	public static final int ACK_STATE_QUERY = 0x8A;
	/**
	 * 机器错误获取指令应答
	 */
	public static final int ACK_MACHINE_ERR_GET = 0x8B;
	/**
	 * 机器数据设置指令应答
	 */
	public static final int ACK_MACHINE_DB_SETTING = 0x8C;
	/**
	 * 数据传输指令应答
	 */
	public static final int ACK_DATA_TRANSFER = 0x8D;
	/**
	 * 清洗操作设置指令应答
	 */
	public static final int ACK_CLEAN_SEQUENCE_SETTING = 0x8E;
	/**
	 * 机器维护参数查询命令应答
	 */
	public static final int ACK_MAINTENANCE_MSG = 0x90;
	/**
	 * 机器维护参数查询命令应答
	 */
	public static final int ACK_CALIBRATION_REQUEST = 0x91;
	/**
	 * 3.42Machine DB getting ack cmd (机器数据读取应答指令0x94)
	 */
	public static final int ACK_DB_GETTING = 0X94;
	/**
	 * 	Machine Calibration Finish Ack cmd (校验完成应答命令0x96)
	 */
	public static final int ACK_CALIBRATION_FINISH = 0x96;

	public static final int ACK_PAY_REQUEST = 0x99;
	
	public static final int ACK_PAY_HANDLE = 0xF0;
	
	public static final int ACK_PAY_CHARGE= 0x9A;
	
	public static final int ACK_QUICK_WATER= 0x9B;
	
	public static final int ACK_PAY_QUERY= 0x9C;

	public static final int ACK_ERROR_CHECK= 0x9D;

	public static final int ACK_CONFIG_FINISH = 0xA0;
	/**
	 * 数据包破损，需要重新发送
	 */
	public static final int ACK_PACKAGE_CORRUPT=0xFB;
	

	/* ========一些参数值========== */

	public static final int INVALIBLE = 0;
	public static final int VALIBLE = 1;

	public static final int LED_MODE_ALWAYS_NO = 1;
	public static final int LED_MODE_FLASH = 2;
	public static final int LED_MODE_BREATH = 3;
	public static final int LED_MODE_CIRCLING = 4;
	public static final int LED_MODE_OFF = 5;

	public static final int LED_COLOR_RED = 1;
	public static final int LED_COLOR_GREEN = 2;
	public static final int LED_COLOR_BLUE = 3;
	public static final int LED_COLOR_ORANGE = 4;
	public static final int LED_COLOR_YELLOW = 5;
	public static final int LED_COLOR_PURPLE = 6;

	public static final int LED_INTENSITY_MORMAL = 1;
	public static final int LED_INTENSITY_WEAK = 1;

	public static final int DISABLE = 0;
	public static final int ENABLE = 1;


	/**
	 * DB geting action
	 */
	public static final String ACTION_DB_SET_ACK = "evo.luna.android.action.db.set.ack";
	public static final String ACTION_DB_GET_GRINDER_CALIBRATION_VALUE = "evo.luna.android.action.db.get.grinder.calibration.value";
	public static final String ACTION_DB_GET_CANISTER_CALIBRATION_VALUE = "evo.luna.android.action.db.get.canister.calibration.value";
	public static final String ACTION_DB_GET_MACHINE_TYPE = "evo.luna.android.action.db.get.machine.type";
	public static final String ACTION_DB_GET_FIRMWARE_VERSION = "evo.luna.android.action.db.get.firmware.version";
	public static final String ACTION_DB_GET_EXTERNAL_VERSION = "evo.luna.android.action.db.get.external.version";
}
