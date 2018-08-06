package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/2/28.
 */
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tb_theme_gallery")
public class ThemeGallery {
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "1") //1:5  2:3
    private  int layouttype;
    @DatabaseField(defaultValue = "0.8")
    private  float minscale;
    @DatabaseField(defaultValue = "1.5")
    private  float maxscale;

    public ThemeGallery() {
    }

    public ThemeGallery(int layouttype, float minscale, float maxscale) {
        this.layouttype = layouttype;
        this.minscale = minscale;
        this.maxscale = maxscale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLayouttype() {
        return layouttype;
    }

    public void setLayouttype(int layouttype) {
        this.layouttype = layouttype;
    }

    public float getMinscale() {
        return minscale;
    }

    public void setMinscale(float minscale) {
        this.minscale = minscale;
    }

    public float getMaxscale() {
        return maxscale;
    }

    public void setMaxscale(float maxscale) {
        this.maxscale = maxscale;
    }

    @Override
    public String toString() {
        return "ThemeGallery{" +
                "id=" + id +
                ", layouttype=" + layouttype +
                ", minscale=" + minscale +
                ", maxscale=" + maxscale +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThemeGallery that = (ThemeGallery) o;

        if (getId() != that.getId()) return false;
        if (getLayouttype() != that.getLayouttype()) return false;
        if (Float.compare(that.getMinscale(), getMinscale()) != 0) return false;
        return Float.compare(that.getMaxscale(), getMaxscale()) == 0;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLayouttype();
        result = 31 * result + (getMinscale() != +0.0f ? Float.floatToIntBits(getMinscale()) : 0);
        result = 31 * result + (getMaxscale() != +0.0f ? Float.floatToIntBits(getMaxscale()) : 0);
        return result;
    }
}
