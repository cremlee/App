package android.luna.Data.module;

/**
 * Created by Lee.li on 2018/5/2.
 */

public class FAQItem {
    private int group;
    private int index;
    private String q_content;
    private String a_content;

    public FAQItem(String q_content, String a_content) {
        this.q_content = q_content;
        this.a_content = a_content;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getQ_content() {
        return q_content;
    }

    public void setQ_content(String q_content) {
        this.q_content = q_content;
    }

    public String getA_content() {
        return a_content;
    }

    public void setA_content(String a_content) {
        this.a_content = a_content;
    }
}
