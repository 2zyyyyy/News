package com.example.tony.news;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tony.news.Fragment.FirstpagerFragment;
import com.example.tony.news.adapter.MainTabAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;   //顶部标题选项布局
    private ViewPager mViewPager;

    private String[] mList_title;//存放标题

    private FirstpagerFragment mFragment;

    private List<FirstpagerFragment> mFirstFragments;//存放Fragment的集合

    private MainTabAdapter mAdapter_title;//标题适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化Fresco
        Fresco.initialize(this);
        initData();//初始化标题数据
        initView();
        initListener();
    }

    private void initListener() {
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //tab被选中或被点击的事件回调
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                for (int i = 0; i < mFirstFragments.size(); i++) {
                    mFirstFragments.get(position).setPosition(position);
                }
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData() {
        mList_title = getResources().getStringArray(R.array.tab_title);
        mFirstFragments = new ArrayList<>();

        //通过标题个数来创建Fragment
        for (int i = 0; i < mList_title.length; i++) {
            FirstpagerFragment first = new FirstpagerFragment();
            mFirstFragments.add(first);
        }
    }

    //初始化布局
    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_title);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mAdapter_title = new MainTabAdapter(getSupportFragmentManager()
                , mFirstFragments, mList_title);

        mViewPager.setAdapter(mAdapter_title);

        //TabLayout 绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
