package com.jjc.dreamproject.model.photo;

import android.util.Log;

import com.google.gson.Gson;
import com.jjc.dreamproject.bean.UrlPhotoEntity;
import com.jjc.dreamproject.contract.photo.PhotoShowContract;
import com.jjc.dreamproject.presenter.photo.PhotoShowPresenter;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PhotoShowModel implements PhotoShowContract.Model {
    private PhotoShowPresenter presenter;

    public PhotoShowModel(PhotoShowPresenter presenter){
        this.presenter = presenter;
    }
    @Override
    public void getPhoto(int page) {
        String url = "http://www.apiopen.top:88/meituApi?page="+page;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        try {
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    presenter.returnPhotos(false,null);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    Gson gson = new Gson();
                    UrlPhotoEntity urlPhotoEntity = gson.fromJson(json, UrlPhotoEntity.class);
                    presenter.returnPhotos(true,urlPhotoEntity.getData());

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            presenter.returnPhotos(false,null);
        }
    }
}

