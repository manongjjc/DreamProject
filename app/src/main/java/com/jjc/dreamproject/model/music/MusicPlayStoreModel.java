package com.jjc.dreamproject.model.music;

import android.content.Context;

import com.jjc.dreamproject.contract.music.MusicPlayStoreContract;
import com.jjc.dreamproject.presenter.music.MusicPlayStorePresenter;
import com.jjc.dreamproject.util.SharedPreferencesUtil;
import com.jjc.dreamproject.util.UrlApi;
import com.jjc.dreamproject.view.lrc.LrcDataBuilder;
import com.jjc.dreamproject.view.lrc.LrcRow;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MusicPlayStoreModel implements MusicPlayStoreContract.Model {
    private MusicPlayStorePresenter playStorePresenter;

    public MusicPlayStoreModel(MusicPlayStorePresenter playStorePresenter) {
        this.playStorePresenter = playStorePresenter;
    }

    @Override
    public void getLrc(final String songId, final Context context) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                SharedPreferencesUtil instance = SharedPreferencesUtil.getInstance(context);
                String sp = instance.getSP(songId);
                if(sp==null||"".equals(sp)){
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String musicLyric = songId;
                    Request request = new Request.Builder()
                            .url(musicLyric)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    try {
                        Response execute = call.execute();
                        String lyr = new String(execute.body().bytes(), "GB2312");
                        if(lyr.contains("[CDATA[")){
                            instance.putSP(songId,lyr);
                            LrcDataBuilder builder = new LrcDataBuilder();
                            List<LrcRow> build = builder.Build(lyr);
                            playStorePresenter.startShowLrc(true,build);
                        }else{
                            playStorePresenter.startShowLrc(false,null);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        playStorePresenter.startShowLrc(false,null);
                    }
                }else{
                    LrcDataBuilder builder = new LrcDataBuilder();
                    List<LrcRow> build = builder.Build(sp);

                    playStorePresenter.startShowLrc(true,build);
                }
            }
        }).subscribeOn(Schedulers.newThread()).subscribe();
    }
}

