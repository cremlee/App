package android.luna.BlueCom.cmd;

import android.annotation.SuppressLint;
import android.luna.BlueCom.BlueCmdDefine;
import android.luna.Data.CustomerUI.DrinkMenuButton;

import java.util.List;

public class RespQueryBeverage extends BlueCmdFactory implements IBluecmd {
	private List<DrinkMenuButton> _data;

	public RespQueryBeverage(List<DrinkMenuButton> _data) {
		this._data = _data;
	}
	@SuppressLint("DefaultLocale")
	@Override
	public String toCmd() {
		String ret ="";
		if(_data!=null && _data.size()>0)
		{
			for (DrinkMenuButton item:_data
				 ) {
                if(!item.getName().equals(""))
				    ret+=(String.format("%04d",item.getPid())+"+"+item.getName()+":");
			}
			ret = ret.substring(0,ret.lastIndexOf(":"));
			return super.buildCmdPkg(BlueCmdDefine.CMD_QUERY_BEVERAGE+ret);
		}
		else
			return super.buildCmdPkg(BlueCmdDefine.CMD_QUERY_BEVERAGE);
	}
}
