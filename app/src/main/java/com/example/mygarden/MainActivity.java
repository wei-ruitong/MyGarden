package com.example.mygarden;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView  view1, view2,view3,view4;
    private ViewPager vp;
    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragmentAdapter mFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
getSupportActionBar().hide();
        initViews();

        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        vp.setOffscreenPageLimit(4);//ViewPager的缓存为4帧
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);//初始设置ViewPager选中第一帧
        view1.setTextColor(Color.parseColor("#7093db"));
        //ViewPager的监听事件
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*此方法在页面被选中时调用*/
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /*此方法是在状态改变的时候调用，其中arg0这个参数有三种状态（0，1，2）。
                arg0 ==1的时辰默示正在滑动，
                arg0==2的时辰默示滑动完毕了，
                arg0==0的时辰默示什么都没做。*/
            }
        });
    }

    /**
     * 初始化布局View
     */
    private void initViews() {
        view1 = (TextView) findViewById(R.id.view1);
        view2 = (TextView) findViewById(R.id.view2);
        view3=(TextView)findViewById(R.id.view3);
        view4=(TextView)findViewById(R.id.view4);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(0, true);
            }
        });
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(1, true);
            }
        });
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(2, true);
            }
        });
        view4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp.setCurrentItem(3, true);
            }
        });

        vp = (ViewPager) findViewById(R.id.mainViewPager);
        oneFragment = new OneFragment();
        twoFragment = new TwoFragment();

        threeFragment=new ThreeFragment();
        fourFragment=new FourFragment();
        //给FragmentList添加数据
        mFragmentList.add(oneFragment);

        mFragmentList.add(twoFragment);
        mFragmentList.add(threeFragment);
        mFragmentList.add(fourFragment);
    }

    /**
     * 点击底部Text 动态修改ViewPager的内容
     */

  /*  public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_weixin:
                vp.setCurrentItem(0, true);
                break;
            case R.id.item_tongxunlu:
                vp.setCurrentItem(1, true);
                break; case R.id.view3:
                vp.setCurrentItem(2, true);
                break; case R.id.view4:
                vp.setCurrentItem(3, true);
                break;
        }
    }
*/
    public class FragmentAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        public FragmentAdapter(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
    /*
     *由ViewPager的滑动修改底部导航Text的颜色
     */
    private void changeTextColor(int position) {
        if (position == 0) {
            view1.setTextColor(Color.parseColor("#7093db"));
            view2.setTextColor(Color.parseColor("#000000"));
            view3.setTextColor(Color.parseColor("#000000"));
            view4.setTextColor(Color.parseColor("#000000"));

        } else if (position == 1) {
            view2.setTextColor(Color.parseColor("#7093db"));
            view1.setTextColor(Color.parseColor("#000000"));
            view3.setTextColor(Color.parseColor("#000000"));
            view4.setTextColor(Color.parseColor("#000000"));

        } else if (position == 2) {
            view3.setTextColor(Color.parseColor("#7093db"));
            view2.setTextColor(Color.parseColor("#000000"));
            view1.setTextColor(Color.parseColor("#000000"));
            view4.setTextColor(Color.parseColor("#000000"));
        } else if (position == 3) {
            view4.setTextColor(Color.parseColor("#7093db"));
            view2.setTextColor(Color.parseColor("#000000"));
            view3.setTextColor(Color.parseColor("#000000"));
            view1.setTextColor(Color.parseColor("#000000"));
        }
    }


}


