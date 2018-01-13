package com.jjc.dreamproject.view.test;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.FrameLayout;

import com.jjc.dreamproject.BuildConfig;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.contract.test.TestContract;
import com.jjc.dreamproject.presenter.test.TestPresenter;
import com.jjc.dreamproject.view.BaseActivity;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.SupportFragment;

public class TestActivity extends BaseActivity<TestContract.Presenter> implements TestContract.View{
    private FrameLayout test_fragment;
    private BottomBar bottomBar;
    private SupportFragment[] mFragments;
    private int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mFragments = new SupportFragment[5];
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        test_fragment = (FrameLayout) findViewById(R.id.test_fragment);
//        loadRootFragment(R.id.test_fragment,new TestFragment());
        initData();
        initEvent();

    }

    private void initData() {
        mFragments[0] = new OneFragment();
        mFragments[1] = new TwoFragment();
        mFragments[2] = new ThreeFragment();
        mFragments[3] = new FourFragment();
        mFragments[4] = new FiveFragment();
        loadMultipleRootFragment(R.id.test_fragment,0,mFragments[0],mFragments[1],mFragments[2],mFragments[3],mFragments[4]);
    }

    private void initEvent() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Log.d("JYD","activity setOnTabSelectListener");
                int changeTab = 0;
                switch (tabId){
                    case R.id.tab_recents:
                        changeTab = 0;
                        break;
                    case R.id.tab_favorites:
                        changeTab = 1;
                        break;
                    case R.id.tab_nearby:
                        changeTab = 2;
                        break;
                    case R.id.tab_friends:
                        changeTab = 3;
                        break;
                    case R.id.tab_restaurants:
                        changeTab = 4;
                        break;
                }
                showHideFragment(mFragments[changeTab],mFragments[currentPage]);
                currentPage = changeTab;
            }
        });
    }


    @Override
    public TestContract.Presenter bindPresenter() {
        return new TestPresenter();
    }


}

