package android.luna.rs232.Cmd;
import android.luna.Data.module.FiterBrewStep;
import android.luna.Data.module.IngredientEspresso;
import android.luna.Data.module.IngredientFilterBrew;
import android.luna.Data.module.IngredientFilterBrewAdvance;
import android.luna.Data.module.IngredientInstant;
import android.luna.Data.module.IngredientMilk;
import android.luna.Data.module.IngredientMono;
import android.luna.Data.module.IngredientWater;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.rs232.Cmd.base.*;
import android.util.Log;

import java.util.List;


public class CmdMakeIngredient extends BaseCmd {

	private static final String TAG = "CmdMakeIngredient";
	/*private List<FiterBrewStep> FiterBrewStepLists = new ArrayList<FiterBrewStep>();
	private FilterBrewStepDao filterBrewStepDao;*/

	/**
	 * 构建指令
	 * 
	 * @param operaterCmd
	 * @param octIngredientPid
	 *            此ID由上位机管理
	 * @param ingredientType
	 * @param ingredientStructure
	 * @return
	 */
	public String buildCmd(String operaterCmd, int octIngredientPid, String ingredientType, String ingredientStructure) {
		String hexIngredientPid = AndroidUtils_Ext.oct2Hex2(octIngredientPid);
		StringBuffer buffer = new StringBuffer();
		buffer.append("0007");
		buffer.append(operaterCmd);
		buffer.append(hexIngredientPid);
		buffer.append(ingredientType);
		buffer.append(ingredientStructure);
		return super.buildCmdPkg(buffer.toString());
	}

	public String buildEspressoStructure(IngredientEspresso espresso)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append(AndroidUtils_Ext.float2Hex2(espresso.getPowderamount() / 10.0f));
		buffer.append(AndroidUtils_Ext.oct2Hex2(espresso.getPreinfusiontime()*1000));
		buffer.append(AndroidUtils_Ext.oct2Hex2(espresso.getPrebrewtime()*1000));
		buffer.append(AndroidUtils_Ext.oct2Hex2(espresso.getBrewtime()*1000));
		buffer.append(AndroidUtils_Ext.oct2Hex2(espresso.getWatervolume()));
		buffer.append(AndroidUtils_Ext.oct2Hex(espresso.getPressure()));
		buffer.append(AndroidUtils_Ext.oct2Hex(espresso.getWatertype()));
		buffer.append(AndroidUtils_Ext.oct2Hex(espresso.getPowdertype()));
		return buffer.toString();
	}
	public String buildFilterBrewAdvanceStructure(IngredientFilterBrewAdvance filterbrewadvance, List<FiterBrewStep> FiterBrewStepLists)
	{
		StringBuffer buffer = new StringBuffer();
		/*filterBrewStepDao = new FilterBrewStepDao();
		
		FiterBrewStepLists = filterBrewStepDao.getFilterBrewStep(filterbrewadvance.getPid());*/
		buffer.append(AndroidUtils_Ext.oct2Hex(filterbrewadvance.getPhaseNumber()));
		for(int i=0;i<FiterBrewStepLists.size();i++)
		{
			buffer.append(AndroidUtils_Ext.oct2Hex2(FiterBrewStepLists.get(i).getWait()*100)); // phase1 – brew motor wait
			buffer.append(AndroidUtils_Ext.oct2Hex2(FiterBrewStepLists.get(i).getPosition())); // phase1 – brew motor postion
			buffer.append(AndroidUtils_Ext.oct2Hex(FiterBrewStepLists.get(i).getSpeed())); // phase1 – brew motor postion
		}
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterbrewadvance.getWaterDispenDelay()*100));
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterbrewadvance.getWaterVolume()));
		buffer.append("01");
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterbrewadvance.getGrinder1Type()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterbrewadvance.getGrinder1DispenDelay()*100));
		buffer.append(AndroidUtils_Ext.float2Hex2(filterbrewadvance.getGrinder1Amount() / 10.0f));
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterbrewadvance.getGrinder2Type()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterbrewadvance.getGrinder2DispenDelay()*100));
		buffer.append(AndroidUtils_Ext.float2Hex2(filterbrewadvance.getGrinder2Amount() / 10.0f));
		return buffer.toString();
	}
	/**
	 * 
	 * @param filterBrew
	 *            机器类型，type = 0 TM13,type = 1 TM04
	 * @return
	 */
	public String buildFilterBrewStructure(IngredientFilterBrew filterBrew) {

		StringBuffer buffer = new StringBuffer();

		// step1
		buffer.append(AndroidUtils_Ext.oct2Hex(filterBrew.getPhaseNumber()));
		
		buffer.append(AndroidUtils_Ext.oct2Hex2(0)); // phase1 – brew motor wait
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterBrew.getWaterVolume()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterBrew.getGrinder1Type()));
		buffer.append(AndroidUtils_Ext.float2Hex2(filterBrew.getGrinder1Amount() / 10.0f));
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterBrew.getGrinder2Type()));
		buffer.append(AndroidUtils_Ext.float2Hex2(filterBrew.getGrinder2Amount() / 10.0f));

		/* step2 */
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterBrew.getTmPre13() * 100));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		/* step3 */
		// Extraction Time
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterBrew.getExtractionTime() * 100));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		/* step4 */
		// Decompress Time
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterBrew.getDecompressTime() * 100));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		/* step5 */
		// Open Delay Time
		buffer.append(AndroidUtils_Ext.oct2Hex2(filterBrew.getOpenDelayTime() * 100));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));
		buffer.append(AndroidUtils_Ext.oct2Hex2(0));

		// Brew rise position (the percent of the whole rise range)
		buffer.append(AndroidUtils_Ext.oct2Hex(filterBrew.getPreBrewPosition()));
		// Brew down position(the percent of the whole down range)
		buffer.append(AndroidUtils_Ext.oct2Hex(filterBrew.getDecompressPosition()));
		return buffer.toString();
	}

	public String buildWaterStructure(IngredientWater water) {

		String hexWaterType = AndroidUtils_Ext.oct2Hex(water.getWaterType());
		String hexWaterVolume = AndroidUtils_Ext.oct2Hex2(water.getWaterVolume());

		StringBuffer buffer = new StringBuffer();
		buffer.append(hexWaterType);
		buffer.append(hexWaterVolume);
		return buffer.toString();
	}
	
	public String buildMilkStructure(IngredientMilk milk) {

		String hexWaterType = AndroidUtils_Ext.oct2Hex(milk.getType());
		String hexWaterVolume = AndroidUtils_Ext.oct2Hex2(milk.getVolume());

		StringBuffer buffer = new StringBuffer();
		buffer.append(hexWaterType);
		buffer.append(hexWaterVolume);
		return buffer.toString();
	}

	public String buildInstantStructure(IngredientInstant instant) {

		StringBuilder buffer = new StringBuilder();

		buffer.append(AndroidUtils_Ext.oct2Hex(instant.getMixNumber()));
		// Mix-whipper pre flush time (ms)
		buffer.append(AndroidUtils_Ext.oct2Hex2(instant.getPreflushVolume()));
		// Mix-pause time before flush(ms)
		buffer.append(AndroidUtils_Ext.oct2Hex2(instant.getPreflushPauseTime()));
		// Mix-pause time after dispense(ms)
		buffer.append(AndroidUtils_Ext.oct2Hex2(instant.getPauseTimeAfterDispense()));
		// Mix-water dispense time(ms)
		// buffer.append(AndroidUtils.oct2Hex2(instant.getWaterDispenseTime()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(instant.getWaterVolume()));
		//Mix-start run time
		buffer.append(AndroidUtils_Ext.oct2Hex(instant.getMixStart()));
		//Mix-start run time
		buffer.append(AndroidUtils_Ext.oct2Hex(instant.getMixStop()));
		// Mix-water after flush time (ms)		
		buffer.append(AndroidUtils_Ext.oct2Hex2(instant.getWaterAfterFlushVolume()));
		// whipper speed
		buffer.append(AndroidUtils_Ext.oct2Hex(instant.getWhipperSpeed()));
		// Ingredient water type
		buffer.append(AndroidUtils_Ext.oct2Hex(Integer.valueOf(instant.getWaterType())));
		// package number
		buffer.append(AndroidUtils_Ext.oct2Hex(instant.getPacketNumber()));
		// Packet1 type
		buffer.append(AndroidUtils_Ext.oct2Hex2(Integer.valueOf(instant.getPacket1Type())));
		// packet1 amount
		buffer.append(AndroidUtils_Ext.float2Hex2(instant.getPacket1Amount() / 10.0f));
		// Packet2 type
		buffer.append(AndroidUtils_Ext.oct2Hex2(Integer.valueOf(instant.getPacket2Type())));
		// packet2 amount
		buffer.append(AndroidUtils_Ext.float2Hex2(instant.getPacket2Amount() / 10.0f));
		return buffer.toString();
	}


	public String buildMonoStructure(IngredientMono mono) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(AndroidUtils_Ext.float2Hex2(mono.getPowderamount()));
		buffer.append(AndroidUtils_Ext.oct2Hex(mono.getPowdertype()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getInfusiontime()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getInfusionwatervolume()));
		buffer.append(AndroidUtils_Ext.oct2Hex(mono.getInfusionwatertype()));
		buffer.append(AndroidUtils_Ext.oct2Hex(mono.getInfusionwaterntc()));

		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getBubblerruntime()));
		buffer.append(AndroidUtils_Ext.oct2Hex(mono.getBubblerspeed()));

		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getBrewtime()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getDispensewatervolume()));
		buffer.append(AndroidUtils_Ext.oct2Hex(mono.getDispensewatertype()));
		buffer.append(AndroidUtils_Ext.oct2Hex(mono.getDispensewaterntc()));

		/*buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getAirruntime()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getPowdervolume()));
		buffer.append(AndroidUtils_Ext.oct2Hex(mono.getWaterpressure()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getAirspeed()));
		buffer.append(AndroidUtils_Ext.oct2Hex2(mono.getPowdervolumetype()));*/
		return buffer.toString();
	}
}
