package android.luna.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.luna.Data.module.DrinkName;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import evo.luna.android.R;

public class AndroidUtils_Ext {

	/*
	 * S,V,M,XL,2XL,other,none 4~20
	 */
	public static String dateToString(Date data) {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(data);
	}

	private static int[][] filterswater =
			{{5400, 2250, 12900, 8100, 21000, 1000, 999999},
					{4320, 1800, 10320, 6480, 16800, 1000, 999999},
					{3600, 1500, 8600, 5400, 14000, 1000, 999999},
					{3085, 1285, 7370, 4625, 12000, 1000, 999999},
					{2700, 1125, 6450, 4050, 10500, 1000, 999999},
					{2400, 1000, 5730, 3600, 9330, 1000, 999999},
					{2160, 900, 5160, 3240, 8400, 1000, 999999},
					{1780, 740, 4255, 2670, 6925, 1000, 999999},
					{1630, 680, 3900, 2450, 6350, 1000, 999999},
					{1505, 625, 3600, 2260, 5860, 1000, 999999},
					{1400, 580, 3340, 2100, 5440, 1000, 999999},
					{1305, 540, 3120, 1960, 5080, 1000, 999999},
					{1100, 455, 2625, 1650, 4275, 1000, 999999},
					{1035, 430, 2470, 1550, 4025, 1000, 999999},
					{975, 405, 2335, 1465, 3800, 1000, 999999},
					{925, 385, 2210, 1385, 3600, 1000, 999999},
					{880, 365, 2100, 1320, 3420, 1000, 999999},
					{835, 345, 2000, 1255, 3260, 1000, 999999},
					{800, 330, 1910, 1200, 3110, 1000, 999999},
					{765, 315, 1825, 1145, 2975, 1000, 999999},
					{730, 305, 1750, 1100, 2850, 1000, 999999},
					{700, 290, 1680, 1055, 2735, 1000, 999999},
					{500, 200, 1440, 750, 1950, 1000, 999999}
			};
	/*
	 * X,S,V,M,L,XL,2XL,other,none
	 */
	private static int[][] filtersc2water =
			{
					{1800, 3000, 7500, 11400, 15600, 20400, 36000, 1000, 999999},
					{1440, 2400, 6000, 9120, 12480, 16320, 28800, 1000, 999999},
					{1200, 2000, 5000, 7600, 10400, 13600, 24000, 1000, 999999},
					{1025, 1710, 4285, 6510, 8910, 11655, 20570, 1000, 999999},
					{900, 1500, 3750, 5700, 7800, 10200, 18000, 1000, 999999},
					{800, 1330, 3330, 5065, 6930, 9065, 16000, 1000, 999999},
					{720, 1200, 3000, 4560, 6240, 8160, 14400, 1000, 999999},
					{655, 1090, 2725, 4145, 5670, 7415, 13090, 1000, 999999},
					{600, 1000, 2500, 3800, 5200, 6800, 12000, 1000, 999999},
					{550, 920, 2305, 3505, 4800, 6275, 11075, 1000, 999999},
					{510, 855, 2140, 3255, 4455, 5825, 10285, 1000, 999999},
					{480, 800, 2000, 3040, 4160, 5440, 9600, 1000, 999999},
					{450, 750, 1875, 2850, 3900, 5100, 9000, 1000, 999999},
					{420, 705, 1765, 2680, 3670, 4800, 8470, 1000, 999999},
					{400, 665, 1665, 2530, 3465, 4530, 8000, 1000, 999999},
					{375, 630, 1575, 2400, 3280, 4295, 7575, 1000, 999999},
					{360, 600, 1500, 2280, 3120, 4080, 7200, 1000, 999999},
					{340, 570, 1425, 2170, 2970, 3885, 6855, 1000, 999999},
					{325, 545, 1360, 2070, 2835, 3705, 6545, 1000, 999999},
					{310, 520, 1300, 1980, 2710, 3545, 6260, 1000, 999999},
					{300, 500, 1250, 1900, 2600, 3400, 6000, 1000, 999999},
					{255, 430, 1075, 1635, 2235, 2925, 5160, 1000, 999999},
					{245, 410, 1030, 1570, 2150, 2810, 4965, 1000, 999999}
			};

	/*public final  static int Local_cn =1;
	public final  static int Local_nl =2;
	public final  static int Local_da =3;
	public final  static int Local_en =4;
	public final  static int Local_fi =5;
	public final  static int Local_gm =6;
	public final  static int Local_no =7;
	public final  static int Local_sv =8;*/
	public static Locale getLocal(int key) {
		Locale locale = Locale.US;
		switch (key) {
			case DrinkName.Local_cn:
				locale = Locale.SIMPLIFIED_CHINESE;
				break;
			case DrinkName.Local_da:
				locale = new Locale("da","DK");
				break;
			case DrinkName.Local_en:
				locale = Locale.ENGLISH;
				break;
			case DrinkName.Local_fi:
				locale = new Locale("fi","FI");
				break;
			case DrinkName.Local_gm:
				break;
			case DrinkName.Local_no:
				break;
			case DrinkName.Local_sv:
				break;
			case DrinkName.Local_nl:
				break;
		}
		return locale;
	}

	/**
	 * 获取星期字符串
	 *
	 * @param week 0(sunday)~6(saturday)
	 * @return
	 */
	public static String weekday(int week, Context context) {
		switch (week) {
			case 0:
				return context.getString(R.string.sun);
			case 1:
				return context.getString(R.string.mon);
			case 2:
				return context.getString(R.string.tue);
			case 3:
				return context.getString(R.string.wed);
			case 4:
				return context.getString(R.string.thu);
			case 5:
				return context.getString(R.string.fri);
			case 6:
				return context.getString(R.string.sat);
		}
		return "";
	}

	/**
	 * Screen Saver Time
	 *
	 * @param minutes ,当minutes为： 1, 20 天 2，1 分钟 3，5 分钟 4，15 分钟 5，30 分钟
	 * @return
	 */
	public static long getScreenSaverTime(int minutes) {
		switch (minutes) {
			case 2:
				return 1 * 60 * 1000;
			case 3:
				return 5 * 60 * 1000;
			case 4:
				return 15 * 60 * 1000;
			case 5:
				return 30 * 60 * 1000;
			default:
				return 20 * 24 * 60 * 60 * 1000;
		}
	}

	public static String inttoBCD(int len) {
		return String.format("%04d", len);
	}

	/**
	 * Filter Count Formular
	 *
	 * @param hardnessLevel
	 * @param filterInstalled
	 * @return 对应值
	 */
	public static int filterCountFormular(int hardnessLevel, int filterInstalled) {
		int[][] filters =
				{{7500, 3000, 5400, 2250, 1000, 999999},
						{6000, 2400, 4320, 1800, 1000, 999999},
						{5000, 2000, 3600, 1500, 1000, 999999},
						{4285, 1710, 3085, 1285, 1000, 999999},
						{3750, 1500, 2700, 1125, 1000, 999999},
						{3330, 1330, 2400, 1000, 1000, 999999},
						{3000, 1200, 2160, 900, 1000, 999999},
						{2725, 1090, 1780, 740, 1000, 999999},
						{2500, 1000, 1630, 680, 1000, 999999},
						{2305, 920, 1505, 625, 1000, 999999},
						{2140, 855, 1400, 580, 1000, 999999},
						{2000, 800, 1305, 540, 1000, 999999},
						{1875, 750, 1100, 455, 1000, 999999},
						{1765, 705, 1035, 430, 1000, 999999},
						{1665, 665, 975, 405, 1000, 999999},
						{1575, 630, 925, 385, 1000, 999999},
						{1500, 600, 880, 365, 1000, 999999},
						{1425, 570, 835, 345, 1000, 999999},
						{1360, 545, 800, 330, 1000, 999999},
						{1300, 520, 765, 315, 1000, 999999},
						{1250, 500, 730, 305, 1000, 999999},
						{1075, 430, 700, 290, 1000, 999999},
						{1030, 410, 500, 205, 1000, 999999},
						{3750, 1500, 2700, 1125, 1000, 999999}};
		return filters[hardnessLevel][filterInstalled];
	}

	/**
	 * Filter Count Formular
	 *
	 * @param hardnessLevel
	 * @param filterInstalled
	 * @return 对应值
	 */
	public static int filterCountFormularnew(int hardnessLevel, int filterInstalled, byte watertype) {

		if (watertype == 0)
			return filterswater[hardnessLevel][filterInstalled];
		else
			return filtersc2water[hardnessLevel][filterInstalled];
	}

	public static String getupdatefilename(int position, boolean isimport) {
		String ret = "Configure 1";
		switch (position) {
			case 0:
				ret = "Configure 1";
				break;
			case 1:
				ret = "Configure 2";
				break;
			case 2:
				ret = "Configure 3";
				break;
			case 3:
				ret = "Configure 4";
				break;
			case 4:
				ret = "Configure 5";
				break;
			case 5:
				if (isimport)
					ret = "Factory Settings";
				else
					ret = "Picture And PDF";
				break;
			case 6:
				ret = "Picture And PDF";
				break;
		}
		return ret;

	}

	public static String float2Hex2(float paramFloat) {
		int value = (int) (paramFloat * 100);
		return String.format("%02X", new Object[]{Integer.valueOf(value / 100)}) + String.format("%02X", new Object[]{Integer.valueOf((value % 100)/10)});

	}

	public static String octscale2Hex2(int  param,int scale ) {

		return String.format("%02X", new Object[]{Integer.valueOf(param / scale)}) + String.format("%02X", new Object[]{Integer.valueOf(param % scale)});

	}

	public static String Int2CurrencyFormat(int value, int scale) {
		int a = value / scale;
		int b = value % scale;
		return String.format("%d.%02d", a, b);
	}

	public static String GetPriceprefix(int currency) {
		String ret = "kr";
		switch (currency) {
			case 1:
				ret = " kr";
				break;
			case 2:
				ret = " €";
				break;
			case 3:
				ret = " ＄";
				break;
			case 4:
				ret = " ￥";
				break;
		}
		return ret;
	}

	public static int String2colorint(String strvalue) {
		int ret;
		try {
			ret = Integer.valueOf(strvalue);
		} catch (Exception e) {
			ret = 0;
		}
		return ret;
	}

	public static String oct2Hex(int paramInt) {
		paramInt = (paramInt &0xff);
		return String.format("%02X", paramInt);
	}

	public static String oct2Hex2(int paramInt) {
		paramInt = (paramInt &0xffff);
		return String.format("%04X", paramInt);

	}

	public static String oct2Hex4(int paramInt) {

		paramInt = (paramInt &0xffffffff);
		return String.format("%08X", paramInt);
	}

	public static String emptySpace(String paramString) {
		StringBuffer localStringBuffer = new StringBuffer();
		int i = paramString.length() / 2;
		for (int j = 0; j < i; j++) {
			localStringBuffer.append(paramString.substring(j * 2, j * 2 + 2)).append(" ");
		}
		return localStringBuffer.toString().trim();
	}

	public static String calcCheckSum(String paramString) {
		String[] paramStringlst = emptySpace(paramString).split(" ");
		int i = 0;
		for (int j = 0; j < paramStringlst.length; j++) {
			Integer localInteger = Integer.valueOf(paramStringlst[j], 16);
			i ^= localInteger.intValue();
		}
		String str;
		if ((
				str = Integer.toHexString(i))
				.length() == 1) {
			str = "0" + str;
		}
		return str;
	}

	public static String parsing(byte[] paramArrayOfByte, int paramInt) {
		StringBuilder localStringBuilder = new StringBuilder(paramArrayOfByte.length);

		if ((paramArrayOfByte == null) || (paramInt <= 0)) {
			return "";
		}
		for (int i = 0; i < paramInt; i++) {
			String str;
			if ((
					str = Integer.toHexString(paramArrayOfByte[i] & 0xFF))
					.length() < 2) {
				localStringBuilder.append(0);
			}
			localStringBuilder.append(str);
		}

		return localStringBuilder.toString();
	}

	public static int dp2px(float value, Context context) {
		final float scale = context.getResources().getDisplayMetrics().densityDpi;
		return (int) (value * (scale / 160) + 0.5f);
	}


	/**
	 * px转dp.
	 *
	 * @param value   the value
	 * @param context the context
	 * @return the int
	 */
	public static int px2dp(float value, Context context) {
		final float scale = context.getResources().getDisplayMetrics().densityDpi;
		return (int) ((value * 160) / scale + 0.5f);
	}

	/**
	 * sp转px.
	 *
	 * @param value the value
	 * @return the int
	 */
	public static int sp2px(float value, Context context) {
		Resources r;
		if (context == null) {
			r = Resources.getSystem();
		} else {
			r = context.getResources();
		}
		float spvalue = value * r.getDisplayMetrics().scaledDensity;
		return (int) (spvalue + 0.5f);
	}


	/**
	 * px转sp.
	 *
	 * @param value   the value
	 * @param context the context
	 * @return the int
	 */
	public static int px2sp(float value, Context context) {
		final float scale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (value / scale + 0.5f);
	}

	public static int fontColor(String paramString) {
		switch (Integer.valueOf(paramString).intValue()) {
			case 1:
				return Color.parseColor("#000000");
			case 2:
				return Color.parseColor("#FFFF00");
			case 3:
				return Color.parseColor("#FF0000");
			case 4:
				return Color.parseColor("#00FF00");
			case 5:
				return Color.parseColor("#DD14DD");
			case 6:
				return Color.parseColor("#F39438");
			case 7:
				return Color.parseColor("#4A82CC");
			case 8:
				return Color.parseColor("#FFFFFF");
		}

		return Color.parseColor("#4A82CC");
	}

	public static String bytes2String(byte[] paramArrayOfByte) {
		StringBuilder localStringBuilder = new StringBuilder(paramArrayOfByte.length);
		int j = paramArrayOfByte.length;
		for (int i = 0; i < j; i++) {
			localStringBuilder.append(String.format("%02X ", Byte.valueOf(paramArrayOfByte[i])));
		}
		return localStringBuilder.toString().trim();
	}

	public static String bufferedReader(String paramString)
			throws IOException {
		File file = new File(paramString);
		if (!file.exists() || file.isDirectory()) {
			throw new FileNotFoundException();
		}
		BufferedReader bufreader = new BufferedReader(new FileReader(file));

		StringBuffer localStringBuffer = new StringBuffer();
		String str = bufreader.readLine();
		while (str != null) {
			localStringBuffer.append(str + " ");
			str = bufreader.readLine();
		}
		bufreader.close();
		return localStringBuffer.toString();
	}

	public static String decrypt(String paramString1, String paramString2)
			throws IOException, Exception {
		if (paramString1 == null) {
			return null;
		}

		return bytes2String(decrypt(new BASE64Decoder()
						.decodeBuffer(paramString1),
				paramString2.getBytes("ASCII")));
	}

	public static byte[] decrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
			throws Exception {
		IvParameterSpec localIvParameterSpec = new IvParameterSpec(paramArrayOfByte2);

		DESKeySpec desKeySpec = new DESKeySpec(paramArrayOfByte2);

		SecretKey secretKey = SecretKeyFactory.getInstance("DES")
				.generateSecret(desKeySpec);
		Cipher localCipher;
		localCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		localCipher.init(2, secretKey, localIvParameterSpec);
		return localCipher.doFinal(paramArrayOfByte1);
	}
}
