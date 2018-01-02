package com.jjc.dreamproject.view.music;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jjc.dreamproject.R;
import com.jjc.dreamproject.anim.BlurTransformation;
import com.jjc.dreamproject.bean.MediaBean;
import com.jjc.dreamproject.contract.music.MusicPlayContract;
import com.jjc.dreamproject.presenter.music.MusicPlayPresenter;

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
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;


public class MusicPlayActivity extends BaseActivity<MusicPlayContract.Presenter> implements MusicPlayContract.View ,SuperPlayer.OnNetChangeListener, SeekListener, LrcShowBarInterface {
    private Toolbar toolbar;
    private SuperPlayer view_super_player;
    private ImageView music_photo;
    private String url;
    private String photoUrl;
    private String name;
    private String lyricId;
    private String singerName;
    private LrcView lrcview;

    // 更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    // 更新歌词的定时器
    private Timer mTimer;
    // 更新歌词的定时任务
    private TimerTask mTask;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        initData();
        initView();
        initToolbar();
        initPlay();
        presenter.startLrc(lyricId,this);

    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        photoUrl = intent.getStringExtra("photoUrl");
        lyricId = intent.getStringExtra("lyricId");
        name = intent.getStringExtra("name");
        singerName = intent.getStringExtra("singerName");
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
                        Log.d("JYD","监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）");
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
        music_play_titile.setText(name==null?"MusicPlay":name);
        toolbar.findViewById(R.id.music_store).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LemonBubbleInfo roundProgressBubbleInfo = LemonBubble.getRoundProgressBubbleInfo();
                roundProgressBubbleInfo.setIconColor(Color.GRAY);
                roundProgressBubbleInfo.setTitle("正在提交数据，请稍等");
                LemonBubble.showBubbleInfo(MusicPlayActivity.this,roundProgressBubbleInfo);
                BmobQuery<MediaBean> query = new BmobQuery<MediaBean>();
                query.addWhereEqualTo("url",url);
                query.findObjects(new FindListener<MediaBean>() {
                    @Override
                    public void done(List<MediaBean> list, BmobException e) {
                        if(e==null){
                            if(list.size()==0){
                                MediaBean bean = new MediaBean();
                                bean.setTitle(name);
                                bean.setUrl(url);
                                bean.setTylp(2);
                                bean.setLyricUrl(UrlApi.getMusicLyric(Integer.parseInt(lyricId)));
                                bean.setSingerName(singerName);
                                bean.setPhotoUrl(photoUrl);
                                bean.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e==null){
                                            LemonBubble.showRight(MusicPlayActivity.this,"添加成功",1500);
                                        }else{
                                            LemonBubble.showError(MusicPlayActivity.this,"添加收藏失败，请重试",1500);
                                        }
                                    }
                                });
                            }else{
                                LemonBubble.showError(MusicPlayActivity.this,"已收藏，请勿重复收藏",1500);
                            }
                        }else{
                            LemonBubble.showError(MusicPlayActivity.this,"添加收藏失败，请重试",1500);
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.music_play_toolbar);
        view_super_player = (SuperPlayer) findViewById(R.id.view_super_player);
        music_photo = (ImageView) findViewById(R.id.music_photo);
        lrcview = (LrcView) findViewById(R.id.music_lrcview);

        lrcview.setBarInterface(this);
        view_super_player.setSeekListener(this);
        // 设置自定义的LrcView上下拖动歌词时监听
        lrcview.setLrcViewSeekListener(new ILrcView.LrcViewSeekListener() {

            @Override
            public void onSeek(LrcRow currentlrcrow, long Currenselectrowtime) {
                if (view_super_player != null) {
                    view_super_player.seekTo((int) Currenselectrowtime,true);
                }
            }
        });
    }


    @Override
    public MusicPlayContract.Presenter bindPresenter() {
        return new MusicPlayPresenter();
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
    public void onBackPressed() {
        if (view_super_player != null && view_super_player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void showLrc(final boolean isSuccess, final List<LrcRow> rows) {
               Observable.create(new Observable.OnSubscribe<Object>() {
                   @Override
                   public void call(Subscriber<? super Object> subscriber) {
                       if(isSuccess){
                           if(rows!=null){
                               lrcview.setLrcData(rows);
                           }
                       }else{
                           LemonBubble.showError(MusicPlayActivity.this,"歌词加载失败",1000);
                           view_super_player.play(url==null?"http://ws.stream.qqmusic.qq.com/1913719.m4a?fromtag=46":url);//开始播放视频
                           Glide.with(MusicPlayActivity.this).load(photoUrl==null?"http://imgcache.qq.com/music/photo/album_500/20/500_albumpic_140820_0.jpg":photoUrl)
                                   .placeholder(R.drawable.ic_defout)
                                   .crossFade()
                                   .into(music_photo);
                           return;

                       }
                       view_super_player.play(url==null?"http://ws.stream.qqmusic.qq.com/1913719.m4a?fromtag=46":url);//开始播放视频
                       Glide.with(MusicPlayActivity.this).load(photoUrl==null?"http://imgcache.qq.com/music/photo/album_500/20/500_albumpic_140820_0.jpg":photoUrl)
                               .placeholder(R.drawable.ic_defout)
                               .crossFade()
                               .bitmapTransform(new BlurTransformation(MusicPlayActivity.this,0.5f, R.color.big_white))
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
        if(view_super_player!=null){
            view_super_player.showBottomControl(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    view_super_player.showBottomControl(false);
                }
            },1000);
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
            MusicPlayActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    // 滚动歌词
                    lrcview.seekLrcToTime(timePassed);

                }
            });

        }
    }


}

