package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/2/28.
 */
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tb_screensetting")
public class ScreenSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId=true)
    private int id;
    @DatabaseField(defaultValue="0")
    private int textcolor;
    @DatabaseField
    private String mainbkgpath="";
    @DatabaseField
    private String bannerpath="";
    @DatabaseField(defaultValue="0")
    private int screensaverflag;
    @DatabaseField
    private String screensaverpath="";
    @DatabaseField
    private String ecopath="";
    @DatabaseField
    private String ecoinfo="";
    @DatabaseField(defaultValue="0")
    private int showlanguage;
    @DatabaseField(defaultValue="0")
    private int showprofile;
    @DatabaseField(defaultValue="0")
    private int showfavourite;
    @DatabaseField(defaultValue="1")      //1 normal 2 gallery 3 cloud 4 shop 5 group-1 6 group-2
    private int themetype=1;
    @DatabaseField(defaultValue="0")
    private int logoflag;
    @DatabaseField
    private String logopath="";
    @DatabaseField(defaultValue="1")
    private int uichangestyle;
    @DatabaseField(defaultValue="0")
    private int uihelp;

    public ScreenSettings() {
    }

    public ScreenSettings(int textcolor, String mainbkgpath, String bannerpath, int screensaverflag, String screensaverpath, String ecopath, String ecoinfo, int showlanguage, int showfavourite, int themetype, int logoflag, String logopath, int uichangestyle) {
        this.textcolor = textcolor;
        this.mainbkgpath = mainbkgpath;
        this.bannerpath = bannerpath;
        this.screensaverflag = screensaverflag;
        this.screensaverpath = screensaverpath;
        this.ecopath = ecopath;
        this.ecoinfo = ecoinfo;
        this.showlanguage = showlanguage;
        this.showfavourite = showfavourite;
        this.themetype = themetype;
        this.logoflag = logoflag;
        this.logopath = logopath;
        this.uichangestyle = uichangestyle;
    }

    public ScreenSettings(int textcolor, String mainbkgpath, String bannerpath, int screensaverflag, String screensaverpath, String ecopath, String ecoinfo, int showlanguage, int showprofile, int showfavourite, int themetype, int logoflag, String logopath, int uichangestyle, int uihelp) {
        this.textcolor = textcolor;
        this.mainbkgpath = mainbkgpath;
        this.bannerpath = bannerpath;
        this.screensaverflag = screensaverflag;
        this.screensaverpath = screensaverpath;
        this.ecopath = ecopath;
        this.ecoinfo = ecoinfo;
        this.showlanguage = showlanguage;
        this.showprofile = showprofile;
        this.showfavourite = showfavourite;
        this.themetype = themetype;
        this.logoflag = logoflag;
        this.logopath = logopath;
        this.uichangestyle = uichangestyle;
        this.uihelp = uihelp;
    }

    public int getTextcolor() {
        return textcolor;
    }

    public void setTextcolor(int textcolor) {
        this.textcolor = textcolor;
    }

    public String getMainbkgpath() {
        return mainbkgpath;
    }

    public void setMainbkgpath(String mainbkgpath) {
        this.mainbkgpath = mainbkgpath;
    }

    public String getBannerpath() {
        return bannerpath;
    }

    public void setBannerpath(String bannerpath) {
        this.bannerpath = bannerpath;
    }

    public int getScreensaverflag() {
        return screensaverflag;
    }

    public void setScreensaverflag(int screensaverflag) {
        this.screensaverflag = screensaverflag;
    }

    public String getScreensaverpath() {
        return screensaverpath;
    }

    public void setScreensaverpath(String screensaverpath) {
        this.screensaverpath = screensaverpath;
    }

    public String getEcopath() {
        return ecopath;
    }

    public void setEcopath(String ecopath) {
        this.ecopath = ecopath;
    }

    public String getEcoinfo() {
        return ecoinfo;
    }

    public void setEcoinfo(String ecoinfo) {
        this.ecoinfo = ecoinfo;
    }

    public int getShowlanguage() {
        return showlanguage;
    }

    public void setShowlanguage(int showlanguage) {
        this.showlanguage = showlanguage;
    }

    public int getShowfavourite() {
        return showfavourite;
    }

    public void setShowfavourite(int showfavourite) {
        this.showfavourite = showfavourite;
    }

    public int getThemetype() {
        return themetype;
    }

    public void setThemetype(int themetype) {
        this.themetype = themetype;
    }

    public int getLogoflag() {
        return logoflag;
    }

    public void setLogoflag(int logoflag) {
        this.logoflag = logoflag;
    }

    public String getLogopath() {
        return logopath;
    }

    public void setLogopath(String logopath) {
        this.logopath = logopath;
    }

    public int getUichangestyle() {
        return uichangestyle;
    }

    public void setUichangestyle(int uichangestyle) {
        this.uichangestyle = uichangestyle;
    }

    public int getUihelp() {
        return uihelp;
    }

    public void setUihelp(int uihelp) {
        this.uihelp = uihelp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShowprofile() {
        return showprofile;
    }

    public void setShowprofile(int showprofile) {
        this.showprofile = showprofile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScreenSettings that = (ScreenSettings) o;

        if (id != that.id) return false;
        if (getTextcolor() != that.getTextcolor()) return false;
        if (getScreensaverflag() != that.getScreensaverflag()) return false;
        if (getShowlanguage() != that.getShowlanguage()) return false;
        if (getShowfavourite() != that.getShowfavourite()) return false;
        if (getThemetype() != that.getThemetype()) return false;
        if (getLogoflag() != that.getLogoflag()) return false;
        if (getUichangestyle() != that.getUichangestyle()) return false;
        if (getUihelp() != that.getUihelp()) return false;
        if (!getMainbkgpath().equals(that.getMainbkgpath())) return false;
        if (!getBannerpath().equals(that.getBannerpath())) return false;
        if (!getScreensaverpath().equals(that.getScreensaverpath())) return false;
        if (!getEcopath().equals(that.getEcopath())) return false;
        if (!getEcoinfo().equals(that.getEcoinfo())) return false;
        return getLogopath().equals(that.getLogopath());

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + getTextcolor();
        result = 31 * result + getMainbkgpath().hashCode();
        result = 31 * result + getBannerpath().hashCode();
        result = 31 * result + getScreensaverflag();
        result = 31 * result + getScreensaverpath().hashCode();
        result = 31 * result + getEcopath().hashCode();
        result = 31 * result + getEcoinfo().hashCode();
        result = 31 * result + getShowlanguage();
        result = 31 * result + getShowfavourite();
        result = 31 * result + getThemetype();
        result = 31 * result + getLogoflag();
        result = 31 * result + getLogopath().hashCode();
        result = 31 * result + getUichangestyle();
        result = 31 * result + getUihelp();
        return result;
    }

    @Override
    public String toString() {
        return "ScreenSettings{" +
                "id=" + id +
                ", textcolor=" + textcolor +
                ", mainbkgpath='" + mainbkgpath + '\'' +
                ", bannerpath='" + bannerpath + '\'' +
                ", screensaverflag=" + screensaverflag +
                ", screensaverpath='" + screensaverpath + '\'' +
                ", ecopath='" + ecopath + '\'' +
                ", ecoinfo='" + ecoinfo + '\'' +
                ", showlanguage=" + showlanguage +
                ", showfavourite=" + showfavourite +
                ", themetype=" + themetype +
                ", logoflag=" + logoflag +
                ", logopath='" + logopath + '\'' +
                ", uihelp='" + uihelp + '\'' +
                ", uichangestyle=" + uichangestyle +
                '}';
    }
}
