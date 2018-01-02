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
import android.widget.ImageView;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.contract.MainContract;
import com.jjc.dreamproject.contract.RegisterContract;
import com.jjc.dreamproject.contract.photo.PhotoShowContract;
import com.jjc.dreamproject.presenter.MainPresenter;
import com.jjc.dreamproject.presenter.RegisterPresenter;
import com.jjc.dreamproject.presenter.photo.PhotoShowPresenter;
import com.jjc.dreamproject.util.ToastUtil;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;

/**
 * Created by JJC on 2017/11/29.
 */

public class RegisterActivity extends BaseActivity<RegisterContract.Presenter> implements RegisterContract.View, View.OnClickListener {
    private Toolbar activity_register_toolbar;
    private AutoCompleteTextView register_email;
    private EditText register_password;
    private Button email_register_button;
    private AutoCompleteTextView register_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initToolbar();
        addEvent();
    }

    private void addEvent() {
        email_register_button.setOnClickListener(this);
    }

    private void initToolbar() {
        activity_register_toolbar.setTitle("");
        ImageView icon = activity_register_toolbar.findViewById(R.id.register_icon);
        icon.setImageResource(R.drawable.ic_keyboard_arrow_left_black_24dp);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setSupportActionBar(activity_register_toolbar);
    }

    private void initView() {
        activity_register_toolbar = (Toolbar) findViewById(R.id.activity_register_toolbar);
        register_email = (AutoCompleteTextView) findViewById(R.id.register_email);
        register_password = (EditText) findViewById(R.id.register_password);
        email_register_button = (Button) findViewById(R.id.email_register_button);
        register_code = (AutoCompleteTextView) findViewById(R.id.register_code);
    }

    @Override
    public RegisterContract.Presenter bindPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void stopDialog(boolean isSuccess, String alert,String name,String pathword) {
        LemonBubble.hide();
        if(isSuccess){
            new ToastUtil().Short(this,alert).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
            Intent intent = new Intent();
            intent.putExtra("name",name);
            intent.putExtra("pathword",pathword);
            this.setResult(0,intent);
            finish();
        }else{
            new ToastUtil().Short(this,alert).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_register_button:
                clearKeyboard();
                String code  = register_code.getText().toString().trim();
                if(code.equals("")){
                    new ToastUtil().Short(this,"请输入注册码").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }else if(!code.equals("cqfeizhi")){
                    new ToastUtil().Short(this,"注册码错误，请重新输入").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }
                String email = register_email.getText().toString().trim();
                if(email.equals("")){
                    new ToastUtil().Short(this,"请输入账号").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }
                String password = register_password.getText().toString().trim();
                if(password.equals("")){
                    new ToastUtil().Short(this,"请输入密码").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }
                presenter.startRegist(email,password);
                LemonBubbleInfo roundProgressBubbleInfo = LemonBubble.getRoundProgressBubbleInfo();
                roundProgressBubbleInfo.setIconColor(Color.GRAY);
                roundProgressBubbleInfo.setTitle("注册中");
                LemonBubble.showBubbleInfo(RegisterActivity.this,roundProgressBubbleInfo);
                break;
        }
    }

    /**
     * 清除控件焦点，隐藏软键盘
     */
    private void clearKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(register_code.getWindowToken(),0);
        imm.hideSoftInputFromWindow(register_email.getWindowToken(),0);
        imm.hideSoftInputFromWindow(register_password.getWindowToken(),0);
    }
}
