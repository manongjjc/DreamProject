package com.jjc.dreamproject.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jjc.dreamproject.bean.UserBean;
import com.jjc.dreamproject.contract.MainContract;
import com.jjc.dreamproject.presenter.MainPresenter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class MainModel implements MainContract.Model {
    private MainPresenter presenter;

    public MainModel(MainPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void login(String name, final String pathword) {
        BmobQuery<UserBean> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("name",name);
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(new FindListener<UserBean>() {
            @Override
            public void done(List<UserBean> list, BmobException e) {
                if(e==null){
                    if(list.size()>0){
                        String pathword1 = list.get(0).getPathword();
                        if(pathword.equals(pathword1)){
                            presenter.loginSucceed();
                        }else{
                            presenter.loginDefeated("账号密码错误，请重试");
                        }
                    }else{
                        presenter.loginDefeated("账号密码错误，请重试");
                    }
                }else{
                    if(e.getErrorCode()==101){
                        presenter.loginDefeated("用戶尚未注册，请前往注册");
                    }else{
                        presenter.loginDefeated(e.getMessage());
                    }


                }
            }
        });
//          Observable.create(new Observable.OnSubscribe<Object>() {
//              @Override
//              public void call(Subscriber<? super Object> subscriber) {
//                  try {
//                      Document document = Jsoup.connect("http://m.cmvideo.cn/wap/resource/mh/share/migushare.jsp?cid=624878440").get();
//                      Log.d("JYD",document.toString());
//                      String attr = document.getElementsByTag("video").attr("src");
//                      Log.d("JYD",attr);
//                  } catch (IOException e) {
//                      e.printStackTrace();
//                  }
//
//              }
//          }).subscribeOn(Schedulers.newThread()).subscribe();

    }
    //http://h5live.gslb.cmvideo.cn/wd_r2/ocn/cctv14hd/600/index.m3u8?msisdn=0497054525066&amp;mdspid=&amp;spid=699004&amp;netType=1&amp;sid=5500212875&amp;pid=2028597139&amp;timestamp=20171222171308&amp;SecurityKey=20171222171308&amp;resourceId=&amp;resourceType=&amp;Channel_ID=1004_10010001005&amp;ProgramID=624878440&amp;ParentNodeID=-99&amp;client_ip=183.64.251.189&amp;assertID=5500212875&amp;encrypt=b1062880ac34c8fef8f8a0ef39e2f14a
    // http://h5live.gslb.cmvideo.cn/wd_r2/ocn/cctv14hd/600/index.m3u8?msisdn=0416956706851&mdspid=&spid=699004&netType=1&sid=5500212875&pid=2028597139&timestamp=20171222171549&SecurityKey=20171222171549&resourceId=&resourceType=&Channel_ID=1004_10010001005&ProgramID=624878440&ParentNodeID=-99&client_ip=183.64.251.189&assertID=5500212875&encrypt=fa6d7023cc187e73642571182aebb4f0
}

