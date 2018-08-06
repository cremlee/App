package android.luna.Data.module;

import java.io.Serializable;

public class Setting implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int image;
	private int bgColor;
	
	public Setting() {
	}
	
	public Setting(String name, int image, int bgColor) {
		super();
		this.name = name;
		this.image = image;
		this.bgColor = bgColor;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getBgColor() {
		return bgColor;
	}
	public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}

}
