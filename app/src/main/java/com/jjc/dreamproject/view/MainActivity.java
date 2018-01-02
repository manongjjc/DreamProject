package com.jjc.dreamproject.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;

import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View, View.OnClickListener {
    private AutoCompleteTextView email;
    private EditText password;
    private Button email_sign_in_button;
    private TextView login_register;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, "68d47a7bb7651a77eaf249b092038ca6");
        initView();
        initToolbar();
        addEvent();
    }

    private void initToolbar() {
        TextView textView = toolbar.findViewById(R.id.main_titile);
        textView.setText("Joyeux");
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

    }

    @Override
    public MainContract.Presenter bindPresenter() {
        return new MainPresenter();
    }
    private void addEvent() {
        email_sign_in_button.setOnClickListener(this);
        login_register.setOnClickListener(this);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        email = (AutoCompleteTextView) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        login_register = (TextView) findViewById(R.id.login_register);
    }

    @Override
    public void stopDialog(boolean isSucceed, String alert) {
        LemonBubble.hide();
        if(isSucceed){
            /**
             * 跳转界面
             */
            new ToastUtil().Short(this,"登陆成功").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
            Intent intent = new Intent();
            intent.setClass(this, FirstActivity.class);
            startActivity(intent);
            finish();
        }else{
            new ToastUtil().Short(this,alert).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_sign_in_button:
                clearKeyboard();
                String trim = email.getText().toString().trim();
                if(trim.equals("")){
                    new ToastUtil().Short(MainActivity.this,"请输入账号").show();
                    return;
                }
                String pword = password.getText().toString().trim();
                if(pword.equals("")){
                    new ToastUtil().Short(MainActivity.this,"请输入密码").show();
                    return;
                }
                LemonBubbleInfo roundProgressBubbleInfo = LemonBubble.getRoundProgressBubbleInfo();
                roundProgressBubbleInfo.setIconColor(Color.GRAY);
                roundProgressBubbleInfo.setTitle("登录中");
                LemonBubble.showBubbleInfo(MainActivity.this,roundProgressBubbleInfo);
                presenter.startLogin(trim,pword);
                break;
            case R.id.login_register:
                clearKeyboard();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RegisterActivity.class);
                startActivityForResult(intent, UtilCode.MainActivityForResult);
                break;
        }
    }
    /**
     * 清除控件焦点，隐藏软键盘
     */
    private void clearKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(password.getWindowToken(),0);
        imm.hideSoftInputFromWindow(email.getWindowToken(),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case UtilCode.MainActivityForResult:
                if(data==null)
                    return;
                String name = data.getStringExtra("name");
                String pathword = data.getStringExtra("pathword");
                if(name!=null&&!name.trim().equals("")){
                    if(pathword!=null&&!pathword.trim().equals("")){
                        email.setText(name);
                        password.setText(pathword);
                    }
                }
                break;
        }
    }
}
