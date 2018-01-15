package com.jjc.dreamproject.view.register;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.contract.register.RegisterContract;
import com.jjc.dreamproject.presenter.register.RegisterPresenter;
import com.jjc.dreamproject.util.ToastUtil;
import com.jjc.dreamproject.util.UtilCode;
import com.jjc.dreamproject.view.BaseFragment;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;


public class RegisterFragment extends BaseFragment<RegisterContract.Presenter> implements RegisterContract.View, View.OnClickListener {
    private Toolbar activity_register_toolbar;
    private AutoCompleteTextView register_email;
    private EditText register_password;
    private Button email_register_button;
    private AutoCompleteTextView register_code;
    public RegisterFragment() {
        // Required empty public constructor
    }

    private RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_register, container, false);
        initView(view);
        initToolbar();
        addEvent();
        return view;
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
                getActivity().onBackPressed();

            }
        });
        AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
        appCompatActivity.setSupportActionBar(activity_register_toolbar);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState != null) {
            //data recovery
            register_email.setText(savedInstanceState.getString("email",""));
            register_password.setText(savedInstanceState.getString("password",""));
            register_code.setText(savedInstanceState.getString("code",""));
        }

    }

    private void initView(View view) {
        activity_register_toolbar = view.findViewById(R.id.activity_register_toolbar);
        register_email = view.findViewById(R.id.register_email);
        register_password = view.findViewById(R.id.register_password);
        email_register_button = view.findViewById(R.id.email_register_button);
        register_code = view.findViewById(R.id.register_code);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //The exception status data is saved.
        outState.putString("email",register_email.getText().toString().trim());
        outState.putString("password",register_password.getText().toString().trim());
        outState.putString("code",register_code.getText().toString().trim());
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
    public RegisterContract.Presenter bindPresenter() {
        return new RegisterPresenter();
    }


    @Override
    public void stopDialog(boolean isSuccess, String alert, String name, String pathword) {
        LemonBubble.hide();
        if(isSuccess){
            new ToastUtil().Short(getContext(),alert).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
            Bundle bundle = new Bundle();
            bundle.putString("name",name);
            bundle.putString("pathword",pathword);
            setFragmentResult(UtilCode.RESULT_OK,bundle);
//            this.setResult(0,intent);
            getActivity().onBackPressed();
        }else{
            new ToastUtil().Short(getContext(),alert).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_register_button:
                clearKeyboard();
                String code  = register_code.getText().toString().trim();
                if(code.equals("")){
                    new ToastUtil().Short(getContext(),"请输入注册码").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }else if(!code.equals("cqfeizhi")){
                    new ToastUtil().Short(getContext(),"注册码错误，请重新输入").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }
                String email = register_email.getText().toString().trim();
                if(email.equals("")){
                    new ToastUtil().Short(getContext(),"请输入账号").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }
                String password = register_password.getText().toString().trim();
                if(password.equals("")){
                    new ToastUtil().Short(getContext(),"请输入密码").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    return;
                }
                presenter.startRegist(email,password);
                LemonBubbleInfo roundProgressBubbleInfo = LemonBubble.getRoundProgressBubbleInfo();
                roundProgressBubbleInfo.setIconColor(Color.GRAY);
                roundProgressBubbleInfo.setTitle("注册中");
                LemonBubble.showBubbleInfo(getContext(),roundProgressBubbleInfo);
                break;
        }
    }

    /**
     * 清除控件焦点，隐藏软键盘
     */
    private void clearKeyboard(){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(register_code.getWindowToken(),0);
        imm.hideSoftInputFromWindow(register_email.getWindowToken(),0);
        imm.hideSoftInputFromWindow(register_password.getWindowToken(),0);
    }

}

