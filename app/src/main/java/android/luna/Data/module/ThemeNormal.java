package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/2/28.
 */
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tb_theme_normal")
public class ThemeNormal {
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "1") //1:2*5  2:3*5
    private  int layouttype;

    public ThemeNormal() {
    }

    public ThemeNormal(int layouttype) {
        this.layouttype = layouttype;
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

    @Override
    public String toString() {
        return "ThemeNormal{" +
                "id=" + id +
                ", layouttype=" + layouttype +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThemeNormal that = (ThemeNormal) o;

        if (getId() != that.getId()) return false;
        return getLayouttype() == that.getLayouttype();

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getLayouttype();
        return result;
    }
}
