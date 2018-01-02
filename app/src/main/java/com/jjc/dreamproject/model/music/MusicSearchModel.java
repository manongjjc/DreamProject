package com.jjc.dreamproject.model.music;

import android.util.Log;

import com.google.gson.Gson;
import com.jjc.dreamproject.bean.QueryMusicEntity;
import com.jjc.dreamproject.contract.music.MusicSearchContract;
import com.jjc.dreamproject.presenter.music.MusicSearchPresenter;
import com.jjc.dreamproject.util.UrlApi;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MusicSearchModel implements MusicSearchContract.Model {
    private MusicSearchPresenter presenter;

    public MusicSearchModel(MusicSearchPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void startQuery(final int number, final String key) {
          Observable.create(new Observable.OnSubscribe<Object>() {
              @Override
              public void call(Subscriber<? super Object> subscriber) {
                  OkHttpClient okHttpClient = new OkHttpClient();
                  String musicLyric = UrlApi.getQueryMusic(number,key);
                  Log.d("JYD",musicLyric);
                  Request request = new Request.Builder()
                          .url(musicLyric)
                          .build();
                  Call call = okHttpClient.newCall(request);
                  try {
                      Response execute = call.execute();
                      String json = execute.body().string();
                      Gson gson = new Gson();
                      QueryMusicEntity queryMusicEntity = gson.fromJson(json, QueryMusicEntity.class);
                      if(queryMusicEntity.getData()!=null&&queryMusicEntity.getData().getSong()!=null&&queryMusicEntity.getData().getSong().getList()!=null&&queryMusicEntity.getData().getSong().getList().size()>0){
                          presenter.showRequest(true,queryMusicEntity.getData().getSong().getList());
                          Log.d("JYD",json);
                      }else{
                          presenter.showRequest(false,null);
                      }
                  }catch (Exception e){
                      e.printStackTrace();
                      presenter.showRequest(false,null);
                      Log.d("JYD",e.toString());
                  }
              }
          }).subscribeOn(Schedulers.newThread()).subscribe();
    }
}

