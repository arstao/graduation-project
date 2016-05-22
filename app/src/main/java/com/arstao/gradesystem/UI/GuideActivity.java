package com.arstao.gradesystem.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arstao.gradesystem.R;
import com.arstao.gradesystem.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arstao on 2016/4/26.
 */
public class GuideActivity extends BaseActivity {
    public static final String LOGIN = "login";
    public static final String REGISTER = "register";
    private ImageView[] tips;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected Toolbar setToolBar() {
        return (Toolbar) findViewById(R.id.tb_guide);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    private int[] imgs = {R.drawable.ic_guide1, R.drawable.ic_guide2, R.drawable.ic_guide3};

    @Override
    public void initView() {
        Button btn_tologin = (Button) findViewById(R.id.btn_tologin);
        Button btn_toregister = (Button) findViewById(R.id.btn_toregister);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.viewGroup);

        btn_tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                intent.putExtra("type", LOGIN);
                startActivity(intent);
            }
        });
        btn_toregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
                intent.putExtra("type", REGISTER);
                startActivity(intent);
            }
        });
        List<View> listViews = new ArrayList<View>();
        tips = new ImageView[imgs.length];
        for (int i = 0; i < imgs.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
            tips[i] = imageView;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(20,20));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            viewGroup.addView(imageView, layoutParams);

            ImageView view = new ImageView(this);
            view.setBackgroundResource(imgs[i]);
            listViews.add(view);
        }
        setImageBackground(0);
        ViewPager vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(new ViewPagerAdapter(listViews));
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setImageBackground(position % imgs.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void initData() {

    }

    public class ViewPagerAdapter extends PagerAdapter {
        private List<View> list;

        public ViewPagerAdapter(List<View> list) {
            this.list = list;
        }

        @Override
        public int getCount() {

            if (list != null && list.size() > 0) {
                return list.size();
            } else {
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setImageDrawable(getResources().getDrawable(R.drawable.point_focus));
            } else {
                tips[i].setImageDrawable(getResources().getDrawable(R.drawable.point_normal));
            }
        }
    }
}
