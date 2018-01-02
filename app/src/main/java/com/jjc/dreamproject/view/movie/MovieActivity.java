package com.jjc.dreamproject.view.movie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.movie.MovieContract;
import com.jjc.dreamproject.presenter.movie.MoviePresenter;
import com.jjc.dreamproject.util.ToastUtil;
import com.jjc.dreamproject.view.BaseActivity;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.superplayer.library.SuperPlayer;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MovieActivity extends BaseActivity<MovieContract.Presenter> implements MovieContract.View, SuperPlayer.OnNetChangeListener {
    private Toolbar movie_toolbar;
    private TextView moview_play_titile;
    private SwipyRefreshLayout movie_refresh;
    private SuperPlayer movie_super_player;
    private int currentPage = 1;
    private int currentMedia = -1;
    private List<MediaBean> beanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        initView();
        initToolbar();
        initEvent();
        presenter.disposeMovie(currentPage);
    }

    private void initEvent() {
        movie_refresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
                    movie_refresh.setRefreshing(false);
                    if(currentMedia==0||currentMedia==-1){
                        currentMedia = 0;
                    }else{
                        currentMedia--;
                    }
                    startPlay();
                }else {
                    int size = beanList.size();
                    if(currentMedia<size-1){
                        movie_refresh.setRefreshing(false);
                        currentMedia++;
                        startPlay();
                    }else{
                        currentPage++;
                        presenter.disposeMovie(currentPage);
                    }
                }
            }
        });
        movie_super_player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                        Log.d("JYD","监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）");
                    }
                }).onComplete(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                         */

                        if(currentMedia==beanList.size()-1){
                            new ToastUtil().Short(MovieActivity.this,"视频已经播放完毕").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                        }else{
                            currentMedia++;
                            startPlay();
                        }

                    }
                }).onInfo(new SuperPlayer.OnInfoListener() {
                    @Override
                    public void onInfo(int what, int extra) {
                        /**
                         * 监听视频的相关信息。
                         */
                        Log.d("JYD","监听视频的相关信息");
                    }
                }).onError(new SuperPlayer.OnErrorListener() {
                    @Override
                    public void onError(int what, int extra) {
                        /**
                         * 监听视频播放失败的回调
                         */
                        Log.d("JYD","监听视频播放失败的回调");
                    }
                });//开始播放视频
        movie_super_player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
        movie_super_player.setPlayerWH(0, movie_super_player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
    }

    private void initToolbar() {
        moview_play_titile = movie_toolbar.findViewById(R.id.moview_play_titile);
        movie_toolbar.findViewById(R.id.movie_play_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        movie_toolbar = (Toolbar) findViewById(R.id.movie_toolbar);
        movie_refresh = (SwipyRefreshLayout) findViewById(R.id.movie_refresh);
        movie_super_player = (SuperPlayer) findViewById(R.id.movie_super_player);
    }


    @Override
    public MovieContract.Presenter bindPresenter() {
        return new MoviePresenter();
    }


    @Override
    public void showNewMovies(final boolean isSuccess, final List<MediaBean> beanList1, final String message) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                movie_refresh.setRefreshing(false);
                if(isSuccess){
                    beanList.addAll(beanList1);
                    if(currentMedia==-1){
                        currentMedia = 0;
                    }else{
                        currentMedia ++;
                    }
                    startPlay();
                }else{
                    new ToastUtil().Short(MovieActivity.this,message).setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                }
            }
         }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();

    }
    private void startPlay(){
        int size = beanList.size();
        if(currentMedia==-1){
            currentMedia = 0;
        }
        if(currentMedia>=size)
            currentMedia = size-1;
        MediaBean mediaBean = beanList.get(currentMedia);
        moview_play_titile.setText(mediaBean.getTitle());
        movie_super_player.play(mediaBean.getUrl()==null?"http://ws.stream.qqmusic.qq.com/1913719.m4a?fromtag=46":mediaBean.getUrl());//开始播放视频
    }
    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
        Toast.makeText(this, "当前网络环境是WIFI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        Toast.makeText(this, "当前网络环境是手机网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        Toast.makeText(this, "网络链接断开", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {
        Toast.makeText(this, "无网络链接", Toast.LENGTH_SHORT).show();
    }


    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (movie_super_player != null) {
            movie_super_player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (movie_super_player != null) {
            movie_super_player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movie_super_player != null) {
            movie_super_player.onDestroy();
        }
    }
}

