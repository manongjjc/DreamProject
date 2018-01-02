package com.jjc.dreamproject.view.music;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.music.MyItemDecoration;
import com.jjc.dreamproject.adapter.music.MyMusicAdapter;
import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.music.MyMusicContract;
import com.jjc.dreamproject.presenter.music.MyMusicPresenter;

import com.jjc.dreamproject.view.BaseActivity;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MyMusicActivity extends BaseActivity<MyMusicContract.Presenter> implements MyMusicContract.View {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    private SwipyRefreshLayout swipyRefreshLayout;
    private int page = 1;
    private MyMusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        initView();
        initToolbar();
        initEvent();
        presenter.startRequest(page);
        page++;
        LemonBubbleInfo roundProgressBubbleInfo = LemonBubble.getRoundProgressBubbleInfo();
        roundProgressBubbleInfo.setIconColor(Color.GRAY);
        roundProgressBubbleInfo.setTitle("正在获取数据");
        LemonBubble.showBubbleInfo(this,roundProgressBubbleInfo);
    }

    private void initEvent() {
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                //调用接口
                presenter.startRequest(page);
                page++;
            }
        });
    }

    private void initToolbar() {
        toolbar.findViewById(R.id.mymusic_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.mymusic_toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.mymusic_recycler);
        mLayoutManager=new GridLayoutManager(MyMusicActivity.this,1, GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
        recyclerView.setLayoutManager(mLayoutManager);
        swipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.mymusic_swipy);
        recyclerView.addItemDecoration(new MyItemDecoration());

    }


    @Override
    public MyMusicContract.Presenter bindPresenter() {
        return new MyMusicPresenter();
    }


    @Override
    public void showMusic(final boolean isSucessed, final List<MediaBean> mediaBeanList, final String message) {
               Observable.create(new Observable.OnSubscribe<Object>() {
                   @Override
                   public void call(Subscriber<? super Object> subscriber) {
                       if(isSucessed){
                           LemonBubble.showRight(MyMusicActivity.this,"获取成功",1500);
                           if(adapter==null){
                               adapter = new MyMusicAdapter(MyMusicActivity.this,mediaBeanList);
                               recyclerView.setAdapter(adapter);
                           }else{
                               adapter.addData(mediaBeanList);
                           }
                       }else{
                           LemonBubble.showError(MyMusicActivity.this,message,1500);
                       }
                       swipyRefreshLayout.setRefreshing(false);
                   }
               }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();

    }
}

