package com.jjc.dreamproject.view.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.GridAdapter;
import com.jjc.dreamproject.bean.UrlPhotoEntity;
import com.jjc.dreamproject.contract.photo.PhotoShowContract;
import com.jjc.dreamproject.presenter.photo.PhotoShowPresenter;
import com.jjc.dreamproject.view.BaseActivity;
import com.lqr.recyclerview.LQRRecyclerView;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class PhotoShowActivity extends BaseActivity<PhotoShowContract.Presenter> implements PhotoShowContract.View {
    private GridAdapter adapter;
    private List<UrlPhotoEntity.DataBean> list;
    private Toolbar photo_toolbar;
    private LQRRecyclerView photo_recyclerView;
    private SwipyRefreshLayout refreshLayout;
//    private GridLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private int currentpage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_show);
        initView();
        initToolbar();
        addEvent();
        presenter.startRequest(currentpage);
    }

    private void addEvent() {

        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                presenter.startRequest(++currentpage);
            }
        });
    }

    private void initToolbar() {
        photo_toolbar.findViewById(R.id.storePhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PhotoShowActivity.this,StoreActivity.class);
                startActivity(intent);
            }
        });
        photo_toolbar.findViewById(R.id.photo_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        photo_toolbar = (Toolbar) findViewById(R.id.photo_toolbar);
        photo_recyclerView = (LQRRecyclerView) findViewById(R.id.photo_recyclerView);
        refreshLayout = (SwipyRefreshLayout) findViewById(R.id.photo_refresh);

//        mLayoutManager=new GridLayoutManager(PhotoShowActivity.this,3, GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
//        photo_recyclerView.setLayoutManager(mLayoutManager);
    }


    @Override
    public PhotoShowContract.Presenter bindPresenter() {
        return new PhotoShowPresenter();
    }


    @Override
    public void addPhotos(final boolean isSuccess, final List<UrlPhotoEntity.DataBean> beanList) {
               Observable.create(new Observable.OnSubscribe<Object>() {
                   @Override
                   public void call(Subscriber<? super Object> subscriber) {
                       refreshLayout.setRefreshing(false);
                       if(isSuccess) {
                           list.addAll(beanList);
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if (adapter == null) {
                                       adapter = new GridAdapter(PhotoShowActivity.this, list);
                                       photo_recyclerView.setAdapter(adapter);
                                   } else {
                                       adapter.setData(list);
                                   }
                               }
                           });
                       }
                   }
               }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();

    }

    @Override
    protected void onDestroy() {
        if(list!=null)
            list.clear();
        super.onDestroy();
    }
}

