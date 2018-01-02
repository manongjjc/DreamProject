package com.jjc.dreamproject.view.music;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.music.MusicAdapter;
import com.jjc.dreamproject.adapter.music.MusicDrawerAdapter;
import com.jjc.dreamproject.adapter.music.MusicQueryAdapter;
import com.jjc.dreamproject.bean.HitMusicEntity;
import com.jjc.dreamproject.contract.music.MusicContract;
import com.jjc.dreamproject.presenter.music.MusicPresenter;

import com.jjc.dreamproject.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MusicActivity extends BaseActivity<MusicContract.Presenter> implements MusicContract.View {
    private RecyclerView recyclerView;
    private GridLayoutManager mLayoutManager;
    private MusicAdapter musicAdapter;
    private Toolbar toolbar;
    private DrawerLayout musicDrawer;
    private MaterialMenuView music_menu;
    private ListView music_right_menu;
    private boolean isDrawerOpened;
    private List<Integer> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        initToolbar();
        initEvent();
        presenter.startHotTop();
    }

    private void initEvent() {
        list.add(1);
        list.add(2);
        list.add(3);
        MusicDrawerAdapter drawerAdapter = new MusicDrawerAdapter(list,this);
        music_right_menu.setAdapter(drawerAdapter);
        music_right_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                musicDrawer.closeDrawer(music_right_menu);
                switch (list.get(i)){
                    case 1:
                        presenter.startHotTop();
                        break;
                    case 2:
                        presenter.startNewMusic();
                        break;
                    case 3:
                        Intent in = new Intent();
                        in.setClass(MusicActivity.this, MusicSearchActivity.class);
                        startActivity(in);
                        break;
                }
            }
        });
        musicDrawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                music_menu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_X,
                        isDrawerOpened ? slideOffset :2 -  slideOffset
                );
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpened = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpened = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        music_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDrawerOpened){
                    musicDrawer.closeDrawer(music_right_menu);
                }else{
                    musicDrawer.openDrawer(music_right_menu);
                }
            }
        });
    }

    private void initToolbar() {
        toolbar.findViewById(R.id.music_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.music_recycler);
        toolbar = (Toolbar) findViewById(R.id.music_toolbar);
        mLayoutManager=new GridLayoutManager(MusicActivity.this,1, GridLayoutManager.VERTICAL,false);//设置为一个3列的纵向网格布局
        recyclerView.setLayoutManager(mLayoutManager);
        musicDrawer = (DrawerLayout) findViewById(R.id.music_drawer);
        music_menu = (MaterialMenuView) findViewById(R.id.music_menu);
        music_right_menu = (ListView) findViewById(R.id.music_right_menu);
    }


    @Override
    public MusicContract.Presenter bindPresenter() {
        return new MusicPresenter();
    }


    @Override
    public void showHotTopMusic(boolean isSucess , final HitMusicEntity musicEntity, String alert) {
        if(isSucess){
                   Observable.create(new Observable.OnSubscribe<Object>() {
                       @Override
                       public void call(Subscriber<? super Object> subscriber) {
                           if(musicAdapter==null){
                               musicAdapter = new MusicAdapter(musicEntity,MusicActivity.this);
                               recyclerView.setAdapter(musicAdapter);
                           }else{
                               musicAdapter.setEntity(musicEntity);
                           }
                       }
                   }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
        }
    }

    @Override
    public void showNewMusic(boolean isSucess , final HitMusicEntity musicEntity, String alert) {
        if(isSucess){
            Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    if(musicAdapter==null){
                        musicAdapter = new MusicAdapter(musicEntity,MusicActivity.this);
                        recyclerView.setAdapter(musicAdapter);
                    }else{
                        musicAdapter.setEntity(musicEntity);
                    }
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

