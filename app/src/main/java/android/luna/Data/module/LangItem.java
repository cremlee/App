package android.luna.Data.module;

import java.io.Serializable;

/**
 * Created by Lee.li on 2018/3/8.
 */

public class LangItem implements Serializable{
    private String lang_tag;
    private String lang_text;

    public LangItem() {
    }

    public LangItem(String lang_tag, String lang_text) {
        this.lang_tag = lang_tag;
        this.lang_text = lang_text;
    }

    public String getLang_tag() {
        return lang_tag;
    }

    public void setLang_tag(String lang_tag) {
        this.lang_tag = lang_tag;
    }

    public String getLang_text() {
        return lang_text;
    }

    public void setLang_text(String lang_text) {
        this.lang_text = lang_text;
    }

    @Override
    public String toString() {
        return "LangItem{" +
                "lang_tag='" + lang_tag + '\'' +
                ", lang_text='" + lang_text + '\'' +
                '}';
    }
}
