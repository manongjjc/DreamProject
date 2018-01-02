package com.jjc.dreamproject.model.music;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.music.MyMusicContract;
import com.jjc.dreamproject.presenter.music.MyMusicPresenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MyMusicModel implements MyMusicContract.Model {
    private MyMusicPresenter presenter;

    public MyMusicModel(MyMusicPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getMyMusic(final int pager) {
          Observable.create(new Observable.OnSubscribe<Object>() {
              @Override
              public void call(Subscriber<? super Object> subscriber) {
                  BmobQuery<MediaBean> bmobQuery = new BmobQuery<>();
                  bmobQuery.addWhereEqualTo("tylp",2);
                  if(pager!=1){
                      bmobQuery.setSkip((pager-1)*50);
                  }
                  bmobQuery.setLimit(pager*5);
                  bmobQuery.findObjects(new FindListener<MediaBean>() {
                      @Override
                      public void done(List<MediaBean> list, BmobException e) {
                          if(e==null){
                              if(list.size()==0)
                                  presenter.returnRequest(false,null,"已经加载完毕~~~");
                              else
                                  presenter.returnRequest(true,list,"");
                          }else{
                              if(e.getErrorCode()==101){
                                  if(pager==1){
                                      presenter.returnRequest(false,null,"您尚未收藏音乐");
                                  }else{
                                      presenter.returnRequest(false,null,"已经加载完毕~~~");
                                  }
                              }else{
                                  presenter.returnRequest(false,null,"服务器异常，请稍后再试!");
                              }
                          }
                      }
                  });
              }
          }).subscribeOn(Schedulers.newThread()).subscribe();

    }
}

