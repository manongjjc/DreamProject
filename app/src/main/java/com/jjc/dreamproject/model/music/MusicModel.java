package com.jjc.dreamproject.model.music;

import com.google.gson.Gson;
import com.jjc.dreamproject.bean.HitMusicEntity;
import com.jjc.dreamproject.bean.SpecialEntity;
import com.jjc.dreamproject.contract.music.MusicContract;
import com.jjc.dreamproject.presenter.music.MusicPresenter;
import com.jjc.dreamproject.util.UrlApi;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MusicModel implements MusicContract.Model {
    private MusicPresenter presenter;

    public MusicModel(MusicPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getHotTopMusic() {
          Observable.create(new Observable.OnSubscribe<Object>() {
              @Override
              public void call(Subscriber<? super Object> subscriber) {
                  BmobQuery<SpecialEntity> bmobQuery = new BmobQuery<SpecialEntity>();
                  bmobQuery.addWhereEqualTo("url", UrlApi.TotalHit);
                  bmobQuery.setLimit(50);
                  bmobQuery.findObjects(new FindListener<SpecialEntity>() {
                      @Override
                      public void done(List<SpecialEntity> list, BmobException e) {
                          if(e==null&&list!=null&&list.size()==1){
                              SpecialEntity specialEntity = list.get(0);
                              Gson gson = new Gson();
                              presenter.retrunHotTop(true,gson.fromJson(specialEntity.getJson(), HitMusicEntity.class),"");
                          }else{
                              presenter.retrunHotTop(false,null,"获取数据失败，请重试");
                          }
                      }
                  });
              }
          }).subscribeOn(Schedulers.newThread()).subscribe();

    }

    @Override
    public void getNewMusic() {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                BmobQuery<SpecialEntity> bmobQuery = new BmobQuery<SpecialEntity>();
                bmobQuery.addWhereEqualTo("url", UrlApi.NewMusic);
                bmobQuery.setLimit(50);
                bmobQuery.findObjects(new FindListener<SpecialEntity>() {
                    @Override
                    public void done(List<SpecialEntity> list, BmobException e) {
                        if(e==null&&list!=null&&list.size()==1){
                            SpecialEntity specialEntity = list.get(0);
                            Gson gson = new Gson();
                            presenter.retrunHotTop(true,gson.fromJson(specialEntity.getJson(), HitMusicEntity.class),"");
                        }else{
                            presenter.retrunHotTop(false,null,"获取数据失败，请重试");
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
    }
}

