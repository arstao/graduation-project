package com.arstao.gradesystem.bean;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.fragment.AboutFragment;
import com.arstao.gradesystem.fragment.ChangePwdFragment;
import com.arstao.gradesystem.fragment.GradeDetailFragment;
import com.arstao.gradesystem.fragment.MatchDetailFragment;
import com.arstao.gradesystem.fragment.SearchFragment;
import com.arstao.gradesystem.fragment.SettingFragment;

/**
 * Created by arstao on 2016/2/24.
 */
public enum SimpleBackPage {

    SETTING(1, R.string.menu_setting, SettingFragment.class),
    ABOUT(2, R.string.menu_about, AboutFragment.class),
    GRADE_DETAIL(3,R.string.grade_detail, GradeDetailFragment.class),
MATCH_DETAIL(4,R.string.match_detail, MatchDetailFragment.class),
CHANGE_PWD(5,R.string.change_pwd, ChangePwdFragment.class),
    SEARCH(6,R.string.page_search, SearchFragment.class);
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