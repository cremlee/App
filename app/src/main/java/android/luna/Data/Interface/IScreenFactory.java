package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/3/1.
 */

public interface IScreenFactory {
    IScreenSetting getScreenSettingDao();
    IThemeNormal getNormalDao();
    IThemeGallery getGalleryDao();
    IThemeCloud getCloudDao();
    ILanguageitem getLanguageitemDao();
}
