package android.luna.rs232.Cmd;
import android.luna.rs232.Cmd.base.*;
public class CmdHardwareTest extends BaseCmd {
	public static final String OPERATOR_OFF ="00";
	public static final String OPERATOR_ALWAYS ="01";
	public static final String OPERATOR_ONCE ="02";

	public static final String OPERATOR_INLET_OPEN ="81";
	public static final String OPERATOR_INLET_CLOSE="82";
	public static final String OPERATOR_OUTLET_OPEN_HOT ="83";
	public static final String OPERATOR_OUTLET_CLOSE_HOT ="84";
	public static final String OPERATOR_OUTLET_OPEN_COLD ="43";
	public static final String OPERATOR_OUTLET_CLOSE_COLD ="44";


	public String buildHardwareTestCmd(String operaterCmd, String testId, String controlSpeed) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("0009");
		buffer.append(operaterCmd);
		buffer.append(testId);
		buffer.append(controlSpeed);

		return super.buildCmdPkg(buffer.toString());
	}
}
