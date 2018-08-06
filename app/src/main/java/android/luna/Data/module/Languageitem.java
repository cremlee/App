package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/3/6.
 */
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "tb_language")
public class Languageitem {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(defaultValue = "1")
    private int en=1;
    @DatabaseField(defaultValue = "0")
    private int nl;
    @DatabaseField(defaultValue = "0")
    private int da;
    @DatabaseField(defaultValue = "0")
    private int fi;
    @DatabaseField(defaultValue = "0")
    private int cn;
    @DatabaseField(defaultValue = "0")
    private int gm;
    @DatabaseField(defaultValue = "0")
    private int sv;
    @DatabaseField(defaultValue = "0")
    private int no;

    public Languageitem() {
    }

    public Languageitem(int en, int nl, int da, int fi, int cn, int gm, int sv, int no) {
        this.en = en;
        this.nl = nl;
        this.da = da;
        this.fi = fi;
        this.cn = cn;
        this.gm = gm;
        this.sv = sv;
        this.no = no;
    }

    public Languageitem(int id, int en, int nl, int da, int fi, int cn, int gm, int sv, int no) {
        this.id = id;
        this.en = en;
        this.nl = nl;
        this.da = da;
        this.fi = fi;
        this.cn = cn;
        this.gm = gm;
        this.sv = sv;
        this.no = no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEn() {
        return en;
    }

    public void setEn(int en) {
        this.en = en;
    }

    public int getNl() {
        return nl;
    }

    public void setNl(int nl) {
        this.nl = nl;
    }

    public int getDa() {
        return da;
    }

    public void setDa(int da) {
        this.da = da;
    }

    public int getFi() {
        return fi;
    }

    public void setFi(int fi) {
        this.fi = fi;
    }

    public int getCn() {
        return cn;
    }

    public void setCn(int cn) {
        this.cn = cn;
    }

    public int getGm() {
        return gm;
    }

    public void setGm(int gm) {
        this.gm = gm;
    }

    public int getSv() {
        return sv;
    }

    public void setSv(int sv) {
        this.sv = sv;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }


    public static String getLanguageName(int lang)
    {
        String ret ="unknown";
        switch (lang)
        {
            case DrinkName.Local_cn:
                ret = "Chinese";
                break;
            case DrinkName.Local_en:
                ret = "English";
                break;
            case DrinkName.Local_nl:
                ret = "Local_nl";
                break;
            case DrinkName.Local_da:
                ret = "Local_da";
                break;
            case DrinkName.Local_fi:
                ret = "Local_fi";
                break;
            case DrinkName.Local_gm:
                ret = "Local_gm";
                break;
            case DrinkName.Local_sv:
                ret = "Local_sv";
                break;
            case DrinkName.Local_no:
                ret = "Local_no";
                break;
        }
        return ret;
    }

    public List<Integer> getLanguageList()
    {
        List<Integer> tmp = new ArrayList<>();
        if(getCn() ==1)
            tmp.add(DrinkName.Local_cn);
        if(getEn() ==1)
            tmp.add(DrinkName.Local_en);
        if(getNl() ==1)
            tmp.add(DrinkName.Local_nl);
        if(getDa() ==1)
            tmp.add(DrinkName.Local_da);
        if(getFi() ==1)
            tmp.add(DrinkName.Local_fi);
        if(getGm() ==1)
            tmp.add(DrinkName.Local_gm);
        if(getSv() ==1)
            tmp.add(DrinkName.Local_sv);
        if(getNo() ==1)
            tmp.add(DrinkName.Local_no);
        return tmp;
    }

    public Integer[] getlangIndice()
    {
        List<Integer> tmp = new ArrayList<>();
        if(getCn() ==1)
            tmp.add(DrinkName.Local_cn-1);
        if(getEn() ==1)
            tmp.add(DrinkName.Local_en-1);
        if(getNl() ==1)
            tmp.add(DrinkName.Local_nl-1);
        if(getDa() ==1)
            tmp.add(DrinkName.Local_da-1);
        if(getFi() ==1)
            tmp.add(DrinkName.Local_fi-1);
        if(getGm() ==1)
            tmp.add(DrinkName.Local_gm-1);
        if(getSv() ==1)
            tmp.add(DrinkName.Local_sv-1);
        if(getNo() ==1)
            tmp.add(DrinkName.Local_no-1);

        if(tmp!=null && tmp.size()>0) {
            Integer[] ret = new Integer[tmp.size()];
            for (int i = 0; i < tmp.size(); i++) {
                ret[i] = tmp.get(i);
            }
            return  ret;
        }
        return null;
    }

    private void clearalllanguage()
    {
        setCn(0);
        setEn(0);
        setNl(0);
        setDa(0);
        setFi(0);
        setGm(0);
        setNo(0);
        setSv(0);
    }
    public void SetallLanguage(Integer[] list)
    {
        clearalllanguage();
        int tag;
        for (Integer item:list)
        {
             tag = item.intValue()+1;
             switch (tag)
             {
                 case DrinkName.Local_cn:
                     setCn(1);
                     break;
                 case DrinkName.Local_en:
                     setEn(1);
                     break;
                 case DrinkName.Local_nl:
                     setNl(1);
                     break;
                 case DrinkName.Local_da:
                     setDa(1);
                     break;
                 case DrinkName.Local_fi:
                     setFi(1);
                     break;
                 case DrinkName.Local_gm:
                     setGm(1);
                     break;
                 case DrinkName.Local_no:
                     setNo(1);
                     break;
                 case DrinkName.Local_sv:
                     setSv(1);
                     break;
             }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Languageitem that = (Languageitem) o;

        if (getId() != that.getId()) return false;
        if (getEn() != that.getEn()) return false;
        if (getNl() != that.getNl()) return false;
        if (getDa() != that.getDa()) return false;
        if (getFi() != that.getFi()) return false;
        if (getCn() != that.getCn()) return false;
        if (getGm() != that.getGm()) return false;
        if (getSv() != that.getSv()) return false;
        return getNo() == that.getNo();

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getEn();
        result = 31 * result + getNl();
        result = 31 * result + getDa();
        result = 31 * result + getFi();
        result = 31 * result + getCn();
        result = 31 * result + getGm();
        result = 31 * result + getSv();
        result = 31 * result + getNo();
        return result;
    }

    @Override
    public String toString() {
        return "Languageitem{" +
                "id=" + id +
                ", en=" + en +
                ", nl=" + nl +
                ", da=" + da +
                ", fi=" + fi +
                ", cn=" + cn +
                ", gm=" + gm +
                ", sv=" + sv +
                ", no=" + no +
                '}';
    }
}
