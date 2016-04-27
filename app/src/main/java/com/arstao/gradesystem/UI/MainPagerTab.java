package com.arstao.gradesystem.UI;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.fragment.PagerTab;
import com.arstao.gradesystem.fragment.PagerTab1;
import com.arstao.gradesystem.fragment.PagerTab2;
import com.arstao.gradesystem.fragment.PagerTab3;

/**
 * Created by arstao on 2016/3/19.
 */
public enum MainPagerTab {
    NEWS(0, R.string.pagertab_1,
         PagerTab.class),
    MAIN(1,R.string.pagertab_2,PagerTab1.class),
    MAI(1,R.string.pagertab_3,PagerTab2.class),
    MAN(1,R.string.pagertab_4,PagerTab3.class);
//
//	TWEET(img1, R.string.main_tab_name_tweet, R.drawable.tab_icon_tweet,
//			TweetsViewPagerFragment.class),
//
//	QUICK(2, R.string.main_tab_name_quick, R.drawable.tab_icon_new,
//			null),
//
//	EXPLORE(3, R.string.main_tab_name_explore, R.drawable.tab_icon_explore,
//			ExploreFragment.class),
//
//	ME(4, R.string.main_tab_name_my, R.drawable.tab_icon_me,
//			MyInformationFragment.class);

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
