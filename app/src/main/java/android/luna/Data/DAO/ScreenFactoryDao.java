package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.ILanguageitem;
import android.luna.Data.Interface.IScreenFactory;
import android.luna.Data.Interface.IScreenSetting;
import android.luna.Data.Interface.IThemeCloud;
import android.luna.Data.Interface.IThemeGallery;
import android.luna.Data.Interface.IThemeNormal;
import android.luna.Data.module.Languageitem;
import android.luna.Data.module.ScreenSettings;
import android.luna.Data.module.ThemeCould;
import android.luna.Data.module.ThemeGallery;
import android.luna.Data.module.ThemeNormal;

import java.sql.SQLException;

/**
 * Created by Lee.li on 2018/3/1.
 */

public class ScreenFactoryDao extends BaseDaobak implements IScreenFactory {
    public ScreenFactoryDao(Context context, CremApp app) {
        super(context, app);
    }

    private IScreenSetting _ScreenSettingDao=null;
    private IThemeNormal _NormalDao=null;
    private IThemeGallery _GalleryDao=null;
    private IThemeCloud _CloudDao=null;
    private ILanguageitem _Languageitem=null;

    @Override
    public IScreenSetting getScreenSettingDao() {
        if(_ScreenSettingDao == null)
            _ScreenSettingDao =new IScreenSetting(){
                @Override
                public ScreenSettings query() {
                    try {
                       return getApp().getHelper().get_ScreenSettingsDao().queryForId(1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(ScreenSettings screenSettings) {
                    screenSettings.setId(1);
                    try {
                         getApp().getHelper().get_ScreenSettingsDao().createOrUpdate(screenSettings);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }
            };
        return _ScreenSettingDao;
    }

    @Override
    public IThemeNormal getNormalDao() {
        if(_NormalDao==null)
        {
            _NormalDao = new IThemeNormal() {
                @Override
                public ThemeNormal query() {
                    try {
                        return getApp().getHelper().get_ThemeNormalDao().queryForId(1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(ThemeNormal themeNormal) {
                    themeNormal.setId(1);
                    try {
                        getApp().getHelper().get_ThemeNormalDao().createOrUpdate(themeNormal);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            };
        }
        return  _NormalDao;
    }

    @Override
    public IThemeGallery getGalleryDao() {
        if(_GalleryDao == null)
        {
            _GalleryDao =new IThemeGallery() {
                @Override
                public ThemeGallery query() {
                    try {
                        return getApp().getHelper().get_ThemeGalleryDao().queryForId(1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(ThemeGallery themeGallery) {
                    themeGallery.setId(1);
                    try {
                         getApp().getHelper().get_ThemeGalleryDao().createOrUpdate(themeGallery);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            };
        }
        return _GalleryDao;
    }

    @Override
    public IThemeCloud getCloudDao() {
        if(_CloudDao ==null)
        {
            _CloudDao =new IThemeCloud() {
                @Override
                public ThemeCould query() {
                    try {
                        return getApp().getHelper().get_ThemeCouldDao().queryForId(1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(ThemeCould themeCould) {
                    themeCould.setId(1);
                    try {
                           getApp().getHelper().get_ThemeCouldDao().createOrUpdate(themeCould);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }
            };
        }
        return _CloudDao;
    }

    @Override
    public ILanguageitem getLanguageitemDao() {
        if(_Languageitem ==null)
        {
            _Languageitem = new ILanguageitem() {
                @Override
                public Languageitem query() {
                    try {
                        return getApp().getHelper().get_LanguageitemIntegerDao().queryForId(1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                public int update(Languageitem languageitem) {
                    languageitem.setId(1);
                    try {
                         getApp().getHelper().get_LanguageitemIntegerDao().createOrUpdate(languageitem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return 1;
                }
            };
        }
        return  _Languageitem;
    }
}
