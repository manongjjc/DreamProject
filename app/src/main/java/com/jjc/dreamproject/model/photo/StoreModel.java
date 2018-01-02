package com.jjc.dreamproject.model.photo;

import android.util.Log;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.photo.StoreContract;
import com.jjc.dreamproject.presenter.photo.StorePresenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StoreModel implements StoreContract.Model {
    private StorePresenter storePresenter;
    public StoreModel(StorePresenter storePresenter) {
        this.storePresenter = storePresenter;
    }

    @Override
    public void startRequest(int number,int left) {
        BmobQuery<MediaBean> bmobQuery = new BmobQuery<>();
        bmobQuery.setSkip(number*50+left);
        bmobQuery.setLimit(50);
        bmobQuery.addWhereEqualTo("tylp",1);
        bmobQuery.findObjects(new FindListener<MediaBean>() {
            @Override
            public void done(List<MediaBean> list, BmobException e) {
                if(e==null){
                    for (MediaBean mediaBean : list) {
                        Log.d("JYD",mediaBean.toString());
                    }
                    storePresenter.showStore(true,list,"");
                }else{
                    Log.d("JYD",e.toString());
                    if(e.getErrorCode()==101){
                        storePresenter.showStore(false,null,"列表为空~~~");
                    }else{
                        storePresenter.showStore(false,null,"加载失败，请重试");
                    }
                }
            }
        });
    }
}

