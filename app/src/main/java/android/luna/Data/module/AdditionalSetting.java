package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_additional_setting")
public class AdditionalSetting implements Serializable {

	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(defaultValue = "100")
	private int fanSpeed = 100;
	
	@DatabaseField(defaultValue = "30")
	private int fanRunTime = 30;
	
	@DatabaseField(defaultValue = "0")
	private int fanRunContinuously = 0;
	
	@DatabaseField(defaultValue = "1")
	private int useDripTray = 1;
	
	@DatabaseField(defaultValue = "1")
	private int jugModeEnable = 1;
	
	@DatabaseField(defaultValue = "0")
	private int useCupSensor = 0;
	
	@DatabaseField(defaultValue = "1")
	private int cleanWarnBlockMachine = 1;
	
	@DatabaseField(defaultValue = "0")
	private int waterFilter = 0;
	
	@DatabaseField(defaultValue = "0")
	private int waterHardness = 0;
	
	@DatabaseField(defaultValue = "999999")
	private int waterVolume = 999999;
	
	@DatabaseField(defaultValue = "0")
	private int usedailyclean = 0;
	
	@DatabaseField(defaultValue = "1")
	private int onebuttonstartenable = 1;
	
	@DatabaseField(defaultValue = "1")
	private int beepmode = 1;
	
	@DatabaseField(defaultValue = "0")
	private int inteeco = 0;
	
	@DatabaseField(defaultValue = "100")
	private int fanSpeedAfterDisp = 100;
	
	@DatabaseField(defaultValue = "30")
	private int fanRunTimeAfterDisp = 30;
	
	
	
	public int getFanSpeedAfterDisp() {
		return fanSpeedAfterDisp;
	}

	public void setFanSpeedAfterDisp(int fanSpeedAfterDisp) {
		this.fanSpeedAfterDisp = fanSpeedAfterDisp;
	}

	public int getFanRunTimeAfterDisp() {
		return fanRunTimeAfterDisp;
	}

	public void setFanRunTimeAfterDisp(int fanRunTimeAfterDisp) {
		this.fanRunTimeAfterDisp = fanRunTimeAfterDisp;
	}

	public int getInteeco() {
		return inteeco;
	}

	public void setInteeco(int inteeco) {
		this.inteeco = inteeco;
	}

	public int getBeepmode() {
		return beepmode;
	}

	public void setBeepmode(int beepmode) {
		this.beepmode = beepmode;
	}

	public int getOnebuttonstartenable() {
		return onebuttonstartenable;
	}

	public void setOnebuttonstartenable(int onebuttonstartenable) {
		this.onebuttonstartenable = onebuttonstartenable;
	}

	public int getUsedailyclean() {
		return usedailyclean;
	}

	public void setUsedailyclean(int usedailyclean) {
		this.usedailyclean = usedailyclean;
	}

	public AdditionalSetting() {
		super();
	}

	public AdditionalSetting(int fanSpeed, int fanRunTime, int fanRunContinuously, int useDripTray, int jugModeEnable, int useCupSensor,int onebuttonstartenable,int beep,int inteco) {
		super();
		this.fanSpeed = fanSpeed;
		this.fanRunTime = fanRunTime;
		this.fanRunContinuously = fanRunContinuously;
		this.useDripTray = useDripTray;
		this.jugModeEnable = jugModeEnable;
		this.useCupSensor = useCupSensor;
		this.onebuttonstartenable = onebuttonstartenable;
		this.beepmode =beep;
		this.inteeco = inteco;
	}

	public AdditionalSetting(int waterFilter, int waterHardness, int waterVolume) {
		super();
		this.waterFilter = waterFilter;
		this.waterHardness = waterHardness;
		this.waterVolume = waterVolume;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFanSpeed() {
		return fanSpeed;
	}

	public void setFanSpeed(int fanSpeed) {
		this.fanSpeed = fanSpeed;
	}

	public int getFanRunTime() {
		return fanRunTime;
	}

	public void setFanRunTime(int fanRunTime) {
		this.fanRunTime = fanRunTime;
	}

	public int getFanRunContinuously() {
		return fanRunContinuously;
	}

	public void setFanRunContinuously(int fanRunContinuously) {
		this.fanRunContinuously = fanRunContinuously;
	}

	public int getUseDripTray() {
		return useDripTray;
	}

	public void setUseDripTray(int useDripTray) {
		this.useDripTray = useDripTray;
	}

	public int getJugModeEnable() {
		return jugModeEnable;
	}

	public void setJugModeEnable(int jugModeEnable) {
		this.jugModeEnable = jugModeEnable;
	}

	public int getUseCupSensor() {
		return useCupSensor;
	}

	public void setUseCupSensor(int useCupSensor) {
		this.useCupSensor = useCupSensor;
	}

	public int getWaterFilter() {
		return waterFilter;
	}

	public void setWaterFilter(int waterFilter) {
		this.waterFilter = waterFilter;
	}

	public int getWaterHardness() {
		return waterHardness;
	}

	public void setWaterHardness(int waterHardness) {
		this.waterHardness = waterHardness;
	}

	public int getWaterVolume() {
		return waterVolume;
	}

	public void setWaterVolume(int waterVolume) {
		this.waterVolume = waterVolume;
	}

	public int getCleanWarnBlockMachine() {
		return cleanWarnBlockMachine;
	}

	public void setCleanWarnBlockMachine(int cleanWarnBlockMachine) {
		this.cleanWarnBlockMachine = cleanWarnBlockMachine;
	}

	@Override
	public String toString() {
		return "AdditionalSetting [fanSpeed=" + fanSpeed + ", fanRunTime="
				+ fanRunTime + ", fanRunContinuously=" + fanRunContinuously
				+ ", useDripTray=" + useDripTray + ", jugModeEnable="
				+ jugModeEnable + ", useCupSensor=" + useCupSensor
				+ ", cleanWarnBlockMachine=" + cleanWarnBlockMachine
				+ ", waterFilter=" + waterFilter + ", waterHardness="
				+ waterHardness + ", waterVolume=" + waterVolume
				+ ", usedailyclean=" + usedailyclean
				+ ", onebuttonstartenable=" + onebuttonstartenable
				+ ", beepmode=" + beepmode + ", inteeco=" + inteeco
				+ ", fanSpeedAfterDisp=" + fanSpeedAfterDisp
				+ ", fanRunTimeAfterDisp=" + fanRunTimeAfterDisp + "]";
	}

	

}
