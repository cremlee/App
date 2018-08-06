package android.luna.Data.module;
import java.io.Serializable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 用于记录临时事件，在app启动的时候需要先表中的临时事件删除
 * 
 * @author wujiayi
 * 
 */
@DatabaseTable(tableName = "tb_additional_event")
public class AdditionalEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int schedulerId;
	@DatabaseField
	private int count;

	public AdditionalEvent() {
	}

	public AdditionalEvent(int schedulerId, int count) {
		super();
		this.schedulerId = schedulerId;
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSchedulerId() {
		return schedulerId;
	}

	public void setSchedulerId(int schedulerId) {
		this.schedulerId = schedulerId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
