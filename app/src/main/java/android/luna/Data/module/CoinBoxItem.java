package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_coin_box")
public class CoinBoxItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;	
	@DatabaseField(defaultValue = "10")
	private int coinchannel1;
	@DatabaseField(defaultValue = "20")
	private int coinchannel2;
	@DatabaseField(defaultValue = "50")
	private int coinchannel3;
	@DatabaseField(defaultValue = "100")
	private int coinchannel4;
	@DatabaseField(defaultValue = "200")
	private int coinchannel5;
	@DatabaseField(defaultValue = "500")
	private int coinchannel6;	
	public CoinBoxItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CoinBoxItem(int coinchannel1, int coinchannel2, int coinchannel3,
			int coinchannel4, int coinchannel5, int coinchannel6) {
		super();
		
		this.coinchannel1 = coinchannel1;
		this.coinchannel2 = coinchannel2;
		this.coinchannel3 = coinchannel3;
		this.coinchannel4 = coinchannel4;
		this.coinchannel5 = coinchannel5;
		this.coinchannel6 = coinchannel6;		
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getCoinchannel1() {
		return coinchannel1;
	}
	public void setCoinchannel1(int coinchannel1) {
		this.coinchannel1 = coinchannel1;
	}
	public int getCoinchannel2() {
		return coinchannel2;
	}
	public void setCoinchannel2(int coinchannel2) {
		this.coinchannel2 = coinchannel2;
	}
	public int getCoinchannel3() {
		return coinchannel3;
	}
	public void setCoinchannel3(int coinchannel3) {
		this.coinchannel3 = coinchannel3;
	}
	public int getCoinchannel4() {
		return coinchannel4;
	}
	public void setCoinchannel4(int coinchannel4) {
		this.coinchannel4 = coinchannel4;
	}
	public int getCoinchannel5() {
		return coinchannel5;
	}
	public void setCoinchannel5(int coinchannel5) {
		this.coinchannel5 = coinchannel5;
	}
	public int getCoinchannel6() {
		return coinchannel6;
	}
	public void setCoinchannel6(int coinchannel6) {
		this.coinchannel6 = coinchannel6;
	}
	
	
}
