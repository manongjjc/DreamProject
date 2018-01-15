package com.jjc.dreamproject.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.contract.MainContract;
import com.jjc.dreamproject.presenter.MainPresenter;

import com.jjc.dreamproject.util.ToastUtil;
import com.jjc.dreamproject.util.UtilCode;
import com.jjc.dreamproject.view.BaseFragment;
import com.jjc.dreamproject.view.register.RegisterFragment;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;


public class MainFragment extends BaseFragment<MainContract.Presenter> implements MainContract.View, View.OnClickListener {
    private AutoCompleteTextView email;
    private EditText password;
    private Button email_sign_in_button;
    private TextView login_register;
    private Toolbar toolbar;
    public MainFragment() {
        // Required empty public constructor
    }

    private MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);
        initView(view);
        initToolbar();
        addEvent();
        return view;
    }

    private void addEvent() {
        email_sign_in_button.setOnClickListener(this);
        login_register.setOnClickListener(this);
    }

    private void initToolbar() {
        TextView textView = toolbar.findViewById(R.id.main_titile);
        textView.setText("Joyeux");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
//        setSupportActionBar(toolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState != null) {
            //data recovery
            String name = savedInstanceState.getString("name");
            String pathword = savedInstanceState.getString("pathword");
            if(name!=null&&!name.trim().equals("")){
                if(pathword!=null&&!pathword.trim().equals("")){
                    email.setText(name);
                    password.setText(pathword);
                }
            }
        }

    }

    private void initView(View view) {
        toolbar = view.findViewById(R.id.activity_main_toolbar);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        email_sign_in_button = view.findViewById(R.id.email_sign_in_button);
        login_register = view.findViewById(R.id.login_register);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //The exception status data is saved.
        outState.putString("email",email.getText().toString().trim());
        outState.putString("password",password.getText().toString().trim());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        //fragment  show
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        //fragment hide
    }

    @Override
    public MainContract.Presenter bindPresenter() {
        return new MainPresenter();
    }


    @Override
    public void stopDialog(boolean isSucceed, String alert) {
        LemonBubble.hide();
        if(isSucceed){
            /**
             * 跳转界面
             */
            new ToastUtil().Short(getContext(),"登陆成功").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
            Intent intent = new Intent();
            intent.setClass(getContext(), FirstActivity.class);
            startActivity(intent);
            getActivity().finish();
        }else{
            new ToastUtil().Short(getContext(),alert).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_sign_in_button:
                clearKeyboard();
                String trim = email.getText().toString().trim();
                if(trim.equals("")){
                    new ToastUtil().Short(getContext(),"请输入账号").show();
                    return;
                }
                String pword = password.getText().toString().trim();
                if(pword.equals("")){
                    new ToastUtil().Short(getContext(),"请输入密码").show();
                    return;
                }
                LemonBubbleInfo roundProgressBubbleInfo = LemonBubble.getRoundProgressBubbleInfo();
                roundProgressBubbleInfo.setIconColor(Color.GRAY);
                roundProgressBubbleInfo.setTitle("登录中");
                LemonBubble.showBubbleInfo(getContext(),roundProgressBubbleInfo);
                presenter.startLogin(trim,pword);
                break;
            case R.id.login_register:
                clearKeyboard();
//                Intent intent = new Intent();
//                intent.setClass(getContext(),RegisterActivity.class);
//                startActivityForResult(intent, UtilCode.MainActivityForResult);
                startForResult(new RegisterFragment(),UtilCode.REQ_CODE);
                break;
        }
    }

    /**
     * 清除控件焦点，隐藏软键盘
     */
    private void clearKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(),0);
        imm.hideSoftInputFromWindow(email.getWindowToken(),0);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        if(requestCode==UtilCode.REQ_CODE&&resultCode==UtilCode.RESULT_OK){
            String name = data.getString("name");
            String pathword = data.getString("pathword");
            if(name!=null&&!name.trim().equals("")){
                if(pathword!=null&&!pathword.trim().equals("")){
                    email.setText(name);
                    password.setText(pathword);
                }
            }
        }
    }
}

