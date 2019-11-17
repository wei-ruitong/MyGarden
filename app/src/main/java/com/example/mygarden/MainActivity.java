package com.example.mygarden;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

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
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
       toolbar.setTitle("规模化农业种植软件");
     //  toolbar.setLogo(R.drawable.touxiang2);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navigationView=(NavigationView)findViewById(R.id.nav_view);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.caidan7);
        }
        navigationView.setCheckedItem(R.id.shezhi);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
             // drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.shezhi:
                       // Toast.makeText(MainActivity.this,"设置",Toast.LENGTH_SHORT).show();
                        showSingDialog();
                        break;
                    case R.id.add:
                        Intent intent=new Intent(MainActivity.this,AddEarthActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.version:
                        //Toast.makeText(MainActivity.this,"版本信息",Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder singleChoiceDialog1= new AlertDialog.Builder(MainActivity.this);
                        // singleChoiceDialog.setIcon(R.drawable.icon);
                        singleChoiceDialog1.setTitle("Version:1.0");
                        singleChoiceDialog1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        });
                        singleChoiceDialog1.show();
                        break;
                    case R.id.lianxi:
                        Toast.makeText(MainActivity.this,"关于我们",Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(MainActivity.this,AboutWe.class);
                        startActivity(intent1);


                        break;
                    case R.id.zhishi:
                        //Toast.makeText(MainActivity.this,"知识库",Toast.LENGTH_SHORT).show();
                        Intent intent2=new Intent(MainActivity.this,KnowledgeActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.help:
                       // Toast.makeText(MainActivity.this,"使用帮助",Toast.LENGTH_SHORT).show();
                        Intent intent3=new Intent(MainActivity.this,HelpActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.tuichu:
                      //  Toast.makeText(MainActivity.this,"退出当前账号",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.del:
                       // Toast.makeText(MainActivity.this,"删除地块",Toast.LENGTH_SHORT).show();
                        Intent intent4=new Intent(MainActivity.this,DeleteActivity.class);
                        startActivity(intent4);
                        break;

                }
              return  true;
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
                default:
        }
        return  true;
    }
    /**
     * 单选Dialog
     */
    int choice;
    private void showSingDialog(){
        final String[] items = {"夜间模式","日间模式"};
        AlertDialog.Builder singleChoiceDialog = new AlertDialog.Builder(MainActivity.this);
       // singleChoiceDialog.setIcon(R.drawable.icon);
        singleChoiceDialog.setTitle("模式选择");
        //第二个参数是默认的选项
        singleChoiceDialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                choice= which;
            }
        });
        singleChoiceDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choice!=-1){
                    Toast.makeText(MainActivity.this,
                            "你选择了" + items[choice],
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        singleChoiceDialog.show();
    }
}


