package com.jjc.dreamproject.view;

import android.os.Bundle;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.contract.LoginContract;
import com.jjc.dreamproject.presenter.LoginPresenter;


public class LoginActivity extends BaseActivity<LoginContract.Presenter> implements LoginContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(findFragment(MainFragment.class)==null)
            loadRootFragment(R.id.framelayout,new MainFragment());
    }


    @Override
    public LoginContract.Presenter bindPresenter() {
        return new LoginPresenter();
    }


}

