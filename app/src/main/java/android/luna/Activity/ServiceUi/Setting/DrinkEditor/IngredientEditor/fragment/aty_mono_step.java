package android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment;

import android.luna.Activity.Base.AppManager;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.monostepAdapter;
import android.luna.Data.DAO.MonoStepDao;
import android.luna.Data.module.IngredientMonoProcess;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.widget.MySpinerAdapter;
import android.luna.ViewUi.widget.SettingItemDropDown;
import android.luna.ViewUi.widget.SettingItemSeekBar;
import android.luna.ViewUi.widget.SettingItemTextView2;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evo.luna.android.R;

public class aty_mono_step extends BaseActivity implements View.OnClickListener {

    private Button btn_close,add;
    private ListView lv_step;
    private SettingItemTextView2 stepindex;
    private SettingItemSeekBar infusiontime,infusionvolume,infusiontemp;
    private SettingItemDropDown infusiontype;
    private SettingItemSeekBar bubSpeedItem,bubTimeItem,outletspeed,outlettime,presstime;
    private SettingItemSeekBar  dispensevolume,dispensetemp;
    private SettingItemDropDown dispensetype;

    private LinearLayout stepinfo;

    private MonoStepDao monoStepDao;
    private List<IngredientMonoProcess> monoProcesses;
    private int ingredientpid;
    private IngredientMonoProcess _monoProcess;
    private monostepAdapter _monostepAdapter;
    private Map<String, String> map = new HashMap<>();
    private void initmap()
    {
        String[] strings = getResources().getStringArray(R.array.filter_water_value);
        int keys[] = getResources().getIntArray(R.array.filter_water_key);
        for (int i = 0; i < strings.length; i++) {
            map.put(String.valueOf(i), strings[i]);
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        updateui(0);
    }

    @Override
    public void InitData() {
        super.InitData();
        setContentView(R.layout.aty_mono_step);
        initmap();
        monoStepDao = new MonoStepDao(this,getApp());
        ingredientpid = getIntent().getIntExtra("ingredientpid",0);
        if(ingredientpid!=0)
        {
            monoProcesses = monoStepDao.getmonosteps(ingredientpid);
        }
        _monostepAdapter = new monostepAdapter(this,monoProcesses);

        stepinfo = findViewById(R.id.stepinfo);
        btn_close = findViewById(R.id.btn_close);
        add = findViewById(R.id.add);
        lv_step = findViewById(R.id.lv_step);
        stepindex = findViewById(R.id.stepindex);

        infusiontype = findViewById(R.id.infusiontype);
        infusiontime = findViewById(R.id.infusiontime);
        infusionvolume = findViewById(R.id.infusionvolume);
        infusiontemp = findViewById(R.id.infusiontemp);

        bubSpeedItem = findViewById(R.id.bubSpeedItem);
        bubTimeItem = findViewById(R.id.bubTimeItem);
        outletspeed = findViewById(R.id.outletspeed);
        outlettime = findViewById(R.id.outlettime);
        presstime = findViewById(R.id.presstime);

        dispensetype = findViewById(R.id.dispensetype);
        dispensevolume = findViewById(R.id.dispensevolume);
        dispensetemp = findViewById(R.id.dispensetemp);

        lv_step.setAdapter(_monostepAdapter);
        lv_step.setSelector(R.color.material_blue_400);

        infusiontype.setItemAndValues(map);
        infusiontype.refreshData(0);
        dispensetype.setItemAndValues(map);
        dispensetype.refreshData(0);
    }

    private void updateui(int index)
    {
        if(_monoProcess==null)
        {
            stepinfo.setVisibility(View.INVISIBLE);
            return;
        }
        stepinfo.setVisibility(View.VISIBLE);
        stepindex.setTextTitle(getResources().getString(R.string.SVR_DRINK_INGREDIENT_MONO_STEP)+_monoProcess.getStepindex());

        //infusiontype = findViewById(R.id.infusiontype);
        String key = String.valueOf(_monoProcess.getInfusion_type());
        infusiontype.setSelItem(key, infusiontype.getItemAndValues().get(key));
        infusiontime.setCur(_monoProcess.getInfusion_tm());
        infusionvolume.setCur(_monoProcess.getInfusion_volume());
        infusiontemp.setCur(_monoProcess.getInfusion_temperature());

        bubSpeedItem.setCur(_monoProcess.getBubpump_spd());
        bubTimeItem.setCur(_monoProcess.getBubpump_tm());
        outletspeed.setCur(_monoProcess.getOutlet_speed());
        outlettime.setCur(_monoProcess.getOutlet_tm());

        presstime.setCur(_monoProcess.getPress_tm());
        key = String.valueOf(_monoProcess.getDispense_type());
        dispensetype.setSelItem(key, dispensetype.getItemAndValues().get(key));
        //dispensetype = findViewById(R.id.dispensetype);
        dispensevolume.setCur(_monoProcess.getDispense_volume());
        dispensetemp.setCur(_monoProcess.getDispense_temperature());

    }
    private void savestep()
    {
        monoStepDao.updatemonosteplist(ingredientpid,monoProcesses);
    }
    @Override
    public void InitEvent() {
        super.InitEvent();
        lv_step.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO: 2018/11/28
                if(_monoProcess!=null&&id == _monoProcess.getStepindex())
                    return;
                showTestToast("setOnItemClickListener:"+id);
                _monoProcess = monoProcesses.get(position);
                updateui((int) id);
            }
        });
        btn_close.setOnClickListener(this);
        add.setOnClickListener(this);
        stepindex.setOnClickListener(this);

        infusiontype.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = infusiontype.getItemAndValues().get(key);
                infusiontype.setSelItem(key, name);
                _monoProcess.setInfusion_type(Integer.valueOf(key));
            }
        });

        dispensetype.getSpinerPopWindow().setItemListener(new MySpinerAdapter.OnItemSelectListener() {
            @Override
            public void onItemClick(String key) {
                String name = dispensetype.getItemAndValues().get(key);
                dispensetype.setSelItem(key, name);
                _monoProcess.setDispense_type(Integer.valueOf(key));
            }
        });

        infusiontime.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += infusiontime.getMin();
                    infusiontime.setCur(volume);
                    _monoProcess.setInfusion_tm(volume);
                }
            }
        });

        infusionvolume.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += infusionvolume.getMin();
                    infusionvolume.setCur(volume);
                    _monoProcess.setInfusion_volume(volume);
                }
            }
        });

        infusiontemp.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += infusiontemp.getMin();
                    infusiontemp.setCur(volume);
                    _monoProcess.setInfusion_temperature(volume);
                }
            }
        });





        bubSpeedItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += bubSpeedItem.getMin();
                    bubSpeedItem.setCur(volume);
                    _monoProcess.setBubpump_spd(volume);
                }
            }
        });

        bubTimeItem.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += bubTimeItem.getMin();
                    bubTimeItem.setCur(volume);
                    _monoProcess.setBubpump_tm(volume);
                }
            }
        });

        outletspeed.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += outletspeed.getMin();
                    outletspeed.setCur(volume);
                    _monoProcess.setOutlet_speed(volume);
                }
            }
        });

        outlettime.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += outlettime.getMin();
                    outlettime.setCur(volume);
                    _monoProcess.setOutlet_tm(volume);
                }
            }
        });

        presstime.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += presstime.getMin();
                    presstime.setCur(volume);
                    _monoProcess.setPress_tm(volume);
                }
            }
        });


        dispensevolume.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += dispensevolume.getMin();
                    dispensevolume.setCur(volume);
                    _monoProcess.setDispense_volume(volume);
                }
            }
        });
        dispensetemp.getSeekbar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int volume = progress;
                if (fromUser) {
                    volume += dispensetemp.getMin();
                    dispensetemp.setCur(volume);
                    _monoProcess.setDispense_temperature(volume);
                }
            }
        });
    }

    private void restore()
    {
        if(monoProcesses!=null&&monoProcesses.size()>0)
        {
            for (int i=1;i<=monoProcesses.size();i++)
            {
                monoProcesses.get(i-1).setStepindex(i);
            }
        }
    }
    private MaterialDialog progressDialog;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_close:
                savestep();
                AppManager.getAppManager().finishActivity(aty_mono_step.this);
                break;
            case R.id.add:
                if(monoProcesses==null)
                    monoProcesses = new ArrayList<>();
                monoProcesses.add(new IngredientMonoProcess());
                restore();
                _monostepAdapter.set_data(monoProcesses);
                lv_step.performItemClick(lv_step.getChildAt(monoProcesses.size()-1),monoProcesses.size()-1,monoProcesses.size());
                lv_step.setSelection(monoProcesses.size()-1);
                _monostepAdapter.notifyDataSetChanged();
                break;
            case R.id.stepindex:
                // TODO: 2018/11/28 show confirm window
                progressDialog = new MaterialDialog.Builder(aty_mono_step.this)
                        .title("Delete")
                        .content("Do you want to delete this step ?")
                        .positiveText("Yes")
                        .positiveColor(getResources().getColor(R.color.green_grass))
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                monoProcesses.remove(_monoProcess);
                                _monoProcess =null;
                                restore();
                                updateui(0);
                                _monostepAdapter.notifyDataSetChanged();
                            }
                        })
                        .negativeText("No")
                        .negativeColor(getResources().getColor(R.color.red_wine))
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                progressDialog.dismiss();
                            }
                        })
                        .canceledOnTouchOutside(false)
                        .show();
                break;
        }
    }
}
