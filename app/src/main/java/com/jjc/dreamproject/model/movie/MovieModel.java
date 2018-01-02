package com.jjc.dreamproject.model.movie;

import android.util.Log;

import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.movie.MovieContract;
import com.jjc.dreamproject.presenter.movie.MoviePresenter;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MovieModel implements MovieContract.Model {
    private MoviePresenter presenter;

    public MovieModel(MoviePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void start(final int page) {
          Observable.create(new Observable.OnSubscribe<Object>() {
              @Override
              public void call(Subscriber<? super Object> subscriber) {
                  BmobQuery<MediaBean> bmobQuery = new BmobQuery<MediaBean>();
                  bmobQuery.addWhereEqualTo("tylp",3);
                  if(page!=1){
                      bmobQuery.setSkip((page-1)*50);
                  }
                  bmobQuery.setLimit(50*page);
                  bmobQuery.findObjects(new FindListener<MediaBean>() {
                      @Override
                      public void done(List<MediaBean> list, BmobException e) {
                            if(e==null){
                                if(list.size()==0){
                                    presenter.returnRequest(false,null,"数据已经获取完毕~~~");
                                }else{
                                    presenter.returnRequest(true,list,"");
                                }
                            }else{
                                presenter.returnRequest(false,null,e.getMessage());
                            }
                      }
                  });
              }
          }).subscribeOn(Schedulers.newThread()).subscribe();
    }
}

