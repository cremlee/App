package android.luna.Data.Interface;

/**
 * Created by Lee.li on 2018/8/7.
 */

public interface ISystemSettingFactory  {
    ISmartSettings getSmartSettingDao();
    ISecretSettings getSecretSettingDao();
    IDisplaySoundSettings getDisplaySoundSetting();
}
