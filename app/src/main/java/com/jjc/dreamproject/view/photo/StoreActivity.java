package com.jjc.dreamproject.view.photo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.QuickPageAdapter;
import com.jjc.dreamproject.anim.DepthPageTransformer;
import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.photo.StoreContract;
import com.jjc.dreamproject.presenter.photo.StorePresenter;
import com.jjc.dreamproject.view.BaseActivity;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import uk.co.senab.photoview.PhotoView;


public class StoreActivity extends BaseActivity<StoreContract.Presenter> implements StoreContract.View {
    private Toolbar toolbar;
    private com.jjc.dreamproject.view.MyViewPager pager;
    private List<PhotoView> beens;
    private QuickPageAdapter<PhotoView> pageAdapter;
    private List<MediaBean> showData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        initView();
        initToolbar();
        addEvent();
        presenter.start(0,0);
    }

    private void addEvent() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d("JYD","执行刷新任务"+System.currentTimeMillis());
                int size = beens.size();
                if(size-1==position){
                    int page =  size/50;
                    int left =  size%50;
                    presenter.start(page,left);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initToolbar() {
        toolbar.findViewById(R.id.photoStorBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        showData = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.photoStoreTool);
        pager = (com.jjc.dreamproject.view.MyViewPager) findViewById(R.id.storePhotoPager);
        pager.setPageTransformer(false,new DepthPageTransformer());
        beens = new ArrayList<>();
    }


    @Override
    public StoreContract.Presenter bindPresenter() {
        return new StorePresenter();
    }


    @Override
    public void returnRequest(boolean isSucces, List<MediaBean> medias, final String alert) {
        if(isSucces){
            if(medias.size()==0) {
                Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        LemonBubble.showRight(StoreActivity.this,"全部加载完毕~~",2000);
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
            }else {
                showData.addAll(medias);
                for (final MediaBean media : medias) {
                    final PhotoView photoView = new PhotoView(StoreActivity.this);
                    ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    photoView.setLayoutParams(layoutParams);
                    Glide.with(StoreActivity.this)
                            .load(media.getUrl())
                            .placeholder(R.drawable.ic_defout)
                            .crossFade()
                            .into(photoView);//加载网络图片
                    beens.add(photoView);
                }

                Observable.create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {
                        LemonBubble.showRight(StoreActivity.this,"加载成功",2000);
                        if(pageAdapter==null){
                            pageAdapter = new QuickPageAdapter<PhotoView>(beens);
                            pager.setAdapter(pageAdapter);
                        }else{
                            pageAdapter.setList(beens);
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
            }

        }else{
            Observable.create(new Observable.OnSubscribe<Object>() {
                @Override
                public void call(Subscriber<? super Object> subscriber) {
                    LemonBubble.showError(StoreActivity.this,alert,2000);
                }
            }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();
        }

    }

    @Override
    protected void onDestroy() {
        if(beens!=null)
            beens.clear();
        super.onDestroy();
    }
}

