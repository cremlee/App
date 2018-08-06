package android.luna.Data.module;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_pay_setting")
public class PaymentSetting implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(defaultValue = "1")
	private int currency;
	@DatabaseField(defaultValue = "0")
	private int isfreeon;
	@DatabaseField(defaultValue = "000000")
	private String freepin = "000000";
	@DatabaseField(defaultValue = "0")
	private int iscoinon;
	@DatabaseField(defaultValue = "0")
	private int isnayaxon;
	@DatabaseField(defaultValue = "0")
	private int iscogeson;
	@DatabaseField(defaultValue = "0")
	private int iswechaton;
	@DatabaseField(defaultValue = "0")
	private int isalipayon;
	@DatabaseField(defaultValue = "0")
	private int enablehappy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public int getIsfreeon() {
		return isfreeon;
	}

	public void setIsfreeon(int isfreeon) {
		this.isfreeon = isfreeon;
	}

	public String getFreepin() {
		return freepin;
	}

	public void setFreepin(String freepin) {
		this.freepin = freepin;
	}

	public int getIscoinon() {
		return iscoinon;
	}

	public void setIscoinon(int iscoinon) {
		this.iscoinon = iscoinon;
	}

	public int getIsnayaxon() {
		return isnayaxon;
	}

	public void setIsnayaxon(int isnayaxon) {
		this.isnayaxon = isnayaxon;
	}

	public int getIscogeson() {
		return iscogeson;
	}

	public void setIscogeson(int iscogeson) {
		this.iscogeson = iscogeson;
	}

	public int getIswechaton() {
		return iswechaton;
	}

	public void setIswechaton(int iswechaton) {
		this.iswechaton = iswechaton;
	}

	public int getIsalipayon() {
		return isalipayon;
	}

	public void setIsalipayon(int isalipayon) {
		this.isalipayon = isalipayon;
	}

	public int getEnablehappy() {
		return enablehappy;
	}

	public void setEnablehappy(int enablehappy) {
		this.enablehappy = enablehappy;
	}

	public PaymentSetting() {
	}

	public PaymentSetting(int id) {
		this.id = id;
	}

	public PaymentSetting(int currency, int isfreeon, String freepin, int iscoinon, int isnayaxon, int iscogeson, int iswechaton, int isalipayon, int enablehappy) {
		this.currency = currency;
		this.isfreeon = isfreeon;
		this.freepin = freepin;
		this.iscoinon = iscoinon;
		this.isnayaxon = isnayaxon;
		this.iscogeson = iscogeson;
		this.iswechaton = iswechaton;
		this.isalipayon = isalipayon;
		this.enablehappy = enablehappy;
	}

	public boolean isPaymentEnable()
	{
		return (isfreeon+iscoinon+isnayaxon+iscogeson+iswechaton+isalipayon)>0;
	}

}
