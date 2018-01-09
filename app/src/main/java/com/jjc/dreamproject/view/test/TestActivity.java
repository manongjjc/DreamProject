package com.jjc.dreamproject.view.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.jjc.dreamproject.BuildConfig;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.contract.test.TestContract;
import com.jjc.dreamproject.presenter.test.TestPresenter;
import com.jjc.dreamproject.view.BaseActivity;

import me.yokeyword.fragmentation.Fragmentation;

public class TestActivity extends BaseActivity<TestContract.Presenter> implements TestContract.View{
    private FrameLayout test_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test_fragment = (FrameLayout) findViewById(R.id.test_fragment);
        loadRootFragment(R.id.test_fragment,new TestFragment());

    }


    @Override
    public TestContract.Presenter bindPresenter() {
        return new TestPresenter();
    }


}

