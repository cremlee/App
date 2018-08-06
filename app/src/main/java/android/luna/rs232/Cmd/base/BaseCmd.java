package android.luna.rs232.Cmd.base;
import android.luna.Utils.AndroidUtils_Ext;
import android.luna.Utils.Logger.EvoTrace;
import android.util.Log;



/**
 * @author Administrator
 * 
 */
public class BaseCmd {

	private static final String TAG = "BaseCmd";
	private static int packetNo;

	public int packetNO() {
		packetNo++;
		if (packetNo > 255) {
			packetNo = 0;
		}
		return packetNo;
	}

	/**
	 * 构建封包数据
	 * 
	 * @param data
	 * @param packetNO
	 * @return
	 */
	public String buildCmdPkg(String data) { 
		// 获取封包长度=Packet no+data length+checksum
		// 其中Packet no和Checksum为1，所以用data+2及就是封包长度
		int packetNoLength = data.length()/2 + 2;
		String hexPackageNoHLength = AndroidUtils_Ext.oct2Hex(((packetNoLength&0xff00) >>8));
		String hexPackageNoLLength = AndroidUtils_Ext.oct2Hex((packetNoLength & 0x00ff));
		String hexPacketNo = AndroidUtils_Ext.oct2Hex(packetNO());
		String packetNoAndData = hexPacketNo + data;
		String checkSum = AndroidUtils_Ext.calcCheckSum(packetNoAndData);
		StringBuffer cmdBuffer = new StringBuffer();
		cmdBuffer.append("FEFC");
		cmdBuffer.append(hexPackageNoHLength);
		cmdBuffer.append(hexPackageNoLLength);
		cmdBuffer.append(packetNoAndData);
		cmdBuffer.append(checkSum);
		String cmdTemp = cmdBuffer.toString();
		StringBuffer buffer = new StringBuffer();
		int length = cmdTemp.length()/2;
		for (int i = 0; i < length; i++) {
			buffer.append(cmdTemp.substring(i*2, i*2+2)).append(" ");
		}
		String cmd = buffer.toString().trim();
		cmd=cmd+":50";
		EvoTrace.e(TAG, "build cmd:"+cmd);
		return cmd;
	}
	public String buildCmdPkg(String data,String ot) { 
		// 获取封包长度=Packet no+data length+checksum
		// 其中Packet no和Checksum为1，所以用data+2及就是封包长度
		int packetNoLength = data.length()/2 + 2;
		String hexPackageNoHLength = AndroidUtils_Ext.oct2Hex(((packetNoLength&0xff00) >>8));
		String hexPackageNoLLength = AndroidUtils_Ext.oct2Hex((packetNoLength & 0x00ff));
		String hexPacketNo = AndroidUtils_Ext.oct2Hex(packetNO());
		String packetNoAndData = hexPacketNo + data;
		String checkSum = AndroidUtils_Ext.calcCheckSum(packetNoAndData);
		StringBuffer cmdBuffer = new StringBuffer();
		cmdBuffer.append("FEFC");
		cmdBuffer.append(hexPackageNoHLength);
		cmdBuffer.append(hexPackageNoLLength);
		cmdBuffer.append(packetNoAndData);
		cmdBuffer.append(checkSum);
		String cmdTemp = cmdBuffer.toString();
		StringBuffer buffer = new StringBuffer();
		int length = cmdTemp.length()/2;
		for (int i = 0; i < length; i++) {
			buffer.append(cmdTemp.substring(i*2, i*2+2)).append(" ");
		}
		String cmd = buffer.toString().trim();
		cmd=cmd+":"+ot;
		EvoTrace.e(TAG, "build cmd:"+cmd);
		return cmd;
	}
}
