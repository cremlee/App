package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/5/16.
 */

public class IconDateBean {
    private  String name;
    private  String path;

    public IconDateBean(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
