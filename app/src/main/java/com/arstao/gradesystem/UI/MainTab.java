package com.arstao.gradesystem.UI;


import com.arstao.gradesystem.R;
import com.arstao.gradesystem.fragment.MineFragment;
import com.arstao.gradesystem.fragment.ScanFragment;

public enum MainTab {

	SCAN(0, R.string.tab_scan, R.drawable.alien,ScanFragment.class),
	GRADE(1, R.string.tab_grade, R.drawable.alien,ScanFragment.class),
	MESSAGE(2, R.string.tab_message, R.drawable.alien,ScanFragment.class),
	MINE(3,R.string.tab_mine,R.drawable.alien,MineFragment.class);
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
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
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

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
