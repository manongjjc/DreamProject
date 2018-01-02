package com.jjc.dreamproject.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.FirstActivityAdapter;
import com.jjc.dreamproject.adapter.FirstDrawerAdapter;
import com.jjc.dreamproject.adapter.QuickPageAdapter;
import com.jjc.dreamproject.anim.DepthPageTransformer;
import com.jjc.dreamproject.contract.FirstContract;
import com.jjc.dreamproject.presenter.FirstPresenter;

import com.jjc.dreamproject.util.UrlApi;
import com.jjc.dreamproject.view.BaseActivity;
import com.jjc.dreamproject.view.movie.MovieActivity;
import com.jjc.dreamproject.view.music.MusicActivity;
import com.jjc.dreamproject.view.music.MyMusicActivity;
import com.jjc.dreamproject.view.photo.FixedSpeedScroller;
import com.jjc.dreamproject.view.photo.PhotoShowActivity;
import com.jjc.dreamproject.view.photo.StoreActivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class FirstActivity extends BaseActivity<FirstContract.Presenter> implements FirstContract.View {
    private RecyclerView fristRecyvler;
    private GridLayoutManager mLayoutManager;
    private MyViewPager pager;
    private List<String> urls;
    private FixedSpeedScroller mScroller;
    private LinearLayout viewpager_linerlayout;
    private List<ImageView> mDots;
    private MaterialMenuView first_menu;
    private DrawerLayout first_drawer;
    private ListView first_list;
    private List<Integer> keys;
    private boolean isDrawerOpened;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int currentItem = pager.getCurrentItem();
                    if (isForward) {
                        if (currentItem == urls.size() - 1) {
                            pager.setCurrentItem(currentItem - 1);
                            mScroller.setmDuration(1000);
                            isForward = false;
                        } else {
                            pager.setCurrentItem(currentItem + 1);
                            mScroller.setmDuration(1000);
                        }
                        handler.sendEmptyMessageDelayed(0, 3000);
                    } else {
                        if (currentItem == 0) {
                            pager.setCurrentItem(currentItem + 1);
                            mScroller.setmDuration(1000);
                            isForward = true;
                        } else {
                            pager.setCurrentItem(currentItem - 1);
                            mScroller.setmDuration(1000);
                        }
                        handler.sendEmptyMessageDelayed(0, 3000);
                    }
                    break;
            }
        }
    };
    private boolean isForward = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        initView();
        initData();
        initPager();
        initDrawer();
        handler.sendEmptyMessage(0);
    }

    private void initDrawer() {
        first_drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                first_menu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        isDrawerOpened ? slideOffset : 2 - slideOffset
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
        first_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDrawerOpened) {
                    first_drawer.closeDrawer(first_list);
                } else {
                    first_drawer.openDrawer(first_list);
                }
            }
        });
        keys = new ArrayList<>();
        keys.add(1);
        keys.add(2);
        keys.add(3);
        FirstDrawerAdapter adapter = new FirstDrawerAdapter(keys, this);
        first_list.setAdapter(adapter);
        first_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;

                switch (keys.get(i)) {
                    case 1:
                        intent = new Intent();
                        intent.setClass(FirstActivity.this, StoreActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent();
                        intent.setClass(FirstActivity.this, MyMusicActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent();
                        intent.setClass(FirstActivity.this, MovieActivity.class);
                        startActivity(intent);
                        break;
                }
                closeDrawer();
            }
        });
    }

    private void initPager() {
        List<ImageView> list = new ArrayList<>();
        urls = new ArrayList<>();
        urls.add(UrlApi.MusicPhoto1);
        urls.add(UrlApi.MusicPhoto2);
        urls.add(UrlApi.PicturePhoto1);
        urls.add(UrlApi.PicturePhoto2);
        urls.add(UrlApi.MoviePhoto1);
        urls.add(UrlApi.MoviePhoto2);
        for (int i = 0; i < urls.size(); i++) {
            ImageView imageView = new ImageView(this);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this)
                    .load(urls.get(i))
                    .placeholder(R.drawable.ic_defout)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);//加载网络图片
            switch (i) {
                case 0:
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(FirstActivity.this, MusicActivity.class);
                            startActivity(intent);
                        }
                    });

                    break;
                case 1:
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(FirstActivity.this, MusicActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case 2:
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(FirstActivity.this, PhotoShowActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case 3:
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent();
                            intent.setClass(FirstActivity.this, PhotoShowActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
            list.add(imageView);
        }
        QuickPageAdapter<ImageView> pageAdapter = new QuickPageAdapter<>(list);
        pager.setAdapter(pageAdapter);
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller = new FixedSpeedScroller(pager.getContext(), new AccelerateInterpolator());
            mField.set(pager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //指示条
        mDots = new ArrayList<ImageView>();//底部圆点集合的初始化
        for (int i = 0; i < urls.size(); i++) {//根据界面数量动态添加圆点
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(35, 35));//设置ImageView的宽度和高度
            imageView.setPadding(5, 5, 5, 5);//设置圆点的Padding，与周围的距离
            imageView.setImageResource(R.drawable.dot_normal);//设置图片
            mDots.add(imageView);//将该图片添加到圆点集合中
            viewpager_linerlayout.addView(imageView);//将图片添加到LinearLayout中
        }
        mDots.get(0).setImageResource(R.drawable.dot_select);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < urls.size(); i++) {
                    //将所有的圆点设置为为选中时候的图片
                    mDots.get(i).setImageResource(R.drawable.dot_normal);
                }
                //将被选中的图片中的圆点设置为被选中的时候的图片
                mDots.get(position).setImageResource(R.drawable.dot_select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        FirstActivityAdapter adapter = new FirstActivityAdapter(this, list);
        fristRecyvler.setAdapter(adapter);

    }

    private void initView() {
        fristRecyvler = (RecyclerView) findViewById(R.id.fristRecyvler);
        pager = (MyViewPager) findViewById(R.id.frist_activity_pager);
        pager.setPageTransformer(false, new DepthPageTransformer());
        mLayoutManager = new GridLayoutManager(FirstActivity.this, 3, GridLayoutManager.VERTICAL, false);//设置为一个3列的纵向网格布局
        fristRecyvler.setLayoutManager(mLayoutManager);
        viewpager_linerlayout = (LinearLayout) findViewById(R.id.viewpager_linerlayout);
        first_menu = (MaterialMenuView) findViewById(R.id.first_menu);
        first_drawer = (DrawerLayout) findViewById(R.id.first_drawer);
        first_list = (ListView) findViewById(R.id.first_list);
    }


    @Override
    public FirstContract.Presenter bindPresenter() {
        return new FirstPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null)
            handler.removeMessages(0);
    }

    private void openDrawer(){
        first_drawer.openDrawer(first_list);
    }

    private void closeDrawer(){
        first_drawer.closeDrawer(first_list);
    }
}

