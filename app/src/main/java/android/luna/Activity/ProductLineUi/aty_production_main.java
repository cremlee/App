package android.luna.Activity.ProductLineUi;
import android.content.Intent;
import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ProductLineUi.Fragment.Fgt_production_calibration;
import android.luna.Activity.ProductLineUi.Fragment.Fgt_production_config;
import android.luna.Activity.ProductLineUi.Fragment.Fgt_production_dryclean;
import android.luna.Activity.ProductLineUi.Fragment.Fgt_production_report;
import android.luna.Activity.ProductLineUi.Fragment.Fgt_production_test;
import android.os.Bundle;
import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/7/4.
 */

public class aty_production_main extends BaseActivity implements Fgt_production_dryclean.nextListerner, Fgt_production_report.nextListerner, Fgt_production_config.nextListerner ,Fgt_production_test.nextListerner,Fgt_production_calibration.nextListerner{
    private Fgt_production_config production_config;
    private Fgt_production_test productionTest;
    private Fgt_production_calibration productionCalibration;
    private Fgt_production_report productionReport;
    private Fgt_production_dryclean productionDryclean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getApp().bindAllService();
        super.onCreate(savedInstanceState);
        if(production_config==null)
        {
            production_config = new Fgt_production_config();
            production_config.setOnnextListerner(this);
        }
        /*if(productionDryclean==null)
        {
            productionDryclean = new Fgt_production_dryclean();
            productionDryclean.setOnnextListerner(this);
        }*/

        getFragmentManager().beginTransaction().replace(R.id.tb_fun,production_config).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void InitView() {
        super.InitView();
        setContentView(R.layout.aty_production_base);
    }

    @Override
    public void InitData() {
        super.InitData();
    }

    @Override
    public void InitEvent() {
        super.InitEvent();
    }

    @Override
    public void Clicked(int a) {
        switch (a)
        {
            case 1:
                if(productionTest==null)
                {
                    productionTest = new Fgt_production_test();
                    productionTest.setOnnextListerner(this);
                }
                getFragmentManager().beginTransaction().replace(R.id.tb_fun,productionTest).commit();
                break;
            case 2:
                if(productionCalibration==null)
                {
                    productionCalibration = new Fgt_production_calibration();
                    productionCalibration.setOnnextListerner(this);
                }
                getFragmentManager().beginTransaction().replace(R.id.tb_fun,productionCalibration).commit();
                break;
            case 3:
                if(productionReport==null)
                {
                    productionReport = new Fgt_production_report();
                    productionReport.setOnnextListerner(this);
                }
                getFragmentManager().beginTransaction().replace(R.id.tb_fun,productionReport).commit();
                break;
            case 4:
                if(productionDryclean==null)
                {
                    productionDryclean = new Fgt_production_dryclean();
                    productionDryclean.setOnnextListerner(this);
                }
                getFragmentManager().beginTransaction().replace(R.id.tb_fun,productionDryclean).commit();
                break;
            case 5:
                AppManager.getAppManager().finishActivity(aty_production_main.this);
                break;
        }
    }

    @Override
    public void UpdateLanguage() {
        restartAct();
    }

    private void restartAct() {
        finish();
        Intent _Intent = new Intent(this, aty_production_main.class);
        startActivity(_Intent);
        //清除Activity退出和进入的动画
        overridePendingTransition(0, 0);
    }
}
