package com.jjc.dreamproject.application;

import android.app.Application;
import android.util.Log;

import com.jjc.dreamproject.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;
import me.yokeyword.fragmentation.Fragmentation;

/**
 * Created by JJC on 2017/12/25.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "1885796a71", true);
        Bmob.initialize(this, "68d47a7bb7651a77eaf249b092038ca6");
        Fragmentation.builder()
                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();

    }
}
