package com.jjc.dreamproject.view.music;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;
import com.bumptech.glide.Glide;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.adapter.music.MyItemDecoration;
import com.jjc.dreamproject.adapter.music.MyMusicDrawerAdapter;
import com.jjc.dreamproject.anim.BlurTransformation;
import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.music.MusicPlayStoreContract;
import com.jjc.dreamproject.observer.StoreMusicObserver;
import com.jjc.dreamproject.presenter.music.MusicPlayStorePresenter;

import com.jjc.dreamproject.util.SharedPreferencesUtil;
import com.jjc.dreamproject.util.ToastUtil;
import com.jjc.dreamproject.util.UrlApi;
import com.jjc.dreamproject.util.UtilCode;
import com.jjc.dreamproject.view.BaseActivity;
import com.jjc.dreamproject.view.lrc.ILrcView;
import com.jjc.dreamproject.view.lrc.LrcRow;
import com.jjc.dreamproject.view.lrc.LrcShowBarInterface;
import com.jjc.dreamproject.view.lrc.LrcView;
import com.superplayer.library.SuperPlayer;
import com.superplayer.library.mediaplayer.SeekListener;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MusicPlayStoreActivity extends BaseActivity<MusicPlayStoreContract.Presenter> implements MusicPlayStoreContract.View, SuperPlayer.OnNetChangeListener, SeekListener, LrcShowBarInterface {
    private Toolbar toolbar;
    private SuperPlayer view_super_player;
    private ImageView music_photo;
    private String url;
    private String photoUrl;
    private String name;
    private String lyricId;
    private String singerName;
    private LrcView lrcview;
    private MaterialMenuView music_menu;
    private DrawerLayout music_play_drawer;
    private RecyclerView music_store_recycler;
    private GridLayoutManager mLayoutManager;
    private MyMusicDrawerAdapter adapter;

    // 更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    // 更新歌词的定时器
    private Timer mTimer;
    // 更新歌词的定时任务
    private TimerTask mTask;

    private Handler handler = new Handler();

    private List<MediaBean> list;
    private MediaBean mediaBean;

    private boolean isDrawerOpened;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_store_play);
        initData();
        initView();
        initRecycler();
        initToolbar();
        initPlay();
        presenter.startLrc(lyricId, this);
        view_super_player.setMusicTylpListtener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView imageView = (ImageView) view;
                int i = setCurrentTylp();
                imageView.setImageResource(i);
            }
        }, getCurrentTylp());
    }

    private void initRecycler() {
        mLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);//设置为一个3列的纵向网格布局
        music_store_recycler.setLayoutManager(mLayoutManager);
        music_store_recycler.addItemDecoration(new MyItemDecoration());
        adapter = new MyMusicDrawerAdapter(list, mediaBean, this);
        adapter.setInter(new MyMusicDrawerAdapter.ReturnCurrentMedia() {
            @Override
            public void currentMedia(MediaBean mediaBean1) {
                Log.d("JYD", "onclick inter 222");
                music_play_drawer.closeDrawer(music_store_recycler);
                if (!mediaBean.getUrl().equals(mediaBean1.getUrl())) {
                    mediaBean = mediaBean1;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (adapter != null)
                                adapter.setCurrentMediaBean(mediaBean);
                            play();
                        }
                    },1000);

                }
            }
        });
        music_store_recycler.setAdapter(adapter);
//        music_store_recycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("JYD", "onclick inter 222");
//                MediaBean mBean = list.get(i);
//                music_play_drawer.closeDrawer(music_store_recycler);
//                if (!mediaBean.getUrl().equals(mBean.getUrl())) {
//                    mediaBean = mBean;
//                    if (adapter != null)
//                        adapter.setCurrentMediaBean(mediaBean);
//                    play();
//                }
//            }
//        });

    }


    private void initData() {
        Intent intent = getIntent();
        mediaBean = (MediaBean) intent.getSerializableExtra("current");
        url = mediaBean.getUrl();
        photoUrl = mediaBean.getPhotoUrl();
        lyricId = mediaBean.getLyricId();
        name = mediaBean.getTitle();
        singerName = mediaBean.getSingerName();

        list = StoreMusicObserver.instance().getMedias();
    }

    private void initPlay() {
        view_super_player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new SuperPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                        Log.d("JYD", "监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）");
                        music_photo.setVisibility(View.VISIBLE);
                        lrcview.setVisibility(View.VISIBLE);
                        if (mTimer == null) {
                            mTimer = new Timer();
                            mTask = new LrcTask();
                            mTimer.scheduleAtFixedRate(mTask, 0, mPalyTimerDuration);
                        }
                    }
                }).onComplete(new Runnable() {
            @Override
            public void run() {
                /**
                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                 */
                stopLrcPlay();
                int currentTylp = getCurrentTylp();
                int size = list.size();
                int currentNumber = 0;
                for (int i = 0; i < list.size(); i++) {
                    MediaBean bean = list.get(i);
                    if (bean.getUrl().equals(mediaBean.getUrl())) {
                        currentNumber = i;
                        break;
                    }
                }
                switch (currentTylp) {
                    case R.drawable.ic_repeat:
                        if (currentNumber == size - 1) {
                            currentNumber = 0;
                        } else {
                            currentNumber++;
                        }
                        mediaBean = list.get(currentNumber);
                        play();
                        break;
                    case R.drawable.ic_playlist_play:
                        if (currentNumber == size - 1) {
                        } else {
                            currentNumber++;
                            mediaBean = list.get(currentNumber);
                            play();
                        }
                        break;
                    case R.drawable.ic_repeat_one:
                        play();
                        break;
                    case R.drawable.ic_shuffle:
                        Random random = new Random();
                        for (int i = 0; i < 100; i++) {
                            int abs = Math.abs(random.nextInt(100)) % size;
                            if (currentNumber == abs)
                                continue;
                            else {
                                mediaBean = list.get(abs);
                                break;
                            }
                        }
                        play();
                        if (adapter != null)
                            adapter.setCurrentMediaBean(mediaBean);
                        break;
                }

            }
        }).onInfo(new SuperPlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                /**
                 * 监听视频的相关信息。
                 */
                Log.d("JYD", "监听视频的相关信息");
            }
        }).onError(new SuperPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                /**
                 * 监听视频播放失败的回调
                 */
                Log.d("JYD", "监听视频播放失败的回调");
                music_photo.setVisibility(View.GONE);
                lrcview.setVisibility(View.GONE);
            }
        });//开始播放视频
        view_super_player.setScaleType(SuperPlayer.SCALETYPE_FITXY);
        view_super_player.setPlayerWH(0, view_super_player.getMeasuredHeight());//设置竖屏的时候屏幕的高度，如果不设置会切换后按照16:9的高度重置
    }

    private void initToolbar() {
        toolbar.findViewById(R.id.music_play_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView music_play_titile = toolbar.findViewById(R.id.music_play_titile);
        music_play_titile.setText(name == null ? "MusicPlay" : name);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.music_play_toolbar);
        view_super_player = (SuperPlayer) findViewById(R.id.view_super_player);
        music_photo = (ImageView) findViewById(R.id.music_photo);
        lrcview = (LrcView) findViewById(R.id.music_lrcview);
        music_menu = (MaterialMenuView) findViewById(R.id.my_music_menu);
        music_play_drawer = (DrawerLayout) findViewById(R.id.music_play_drawer);
        music_store_recycler = (RecyclerView) findViewById(R.id.music_store_recycler);

        lrcview.setBarInterface(this);
        view_super_player.setSeekListener(this);
        // 设置自定义的LrcView上下拖动歌词时监听
        lrcview.setLrcViewSeekListener(new ILrcView.LrcViewSeekListener() {

            @Override
            public void onSeek(LrcRow currentlrcrow, long Currenselectrowtime) {
                if (view_super_player != null) {
                    view_super_player.seekTo((int) Currenselectrowtime, true);
                }
            }
        });


        music_play_drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                music_menu.setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_X,
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
        music_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDrawerOpened) {
                    music_play_drawer.closeDrawer(music_store_recycler);
                } else {
                    music_play_drawer.openDrawer(music_store_recycler);
                }
            }
        });
    }

    /**
     * 网络链接监听类
     */
    @Override
    public void onWifi() {
//        Toast.makeText(this, "当前网络环境是WIFI", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMobile() {
        music_photo.setVisibility(View.GONE);
        lrcview.setVisibility(View.GONE);
//        Toast.makeText(this, "当前网络环境是手机网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisConnect() {
        music_photo.setVisibility(View.GONE);
        lrcview.setVisibility(View.GONE);
//        Toast.makeText(this, "网络链接断开", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoAvailable() {
        music_photo.setVisibility(View.GONE);
        lrcview.setVisibility(View.GONE);
//        Toast.makeText(this, "无网络链接", Toast.LENGTH_SHORT).show();
    }


    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
//        if (view_super_player != null) {
//            view_super_player.onPause();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (view_super_player != null) {
//            view_super_player.onResume();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (view_super_player != null) {
            view_super_player.onDestroy();
        }
        stopLrcPlay();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (view_super_player != null) {
            view_super_player.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onBackPressedSupport() {
        if (view_super_player != null && view_super_player.onBackPressed()) {
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    public void showLrc(final boolean isSuccess, final List<LrcRow> rows) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                if (isSuccess) {
                    if (rows != null) {
                        lrcview.setLrcData(rows);
                    }
                } else {
                    view_super_player.play(url == null ? "http://ws.stream.qqmusic.qq.com/1913719.m4a?fromtag=46" : url);//开始播放视频
                    Glide.with(MusicPlayStoreActivity.this).load(photoUrl == null ? "http://imgcache.qq.com/music/photo/album_500/20/500_albumpic_140820_0.jpg" : photoUrl)
                            .placeholder(R.drawable.ic_defout)
                            .crossFade()
                            .into(music_photo);
                    lrcview.setLrcData(null);
                    return;

                }
                view_super_player.play(url == null ? "http://ws.stream.qqmusic.qq.com/1913719.m4a?fromtag=46" : url);//开始播放视频
                Glide.with(MusicPlayStoreActivity.this).load(photoUrl == null ? "http://imgcache.qq.com/music/photo/album_500/20/500_albumpic_140820_0.jpg" : photoUrl)
                        .placeholder(R.drawable.ic_defout)
                        .crossFade()
                        .bitmapTransform(new BlurTransformation(MusicPlayStoreActivity.this, 0.5f, R.color.big_white))
                        .into(music_photo);


            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe();

    }

    /**
     * 停止展示歌曲
     */
    public void stopLrcPlay() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void restart(long time) {
        lrcview.seekLrcToTime(time);
    }

    @Override
    public void showBar() {
        if (view_super_player != null) {
            view_super_player.showBottomControl(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view_super_player.showBottomControl(false);
                }
            }, 1000);
        }
    }

    /**
     * 展示歌曲的定时任务
     */
    class LrcTask extends TimerTask {
        @Override
        public void run() {
            // 获取歌曲播放的位置
            final long timePassed = view_super_player.getCurrentPosition();
            MusicPlayStoreActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // 滚动歌词
                    lrcview.seekLrcToTime(timePassed);

                }
            });

        }
    }

    private int getCurrentTylp() {
        String sp = SharedPreferencesUtil.getInstance(this).getSP(UtilCode.MusicPlayTylp);
        if (sp.equals("")) {
            return R.drawable.ic_playlist_play;
        } else {
            int i = Integer.parseInt(sp);
            int returnImage = R.drawable.ic_playlist_play;
            switch (i) {
                case 1:
                    returnImage = R.drawable.ic_playlist_play;
                    break;
                case 2:
                    returnImage = R.drawable.ic_repeat;
                    break;
                case 3:
                    returnImage = R.drawable.ic_repeat_one;
                    break;
                case 4:
                    returnImage = R.drawable.ic_shuffle;
                    break;
            }
            return returnImage;
        }
    }

    private int setCurrentTylp() {
        String sp = SharedPreferencesUtil.getInstance(this).getSP(UtilCode.MusicPlayTylp);
        if (sp.equals("")) {
            SharedPreferencesUtil.getInstance(this).putSP(UtilCode.MusicPlayTylp, 2 + "");
            new ToastUtil().Short(this, "循环播放").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
            return R.drawable.ic_repeat;
        } else {
            int i = Integer.parseInt(sp);
            int returnImage = R.drawable.ic_playlist_play;
            switch (i) {
                case 1:
                    returnImage = R.drawable.ic_repeat;
                    SharedPreferencesUtil.getInstance(this).putSP(UtilCode.MusicPlayTylp, 2 + "");
                    new ToastUtil().Short(this, "循环播放").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    break;
                case 2:
                    returnImage = R.drawable.ic_repeat_one;
                    SharedPreferencesUtil.getInstance(this).putSP(UtilCode.MusicPlayTylp, 3 + "");
                    new ToastUtil().Short(this, "单曲循环").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    break;
                case 3:
                    returnImage = R.drawable.ic_shuffle;
                    SharedPreferencesUtil.getInstance(this).putSP(UtilCode.MusicPlayTylp, 4 + "");
                    new ToastUtil().Short(this, "随机播放").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    break;
                case 4:
                    returnImage = R.drawable.ic_playlist_play;
                    SharedPreferencesUtil.getInstance(this).putSP(UtilCode.MusicPlayTylp, 1 + "");
                    new ToastUtil().Short(this, "顺序播放").setToastBackground(Color.WHITE, R.drawable.toast_radius).show();
                    break;
            }
            return returnImage;
        }
    }

    private void play() {
        url = mediaBean.getUrl();
        photoUrl = mediaBean.getPhotoUrl();
        lyricId = mediaBean.getLyricId();
        name = mediaBean.getTitle();
        singerName = mediaBean.getSingerName();

        presenter.startLrc(lyricId, this);
        TextView music_play_titile = toolbar.findViewById(R.id.music_play_titile);
        music_play_titile.setText(name == null ? "MusicPlay" : name);
    }

    @Override
    public MusicPlayStoreContract.Presenter bindPresenter() {
        return new MusicPlayStorePresenter();
    }
}

