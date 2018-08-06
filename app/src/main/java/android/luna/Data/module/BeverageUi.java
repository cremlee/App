package android.luna.Data.module;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_beverage_ui")
public class BeverageUi implements Serializable {
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int pid;
	@DatabaseField
	private String iconPath="";
	@DatabaseField(defaultValue = "0")
	private int sortIndex;
	@DatabaseField(defaultValue = "0")
	private int showOrhide;
	@DatabaseField(defaultValue = "0")
	private int iconSize;
	@DatabaseField
	private String storyTellingPath="";
	@DatabaseField
	private String dispenseTellingPath="";
	@DatabaseField
	private String galleryBkgPath="";
	@DatabaseField(defaultValue = "0")
	private int quickDrink;
	@DatabaseField
	private String name="no name";

	public BeverageUi(){super();}

	public BeverageUi(int pid ,String name){
		super();
		this.name =name;
		this.pid =pid;
	}
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public int getShowOrhide() {
		return showOrhide;
	}

	public void setShowOrhide(int showOrhide) {
		this.showOrhide = showOrhide;
	}

	public int getIconSize() {
		return iconSize;
	}

	public void setIconSize(int iconSize) {
		this.iconSize = iconSize;
	}

	public String getStoryTellingPath() {
		return storyTellingPath;
	}

	public void setStoryTellingPath(String storyTellingPath) {
		this.storyTellingPath = storyTellingPath;
	}

	public String getDispenseTellingPath() {
		return dispenseTellingPath;
	}

	public void setDispenseTellingPath(String dispenseTellingPath) {
		this.dispenseTellingPath = dispenseTellingPath;
	}

	public String getGalleryBkgPath() {
		return galleryBkgPath;
	}

	public void setGalleryBkgPath(String galleryBkgPath) {
		this.galleryBkgPath = galleryBkgPath;
	}

	public int getQuickDrink() {
		return quickDrink;
	}

	public void setQuickDrink(int quickDrink) {
		this.quickDrink = quickDrink;
	}

	@Override
	public String toString() {
		return "BeverageUi{" +
				"id=" + id +
				", pid=" + pid +
				", iconPath='" + iconPath + '\'' +
				", sortIndex=" + sortIndex +
				", showOrhide=" + showOrhide +
				", iconSize=" + iconSize +
				", storyTellingPath='" + storyTellingPath + '\'' +
				", dispenseTellingPath='" + dispenseTellingPath + '\'' +
				", galleryBkgPath='" + galleryBkgPath + '\'' +
				", quickDrink=" + quickDrink +
				", name='" + name + '\'' +
				'}';
	}
}
