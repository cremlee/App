package android.luna.Data.module;

import java.io.Serializable;

import android.annotation.SuppressLint;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressLint("SdCardPath")
@DatabaseTable(tableName = "tb_layout_setting")
public class LayoutSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int preRow = 4;
	@DatabaseField(defaultValue = "6")
	private String fontColor = "6";
	@DatabaseField(defaultValue = "1")
	private String screenSaverTime = "1";
	@DatabaseField(defaultValue = "/mnt/sdcard/evo/ScreenSaver/0.jpg")
	private String screenSaverImage = "/mnt/sdcard/evo/ScreenSaver/0.jpg";
	@DatabaseField(defaultValue = "1")
	private String drinkAnimation = "1";
	@DatabaseField(defaultValue = "1")
	private int showMainBackground = 1;
	@DatabaseField(defaultValue = "/mnt/sdcard/evo/background/bg02.png")
	private String mainBackgroundPicture = "/mnt/sdcard/evo/background/bg02.png";
	@DatabaseField(defaultValue = "1")
	private int showSleepBackground = 1;
	@DatabaseField(defaultValue = "/mnt/sdcard/evo/background/bg02.png")
	private String sleepBackgroundPicture = "/mnt/sdcard/evo/background/bg02.png";
	@DatabaseField(defaultValue = "0")
	private int slideShow = 0;
	@DatabaseField(defaultValue = "2")
	private String screenSaverInterval = "2";
	@DatabaseField(defaultValue = "1")
	private int showBrand = 1;
	@DatabaseField(defaultValue = "/mnt/sdcard/evo/branding/cqube_grey.png")
	private String brandPicture = "/mnt/sdcard/evo/branding/cqube_grey.png";
	@DatabaseField
	private String welcomeMsg = "";
	@DatabaseField(defaultValue = "Energy Saving Mode")
	private String sleepMsg = "Energy Saving Mode";
	// 0:0 or 1 Large icon ; 1:4 fixed Large icons
	@DatabaseField
	private int largeIconMode = 0;
	@DatabaseField
	private String drinkImage = "";
	@DatabaseField
	private int drinkdefaultcolor = 0;
	
	
	public int getDrinkdefaultcolor() {
		return drinkdefaultcolor;
	}

	public void setDrinkdefaultcolor(int drinkdefaultcolor) {
		this.drinkdefaultcolor = drinkdefaultcolor;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPreRow() {
		return preRow;
	}

	public void setPreRow(int preRow) {
		this.preRow = preRow;
	}

	public String getFontColor() {
		return fontColor;
	}
	public int getFontColorint() {
		try
		{
			return Integer.valueOf(fontColor).intValue();
			
		}
		catch(Exception e)
		{
			
		}
		return 0;
	}
	
	public int getFontColorindex() {
		try
		{
			int a = Integer.valueOf(fontColor).intValue()-1;
			if(a>=0 && a<=7)
				return a;
			return 0;
		}
		catch(Exception e)
		{
			
		}
		return 0;
	}
	
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public String getScreenSaverTime() {
		return screenSaverTime;
	}

	public void setScreenSaverTime(String screenSaverTime) {
		this.screenSaverTime = screenSaverTime;
	}

	public String getScreenSaverImage() {
		return screenSaverImage;
	}

	public void setScreenSaverImage(String screenSaverImage) {
		this.screenSaverImage = screenSaverImage;
	}

	public String getDrinkAnimation() {
		return drinkAnimation;
	}

	public void setDrinkAnimation(String drinkAnimation) {
		this.drinkAnimation = drinkAnimation;
	}

	public int getShowMainBackground() {
		return showMainBackground;
	}

	public void setShowMainBackground(int showMainBackground) {
		this.showMainBackground = showMainBackground;
	}

	public String getMainBackgroundPicture() {
		return mainBackgroundPicture;
	}

	public void setMainBackgroundPicture(String mainBackgroundPicture) {
		this.mainBackgroundPicture = mainBackgroundPicture;
	}

	public int getShowSleepBackground() {
		return showSleepBackground;
	}

	public void setShowSleepBackground(int showSleepBackground) {
		this.showSleepBackground = showSleepBackground;
	}

	public String getSleepBackgroundPicture() {
		return sleepBackgroundPicture;
	}

	public void setSleepBackgroundPicture(String sleepBackgroundPicture) {
		this.sleepBackgroundPicture = sleepBackgroundPicture;
	}

	public int getSlideShow() {
		return slideShow;
	}

	public void setSlideShow(int slideShow) {
		this.slideShow = slideShow;
	}

	public String getScreenSaverInterval() {
		return screenSaverInterval;
	}

	public void setScreenSaverInterval(String screenSaverInterval) {
		this.screenSaverInterval = screenSaverInterval;
	}

	public int getShowBrand() {
		return showBrand;
	}

	public void setShowBrand(int showBrand) {
		this.showBrand = showBrand;
	}

	public String getBrandPicture() {
		return brandPicture;
	}

	public void setBrandPicture(String brandPicture) {
		this.brandPicture = brandPicture;
	}

	public String getWelcomeMsg() {
		return welcomeMsg;
	}

	public void setWelcomeMsg(String welcomeMsg) {
		this.welcomeMsg = welcomeMsg;
	}

	public String getSleepMsg() {
		return sleepMsg;
	}

	public void setSleepMsg(String sleepMsg) {
		this.sleepMsg = sleepMsg;
	}

	public int getLargeIconMode() {
		return largeIconMode;
	}

	public void setLargeIconMode(int largeIconMode) {
		this.largeIconMode = largeIconMode;
	}

	public String getDrinkImage() {
		return drinkImage;
	}

	public void setDrinkImage(String drinkImage) {
		this.drinkImage = drinkImage;
	}

}
