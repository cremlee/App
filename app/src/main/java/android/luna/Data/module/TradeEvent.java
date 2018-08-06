package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "tb_trade")
public class TradeEvent implements Serializable {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private String tradetm;
	@DatabaseField
	private int tradetype;
	@DatabaseField
	private int tradevalue;
	@DatabaseField
	private int tradeflag;
	@DatabaseField
	private int week;
	
	public TradeEvent(String tradetm, int tradetype, int tradevalue,
			int tradeflag,int week) {
		super();
		this.tradetm = tradetm;
		this.tradetype = tradetype;
		this.tradevalue = tradevalue;
		this.tradeflag = tradeflag;
		this.week =week;
	}
	public TradeEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTradetm() {
		return tradetm;
	}
	public void setTradetm(String tradetm) {
		this.tradetm = tradetm;
	}
	public int getTradetype() {
		return tradetype;
	}
	public void setTradetype(int tradetype) {
		this.tradetype = tradetype;
	}
	public int getTradevalue() {
		return tradevalue;
	}
	public void setTradevalue(int tradevalue) {
		this.tradevalue = tradevalue;
	}
	public int getTradeflag() {
		return tradeflag;
	}
	public void setTradeflag(int tradeflag) {
		this.tradeflag = tradeflag;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	
	
}
