package android.luna.Data.DAO;

import android.content.Context;
import android.luna.Activity.Base.CremApp;
import android.luna.Data.Interface.IDisplaySoundSettings;
import android.luna.Data.Interface.ISecretSettings;
import android.luna.Data.Interface.ISmartSettings;
import android.luna.Data.Interface.ISystemSettingFactory;
import android.luna.Data.module.System.DisplaySoundSettings;
import android.luna.Data.module.System.SecretSettings;
import android.luna.Data.module.System.SmartSettings;

import java.sql.SQLException;

/**
 * Created by Lee.li on 2018/8/7.
 */

public class SystemSettingFactory extends BaseDaobak implements ISystemSettingFactory  {
    public SystemSettingFactory(Context context, CremApp app) {
        super(context, app);
    }

    private ISmartSettings    smartSettingsdao=null;
    private ISecretSettings   secretSettingsdao=null;
    private IDisplaySoundSettings displaySoundSettingsdao=null;

    @Override
    public ISmartSettings getSmartSettingDao() {
        if(smartSettingsdao==null)
            smartSettingsdao = new ISmartSettings() {
                @Override
                public void loaddefault() {

                }

                @Override
                public void modify(SmartSettings smartSettings) {
                    try {
                        getHelper().get_smartSettingsIntegerDao().update(smartSettings);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void delete() {

                }

                @Override
                public void create(SmartSettings smartSettings) {

                }

                @Override
                public SmartSettings query() {
                    try {
                        SmartSettings ret = getHelper().get_smartSettingsIntegerDao().queryForId(1);
                        if(ret ==null)
                        {
                            getHelper().get_smartSettingsIntegerDao().create(new SmartSettings());
                            return getHelper().get_smartSettingsIntegerDao().queryForId(1);
                        }
                        return ret;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        return smartSettingsdao;
    }

    @Override
    public ISecretSettings getSecretSettingDao() {
        if(secretSettingsdao == null)
            secretSettingsdao = new ISecretSettings() {
                @Override
                public void loaddefault() {

                }

                @Override
                public void modify(SecretSettings secretSettings) {
                    try {
                        getHelper().get_secretSettingsIntegerDao().update(secretSettings);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void delete() {

                }

                @Override
                public void create(SecretSettings secretSettings) {

                }

                @Override
                public SecretSettings query() {
                    try {
                        SecretSettings ret = getHelper().get_secretSettingsIntegerDao().queryForId(1);
                        if(ret ==null)
                        {
                            getHelper().get_secretSettingsIntegerDao().create(new SecretSettings());
                            return getHelper().get_secretSettingsIntegerDao().queryForId(1);
                        }
                        return ret;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        return secretSettingsdao;
    }

    @Override
    public IDisplaySoundSettings getDisplaySoundSetting() {
        if(displaySoundSettingsdao==null)
            displaySoundSettingsdao =new IDisplaySoundSettings() {
                @Override
                public void loaddefault() {

                }

                @Override
                public void modify(DisplaySoundSettings displaySoundSettings) {
                    try {
                        getHelper().get_displaySoundSettingsIntegerDao().update(displaySoundSettings);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void delete() {

                }

                @Override
                public void create(DisplaySoundSettings displaySoundSettings) {

                }

                @Override
                public DisplaySoundSettings query() {
                    try {
                        DisplaySoundSettings ret = getHelper().get_displaySoundSettingsIntegerDao().queryForId(1);
                        if(ret ==null)
                        {
                            getHelper().get_displaySoundSettingsIntegerDao().create(new DisplaySoundSettings());
                            return getHelper().get_displaySoundSettingsIntegerDao().queryForId(1);
                        }
                        return ret;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        return displaySoundSettingsdao;
    }
}
