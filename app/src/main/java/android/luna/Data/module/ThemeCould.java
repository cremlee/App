package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/2/28.
 */
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="tb_theme_normal")
public class ThemeCould {
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "1") //1:2*5  2:3*5
    private  int layouttype;

    public ThemeCould() {
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
}
