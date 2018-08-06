package android.luna.rs232.Cmd.base;
import android.content.Context;
import evo.luna.android.R;

public class CmdDefine {

	public static final  int CMD_RSP_OK = 0x01;
	public static final  int CMD_RSP_Err = 0x02;
	public static final  int CMD_RSP_Err_Packge = 0x03;
	public static final  int CMD_RSP_Err_inDispensing = 0x04;
	public static final  int CMD_RSP_Err_ID_Invalid = 0x05;
	public static final  int CMD_RSP_Err_CMD = 0x06;
	public static final  int CMD_RSP_Err_State = 0x07;
	public static final  String CMD_ERROR_CHECK = "1d";
	public static String GetErrDescribe(Context con ,int errorcode)
	{
		String ret =null;
		switch(errorcode)	
		{
		case CMD_RSP_OK:
			ret = con.getResources().getString(R.string.cmd_rsp_ok);
			break;
		case CMD_RSP_Err:
			ret = con.getResources().getString(R.string.cmd_rsp_err);
			break;
		case CMD_RSP_Err_Packge:
			ret = con.getResources().getString(R.string.cmd_rsp_err_packge);
			break;
		case CMD_RSP_Err_inDispensing:
			ret = con.getResources().getString(R.string.cmd_rsp_err_indispensing);
			break;
		case CMD_RSP_Err_ID_Invalid:
			ret = con.getResources().getString(R.string.cmd_rsp_id_invalid);
			break;
		case CMD_RSP_Err_CMD:
			ret = con.getResources().getString(R.string.cmd_rsp_err_cmd);
			break;
		case CMD_RSP_Err_State:
			ret = con.getResources().getString(R.string.cmd_rsp_err_state);
			break;
			default:
				break;
		}
		return ret;
	}
}
