package com.arstao.gradesystem.UI;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.fragment.PagerTab;

/**
 * Created by arstao on 2016/3/19.
 */
public enum MainPagerTab {
    ALL(0, R.string.pagertab_1
            , PagerTab.class),
    COMPETITIVE(1,R.string.pagertab_2,PagerTab.class),
    ENJOY(2,R.string.pagertab_3,PagerTab.class);

    private int idx;
    private int resName;
    private Class<?> clz;

    private MainPagerTab(int idx, int resName,  Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }


    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
