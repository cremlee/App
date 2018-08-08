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

                }

                @Override
                public void delete() {

                }

                @Override
                public void create(SmartSettings smartSettings) {

                }

                @Override
                public SmartSettings query() {
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

                }

                @Override
                public void delete() {

                }

                @Override
                public void create(SecretSettings secretSettings) {

                }

                @Override
                public SecretSettings query() {
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

                }

                @Override
                public void delete() {

                }

                @Override
                public void create(DisplaySoundSettings displaySoundSettings) {

                }

                @Override
                public DisplaySoundSettings query() {
                    return null;
                }
            };
        return displaySoundSettingsdao;
    }
}
