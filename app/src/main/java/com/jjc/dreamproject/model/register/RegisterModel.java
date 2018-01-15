package com.jjc.dreamproject.model.register;

import com.jjc.dreamproject.bean.UserBean;
import com.jjc.dreamproject.contract.register.RegisterContract;
import com.jjc.dreamproject.presenter.register.RegisterPresenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterModel implements RegisterContract.Model {
    private RegisterPresenter presenter;

    public RegisterModel(RegisterPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void regist(final String name, final String pathword) {
        BmobQuery<UserBean> query = new BmobQuery<>();
        query.addWhereEqualTo("name",name);
        query.setLimit(50);
        query.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if(e==null&&list.size()!=0){
                    presenter.returnResult(false,"该账号已存在，请重新输入","","");
                }else{
                    if(e==null||e.getErrorCode()==101){
                        UserBean userBean = new UserBean();
                        userBean.setName(name);
                        userBean.setPathword(pathword);
                        userBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    presenter.returnResult(true,"注册成功",name,pathword);
                                }else{
                                    presenter.returnResult(false,"注册失败，请重试","","");
                                }
                            }
                        });
                    }else{
                        presenter.returnResult(false,"注册失败，请重试","","");
                    }
                }
            }
        });
    }
}

