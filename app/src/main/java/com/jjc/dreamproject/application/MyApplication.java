package com.jjc.dreamproject.application;

import android.app.Application;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by JJC on 2017/12/25.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "1885796a71", true);
    }
}
