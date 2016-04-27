package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.fragment.QuestsFragment;

/**
 * Created by arstao on 2016/2/24.
 */
public enum SimpleBackPage {

    QUESTS(1, R.string.menu_quests, QuestsFragment.class);



    private int title;
    private Class<?> clz;
    private int value;

    private SimpleBackPage(int value, int title, Class<?> clz) {
        this.value = value;
        this.title = title;
        this.clz = clz;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    public int getValue() {
        return value;
    }
}