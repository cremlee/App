package android.luna.Data.module;

import java.io.Serializable;

/**
 * 警告信息
 * 
 * @author Administrator
 * 
 */
/**
 * @author Administrator
 * 
 */
public class Warning implements Serializable {

	public static final int TYPE_WARNING = 0;
	public static final int TYPE_SCHEDULE = 1;
	public static final int TYPE_TEMPERATURE_SETTING = 2;
	public static final int TYPE_CHANGE_PIN = 3;
	public static final int TYPE_CLEANING = 4;
	public static final int TYPE_OTHERS = 5;

	private static final long serialVersionUID = 1L;
	private String title; // Object No Description
	private String content;
	private int type; // Sub object No Description
	private boolean isBlock;

	public Warning() {
	}

	public Warning(String title, String content, int type, boolean isBlock) {
		super();
		this.title = title;
		this.content = content;
		this.type = type;
		this.isBlock = isBlock;
	}
	
	public Warning(String title,String content){
		super();
		this.title = title;
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o instanceof Warning) {
			Warning d = (Warning) o;
			return d.getType() == type;
		}

		return false;
	}

	@Override
	public String toString() {
		return "Warning [title=" + title + ", content=" + content + ", type=" + type + ", isBlock=" + isBlock + "]";
	}

}
