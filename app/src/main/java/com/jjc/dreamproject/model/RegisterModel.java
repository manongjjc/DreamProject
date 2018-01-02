package com.jjc.dreamproject.model;

import android.util.Log;

import com.jjc.dreamproject.bean.UserBean;
import com.jjc.dreamproject.contract.RegisterContract;
import com.jjc.dreamproject.presenter.RegisterPresenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterModel implements RegisterContract.Model {

    @Override
    public void regist(final String name, final String pathword, final RegisterPresenter registerPresenter) {
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.addWhereEqualTo("name",name);
        query.setLimit(50);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if(e==null&&list.size()!=0){
                    registerPresenter.returnResult(false,"该账号已存在，请重新输入","","");
                }else{
                    if(e==null||e.getErrorCode()==101){
                        UserBean userBean = new UserBean();
                        userBean.setName(name);
                        userBean.setPathword(pathword);
                        userBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    registerPresenter.returnResult(true,"注册成功",name,pathword);
                                }else{
                                    registerPresenter.returnResult(false,"注册失败，请重试","","");
                                }
                            }
                        });
                    }else{
                        registerPresenter.returnResult(false,"注册失败，请重试","","");
                    }
                }
            }
        });
    }
}

