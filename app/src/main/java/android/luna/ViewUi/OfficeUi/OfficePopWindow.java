package android.luna.ViewUi.OfficeUi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.luna.Activity.Base.BaseActivity;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.BeverageEditor.aty_beverage_maker;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.IngredientEditor.fragment.FilterbrewFragment;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.adapter.GroupAdapter;
import android.luna.Activity.ServiceUi.Setting.DrinkEditor.decoration.DividerItemDecoration;
import android.luna.Data.CustomerUI.DrinkMenuButton;
import android.luna.Data.DAO.BeverageFactoryDao;
import android.luna.Data.DAO.PersonFactoryDao;
import android.luna.Data.module.BeverageUi;
import android.luna.Data.module.IconDateBean;
import android.luna.Data.module.PersonDrink;
import android.luna.Data.module.PersonItem;
import android.luna.Utils.FileHelper;
import android.luna.Utils.PictureManager;
import android.luna.ViewUi.MaterialDialog.DialogAction;
import android.luna.ViewUi.MaterialDialog.MaterialDialog;
import android.luna.ViewUi.OfficeUi.Adapter.DrinkSpinnerAdapter;
import android.luna.ViewUi.OfficeUi.Adapter.RecyclerViewGridAdapter;
import android.luna.ViewUi.OfficeUi.Fragment.PasswordFragment;
import android.luna.ViewUi.OfficeUi.Fragment.PersonFragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import evo.luna.android.R;

/**
 * Created by Lee.li on 2018/4/26.
 */

public class OfficePopWindow extends PopupWindow implements View.OnClickListener,View.OnDragListener {
    private View conentView;
    private BaseActivity mcontext;
    private List<PersonItem> _personItems =new ArrayList<>();
    private RecyclerView m_rv;
    private IndexBar indexBar;
    private TextView tvSideBarHint,fun_drag;

    private EditText fun_name,fun_icon_name;
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;
    private PersonAdapter mAdapter;
    private ImageView icon;
    private RelativeLayout person_drink,lyt_psw,fun_drink_edt,fun_icon_edt;
    private PersonFactoryDao personFactoryDao =null;
    private BeverageFactoryDao beverageFactoryDao=null;
    private boolean iseditmode =false;
    ////edit part////
    private CheckBox switch_psw;
    private RelativeLayout drk_1_lyt,drk_2_lyt,drk_3_lyt,drk_4_lyt;
    private ToggleButton switch_edit;
    private List<PersonDrink> personDrinks =new ArrayList<>(4);
    private PersonDrink _currenPersonDrink=null;
    private PatternLockView person_psw;
    private ImageView drk_1_icon,drk_2_icon,drk_3_icon,drk_4_icon;
    private TextView drk_1_name,drk_2_name,drk_3_name,drk_4_name,txt_psw;
    private PersonItem _currentPerson;
    private Button btn_remove,btn_changpsw,fun_btn_set,fun_drink_close,fun_btn_icon_set,fun_icon_close;
    private List<DrinkMenuButton> drinkMenuButtonList;
    private DrinkSpinnerAdapter drinkSpinnerAdapter;
    private ListView fun_sp;

    private RecyclerView rv_icon;
    private RecyclerViewGridAdapter recyclerViewGridAdapter;
    private List<IconDateBean> _icondata = new ArrayList<>(20);
    //preselection
    private RelativeLayout strengthItem,volumeItem,milkItem;
    private RatingBar strengthBatingBar,volumeBatingBar,milkBatingBar;
    private Button exit;

  // 0-idle   1-change_input_old 2-change_new_first 3-change_new_confirm 4-create new profile
    private int mode_psw =0;
    private String psw_first="";
    //0-idel ,1-4 drink1 -drink4 ,9 icon
    private  int mode_edt =0;

    private String iconpath = "";

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        int id = v.getId();
        switch (action) {
            case DragEvent.ACTION_DROP:
                if (id == R.id.fun_drag) {
                    iconpath = event.getClipData().getDescription().getLabel().toString();
                    icon.setImageBitmap(PictureManager.getInstance().getBitmapFromMemCache(iconpath));

                }
                break;
        }
        return true;
    }

    public interface OnQuickStartClick
    {
        void Start(PersonDrink p);
    }

    private  OnQuickStartClick _OnQuickStartClick =null;

    public void SetOnQuickStartClick(OnQuickStartClick a)
    {
        this._OnQuickStartClick =a;
    }

    public OfficePopWindow(final BaseActivity context, List<PersonItem> a, PersonFactoryDao b, BeverageFactoryDao c ) {
        super(context);
        this.setFocusable(true);
        this.mcontext = context;
        this._personItems =a;
        this.personFactoryDao = b;
        this.beverageFactoryDao =c;
        drinkMenuButtonList = beverageFactoryDao.getDrinkIconItems(beverageFactoryDao.getBeverageNameDao().getlocalinfo());
        drinkSpinnerAdapter = new DrinkSpinnerAdapter(this.mcontext,drinkMenuButtonList);
        mAdapter = new PersonAdapter(this.mcontext,this._personItems);
        this.initPopupWindow();
    }

    private void setImagerec(ImageView scr,int pid)
    {

       BeverageUi tmp = beverageFactoryDao.getBeverageUiDao().query(pid);
        if(tmp!=null)
        {
            if(tmp.getIconPath()!=null && !tmp.getIconPath().equals(""))
            {
                Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(tmp.getIconPath());
                if (bitmap == null) {
                    final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(tmp.getIconPath(), 120, 120);
                    if (tmpbitmap != null) {
                        PictureManager.getInstance().addBitmapToMemoryCache(tmp.getIconPath(), tmpbitmap);
                        bitmap = PictureManager.getInstance().getBitmapFromMemCache(tmp.getIconPath());
                    }
                }
                if (bitmap != null)
                    scr.setImageBitmap(bitmap);
                else
                    scr.setImageResource(R.mipmap.ic_add);
            }
            else
            {
                scr.setImageResource(R.mipmap.ic_add);
            }
        }
        else
        {
            scr.setImageResource(R.mipmap.ic_add);
        }
    }
    private void HideEdit()
    {
        iseditmode =false;
        switch_psw.setVisibility(View.INVISIBLE);
        btn_remove.setVisibility(View.INVISIBLE);
        btn_changpsw.setVisibility(View.INVISIBLE);
        drk_1_lyt.setVisibility(View.INVISIBLE);
        drk_2_lyt.setVisibility(View.INVISIBLE);
        drk_3_lyt.setVisibility(View.INVISIBLE);
        drk_4_lyt.setVisibility(View.INVISIBLE);
    }

    private void ShowEdit()
    {
        iseditmode =true;
        btn_remove.setVisibility(View.VISIBLE);
        btn_changpsw.setVisibility(View.VISIBLE);
        switch_psw.setVisibility(View.VISIBLE);
        drk_1_lyt.setVisibility(View.VISIBLE);
        drk_2_lyt.setVisibility(View.VISIBLE);
        drk_3_lyt.setVisibility(View.VISIBLE);
        drk_4_lyt.setVisibility(View.VISIBLE);
    }
    private void initPopupWindow()
    {
        LayoutInflater inflater = (LayoutInflater) mcontext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.aty_office_ui, null);
        this.setContentView(conentView);

        fun_drag = conentView.findViewById(R.id.fun_drag);
        fun_drag.setOnDragListener(this);
        fun_icon_name= conentView.findViewById(R.id.fun_icon_name);
        fun_btn_icon_set = conentView.findViewById(R.id.fun_btn_icon_set);
        fun_btn_icon_set.setOnClickListener(this);
        fun_icon_close = conentView.findViewById(R.id.fun_icon_close);
        fun_icon_close.setOnClickListener(this);
        rv_icon = conentView.findViewById(R.id.rv_icon);
        if(!mcontext.getApp().getUsbpath().equals("")) {
            List<String> files = FileHelper.getIconFile(new File(mcontext.getApp().getUsbpath() + "/profile/"));
            for (String file : files) {
                _icondata.add(new IconDateBean(file.substring(file.lastIndexOf("/")+1), file));
            }
        }
        recyclerViewGridAdapter = new RecyclerViewGridAdapter(mcontext,_icondata);
        rv_icon.setAdapter(recyclerViewGridAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mcontext, 2);
        //GridLayout.HORIZONTAL水平 GridLayout.VERTICAL默认垂直
        gridLayoutManager.setOrientation(GridLayout.VERTICAL);
        //设置布局管理器， 参数linearLayoutManager对象
        rv_icon.setLayoutManager(gridLayoutManager);


        fun_icon_edt = conentView.findViewById(R.id.fun_icon_edt);
        fun_icon_edt.setVisibility(View.GONE);

        fun_name = conentView.findViewById(R.id.fun_name);
        fun_drink_close = conentView.findViewById(R.id.fun_drink_close);
        fun_drink_close.setOnClickListener(this);
        strengthItem= conentView.findViewById(R.id.strengthItem);
        volumeItem= conentView.findViewById(R.id.volumeItem);
        milkItem= conentView.findViewById(R.id.milkItem);
        strengthBatingBar= conentView.findViewById(R.id.strengthBatingBar);
        volumeBatingBar= conentView.findViewById(R.id.volumeBatingBar);
        milkBatingBar= conentView.findViewById(R.id.milkBatingBar);
        exit = conentView.findViewById(R.id.exit);
        exit.setOnClickListener(this);
        volumeBatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser)
                    _currenPersonDrink.setVolume((int)rating);
            }
        });

        strengthBatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser)
                    _currenPersonDrink.setStrength((int)rating);
            }
        });

        milkBatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(fromUser)
                    _currenPersonDrink.setMilk((int)rating);
            }
        });


        fun_sp = conentView.findViewById(R.id.fun_sp);
        fun_sp.setAdapter(drinkSpinnerAdapter);

        fun_sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drinkSpinnerAdapter.setSelectposition(position);
                 //// TODO: 2018/5/15 update name and preselection
                drinkSpinnerAdapter.notifyDataSetChanged();
                DrinkMenuButton tmp = drinkSpinnerAdapter.getItem(position);
                if(_currenPersonDrink ==null)
                {
                    _currenPersonDrink = new PersonDrink(_currentPerson.getId(),tmp.getPid(),tmp.getName());

                }
                else
                {
                    _currenPersonDrink.setPid(tmp.getPid());
                    _currenPersonDrink.setName(tmp.getName());
                }
                volumeBatingBar.setRating(_currenPersonDrink.getVolume());
                strengthBatingBar.setRating(_currenPersonDrink.getStrength());
                milkBatingBar.setRating(_currenPersonDrink.getMilk());
                _currenPersonDrink.setPosition(mode_edt);
                fun_name.setText(_currenPersonDrink.getName());
            }
        });


        fun_btn_set = conentView.findViewById(R.id.fun_btn_set);
        fun_btn_set.setOnClickListener(this);
        fun_drink_edt = conentView.findViewById(R.id.fun_drink_edt);
        fun_drink_edt.setVisibility(View.GONE);
        m_rv = conentView.findViewById(R.id.m_rv);
        indexBar = conentView.findViewById(R.id.indexBar);
        tvSideBarHint = conentView.findViewById(R.id.tvSideBarHint);
        person_drink = conentView.findViewById(R.id.person_drink);
        txt_psw = conentView.findViewById(R.id.txt_psw);
        person_psw =conentView.findViewById(R.id.person_psw);

        btn_remove =conentView.findViewById(R.id.btn_remove);
        btn_remove.setOnClickListener(this);
        btn_changpsw =conentView.findViewById(R.id.btn_changpsw);
        btn_changpsw.setOnClickListener(this);

        lyt_psw = conentView.findViewById(R.id.lyt_psw);
        icon = conentView.findViewById(R.id.icon);
        icon.setOnClickListener(this);
        switch_psw= conentView.findViewById(R.id.switch_psw);
        switch_psw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                _currentPerson.setIslocked(isChecked?1:0);
                personFactoryDao.getPersonItemDao().update(_currentPerson);
            }
        });
        drk_1_lyt= conentView.findViewById(R.id.drk_1_lyt);
        drk_2_lyt= conentView.findViewById(R.id.drk_2_lyt);
        drk_3_lyt= conentView.findViewById(R.id.drk_3_lyt);
        drk_4_lyt= conentView.findViewById(R.id.drk_4_lyt);
        HideEdit();
        drk_1_lyt.setOnClickListener(this);
        drk_2_lyt.setOnClickListener(this);
        drk_3_lyt.setOnClickListener(this);
        drk_4_lyt.setOnClickListener(this);

        drk_1_icon = conentView.findViewById(R.id.drk_1_icon);
        drk_2_icon = conentView.findViewById(R.id.drk_2_icon);
        drk_3_icon = conentView.findViewById(R.id.drk_3_icon);
        drk_4_icon = conentView.findViewById(R.id.drk_4_icon);

        drk_1_name = conentView.findViewById(R.id.drk_1_name);
        drk_2_name = conentView.findViewById(R.id.drk_2_name);
        drk_3_name = conentView.findViewById(R.id.drk_3_name);
        drk_4_name = conentView.findViewById(R.id.drk_4_name);

        switch_edit = conentView.findViewById(R.id.switch_edit);

        switch_edit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    ShowEdit();
                else
                    initPersondrinkView(_currentPerson);
            }
        });


        person_psw.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

                //txt_psw.setText("Input the Password");
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
               // txt_psw.setText("Input the Password");
            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                if(mode_psw ==0) {
                    if (PatternLockUtils.patternToString(person_psw, pattern).equals(_currentPerson.getPsw())) {
                        person_drink.setVisibility(View.VISIBLE);
                        fun_icon_edt.setVisibility(View.GONE);
                        lyt_psw.setVisibility(View.GONE);
                        txt_psw.setText("Input the Pin");
                    } else {
                        txt_psw.setText("Password is Wrong");
                        person_psw.clearPattern();
                    }
                }else if(mode_psw ==1)
                {
                    if(PatternLockUtils.patternToString(person_psw, pattern).equals(_currentPerson.getPsw()))
                    {
                        txt_psw.setText("Input the new Pin");
                        mode_psw =2;
                        person_psw.clearPattern();
                    }
                    else
                    {
                        txt_psw.setText("Old pin is Wrong");
                        person_psw.clearPattern();
                    }
                }
                else if(mode_psw ==2)
                {
                    psw_first = PatternLockUtils.patternToString(person_psw, pattern);
                    mode_psw =3;
                    txt_psw.setText("Confirm the new pin");
                }
                else if(mode_psw ==3)
                {
                    if(PatternLockUtils.patternToString(person_psw, pattern).equals(psw_first))
                    {
                        txt_psw.setText("modify the pin!");
                        _currentPerson.setPsw(PatternLockUtils.patternToString(person_psw, pattern));
                        mode_psw =0;
                        person_drink.setVisibility(View.VISIBLE);
                        lyt_psw.setVisibility(View.GONE);

                        personFactoryDao.getPersonItemDao().update(_currentPerson);
                    }
                    else
                    {
                        txt_psw.setText("Two pin is different,Input the new Pin");
                        mode_psw =2;
                        person_psw.clearPattern();
                    }
                }
                else if(mode_psw ==4)
                {
                    mode_psw =0;
                    lyt_psw.setVisibility(View.GONE);
                    if(PatternLockUtils.patternToString(person_psw, pattern).equals("01258"))
                    {
                        //// TODO: 2018/5/15 create new one
                        _currentPerson = new PersonItem("new","",0,"01258");
                        personFactoryDao.getPersonItemDao().create(_currentPerson);
                        //_personItems = personFactoryDao.getPersonItemDao().quryallRecord();
                        _currentPerson.setTop(true);
                        _personItems.add(1,_currentPerson);
                        mAdapter.setDatas(_personItems).notifyDataSetChanged();
                        mAdapter.rollback(1);
                        initPersondrinkView(_currentPerson);
                        switch_edit.setChecked(false);
                        iseditmode =false;
                        person_drink.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void onCleared() {
                txt_psw.setText("Input the Password");
            }
        });
        //int h = mcontext.getWindowManager().getDefaultDisplay().getHeight();
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        person_drink.setVisibility(View.GONE);
        lyt_psw.setVisibility(View.GONE);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.AnimationPreview);
        m_rv.setLayoutManager(mManager = new LinearLayoutManager(this.mcontext));
        m_rv.setAdapter(mAdapter);
        m_rv.addItemDecoration(mDecoration = new SuspensionDecoration(this.mcontext, _personItems));
        m_rv.addItemDecoration(new DividerItemDecoration(this.mcontext, DividerItemDecoration.VERTICAL_LIST));

        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)//设置RecyclerView的LayoutManager
                .setmSourceDatas(_personItems)//设置数据
                .invalidate();
        mDecoration.setmDatas(_personItems);

        mAdapter.setOnPersonItemClicked(new PersonAdapter.OnPersonItemClicked() {
            @Override
            public void OnitemClick(int position) {

                if(position == 0)
                {
                    mode_psw =4;
                    lyt_psw.setVisibility(View.VISIBLE);
                    person_drink.setVisibility(View.GONE);
                    fun_icon_edt.setVisibility(View.GONE);
                    person_psw.clearPattern();
                    txt_psw.setText("input the pin to Create the new profile");
                    return;
                }
                _currentPerson = _personItems.get(position);
                initPersondrinkView(_currentPerson);
                switch_edit.setChecked(false);
                iseditmode =false;
                if(_personItems.get(position).getIslocked() == 1)
                {
                    //// TODO: 2018/5/7 need input password
                    lyt_psw.setVisibility(View.VISIBLE);
                    person_drink.setVisibility(View.GONE);
                    fun_icon_edt.setVisibility(View.GONE);
                    person_psw.clearPattern();
                }
                else {
                    person_drink.setVisibility(View.VISIBLE);
                    fun_icon_edt.setVisibility(View.GONE);
                }
            }
        });

    }

    public void updatedrinkui()
    {
        personDrinks =personFactoryDao.getPersonDrinkDao().quryallRecord(_currentPerson.getId());
        setImagerec(drk_1_icon,9999);
        setImagerec(drk_2_icon,9999);
        setImagerec(drk_3_icon,9999);
        setImagerec(drk_4_icon,9999);
        drk_1_lyt.setTag(null);
        drk_2_lyt.setTag(null);
        drk_3_lyt.setTag(null);
        drk_4_lyt.setTag(null);
        if(personDrinks!=null && personDrinks.size()>0)
        {
            for (PersonDrink item:personDrinks)
            {
                if(item.getPosition() ==1)
                {
                    drk_1_lyt.setVisibility(View.VISIBLE);
                    drk_1_lyt.setTag(item);
                    drk_1_name.setText(item.getName());
                    setImagerec(drk_1_icon,item.getPid());
                }
                else if(item.getPosition() ==2)
                {
                    drk_2_lyt.setVisibility(View.VISIBLE);
                    drk_2_lyt.setTag(item);
                    drk_2_name.setText(item.getName());
                    setImagerec(drk_2_icon,item.getPid());
                }
                else if(item.getPosition() ==3)
                {
                    drk_3_lyt.setVisibility(View.VISIBLE);
                    drk_3_lyt.setTag(item);
                    drk_3_name.setText(item.getName());
                    setImagerec(drk_3_icon,item.getPid());
                }
                else if(item.getPosition() ==4)
                {
                    drk_4_lyt.setVisibility(View.VISIBLE);
                    drk_4_lyt.setTag(item);
                    drk_4_name.setText(item.getName());
                    setImagerec(drk_4_icon,item.getPid());
                }
            }
        }
    }

    public void initPersondrinkView(PersonItem data) {
        iseditmode =false;
        HideEdit();
        if(data.getIconpath()!=null && !data.getIconpath().equals(""))
        {
            Bitmap bitmap = PictureManager.getInstance().getBitmapFromMemCache(data.getIconpath());
            if (bitmap == null) {
                final Bitmap tmpbitmap = PictureManager.decodeSampledBitmapFromResource(data.getIconpath(), 120, 120);
                if (tmpbitmap != null) {
                    PictureManager.getInstance().addBitmapToMemoryCache(data.getIconpath(), tmpbitmap);
                    bitmap = PictureManager.getInstance().getBitmapFromMemCache(data.getIconpath());
                }
            }
            if (bitmap != null)
                icon.setImageBitmap(bitmap);
            else
                icon.setImageResource(R.mipmap.ic_person_default);
        }
        else
        {
            icon.setImageResource(R.mipmap.ic_person_default);
        }

        switch_psw.setChecked(data.getIslocked() == 1);
        updatedrinkui();
    }
    public void show(View parent) {
        if (this.isShowing()) {
            this.dismiss();
        } else {
            this.showAtLocation(parent,Gravity.FILL, 0, 0);
        }
    }

    public void NotifyUSB(boolean ready)
    {
        _icondata.clear();
        if(ready)
        {
           List<String> files= FileHelper.getIconFile(new File(mcontext.getApp().getUsbpath()+"/profile/"));
            for (String file :files)
            {
                _icondata.add(new IconDateBean(file.substring(file.lastIndexOf("/")+1),file));
            }
        }
        recyclerViewGridAdapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.exit:
                this.dismiss();
            case R.id.fun_icon_close:
                iconpath ="";
                fun_icon_edt.setVisibility(View.GONE);
                break;
            case R.id.fun_btn_icon_set:
                if(fun_icon_name.getText() != null && !fun_icon_name.getText().toString().equals("")) {
                    _currentPerson.setName(fun_icon_name.getText().toString());
                    if (!iconpath.equals("")) {
                        String newpath = FileHelper.PATH_PROFILE + iconpath.substring(iconpath.lastIndexOf("/") + 1);
                        _currentPerson.setIconpath(newpath);
                        FileHelper.copyFile(iconpath, newpath);
                    }
                    personFactoryDao.getPersonItemDao().update(_currentPerson);
                    fun_icon_edt.setVisibility(View.GONE);
                    iconpath ="";
                    mAdapter.notifyDataSetChanged();
                }else
                {
                    mcontext.showToast("Input the Name");
                }
                break;
            case R.id.fun_drink_close:
                fun_drink_edt.setVisibility(View.GONE);
                updatedrinkui();
                break;
            case R.id.btn_remove:
                _personItems.remove(_currentPerson);
                personFactoryDao.getPersonItemDao().delete(_currentPerson.getId());
                _currentPerson =null;
                person_drink.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                mAdapter.rollback(0);
                break;
            case R.id.fun_btn_set:
                fun_drink_edt.setVisibility(View.GONE);
                _currenPersonDrink.setName(fun_name.getText().toString());
                personFactoryDao.getPersonDrinkDao().updateorNew(_currenPersonDrink);
                updatedrinkui();
                break;
            case R.id.icon:
                if(iseditmode)
                {
                    mode_edt =9;
                    fun_icon_edt.setVisibility(View.VISIBLE);
                    fun_icon_name.setText(_currentPerson.getName());
                }
                break;
            case R.id.btn_changpsw:
                mode_psw =1;
                person_psw.clearPattern();
                lyt_psw.setVisibility(View.VISIBLE);
                person_drink.setVisibility(View.GONE);
                fun_icon_edt.setVisibility(View.GONE);
                txt_psw.setText("Please input the old pin!");
                break;
            case R.id.drk_1_lyt:
            case R.id.drk_2_lyt:
            case R.id.drk_3_lyt:
            case R.id.drk_4_lyt:
                if(iseditmode)
                {
                    fun_drink_edt.setVisibility(View.VISIBLE);
                    if(v.getId() == R.id.drk_1_lyt)
                        mode_edt =1;
                    if(v.getId() == R.id.drk_2_lyt)
                        mode_edt =2;
                    if(v.getId() == R.id.drk_3_lyt)
                        mode_edt =3;
                    if(v.getId() == R.id.drk_4_lyt)
                        mode_edt =4;
                    _currenPersonDrink = (PersonDrink)v.getTag();
                    if(_currenPersonDrink!=null) {
                        volumeBatingBar.setRating(_currenPersonDrink.getVolume());
                        strengthBatingBar.setRating(_currenPersonDrink.getStrength());
                        milkBatingBar.setRating(_currenPersonDrink.getMilk());
                        fun_name.setText(_currenPersonDrink.getName());
                    }
                    else
                    {
                        fun_name.setText(null);
                    }
                }
                else
                {
                    if(v.getTag()!=null)
                    {
                        if(_OnQuickStartClick!=null)
                        {
                            _OnQuickStartClick.Start((PersonDrink )v.getTag());
                            this.dismiss();
                        }
                    }
                }
                break;
        }
    }
}
