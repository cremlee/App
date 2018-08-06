package android.luna.Data.module;

import java.io.Serializable;

/**
 * 实时状态
 * 
 * @author Administrator
 * 
 */
public class EVOState implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static int MODE_NORMAL = 1;
	public final static int MODE_SLEEP = 10;
	public final static int MODE_BACKUP = 11;
	public final static int MODE_CLEAN = 100; // Exit时需要判断
	public final static int MODE_DISPENSING = 101; // Exit时需要判断
	public final static int MODE_NO_DISPENSING = 110;
	public final static int MODE_CALIBRATION = 111;
	public final static int MODE_HEATING = 1000;
	public final static int MODE_WATERING = 1001;

	/* 工作模式 start */
	private int workMode; //
	private int workModePrarm;// 工作模式参数

	/* 各模块状态 start */
	private int moduleStateReserve;//
	private int moduleStateMotor;//
	private int moduleStateHeating;
	private int moduleStateCooling;
	private int moduleStateWater;
	/* 各模块状态 end */
	private float fridgeTemp;
	private float fanTemp;
	private float waterTankTemp;

	/* Sensor state start 1 byte 7*/
	private int sensorStateDoor;
	private int sensorStateDripTray;
	private int sensorStateDripTrayOverflow;
	private int sensorStateCup1=0;
	private int sensorStateCup2=0;
	private int sensorStateWaterLevel;
	private int sensorStateBrewSwitch;
	
	/* Sensor state start 2 byte 8*/
	private int sensorStateHeater;
	
	
	
	
	public int getSensorStateHeater() {
		return sensorStateHeater;
	}

	public void setSensorStateHeater(int sensorStateHeater) {
		this.sensorStateHeater = sensorStateHeater;
	}

	public int getSensorStateBrewSwitch() {
		return sensorStateBrewSwitch;
	}

	public void setSensorStateBrewSwitch(int sensorStateBrewSwitch) {
		this.sensorStateBrewSwitch = sensorStateBrewSwitch;
	}

	/* Sensor state end */
	public int getWorkMode() {
		return workMode;
	}

	public void setWorkMode(int workMode) {
		this.workMode = workMode;
	}

	public int getWorkModePrarm() {
		return workModePrarm;
	}

	public void setWorkModePrarm(int workModePrarm) {
		this.workModePrarm = workModePrarm;
	}

	public int getModuleStateReserve() {
		return moduleStateReserve;
	}

	public void setModuleStateReserve(int moduleStateReserve) {
		this.moduleStateReserve = moduleStateReserve;
	}

	public int getModuleStateMotor() {
		return moduleStateMotor;
	}

	public void setModuleStateMotor(int moduleStateMotor) {
		this.moduleStateMotor = moduleStateMotor;
	}

	public int getModuleStateHeating() {
		return moduleStateHeating;
	}

	public void setModuleStateHeating(int moduleStateHeating) {
		this.moduleStateHeating = moduleStateHeating;
	}

	public int getModuleStateCooling() {
		return moduleStateCooling;
	}

	public void setModuleStateCooling(int moduleStateCooling) {
		this.moduleStateCooling = moduleStateCooling;
	}

	public int getModuleStateWater() {
		return moduleStateWater;
	}

	public void setModuleStateWater(int moduleStateWater) {
		this.moduleStateWater = moduleStateWater;
	}

	public float getFridgeTemp() {
		return fridgeTemp;
	}

	public void setFridgeTemp(float fridgeTemp) {
		this.fridgeTemp = fridgeTemp;
	}

	public float getFanTemp() {
		return fanTemp;
	}

	public void setFanTemp(float fanTemp) {
		this.fanTemp = fanTemp;
	}

	public float getWaterTankTemp() {
		return waterTankTemp;
	}

	public void setWaterTankTemp(float waterTankTemp) {
		this.waterTankTemp = waterTankTemp;
	}

	public int getSensorStateDoor() {
		return sensorStateDoor;
	}

	public void setSensorStateDoor(int sensorStateDoor) {
		this.sensorStateDoor = sensorStateDoor;
	}

	public int getSensorStateDripTray() {
		return sensorStateDripTray;
	}

	public void setSensorStateDripTray(int sensorStateDripTray) {
		this.sensorStateDripTray = sensorStateDripTray;
	}

	public int getSensorStateDripTrayOverflow() {
		return sensorStateDripTrayOverflow;
	}

	public void setSensorStateDripTrayOverflow(int sensorStateDripTrayOverflow) {
		this.sensorStateDripTrayOverflow = sensorStateDripTrayOverflow;
	}

	public int getSensorStateCup1() {
		return sensorStateCup1;
	}

	public void setSensorStateCup1(int sensorStateCup1) {
		this.sensorStateCup1 = sensorStateCup1;
	}

	public int getSensorStateCup2() {
		return sensorStateCup2;
	}

	public void setSensorStateCup2(int sensorStateCup2) {
		this.sensorStateCup2 = sensorStateCup2;
	}

	public int getSensorStateWaterLevel() {
		return sensorStateWaterLevel;
	}

	public void setSensorStateWaterLevel(int sensorStateWaterLevel) {
		this.sensorStateWaterLevel = sensorStateWaterLevel;
	}

	@Override
	public String toString() {
		return "EVOState [workMode=" + workMode + ", workModePrarm=" + workModePrarm + ", moduleStateReserve=" + moduleStateReserve + ", moduleStateMotor=" + moduleStateMotor + ", moduleStateHeating=" + moduleStateHeating + ", moduleStateCooling=" + moduleStateCooling + ", moduleStateWater=" + moduleStateWater + ", fridgeTemp=" + fridgeTemp + ", fanTemp=" + fanTemp + ", waterTankTemp=" + waterTankTemp + ", sensorStateDoor=" + sensorStateDoor + ", sensorStateDripTray=" + sensorStateDripTray + ", sensorStateDripTrayOverflow=" + sensorStateDripTrayOverflow + ", sensorStateCup1=" + sensorStateCup1 + ", sensorStateCup2=" + sensorStateCup2 + ", sensorStateWaterLevel=" + sensorStateWaterLevel + "]";
	}

}
