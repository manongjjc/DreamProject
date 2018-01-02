package com.jjc.dreamproject.view.music;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.music.MusicQueryAdapter;
import com.jjc.dreamproject.adapter.music.MusicSearchHistory;
import com.jjc.dreamproject.bean.QueryMusicEntity;
import com.jjc.dreamproject.contract.music.MusicSearchContract;
import com.jjc.dreamproject.presenter.music.MusicSearchPresenter;
import com.jjc.dreamproject.util.SharedPreferencesUtil;
import com.jjc.dreamproject.util.ToastUtil;
import com.jjc.dreamproject.util.UtilCode;
import com.jjc.dreamproject.view.BaseActivity;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MusicSearchActivity extends BaseActivity<MusicSearchContract.Presenter> implements MusicSearchContract.View {
    private ListView musicSearchList;
    private Toolbar music_search_toolabr;
    private ImageView claer;
    private EditText editText;
    private int lage = 1;
    private MusicQueryAdapter queryAdapter;
    private SwipyRefreshLayout swipeRefreshLayout;
    private boolean isSearching = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_search);
        initVive();
        initToolbar();
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<String>();
        list.add("搜索历史");
        String sp = SharedPreferencesUtil.getInstance(MusicSearchActivity.this).getSP(UtilCode.SEARCH_HISTORY);
        String[] split = sp.split(",");
        if(split==null||split.length==0||sp.equals("")){
        }else {
            list.addAll(Arrays.asList(split));
        }
        MusicSearchHistory history = new MusicSearchHistory(list,MusicSearchActivity.this);
        musicSearchList.setAdapter(history);
        final List<String> finalList = list;
        musicSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0)
                    editText.setText(finalList.get(i));
            }
        });
        isSearching = false;
    }

    private void initToolbar() {
        music_search_toolabr.findViewById(R.id.music_search_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                if(s.equals("")){
                    finish();
                }else{
                    editText.setText("");
                }
            }
        });
        editText = music_search_toolabr.findViewById(R.id.music_search_editext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String trim = editable.toString().trim();
                if(trim.equals("")){
                    claer.setImageResource(R.drawable.ic_mic);
                    List<String> list = new ArrayList<String>();
                    list.add("搜索历史");
                    String sp = SharedPreferencesUtil.getInstance(MusicSearchActivity.this).getSP(UtilCode.SEARCH_HISTORY);
                    String[] split = sp.split(",");
                    if(split==null||split.length==0||sp.equals("")){
                    }else {
                        list.addAll(Arrays.asList(split));
                    }
                    MusicSearchHistory history = new MusicSearchHistory(list,MusicSearchActivity.this);
                    musicSearchList.setAdapter(history);
                    final List<String> finalList = list;
                    musicSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i!=0)
                                editText.setText(finalList.get(i));
                        }
                    });
                    isSearching = false;
                }else{
                    lage = 1;
                    claer.setImageResource(R.drawable.ic_clear);
                    presenter.start(lage*20,trim);
                    isSearching = true;
                }
            }
        });
        claer = music_search_toolabr.findViewById(R.id.music_search_claer);
        claer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().equals(""))
                    return;
                editText.setText("");
            }
        });
    }

    private void initVive() {
        musicSearchList = (ListView) findViewById(R.id.musicSearchList);
        music_search_toolabr = (Toolbar) findViewById(R.id.music_search_toolabr);
        swipeRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.music_search_swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                if(direction == SwipyRefreshLayoutDirection.TOP){
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    if(isSearching){
                        lage++;
                        String trim = editText.getText().toString().trim();
                        presenter.start(lage*20,trim);
                    }else{
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
    }


    @Override
    public MusicSearchContract.Presenter bindPresenter() {
        return new MusicSearchPresenter();
    }


    @Override
    public void showQueryData(final boolean isSuceeed, final List<QueryMusicEntity.DataBean.SongBean.ListBean> songs) {
               Observable.create(new Observable.OnSubscribe<Object>() {
                   @Override
                   public void call(Subscriber<? super Object> subscriber) {
                       swipeRefreshLayout.setRefreshing(false);
                       List<QueryMusicEntity.DataBean.SongBean.ListBean> data;
                       if(isSuceeed){
                           data = songs;
                       }else{
                           new ToastUtil().Short(MusicSearchActivity.this,"未查询到相关数据").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                           data = new ArrayList<>();
                       }
                       if(lage==1){
                           queryAdapter = new MusicQueryAdapter(data,MusicSearchActivity.this,editText.getText().toString().trim());
                           musicSearchList.setAdapter(queryAdapter);
                           musicSearchList.setOnItemClickListener(null);
                       }else {
                           if(queryAdapter!=null){
                               queryAdapter.setData(data);
                           }else{
                               queryAdapter = new MusicQueryAdapter(data,MusicSearchActivity.this,editText.getText().toString().trim());
                               musicSearchList.setAdapter(queryAdapter);
                               musicSearchList.setOnItemClickListener(null);
                           }
                       }
                   }
               }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) {//按键的按下事件
            } else if (event.getAction() == KeyEvent.ACTION_UP && event.getRepeatCount() == 0) {//按键的抬起事件
                String s = editText.getText().toString();
                if(s.equals("")){
                    finish();
                }else{
                    editText.setText("");
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }
}

